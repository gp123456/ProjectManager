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
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.LocationEnum;
import com.allone.projectmanager.model.ProjectModel;
import com.google.common.base.Strings;
import java.util.Collection;
import java.util.List;
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

    private String[] getProjectBillInfo(Long pId) {
        String[] result = {"", "", "", "", ""};
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);
        List<BillMaterialService> bmss = null;
        Integer index = 1;

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                bmss = srvProjectManager.getDaoProjectBill().getByProject(pd.getId());
                result[0] += (!(index++).equals(pds.size())) ?
                             "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n" :
                             "<option value='" + pd.getId() + "' selected>" + pd.getReference() + "</option>\n";

                if (bmss != null && !bmss.isEmpty()) {
                    for (BillMaterialService value : bmss) {
                        List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoProjectBillItem().getByBillMaterialService(value.getId());

                        if (bmsis != null && !bmsis.isEmpty()) {
                            for (BillMaterialServiceItem bmsi : bmsis) {
                                setVirtualRequestQuotationItem(new ProjectModel(value.getProject(), LocationEnum.GREECE.getId()),
                                                               new RequestQuotationItem.Builder()
                                                               .setItemBillMaterialService(bmsi.getId())
                                                               .build());
                            }
                        }
                    }
                }
            }
            if (bmss != null && !bmss.isEmpty()) {
                BillMaterialService bms = bmss.get(0);

                result[1] += "";
                result[2] += CurrencyEnum.EUR.name();
                if (!Strings.isNullOrEmpty(bms.getNote())) {
                    result[3] += bms.getNote();
                }
                result[4] = createRquestQuotationItem(new ProjectModel(bms.getProject(), LocationEnum.GREECE.getId()));
            }
        }

        return result;
    }

    private String createRquestQuotationItem(ProjectModel pm) {
        String result = "";

        Collection<RequestQuotationItem> rqis = getRequestQuotationItems(pm);

        if (rqis != null && !rqis.isEmpty()) {
            Integer count = 0;

            for (RequestQuotationItem rqi : rqis) {
                BillMaterialServiceItem bmsi = srvProjectManager.getDaoProjectBillItem().getById(rqi.getItemBillMaterialService());

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
        this.setContent("../project/RequestQuotation.jsp");
        setHeaderInfo(model);
        p = srvProjectManager.getDaoProject().getById(p.getId());
        String[] pbInfo = getProjectBillInfo(p.getId());
//        Map values = model.asMap();
//        
//        if (values != null && !values.isEmpty()) {
//            values.clear();
//        }
        model.addAttribute("projectId", p.getId());
        model.addAttribute("projectReference", p.getReference());
        model.addAttribute("subproject", pbInfo[0]);
        model.addAttribute("supplier", pbInfo[1]);
        model.addAttribute("currency", pbInfo[2]);
        model.addAttribute("location", createLocations());
        model.addAttribute("noteBillMaterialService", pbInfo[3]);
        model.addAttribute("itemBillMaterialService", pbInfo[4]);
        model.addAttribute("buttonSavePDF", "<input type='button' value='Save PDF' class='button' onclick='savePDF(\"" + p.getId() + "\")'>");
        model.addAttribute("buttonSaveExcel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS(\"" + p.getId() + "\")'>");
        model.addAttribute("buttonSendEmail", "<input type='button' value='Send eMail' class='button' onclick='sendEmail(\"" + p.getId() + "\")'>");

        return "index";
    }

    @RequestMapping(value = "/request-quotation/supplier")
    public @ResponseBody
    String viewSupplier(Long pdId, Integer location, String supplier) {
        String response = "";

//        if (pdId != null && location != null && !Strings.isNullOrEmpty(supplier)) {
//            ProjectModel pbm = new ProjectModel(pdId, location);
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
}
