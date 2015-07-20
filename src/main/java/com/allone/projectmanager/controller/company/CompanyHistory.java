/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.company;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
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
@RequestMapping(value = "/company")
public class CompanyHistory extends Common {
    private static final Logger LOG = Logger.getLogger(CompanyHistory.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;
    
    @RequestMapping(value = "/history")
    public String History(Model model) {
        this.setTitle("Companies-History");
        this.setSide_bar("../company/sidebar.jsp");
        this.setContent("../company/History.jsp");
        setHeaderInfo(model);

        return "index";
    }
}
