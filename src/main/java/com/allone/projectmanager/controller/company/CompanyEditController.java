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
public class CompanyEditController extends Common {
    private static final Logger LOG = Logger.getLogger(CompanyEditController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;
    
    @RequestMapping(value = "/edit")
    public String Edit(Model model) {
        this.setTitle("Companies-Edit");
        this.setSide_bar("../company/sidebar.jsp");
        this.setContent("../company/Edit.jsp");
        setHeaderInfo(model);

        return "index";
    }
}