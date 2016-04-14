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
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.RequestQuotationModel;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.Collection;
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
        Integer index = 1;

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                if (pd.getType().equals(ProjectTypeEnum.SALE.name())) {
                    result[0] += (!(index++).equals(pds.size())) ?
                                 "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n" :
                                 "<option value='" + pd.getId() + "' selected>" + pd.getReference() + "</option>\n";
                }
            }

            RequestQuotationModel rqm = new RequestQuotationModel(pds.get(0).getId(), "");
            RequestQuotation rq = getRequestQuotation(rqm);

            if (rq != null) {
                Integer materialCost = (rq.getMaterialCost() instanceof Integer) ? rq.getMaterialCost() : 0;
                Integer expensesCost = (rq.getExpensesCost() instanceof Integer) ? rq.getExpensesCost() : 0;
                Integer grandTotal = (rq.getGrandTotal() instanceof Integer) ? rq.getGrandTotal() : 0;
                Integer deliveryCost = (rq.getDeliveryCost() instanceof Integer) ? rq.getDeliveryCost() : 0;
                Integer otherExpenses = (rq.getOtherExpenses() instanceof Integer) ? rq.getOtherExpenses() : 0;

                result[1] = "<tr>\n" +
                            "<td>" + materialCost + "</td>\n" +
                            "<td>" + expensesCost + "</td>\n" +
                            "<td>" + grandTotal + "</td>\n" +
                            "<td id='delivery" + pds.get(0).getId() + rq.getSupplier() +
                            "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + deliveryCost + "</td>\n" +
                            "<td id='expenses" + pds.get(0).getId() + rq.getSupplier() +
                            "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + otherExpenses + "</td>\n" +
                            "</tr>\n";
                result[2] = (rq != null) ? rq.getNote() : "";

            }

            Collection<RequestQuotationItem> rqis = getRequestQuotationItems(rqm);

            index = 1;
            if (rqis != null && !rqis.isEmpty()) {
                for (RequestQuotationItem rqi : rqis) {
                    BillMaterialServiceItem bmsi = (rqi.getBillMaterialServiceItem() != null) ? srvProjectManager.getDaoProjectBillItem().getById(rqi.getBillMaterialServiceItem()) : null;
                    Item item = (bmsi != null) ? srvProjectManager.getDaoItem().getById(bmsi.getItem()) : null;
                    String imno = (item != null) ? item.getImno() : "";
                    String description = (item != null) ? item.getDescription() : "";
                    Integer quantity = (rqi.getAvailability() != null) ? rqi.getAvailability() : 0;
                    Integer price = (rqi.getUnitPrice() != null) ? rqi.getUnitPrice() : 0;
                    BigDecimal discount = (rqi.getDiscount() != null) ? rqi.getDiscount() : BigDecimal.ZERO;
                    Integer availability = (rqi.getAvailability() != null) ? rqi.getAvailability() : 0;
                    Integer total = (rqi.getTotal() != null) ? rqi.getTotal() : 0;

                    result[3] += "<tr>\n" +
                                 "<td>" + index++ + "</td>\n" +
                                 "<td>" + imno + "</td>\n" +
                                 "<td>" + description + "</td>\n" +
                                 "<td>" + quantity + "</td>\n" +
                                 "<td id='price" + pds.get(0).getId() + rq.getSupplier() +
                                 "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + price + "</td>\n" +
                                 "<td id='discount" + pds.get(0).getId() + rq.getSupplier() +
                                 "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + discount + "</td>\n" +
                                 "<td id='availability" + pds.get(0).getId() + rq.getSupplier() +
                                 "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + availability + "</td>\n" +
                                 "<td>" + total + "</td>\n" +
                                 "</tr>";
                }
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
    String changeSubproject(Long pdId, String supplier) {
        if (pdId != null) {
            Map<String, String> content = new HashMap<>();
            String[] rqInfo = getRequestQuotationInfo(pdId);
            
            content.put("requestQuotation", rqInfo[1]);
            content.put("note", rqInfo[2]);
            content.put("itemRequestQuotation", rqInfo[3]);

            return new Gson().toJson(content);
        } else {
            return "";
        }
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
                    response += "<option value='" + supplier.getName() + "'>" + supplier.getName() + "</option>\n";
                }
                content.put("suppliers", response);
            }
            content.put("requestQuotation", rqInfo[1]);
            content.put("note", rqInfo[2]);
            content.put("itemRequestQuotation", rqInfo[3]);

            return new Gson().toJson(content);
        } else {
            return "";
        }
    }
}
