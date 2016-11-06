/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.BillMaterialServiceItem;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.RequestQuotationItem;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
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
public class HistoryNewProjectController extends Common {

    private static final Logger logger = Logger.getLogger(HistoryNewProjectController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    private String getBMSIHeader() {
        return "<tr>"
                + "<th>Item</th>\n"
                + "<th>Price</th>\n"
                + "<th>Availability</th>\n"
                + "<th>Quantity</th>\n"
                + "</tr>\n";
    }

    private String getBMSIBody(List<BillMaterialServiceItem> bmsis) {
        String response = "";

        for (BillMaterialServiceItem bmsi : bmsis) {
            Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

            if (item != null) {
                response += "<tr>\n"
                        + "<td>" + (!Strings.isNullOrEmpty(item.getImno()) ? item.getImno() : "") + "</td>\n"
                        + "<td>" + (bmsi.getPrice() != null ? bmsi.getPrice() : "0.00") + "</td>\n"
                        + "<td>" + (bmsi.getAvailable() != null ? bmsi.getAvailable() : "0") + "</td>\n"
                        + "<td>" + (bmsi.getQuantity() != null ? bmsi.getQuantity() : "0") + "</td>\n"
                        + "</tr>";
            }
        }

        return response;
    }

    private String getBMSIFooter(Integer count) {
        String response = "";

        return response;
    }

    private String getRFQIHeader() {
        return "<tr>"
                + "<th>Item</th>\n"
                + "<th>Price</th>\n"
                + "<th>Discount</th>\n"
                + "<th>Availability</th>\n"
                + "<th>Total</th>\n"
                + "</tr>\n";
    }

    private String getRFQIBody(List<RequestQuotationItem> rfqis) {
        String response = "";

        for (RequestQuotationItem rfqi : rfqis) {
            BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(rfqi.getBillMaterialServiceItem());

            if (bmsi != null) {
                Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                if (item != null) {
                    response += "<tr>\n"
                            + "<td>" + (!Strings.isNullOrEmpty(item.getImno()) ? item.getImno() : "") + "</td>\n"
                            + "<td>" + (rfqi.getUnitPrice() != null ? rfqi.getUnitPrice() : "0.00") + "</td>\n"
                            + "<td>" + (rfqi.getDiscount() != null ? rfqi.getDiscount() : "0.00") + "</td>\n"
                            + "<td>" + (rfqi.getAvailability() != null ? rfqi.getAvailability() : "0") + "</td>\n"
                            + "<td>" + (rfqi.getTotal() != null ? rfqi.getTotal() : "0.00") + "</td>\n"
                            + "</tr>";
                }
            }
        }

        return response;
    }

    private String getRFQIFooter(Integer count) {
        String response = "";

        return response;
    }

    private String getRQSHeader() {
        return "<tr>"
                + "<th>Supplier</th>\n"
                + "<th>Currency</th>\n"
                + "<th>Delivery Cost</th>\n"
                + "<th>Other Expenses</th>\n"
                + "<th>Material Cost</th>\n"
                + "<th>Grand Total</th>\n"
                + "</tr>\n";
    }

    private String getRQSBody(List<RequestQuotation> rqs) {
        String response = "";

        for (RequestQuotation rq : rqs) {
            response += "<tr>\n"
                    + "<td><a href='#' onclick='dlgViewRFQ(" + rq.getId() + ")'>" + rq.getSupplier() + "</a></td>\n"
                    + "<td>" + getCurrencyName(rq.getCurrency()) + "</td>\n"
                    + "<td>" + rq.getDeliveryCost() + "</td>\n"
                    + "<td>" + rq.getOtherExpenses() + "</td>\n"
                    + "<td>" + rq.getMaterialCost() + "</td>\n"
                    + "<td>" + rq.getGrandTotal() + "</td>\n"
                    + "</tr>";
        }

        return response;
    }

    private String getRQSFooter(Integer count) {
        String response = "";

        return response;
    }

    @RequestMapping(value = "/history-new-project")
    public String WorkOrder(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                this.setTitle("History-Projects");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/HistoryNewProject.jsp");
                setHeaderInfo(session, model);

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = "/history-new-project/bms-info")
    public @ResponseBody
    String getBMSInfo(Long pdId) {
        Map<String, String> content = new HashMap<>();
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);

        if (pd != null) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

            if (bms != null) {
                List<BillMaterialServiceItem> bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getByBillMaterialService(bms.getId());

                content.put("bmsName", bms.getName());
                content.put("bmsProject", pd.getReference());
                content.put("bmsComplete", bms.getComplete().toString());
                content.put("bmsNote", bms.getNote());
                if (bmsi != null && !bmsi.isEmpty()) {
                    content.put("bmsiHeader", getBMSIHeader());
                    content.put("bmsiBody", getBMSIBody(bmsi));
                    content.put("bmsiFooter", getBMSIFooter(bmsi.size()));
                }

                List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                if (rqs != null && !rqs.isEmpty()) {
                    content.put("rfqHeader", getRQSHeader());
                    content.put("rfqBody", getRQSBody(rqs));
                    content.put("rfqFooter", getRQSFooter(rqs.size()));
                }
            }
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/history-new-project/rfq-info")
    public @ResponseBody
    String getRFQInfo(Long rqId) {
        Map<String, String> content = new HashMap<>();
        RequestQuotation rq = srvProjectManager.getDaoRequestQuotation().getById(rqId);

        if (rq != null) {
            List<RequestQuotationItem> rfqi = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());

            content.put("rfqName", !Strings.isNullOrEmpty(rq.getName()) ? rq.getName() : "");
            content.put("rfqComplete", rq.getComplete().toString());
            content.put("rfqDiscard", rq.getDiscard().toString());
            content.put("rfqSupplier", !Strings.isNullOrEmpty(rq.getSupplier()) ? rq.getSupplier() : "");
            content.put("rfqCurrency", rq.getCurrency() != null ? getCurrencyById(rq.getCurrency()) : "");
            content.put("rfqMaterialCost", rq.getMaterialCost() != null ? rq.getMaterialCost().toString() : "");
            content.put("rfqDeliveryCost", rq.getDeliveryCost() != null ? rq.getDeliveryCost().toString() : "");
            content.put("rfqOtherExpenses", rq.getOtherExpenses() != null ? rq.getOtherExpenses().toString() : "");
            content.put("rfqGrandTotal", rq.getGrandTotal() != null ? rq.getGrandTotal().toString() : "");
            content.put("rfqNote", !Strings.isNullOrEmpty(rq.getNote()) ? rq.getNote() : "");
            content.put("rfqSupplierNote", !Strings.isNullOrEmpty(rq.getSupplierNote()) ? rq.getSupplierNote() : "");
            if (rfqi != null && !rfqi.isEmpty()) {
                content.put("rfqiHeader", getRFQIHeader());
                content.put("rfqiBody", getRFQIBody(rfqi));
                content.put("rfqiFooter", getRFQIFooter(rfqi.size()));
            }
        }

        return new Gson().toJson(content);
    }
}
