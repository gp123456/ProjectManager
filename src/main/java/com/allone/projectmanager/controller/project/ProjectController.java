/*
 * To change this license header, choose License Headers in ProjectController Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.Root;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.tools.JasperReport;
import com.allone.projectmanager.tools.Printing;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.print.PrintException;
import net.sf.jasperreports.engine.JRException;
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
public class ProjectController extends ProjectCommon {

    private static final Logger LOG = Logger.getLogger(Root.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    public void setSrvProjectManager(ProjectManagerService srvProjectManager) {
        this.srvProjectManager = srvProjectManager;
        this.srvProjectManager.loadPropertyValues();
    }

    public ProjectManagerService getSrvProjectManager() {
        return srvProjectManager;
    }

    private String createContent(Project p, Integer offset, Integer size) {
        Map<String, String> content = new HashMap<>();

        content = new Gson().fromJson(refreshSearchContent(srvProjectManager, offset, size), content.getClass());
        if (p != null && !p.getId().equals(-1l)) {
            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start")),
                    getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);
        } else {
            content.put("project_header", null);
            content.put("project_body", null);
            content.put("project_footer", null);
        }

        return new Gson().toJson(content);
    }

    private Map<String, Object> getMyProjectInfo(String keySize,
            String keyInfo,
            String mode,
            List<Project> info,
            String url) {
        Map<String, Object> content = new HashMap<>();

        content.put(keySize, 0);
        content.put(keyInfo, "");

        if (info != null && !info.isEmpty()) {
            String response = "";

            content.put(keySize, mode + "[" + info.size() + "]");

            for (Project p : info) {
                response += "<li onclick=\"window.location.href = '" + url + "?id=" + p.getId() + "&reference="
                        + p.getReference() + "';\">" + p.getReference() + "</li>";
            }
            content.put(keyInfo, response);
        }

        return content;
    }

    private Map<String, Object> getMenuInfo(String mode) {
        Map<String, Object> content = new HashMap<>();

        if (mode != null && (mode.isEmpty() || mode.equals(ProjectTypeEnum.SALE.getValue()))) {
            List<Project> sales = srvProjectManager.getDaoProject().getMyProjectType(getUser().getId(),
                    ProjectTypeEnum.SALE.getId());

            if (mode.isEmpty()) {
                content.put("project_sale_size_id", "#project-sale-size");
                content.put("project_sale_id", "#project-sale");
                content.putAll(getMyProjectInfo("project_sale_size", "project_sale_info", "Sale", sales,
                        "/ProjectManager/project/sale/edit"));
            } else {
                content.put("project_size_id", "#project-sale-size");
                content.put("project_id", "#project-sale");
                content.putAll(getMyProjectInfo("project_size", "project_info", "Sale", sales,
                        "/ProjectManager/project/sale/edit"));
            }
        }
        if (mode != null && (mode.isEmpty() || mode.equals(ProjectTypeEnum.SERVICE.getValue()))) {
            List<Project> services = srvProjectManager.getDaoProject().getMyProjectType(getUser().getId(),
                    ProjectTypeEnum.SERVICE.getId());

            if (mode.isEmpty()) {
                content.put("project_service_size_id", "#project-service-size");
                content.put("project_service_id", "#project-service");
                content.putAll(getMyProjectInfo("project_service_size", "project_service_info", "Service", services,
                        "/ProjectManager/project/service/edit"));
            } else {
                content.put("project_size_id", "#project-service-size");
                content.put("project_id", "#project-service");
                content.putAll(getMyProjectInfo("project_size", "project_info", "Service", services,
                        "/ProjectManager/project/service/edit"));
            }
        }
        if (mode != null && (mode.isEmpty() || mode.equals(CompanyEnum.MTS.name()))) {
            if (mode.isEmpty()) {
                List<Project> mts = srvProjectManager.getDaoProject().getMyProjectCompany(getUser().getId(),
                        CompanyEnum.MTS.name());

                content.put("project_mts_size_id", "#project-mts-size");
                content.put("project_mts_id", "#project-mts");
                content.putAll(getMyProjectInfo("project_mts_size", "project_mts_info", "MGPS Anodes", mts,
                        "/ProjectManager/project/mts/edit"));
            } else {
                List<Project> sales = srvProjectManager.getDaoProject().getMyProjectType(getUser().getId(),
                        ProjectTypeEnum.SALE.getId());
                List<Project> mts = srvProjectManager.getDaoProject().getMyProjectCompany(getUser().getId(),
                        CompanyEnum.MTS.name());

                content.put("project_sale_size_id", "#project-sale-size");
                content.put("project_sale_id", "#project-sale");
                content.putAll(getMyProjectInfo("project_sale_size", "project_sale_info", "Sale", sales,
                        "/ProjectManager/project/sale/edit"));
                content.put("project_mts_size_id", "#project-mts-size");
                content.put("project_mts_id", "#project-mts");
                content.putAll(getMyProjectInfo("project_mts_size", "project_mts_info", "MGPS Anodes", mts,
                        "/ProjectManager/project/mts/edit"));
            }
        }

        return content;
    }

    @RequestMapping(value = "/snapshot")
    public String Snapshot(Model model) {
        this.setTitle("Projects");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/ViewProject.jsp");
        setHeaderInfo(model);

        return "index";
    }

    @RequestMapping(value = "/sale")
    public String Sale(Model model) {
        this.setTitle("Projects-Sale");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("project_id", -1);
        model.addAttribute("project_reference", "New Project - REF: " + getUser().getProject_reference());
        setProjectType(ProjectTypeEnum.SALE.getValue());

        return "index";
    }

    @RequestMapping(value = "/sale/edit")
    public String SaleEdit(Project p,
            Model model) {
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("project_id", p.getId());
        model.addAttribute("project_reference", "Edit Project - REF:" + p.getReference());

        return "index";
    }

    @RequestMapping(value = "/service")
    public String Service(Model model) {
        this.setTitle("Projects-Service");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("project_id", -1);
        model.addAttribute("project_reference", "New Project - REF:" + getUser().getProject_reference());
        setProjectType(ProjectTypeEnum.SERVICE.getValue());

        return "index";
    }

    @RequestMapping(value = "/service/edit")
    public String ServiceEdit(Project p,
            Model model) {
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("project_id", p.getId());
        model.addAttribute("project_reference", "Edit Project - REF:" + p.getReference());

        return "index";
    }

    @RequestMapping(value = "/mts")
    public String MTS(Model model) {
        this.setTitle("Projects-MGPS Anodes");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("project_id", -1);
        model.addAttribute("project_reference", "New Project - REF:" + getUser().getProject_reference());
        setProjectType(CompanyEnum.MTS.name());

        return "index";
    }

    @RequestMapping(value = "/mts/edit")
    public String MTSEdit(Project p,
            Model model) {
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("project_id", p.getId());
        model.addAttribute("project_reference", "Edit Project - REF:" + p.getReference());

        return "index";
    }

    @RequestMapping(value = {"/save", "/sale/save", "/service/save", "/mts/save"})
    public @ResponseBody
    String saveProject(Project p, Integer offset, Integer size) {
        Collabs user = srvProjectManager.getDaoCollab().getById(getUser().getId());

        if (user != null) {
            Map<String, Object> content = new HashMap<>();

            p.setReference(getUser().getProject_reference());
            p.setStatus(ProjectStatusEnum.CREATE.getId());
            p.setType(getProjectType(getProjectType()));
            p.setCreateDate(new Date());
            p.setCollab(getUser().getId());
            p = srvProjectManager.getDaoProject().add(p);
            user = srvProjectManager.getDaoCollab().updateProjectId(user.getId());
            getUser().setProject_reference(user.getProjectId() + "/" + user.getProjectPrefix());

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start")),
                    getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("reference", createSearchReference(srvProjectManager, offset, size));
            content.put("project_header", projectHeader);
            content.put("project_body", projectBody);
            content.put("project_footer", projectFooter);
            content.put("project_reference", "New Project - REF:" + getUser().getProject_reference());
            content.put("project_type", getProjectType());
            content.putAll(getMenuInfo(getProjectType()));

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = {"/search", "/sale/search", "/service/search", "/mts/search", "/packing-list/search"})
    public @ResponseBody
    String searchProject(Project p, Integer offset, Integer size, String mode, Model model) {
        mode = (Strings.isNullOrEmpty(mode)) ? "edit" : mode;

        return searchProject(srvProjectManager, p, offset, size, mode, model);
    }

    @RequestMapping(value = {"/refresh", "/sale/refresh", "/service/refresh", "/mts/refresh"})
    public @ResponseBody
    String refreshSearchContent(Integer offset, Integer size) {
        return refreshSearchContent(srvProjectManager, offset, size);
    }

    @RequestMapping(value = {"/project-edit", "/sale/project-edit", "/service/project-edit", "/mts/project-edit"})
    public @ResponseBody
    String editProject(Project p, Integer offset, Integer size) {
        Project dbPrj = srvProjectManager.getDaoProject().getById(p.getId());

        if (dbPrj != null) {
            Map<String, String> content = new HashMap<>();

            if (!p.getCompany().equals("-1")) {
                dbPrj.setCompany(p.getCompany());
            }
            if (!p.getVessel().equals(-1l)) {
                dbPrj.setVessel(p.getVessel());
            }
            if (!p.getCustomer().equals("-1")) {
                dbPrj.setCustomer(p.getCustomer());
            }
            if (!p.getContact().equals(-1l)) {
                dbPrj.setContact(p.getContact());
            }
            srvProjectManager.getDaoProject().edit(dbPrj);

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.
                    asList("Processed",
                            "Start",
                            "Start",
                            "Start",
                            "Start",
                            "Start",
                            "Start")),
                    getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = {"/remove", "/sale/remove", "/service/remove", "/mts/remove"})
    public @ResponseBody
    String removeProject(Project p, Integer offset, Integer size) {
        Map<String, String> content = new HashMap<>();

        srvProjectManager.getDaoProject().delete(p.getId());

        content.put("reference", createSearchReference(srvProjectManager, offset, size));
        content.put("rows", "");

        return new Gson().toJson(content);
    }

    @RequestMapping(value = {"/createpdf", "/sale/createpdf", "/service/createpdf", "/mts/createpdf"})
    public @ResponseBody
    String createProjectPDF(Project p, Integer offset, Integer size) throws JRException {
        p = srvProjectManager.getDaoProject().getById(p.getId());

        if (p != null) {
            Map<String, String> content = new HashMap<>();
            Collabs user = srvProjectManager.getDaoCollab().getById(p.getCollab());
            Vessel vess = srvProjectManager.getDaoVessel().getById(p.getVessel());

            JasperReport.createProjectReport(p, getProjectStatusName(p.getStatus()), getProjectTypeName(p.getType()),
                    (user != null) ? user.getSurname() + ", " + user.getName() : "",
                    (vess != null) ? vess.getName() : "");

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
                    "Processed",
                    "Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start")),
                    getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";

    }

    @RequestMapping(value = {"/printpdf", "/sale/printpdf", "/service/printpdf", "/mts/printpdf"})
    public @ResponseBody
    String printProjectPDF(Project p, Integer offset, Integer size) throws IOException, FileNotFoundException,
            PrintException {
        Project dbPrj = srvProjectManager.getDaoProject().getById(p.getId());

        if (dbPrj != null) {
            Map<String, String> content = new HashMap<>();
            String strPath = JasperReport.getPATH_PROJECT() + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                    + "/" + dbPrj.getReference().replace("/", "_") + ".pdf";

            Printing.printing(strPath);

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
                    "Start",
                    "Processed",
                    "Start",
                    "Start",
                    "Start",
                    "Start")),
                    getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = {"/createxls", "/sale/createxls", "/service/createxls", "/mts/createxls"})
    public @ResponseBody
    String createProjectXLS(ProjectController p) {
        return "";
    }

    @RequestMapping(value = {"/printxls", "/sale/printxls", "/service/printxls", "/mts/printxls"})
    public @ResponseBody
    String printProjectXLS(ProjectController p) {
        return "";
    }

    @RequestMapping(value = {"/sendemail", "/sale/sendemail", "/service/sendemail", "/mts/sendemail"})
    public @ResponseBody
    String sendProjectEmail(ProjectController p) {
        return "";
    }

    @RequestMapping(value = {"/content", "/sale/content", "/service/content", "/mts/content"})
    public @ResponseBody
    String getContent(Project p, Integer offset, Integer size) {
        return createContent(p, offset, size);
    }

    @RequestMapping(value = "/view")
    public @ResponseBody
    String getView(Project p, Integer offset, Integer size) {
        if (p != null) {
            Map<String, String> content = new HashMap<>();
            String projectHeader = createProjectHeader(getModeView());
            Object[] projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
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

    @RequestMapping(value = "/edit")
    public @ResponseBody
    String getEdit(Project p, Integer offset, Integer size) {
        if (p != null) {
            Map<String, String> content = new HashMap<>();
            String projectHeader = createProjectHeader(getModeView());
            Object[] projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
                    "Start",
                    "Start")),
                    getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = {"/menu-info", "/sale/menu-info", "/service/menu-info", "/mts/menu-info"})
    public @ResponseBody
    String getProjectCreate() {
        Map<String, Object> content = getMenuInfo("");

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/id")
    public @ResponseBody
    String getProjectById(Project p, Integer offset, Integer size) {
        if (p != null) {
            Map<String, String> content = new HashMap<>();
            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
                    "Start",
                    "Start",
                    "Start",
                    "Start",
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
