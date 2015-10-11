/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import org.springframework.stereotype.Controller;
import com.allone.projectmanager.model.User;
import com.allone.projectmanager.tools.JasperReport;
import com.allone.projectmanager.tools.Printing;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author antonia
 */
@Controller
public class Root extends ProjectCommon {

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

    @RequestMapping(value = "/")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/home")
    public String index(@Validated User user, Model model) throws ParseException {
        Collabs collab = srvProjectManager.getDaoCollab().login(user.getUsername(), user.getPassword());

        if (collab != null) {
            SimpleDateFormat ds = new SimpleDateFormat("d MMM Y HH:mm");

            getUser().setId(collab.getId());
            getUser().setScreen_name(user.getUsername());
            getUser().setLast_login(ds.format(new Date()));
            getUser().setFull_name(collab.getSurname() + " " + collab.getName());
            getUser().setProject_reference((collab.getProjectId() + 1l) + "/" + collab.getProjectPrefix());
            getUser().setProject_expired(collab.getProjectExpired());
            setTitle("Project - View");
            setHeader("header.jsp");
            setContent("../project/ViewProject.jsp");
            setHeaderInfo(model);
            model.addAttribute("login", "true");
            
            return "index";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/items")
    public String Items(@Validated User user, Model model) throws ParseException {
        return "items";
    }

    @RequestMapping(value = "/view")
    public @ResponseBody
    String getView() {
        Map<String, List<String>> content = new HashMap<>();
        content.put("OpenProjectSaleStatus", getOpenProjectStatusByType(srvProjectManager, ProjectTypeEnum.SALE.
                                                                       toString()));
        content.put("OpenProjectServiceStatus", getOpenProjectStatusByType(srvProjectManager, ProjectTypeEnum.SERVICE.
                                                                          toString()));
        content.put("OpenProjectSaleCompany", getOpenProjectCompanyByType(srvProjectManager, ProjectTypeEnum.SALE.
                                                                          toString()));
        content.put("OpenProjectServiceCompany", getOpenProjectCompanyByType(srvProjectManager, ProjectTypeEnum.SERVICE.
                                                                             toString()));

        String info = new Gson().toJson(content);
        
        return info;
    }

    @RequestMapping(value = "/refresh")
    public @ResponseBody
    String refreshSearchContent(Integer offset, Integer size) {
        return refreshSearchContent(srvProjectManager, offset, size);
    }

    @RequestMapping(value = "/sendemail")
    public @ResponseBody
    String sendProjectEmail(Project p) {
        return "";
    }

    @RequestMapping(value = "/createxls")
    public @ResponseBody
    String createProjectXLS(Project p) {
        return "";
    }

    @RequestMapping(value = "/printxls")
    public @ResponseBody
    String printProjectXLS(Project p) {
        return "";
    }

    @RequestMapping(value = "/printpdf")
    public @ResponseBody
    String printProjectPDF(ProjectDetail pd, Integer offset, Integer size) throws IOException, FileNotFoundException,
                                                                                  PrintException {
        ProjectDetail dbpd = srvProjectManager.getDaoProjectDetail().getById(pd.getId());
        Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

        if (dbpd != null && p != null) {
            Map<String, String> content = new HashMap<>();
            String strPath = JasperReport.getPATH_PROJECT() + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +
                   "/" + p.getReference().replace("/", "_") + ".pdf";

            Printing.printing(strPath);

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, dbpd, new ArrayList<String>(Arrays.asList(
                                                     "Start", "Start", "Processed", "Start", "Start", "Start", "Start")),
                                                     getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = "/search")
    public @ResponseBody
    String searchProject(ProjectDetail pd, Integer offset, Integer size) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            String projectHeader = createProjectHeader(getModeView());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList("Start",
                                                                                                                "Start",
                                                                                                                "Start")),
                                                     getModeView(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }
}
