/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.WCSProjectManagerService;
import com.allone.projectmanager.controller.common.RequestQuotationCommon;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.BillMaterialServiceItem;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.RequestQuotationItem;
import com.allone.projectmanager.model.ProjectModel;
import com.google.common.base.Strings;
import com.google.gson.Gson;
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
        String[] result = {"", "", "", ""};
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                result[0] += "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>\n";
            }
            List<BillMaterialService> bmss = srvProjectManager.getDaoProjectBill().getByProject(pds.get(0).getId());

            if (bmss != null && !bmss.isEmpty()) {
                for (BillMaterialService value : bmss) {
                    List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoProjectBillItem().getByProjectBill(value.getId());

                    if (bmsis != null && !bmsis.isEmpty()) {
                        for (BillMaterialServiceItem bmsi : bmsis) {
                            setVirtualRequestQuotationItem(new ProjectModel(value.getProject(), getLocationIdByName(value.getLocation())),
                                                           new RequestQuotationItem.Builder()
                                                           .setItemBillMaterialService(bmsi.getId())
                                                           .build());
                        }
                    }
                }
                BillMaterialService bms = bmss.get(0);

                if (!Strings.isNullOrEmpty(bms.getNote())) {
                    result[1] += getCurrencyById(bms.getCurrency());
                    result[2] += bms.getNote();
                }
                result[3] = createRquestQuotationItem(new ProjectModel(bms.getProject(), getLocationIdByName(bms.getLocation())));
            }
        }

        return result;
    }

    private String createRquestQuotationItem(ProjectModel pm) {
        String result = "";
        
        logger.log(Level.INFO, "{0},{1}", new Object[]{pm.getId(), pm.getLocation()});

        Collection<RequestQuotationItem> rqis = getRequestQuotationItems(pm);

        if (rqis != null && !rqis.isEmpty()) {
            Integer count = 0;

            for (RequestQuotationItem rqi : rqis) {
                BillMaterialServiceItem bmsi = srvProjectManager.getDaoProjectBillItem().getById(rqi.getItemBillMaterialService());

                if (bmsi != null) {
                    logger.log(Level.INFO, "{0},{1}", new Object[]{count, bmsi.getItemImno()});
                    
                    result += "<tr>\n" +
                              "<td>" + ++count + "</td>\n" +
                              "<td>" + bmsi.getItemImno() + "</td>\n" +
                              "<td>" + bmsi.getItemDescription() + "</td>\n" +
                              "<td>" + bmsi.getQuantity() + "</td>\n" +
                              "<td id='availability" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>delivery-cost" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>other-expenses" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>unit-price" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>discount" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "<td id='>total" + bmsi.getId() + "' style='background:#333;color:#E7E5DC'><div contenteditable></div>" + "</td>\n" +
                              "</tr>\n";
                }
            }
        }

        return result;
    }

    private String changeRequestQuotation(Long pdId, Integer location) {
        Map<String, String> content = new HashMap<>();

        List<BillMaterialService> bmss = srvProjectManager.getDaoProjectBill().getByProject(pdId);

        content.put("currency", "");
        if (bmss != null && !bmss.isEmpty()) {
            for (BillMaterialService bms : bmss) {
                if (bms.getLocation().equals(getLocationNameById(location))) {
                    content.put("currency", getCurrencyById(bms.getCurrency()));
                    break;
                }
            }
        }
        content.put("itemRequestQuotation", createRquestQuotationItem(new ProjectModel(pdId, location)));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/request-quotation")
    public String RequestQuotation(Project p, Model model) {
        this.setTitle("Projects - Request Quotation");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/RequestQuotation.jsp");
        setHeaderInfo(model);
        p = srvProjectManager.getDaoProject().getById(p.getId());
        String[] pbInfo = getProjectBillInfo(p.getId());
        
        logger.log(Level.INFO, "{0},{1},{2}", new Object[]{pbInfo[0], pbInfo[1], pbInfo[2]});

        model.addAttribute("projectId", p.getId());
        model.addAttribute("projectReference", p.getReference());
        model.addAttribute("subproject", pbInfo[0]);
        model.addAttribute("currency", pbInfo[1]);
        model.addAttribute("location", createLocations());
        model.addAttribute("noteBillMaterialService", pbInfo[2]);
        model.addAttribute("itemBillMaterialService", pbInfo[3]);
        model.addAttribute("buttonSavePDF", "<input type='button' value='Save PDF' class='button' onclick='savePDF(\"" + p.getId() + "\")'>");
        model.addAttribute("buttonSaveExcel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS(\"" + p.getId() + "\")'>");
        model.addAttribute("buttonSendEmail", "<input type='button' value='Send eMail' class='button' onclick='sendEmail(\"" + p.getId() + "\")'>");

        return "index";
    }

    @RequestMapping(value = "/request-quotation/change")
    public String change(Long pdId, Integer location) {
        return changeRequestQuotation(pdId, location);
    }
}
