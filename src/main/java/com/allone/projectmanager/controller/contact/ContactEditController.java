/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.contact;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class ContactEditController extends Common {

    private static final Logger logger = Logger.getLogger(ContactEditController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    @RequestMapping(value = "/edit")
    public String Edit(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                this.setTitle("Contacts-Edit");
                this.setSide_bar("../contact/sidebar.jsp");
                this.setContent("../contact/Edit.jsp");
                setHeaderInfo(session, model);

                return "index";
            }
        }

        return "";
    }
}
