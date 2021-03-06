/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.vessel;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
import com.allone.projectmanager.entities.Vessel;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String New(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                setUser(srvProjectManager.getUser());
                this.setTitle("Vessel-New");
                this.setSide_bar("../vessel/sidebar.jsp");
                this.setContent("../vessel/New.jsp");
                setHeaderInfo(model);

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = "/add")
    public @ResponseBody
    String addVessel(Vessel vess) {
        String response = "";

        if (vess != null) {
            vess = srvProjectManager.getDaoVessel().add(vess);

            response = "<option value='"
                    + vess.getId() + "' selected='selected'>"
                    + vess.getName() + "</option>";
        }

        return response;
    }
}
