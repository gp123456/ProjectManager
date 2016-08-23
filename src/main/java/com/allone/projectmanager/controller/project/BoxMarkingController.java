/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.controller.common.Common;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project-box")
public class BoxMarkingController extends Common {

    @RequestMapping(value = "box-markings")
    public String BoxMarking(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                this.setTitle("Projects-Box Marking");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/BoxMarking.jsp");
                setHeaderInfo(session, model);

                return "index";
            }
        }

        return "";
    }
}
