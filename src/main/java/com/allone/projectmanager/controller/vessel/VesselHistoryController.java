/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.vessel;

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
@RequestMapping(value = "/vessel")
public class VesselHistoryController extends Common {

    private static final Logger logger = Logger.getLogger(VesselHistoryController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    @RequestMapping(value = "/snapshot")
    public String History(Model model) {
        this.setTitle("Vessel");
        this.setHeader("header.jsp");
        this.setSide_bar("../vessel/sidebar.jsp");
        this.setContent("../vessel/History.jsp");
        setHeaderInfo(model);

        return "index";
    }
}
