/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project")
public class PurchaseOrderController extends ProjectCommon {
    @RequestMapping(value = "/purchase-order")
    public String PurchaseOrder(Project p, Long pdId, Model model) {
        this.setTitle("Projects - Purchase Order");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/PurchaseOrder.jsp");
        setHeaderInfo(model);
        model.addAttribute("pd_id", pdId);
        model.addAttribute("project_reference", p.getReference());
        
        return "index";
    }
}
