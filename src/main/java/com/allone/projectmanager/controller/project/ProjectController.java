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

    private String createContent(ProjectDetail pd) {
        Map<String, String> content = new HashMap<>();

        content.put("company", createSearchCompany());
        content.put("type", createSearchType());
        content.put("vessel", createSearchVessel(srvProjectManager, null));
        content.put("customer", createSearchCustomer(srvProjectManager, null));
        content.put("contact", createSearchContact(srvProjectManager, null));

        if (pd != null && !pd.getId().equals(-1l)) {
            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList(
                                                     "Start", "Start", "Start", "Start", "Start", "Start", "Start")),
                                                     getModeEdit(), 0, 1);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";
            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);
        }

        return new Gson().toJson(content);
    }

    private Map<String, Object> getMenuInfo() {
        Map<String, Object> content = new HashMap<>();
        Long create = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.CREATE.toString());
        Long bill = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.PROJECT_BILL.toString());
        Long quota = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.REQUEST_QUOTATION.
             toString());
        Long purchase = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.PURCHASE_ORDER.
             toString());
        Long work = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.WORK_ORDER.toString());
        Long ack = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.ACK_ORDER.toString());
        Long packing = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.PACKING_LIST.toString());
        Long delivery = srvProjectManager.getDaoProjectDetail().
             countByStatus(ProjectStatusEnum.DELIVERY_NOTE.toString());
        Long shipping = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.SHIPPING_INVOICE.
             toString());
        Long invoice = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.INVOICE.toString());
        Long box = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.BOX_MARKING.toString());
        Long credit = srvProjectManager.getDaoProjectDetail().countByStatus(ProjectStatusEnum.CREDIT_NOTE.toString());

        content.put("project_new_size", "New[" + create + "]");
        content.put("project_bill_size", "Bill of Material[" + bill + "]");
        content.put("project_quota_size", "Request for Quotation[" + quota + "]");
        content.put("project_purchase_size", "Purchase Order[" + purchase + "]");
        content.put("project_work_size", "Work Order[" + work + "]");
        content.put("project_ack_size", "Order Acknowledge[" + ack + "]");
        content.put("project_packing_size", "Packing List[" + packing + "]");
        content.put("project_delivery_size", "Delivery Note[" + delivery + "]");
        content.put("project_shipping_size", "Shipping Invoice[" + shipping + "]");
        content.put("project_invoice_size", "Invoice[" + invoice + "]");
        content.put("project_box_size", "Box Markings[" + box + "]");
        content.put("project_credit_size", "Credit Note[" + credit + "]");

        return content;
    }

    @RequestMapping(value = "/snapshot")
    public String Snapshot(Model model) {
        this.setTitle("Project");
        this.setHeader(null);
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent(null);
        setHeaderInfo(model);

        return "index";
    }

    @RequestMapping(value = "/new")
    public String NewProject(Model model) {
        Date expired = new Date(new Date().getTime() + getUser().getProject_expired() * 86400000l);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/YYYY");

        this.setTitle("Project - New");
        this.setHeader(null);
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("pd_id", -1);
        model.addAttribute("project_reference", "New Project - REF:" + getUser().getProject_reference());
        model.addAttribute("expired", format.format(expired));
        model.addAttribute("project_button_value", "Save");
        model.addAttribute("project_button_id", "project-save");
        model.addAttribute("project_button_action", "saveProject()");

        return "index";
    }

    @RequestMapping(value = "/edit-form")
    public String EditProject(Project p, Long pdId, Model model) {
        this.setTitle("Projects - Edit");
        this.setHeader(null);
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/NewProject.jsp");
        setHeaderInfo(model);
        model.addAttribute("pd_id", pdId);
        model.addAttribute("project_reference", "Edit Project - REF:" + p.getReference());
        model.addAttribute("project_button_value", "Edit");
        model.addAttribute("project_button_id", "project-edit");
        model.addAttribute("project_button_action", "editRow()");

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
            pd.setReference(p.getReference() + "/1");
            pd = srvProjectManager.getDaoProjectDetail().add(pd);

            user = srvProjectManager.getDaoCollab().updateProjectId(user.getId());
            getUser().setProject_reference((user.getProjectId() + 1) + "/" + user.getProjectPrefix());

            String projectHeader = createProjectHeader(getModeEdit());
            Object[] projectBody = createProjectBody(srvProjectManager, pd, new ArrayList<String>(Arrays.asList(
                                                     "Start", "Start", "Start", "Start", "Start", "Start", "Start")),
                                                     getModeEdit(), offset, size);
            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody);
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
    String editProject(ProjectDetail pd) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            ProjectDetail dbpd = srvProjectManager.getDaoProjectDetail().getById(pd.getId());

            if (dbpd != null) {
                if (pd.getType() != null) {
                    dbpd.setType(pd.getType());
                }
                if (pd.getExpired() != null) {
                    dbpd.setExpired(pd.getExpired());
                }
                if (pd.getCustomer() != null) {
                    dbpd.setCustomer(pd.getCustomer());
                }
                if (pd.getVessel() != null) {
                    dbpd.setVessel(pd.getVessel());
                }
                if (pd.getCompany() != null) {
                    dbpd.setCompany(pd.getCompany());
                }
                if (pd.getContact() != null) {
                    dbpd.setContact(pd.getContact());
                }
                srvProjectManager.getDaoProjectDetail().edit(dbpd);

                String projectHeader = createProjectHeader(getModeEdit());
                Object[] projectBody = createProjectBody(srvProjectManager, dbpd, new ArrayList<String>(Arrays.asList(
                                                         "Processed", "Start", "Start", "Start", "Start", "Start",
                                                         "Start")), getModeEdit(), 0, 1);
                String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

                content.put("project_header", projectHeader);
                content.put("project_body", projectBody[1].toString());
                content.put("project_footer", projectFooter);

                return new Gson().toJson(content);
            }
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
    String getContent(ProjectDetail pd) {
        return createContent(pd);
    }

    @RequestMapping(value = {"/filter-vessel"})
    public @ResponseBody
    String filterVessel(Long vessel) {
        Map<String, String> content = new HashMap<>();

        if (vessel != null) {
            String response = "";
            Vessel v = srvProjectManager.getDaoVessel().getById(vessel);
            Company co = (v != null) ? srvProjectManager.getDaoCompany().getByTypeName(CompanyTypeEnum.CUSTOMER, v.
                                                                                       getCompany()) : null;
            List<Contact> contacts = (v != null) ? srvProjectManager.getDaoContact().getByVessel(v.getId()) : null;

            if (co != null) {
                response += "<option value='" + co.getName() + "'>" + co.getName() + "</option>";
                content.put("customer", response);
            } else if (vessel.equals(-1l)) {
                content.put("customer", createSearchCustomer(srvProjectManager, null));
            } else {
                content.put("customer", response);
            }
            response = "";
            if (contacts != null && contacts.isEmpty() == false) {
                for (Iterator<Contact> it = contacts.iterator(); it.hasNext();) {
                    Contact c = it.next();

                    response += "<option value='" + c.getId() + "'>" + c.getName() + "</option>";
                    content.put("contact", response);
                }
            } else if (vessel.equals(-1l)) {
                content.put("contact", createSearchContact(srvProjectManager, null));
            } else {
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

    @RequestMapping(value = "/set-project")
    public @ResponseBody
    String getSetProject(ProjectDetail pd) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            pd = srvProjectManager.getDaoProjectDetail().getById(pd.getId());

            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

            content.put("project_id", pd.getProject().toString());
            content.put("project_reference", p.getReference());
            content.put("pdId", pd.getId().toString());

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

    @RequestMapping(value = "/lst-project")
    public @ResponseBody
    String lstProjects(ProjectDetail _pd) {
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByStatus(_pd.getStatus(), 0,
                                                                                      Integer.MAX_VALUE);
        String response = "";

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

                response += "<input type='radio' id='" + pd.getId() + "' name='radio-project' value='" + pd.getId() +
                "'><label for='" + pd.getId() + "' class='radio-label'>" + p.getReference() + "[" + pd.getType() +
                "]</label><br>";
            }
        }

        return response;
    }

    @RequestMapping(value = "/lst-status")
    public @ResponseBody
    String lstStatus() {
        String response = "";

        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
            response += "<input type='radio' id='" + status.toString() + "' name='radio-project' value='" + status.
            toString() + "'><label for='" + status.toString() + "' class='radio-label'>" + status.toString() +
            "</label><br>";
        }

        return response;
    }
    
    @RequestMapping(value = "/lst-customer")
    public @ResponseBody
    String lstCustomer() {
        return createSearchCustomer(srvProjectManager, null);
    }
}
