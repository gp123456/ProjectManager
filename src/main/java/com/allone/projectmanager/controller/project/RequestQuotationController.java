/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.WCSProjectManagerService;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectBill;
import com.allone.projectmanager.entities.ProjectBillItem;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.wcs.WCSCompany;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.model.SearchInfo;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project")
public class RequestQuotationController extends ProjectCommon {

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
                List<ProjectBill> pbs = srvProjectManager.getDaoProjectBill().getByProject(pd.getId());

                if (pbs != null && !pbs.isEmpty()) {
                    for (ProjectBill pb : pbs) {
                        if (!Strings.isNullOrEmpty(pb.getNote())) {
                            result[0] += pb.getNote();
                        }

                        List<ProjectBillItem> pbis = srvProjectManager.getDaoProjectBillItem().getByProjectBill(pb.
                                              getId());

                        if (pbis != null && !pbis.isEmpty()) {
                            Integer count = 0;

                            for (ProjectBillItem pbi : pbis) {
                                if (pbi != null) {
                                    Item item = srvProjectManager.getDaoItem().getById(pbi.getItem());

                                    result[1] += "<tr>\n" +
                                                 "<td>" + ++count + "</td>\n" +
                                                 "<td>" +  pbi.getItemImno() + "</td>\n" +
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
}
