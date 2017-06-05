/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.stock;

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
@RequestMapping(value = "/stock")
public class StockEdit extends Common {

    private static final Logger logger = Logger.getLogger(StockEdit.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    @RequestMapping(value = "/edit")
    public String Edit(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                setUser(srvProjectManager.getUser());
                this.setTitle("Stock-Edit");
                this.setSide_bar("../stock/sidebar.jsp");
                this.setContent("../stock/Edit.jsp");
                setHeaderInfo(model);

                return "index";
            }
        }

        return "";
    }
}
