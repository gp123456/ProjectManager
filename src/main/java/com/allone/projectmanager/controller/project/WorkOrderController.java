/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.controller.common.Common;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project")
public class WorkOrderController extends Common {
    @RequestMapping(value = "/work-order")
    public String WorkOrder(Model model) {
        this.setTitle("Projects-Work Order");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/WorkOrder.jsp");
        setHeaderInfo(model);
        
        return "index";
    }
}
