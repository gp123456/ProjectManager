/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.WCSProjectManagerService;
import com.allone.projectmanager.controller.common.RequestQuotationCommon;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.BillMaterialServiceItem;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.RequestQuotationItem;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.google.common.base.Strings;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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

    @Autowired
    WCSProjectManagerService srvWCSProjectManager;

    private String[] getProjectBillInfo(Long pId) {
        String[] result = {"", ""};
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                List<BillMaterialService> pbs = srvProjectManager.getDaoProjectBill().getByProject(pd.getId());

                if (pbs != null && !pbs.isEmpty()) {
                    for (BillMaterialService pb : pbs) {
                        if (!Strings.isNullOrEmpty(pb.getNote())) {
                            result[0] += pb.getNote();
                        }

                        List<BillMaterialServiceItem> pbis = srvProjectManager.getDaoProjectBillItem()
                                                      .getByProjectBill(pb.getId());

                        if (pbis != null && !pbis.isEmpty()) {
                            Integer count = 0;

                            for (BillMaterialServiceItem pbi : pbis) {
                                if (pbi != null) {
                                    Item item = srvProjectManager.getDaoItem().getById(pbi.getItem());
                                    setVirtualRequestQuotationItem(pbi.getProjectBill(),
                                                                   new RequestQuotationItem.Builder()
                                                                   .setProjectBillItem(pbi.getId())
                                                                   .setClassSave("button alarm")
                                                                   .setClassSave("button alarm")
                                                                   .build());

                                    result[1] += "<tr>\n" +
                                                 "<td>" + ++count + "</td>\n" +
                                                 "<td>" + pbi.getItemImno() + "</td>\n" +
                                                 "<td>" + pbi.getItemDescription() + "</td>\n" +
                                                 "<td>" + getCurrencyById(pbi.getCurrency()) + "</td>\n" +
                                                 "<td>" + pbi.getQuantity() + "</td>\n" +
                                                 "<td>" + item.getQuantity() + "</td>\n" +
                                                 "<td id='quantity" + pbi.getProjectBill() + pbi.getId() +
                                                 "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" +
                                                 "</td>\n" +
                                                 "<td>" + pbi.getPrice() + "</td>\n" +
                                                 "<td>" + item.getPrice() + "</td>\n" +
                                                 "<td id='price" + pbi.getProjectBill() + pbi.getId() +
                                                 "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" +
                                                 "</td>\n" +
                                                 "<td id='discount" + pbi.getProjectBill() + pbi.getId() +
                                                 "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" +
                                                 "</td>\n" +
                                                 "<td></td>\n" +
                                                 "<td><input type='button' id='Edit' value='Edit' class='button' " +
                                                 "onclick='editRequestQuotationValues(" + pbi.getProjectBill() + "," +
                                                 pbi.getId() + ")' ></td>\n" +
                                                 "<td><input type='button' id='Refresh' value='Refresh' " +
                                                 "class='button' onclick='refreshValues(" + pbi.getProjectBill() +
                                                 "," + pbi.getId() + ")' ></td>\n" +
                                                 "<td><input type='button' id='Remove' value='Remove' " +
                                                 "class='button' onclick='removeValues(" + pbi.getProjectBill() +
                                                 "," + pbi.getId() + ")' ></td>\n" +
                                                 "<td><input type='button' id='Save' value='Save' " +
                                                 "class='button' onclick='saveValues(" + pbi.getProjectBill() +
                                                 "," + pbi.getId() + ")' ></td>\n" +
                                                 "</tr>\n";
                                }
                            }
                        }
                    }
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

        model.addAttribute("pdId", p.getId());
        model.addAttribute("projectReference", p.getReference());
        model.addAttribute("projectBillNote", pbInfo[0]);
        model.addAttribute("projectBillItems", pbInfo[1]);
        model.addAttribute("supplier", createSearchCompany(srvWCSProjectManager, null, CompanyTypeEnum.SUPPLIER));
        model.addAttribute("buttonSavePDF",
                           "<input type='button' value='Save PDF' class='button' onclick='savePDF(\"" +
                           p.getReference() + "\")'>");
        model.addAttribute("buttonSaveExcel",
                           "<input type='button' value='Save Excel' class='button' onclick='saveXLS(\"" +
                           p.getReference() + "\")'>");
        model.addAttribute("buttonSendEmail",
                           "<input type='button' value='Send eMail' class='button' onclick='sendEmail(\"" +
                           p.getId() + "\")'>");

        return "index";
    }

    @RequestMapping(value = "/request-quotation/item/edit")
    public @ResponseBody
    String Edit(Long pbId, RequestQuotationItem item) {
        RequestQuotationItem temp = getRequestQuotationItem(pbId, item.getProjectBillItem());

        if (temp != null) {
            temp.setClassRefresh(item.getClassRefresh());
            temp.setClassSave(item.getClassSave());
            editVirtualRequestQuotationItem(pbId, temp);

            return createRequestQuotationItems();
        }

        return "";
    }

    private String createRequestQuotationItems() {
        String response = "";
        Set<Long> pbIds = getRequestQuotationIds();

        if (pbIds != null && !pbIds.isEmpty()) {
            for (Long pbId : pbIds) {
                Collection<RequestQuotationItem> items = getRequestQuotationItems(pbId);

                if (items != null && !items.isEmpty()) {
                    Integer count = 0;

                    for (RequestQuotationItem item : items) {
                        if (item != null) {
                            BillMaterialServiceItem pbi = srvProjectManager.getDaoProjectBillItem().getById(item.
                                                    getProjectBillItem());

                            if (pbi != null) {
                                response += "<tr>\n" +
                                            "<td>" + ++count + "</td>\n" +
                                            "<td>" + pbi.getItemImno() + "</td>\n" +
                                            "<td>" + pbi.getItemDescription() + "</td>\n" +
                                            "<td>" + getCurrencyById(pbi.getCurrency()) + "</td>\n" +
                                            "<td>" + pbi.getQuantity() + "</td>\n" +
                                            "<td>" + item.getQuantity() + "</td>\n" +
                                            "<td id='quantity" + pbi.getProjectBill() + pbi.getId() +
                                            "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" +
                                            "</td>\n" +
                                            "<td>" + pbi.getPrice() + "</td>\n" +
                                            "<td>" + item.getPrice() + "</td>\n" +
                                            "<td id='price" + pbi.getProjectBill() + pbi.getId() +
                                            "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" +
                                            "</td>\n" +
                                            "<td id='discount" + pbi.getProjectBill() + pbi.getId() +
                                            "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" +
                                            "</td>\n" +
                                            "<td></td>\n" +
                                            "<td><input type='button' id='Edit' value='Edit' class='button' " +
                                            "onclick='editValues(" + pbi.getProjectBill() + "," + pbi.getId() +
                                            ")' ></td>\n" +
                                            "<td><input type='button' id='Refresh' value='Refresh' " +
                                            "class='button' onclick='refreshValues(" + pbi.getProjectBill() +
                                            "," + pbi.getId() + ")' ></td>\n" +
                                            "<td><input type='button' id='Remove' value='Remove' " +
                                            "class='button' onclick='removeValues(" + pbi.getProjectBill() +
                                            "," + pbi.getId() + ")' ></td>\n" +
                                            "<td><input type='button' id='Save' value='Save' " +
                                            "class='button' onclick='saveValues(" + pbi.getProjectBill() +
                                            "," + pbi.getId() + ")' ></td>\n" +
                                            "</tr>\n";

                            }
                        }
                    }
                }
            }
        }

        return response;
    }
}
