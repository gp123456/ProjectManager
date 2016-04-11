/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.RequestQuotationCommon;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.BillMaterialServiceItem;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.RequestQuotationItem;
import com.allone.projectmanager.entities.Stock;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.RequestQuotationModel;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project")
public class RequestQuotationController extends RequestQuotationCommon {

    private static final Logger logger = Logger.getLogger(RequestQuotationController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    private String[] getProjectDetailBillMaterialServiceInfo(Long pId) {
        String[] result = {"", "", ""};
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);
        Integer index = 1;

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                if (pd.getType().equals(ProjectTypeEnum.SALE.name())) {
                    result[0] += "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n";

                    if (index.equals(1)) {
                        BillMaterialService bms = srvProjectManager.getDaoProjectBill().getByProject(pd.getId());

                        if (bms != null) {
                            String name = (!Strings.isNullOrEmpty(bms.getName())) ? bms.getName() : "";
                            String subproject = (!Strings.isNullOrEmpty(pd.getReference())) ? pd.getReference() : "";

                            result[1] = "<tr>\n" +
                                        "<td>" + name + "</td>\n" +
                                        "<td>" + subproject + "</td>\n</tr>\n";
                            List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoProjectBillItem().getByBillMaterialService(bms.getId());

                            if (bmsis != null && !bmsis.isEmpty()) {
                                for (BillMaterialServiceItem bmsi : bmsis) {
                                    Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                                    if (item != null) {
                                        Stock stock = srvProjectManager.getDaoStock().getById(item.getLocation());
                                        String imno = item.getImno();
                                        String stockName = (stock != null) ? stock.getLocation() : "";
                                        String quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity().toString() : "";

                                        result[2] +=
                                        "<tr>\n" +
                                        "<td>" + imno + "</td>\n" +
                                        "<td>" + stockName + "</td>\n" +
                                        "<td>" + quantity + "</td>\n" +
                                        "<td><input type='checkbox' id='" + bmsi.getBillMaterialService() + bmsi.getId() + "'></td>\n" +
                                        "</tr>\n";
                                    }
                                }
                            }
                        }
                        index++;
                    }
                }
            }
        }

        return result;
    }

    private String[] getBillMaterialServiceByProjectDetail(Long pdId) {
        String[] result = {"", ""};
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
        Integer index = 1;

        if (pd != null) {
            if (pd.getType().equals(ProjectTypeEnum.SALE.name())) {
                if (index.equals(1)) {
                    BillMaterialService bms = srvProjectManager.getDaoProjectBill().getByProject(pd.getId());

                    if (bms != null) {
                        String name = (!Strings.isNullOrEmpty(bms.getName())) ? bms.getName() : "";
                        String subproject = (!Strings.isNullOrEmpty(pd.getReference())) ? pd.getReference() : "";

                        result[0] = "<tr>\n" +
                                    "<td>" + name + "</td>\n" +
                                    "<td>" + subproject + "</td>\n</tr>\n";
                        List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoProjectBillItem().getByBillMaterialService(bms.getId());

                        if (bmsis != null && !bmsis.isEmpty()) {
                            for (BillMaterialServiceItem bmsi : bmsis) {
                                Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                                if (item != null) {
                                    Stock stock = srvProjectManager.getDaoStock().getById(item.getLocation());
                                    String imno = item.getImno();
                                    String stockName = (stock != null) ? stock.getLocation() : "";
                                    String quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity().toString() : "";

                                    result[1] +=
                                    "<tr>\n" +
                                    "<td>" + imno + "</td>\n" +
                                    "<td>" + stockName + "</td>\n" +
                                    "<td>" + quantity + "</td>\n" +
                                    "<td><input type='checkbox' id='" + bmsi.getBillMaterialService() + bmsi.getId() + "'></td>\n" +
                                    "</tr>\n";
                                }
                            }
                        }
                    }
                    index++;
                }
            }
        }

        return result;
    }

    private String createRquestQuotationItem(RequestQuotationModel pm) {
        String result = "";

        Collection<RequestQuotationItem> rqis = getRequestQuotationItems(pm);

        if (rqis != null && !rqis.isEmpty()) {
            Integer count = 0;

            for (RequestQuotationItem rqi : rqis) {
                BillMaterialServiceItem bmsi = srvProjectManager.getDaoProjectBillItem().getById(rqi.getBillMaterialServiceItem());

                if (bmsi != null) {
                    Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                    result += "<tr>\n" +
                              "<td>" + ++count + "</td>\n" +
                              "<td>" + ((item != null) ? item.getImno() : "") + "</td>\n" +
                              "<td>" + ((item != null) ? item.getDescription() : "") + "</td>\n" +
                              "<td>" + bmsi.getQuantity() + "</td>\n" +
                              "<td id='availability" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>delivery-cost" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>other-expenses" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>unit-price" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>discount" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>total" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "</tr>\n";
                }
            }
        }

        return result;
    }

    @RequestMapping(value = "/request-quotation")
    public String RequestQuotation(Project p, Model model) {
        this.setTitle("Projects - Request Quotation");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/RequestQuotationSelectItem.jsp");
        setHeaderInfo(model);
        p = srvProjectManager.getDaoProject().getById(p.getId());
        String[] pbInfo = getProjectDetailBillMaterialServiceInfo(p.getId());

        model.addAttribute("projectId", p.getId());
        model.addAttribute("projectReference", p.getReference());
        model.addAttribute("subproject", pbInfo[0]);
        model.addAttribute("billMaterialService", pbInfo[1]);
        model.addAttribute("billMaterialServiceItems", pbInfo[2]);
//        model.addAttribute("location", createLocations());
//        model.addAttribute("noteBillMaterialService", pbInfo[3]);
//        model.addAttribute("itemBillMaterialService", pbInfo[4]);
//        model.addAttribute("buttonSavePDF", "<input type='button' value='Save PDF' class='button' onclick='savePDF(\"" + p.getId() + "\")'>");
//        model.addAttribute("buttonSaveExcel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS(\"" + p.getId() + "\")'>");
//        model.addAttribute("buttonSendEmail", "<input type='button' value='Send eMail' class='button' onclick='sendEmail(\"" + p.getId() + "\")'>");

        return "index";
    }

    @RequestMapping(value = "/request-quotation/supplier")
    public @ResponseBody
    String viewSupplier(Long pdId, Integer location, String supplier) {
        String response = "";

//        if (pdId != null && location != null && !Strings.isNullOrEmpty(supplier)) {
//            RequestQuotationModel pbm = new RequestQuotationModel(pdId, location);
//            BillMaterialService vpb = getBillMaterialService(pbm);
//
//            if (vpb != null) {
//                vpb.setSupplier(supplier);
//            }
//
////            response = createProjectBill(pbm);
//        }
        return response;
    }

    @RequestMapping(value = "/request-quotation/change-subproject")
    public @ResponseBody
    String changeSubproject(Long pdId) {
        if (pdId != null) {
            Map<String, String> content = new HashMap<>();
            String[] pbInfo = getBillMaterialServiceByProjectDetail(pdId);

            content.put("billMaterialService", pbInfo[0]);
            content.put("billMaterialServiceItems", pbInfo[1]);

            return new Gson().toJson(content);
        }

        return "";
    }
}
