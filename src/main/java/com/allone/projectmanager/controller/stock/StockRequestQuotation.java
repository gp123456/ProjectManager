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
public class StockRequestQuotation extends Common {
    private static final Logger LOG = Logger.getLogger(StockRequestQuotation.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;
    
    @RequestMapping(value = "/request-quotation")
    public String RequestQuotation(Model model) {
        this.setTitle("Stock-Request Quotation");
        this.setSide_bar("../stock/sidebar.jsp");
        this.setContent("../stock/RequestQuotation.jsp");
        setHeaderInfo(model);

        return "index";
    }
}
