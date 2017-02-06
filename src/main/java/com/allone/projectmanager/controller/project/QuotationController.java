/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.QuotationCommon;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.BillMaterialServiceItem;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Quotation;
import com.allone.projectmanager.entities.QuotationItem;
import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.RequestQuotationItem;
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.LocationEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.QuotationItemModel;
import com.allone.projectmanager.model.User;
import com.allone.projectmanager.tools.DownloadFile;
import com.allone.projectmanager.reports.QuotationReport;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.commons.io.IOUtils;
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
public class QuotationController extends QuotationCommon {

    private static final Logger logger = Logger.getLogger(QuotationController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

//    private String getHtmlRequestQuotation(RequestQuotation item) {
//        return "<tr>\n"
//                + "<td>" + item.getDeliveryCost() + "</td>\n"
//                + "<td>" + item.getOtherExpenses() + "</td>\n"
//                + "<td>" + item.getMaterialCost() + "</td>\n"
//                + "<td>" + item.getGrandTotal() + "</td>\n"
//                + "</tr>\n";
//    }
//
//    private String getHtmlRequestQuotationItems(RequestQuotation rq) {
//        List<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());
//        String html = "";
//
//        if (items != null && !items.isEmpty()) {
//            Integer index = 1;
//
//            for (RequestQuotationItem item : items) {
//                BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());
//
//                if (bmsi != null) {
//                    Item i = srvProjectManager.getDaoItem().getById(bmsi.getItem());
//
//                    if (i != null) {
//                        html += "<tr>\n"
//                                + "<td>" + index++ + "</td>\n"
//                                + "<td>" + i.getImno() + "</td>\n"
//                                + "<td>" + i.getDescription() + "</td>\n"
//                                + "<td>" + bmsi.getQuantity() + "</td>\n"
//                                + "<td>" + item.getUnitPrice() + "</td>\n"
//                                + "<td>" + item.getDiscount() + "</td>\n"
//                                + "<td>" + item.getAvailability() + "</td>\n"
//                                + "<td>" + item.getTotal() + "</td>\n"
//                                + "</tr>\n";
//                    }
//                }
//            }
//        }
//
//        return html;
//    }
//
//    private Boolean moreRequestQuotationByProjectDetail(List<ProjectDetail> pds) {
//        Boolean more = Boolean.FALSE;
//
//        for (ProjectDetail pd : pds) {
//            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId());
//
//            if (bms != null) {
//                List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());
//
//                if (rqs != null && !rqs.isEmpty() && rqs.size() > 1) {
//                    more = Boolean.TRUE;
//                    break;
//                }
//            }
//        }
//
//        return more;
//    }
    private String getHtmlQuotation(String reference, String customer) {
        return "<tr>\n"
                + "<td>" + reference + "-" + customer + "</td>\n"
                + "<td>greece</td>\n"
                + "<td id='grand1'>0.00</td>\n"
                + "<td id='rq-currency1'>EUR</td>\n"
                + "</tr>\n";
    }

    private String getHtmlQuotation() {
        Collection<Quotation> qs = getMapQuotation();

        if (qs != null && !qs.isEmpty()) {
            String html = "";

            return qs.stream().map((q) -> "<tr>\n"
                    + "<td>" + q.getName() + "</td>\n"
                    + "<td>" + getLocationNameById(q.getLocation()) + "</td>\n"
                    + "<td id='grand" + q.getLocation() + "'>" + (q.getGrandTotal() != null ? q.getGrandTotal() : "0.00") + "</td>\n"
                    + "<td id='rq-currency" + q.getLocation() + "'>" + getCurrencyById(q.getCurrency()) + "</td>\n"
                    + "</tr>\n").reduce(html, String::concat);
        }

        return null;
    }

    private String getHtmlQuotationItems(Long pdId) {
        BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

        if (bms != null) {
            List<RequestQuotation> rq = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

            if (rq != null && !rq.isEmpty()) {
                List<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.get(0).getId());

                if (items != null && !items.isEmpty()) {
                    String html = "<tr><td colspan='7'>" + LocationEnum.GREECE.getName().toUpperCase() + "</td></tr>\n";
                    Integer index = 1;

                    for (RequestQuotationItem item : items) {
                        BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());

                        if (bmsi != null) {
                            Item i = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                            if (i != null) {
                                html += "<tr>\n"
                                        + "<td>" + index++ + "</td>\n"
                                        + "<td>" + i.getDescription() + "</td>\n"
                                        + "<td>" + i.getImno() + "</td>\n"
                                        + "<td>" + bmsi.getQuantity() + "</td>\n"
                                        + "<td id='price" + item.getId() + "1' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                                        + "<div contenteditable></div></td>\n"
                                        + "<td id='discount" + item.getId() + "1' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                                        + "<div contenteditable></div></td>\n"
                                        + "<td>0.00</td>\n"
                                        + "</tr>\n";

                                return html;
                            }
                        }
                    }
                }
            }
        }

        return "";
    }

    private String getHtmlQuotationItems() {
        Collection<Quotation> qs = getMapQuotation();

        if (qs != null && !qs.isEmpty()) {
            String html = "";

            return qs.stream().map((q) -> getHtmlQuotationItems(q.getBillMaterialService(), q.getLocation())).reduce(html, String::concat);
        }

        return null;
    }

    private String getHtmlQuotationItems(Long bmsId, Integer location) {
        String html = "<tr><td colspan='7'>" + getLocationNameById(location).toUpperCase() + "</td></tr>\n";
        List<QuotationItem> items = getMapQuotationItem(bmsId, location);

        if (items != null && !items.isEmpty()) {
            Integer index = 0;

            for (QuotationItem item : items) {
                BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());

                if (bmsi != null) {
                    Item i = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                    if (i != null) {
                        html += "<tr>\n"
                                + "<td>" + ++index + "</td>\n"
                                + "<td>" + i.getDescription() + "</td>\n"
                                + "<td>" + i.getImno() + "</td>\n"
                                + "<td>" + bmsi.getQuantity() + "</td>\n"
                                + ((item.getEdit().equals(Boolean.TRUE))
                                ? "<td id='price" + item.getBillMaterialServiceItem() + location
                                + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div></td>\n"
                                : "<td id='price" + item.getBillMaterialServiceItem() + location + "'>" + item.getUnitPrice() + "</td>\n")
                                + ((item.getEdit().equals(Boolean.TRUE))
                                ? "<td id='discount" + item.getBillMaterialServiceItem() + location
                                + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div></td>\n"
                                : "<td id='discount" + item.getBillMaterialServiceItem() + location + "'>" + item.getDiscount() + "</td>\n")
                                + ((item.getEdit().equals(Boolean.TRUE))
                                ? "<td id='net" + item.getBillMaterialServiceItem() + location + "'>0.00</td>\n"
                                : "<td id='net" + item.getBillMaterialServiceItem() + location + "'>" + item.getTotal() + "</td>\n")
                                + "</tr>\n";
                    }
                }
            }
        }

        return html;
    }

//    private String getHtmlRQQuotationItems(Long rqId) {
//        String html = "<tr><td colspan='7'>" + LocationEnum.GREECE.getName().toUpperCase() + "</td></tr>\n";
//        List items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rqId);
//
//        if (items != null && !items.isEmpty()) {
//            Integer index = 1;
//
//            for (RequestQuotationItem item : (List<RequestQuotationItem>) items) {
//                BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());
//
//                if (bmsi != null) {
//                    Item i = srvProjectManager.getDaoItem().getById(bmsi.getItem());
//
//                    if (i != null) {
//                        html += "<tr>\n"
//                                + "<td>" + index++ + "</td>\n"
//                                + "<td>" + i.getDescription() + "</td>\n"
//                                + "<td>" + i.getImno() + "</td>\n"
//                                + "<td>" + bmsi.getQuantity() + "</td>\n"
//                                + "<td id='price" + item.getId() + "1' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
//                                + "<div contenteditable></div></td>\n"
//                                + "<td id='discount" + item.getId() + "1' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
//                                + "<div contenteditable></div></td>\n"
//                                + "<td id='net" + item.getId() + "1'>0.00</td>\n"
//                                + "</tr>\n";
//                    }
//                }
//            }
//        }
//
//        return html;
//    }
    private String editInfo(Long pId, Model model, String mode) {
        String content = "../root/message.jsp";
        Project p = srvProjectManager.getDaoProject().getById(pId);

        if (p == null) {
            model.addAttribute("title", "QUOTATION - SELECT REQUEST FOR QUOTATION - REF:" + p.getReference());
            model.addAttribute("message", "No valid project id:" + pId);

            return content;
        }

        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectIdType(pId, ProjectTypeEnum.SALE.toString());

        if (mode.equals("Q-EDIT")) {
            if (p.getStatus().equals(ProjectStatusEnum.QUOTATION.toString())) {
                p.setStatus(ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString());
                srvProjectManager.getDaoProject().edit(p);
            }
        }
        model.addAttribute("projectReference", p.getReference());
        if (pds != null && !pds.isEmpty()) {
            Contact contact = srvProjectManager.getDaoContact().getById(pds.get(0).getContact());
            String info = "";
            Integer index = 0;

            for (ProjectDetail pd : pds) {
                info += (index.equals(0))
                        ? "<option value='" + pd.getId() + "' selected>" + pd.getReference() + "</option>"
                        : "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>";
                index++;
                if (mode.equals("Q-EDIT")) {
                    if (pd.getStatus().equals(ProjectStatusEnum.QUOTATION.toString())) {
                        pd.setStatus(ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString());
                        srvProjectManager.getDaoProjectDetail().edit(pd);
                    }
                }
            }
            model.addAttribute("type", pds.get(0).getType());
            model.addAttribute("company", pds.get(0).getCompany());
            model.addAttribute("customer", pds.get(0).getCustomer());
            model.addAttribute("vessel", pds.get(0).getVesselName());
            model.addAttribute("contact", (contact != null) ? contact.getName() + " " + contact.getSurname() : "");
            model.addAttribute("subprojects", info);

            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pds.get(0).getId());

            if (bms != null) {
                Quotation firstQ = null;
                List<Quotation> qs = srvProjectManager.getDaoQuotation().getByBillMaterialService(bms.getId());

                if (qs != null && !qs.isEmpty()) {
                    firstQ = qs.get(0);
                    qs.stream().forEach((q) -> {
                        List<QuotationItem> qis = srvProjectManager.getDaoQuotationItem().getByQuotation(q.getId());

                        setMapQuotation(q);
                        if (qis != null && !qis.isEmpty()) {
                            qis.stream().forEach((qi) -> {
                                qi.setEdit(Boolean.FALSE);
                                setMapQuotationItem(q.getBillMaterialService(), q.getLocation(), qi);
                            });
                        }
                    });
                    if (firstQ != null) {
                        model.addAttribute("welcome", firstQ.getWelcome());
                        info = "";
                        for (LocationEnum location : LocationEnum.values()) {
                            info += "<option value='" + location.getId() + "'>" + location.getName() + "</option>";
                        }
                        model.addAttribute("location", info);
                        info = "";
                        for (CurrencyEnum currency : CurrencyEnum.values()) {
                            info += (currency.getId().equals(CurrencyEnum.EUR.getId()))
                                    ? "<option value='" + currency.getId() + "' selected>" + currency.getSymbol() + "</option>"
                                    : "<option value='" + currency.getId() + "'>" + currency.getSymbol() + "</option>";
                        }
                        model.addAttribute("customerReference", firstQ.getCustomerReference());
                        model.addAttribute("currency", info);
                        model.addAttribute("quotation", getHtmlQuotation());
                        model.addAttribute("quotationItem", getHtmlQuotationItems());
                        model.addAttribute("remark", firstQ.getRemark());
                        model.addAttribute("availability", firstQ.getAvailability());
                        model.addAttribute("delivery", firstQ.getDelivery());
                        model.addAttribute("packing", firstQ.getPacking());
                        model.addAttribute("payment", firstQ.getPayment());
                        model.addAttribute("validity", firstQ.getValidity());
                        model.addAttribute("note", firstQ.getNote());
                        model.addAttribute("billMaterialServiceId", bms.getId());
                        content = "../project/Quotation.jsp";
                    }
                }
            }
        }

        return content;
    }

    private String setQuotation(Long pId, ProjectDetail pd, long bmsId, Model model) {
        Contact contact = srvProjectManager.getDaoContact().getById(pd.getContact());
        String info = "";

        setVirualQuotation(pId);
        model.addAttribute("type", pd.getType());
        model.addAttribute("company", pd.getCompany());
        model.addAttribute("customer", pd.getCustomer());
        model.addAttribute("vessel", pd.getVesselName());
        model.addAttribute("contact", (contact != null) ? contact.getName() + " " + contact.getSurname() : "");

        for (LocationEnum location : LocationEnum.values()) {
            info += "<option value='" + location.getId() + "'>" + location.getName() + "</option>";
        }
        model.addAttribute("location", info);
        info = "";
        for (CurrencyEnum currency : CurrencyEnum.values()) {
            info += (currency.getId().equals(CurrencyEnum.EUR.getId()))
                    ? "<option value='" + currency.getId() + "' selected>" + currency.getSymbol() + "</option>"
                    : "<option value='" + currency.getId() + "'>" + currency.getSymbol() + "</option>";
        }
        model.addAttribute("currency", info);
        model.addAttribute("quotation", getHtmlQuotation());
        model.addAttribute("quotationItem", getHtmlQuotationItems());
        model.addAttribute("billMaterialServiceId", bmsId);

        return "../project/Quotation.jsp";
    }

    private String selectInfo(Long pId, Model model) {
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectIdType(pId, ProjectTypeEnum.SALE.name());
        String content = "../root/message.jsp";

        if (pds != null && !pds.isEmpty()) {
            String info = "";
            Integer index = 0;

            for (ProjectDetail pd : pds) {
                info += (index.equals(0))
                        ? "<option value='" + pd.getId() + "' selected>" + pd.getReference() + "</option>"
                        : "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>";
                index++;
            }
            model.addAttribute("subprojects", info);

            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pds.get(0).getId());

            if (bms != null) {
                content = setQuotation(pId, pds.get(0), bms.getId(), model);
            } else {
                model.addAttribute("message", "No found Bill of Materials or Services");
            }
        } else {
            model.addAttribute("message", "No found Project");
        }

        return content;
    }

    private void selectInfo(ProjectDetail pd, String mode, Map<String, String> content) {
        if (pd != null) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId());

            if (bms != null) {
                String info = "";
                Contact contact = srvProjectManager.getDaoContact().getById(pd.getContact());

                content.put("type", pd.getType());
                content.put("company", pd.getCompany());
                content.put("customer", pd.getCustomer());
                content.put("vessel", pd.getVesselName());
                content.put("contact", (contact != null) ? contact.getName() + " " + contact.getSurname() : "");
                info = "";
                for (LocationEnum location : LocationEnum.values()) {
                    info += "<option value='" + location.getId() + "'>" + location.getName() + "</option>";
                }
                content.put("location", info);
                info = "";
                for (CurrencyEnum currency : CurrencyEnum.values()) {
                    info += (currency.getId().equals(CurrencyEnum.EUR.getId()))
                            ? "<option value='" + currency.getId() + "' selected>" + currency.getSymbol() + "</option>"
                            : "<option value='" + currency.getId() + "'>" + currency.getSymbol() + "</option>";
                }
                content.put("currency", info);
                content.put("quotation", getHtmlQuotation(pd.getReference(), pd.getCustomer()));
                content.put("quotationItem", getHtmlQuotationItems(pd.getId()));
                content.put("billMaterialServiceId", bms.getId().toString());
            }
        }
    }

    private void setVirualQuotation(Long pId) {
        List<ProjectDetail> items = srvProjectManager.getDaoProjectDetail().getByProjectIdType(pId, ProjectTypeEnum.SALE.name());

        if (items != null && !items.isEmpty()) {
            for (ProjectDetail item : items) {
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(item.getId());

                if (bms != null) {
                    setMapQuotation(new Quotation.Builder()
                            .setCurrency(CurrencyEnum.EUR.getId())
                            .setCustomer(item.getCustomer())
                            .setLocation(LocationEnum.GREECE.getId())
                            .setName(item.getReference() + "-" + item.getCustomer())
                            .setBillMaterialService(bms.getId())
                            .build());

                    List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoBillMaterialServiceItem().getByBillMaterialService(bms.getId());

                    if (bmsis != null && !bmsis.isEmpty()) {
                        bmsis.stream().forEach((bmsi) -> {
                            setMapQuotationItem(bms.getId(), LocationEnum.GREECE.getId(), new QuotationItem.Builder()
                                    .setBillMaterialServiceItem(bmsi.getId())
                                    .setEdit(Boolean.TRUE)
                                    .build());
                        });
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/quotation")
    public String Quotation(HttpServletRequest request, Project p, String mode, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();
            User user = getUser(session.getId());
            Date expired = new Date(new Date().getTime() + user.getProject_expired() * 86400000l);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");

            this.setTitle("Quotation");
            this.setHeader("header.jsp");
            this.setSide_bar("../project/sidebar.jsp");
            if (!Strings.isNullOrEmpty(mode)) {
                if (mode.equals("NEW")) {
                    resetCommonValues();
                    p = srvProjectManager.getDaoProject().getById(p.getId());
                    if (p != null) {
                        model.addAttribute("projectReference", p.getReference());
                        model.addAttribute("is_quotation", 1);
                        model.addAttribute("display", "display: none;");
                        model.addAttribute("expired", format.format(expired));
                        this.setContent(selectInfo(p.getId(), model));
                        this.setClassContent("content");
                        setHeaderInfo(session, model);
                    }
//                } else if (mode.equals("NEW_RQ")) {
//                    this.setContent(selectInfo(model));
//                    this.setClassContent("content");
//                    setHeaderInfo(session, model);
//                    setVirualQuotation();
                } else if (mode.equals("Q-EDIT") || mode.equals("EDIT-PROJECT")) {
                    resetCommonValues();
                    model.addAttribute("display", "display: inline;");
                    this.setContent(editInfo(p.getId(), model, mode));
                    this.setClassContent("content");
                    setHeaderInfo(session, model);
                }
            }

            return "index";
        }

        return null;
    }

    @RequestMapping(value = "/quotation/change-quota-suproject")
    public @ResponseBody
    String changeQuotaSubproject(HttpServletRequest request, ProjectDetail pd, String mode) {
        if (request != null) {
            pd = srvProjectManager.getDaoProjectDetail().getById(pd.getId());
            if (pd != null && !Strings.isNullOrEmpty(mode)) {
                Map<String, String> content = new HashMap();

                selectInfo(pd, mode, content);

                return new Gson().toJson(content);
            }
        }

        return "";
    }

//    @RequestMapping(value = "/quotation/rq-submit")
//    public @ResponseBody
//    String RFQSubmit(HttpServletRequest request, String infoRQ) {
//        if (!Strings.isNullOrEmpty(infoRQ)) {
//            setLstRQInfo(infoRQ, srvProjectManager);
//        }
//
//        return null;
//    }
    @RequestMapping(value = "/quotation/get-item")
    public @ResponseBody
    String getItem(HttpServletRequest request, Long bmsId, Integer location) {
        if (bmsId != null && location != null) {
            List<QuotationItem> items = getMapQuotationItem(bmsId, location);
            Map<String, String> content = new HashMap<>();

            content.put("billMaterialService", bmsId.toString());
            content.put("location", location.toString());
            if (items != null && !items.isEmpty()) {
                Set<QuotationItemModel> qis = new HashSet<>();

                items.stream().forEach((item) -> {
                    BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());

                    if (bmsi != null) {
                        qis.add(new QuotationItemModel(item.getBillMaterialServiceItem(), bmsi.getQuantity()));
                    }
                });

                content.put("billMaterialServiceItem", new Gson().toJson(qis));

                return new Gson().toJson(content);
            }
        }

        return null;
    }

    @RequestMapping(value = "/quotation/calculate")
    public @ResponseBody
    String
            getCalculate(HttpServletRequest request, Long id, Integer location, String itemInfo) {
        if (id != null && location != null && !Strings.isNullOrEmpty(itemInfo)) {
            QuotationItemModel[] items = new Gson().fromJson(itemInfo, QuotationItemModel[].class
            );

            if (items != null && items.length > 0) {
                Map<String, String> content = new HashMap<>();
                BigDecimal sumPriceItems = BigDecimal.ZERO;

                for (QuotationItemModel item : items) {
                    BigDecimal totalPrice = item.price.multiply(new BigDecimal(item.qty));
                    BigDecimal discountPrice = totalPrice.multiply(item.discount).divide(new BigDecimal(100));
                    BigDecimal netPrice = totalPrice.subtract(discountPrice);
                    sumPriceItems = sumPriceItems.add(netPrice);

                    editMapQuotationItem(id, location, new QuotationItem.Builder()
                            .setBillMaterialServiceItem(item.getId())
                            .setUnitPrice(item.price.setScale(2, RoundingMode.CEILING))
                            .setDiscount(item.discount.setScale(1, RoundingMode.CEILING))
                            .setTotal(netPrice.setScale(2, RoundingMode.CEILING))
                            .setEdit(Boolean.FALSE)
                            .build());
                }
                editMapQuotation(id, location, new Quotation.Builder()
                        .setGrandTotal(sumPriceItems.setScale(2, RoundingMode.CEILING))
                        .build());
                content.put("quotation", getHtmlQuotation());
                content.put("quotationItem", getHtmlQuotationItems());

                return new Gson().toJson(content);
            }
        }

        return null;
    }

    @RequestMapping(value = "/quotation/change-currency")
    public @ResponseBody
    String changeCurrency(HttpServletRequest request, Long bmsId, Integer location, Integer currency) {
        editMapQuotation(bmsId, location, new Quotation.Builder().setCurrency(currency).build());

        return (currency != null) ? getCurrencyById(currency) : null;
    }

    @RequestMapping(value = "/quotation/add-item")
    public @ResponseBody
    String addItem(Long bmsId, Integer location, Integer currency) {
        if (bmsId != null && location != null && currency != null) {
            Map<String, String> content = new HashMap<>();
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(bmsId);

            if (getMapQuotation(bmsId, location) != null) {
                content.put("status", "exist");
                content.put("message", "Exist Quotation for BMS=" + bms.getName() + " and Location=" + getLocationNameById(location));

                return new Gson().toJson(content);
            } else if (bms != null) {
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                if (pd != null) {
                    List<BillMaterialServiceItem> items = srvProjectManager.getDaoBillMaterialServiceItem().getByBillMaterialService(bmsId);

                    setMapQuotation(new Quotation.Builder()
                            .setCurrency(currency)
                            .setCustomer(pd.getCustomer())
                            .setName(pd.getReference() + "-" + pd.getCustomer())
                            .setBillMaterialService(bmsId)
                            .setLocation(location)
                            .build());
                    if (items != null && !items.isEmpty()) {
                        items.stream().forEach((item) -> {
                            setMapQuotationItem(bms.getId(), location, new QuotationItem.Builder()
                                    .setBillMaterialServiceItem(item.getId())
                                    .setEdit(Boolean.TRUE)
                                    .build());
                        });
                    }
                    content.put("status", "created");
                    content.put("quotation", getHtmlQuotation());
                    content.put("quotationItem", getHtmlQuotationItems());

                    return new Gson().toJson(content);
                }
            }
        }

        return null;
    }

    @RequestMapping(value = "/quotation/clear")
    public @ResponseBody
    String addItem(Long bmsId, Integer location) {
        List<QuotationItem> items = getMapQuotationItem(bmsId, location);

        if (items != null && !items.isEmpty()) {
            items.stream().forEach((item) -> {
                item.setEdit(Boolean.FALSE);
            });
        }

        return null;
    }

    @RequestMapping(value = "/quotation/save")
    public void save(Quotation _q, String dateExpired, final HttpServletRequest request, final HttpServletResponse response) {
        if (_q != null) {
            Collection<Quotation> qs = getMapQuotation();

            if (qs != null && !qs.isEmpty()) {
                qs.stream()
                        .map((q) -> {
                            q.setCreated(new Date());
                            return q;
                        })
                        .map((q) -> {
                            q.setComplete(Boolean.TRUE);
                            return q;
                        })
                        .map((q) -> {
                            q.setDiscard(Boolean.FALSE);
                            return q;
                        })
                        .map((q) -> {
                            q.setCustomerReference(_q.getCustomerReference());
                            return q;
                        })
                        .map((q) -> {
                            q.setAvailability(_q.getAvailability());
                            return q;
                        })
                        .map((q) -> {
                            q.setDelivery(_q.getDelivery());
                            return q;
                        })
                        .map((q) -> {
                            q.setPacking(_q.getPacking());
                            return q;
                        })
                        .map((q) -> {
                            q.setPayment(_q.getPayment());
                            return q;
                        })
                        .map((q) -> {
                            q.setValidity(_q.getValidity());
                            return q;
                        })
                        .map((q) -> {
                            q.setWelcome(_q.getWelcome());
                            return q;
                        })
                        .map((q) -> {
                            q.setRemark(_q.getRemark());
                            return q;
                        })
                        .map((q) -> {
                            q.setNote(_q.getNote());
                            return q;
                        })
                        .map((q) -> {
                            if (q.getId() == null) {
                                srvProjectManager.getDaoQuotation().add(q);
                            } else {
                                srvProjectManager.getDaoQuotation().edit(q);
                            }

                            return q;
                        })
                        .filter((q) -> (q != null)).forEach((q) -> {
                    List<QuotationItem> qis = getMapQuotationItem(q.getBillMaterialService(), q.getLocation());

                    if (qis != null && !qis.isEmpty()) {
                        qis.stream().forEach((qi) -> {
                            if (qi.getId() == null) {
                                qi.setQuotation(q.getId());
                                srvProjectManager.getDaoQuotationItem().add(qi);
                            } else {
                                srvProjectManager.getDaoQuotationItem().edit(qi);
                            }
                        });
                    }
                });

                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(qs.iterator().next().getBillMaterialService());

                if (bms != null) {
                    ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                    if (pd != null) {
                        Project p = srvProjectManager.getDaoProject().getById(pd.getProject());
                        Contact cont = srvProjectManager.getDaoContact().getById(pd.getContact());

                        pd.setStatus(ProjectStatusEnum.QUOTATION.toString());
                        if (!Strings.isNullOrEmpty(dateExpired)) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date parsedDate = formatter.parse(dateExpired);
                                pd.setExpired(parsedDate);
                            } catch (ParseException ex) {
                                logger.log(Level.SEVERE, null, ex);
                            }
                        }
                        srvProjectManager.getDaoProjectDetail().edit(pd);

                        if (p != null) {
                            Map<String, String> titleLeft = new LinkedHashMap<>();
                            Map<String, String> titleRigth = new LinkedHashMap<>();
                            Map<String, String> additional = new LinkedHashMap<>();
                            Map<String, String> comments = new LinkedHashMap<>();
                            Set<Map<String, Set<Map<String, String>>>> offers = new LinkedHashSet<>();
                            Map<String, Map<String, String>> info = new LinkedHashMap<>();
                            SimpleDateFormat ds = new SimpleDateFormat("dd-MM-yyyy");

                            titleLeft.put("To", pd.getCustomer());
                            titleLeft.put("Tel", (cont != null) ? cont.getPhone() : "");
                            titleLeft.put("Attn", (cont != null) ? cont.getSurname() + " " + cont.getName() : "");
                            titleLeft.put("Re", pd.getVesselName());
                            titleRigth.put("Our Ref", pd.getReference());
                            titleRigth.put("Date", ds.format(new Date()));
                            titleRigth.put("Your Ref", _q.getCustomerReference());
                            additional.put("Availability", _q.getAvailability());
                            additional.put("Delivery", _q.getDelivery());
                            additional.put("Packing", _q.getPacking());
                            additional.put("Payment", _q.getPayment());
                            additional.put("Validity", _q.getValidity());
                            comments.put("welcome", _q.getWelcome());
                            comments.put("remarks", _q.getRemark());
                            comments.put("notes", _q.getNote());
                            for (Quotation q : qs) {
                                List<QuotationItem> qis = getMapQuotationItem(q.getBillMaterialService(), q.getLocation());
                                String location = "EX WORKS "
                                        + getLocationNameById(q.getLocation())
                                        + "("
                                        + getCurrencyById(q.getCurrency())
                                        + ")"
                                        + " - Total: " + q.getGrandTotal();

                                if (qis != null && !qis.isEmpty()) {
                                    Set<Map<String, String>> values = new LinkedHashSet<>();

                                    for (QuotationItem qi : qis) {
                                        Map<String, String> value = new LinkedHashMap<>();
                                        BillMaterialServiceItem bmsi
                                                = srvProjectManager.getDaoBillMaterialServiceItem().getById(qi.getBillMaterialServiceItem());

                                        if (bmsi != null) {
                                            Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                                            if (item != null) {
                                                value.put("description", item.getDescription());
                                                value.put("type", item.getImno());
                                            }
                                            value.put("quantity", bmsi.getQuantity().toString());
                                        }
                                        value.put("price", qi.getUnitPrice().toString());
                                        value.put("discount", qi.getDiscount().toString());
                                        value.put("total", qi.getTotal().toString());
                                        values.add(value);
                                    }
                                    offers.add(
                                            new LinkedHashMap<String, Set<Map<String, String>>>() {
                                        {
                                            put(location, values);
                                        }
                                    });
                                }
                            }
                            info.put("titleLeft", titleLeft);
                            info.put("titleRight", titleRigth);
                            info.put("additional", additional);
                            info.put("comments", comments);
                            p.setStatus(ProjectStatusEnum.QUOTATION.toString());
                            srvProjectManager.getDaoProject().edit(p);
                            resetCommonValues();
                            QuotationReport rq = new QuotationReport(info, offers);
                            File file = new File("c:\\ProjectManager\\" + rq.getPdfFile());

                            try {
                                if (request != null) {
                                    HttpSession session = request.getSession();

                                    if (session != null) {
                                        InputStream in = new FileInputStream(file);
                                        OutputStream output = response.getOutputStream();

                                        response.reset();
                                        response.setContentType("application/octet-stream");
                                        response.setContentLength((int) (file.length()));
                                        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
                                        IOUtils.copyLarge(in, output);
                                        output.flush();
                                        output.close();
                                    }
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(QuotationController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(QuotationController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/quotation/complete")
    public @ResponseBody
    String complete(Long bmsId, Integer location
    ) {
        if (bmsId != null && location != null) {
            Quotation q = getMapQuotation(bmsId, location);

            if (q != null) {
                q.setComplete(Boolean.TRUE);
                q.setDiscard(Boolean.FALSE);

                srvProjectManager.getDaoQuotation().edit(q);

                return "<h1>Quotation:" + q.getName() + " - Complete</h1>\n";
            }
        }

        return null;
    }
}
