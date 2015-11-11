/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.vessel;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
import com.allone.projectmanager.entities.Vessel;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
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
@RequestMapping(value = "/vessel")
public class VesselNewController extends Common {
    private static final Logger logger = Logger.getLogger(VesselNewController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;
    
    @RequestMapping(value = "/new")
    public String New(Model model) {
        this.setTitle("Vessel-New");
        this.setSide_bar("../vessel/sidebar.jsp");
        this.setContent("../vessel/New.jsp");
        setHeaderInfo(model);

        return "index";
    }
    
    @RequestMapping(value = "/add")
    public @ResponseBody
    String addVessel(Vessel vess) {
        Map<String, String> content = new HashMap<>();

        if (vess != null) {
            vess = srvProjectManager.getDaoVessel().add(vess);
            
            content.put("vessel", createSearchVessel(srvProjectManager, vess.getId().toString()));
//            content.put("customer", createSearchCustomer(srvProjectManager, vess.getCompany()));
//            content.put("contact", createSearchContact(srvProjectManager, vess.getId()));
        }

        return new Gson().toJson(content);
    }
}
