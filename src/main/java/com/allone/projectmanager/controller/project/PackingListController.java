/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.Root;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.ProjectDetail;
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
@RequestMapping(value = "/project")
public class PackingListController extends ProjectCommon {

    private static final Logger logger = Logger.getLogger(Root.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    public void setSrvProjectManager(ProjectManagerService srvProjectManager) {
        this.srvProjectManager = srvProjectManager;
        this.srvProjectManager.loadPropertyValues();
    }

    public ProjectManagerService getSrvProjectManager() {
        return srvProjectManager;
    }

    @RequestMapping(value = "/packing-list")
    public String PackingList(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                setUser(srvProjectManager.getUser());
                this.setTitle("Projects-Packing List");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/PackingList.jsp");
                setHeaderInfo(model);

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = "/packing-list/id")
    public @ResponseBody
    String getProjectPackingList(ProjectDetail pd, Model model) {
        return "";//searchProject(srvProjectManager, null, "new", pd, null, null, null, null, 0, Integer.MAX_VALUE, "view");
    }
}
