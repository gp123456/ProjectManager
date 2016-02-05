/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectBill;
import com.allone.projectmanager.entities.ProjectDetail;
import java.util.List;
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

    @Autowired
    ProjectManagerService srvProjectManager;
    
    private String getProjectBillItems(Long pId) {
        String result = "";
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);
        
        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                List<ProjectBill> pbs = srvProjectManager.getDaoProjectBill().getByProject(pd.getId());
                
                if (pbs != null && !pbs.isEmpty()) {
                    for (ProjectBill pb : pbs) {
                        result = "<table>\n" +
                                 "<th>ITEM</th>\n" +
                                 "<th>CODE</th>\n" +
                                 "<th>DESCRIPTION</th>\n" +
                                 "<th>QUANTITY</th>\n" +
                                 "<th>UNIT PRICE</th>\n" + 
                                 "<th>DISCOUNT</th>\n" +
                                 "<th>TOTAL</th>\n" +
                                 "<tbody></tbody>\n" +
                                 "</table>\n";
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

        model.addAttribute("pd_id", p.getId());
        model.addAttribute("project_reference", p.getReference());
        model.addAttribute("project-bill-items", getProjectBillItems(p.getId()));

        return "index";
    }
}
