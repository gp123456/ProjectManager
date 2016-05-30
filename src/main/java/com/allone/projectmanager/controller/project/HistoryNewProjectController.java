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
public class HistoryNewProjectController extends Common {
    @RequestMapping(value = "/history-new-project")
    public String WorkOrder(Model model) {
        this.setTitle("History-New-Projects");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/HistoryNewProject.jsp");
        model.addAttribute("prj_version", "new");
        setHeaderInfo(model);
        
        return "index";
    }
}
