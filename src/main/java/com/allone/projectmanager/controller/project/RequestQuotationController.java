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
import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.RequestQuotationItem;
import com.allone.projectmanager.entities.Stock;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.LocationEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.RequestQuotationModel;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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

    private String[] getRequestQuotationInfo(Long pId) {
        String[] result = {"", "", "", "", ""};
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);
        BillMaterialService bms = null;
        Integer index = 1;

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                bms = srvProjectManager.getDaoProjectBill().getByProject(pd.getId());
                result[0] += (!(index++).equals(pds.size())) ?
                             "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n" :
                             "<option value='" + pd.getId() + "' selected>" + pd.getReference() + "</option>\n";

                if (bms != null) {
                    List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoProjectBillItem().getByBillMaterialService(bms.getId());

                    if (bmsis != null && !bmsis.isEmpty()) {
                        for (BillMaterialServiceItem bmsi : bmsis) {
//                            setVirtualRequestQuotationItem(new ProjectModel(bms.getProject(), LocationEnum.GREECE.getId()),
//                                                           new RequestQuotationItem.Builder()
//                                                           .setItemBillMaterialService(bmsi.getId())
//                                                           .build());
                        }
                    }
                }
            }
            if (bms != null) {
                result[1] += "";
                result[2] += CurrencyEnum.EUR.name();
                if (!Strings.isNullOrEmpty(bms.getNote())) {
                    result[3] += bms.getNote();
                }
//                result[4] = createRquestQuotationItem(new ProjectModel(bms.getProject(), LocationEnum.GREECE.getId()));
            }
        }

        return result;
    }

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
                                        Boolean checked = findVirtualItem(new RequestQuotationModel(pd.getId(), ""), bmsi.getId());

                                        result[2] +=
                                        "<tr>\n" +
                                        "<td>" + imno + "</td>\n" +
                                        "<td>" + stockName + "</td>\n" +
                                        "<td>" + quantity + "</td>\n" +
                                        "<td><input type='checkbox' " + ((checked) ? "checked" : "") + " onclick='handleClick(this," + bmsi.getBillMaterialService() + "," + bmsi.getId() +
                                        ");'></td>\n" +
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

    private String[] getBillMaterialServiceByProjectDetail(RequestQuotationModel rqm) {
        String[] result = {"", ""};
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(rqm.getProjectDetailId());
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
                                    Boolean checked = findVirtualItem(rqm, bmsi.getId());

                                    result[1] +=
                                    "<tr>\n" +
                                    "<td>" + imno + "</td>\n" +
                                    "<td>" + stockName + "</td>\n" +
                                    "<td>" + quantity + "</td>\n" +
                                    "<td><input type='checkbox' " + ((checked) ? "checked" : "") + " onclick='handleClick(this," + bmsi.getBillMaterialService() + "," + bmsi.getId() + ");'></td>\n" +
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

    @RequestMapping(value = "/request-quotation")
    public String RequestQuotation(Project p, String mode, Model model) {
        if (mode.equals("RQ")) {
            this.setTitle("Projects - Request Quotation");
            this.setSide_bar("../project/sidebar.jsp");
            this.setContent("../project/RequestQuotation.jsp");
            p = srvProjectManager.getDaoProject().getById(p.getId());
            setHeaderInfo(model);

            model.addAttribute("projectId", p.getId());
            model.addAttribute("projectReference", p.getReference());
            model.addAttribute("buttonCancel", "<input type='button' value='Cancel' class='button' onclick='cancel(" + p.getId() + ")'>");
            model.addAttribute("buttonSendEmail", "<input type='button' value='Send eMail' class='button' onclick='sendEmail(" + p.getId() + ")'>");
            model.addAttribute("buttonSavePDF", "<input type='button' value='Save PDF' class='button' onclick='savePDF(" + p.getId() + ")'>");
            model.addAttribute("buttonSaveExcel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS(" + p.getId() + ")'>");
        } else {
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
        }

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
            String[] pbInfo = getBillMaterialServiceByProjectDetail(new RequestQuotationModel(pdId, ""));

            content.put("billMaterialService", pbInfo[0]);
            content.put("billMaterialServiceItems", pbInfo[1]);

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = "/request-quotation/create")
    public @ResponseBody
    String create(Long pdId, Boolean checked, Long bms, Long bmsi) {
        if (pdId != null &&
            checked != null &&
            bms != null &&
            bmsi != null) {
            RequestQuotationModel rqm = new RequestQuotationModel(pdId, "");

            if (checked.equals(Boolean.TRUE)) {
                setVirtualRequestQuotation(rqm,
                                           new RequestQuotation.Builder()
                                           .setBillMaterialService(bms)
                                           .build());
                setVirtualRequestQuotationItem(rqm,
                                               new RequestQuotationItem.Builder()
                                               .setBillMaterialServiceItem(bmsi)
                                               .build());
            } else {
                removeVirtualRequestQuotation(rqm,
                                              new RequestQuotation.Builder()
                                              .setBillMaterialService(bms)
                                              .build());
                removeVirtualRequestQuotationItem(rqm,
                                                  new RequestQuotationItem.Builder()
                                                  .setBillMaterialServiceItem(bmsi)
                                                  .build());
            }

            String[] pbInfo = getBillMaterialServiceByProjectDetail(rqm);

            return pbInfo[1];
        }

        return "";
    }

    @RequestMapping(value = "/request-quotation/submit")
    public @ResponseBody
    String submit(Long id) {
        return "request-quotation?id=" + id + "&mode=RQ";
    }

    @RequestMapping(value = "/request-quotation/content")
    public @ResponseBody
    String content(Long pId) {
        if (pId != null) {
            Map<String, String> content = new HashMap<>();
            String[] rqInfo = getRequestQuotationInfo(pId);
            List<Company> suppliers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.SUPPLIER.name());
            String response = "";

            content.put("subprojects", rqInfo[0]);
            if (suppliers != null && !suppliers.isEmpty()) {
                for (Company supplier : suppliers) {
                    response += "<option value='" + supplier.getName()+ "'>" + supplier.getName()+ "</option>\n";
                }
                content.put("suppliers", response);
            }
            content.put("requestQuotation", rqInfo[1]);
            content.put("itemRequestQuotation", rqInfo[2]);
            content.put("note", rqInfo[3]);

            return new Gson().toJson(content);
        } else {
            return "";
        }
    }
}
