/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.PlotInfoModel;
import org.springframework.stereotype.Controller;
import com.allone.projectmanager.model.User;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
    public String logout(HttpServletRequest request) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                removeUser(session.getId());
            }
        }

        return "login";
    }

    @RequestMapping(value = "/home")
    public String index(HttpServletRequest request, User _user, Model model) throws ParseException, UnsupportedEncodingException {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                User user = getUser(session.getId());

                if (user == null) {
                    Collabs collab = srvProjectManager.getDaoCollabs().login(_user.getUsername(), _user.getPassword());

                    if (collab != null) {
                        SimpleDateFormat ds = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        String current = ds.format(new Date());

                        user = new User();
                        user.setId(collab.getId());
                        user.setScreen_name(user.getUsername());
                        user.setLast_login(new String(current.getBytes("ISO-8859-1")));
                        user.setFull_name(collab.getSurname() + " " + collab.getName());
                        user.setProject_reference((collab.getProjectId() + 1l) + "/" + collab.getProjectPrefix());
                        user.setProject_expired(collab.getProjectExpired());
                        user.setEmail(collab.getEmail());
                        user.setRole(collab.getRole());
                        setUser(session.getId(), user);
                        setTitle("Project Manager");
                        setHeader("main_header.jsp");
                        setContent("../project/ViewProject.jsp");
                        setHeaderInfo(session, model);
                        model.addAttribute("login", "true");
                        model.addAttribute("role", user.getRole());

                        return "index";
                    } else {
                        return "login";
                    }
                } else {
                    setTitle("Project Manager");
                    setHeader("main_header.jsp");
                    setContent("../project/ViewProject.jsp");
                    setHeaderInfo(session, model);
                    model.addAttribute("login", "true");
                    model.addAttribute("role", user.getRole());

                    return "index";
                }
            } else {
                return "login";
            }
        }

        return "login";
    }

    @RequestMapping(value = "/view")
    public @ResponseBody
    String getView() {
        Map<String, List<PlotInfoModel>> content = new HashMap<>();

        content.put("OpenProjectSaleStatus", getOpenProjectStatusByType(srvProjectManager, ProjectTypeEnum.SALE.toString()));
        content.put("OpenProjectServiceStatus", getOpenProjectStatusByType(srvProjectManager, ProjectTypeEnum.SERVICE.toString()));
        content.put("OpenProjectSaleCompany", getOpenProjectCompanyByType(srvProjectManager, ProjectTypeEnum.SALE.toString()));
        content.put("OpenProjectServiceCompany", getOpenProjectCompanyByType(srvProjectManager, ProjectTypeEnum.SERVICE.toString()));

        return new Gson().toJson(content);
    }
}
