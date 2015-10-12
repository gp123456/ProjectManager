/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.contact;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
import com.allone.projectmanager.entities.Contact;
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
@RequestMapping(value = "/contact")
public class ContactNewController extends Common {

    private static final Logger logger = Logger.getLogger(ContactNewController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    @RequestMapping(value = "/new")
    public String New(Model model) {
        setTitle("Contacts-New");
        setHeader("header.jsp");
        setSide_bar("../contact/sidebar.jsp");
        setContent("../contact/New.jsp");
        setHeaderInfo(model);

        return "index";
    }

    @RequestMapping(value = "/add")
    public @ResponseBody
    String addContact(Contact cont) {
        String response = "";
        Map<String, String> content = new HashMap<>();
        
        if (cont != null) {
            cont = srvProjectManager.getDaoContact().add(cont);
            
            content.put("contact", createSearchContact(srvProjectManager, cont.getVessel()));
        }
        
        return new Gson().toJson(content);
    }
}
