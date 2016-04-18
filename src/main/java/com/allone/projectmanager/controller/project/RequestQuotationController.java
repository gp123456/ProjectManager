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
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
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

    private String[] getRequestQuotationInfo(Long bms) {
        String[] result = {"", "", ""};
        RequestQuotation rq = getRequestQuotation(bms);
        Integer index = 1;

        if (rq != null) {
            Integer materialCost = (rq.getMaterialCost() instanceof Integer) ? rq.getMaterialCost() : 0;
            Integer expensesCost = (rq.getExpensesCost() instanceof Integer) ? rq.getExpensesCost() : 0;
            Integer grandTotal = (rq.getGrandTotal() instanceof Integer) ? rq.getGrandTotal() : 0;
            Integer deliveryCost = (rq.getDeliveryCost() instanceof Integer) ? rq.getDeliveryCost() : 0;
            Integer otherExpenses = (rq.getOtherExpenses() instanceof Integer) ? rq.getOtherExpenses() : 0;

            result[0] = "<tr>\n" +
                        "<td>" + materialCost + "</td>\n" +
                        "<td>" + expensesCost + "</td>\n" +
                        "<td>" + grandTotal + "</td>\n" +
                        "<td id='delivery" + rq.getBillMaterialService() +
                        "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + ((!deliveryCost.equals(0)) ? deliveryCost : "") + "</td>\n" +
                        "<td id='expenses" + rq.getBillMaterialService() +
                        "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + ((!otherExpenses.equals(0)) ? otherExpenses : "") + "</td>\n" +
                        "<td><input type='button' value='Edit' class='button' onclick='editRQItem(" + rq.getBillMaterialService() + ")'></td>\n" +
                        "<td><input type='button' value='Save' class='button' onclick='saveRQItem(" + rq.getBillMaterialService() + ")'></td>\n" +
                        "</tr>\n";
            result[1] = (rq != null) ? rq.getNote() : "";

        }

        Collection<RequestQuotationItem> rqis = getRequestQuotationItems(bms);

        index = 1;
        if (rqis != null && !rqis.isEmpty()) {
            for (RequestQuotationItem rqi : rqis) {
                BillMaterialServiceItem bmsi = (rqi.getBillMaterialServiceItem() != null) ? srvProjectManager.getDaoBillMaterialServiceItem().getById(rqi.getBillMaterialServiceItem()) : null;
                Item item = (bmsi != null) ? srvProjectManager.getDaoItem().getById(bmsi.getItem()) : null;
                String imno = (item != null) ? item.getImno() : "";
                String description = (item != null) ? item.getDescription() : "";
                Integer quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity() : 0;
//                    Integer price = (rqi.getUnitPrice() != null) ? rqi.getUnitPrice() : 0;
                BigDecimal discount = (rqi.getDiscount() != null) ? rqi.getDiscount() : BigDecimal.ZERO;
                Integer availability = (rqi.getAvailability() != null) ? rqi.getAvailability() : 0;
                Integer total = (rqi.getTotal() != null) ? rqi.getTotal() : 0;

                result[2] += "<tr>\n" +
                             "<td>" + index++ + "</td>\n" +
                             "<td>" + imno + "</td>\n" +
                             "<td>" + description + "</td>\n" +
                             "<td>" + quantity + "</td>\n" +
                             //                                 "<td id='price" + pds.get(0).getId() + rq.getSupplier() +
                             //                                 "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + price + "</td>\n" +
                             "<td id='discount" + rq.getBillMaterialService() +
                             bmsi.getId() +
                             "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + ((!discount.equals(BigDecimal.ZERO)) ? discount : "") + "</td>\n" +
                             "<td id='availability" + rq.getBillMaterialService() +
                             bmsi.getId() +
                             "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" + ((!availability.equals(0)) ? availability : "") + "</td>\n" +
                             "<td>" + total + "</td>\n" +
                             "<td><input type='button' value='Edit' class='button' onclick='editRQItem(" + rq.getBillMaterialService() + "," +
                             bmsi.getId() + ")'></td>\n" +
                             "<td><input type='button' value='Save' class='button' onclick='saveRQItem(" + rq.getBillMaterialService() + "," +
                             bmsi.getId() + ")'></td>\n" +
                             "</tr>";
            }
        }

        return result;
    }

    private String[] getRequestQuotationByProject(Long pId) {
        String[] result = {"", "", "", "", "", ""};
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

            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pds.get(0).getId());

            if (bms != null) {
                String[] info = getRequestQuotationInfo(bms.getId());

                result[1] = info[0];
                result[2] = info[1];
                result[3] = info[2];
            }

            for (CurrencyEnum currency : CurrencyEnum.values()) {
                result[4] += "<option value='" + currency.getId() + "'>" + currency.toString() + "</option>\n";
            }
        }

        return result;
    }

    private String[] getBillMaterialServiceInfo(Long pdId) {
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
        BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);
        String[] result = {"", "", ""};

        if (bms != null) {
            String name = (!Strings.isNullOrEmpty(bms.getName())) ? bms.getName() : "";
            String subproject = (!Strings.isNullOrEmpty(pd.getReference())) ? pd.getReference() : "";

            result[0] = "<tr>\n" +
                        "<td>" + name + "</td>\n" +
                        "<td>" + subproject + "</td>\n</tr>\n";
            List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoBillMaterialServiceItem().getByBillMaterialService(bms.getId());

            if (bmsis != null && !bmsis.isEmpty()) {
                for (BillMaterialServiceItem bmsi : bmsis) {
                    Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                    if (item != null) {
                        Stock stock = srvProjectManager.getDaoStock().getById(item.getLocation());
                        String imno = item.getImno();
                        String stockName = (stock != null) ? stock.getLocation() : "";
                        String quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity().toString() : "";
                        Boolean checked = findVirtualItem(bms.getId(), bmsi.getId());

                        result[1] +=
                        "<tr>\n" +
                        "<td>" + imno + "</td>\n" +
                        "<td>" + stockName + "</td>\n" +
                        "<td>" + quantity + "</td>\n" +
                        "<td><input type='checkbox' " + ((checked) ? "checked" : "") + " onclick='handleClick(this," +
                        bmsi.getBillMaterialService() + "," +
                        bmsi.getId() + ");'></td>\n" +
                        "</tr>\n";
                    }
                }
            }
        }

        return result;
    }

    private String[] getBillMaterialServiceByProjectInfo(Long pId) {
        String[] result = {"", "", ""};
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);
        Integer index = 1;

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                if (pd.getType().equals(ProjectTypeEnum.SALE.name())) {
                    result[0] += "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n";

                    if (index.equals(1)) {
                        String[] info = getBillMaterialServiceInfo(pd.getId());

                        result[1] = info[0];
                        result[2] = info[1];

                        index++;
                    }
                }
            }
        }

        return result;
    }

    private String[] getBillMaterialServiceByProjectDetailInfo(Long pdId) {
        String[] result = {"", "", ""};
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
        Integer index = 1;

        if (pd != null) {
            if (pd.getType().equals(ProjectTypeEnum.SALE.name())) {
                result[0] += "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n";

                if (index.equals(1)) {
                    String[] info = getBillMaterialServiceInfo(pd.getId());

                    result[1] = info[0];
                    result[2] = info[1];

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
            model.addAttribute("buttonCancel", "<input type='button' value='Cancel' class='button' onclick='cancel()'>");
            model.addAttribute("buttonSendEmail", "<input type='button' value='Send eMail' class='button' onclick='sendEmail()'>");
            model.addAttribute("buttonSavePDF", "<input type='button' value='Save PDF' class='button' onclick='savePDF()'>");
            model.addAttribute("buttonSaveExcel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS()'>");
        } else if (mode.equals("BMSI")) {
            this.setTitle("Projects - Request Quotation");
            this.setSide_bar("../project/sidebar.jsp");
            this.setContent("../project/RequestQuotationSelectItem.jsp");
            setHeaderInfo(model);
            String[] pbInfo = getBillMaterialServiceByProjectDetailInfo(p.getProjectDetail());
            p = srvProjectManager.getDaoProject().getById(p.getId());

            model.addAttribute("projectId", p.getId());
            model.addAttribute("projectReference", p.getReference());
            model.addAttribute("subproject", pbInfo[0]);
            model.addAttribute("billMaterialService", pbInfo[1]);
            model.addAttribute("billMaterialServiceItems", pbInfo[2]);
        } else {
            this.setTitle("Projects - Request Quotation");
            this.setSide_bar("../project/sidebar.jsp");
            this.setContent("../project/RequestQuotationSelectItem.jsp");
            setHeaderInfo(model);
            p = srvProjectManager.getDaoProject().getById(p.getId());
            String[] pbInfo = getBillMaterialServiceByProjectInfo(p.getId());

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
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

            if (bms != null) {
                Map<String, String> content = new HashMap<>();
                String[] rqInfo = getRequestQuotationInfo(bms.getId());

                content.put("requestQuotation", rqInfo[0]);
                content.put("note", rqInfo[1]);
                content.put("itemRequestQuotation", rqInfo[2]);

                return new Gson().toJson(content);
            }

        }

        return "";
    }

    @RequestMapping(value = "/request-quotation/change-subproject-bms")
    public @ResponseBody
    String changeBMSSubproject(Long pdId) {
        if (pdId != null) {
            Map<String, String> content = new HashMap<>();
            String[] pbInfo = getBillMaterialServiceInfo(pdId);

            content.put("billMaterialService", pbInfo[0]);
            content.put("billMaterialServiceItems", pbInfo[1]);

            return new Gson().toJson(content);
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/request-quotation/create")
    public @ResponseBody
    String create(Long pdId, Boolean checked, Long bms, Long bmsi) {
        if (checked != null &&
            bms != null &&
            bmsi != null) {
            if (checked.equals(Boolean.TRUE)) {
                setVirtualRequestQuotation(srvProjectManager,
                                           bms,
                                           new RequestQuotation.Builder()
                                           .setBillMaterialService(bms)
                                           .build());
                setVirtualRequestQuotationItem(bms,
                                               new RequestQuotationItem.Builder()
                                               .setBillMaterialServiceItem(bmsi)
                                               .build());
            } else {
                removeVirtualRequestQuotation(bms);
                removeVirtualRequestQuotationItem(bms,
                                                  new RequestQuotationItem.Builder()
                                                  .setBillMaterialServiceItem(bmsi)
                                                  .build());
            }

            String[] pbInfo = getBillMaterialServiceInfo(pdId);

            return pbInfo[1];
        }

        return "";
    }

    @RequestMapping(value = "/request-quotation/submit")
    public @ResponseBody
    String submit(Long id) {
        return "request-quotation?id=" + id + "&mode=RQ";
    }

    @RequestMapping(value = "/request-quotation//select-bmsi")
    public @ResponseBody
    String selectBMSI(Long pId, Long pdId) {
        return "request-quotation?id=" + pId + "&projectDetail=" + pdId + "&mode=BMSI";
    }

    @RequestMapping(value = "/request-quotation/content")
    public @ResponseBody
    String content(Long pId) {
        if (pId != null) {
            Map<String, String> content = new HashMap<>();
            String[] rqInfo = getRequestQuotationByProject(pId);
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
            content.put("currency", rqInfo[4]);

            return new Gson().toJson(content);
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/request-quotation/cancel")
    public @ResponseBody
    String cancel(Long pdId) {
        if (pdId != null) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

            if (bms != null) {
                removeVirtualRequestQuotation(bms.getId());
                RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                if (rq != null) {
                    srvProjectManager.getDaoRequestQuotation().delete(rq);

                    List<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQoutation(rq.getId());

                    if (items != null && !items.isEmpty()) {
                        srvProjectManager.getDaoRequestQuotationItem().delete(items);
                    }
                }
            }
        }

        return "index";
    }

    @RequestMapping(value = "/request-quotation/send-email")
    public @ResponseBody
    String sendEmail(Long pdId, String supplierName, String note) throws MessagingException {
        if (pdId != null && !Strings.isNullOrEmpty(supplierName)) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);
            Company supplier = srvProjectManager.getDaoCompany().getByTypeName(CompanyTypeEnum.SUPPLIER, supplierName);

            if (bms != null && supplier != null) {
                RequestQuotation rq = getRequestQuotation(bms.getId());

                if (rq != null) {
                    rq.setCurrency(1);
                    rq.setSupplier(supplierName);
                    rq.setNote(note);
                    rq = srvProjectManager.getDaoRequestQuotation().add(rq);

                    if (rq != null) {
                        List<RequestQuotationItem> items = getRequestQoutationItems(bms.getId());

                        if (items != null && !items.isEmpty()) {
                            for (RequestQuotationItem item : items) {
                                item.setRequestQuotation(rq.getId());
                            }
                            srvProjectManager.getDaoRequestQuotationItem().add(items);
                            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);

                            if (!Strings.isNullOrEmpty(supplier.getEmail1())) {
                                sendMail("mail.forthnet.gr", "info@wcs.com.gr", supplier.getEmail1(), null, "REQUEST FOR QUOTATION - REF:" + pd.getReference(),
                                         "http://localhost:8080/ProjectManager/project/request-quotation?id=3&mode=RQ&supplier=" + supplierName);
                            } else if (!Strings.isNullOrEmpty(supplier.getEmail2())) {
                                sendMail("mail.forthnet.gr", "info@wcs.com.gr", supplier.getEmail2(), null, "REQUEST FOR QUOTATION - REF:" + pd.getReference(),
                                         "http://localhost:8080/ProjectManager/project/request-quotation?id=3&mode=RQ&supplier=" + supplierName);
                            } else if (!Strings.isNullOrEmpty(supplier.getEmail3())) {
                                sendMail("mail.forthnet.gr", "info@wcs.com.gr", supplier.getEmail3(), null, "REQUEST FOR QUOTATION - REF:" + pd.getReference(),
                                         "http://localhost:8080/ProjectManager/project/request-quotation?id=3&mode=RQ&supplier=" + supplierName);
                            }
                        } else {
                            logger.log(Level.SEVERE, "no found items for request quotation:{0}", rq.getId());
                        }
                    } else {
                        logger.log(Level.SEVERE, "error while persist the requers quotation");
                    }
                } else {
                    logger.log(Level.SEVERE, "no found requers quotation for the bill material service id: {0}", bms.getId());
                }
            } else {
                logger.log(Level.SEVERE, "no found bill material service for the project detail id: {0} or supplier with name: {1}", new Object[]{pdId, supplierName});
            }
        } else {
            logger.log(Level.SEVERE, "error one or more from info: {0},{1}", new Object[]{pdId, supplierName});
        }

        return "";
    }
}
