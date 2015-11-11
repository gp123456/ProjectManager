/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.company;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyNewController extends Common {

    private static final Logger LOG = Logger.getLogger(CompanyNewController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    @RequestMapping(value = "/new")
    public String New(Model model) {
        this.setTitle("Companies-New");
        this.setSide_bar("../company/sidebar.jsp");
        this.setContent("../company/New.jsp");
        setHeaderInfo(model);

        return "index";
    }

    @RequestMapping(value = "/add")
    public @ResponseBody
    String addCompany(Company comp) {
        Map<String, String> content = new HashMap<>();

        if (comp != null) {
            comp.setType(CompanyTypeEnum.CUSTOMER.name());
            comp = srvProjectManager.getDaoCompany().add(comp);
            content.put("customer", createSearchCustomer(srvProjectManager, comp.getName()));
        }

        return new Gson().toJson(content);
    }
    
//    @RequestMapping(value = "/lst-customer")
//    public @ResponseBody
//    String lstCustomer() {
//        return createSearchCustomer(srvProjectManager, null);
//    }
}
