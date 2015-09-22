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
import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
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
import java.util.Iterator;
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

    private static final Logger logger = Logger.getLogger(Root.class.getName());

    @Autowired ProjectManagerService srvProjectManager;

    public void setSrvProjectManager(ProjectManagerService srvProjectManager) {
        this.srvProjectManager = srvProjectManager;
        this.srvProjectManager.loadPropertyValues();
    }

    public ProjectManagerService getSrvProjectManager() {
        return srvProjectManager;
    }

    private String createContent(ProjectDetail pd, Integer offset, Integer size) {
        Map<String, String> content = new HashMap<>();

        content = new Gson().fromJson(refreshSearchContent(srvProjectManager, offset, size), content.getClass());
        if (pd != null) {
            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList(
                                                     "Start", "Start", "Start", "Start", "Start", "Start", "Start")),
                                                     getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);
            content.put("company", createSearchCompany());
            content.put("type", createSearchType());
            content.put("vessel", createSearchVessel(srvProjectManager));
            content.put("customer", createSearchCustomer(srvProjectManager));
            content.put("contact", createSearchContact(srvProjectManager));
        } else {
            content.put("project_header", null);
            content.put("project_body", null);
            content.put("project_footer", null);
        }

        return new Gson().toJson(content);
    }

    private Map<String, Object> getMyProjectInfo(String keySize, String keyInfo, List<ProjectDetail> info, String url) {
        Map<String, Object> content = new HashMap<>();

        content.put(keySize, "Edit[0]");
        content.put(keyInfo, "");

        if (info != null && !info.isEmpty()) {
            String response = "";
            Long project = 0l;

            content.put(keySize, "Edit[" + info.size() + "]");

            for (ProjectDetail pd : info) {
                if (!pd.getProject().equals(project)) {
                    Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

                    if (p != null) {
                        response +=
                        "<li onclick=\"window.location.href = '" + url + "?id=" + pd.getId() + "';\">" +
                        p.getReference() + "</li>";
                    }
                    project = pd.getProject();
                }
            }
            content.put(keyInfo, response);
        }

        return content;
    }

    private Map<String, Object> getMenuInfo() {
        Map<String, Object> content = new HashMap<>();
        List<ProjectDetail> pd = srvProjectManager.getDaoProjectDetail().getByCreator(getUser().getId(),
                                                                                      ProjectStatusEnum.CREATE);

        content.put("project_edit_size_id", "#project-edit-size");
        content.put("project_edit_info_id", "#project-edit");
        content.putAll(getMyProjectInfo("project_edit_size", "project_edit_info", pd, "/ProjectManager/project/edit"));

        return content;
    }

    @RequestMapping(value = "/snapshot")
    public String Snapshot(Model model) {
        this.setTitle("Project");
        this.setHeader("header.jsp");
        this.setSide_bar("../project/sidebar.jsp");
//        this.setContent("../project/ViewProject.jsp");
        setHeaderInfo(model);

        return "index";
    }

    @RequestMapping(value = "/new")
    public String NewProject(Model model) {
        Date expired = new Date(new Date().getTime() + getUser().getProject_expired() * 86400000l);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY");

        this.setTitle("Project - New");
        this.setHeader("header.jsp");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("project_reference", "New Project - REF:" + getUser().getProject_reference());
        model.addAttribute("expired", format.format(expired));

        return "index";
    }

    @RequestMapping(value = "/edit-form")
    public String EditProject(Project p, Model model) {
        this.setTitle("Projects-Edit Project");
        this.setHeader("header.jsp");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("project_id", p.getId());
        model.addAttribute("project_reference", "Edit Project - REF:" + p.getReference());

        return "index";
    }

    @RequestMapping(value = {"/save"})
    public @ResponseBody
    String saveProject(ProjectDetail pd, Integer offset, Integer size) {
        Collabs user = srvProjectManager.getDaoCollab().getById(getUser().getId());

        if (user != null) {
            Map<String, Object> content = new HashMap<>();
            Project p = srvProjectManager.getDaoProject().add(new Project.Builder().setReference(getUser().
                    getProject_reference()).setStatus(ProjectStatusEnum.CREATE.toString()).build());

            pd.setProject(p.getId());
            pd.setStatus(ProjectStatusEnum.CREATE.toString());
            pd.setCreator(user.getId());
            pd.setCreated(new Date());
            srvProjectManager.getDaoProjectDetail().add(pd);

            user = srvProjectManager.getDaoCollab().updateProjectId(user.getId());
            getUser().setProject_reference(user.getProjectId() + "/" + user.getProjectPrefix());

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList(
                                                     "Start", "Start", "Start", "Start", "Start", "Start", "Start")),
                                                     getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("reference", createSearchReference(srvProjectManager, offset, size));
            content.put("project_header", projectHeader);
            content.put("project_body", projectBody);
            content.put("project_footer", projectFooter);
            content.put("project_reference", "New Project - REF:" + getUser().getProject_reference());
            content.put("project_type", getProjectType());
            content.putAll(getMenuInfo());

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = {"/search"})
    public @ResponseBody
    String searchProject(ProjectDetail pd, Integer offset, Integer size, String mode, Model model) {
        mode = (Strings.isNullOrEmpty(mode)) ? "edit" : mode;

        return searchProject(srvProjectManager, pd, offset, size, mode);
    }

    @RequestMapping(value = {"/refresh"})
    public @ResponseBody
    String refreshSearchContent(Integer offset, Integer size) {
        return refreshSearchContent(srvProjectManager, offset, size);
    }

    @RequestMapping(value = {"/project-edit"})
    public @ResponseBody
    String editProject(ProjectDetail pd, Integer offset, Integer size) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();

            srvProjectManager.getDaoProjectDetail().edit(pd);

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList(
                                                     "Processed", "Start", "Start", "Start", "Start", "Start",
                                                     "Start")), getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = {"/remove"})
    public @ResponseBody
    String removeProject(Project p, Integer offset, Integer size) {
        Map<String, String> content = new HashMap<>();

        srvProjectManager.getDaoProject().delete(p.getId());

        content.put("reference", createSearchReference(srvProjectManager, offset, size));
        content.put("rows", "");

        return new Gson().toJson(content);
    }

    @RequestMapping(value = {"/createpdf"})
    public @ResponseBody
    String createProjectPDF(ProjectDetail pd, Integer offset, Integer size) throws JRException {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            Collabs user = srvProjectManager.getDaoCollabs().getById(pd.getCreator());
            Vessel vess = srvProjectManager.getDaoVessel().getById(pd.getVessel());
            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());
            Company cust = srvProjectManager.getDaoCompany().getByTypeName(CompanyTypeEnum.CUSTOMER, pd.
                                                                           getCustomer());

            JasperReport.createProjectReport(p, pd, getProjectStatusName(pd.getStatus()), getProjectTypeName(pd.
                                             getType()), (user != null) ? user.getSurname() + ", " + user.getName() : "",
                                             (vess != null) ? vess.getName() : "", (cust != null) ? cust.getName() : "");

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList("Start",
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

    @RequestMapping(value = {"/printpdf"})
    public @ResponseBody
    String printProjectPDF(ProjectDetail pd, Integer offset, Integer size) throws IOException, FileNotFoundException,
                                                                                  PrintException {
        Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

        if (p != null) {
            Map<String, String> content = new HashMap<>();
            String strPath = JasperReport.getPATH_PROJECT() + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +
                   "/" + p.getReference().replace("/", "_") + ".pdf";

            Printing.printing(strPath);

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList("Start",
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

    @RequestMapping(value = {"/createxls"})
    public @ResponseBody
    String createProjectXLS(ProjectDetail pd) {
        return "";
    }

    @RequestMapping(value = {"/printxls"})
    public @ResponseBody
    String printProjectXLS(ProjectDetail pd) {
        return "";
    }

    @RequestMapping(value = {"/sendemail"})
    public @ResponseBody
    String sendProjectEmail(ProjectDetail p) {
        return "";
    }

    @RequestMapping(value = {"/content"})
    public @ResponseBody
    String getContent(ProjectDetail pd, Integer offset, Integer size) {
        return createContent(pd, offset, size);
    }

    @RequestMapping(value = {"/filter-vessel"})
    public @ResponseBody
    String filterVessel(Long vessel) {
        Map<String, String> content = new HashMap<>();

        if (vessel != null) {
            Vessel v = srvProjectManager.getDaoVessel().getById(vessel);
            String response = "";

            if (v != null) {
                Company co = srvProjectManager.getDaoCompany().getByTypeName(CompanyTypeEnum.CUSTOMER, v.getCompany());

                if (co != null) {
                    response += "<option value=\"" + co.getName() + "\">" + co.getName() + "</option>";
                } else {
                    response = "<option value=\"none\" selected=\"selected\">Select</option>";
                }
                content.put("customer", response);

                List<Contact> contacts = srvProjectManager.getDaoContact().getByVessel(v.getId());

                response = "";
                if (contacts != null && contacts.isEmpty() == false) {
                    for (Iterator<Contact> it = contacts.iterator(); it.hasNext();) {
                        Contact c = it.next();

                        response += "<option value=\"" + c.getId() + "\">" + c.getName() + "</option>";
                    }
                } else {
                    response = "<option value=\"-1\" selected=\"selected\">Select</option>";
                }
                content.put("contact", response);
            }
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/view")
    public @ResponseBody
    String getView(ProjectDetail pd, Integer offset, Integer size) {
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

    @RequestMapping(value = "/edit")
    public @ResponseBody
    String getEdit(Long pdId, Integer offset, Integer size) {
        if (pdId != null) {
            ProjectDetail pd = new ProjectDetail();
            pd.setId(pdId);
            Map<String, String> content = new HashMap<>();
            String projectHeader = createProjectHeader(getModeView());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList("Start",
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

    @RequestMapping(value = {"/menu-info"})
    public @ResponseBody
    String getProjectCreate() {
        Map<String, Object> content = getMenuInfo();

        String project = new Gson().toJson(content);

        return project;
    }

    @RequestMapping(value = "/id")
    public @ResponseBody
    String getProjectById(ProjectDetail pd, Integer offset, Integer size) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList("Start",
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
    
    @RequestMapping(value = "/lst-edit-project")
    public @ResponseBody String lstEditProject(ProjectDetail _pd) {
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByStatus(_pd.getStatus(), 0, Integer.MAX_VALUE);
        String response = "";
        
        if (pds!= null && pds.size() > 0) {
            for (ProjectDetail pd : pds) {
                Project p = srvProjectManager.getDaoProject().getById(pd.getProject());
                
                response += "<label for='name' style='display:block' onclick='editProject(" + pd.getProject() + ", " +
                p.getReference() + ")'>" + p.getReference() + "</label>\n";
            }
        }
        
        return response;
    }
}
