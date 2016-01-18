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
public class HistoryOldProjectController extends Common {
    @RequestMapping(value = "/history-old-project")
    public String WorkOrder(Model model) {
        this.setTitle("History-Old-Project");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/HistoryOldProject.jsp");
        model.addAttribute("prj_version", "old");
        setHeaderInfo(model);
        
        return "index";
    }
}
