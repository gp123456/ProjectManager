/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
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
@RequestMapping(value = "/project-delivery")
public class DeliveryNoteController extends Common {

    @Autowired
    ProjectManagerService srvProjectManager;
    
    @RequestMapping(value = "/delivery-note")
    public String DeliveryNote(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                setUser(srvProjectManager.getUser());
                this.setTitle("Projects-Delivery Note");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/DeliveryNote.jsp");
                setHeaderInfo(model);

                return "index";
            }
        }

        return "";
    }
}
