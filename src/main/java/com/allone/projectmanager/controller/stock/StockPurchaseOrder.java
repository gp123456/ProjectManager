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
public class StockPurchaseOrder extends Common {
    private static final Logger LOG = Logger.getLogger(StockPurchaseOrder.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;
    
    @RequestMapping(value = "/purchase-order")
    public String PurchaseOrder(Model model) {
        this.setTitle("Stock-Purchase Order");
        this.setSide_bar("../stock/sidebar.jsp");
        this.setContent("../stock/PurchaseOrder.jsp");
        setHeaderInfo(model);

        return "index";
    }
}
