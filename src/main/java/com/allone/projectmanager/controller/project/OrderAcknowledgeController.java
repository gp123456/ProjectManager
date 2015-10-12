/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.controller.common.ProjectCommon;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project")
public class OrderAcknowledgeController extends ProjectCommon {
    @RequestMapping(value = "/order-acknowledge")
    public String OrderAcknowledge(Model model) {
        this.setTitle("Projects-Order Acknowledge");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/OrderAcknowledge.jsp");
        setHeaderInfo(model);
        
        return "index";
    }
}