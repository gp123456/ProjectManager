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
public class ContactStatistic extends Common {
    private static final Logger LOG = Logger.getLogger(ContactStatistic.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;
    
    @RequestMapping(value = "/statistic")
    public String Statistic(Integer year, Model model) {
        this.setTitle("Contacts-Statistic");
        this.setSide_bar("../contact/sidebar.jsp");
        this.setContent("../contact/Statistic.jsp");
        setHeaderInfo(model);

        return "index";
    }
}
