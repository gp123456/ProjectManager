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
public class ShippingInvoiceController extends Common {
    @RequestMapping(value = "/shipping-invoice")
    public String ShippingInvoice(Model model) {
        this.setTitle("Projects-Shipping Invoice");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/ShippingInvoice.jsp");
        setHeaderInfo(model);
        
        return "index";
    }
}
