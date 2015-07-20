/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.stock;

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
@RequestMapping(value = "/stock")
public class StockQCCheckLogging extends Common {
    private static final Logger LOG = Logger.getLogger(StockQCCheckLogging.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;
    
    @RequestMapping(value = "/qc-check-logging")
    public String QCCheckLogging(Model model) {
        this.setTitle("Stock-QC Check & Logging");
        this.setSide_bar("../stock/sidebar.jsp");
        this.setContent("../stock/QCCheckLogging.jsp");
        setHeaderInfo(model);

        return "index";
    }
}
