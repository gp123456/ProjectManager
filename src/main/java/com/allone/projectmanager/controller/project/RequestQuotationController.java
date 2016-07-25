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
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @SuppressWarnings("null")
    private String[] getRequestQuotationInfo(Long bms, Boolean supplier) {
        String[] result = {"", ""};
        RequestQuotation rq = getRequestQuotation(bms);
        @SuppressWarnings("UnusedAssignment")
        Integer index = 1;

        if (rq != null) {
            Integer materialCost = (rq.getMaterialCost() instanceof Integer) ? rq.getMaterialCost() : 0;
            Integer grandTotal = (rq.getGrandTotal() instanceof Integer) ? rq.getGrandTotal() : 0;
            Integer deliveryCost = (rq.getDeliveryCost() instanceof Integer) ? rq.getDeliveryCost() : 0;
            Integer otherExpenses = (rq.getOtherExpenses() instanceof Integer) ? rq.getOtherExpenses() : 0;

            result[0] = (supplier.equals(Boolean.FALSE))
                    ? "<tr>\n"
                    + "<td id='delivery" + rq.getBillMaterialService() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'></td>\n"
                    + "<td id='expenses" + rq.getBillMaterialService() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'></td>\n"
                    + "<td>" + materialCost + "</td>\n"
                    + "<td>" + grandTotal + "</td>\n"
                    + "</tr>\n"
                    : "<tr>\n"
                    + "<td id='delivery" + rq.getBillMaterialService()
                    + "' onclick='clearValue(\"delivery\"," + rq.getBillMaterialService() + "," + 0
                    + ");' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + ((!deliveryCost.equals(0)) ? deliveryCost : "") + "</td>\n"
                    + "<td id='expenses" + rq.getBillMaterialService()
                    + "' onclick='clearValue(\"expenses\"," + rq.getBillMaterialService() + "," + 0
                    + ");' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + ((!otherExpenses.equals(0)) ? otherExpenses : "") + "</td>\n"
                    + "<td>" + materialCost + "</td>\n"
                    + "<td>" + grandTotal + "</td>\n"
                    + "</tr>\n";
        }

        Collection<RequestQuotationItem> rqis = getRequestQuotationItems(bms);

        index = 1;
        if (rqis != null && !rqis.isEmpty()) {
            for (RequestQuotationItem rqi : rqis) {
                BillMaterialServiceItem bmsi = (rqi.getBillMaterialServiceItem() != null) ? srvProjectManager.getDaoBillMaterialServiceItem().getById(rqi.getBillMaterialServiceItem()) : null;
                Item item = (bmsi != null) ? srvProjectManager.getDaoItem().getById(bmsi.getItem()) : null;
                String imno = (item != null) ? item.getImno() : "";
                String description = (item != null) ? item.getDescription() : "";
                @SuppressWarnings("null")
                Integer quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity() : 0;
                Integer price = (rqi.getUnitPrice() != null) ? rqi.getUnitPrice() : 0;
                BigDecimal discount = (rqi.getDiscount() != null) ? rqi.getDiscount() : BigDecimal.ZERO;
                Integer availability = (rqi.getAvailability() != null) ? rqi.getAvailability() : 0;
                Integer total = (rqi.getDeliveryCost() != null) ? rqi.getDeliveryCost().intValue() : 0;

                result[1] += (supplier.equals(Boolean.FALSE))
                        ? "<tr>\n"
                        + "<td>" + index++ + "</td>\n"
                        + "<td>" + imno + "</td>\n"
                        + "<td>" + description + "</td>\n"
                        + "<td>" + quantity + "</td>\n"
                        + "<td id='price" + rq.getBillMaterialService() + bmsi.getId()
                        + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'></td>\n"
                        + "<td id='discount" + rq.getBillMaterialService() + bmsi.getId()
                        + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'></td>\n"
                        + "<td id='availability" + rq.getBillMaterialService() + bmsi.getId()
                        + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'></td>\n"
                        + "<td>" + total + "</td>\n"
                        + "</tr>"
                        : "<tr>\n"
                        + "<td>" + index++ + "</td>\n"
                        + "<td>" + imno + "</td>\n"
                        + "<td>" + description + "</td>\n"
                        + "<td>" + quantity + "</td>\n"
                        + "<td id='price" + rq.getBillMaterialService() + bmsi.getId()
                        + "' onclick='clearValue(\"price\"," + rq.getBillMaterialService() + "," + bmsi.getId()
                        + ");' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                        + ((!price.equals(0)) ? price : "") + "</td>\n"
                        + "<td id='discount" + rq.getBillMaterialService() + bmsi.getId()
                        + "' onclick='clearValue(\"discount\"," + rq.getBillMaterialService() + "," + bmsi.getId()
                        + "\");' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                        + ((!discount.equals(BigDecimal.ZERO)) ? discount : "") + "</td>\n"
                        + "<td id='availability" + rq.getBillMaterialService() + bmsi.getId()
                        + "' onclick='clearValue(\"availability\"," + rq.getBillMaterialService() + "," + bmsi.getId()
                        + "\");' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                        + ((!availability.equals(0)) ? availability : "") + "</td>\n"
                        + "<td>" + total + "</td>\n"
                        + "</tr>";
            }
        }

        return result;
    }

    private String[] getRequestQuotationByProject(Long pId) {
        String[] result = {"", "", "", "", ""};
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);
        Integer index = 1;

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                if (pd.getType().equals(ProjectTypeEnum.SALE.name())) {
                    result[0] += (!(index++).equals(pds.size()))
                            ? "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n"
                            : "<option value='" + pd.getId() + "' selected>" + pd.getReference() + "</option>\n";
                }
            }

            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pds.get(0).getId());

            if (bms != null) {
                String[] info = getRequestQuotationInfo(bms.getId(), Boolean.FALSE);

                result[1] = info[0];
                result[2] = info[1];
            }

            for (CurrencyEnum currency : CurrencyEnum.values()) {
                result[3] += "<option value='" + currency.getId() + "'>" + currency.toString() + "</option>\n";
            }
        }

        return result;
    }

    private String getCurrencyName(Integer currency) {
        String name = "";

        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.getId().equals(currency)) {
                name = value.getSymbol();
                break;
            }
        }

        return name;
    }

    private String[] getRequestQuotationById(Long rqId) {
        String[] result = {"", "", "", "", "", ""};
        RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);

        if (rq != null) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(rq.getBillMaterialService());

            if (bms != null) {
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                if (pd != null) {
                    String[] info = getRequestQuotationInfo(bms.getId(), Boolean.TRUE);

                    result[0] = pd.getReference();
                    result[1] = rq.getSupplier();
                    result[2] = getCurrencyName(rq.getCurrency());
                    result[3] = info[0];
                    result[4] = info[1];
                    result[5] = rq.getNote();
                }
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

            result[0] = "<tr>\n"
                    + "<td>" + name + "</td>\n"
                    + "<td>" + subproject + "</td>\n</tr>\n";
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

                        result[1]
                                += "<tr>\n"
                                + "<td>" + imno + "</td>\n"
                                + "<td>" + stockName + "</td>\n"
                                + "<td>" + quantity + "</td>\n"
                                + "<td><input type='checkbox' " + ((checked) ? "checked" : "") + " onclick='handleClick(this,"
                                + bmsi.getBillMaterialService() + ","
                                + bmsi.getId() + ");'></td>\n"
                                + "</tr>\n";
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

    @SuppressWarnings("null")
    private void existRequestQuotation(Long pId) {
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);
        if (pds != null && !pds.isEmpty()) {
            List<BillMaterialService> bmss = new ArrayList<>();

            pds.stream().forEach((pd) -> {
                bmss.add(srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId()));
            });
            if (bmss != null && !bmss.isEmpty()) {
                for (BillMaterialService bms : bmss) {
                    if (getRequestQuotation(bms.getId()) == null) {
                        RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                        if (rq != null) {
                            setRequestQuotation(srvProjectManager, bms.getId(), rq);

                            List<RequestQuotationItem> rqis = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());

                            if (rqis != null && !rqis.isEmpty()) {
                                rqis.stream().forEach((rqi) -> {
                                    setRequestQuotationItem(bms.getId(), rqi);
                                });
                            }
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/request-quotation")
    public String RequestQuotation(Project p, String mode, Model model) {
        switch (mode) {
            case "RQ":
                this.setTitle("Request Quotation");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/RequestQuotation.jsp");
                this.setClassContent("content");
                p = srvProjectManager.getDaoProject().getById(p.getId());
                existRequestQuotation(p.getId());
                setHeaderInfo(model);
                model.addAttribute("projectId", p.getId());
                model.addAttribute("projectReference", p.getReference());
                model.addAttribute("buttonSendEmail", "<input type='button' value='Send eMail' class='button' onclick='sendEmail()'>");
                model.addAttribute("buttonSavePDF", "<input type='button' value='Save PDF' class='button' onclick='savePDF()'>");
                model.addAttribute("buttonSaveExcel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS()'>");
                break;
            case "RQS":
                this.setTitle("Request Quotation Supplier");
                this.setHeader("supplier_header.jsp");
                this.setSide_bar(null);
                this.setContent("../project/RequestQuotationSupplier.jsp");
                this.setClassContent("contentNoSideBar");
                setHeaderInfo(model);
                model.addAttribute("requestQuotationId", p.getId());
                model.addAttribute("buttonCancel", "<input type='button' value='Clear All' class='button' onclick='cancel()'>");
                model.addAttribute("buttonRefresh", "<input type='button' value='Calculate' class='button' onclick='getVirtualRequestQuotation()'>");
                model.addAttribute("buttonSendEmail", "<input type='button' value='Submit' class='button' onclick='sendEmailSupplier()'>");
                RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(p.getId());
                if (rq != null) {
                    BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(rq.getBillMaterialService());

                    if (bms != null) {
                        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                        if (pd != null) {
                            p = srvProjectManager.getDaoProject().getById(pd.getProject());

                            model.addAttribute("projectReference", p.getReference());
                        }
                    }
                }
                break;
            case "BMSI": {
                this.setTitle("Preparation Request Quotation");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/RequestQuotationSelectItem.jsp");
                this.setClassContent("content");
                setHeaderInfo(model);
                String[] pbInfo = getBillMaterialServiceByProjectDetailInfo(p.getProjectDetail());
                p = srvProjectManager.getDaoProject().getById(p.getId());
                model.addAttribute("projectId", p.getId());
                model.addAttribute("projectReference", p.getReference());
                model.addAttribute("subproject", pbInfo[0]);
                model.addAttribute("billMaterialService", pbInfo[1]);
                model.addAttribute("billMaterialServiceItems", pbInfo[2]);
                break;
            }
            default: {
                this.setTitle("Request Quotation");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/RequestQuotationSelectItem.jsp");
                this.setClassContent("content");
                setHeaderInfo(model);
                p = srvProjectManager.getDaoProject().getById(p.getId());
                String[] pbInfo = getBillMaterialServiceByProjectInfo(p.getId());
                model.addAttribute("projectId", p.getId());
                model.addAttribute("projectReference", p.getReference());
                model.addAttribute("subproject", pbInfo[0]);
                model.addAttribute("billMaterialService", pbInfo[1]);
                model.addAttribute("billMaterialServiceItems", pbInfo[2]);
                break;
            }
        }

        return "index";
    }

    @RequestMapping(value = "/request-quotation/change-subproject")
    public @ResponseBody
    String changeSubproject(Long pdId) {
        if (pdId != null) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

            if (bms != null) {
                Map<String, String> content = new HashMap<>();
                String[] rqInfo = getRequestQuotationInfo(bms.getId(), Boolean.FALSE);

                content.put("requestQuotation", rqInfo[0]);
                content.put("itemRequestQuotation", rqInfo[1]);

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

    @RequestMapping(value = "/request-quotation/create-all")
    public @ResponseBody
    String createAll(Long pdId, Boolean checked) {
        if (pdId != null && checked != null) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);
            Collection<BillMaterialServiceItem> items = srvProjectManager.getDaoBillMaterialServiceItem().getByBillMaterialService(bms.getId());

            if (checked.equals(Boolean.TRUE)) {
                setRequestQuotation(srvProjectManager, bms.getId(), new RequestQuotation.Builder().setBillMaterialService(bms.getId()).build());
                for (BillMaterialServiceItem item : items) {
                    setRequestQuotationItem(bms.getId(), new RequestQuotationItem.Builder().setBillMaterialServiceItem(item.getId()).build());
                }
            } else {
                removeRequestQuotation(bms.getId());
                for (BillMaterialServiceItem item : items) {
                    removeRequestQuotationItem(bms.getId(), new RequestQuotationItem.Builder().setBillMaterialServiceItem(item.getId()).build());
                }
            }

            String[] pbInfo = getBillMaterialServiceInfo(pdId);

            return pbInfo[1];
        }
        return "#";
    }

    @RequestMapping(value = "/request-quotation/create")
    public @ResponseBody
    String create(Long pdId, Boolean checked, Long bms, Long bmsi) {
        if (pdId != null && checked != null && bms != null && bmsi != null) {
            if (checked.equals(Boolean.TRUE)) {
                setRequestQuotation(srvProjectManager, bms, new RequestQuotation.Builder().setBillMaterialService(bms).build());
                setRequestQuotationItem(bms, new RequestQuotationItem.Builder().setBillMaterialServiceItem(bmsi).build());
            } else {
                removeRequestQuotation(bms);
                removeRequestQuotationItem(bms, new RequestQuotationItem.Builder().setBillMaterialServiceItem(bmsi).build());
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
            content.put("itemRequestQuotation", rqInfo[2]);
            content.put("currency", rqInfo[3]);

            return new Gson().toJson(content);
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/request-quotation/content-supplier")
    public @ResponseBody
    String contentSupplier(Long rqId) {
        if (rqId != null) {
            Map<String, String> content = new HashMap<>();
            String[] rqInfo = getRequestQuotationById(rqId);

            content.put("subproject", rqInfo[0]);
            content.put("supplier", rqInfo[1]);
            content.put("currency", rqInfo[2]);
            content.put("requestQuotation", rqInfo[3]);
            content.put("itemRequestQuotation", rqInfo[4]);
            content.put("note", rqInfo[5]);

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
                removeRequestQuotation(bms.getId());
                RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                if (rq != null) {
                    srvProjectManager.getDaoRequestQuotation().delete(rq);

                    List<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());

                    if (items != null && !items.isEmpty()) {
                        srvProjectManager.getDaoRequestQuotationItem().delete(items);
                    }
                }
            }
        }

        return "index";
    }

    @RequestMapping(value = "/request-quotation/get-virtual-request-quotation")
    public @ResponseBody
    String getVirtualRequestQuotation(Long rqId) {
        if (rqId != null) {
            RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);

            if (rq != null) {
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(rq.getBillMaterialService());

                if (bms != null) {
                    Map<String, String> content = new HashMap<>();

                    content.put("billMaterialService", bms.getId().toString());

                    Collection<RequestQuotationItem> items = getRequestQuotationItems(bms.getId());

                    if (items != null && !items.isEmpty()) {
                        Set<Long> rqis = new HashSet<>();
                        Set<Integer> qty = new HashSet<>();

                        for (RequestQuotationItem item : items) {
                            rqis.add(item.getBillMaterialServiceItem());
                            BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());
                            qty.add(bmsi.getQuantity());
                        }
                        content.put("billMaterialServiceItemIds", new Gson().toJson(rqis.toArray(), Long[].class));
                        content.put("billMaterialServiceItemQuantities", new Gson().toJson(qty.toArray(), Integer[].class));
                    }

                    return new Gson().toJson(content);
                }
            }
        }

        return "";
    }

    @RequestMapping(value = "/request-quotation/refresh")
    public @ResponseBody
    String refresh(Long bms, String delivery, String expenses, String prices, String discounts, String availabilities, String quantities) {
        if (bms != null) {
            RequestQuotation rq = getRequestQuotation(bms);

            if (rq != null) {
                if (!Strings.isNullOrEmpty(delivery)) {
                    rq.setDeliveryCost(Integer.valueOf(delivery));
                }
                if (!Strings.isNullOrEmpty(expenses)) {
                    rq.setOtherExpenses(Integer.valueOf(expenses));
                }
                Collection<RequestQuotationItem> items = getRequestQuotationItems(bms);
                String[] priceValues = prices.split(",");
                String[] discountValues = discounts.split(",");
                String[] availabilityValues = availabilities.split(",");
                String[] quantitiesValues = quantities.split(",");
                Integer index = 0;
                Integer sumPriceItems = 0;

                for (RequestQuotationItem item : items) {
                    Integer totalPrice = Integer.valueOf(priceValues[index]) * Integer.valueOf(quantitiesValues[index]);

                    item.setUnitPrice(Integer.valueOf(priceValues[index]));
                    item.setAvailability(Integer.valueOf(availabilityValues[index]));
                    item.setDiscount(new BigDecimal(discountValues[index]).divide(new BigDecimal(100)));

                    BigDecimal discountPrice = new BigDecimal(totalPrice).multiply(item.getDiscount());

                    item.setDeliveryCost(new BigDecimal(totalPrice).subtract(discountPrice));
                    sumPriceItems += item.getDeliveryCost().intValue();
                    index++;
                }

                rq.setMaterialCost(sumPriceItems);
                rq.setGrandTotal(rq.getDeliveryCost() + rq.getOtherExpenses() + rq.getMaterialCost());
            }

            String[] rqInfo = getRequestQuotationInfo(bms, Boolean.TRUE);
            Map<String, String> content = new HashMap<>();

            content.put("requestQuotation", rqInfo[0]);
            content.put("itemRequestQuotation", rqInfo[1]);

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = "/request-quotation/send-email")
    public @ResponseBody
    @SuppressWarnings("null")
    String sendEmail(Long pdId, String supplier, Integer currency, String note) throws MessagingException {
        String response = "";

        if (pdId != null && currency != null) {
            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

            if (p != null && pd != null && bms != null) {
                RequestQuotation rq = getRequestQuotation(bms.getId());
                Collection<RequestQuotationItem> rqis = getRequestQoutationItems(bms.getId());
                p.setStatus(ProjectStatusEnum.REQUEST_QUOTATION.toString());
                srvProjectManager.getDaoProject().edit(p);
                pd.setStatus(ProjectStatusEnum.REQUEST_QUOTATION.toString());
                srvProjectManager.getDaoProjectDetail().edit(pd);
                rq.setCurrency(currency);
                rq.setComplete(Boolean.FALSE);
                rq.setNote(note);

                rq.setSupplier(supplier);
                rq = srvProjectManager.getDaoRequestQuotation().add(rq);

                if (rq != null && rqis != null && !rqis.isEmpty()) {
                    for (RequestQuotationItem rqi : rqis) {
                        rqi.setRequestQuotation(rq.getId());
                    }
                    rqis = srvProjectManager.getDaoRequestQuotationItem().add(rqis);

                    if (rqis != null && !rqis.isEmpty()) {
                        Company company = srvProjectManager.getDaoCompany().getByTypeName(CompanyTypeEnum.SUPPLIER, rq.getSupplier());

                        if (company != null && !Strings.isNullOrEmpty(company.getEmail1())) {
                            sendMail("smtp.dmail.hol.gr", "gpatitakis@hol.gr", company.getEmail1(), null,
                                    "MARPO GROUP RFQ - REF:" + pd.getReference(),
                                    "http://localhost:8081/ProjectManager/project/request-quotation?id=" + rq.getId() + "&mode=RQS");

                            response = "<h1>Request Quotation - REF:" + pd.getReference() + " - Complete</h1>\n";
                        }
                    }
                }
            } else {
                if (p == null) {
                    logger.log(Level.SEVERE, "no found project:{0}", pdId);
                    response = "no found project";
                }
                if (pd == null) {
                    logger.log(Level.SEVERE, "no found project detail:{0}", pdId);
                    response = "no found project detail";
                }
                if (bms == null) {
                    logger.log(Level.SEVERE, "no found bill of material or services:{0}", pdId);
                    response = "no found bill of material or services";
                }
            }
        } else {
            if (pdId == null) {
                logger.log(Level.SEVERE, "you must to give a project detail id");
                response = "you must to give a project detail id";
            }
            if (currency == null) {
                logger.log(Level.SEVERE, "you must to give a currency");
                response = "you must to give a currency";
            }
        }

        return response;
    }

    @RequestMapping(value = "/request-quotation/get-value")
    public @ResponseBody
    String getValue(String name, Long bms, Long bmsi) {
        if (bmsi.equals(0)) {
            RequestQuotation rq = getRequestQuotation(bms);

            if (rq != null) {
                if (name.equals("delivery")) {
                    return (rq.getDeliveryCost().equals(0)) ? null : rq.getDeliveryCost().toString();
                } else if (name.equals("expenses")) {
                    return (rq.getOtherExpenses().equals(0)) ? null : rq.getOtherExpenses().toString();
                }
            }
        } else {
            RequestQuotationItem rqi = getRequestQoutationItem(bms, bmsi);

            if (rqi != null) {
                if (name.equals("price")) {
                    return (rqi.getUnitPrice().equals(0)) ? null : rqi.getUnitPrice().toString();
                } else if (name.equals("discount")) {
                    return (rqi.getDiscount().equals(0)) ? null : rqi.getDiscount().toString();
                } else if (name.equals("availability")) {
                    return (rqi.getAvailability().equals(0)) ? null : rqi.getAvailability().toString();
                }
            }
        }

        return null;
    }

    @RequestMapping(value = "/request-quotation/change-supplier")
    public @ResponseBody
    String changeSupplier(Long pdId, String supplier) {
        String response = "";
        BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

        if (bms != null) {
            Collection<String> suppliers = addSupplier(bms.getId(), supplier);

            if (suppliers != null) {
                for (String supp : suppliers) {
                    response += "<tr>"
                            + "<td>" + supp + "</td>\n"
                            + "<td><input type='button' value='Remove' class='button' onclick='removeRequestQuotationSupplier(\"" + supp + "\")'></td>\n"
                            + "</tr>\n";
                }
            }
        }

        return response;
    }

    @RequestMapping(value = "/request-quotation/remove-supplier")
    public @ResponseBody
    String removeSupplier(Long pdId, String supplier) {
        String response = "";
        BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

        if (bms != null) {
            Collection<String> suppliers = removeVirtualSupplier(bms.getId(), supplier);

            if (suppliers != null) {
                for (String supp : suppliers) {
                    response += "<tr>"
                            + "<td>" + supp + "</td>\n"
                            + "<td><input type='button' value='Remove' class='button' onclick='removeRequestQuotationSupplier(\"" + supp + "\")'></td>\n"
                            + "</tr>\n";
                }
            }
        }

        return response;
    }
}
