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
import com.allone.projectmanager.model.RequestQuotationItemModel;
import com.allone.projectmanager.model.User;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @SuppressWarnings("null")
    private String[] getRequestQuotationInfo(Long bms, Boolean supplier) {
        String[] result = {"", ""};
        RequestQuotation rq = getRequestQuotation(bms);
        @SuppressWarnings("UnusedAssignment")
        Integer index = 1;

        if (rq != null) {
            String name = (!Strings.isNullOrEmpty(rq.getName())) ? rq.getName() : "";
            BigDecimal materialCost = (rq.getMaterialCost() != null) ? rq.getMaterialCost() : BigDecimal.ZERO;
            BigDecimal grandTotal = (rq.getGrandTotal() != null) ? rq.getGrandTotal() : BigDecimal.ZERO;
            BigDecimal deliveryCost = (rq.getDeliveryCost() != null) ? rq.getDeliveryCost() : BigDecimal.ZERO;
            BigDecimal otherExpenses = (rq.getOtherExpenses() != null) ? rq.getOtherExpenses() : BigDecimal.ZERO;

            result[0] = (supplier.equals(Boolean.FALSE))
                    ? "<tr>\n"
                    + "<td id='name' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div></td>\n"
                    + "<td id='delivery' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>0.00</td>\n"
                    + "<td id='expenses' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>0.00</td>\n"
                    + "<td id='material' >0.00</td>\n"
                    + "<td id='grand' >0.00</td>\n"
                    + "</tr>\n"
                    : "<tr>\n"
                    + "<td id='name' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + name + "</td>\n"
                    + "<td id='delivery' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + ((!deliveryCost.equals(BigDecimal.ZERO)) ? deliveryCost : "0.00") + "</td>\n"
                    + "<td id='expenses' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + ((!otherExpenses.equals(BigDecimal.ZERO)) ? otherExpenses : "0.00") + "</td>\n"
                    + "<td id='material' >" + ((!materialCost.equals(BigDecimal.ZERO)) ? materialCost : "0.00") + "</td>\n"
                    + "<td id='grand' >" + ((!grandTotal.equals(BigDecimal.ZERO)) ? grandTotal : "0.00") + "</td>\n"
                    + "</tr>\n";
        }

        Collection<RequestQuotationItem> rqis = getRequestQuotationItems(bms);

        index = 1;
        if (rqis != null && !rqis.isEmpty()) {
            for (RequestQuotationItem rqi : rqis) {
                BillMaterialServiceItem bmsi = (rqi.getBillMaterialServiceItem() != null)
                        ? srvProjectManager.getDaoBillMaterialServiceItem().getById(rqi.getBillMaterialServiceItem())
                        : null;
                Item item = (bmsi != null) ? srvProjectManager.getDaoItem().getById(bmsi.getItem()) : null;
                String imno = (item != null) ? item.getImno() : "";
                String description = (item != null) ? item.getDescription() : "";
                @SuppressWarnings("null")
                Integer quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity() : 0;
                BigDecimal price = (rqi.getUnitPrice() != null) ? rqi.getUnitPrice() : BigDecimal.ZERO;
                Integer discount = (rqi.getDiscount() != null) ? rqi.getDiscount() : 0;
                Integer availability = (rqi.getAvailability() != null) ? rqi.getAvailability() : 0;
                BigDecimal total = (rqi.getTotal() != null) ? rqi.getTotal() : BigDecimal.ZERO;

                result[1] += (supplier.equals(Boolean.FALSE))
                        ? "<tr>\n"
                        + "<td>" + index++ + "</td>\n"
                        + "<td>" + imno + "</td>\n"
                        + "<td>" + description + "</td>\n"
                        + "<td>" + quantity + "</td>\n"
                        + "<td id='price" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>0.00</td>\n"
                        + "<td id='discount" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>0</td>\n"
                        + "<td id='availability" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>0</td>\n"
                        + "<td id='net" + rqi.getId() + "'>0.00</td>\n"
                        + "</tr>"
                        : "<tr>\n"
                        + "<td>" + index++ + "</td>\n"
                        + "<td>" + imno + "</td>\n"
                        + "<td>" + description + "</td>\n"
                        + "<td>" + quantity + "</td>\n"
                        + "<td id='price" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!price.equals(BigDecimal.ZERO)) ? price : "0.00") + "</td>\n"
                        + "<td id='discount" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!discount.equals(0)) ? discount : "0") + "</td>\n"
                        + "<td id='availability" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!availability.equals(0)) ? availability : "0") + "</td>\n"
                        + "<td id='net" + rqi.getId() + "'>" + ((!total.equals(BigDecimal.ZERO)) ? total : "0.00") + "</td>\n"
                        + "</tr>";
            }
        }

        return result;
    }

    @SuppressWarnings("null")
    private String[] getRequestQuotationInfo(RequestQuotation rq) {
        String[] result = {"", ""};
        Integer index = 1;

        if (rq != null) {
            String name = (!Strings.isNullOrEmpty(rq.getName())) ? rq.getName() : "";
            BigDecimal materialCost = (rq.getMaterialCost() != null) ? rq.getMaterialCost() : BigDecimal.ZERO;
            BigDecimal grandTotal = (rq.getGrandTotal() != null) ? rq.getGrandTotal() : BigDecimal.ZERO;
            BigDecimal deliveryCost = (rq.getDeliveryCost() != null) ? rq.getDeliveryCost() : BigDecimal.ZERO;
            BigDecimal otherExpenses = (rq.getOtherExpenses() != null) ? rq.getOtherExpenses() : BigDecimal.ZERO;

            result[0] = "<tr>\n"
                    + "<td id='name' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + name + "</td>\n"
                    + "<td id='delivery' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + ((!deliveryCost.equals(BigDecimal.ZERO)) ? deliveryCost : "0.00") + "</td>\n"
                    + "<td id='expenses' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + ((!otherExpenses.equals(BigDecimal.ZERO)) ? otherExpenses : "0.00") + "</td>\n"
                    + "<td id='material' >" + ((!materialCost.equals(BigDecimal.ZERO)) ? materialCost : "0.00") + "</td>\n"
                    + "<td id='grand' >" + ((!grandTotal.equals(BigDecimal.ZERO)) ? grandTotal : "0.00") + "</td>\n"
                    + "</tr>\n";
        }

        Collection<RequestQuotationItem> rqis = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());

        if (rqis != null && !rqis.isEmpty()) {
            for (RequestQuotationItem rqi : rqis) {
                BillMaterialServiceItem bmsi = (rqi.getBillMaterialServiceItem() != null)
                        ? srvProjectManager.getDaoBillMaterialServiceItem().getById(rqi.getBillMaterialServiceItem())
                        : null;
                Item item = (bmsi != null) ? srvProjectManager.getDaoItem().getById(bmsi.getItem()) : null;
                String imno = (item != null) ? item.getImno() : "";
                String description = (item != null) ? item.getDescription() : "";
                @SuppressWarnings("null")
                Integer quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity() : 0;
                BigDecimal price = (rqi.getUnitPrice() != null) ? rqi.getUnitPrice() : BigDecimal.ZERO;
                Integer discount = (rqi.getDiscount() != null) ? rqi.getDiscount() : 0;
                Integer availability = (rqi.getAvailability() != null) ? rqi.getAvailability() : 0;
                BigDecimal total = (rqi.getTotal() != null) ? rqi.getTotal() : BigDecimal.ZERO;

                result[1] += "<tr>\n"
                        + "<td>" + index++ + "</td>\n"
                        + "<td>" + imno + "</td>\n"
                        + "<td>" + description + "</td>\n"
                        + "<td>" + quantity + "</td>\n"
                        + "<td id='price" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!price.equals(BigDecimal.ZERO)) ? price : "0.00") + "</td>\n"
                        + "<td id='discount" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!discount.equals(0)) ? discount : "0") + "</td>\n"
                        + "<td id='availability" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!availability.equals(0)) ? availability : "0") + "</td>\n"
                        + "<td id='net" + rqi.getId() + "'>" + ((!total.equals(BigDecimal.ZERO)) ? total : "0.00") + "</td>\n"
                        + "</tr>";
            }
        }

        return result;
    }

    private String[] getRequestQuotationSupplier(RequestQuotation rq) {
        String[] result = {"", ""};

        if (rq != null) {
            BigDecimal materialCost = (rq.getMaterialCost() != null) ? rq.getMaterialCost() : BigDecimal.ZERO;
            BigDecimal grandTotal = (rq.getGrandTotal() != null) ? rq.getGrandTotal() : BigDecimal.ZERO;
            BigDecimal deliveryCost = (rq.getDeliveryCost() != null) ? rq.getDeliveryCost() : BigDecimal.ZERO;
            BigDecimal otherExpenses = (rq.getOtherExpenses() != null) ? rq.getOtherExpenses() : BigDecimal.ZERO;

            result[0] = "<tr>\n"
                    + "<td id='delivery' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + ((!deliveryCost.equals(BigDecimal.ZERO)) ? deliveryCost : "0.00") + "</td>\n"
                    + "<td id='expenses' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'><div contenteditable></div>"
                    + ((!otherExpenses.equals(BigDecimal.ZERO)) ? otherExpenses : "0.00") + "</td>\n"
                    + "<td id='material' >" + ((!materialCost.equals(BigDecimal.ZERO)) ? materialCost : "0.00") + "</td>\n"
                    + "<td id='grand' >" + ((!grandTotal.equals(BigDecimal.ZERO)) ? grandTotal : "0.00") + "</td>\n"
                    + "</tr>\n";
        }

        Collection<RequestQuotationItem> rqis = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());
        Integer index = 1;

        if (rqis != null && !rqis.isEmpty()) {
            for (RequestQuotationItem rqi : rqis) {
                BillMaterialServiceItem bmsi = (rqi.getBillMaterialServiceItem() != null)
                        ? srvProjectManager.getDaoBillMaterialServiceItem().getById(rqi.getBillMaterialServiceItem())
                        : null;
                Item item = (bmsi != null) ? srvProjectManager.getDaoItem().getById(bmsi.getItem()) : null;
                String imno = (item != null) ? item.getImno() : "";
                String description = (item != null) ? item.getDescription() : "";
                @SuppressWarnings("null")
                Integer quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity() : 0;
                BigDecimal price = (rqi.getUnitPrice() != null) ? rqi.getUnitPrice() : BigDecimal.ZERO;
                Integer discount = (rqi.getDiscount() != null) ? rqi.getDiscount() : 0;
                Integer availability = (rqi.getAvailability() != null) ? rqi.getAvailability() : 0;
                BigDecimal total = (rqi.getTotal() != null) ? rqi.getTotal() : BigDecimal.ZERO;

                result[1] += "<tr>\n"
                        + "<td>" + index++ + "</td>\n"
                        + "<td>" + imno + "</td>\n"
                        + "<td>" + description + "</td>\n"
                        + "<td>" + quantity + "</td>\n"
                        + "<td id='price" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!price.equals(BigDecimal.ZERO)) ? price : "0.00") + "</td>\n"
                        + "<td id='discount" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!discount.equals(0)) ? discount : "0") + "</td>\n"
                        + "<td id='availability" + rqi.getId() + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                        + "<div contenteditable></div>" + ((!availability.equals(0)) ? availability : "0") + "</td>\n"
                        + "<td id='net" + rqi.getId() + "'>" + ((!total.equals(BigDecimal.ZERO)) ? total : "0.00") + "</td>\n"
                        + "</tr>";
            }
        }

        return result;
    }

    @SuppressWarnings("null")
    private RequestQuotation setRequestQuotation(Long rqId) {
        RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);

        if (rq != null) {
            setRequestQuotation(srvProjectManager, rq.getBillMaterialService(), rq);

            List<RequestQuotationItem> rqis = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());

            if (rqis != null && !rqis.isEmpty()) {
                rqis.stream().forEach((rqi) -> {
                    setRequestQuotationItem(rq.getBillMaterialService(), rqi);
                });
            }
        } else {
            return null;
        }

        return rq;
    }

    private String[] getRequestQuotationByProject(Long pId, Boolean fillRQFExist) {
        String[] result = {"", "", "", "", "", "", "", ""};
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectIdType(pId, ProjectTypeEnum.SALE.name());

        if (pds != null && !pds.isEmpty()) {
            Boolean first = Boolean.TRUE;

            for (ProjectDetail pd : pds) {
                result[0] += (first.equals(Boolean.TRUE))
                        ? "<option value='" + pd.getId() + "' selected>" + pd.getReference() + "</option>\n"
                        : "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n";
                first = Boolean.FALSE;
            }

            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pds.get(0).getId());
            RequestQuotation rq = null;

            if (bms != null) {
                if (fillRQFExist.equals(Boolean.TRUE)) {
                    List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                    if (rqs != null && !rqs.isEmpty()) {
                        for (RequestQuotation _rq : rqs) {
                            result[1] += (first.equals(Boolean.TRUE))
                                    ? "<option value='" + _rq.getId() + "' selected>" + _rq.getName() + "</option>\n"
                                    : "<option value='" + _rq.getId() + "'>" + _rq.getName() + "</option>\n";
                            first = Boolean.FALSE;
                        }
                        rq = setRequestQuotation(rqs.get(0).getId());
                    }
                }
                String[] info = getRequestQuotationInfo(bms.getId(), (rq == null) ? Boolean.FALSE : Boolean.TRUE);

                result[2] = info[0];
                result[3] = info[1];
                result[4] = "<option value='none'>Select Currency</option>\n";
                for (CurrencyEnum currency : CurrencyEnum.values()) {
                    result[4] += "<option value='" + currency.getId() + "' "
                            + (rq != null && rq.getCurrency() != null && currency.getId().equals(rq.getCurrency())
                            ? "selected='selected'>" : ">") + currency.toString() + "</option>\n";
                }

                List<Company> suppliers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.SUPPLIER.name());

                result[5] = "<option value='none'>Select Supplier</option>\n";
                if (suppliers != null && !suppliers.isEmpty()) {
                    for (Company supplier : suppliers) {
                        result[5] += "<option value='" + supplier.getName() + "' "
                                + (rq != null && !Strings.isNullOrEmpty(rq.getSupplier()) && supplier.getName().equals(rq.getSupplier())
                                ? "selected='selected'>" : ">") + supplier.getName() + "</option>\n";
                    }
                }
                result[6] = (rq != null && !Strings.isNullOrEmpty(rq.getNote())) ? rq.getNote() : "";
                result[7] = (rq != null && !Strings.isNullOrEmpty(rq.getSupplierNote())) ? rq.getSupplierNote() : "";
            }
        }

        return result;
    }

    private String[] getRequestQuotationById(Long rqId) {
        String[] result = {"", "", "", "", ""};
        RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);

        if (rq != null) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(rq.getBillMaterialService());

            if (bms != null) {
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                if (pd != null) {
                    String[] info = getRequestQuotationSupplier(rq);

                    result[0] = pd.getReference();
                    result[1] = rq.getSupplier();
                    result[2] = getCurrencyName(rq.getCurrency());
                    result[3] = info[0];
                    result[4] = info[1];
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
                    + "<td>" + subproject + "</td>\n"
                    + "</tr>\n";
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

                        result[1] += "<tr>\n"
                                + "<td>" + imno + "</td>\n"
                                + "<td>" + stockName + "</td>\n"
                                + "<td>" + quantity + "</td>\n"
                                + "<td><input type='checkbox' " + ((checked) ? "checked" : "") + " onclick='handleClick(this,"
                                + bmsi.getBillMaterialService() + "," + bmsi.getId() + ");'></td>\n"
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

    private String[] getExistRequestQuotation(Long bmsId) {
        List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bmsId);
        String[] response = {"", "", "", "", "", "", ""};

        if (rqs != null && !rqs.isEmpty()) {
            for (RequestQuotation rq : rqs) {
                response[0] += "<option value='" + rq.getId() + "'>" + rq.getName() + "</option>\n";
            }
            response[1] = rqs.get(0).getSupplier();
            response[2] = getCurrencyName(rqs.get(0).getCurrency());

            String[] rqfInfo = getRequestQuotationInfo(rqs.get(0));

            response[3] = rqfInfo[0];
            response[4] = rqfInfo[1];
            response[5] = (!Strings.isNullOrEmpty(rqs.get(0).getNote())) ? rqs.get(0).getNote() : "";
            response[6] = (!Strings.isNullOrEmpty(rqs.get(0).getSupplierNote())) ? rqs.get(0).getSupplierNote() : "";
        }

        return response;
    }

    @RequestMapping(value = "/request-quotation")
    public String RequestQuotation(HttpServletRequest request, Project p, String emailSender, String mode, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                String[] modeInfo = mode.split("-");
                String _mode = (modeInfo != null) ? modeInfo[0] : mode;

                switch (_mode) {
                    case "RQ":
                        this.setTitle("Request Quotation");
                        this.setHeader("header.jsp");
                        this.setSide_bar("../project/sidebar.jsp");
                        this.setContent("../project/RequestQuotation.jsp");
                        this.setClassContent("content");
                        p = srvProjectManager.getDaoProject().getById(p.getId());
                        if (p != null) {
                            setHeaderInfo(session, model);
                            model.addAttribute("projectId", p.getId());
                            model.addAttribute("mode", modeInfo[1]);
                            model.addAttribute("projectReference", p.getReference());
                            model.addAttribute("buttonSave", "<input type='button' value='Save' class='button' onclick='save()'>");
                            model.addAttribute("buttonSendEmail", "<input type='button' value='Send eMail' class='button' onclick='sendEmail()'>");
                            model.addAttribute("buttonSavePDF", "<input type='button' value='Save PDF' class='button' onclick='savePDF()'>");
                            model.addAttribute("buttonSaveExcel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS()'>");
                        }
                        break;
                    case "RQS":
                        this.setTitle("Request Quotation Supplier");
                        this.setHeader("supplier_header.jsp");
                        this.setSide_bar(null);
                        this.setContent("../project/RequestQuotationSupplier.jsp");
                        this.setClassContent("contentNoSideBar");
                        setHeaderInfo(session, model);
                        model.addAttribute("requestQuotationId", p.getId());
                        model.addAttribute("emailSender", emailSender);
                        model.addAttribute("buttonClearAll", "<input type='button' value='Clear All' class='button' onclick='getRequestQuotation("
                                + "\"clear\"" + ")'>");
                        model.addAttribute("buttonRefresh", "<input type='button' value='Calculate' class='button' onclick='getRequestQuotation("
                                + "\"refresh\"" + ")' id='refresh' disabled='disabled'>");
                        model.addAttribute("buttonSendEmail", "<input type='button' value='Submit' class='button' onclick='sendEmailSupplier()'"
                                + " id='email' disabled='disabled'>");

                        RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(p.getId());
                        List<RequestQuotationItem> rqis = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());

                        if (rq != null) {
                            model.addAttribute("noteRequestQuotation", (!Strings.isNullOrEmpty(rq.getNote())) ? rq.getNote() : "");
                            model.addAttribute("noteSupplierRequestQuotation", (!Strings.isNullOrEmpty(rq.getSupplierNote())) ? rq.getSupplierNote() : "");

                            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(rq.getBillMaterialService());

                            if (bms != null) {
                                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());

                                if (pd != null) {
                                    p = srvProjectManager.getDaoProject().getById(pd.getProject());

                                    model.addAttribute("projectReference", p.getReference());

                                    if (rqis != null && !rqis.isEmpty()) {
                                        String ids = "";

                                        for (RequestQuotationItem rqi : rqis) {
                                            ids += rqi.getId() + ",";
                                        }

                                        if (!Strings.isNullOrEmpty(ids)) {
                                            ids = ids.substring(0, ids.lastIndexOf(","));
                                            model.addAttribute("requestQuotationItemsId", ids);
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case "ERQ": {
                        this.setTitle("Existing Request Quotation");
                        this.setHeader("supplier_header.jsp");
                        this.setSide_bar(null);
                        this.setContent("../project/RequestQuotationExist.jsp");
                        this.setClassContent("contentNoSideBar");
                        setHeaderInfo(session, model);
                        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(p.getId());
                        BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId());
                        String[] info = getExistRequestQuotation(bms.getId());

                        model.addAttribute("projectReference", pd.getReference());
                        model.addAttribute("requestQuotations", info[0]);
                        model.addAttribute("supplier", info[1]);
                        model.addAttribute("currency", info[2]);
                        model.addAttribute("requestQuotation", info[3]);
                        model.addAttribute("itemRequestQuotation", info[4]);
                        model.addAttribute("noteRequestQuotation", info[5]);
                        model.addAttribute("noteSupplierRequestQuotation", info[6]);
                        break;
                    }
                    case "BMSI": {
                        this.setTitle("Preparation Request Quotation");
                        this.setHeader("header.jsp");
                        this.setSide_bar("../project/sidebar.jsp");
                        this.setContent("../project/RequestQuotationSelectItem.jsp");
                        this.setClassContent("content");
                        setHeaderInfo(session, model);
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
                        setHeaderInfo(session, model);
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
        }

        return "";
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
        return "request-quotation?id=" + id + "&mode=RQ-NEW";
    }

    @RequestMapping(value = "/request-quotation/select-bmsi")
    public @ResponseBody
    String selectBMSI(Long pId, Long pdId) {
        return "request-quotation?id=" + pId + "&projectDetail=" + pdId + "&mode=BMSI";
    }

    @RequestMapping(value = "/request-quotation/content")
    public @ResponseBody
    String content(Long pId, String mode) {
        if (pId != null) {
            Map<String, String> content = new HashMap<>();
            String[] rqInfo = getRequestQuotationByProject(pId, mode.equals("EDIT"));

            content.put("subprojects", rqInfo[0]);
            content.put("requestQuotations", rqInfo[1]);
            content.put("requestQuotation", rqInfo[2]);
            content.put("itemRequestQuotation", rqInfo[3]);
            content.put("currency", rqInfo[4]);
            content.put("suppliers", rqInfo[5]);
            content.put("notes", rqInfo[6]);
            content.put("notesSupplier", rqInfo[7]);

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

            return new Gson().toJson(content);
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/request-quotation/get-request-quotation")
    public @ResponseBody
    String getRequestQuotationSupplier(Long rqId) {
        if (rqId != null) {
            RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);

            if (rq != null) {
                Map<String, String> content = new HashMap<>();

                content.put("requestQuotation", rq.getId().toString());

                Collection<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rqId);

                if (items != null && !items.isEmpty()) {
                    Set<RequestQuotationItemModel> rqis = new HashSet<>();

                    items.stream().forEach((item) -> {
                        BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());

                        rqis.add(new RequestQuotationItemModel(item.getId(), bmsi.getQuantity()));
                    });

                    content.put("requestQuotationItem", new Gson().toJson(rqis));
                }

                return new Gson().toJson(content);
            }
        }

        return "";
    }

    @RequestMapping(value = "/request-quotation/refresh")
    public @ResponseBody
    String refresh(Long id, BigDecimal delivery, BigDecimal expenses, String itemInfo) {
        if (id != null) {
            RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(id);
            BigDecimal sumPriceItems = BigDecimal.ZERO;

            if (rq != null) {
                rq.setDeliveryCost((delivery != null) ? delivery : BigDecimal.ZERO);
                rq.setOtherExpenses((expenses != null) ? expenses : BigDecimal.ZERO);
                if (!Strings.isNullOrEmpty(itemInfo)) {
                    RequestQuotationItemModel[] items = new Gson().fromJson(itemInfo, RequestQuotationItemModel[].class);

                    if (items != null && items.length > 0) {
                        for (RequestQuotationItemModel item : items) {
                            RequestQuotationItem rqi = srvProjectManager.getDaoRequestQuotationItem().getById(item.id);

                            if (rqi != null) {
                                BigDecimal totalPrice = item.price.multiply(new BigDecimal(item.qty));
                                BigDecimal discountPrice = totalPrice.multiply(new BigDecimal(item.discount).divide(new BigDecimal(100)));
                                BigDecimal netPrice = totalPrice.subtract(discountPrice);

                                rqi.setUnitPrice(item.price);
                                rqi.setAvailability(item.availability);
                                rqi.setDiscount(item.discount);
                                rqi.setTotal(netPrice);
                                srvProjectManager.getDaoRequestQuotationItem().edit(rqi);
                                sumPriceItems = sumPriceItems.add(netPrice);
                            }
                        }
                    }
                }
                rq.setMaterialCost(sumPriceItems);
                rq.setGrandTotal(rq.getDeliveryCost().add(rq.getOtherExpenses()).add(sumPriceItems));
                srvProjectManager.getDaoRequestQuotation().edit(rq);
            }

            String[] rqInfo = getRequestQuotationSupplier(rq);
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
    String sendEmail(HttpServletRequest request, Long pdId, RequestQuotation rq) throws MessagingException {
        String response = "";

        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                if (pdId != null && rq.getCurrency() != null) {
                    BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

                    if (bms != null) {
                        RequestQuotation _rq = getRequestQuotation(bms.getId());

                        _rq.setName(rq.getName());
                        _rq.setCurrency(rq.getCurrency());
                        _rq.setComplete(Boolean.FALSE);
                        _rq.setNote(rq.getNote());
                        _rq.setSupplier(rq.getSupplier());
                        if (_rq.getId() == null) {
                            _rq = srvProjectManager.getDaoRequestQuotation().add(_rq);

                            Collection<RequestQuotationItem> rqis = getRequestQoutationItems(bms.getId());
                            if (rqis != null && !rqis.isEmpty()) {
                                for (RequestQuotationItem rqi : rqis) {
                                    if (rqi.getRequestQuotation() == null) {
                                        rqi.setRequestQuotation(_rq.getId());
                                    }
                                }

                                srvProjectManager.getDaoRequestQuotationItem().add(rqis);
                            }
                        } else {
                            _rq = srvProjectManager.getDaoRequestQuotation().edit(_rq);
                        }
                        removeRequestQuotation(bms.getId());
                        removeRequestQuotationItems(bms.getId());
                        Company company = srvProjectManager.getDaoCompany().getByTypeName(CompanyTypeEnum.SUPPLIER, _rq.getSupplier());
                        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
                        User user = getUser(session.getId());

                        if (company != null && !Strings.isNullOrEmpty(company.getEmail1()) && user != null
                                && !Strings.isNullOrEmpty(user.getEmail()) && pd != null) {
                            sendMail("smtp.dmail.hol.gr", user.getEmail(), company.getEmail1(), null, "MARPO GROUP RFQ - REF:"
                                    + pd.getReference(), "http://46.176.159.231:8080/ProjectManager/project/request-quotation?id="
                                    + _rq.getId().toString() + "&emailSender=" + user.getEmail() + "&mode=RQS");

                            response = "<h1>Request Quotation - REF:" + pd.getReference() + " - Complete</h1>\n";
//                            response = "http://localhost:8081/ProjectManager/project/request-quotation?id=" + _rq.getId() + "&emailSender="
//                                    + user.getEmail() + "&mode=RQS";
                        } else if (company == null) {
                            response = "<h1>No found supplier</h1>\n";
                        } else if (Strings.isNullOrEmpty(company.getEmail1())) {
                            response = "<h1>The supplier " + company.getName() + " has invalid email:" + company.getEmail1() + "</h1>\n";
                        } else if (user == null) {
                            response = "<h1>You have lost the session. Yu must login again</h1>\n";
                        } else if (user.getEmail() == null) {
                            response = "<h1>You have invalid email:" + user.getEmail() + "</h1>\n";
                        }
                    } else if (bms == null) {
                        response = "no found bill of material or services";
                    }
                } else {
                    if (pdId == null) {
                        response = "you must to give a project detail id";
                    }
                    if (rq.getCurrency() == null) {
                        response = "you must to give a currency";
                    }
                }
            }
        }

        return response;
    }

    @RequestMapping(value = "/request-quotation/save")
    public @ResponseBody
    @SuppressWarnings("null")
    String saveRFQ(HttpServletRequest request, Long pdId, String supplier, Integer currency, String note, String name) throws MessagingException {
        String response = "";

        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                if (pdId != null && currency != null) {
                    BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

                    if (bms != null) {
                        RequestQuotation rq = getRequestQuotation(bms.getId());

                        rq.setCurrency(currency);
                        rq.setComplete(Boolean.FALSE);
                        rq.setNote(note);
                        rq.setSupplier(supplier);
                        rq.setName(name);
                        if (rq.getId() == null) {
                            rq = srvProjectManager.getDaoRequestQuotation().add(rq);

                            Collection<RequestQuotationItem> rqis = getRequestQoutationItems(bms.getId());
                            if (rq != null && rqis != null && !rqis.isEmpty()) {
                                for (RequestQuotationItem rqi : rqis) {
                                    if (rqi.getRequestQuotation() == null) {
                                        rqi.setRequestQuotation(rq.getId());
                                    }
                                }

                                srvProjectManager.getDaoRequestQuotationItem().add(rqis);
                            }
                        } else {
                            rq = srvProjectManager.getDaoRequestQuotation().edit(rq);
                        }
                        removeRequestQuotation(bms.getId());
                        removeRequestQuotationItems(bms.getId());
                    } else if (bms == null) {
                        response = "no found bill of material or services";
                    }
                } else {
                    if (pdId == null) {
                        response = "you must to give a project detail id";
                    }
                    if (currency == null) {
                        response = "you must to give a currency";
                    }
                }
            }
        }

        return response;
    }

    @RequestMapping(value = "/request-quotation/get-items")
    public @ResponseBody
    String getRQItems(Long rqId) {
        RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);
        Collection<RequestQuotationItem> items = getRequestQoutationItems(rq.getBillMaterialService());
        Set<Long> rqi = new HashSet<>();

        if (items != null) {
            items.stream().forEach((item) -> {
                rqi.add(item.getId());
            });

            return new Gson().toJson(rqi.toArray(), Long[].class
            );
        }

        return null;
    }

    @RequestMapping(value = "/request-quotation/send-email-supplier")
    public @ResponseBody
    @SuppressWarnings("null")
    String sendEmailSupplier(final RequestQuotation rq, final String emailSender) throws MessagingException {
        String response = "";
        RequestQuotation dbrq = srvProjectManager.getDaoRequestQuotation().getById(rq.getId());

        if (dbrq != null) {
            dbrq.setComplete(Boolean.TRUE);
            dbrq.setSupplierNote(rq.getSupplierNote());
            srvProjectManager.getDaoRequestQuotation().edit(dbrq);
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(dbrq.getBillMaterialService());

            if (bms != null) {
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());
                Company company = srvProjectManager.getDaoCompany().getByTypeName(CompanyTypeEnum.SUPPLIER, dbrq.getSupplier());

                sendMail("smtp.dmail.hol.gr", emailSender, "gpatitakis@gmail.com", null, "MARPO GROUP RFQ - REF:"
                        + pd.getReference(), "http://46.176.159.231:8080/ProjectManager/project/request-quotation?id="
                        + dbrq.getId().toString() + "&mode=RQS");

                response = "<h1>REQUEST FOR QUOTATION - REF:" + pd.getReference() + " - Complete</h1>\n";
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

//    @RequestMapping(value = "/request-quotation/change-supplier")
//    public @ResponseBody
//    String changeSupplier(Long pdId, String supplier) {
//        String response = "";
//        BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);
//
//        if (bms != null) {
//            Collection<String> suppliers = addSupplier(bms.getId(), supplier);
//
//            if (suppliers != null) {
//                for (String supp : suppliers) {
//                    response += "<tr>"
//                            + "<td>" + supp + "</td>\n"
//                            + "<td><input type='button' value='Remove' class='button' onclick='removeRequestQuotationSupplier(\"" + supp + "\")'></td>\n"
//                            + "</tr>\n";
//                }
//            }
//        }
//
//        return response;
//    }
//    
//    @RequestMapping(value = "/request-quotation/remove-supplier")
//    public @ResponseBody
//    String removeSupplier(Long pdId, String supplier) {
//        String response = "";
//        BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);
//
//        if (bms != null) {
//            Collection<String> suppliers = removeVirtualSupplier(bms.getId(), supplier);
//
//            if (suppliers != null) {
//                for (String supp : suppliers) {
//                    response += "<tr>"
//                            + "<td>" + supp + "</td>\n"
//                            + "<td><input type='button' value='Remove' class='button' onclick='removeRequestQuotationSupplier(\"" + supp + "\")'></td>\n"
//                            + "</tr>\n";
//                }
//            }
//        }
//
//        return response;
//    }
    @RequestMapping(value = "/request-quotation/complete")
    public @ResponseBody
    String completeRequestQuotation(Long pdId) {
        String response = "";

        if (pdId != null) {
            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

            if (p != null && pd != null) {
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId());

                if (bms != null) {
                    List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());
                    Map<String, String> content = new HashMap<>();

                    if (rqs != null && !rqs.isEmpty()) {
                        Boolean complete = Boolean.TRUE;

                        for (RequestQuotation rq : rqs) {
                            if (rq.getComplete().equals(Boolean.FALSE)) {
                                complete = Boolean.FALSE;
                                break;
                            }
                        }
                        if (complete.equals(Boolean.TRUE)) {
                            p.setStatus(ProjectStatusEnum.REQUEST_QUOTATION.toString());
                            srvProjectManager.getDaoProject().edit(p);
                            pd.setStatus(ProjectStatusEnum.REQUEST_QUOTATION.toString());
                            srvProjectManager.getDaoProjectDetail().edit(pd);
                            content.put("header", "REQUEST FOR QUOTATION - REF:" + p.getReference() + " COMPLETE");
                            content.put("location", "http://localhost:8081/ProjectManager/project/history-new-project");

                            return new Gson().toJson(content);
                        }
                    }
                } else {
                    response = "no found bill of material";
                }
            } else if (p == null) {
                response = "no found project";
            } else if (pd == null) {
                response = "no found project detail";
            }
        }

        return response;
    }

    @RequestMapping(value = "/request-quotation/change-rfq")
    public @ResponseBody
    String changeRFQ(Long rqId, Boolean hasList) {
        if (rqId != null) {
            RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);

            if (rq != null) {
                String[] info = getRequestQuotationInfo(rq);
                Map<String, String> content = new HashMap<>();

                if (hasList.equals(Boolean.TRUE)) {
                    String response;
                    List<Company> suppliers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.SUPPLIER.name());

                    response = "<option value='none'>Select Supplier</option>\n";
                    if (suppliers != null && !suppliers.isEmpty()) {
                        for (Company supplier : suppliers) {
                            response += "<option value='" + supplier.getName() + "' "
                                    + (rq != null && !Strings.isNullOrEmpty(rq.getSupplier()) && supplier.getName().equals(rq.getSupplier())
                                    ? "selected='selected'>" : ">") + supplier.getName() + "</option>\n";
                        }
                    }
                    content.put("suppliers", response);

                    response = "<option value='none'>Select Currency</option>\n";
                    for (CurrencyEnum currency : CurrencyEnum.values()) {
                        response += "<option value='" + currency.getId() + "' "
                                + (rq != null && rq.getCurrency() != null && currency.getId().equals(rq.getCurrency())
                                ? "selected='selected'>" : ">") + currency.toString() + "</option>\n";
                    }
                    content.put("currency", response);
                } else {
                    content.put("suppliers", !Strings.isNullOrEmpty(rq.getSupplier()) ? rq.getSupplier() : "");
                    content.put("currency", getCurrencyName((rq.getCurrency() != null) ? rq.getCurrency() : 0));
                }
                content.put("requestQuotation", info[0]);
                content.put("itemRequestQuotation", info[1]);
                content.put("notes", !Strings.isNullOrEmpty(rq.getNote()) ? rq.getNote() : "");
                content.put("notesSupplier", !Strings.isNullOrEmpty(rq.getSupplierNote()) ? rq.getSupplierNote() : "");

                return new Gson().toJson(content);
            }

        }

        return "";
    }
}
