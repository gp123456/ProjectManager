/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.contact;

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
@RequestMapping(value = "/contact")
public class ContactNew extends Common {
    private static final Logger LOG = Logger.getLogger(ContactNew.class.getName());

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
    public String Add(Model model) {
        this.setTitle("Contact-Add");
        setContent("../contact/New.jsp");
        setHeaderInfo(model);

        return "index";
    }
}
