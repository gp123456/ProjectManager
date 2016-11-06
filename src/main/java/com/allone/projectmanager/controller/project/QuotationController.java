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
import com.allone.projectmanager.tools.JasperHelper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
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

    private static final Logger logger = Logger.getLogger(RequestQuotationController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    private String getHtmlRequestQuotation(RequestQuotation item) {
        return "<tr>\n"
                + "<td>" + item.getDeliveryCost() + "</td>\n"
                + "<td>" + item.getOtherExpenses() + "</td>\n"
                + "<td>" + item.getMaterialCost() + "</td>\n"
                + "<td>" + item.getGrandTotal() + "</td>\n"
                + "</tr>\n";
    }

    private String getHtmlRequestQuotationItems(RequestQuotation rq) {
        List<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());
        String html = "";

        if (items != null && !items.isEmpty()) {
            Integer index = 1;

            for (RequestQuotationItem item : items) {
                BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());

                if (bmsi != null) {
                    Item i = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                    if (i != null) {
                        html += "<tr>\n"
                                + "<td>" + index++ + "</td>\n"
                                + "<td>" + i.getImno() + "</td>\n"
                                + "<td>" + i.getDescription() + "</td>\n"
                                + "<td>" + bmsi.getQuantity() + "</td>\n"
                                + "<td>" + item.getUnitPrice() + "</td>\n"
                                + "<td>" + item.getDiscount() + "</td>\n"
                                + "<td>" + item.getAvailability() + "</td>\n"
                                + "<td>" + item.getTotal() + "</td>\n"
                                + "</tr>\n";
                    }
                }
            }
        }

        return html;
    }

    private Boolean moreRequestQuotationByProjectDetail(List<ProjectDetail> pds) {
        Boolean more = Boolean.FALSE;

        for (ProjectDetail pd : pds) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId());

            if (bms != null) {
                List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                if (rqs != null && !rqs.isEmpty() && rqs.size() > 1) {
                    more = Boolean.TRUE;
                    break;
                }
            }
        }

        return more;
    }

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

            return qs.stream().map((q) -> getHtmlQuotationItems(q.getRequestQuotation(), q.getLocation())).reduce(html, String::concat);
        }

        return null;
    }

    private String getHtmlQuotationItems(Long rqId, Integer location) {
        String html = "<tr><td colspan='7'>" + getLocationNameById(location).toUpperCase() + "</td></tr>\n";
        List<QuotationItem> items = getMapQuotationItem(rqId, location);

        if (items != null && !items.isEmpty()) {
            Integer index = 0;

            for (QuotationItem item : items) {
                RequestQuotationItem rqi = srvProjectManager.getDaoRequestQuotationItem().getById(item.getRequestQuotationItem());

                if (rqi != null) {
                    BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(rqi.getBillMaterialServiceItem());

                    if (bmsi != null) {
                        Item i = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                        if (i != null) {
                            html += "<tr>\n"
                                    + "<td>" + ++index + "</td>\n"
                                    + "<td>" + i.getDescription() + "</td>\n"
                                    + "<td>" + i.getImno() + "</td>\n"
                                    + "<td>" + bmsi.getQuantity() + "</td>\n"
                                    + ((item.getEdit().equals(Boolean.TRUE))
                                    ? "<td id='price" + item.getRequestQuotationItem() + location
                                    + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div></td>\n"
                                    : "<td id='price" + item.getRequestQuotationItem() + location + "'>" + item.getUnitPrice() + "</td>\n")
                                    + ((item.getEdit().equals(Boolean.TRUE))
                                    ? "<td id='discount" + item.getRequestQuotationItem() + location
                                    + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div></td>\n"
                                    : "<td id='discount" + item.getRequestQuotationItem() + location + "'>" + item.getDiscount() + "</td>\n")
                                    + ((item.getEdit().equals(Boolean.TRUE))
                                    ? "<td id='net" + item.getRequestQuotationItem() + location + "'>0.00</td>\n"
                                    : "<td id='net" + item.getRequestQuotationItem() + location + "'>" + item.getTotal() + "</td>\n")
                                    + "</tr>\n";
                        }
                    }
                }
            }
        }

        return html;
    }

    private String getHtmlRQQuotationItems(Long rqId) {
        String html = "<tr><td colspan='7'>" + LocationEnum.GREECE.getName().toUpperCase() + "</td></tr>\n";
        List items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rqId);

        if (items != null && !items.isEmpty()) {
            Integer index = 1;

            for (RequestQuotationItem item : (List<RequestQuotationItem>) items) {
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
                                + "<td id='net" + item.getId() + "1'>0.00</td>\n"
                                + "</tr>\n";
                    }
                }
            }
        }

        return html;
    }

    private String editInfo(Long pId, Model model) {
        String content = "../root/message.jsp";
        Project p = srvProjectManager.getDaoProject().getById(pId);

        if (p == null) {
            model.addAttribute("message", "No valid project id:" + pId);

            return content;
        }

        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectIdType(pId, ProjectTypeEnum.SALE.name());

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
            }
            model.addAttribute("type", pds.get(0).getType());
            model.addAttribute("company", pds.get(0).getCompany());
            model.addAttribute("customer", pds.get(0).getCustomer());
            model.addAttribute("vessel", pds.get(0).getVesselName());
            model.addAttribute("contact", contact.getName() + " " + contact.getSurname());
            model.addAttribute("subprojects", info);

            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pds.get(0).getId());

            if (bms != null) {
                List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                if (rqs != null && !rqs.isEmpty()) {
                    Quotation firstQ = null;

                    for (RequestQuotation rq : rqs) {
                        List<Quotation> qs = srvProjectManager.getDaoQuotation().getByRequestForQuotation(rq.getId());

                        if (qs != null && !qs.isEmpty()) {
                            firstQ = qs.get(0);
                            qs.stream().forEach((q) -> {
                                List<QuotationItem> qis = srvProjectManager.getDaoQuotationItem().getByQuotation(q.getId());

                                setMapQuotation(q);
                                if (qis != null && !qis.isEmpty()) {
                                    qis.stream().forEach((qi) -> {
                                        qi.setEdit(Boolean.FALSE);
                                        setMapQuotationItem(q.getRequestQuotation(), q.getLocation(), qi);
                                    });
                                }
                            });
                        }
                    }
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
                        model.addAttribute("requestQuotationId", rqs.get(0).getId());
                        content = "../project/Quotation.jsp";
                    }
                }
            }
        }

        return content;
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
                List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                if (rqs != null && !rqs.isEmpty()) {
                    Boolean more = moreRequestQuotationByProjectDetail(pds);

                    if (more.equals(Boolean.TRUE)) {
                        info = "";
                        index = 0;

                        for (RequestQuotation rq : rqs) {
                            info += (index.equals(0))
                                    ? "<option value='" + rq.getId() + "' selected>" + rq.getName() + "</option>"
                                    : "<option value='" + rq.getId() + "'>" + rq.getName() + "</option>";
                            index++;
                        }
                        model.addAttribute("requestQuotations", info);
                        model.addAttribute("supplier", rqs.get(0).getSupplier());
                        model.addAttribute("currency", getCurrencyById(rqs.get(0).getCurrency()));
                        model.addAttribute("requestQuotation", getHtmlRequestQuotation(rqs.get(0)));
                        model.addAttribute("itemRequestQuotation", getHtmlRequestQuotationItems(rqs.get(0)));
                        model.addAttribute("noteRequestQuotation", rqs.get(0).getNote());
                        model.addAttribute("noteSupplierRequestQuotation", rqs.get(0).getSupplierNote());
                        content = "../project/QuotationSelectRQ.jsp";
                    } else {
                        Contact contact = srvProjectManager.getDaoContact().getById(pds.get(0).getContact());

                        setVirualQuotation(pId);
                        model.addAttribute("type", pds.get(0).getType());
                        model.addAttribute("company", pds.get(0).getCompany());
                        model.addAttribute("customer", pds.get(0).getCustomer());
                        model.addAttribute("vessel", pds.get(0).getVesselName());
                        model.addAttribute("contact", contact.getName() + " " + contact.getSurname());
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
                        model.addAttribute("currency", info);
                        model.addAttribute("quotation", getHtmlQuotation());
                        model.addAttribute("quotationItem", getHtmlQuotationItems());
                        model.addAttribute("requestQuotationId", rqs.get(0).getId());
                        content = "../project/Quotation.jsp";
                    }
                } else {
                    model.addAttribute("message", "No found valid Request for Quotation");
                }
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
                List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                if (rqs != null && !rqs.isEmpty()) {
                    String info = "";
                    Integer index = 0;

                    if (mode.equals("rq")) {
                        for (RequestQuotation rq : rqs) {
                            info += (index.equals(0))
                                    ? "<option value='" + rq.getId() + "' selected>" + rq.getName() + "</option>"
                                    : "<option value='" + rq.getId() + "'>" + rq.getName() + "</option>";
                            index++;
                        }
                        content.put("requestQuotations", info);
                        content.put("supplier", rqs.get(0).getSupplier());
                        content.put("currency", getCurrencyById(rqs.get(0).getCurrency()));
                        content.put("requestQuotation", getHtmlRequestQuotation(rqs.get(0)));
                        content.put("itemRequestQuotation", getHtmlRequestQuotationItems(rqs.get(0)));
                        content.put("noteRequestQuotation", rqs.get(0).getNote());
                        content.put("noteSupplierRequestQuotation", rqs.get(0).getSupplierNote());
                        content.put("requestQuotationId", rqs.get(0).getId().toString());
                    } else if (mode.equals("q")) {
                        Contact contact = srvProjectManager.getDaoContact().getById(pd.getContact());

                        content.put("type", pd.getType());
                        content.put("company", pd.getCompany());
                        content.put("customer", pd.getCustomer());
                        content.put("vessel", pd.getVesselName());
                        content.put("contact", contact.getName() + " " + contact.getSurname());
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
                        content.put("requestQuotationId", rqs.get(0).getId().toString());
                    }
                }
            }
        }
    }

    private List getProjectDetailsByRQIds() {
        List<ProjectDetail> pds = new ArrayList<>();
        List<Long> pdIds = getPDIds();

        if (!pdIds.isEmpty()) {
            pdIds.stream().map((id) -> srvProjectManager.getDaoProjectDetail().getById(id)).filter((pd) -> (pd != null)).forEach((ProjectDetail pd) -> {
                pds.add(pd);
            });
        }

        return pds;
    }

    private List getRequestQuotations() {
        List<RequestQuotation> rqs = new ArrayList<>();
        List<Long> rqIds = getRQIds();

        if (!rqIds.isEmpty()) {
            rqIds.stream().map((id) -> srvProjectManager.getDaoRequestQuotation().getById(id)).filter((rq) -> (rq != null))
                    .forEach((RequestQuotation rq) -> {
                        rqs.add(rq);
                    });
        }

        return rqs;
    }

    private String selectInfo(Model model) {
        Long rqId = getRQId();
        String content = "../root/message.jsp";

        if (rqId != null) {
            String info = "";
            Integer index = 0;

            RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);
            List<ProjectDetail> pds = getProjectDetailsByRQIds();

            if (!pds.isEmpty()) {
                for (ProjectDetail pd : pds) {
                    info += (index.equals(0))
                            ? "<option value='" + pd.getId() + "' selected>" + pd.getReference() + "</option>"
                            : "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>";
                    index++;
                }

                model.addAttribute("subprojects", info);
            }

            if (rq != null) {
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(rq.getBillMaterialService());

                if (bms != null) {
                    ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                    if (pd != null) {
                        Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

                        if (p != null) {
                            Contact contact = srvProjectManager.getDaoContact().getById(pd.getContact());

                            model.addAttribute("requestQuotationId", rq.getId());
                            model.addAttribute("projectReference", p.getReference());
                            model.addAttribute("is_quotation", 1);
                            model.addAttribute("type", pd.getType());
                            model.addAttribute("company", pd.getCompany());
                            model.addAttribute("customer", pd.getCustomer());
                            model.addAttribute("vessel", pd.getVesselName());
                            model.addAttribute("contact", contact.getName() + " " + contact.getSurname());
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
                            model.addAttribute("currency", info);
                            model.addAttribute("quotation", getHtmlQuotation(pd.getReference(), pd.getCustomer()));
                            model.addAttribute("quotationItem", getHtmlRQQuotationItems(rq.getId()));
                            content = "../project/Quotation.jsp";
                        } else {
                            model.addAttribute("message", "No found valid Project for id=" + pd.getProject());
                        }
                    } else {
                        model.addAttribute("message", "No found valid Project Detail for id=" + bms.getProject());
                    }
                } else {
                    model.addAttribute("message", "No found valid Bill of Material for id=" + rq.getBillMaterialService());
                }
            } else {
                model.addAttribute("message", "No found valid Request for Quotation for id=" + rqId);
            }
        } else {
            model.addAttribute("message", "No set map Request for Quotation");
        }

        return content;
    }

    private void setVirualQuotation(Long pId) {
        List<ProjectDetail> items = (pId != null)
                ? srvProjectManager.getDaoProjectDetail().getByProjectIdType(pId, ProjectTypeEnum.SALE.name()) : getProjectDetailsByRQIds();

        if (items != null && !items.isEmpty()) {
            for (ProjectDetail item : items) {
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(item.getId());

                if (bms != null) {
                    List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                    if (rqs != null && !rqs.isEmpty()) {
                        rqs.stream().map((rq) -> {
                            setMapQuotation(new Quotation.Builder()
                                    .setCurrency(CurrencyEnum.EUR.getId())
                                    .setCustomer(item.getCustomer())
                                    .setLocation(LocationEnum.GREECE.getId())
                                    .setName(item.getReference() + "-" + item.getCustomer())
                                    .setRequestQuotation(rq.getId())
                                    .build());
                            return rq;
                        }).forEach((rq) -> {
                            List<RequestQuotationItem> rqis = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());
                            if (rqis != null && !rqis.isEmpty()) {
                                rqis.stream().forEach((rqi) -> {
                                    setMapQuotationItem(rq.getId(), LocationEnum.GREECE.getId(), new QuotationItem.Builder()
                                            .setRequestQuotationItem(rqi.getId())
                                            .setEdit(Boolean.TRUE)
                                            .build());
                                });
                            }
                        });
                    }
                }
            }
        }
    }

    private void setVirualQuotation() {
        List<RequestQuotation> items = getRequestQuotations();

        if (items != null && !items.isEmpty()) {
            items.stream().map((item) -> {
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(item.getBillMaterialService());

                if (bms != null) {
                    ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                    if (pd != null) {
                        setMapQuotation(new Quotation.Builder()
                                .setCurrency(CurrencyEnum.EUR.getId())
                                .setCustomer(pd.getCustomer())
                                .setLocation(LocationEnum.GREECE.getId())
                                .setName(pd.getReference() + "-" + pd.getCustomer())
                                .setRequestQuotation(item.getId())
                                .build());

                        return item;
                    }
                }

                return null;
            }).forEach((item) -> {
                if (item != null) {
                    List<RequestQuotationItem> rqis = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(item.getId());

                    if (rqis != null && !rqis.isEmpty()) {
                        rqis.stream().forEach((rqi) -> {
                            setMapQuotationItem(item.getId(), LocationEnum.GREECE.getId(), new QuotationItem.Builder()
                                    .setRequestQuotationItem(rqi.getId())
                                    .setEdit(Boolean.TRUE)
                                    .build());
                        });
                    }
                }
            });
        }
    }

    @RequestMapping(value = "/quotation")
    public String Quotation(HttpServletRequest request, Project p, String mode, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

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
                        this.setContent(selectInfo(p.getId(), model));
                        this.setClassContent("content");
                        setHeaderInfo(session, model);
                    }
                } else if (mode.equals("NEW_RQ")) {
                    this.setContent(selectInfo(model));
                    this.setClassContent("content");
                    setHeaderInfo(session, model);
                    setVirualQuotation();
                } else if (mode.equals("Q-EDIT")) {
                    resetCommonValues();
                    model.addAttribute("display", "display: inline;");
                    this.setContent(editInfo(p.getId(), model));
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

    @RequestMapping(value = "/quotation/rq-submit")
    public @ResponseBody
    String RFQSubmit(HttpServletRequest request, String infoRQ) {
        if (!Strings.isNullOrEmpty(infoRQ)) {
            setLstRQInfo(infoRQ, srvProjectManager);
        }

        return null;
    }

    @RequestMapping(value = "/quotation/get-item")
    public @ResponseBody
    String getItem(HttpServletRequest request, Long rqId, Integer location) {
        if (rqId != null && location != null) {
            List<QuotationItem> items = getMapQuotationItem(rqId, location);
            Map<String, String> content = new HashMap<>();

            content.put("requestQuotation", rqId.toString());
            content.put("location", location.toString());
            if (items != null && !items.isEmpty()) {
                Set<QuotationItemModel> qis = new HashSet<>();

                items.stream().forEach((item) -> {
                    RequestQuotationItem rqi = srvProjectManager.getDaoRequestQuotationItem().getById(item.getRequestQuotationItem());

                    if (rqi != null) {
                        BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(rqi.getBillMaterialServiceItem());

                        if (bmsi != null) {
                            qis.add(new QuotationItemModel(item.getRequestQuotationItem(), bmsi.getQuantity()));
                        }
                    }
                });

                content.put("requestQuotationItem", new Gson().toJson(qis));

                return new Gson().toJson(content);
            }
        }

        return null;
    }

    @RequestMapping(value = "/quotation/calculate")
    public @ResponseBody
    String getCalculate(HttpServletRequest request, Long id, Integer location, String itemInfo) {
        if (id != null && location != null && !Strings.isNullOrEmpty(itemInfo)) {
            QuotationItemModel[] items = new Gson().fromJson(itemInfo, QuotationItemModel[].class);

            if (items != null && items.length > 0) {
                Map<String, String> content = new HashMap<>();
                BigDecimal sumPriceItems = BigDecimal.ZERO;

                for (QuotationItemModel item : items) {
                    BigDecimal totalPrice = item.price.multiply(new BigDecimal(item.qty));
                    BigDecimal discountPrice = totalPrice.multiply(item.discount).divide(new BigDecimal(100));
                    BigDecimal netPrice = totalPrice.subtract(discountPrice);
                    sumPriceItems = sumPriceItems.add(netPrice);

                    editMapQuotationItem(id, location, new QuotationItem.Builder()
                            .setRequestQuotationItem(item.getId())
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
    String changeCurrency(HttpServletRequest request, Long rqId, Integer location, Integer currency) {
        editMapQuotation(rqId, location, new Quotation.Builder().setCurrency(currency).build());

        return (currency != null) ? getCurrencyById(currency) : null;
    }

    @RequestMapping(value = "/quotation/add-item")
    public @ResponseBody
    String addItem(Long rqId, Integer location, Integer currency) {
        if (rqId != null && location != null && currency != null) {
            Map<String, String> content = new HashMap<>();
            RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);

            if (getMapQuotation(rqId, location) != null) {
                content.put("status", "exist");
                content.put("message", "Exist Quotation for RFQ=" + rq.getName() + " and Location=" + getLocationNameById(location));

                return new Gson().toJson(content);
            } else {
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(rq.getBillMaterialService());

                if (bms != null) {
                    ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                    if (pd != null) {
                        List<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rqId);

                        setMapQuotation(new Quotation.Builder()
                                .setCurrency(currency)
                                .setCustomer(pd.getCustomer())
                                .setName(pd.getReference() + "-" + pd.getCustomer())
                                .setRequestQuotation(rqId)
                                .setLocation(location)
                                .build());
                        if (items != null && !items.isEmpty()) {
                            items.stream().forEach((item) -> {
                                setMapQuotationItem(rq.getId(), location, new QuotationItem.Builder()
                                        .setRequestQuotationItem(item.getId())
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
        }

        return null;
    }

    @RequestMapping(value = "/quotation/clear")
    public @ResponseBody
    String addItem(Long rqId, Integer location) {
        List<QuotationItem> items = getMapQuotationItem(rqId, location);

        if (items != null && !items.isEmpty()) {
            items.stream().forEach((item) -> {
                item.setEdit(Boolean.FALSE);
            });
        }

        return null;
    }

    @RequestMapping(value = "/quotation/save")
    public @ResponseBody
    String save(Quotation _q) {
        if (_q != null) {
            Collection<Quotation> qs = getMapQuotation();

            if (qs != null && !qs.isEmpty()) {
                qs.stream()
                        .map((q) -> {
                            q.setComplete(Boolean.FALSE);
                            return q;
                        })
                        .map((q) -> {
                            q.setDiscard(Boolean.TRUE);
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
                        .map((q) -> srvProjectManager.getDaoQuotation().add(q))
                        .filter((q) -> (q != null)).forEach((q) -> {
                    List<QuotationItem> qis = getMapQuotationItem(q.getRequestQuotation(), q.getLocation());
                    if (qis != null && !qis.isEmpty()) {
                        qis.stream().forEach((qi) -> {
                            qi.setQuotation(q.getId());
                        });

                        srvProjectManager.getDaoQuotationItem().add(qis);
                    }
                });

                RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(qs.iterator().next().getRequestQuotation());

                if (rq != null) {
                    BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(rq.getBillMaterialService());

                    if (bms != null) {
                        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                        if (pd != null) {
                            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());
                            Contact cont = srvProjectManager.getDaoContact().getById(pd.getContact());

                            pd.setStatus(ProjectStatusEnum.QUOTATION.toString());
                            srvProjectManager.getDaoProjectDetail().edit(pd);

                            if (p != null && cont != null) {
                                p.setStatus(ProjectStatusEnum.QUOTATION.toString());
                                srvProjectManager.getDaoProject().edit(p);
                                resetCommonValues();
                                try {
                                    try {
                                        JasperHelper.generateReport("MarpoGroupQuotation.jasper",
                                                "Quotation" + pd.getReference(),
                                                ImmutableMap.builder()
                                                .put("customer", pd.getCustomer())
                                                .put("telephone", cont.getPhone())
                                                .put("contract", cont.getSurname() + " " + cont.getName())
                                                .put("reference", p.getReference())
                                                .put("vessel", pd.getVesselName())
                                                .put("your_ref", _q.getCustomerReference())
                                                .put("welcome", _q.getWelcome())
                                                .put("remark", _q.getRemark())
                                                .put("request_quotation", rq.getId())
                                                .build());
                                    } catch (JRException ex) {
                                        Logger.getLogger(QuotationController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(QuotationController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                return "<h1>Quotation - REF:" + pd.getReference() + " - Complete</h1>\n";
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @RequestMapping(value = "/quotation/complete")
    public @ResponseBody
    String complete(Long rqId, Integer location) {
        if (rqId != null && location != null) {
            Quotation q = getMapQuotation(rqId, location);

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
