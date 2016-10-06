/*
 * To change this license header, choose License Headers in ProjectController Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.model.User;
import com.allone.projectmanager.tools.JasperReport;
import com.allone.projectmanager.tools.Printing;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    private static final Logger logger = Logger.getLogger(ProjectController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    public void setSrvProjectManager(ProjectManagerService srvProjectManager) {
        this.srvProjectManager = srvProjectManager;
        this.srvProjectManager.loadPropertyValues();
    }

    public ProjectManagerService getSrvProjectManager() {
        return srvProjectManager;
    }

    private String createContent(Project p) {
        Map<String, String> content = new HashMap<>();

        content.put("company", fillSearchOwnCompany());
        content.put("type", fillSearchType());
        content.put("vessel", fillSearchVessel(srvProjectManager, null));
        content.put("customer", fillSearchCompany(srvProjectManager, null, CompanyTypeEnum.CUSTOMER));
        content.put("contact", fillSearchContact(srvProjectManager, null));

        if (p != null && !p.getId().equals(-1)) {
            List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());
            String projectBody = "";

            if (pds != null && !pds.isEmpty()) {
                for (ProjectDetail pd : pds) {
                    projectBody += createProjectRow(srvProjectManager, pd);
                }
            }
            content.put("header", createProjectHeader());
            content.put("body", projectBody);
        }

        return new Gson().toJson(content);
    }

    private Map<String, Object> getMenuInfo() {
        Map<String, Object> content = new HashMap<>();
        Long create = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.CREATE.toString());
        Long bill = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString());
        Long quota = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.REQUEST_QUOTATION.toString());
        Long purchase = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.PURCHASE_ORDER.toString());
        Long work = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.WORK_ORDER.toString());
        Long ack = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.ACK_ORDER.toString());
        Long packing = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.PACKING_LIST.toString());
        Long delivery = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.DELIVERY_NOTE.toString());
        Long shipping = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.SHIPPING_INVOICE.toString());
        Long invoice = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.INVOICE.toString());
        Long box = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.BOX_MARKING.toString());
        Long credit = srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.CREDIT_NOTE.toString());

        content.put("project_new_size", "New[" + create + "]");
        content.put("project_bill_size", "Bill of materials or services[" + bill + "]");
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
    public String Snapshot(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                this.setTitle("Project");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/HistoryNewProject.jsp");
                setHeaderInfo(session, model);

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = "/new")
    public String NewProject(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                User user = getUser(session.getId());
                Date expired = new Date(new Date().getTime() + user.getProject_expired() * 86400000l);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");

                this.setTitle("Project - New");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/NewProject.jsp");
                setHeaderInfo(session, model);
                model.addAttribute("p_id", -1);
                model.addAttribute("project_reference", "New Project - REF:" + user.getProject_reference());
                model.addAttribute("expired", format.format(expired));
                model.addAttribute("button_save", "<input type='button' class='button alarm' id='save' onclick='saveProject()' value='Save' />\n");

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = "/edit-form")
    @SuppressWarnings("empty-statement")
    public String EditProject(HttpServletRequest request, Project p, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                this.setTitle("Projects - Edit");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/NewProject.jsp");
                setHeaderInfo(session, model);
                p = srvProjectManager.getDaoProject().getById(p.getId());
                List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());
                if (p != null && pds != null && !pds.isEmpty()) {
                    setUserProjectId(session.getId(), p.getId());
                    model.addAttribute("p_id", p.getId());
                    model.addAttribute("project_reference", "Edit Project - REF:" + p.getReference());
                    model.addAttribute("button_save", "<input type='button' class='button alarm' id='edit' onclick='getSubProject()' value='Edit' />\n");
                }

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = {"/save"})
    public @ResponseBody
    String saveProject(HttpServletRequest request, ProjectDetail pd, String dateExpired, Integer offset, Integer size, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                User user = getUser(session.getId());
                Collabs collab = srvProjectManager.getDaoCollabs().getById(user.getId());
                Vessel v = srvProjectManager.getDaoVessel().getById(pd.getVessel());

                if (collab != null) {
                    Map<String, Object> content = new HashMap<>();
                    Project p = srvProjectManager.getDaoProject().add(new Project.Builder().setReference(user.
                            getProject_reference()).setStatus(ProjectStatusEnum.CREATE.toString()).build());

                    pd.setProject(p.getId());
                    pd.setStatus(ProjectStatusEnum.CREATE.toString());
                    pd.setCreator(collab.getId());
                    pd.setCreated(new Date());
                    pd.setReference(p.getReference() + "/1");
                    pd.setVesselName((v != null) ? v.getName() : "");
                    if (!Strings.isNullOrEmpty(dateExpired)) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date parsedDate = formatter.parse(dateExpired);
                            pd.setExpired(parsedDate);
                        } catch (ParseException ex) {
                            logger.log(Level.SEVERE, null, ex);
                        }
                    }
                    pd = srvProjectManager.getDaoProjectDetail().add(pd);

                    if (pd != null) {
                        setUserProjectId(session.getId(), p.getId());
                        collab = srvProjectManager.getDaoCollabs().updateProjectId(user.getId());
                        user.setProject_reference((collab.getProjectId() + 1) + "/" + collab.getProjectPrefix());

                        String projectHeader = createProjectHeader();
                        Object[] projectBody = createProjectBody(srvProjectManager, pd, null, null, offset, size);

                        content.put("header", projectHeader);
                        content.put("body", projectBody[1]);
                        content.put("project_reference", "New Project - REF:" + user.getProject_reference());
                        content.put("project_type", getProjectType());
                        content.put("location", "http://localhost:8081/ProjectManager/project/history-new-project");
                        content.putAll(getMenuInfo());

                        return new Gson().toJson(content);
                    }
                }
            }
        }

        return "";
    }

    @RequestMapping(value = {"/search"})
    public @ResponseBody
    String searchProject(ProjectDetail p, String date_start, String date_end, Integer offset, Integer size) {
        try {
            Date start = !Strings.isNullOrEmpty(date_start) ? new SimpleDateFormat("dd/MM/yyyy").parse(date_start) : null;
            Date end = !Strings.isNullOrEmpty(date_end) ? new SimpleDateFormat("dd/MM/yyyy").parse(date_end) : null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            return searchProject(srvProjectManager, p, start != null ? formatter.format(start) : null, end != null ? formatter.format(end) : null,
                    offset, size);
        } catch (ParseException ex) {
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    @RequestMapping(value = {"/search-criteria"})
    public @ResponseBody
    String searchCriteria(String version) {
        return searchCriteria(srvProjectManager, version);
    }

    @RequestMapping(value = {"/remove"})
    public @ResponseBody
    String removeProject(Project p) {
        Map<String, String> content = new HashMap<>();

        Project dbp = srvProjectManager.getDaoProject().getById(p.getId());
        srvProjectManager.getDaoProject().delete(dbp);

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

            String projectHeader = createProjectHeader();
            Object[] projectBody = createProjectBody(srvProjectManager, pd, null, null, offset, size);

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());

            return new Gson().toJson(content);
        }

        return "";

    }

    @RequestMapping(
            value = {"/printpdf"})
    public @ResponseBody
    String printProjectPDF(ProjectDetail pd, Integer offset, Integer size) throws IOException, FileNotFoundException,
            PrintException {
        Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

        if (p != null) {
            Map<String, String> content = new HashMap<>();
            String strPath = JasperReport.getPATH_PROJECT() + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                    + "/" + p.getReference().replace("/", "_") + ".pdf";

            Printing.printing(strPath);

            String projectHeader = createProjectHeader();
            Object[] projectBody = createProjectBody(srvProjectManager, pd, null, null, offset, size);
//            String projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
//            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = {"/create-excel"})
    public @ResponseBody
    String createProjectXLS(Long pdId) {
        return ExportExcel(srvProjectManager, pdId);
    }

    @RequestMapping(value = {"/create-all-excel"})
    public @ResponseBody
    String createProjectsXLS(ProjectDetail p, String date_start, String date_end) {
        String response = "no found project details";

        try {
            Date start = !Strings.isNullOrEmpty(date_start) ? new SimpleDateFormat("dd/MM/yyyy").parse(date_start) : null;
            Date end = !Strings.isNullOrEmpty(date_end) ? new SimpleDateFormat("dd/MM/yyyy").parse(date_end) : null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            List<ProjectDetail> pds = getProjectsToExcel(srvProjectManager,
                    p,
                    start != null ? formatter.format(start) : null,
                    end != null ? formatter.format(end) : null);

            if (pds != null && !pds.isEmpty()) {
                for (ProjectDetail pd : pds) {
                    response = ExportExcel(srvProjectManager, pd.getId());
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
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
    String getContent(Project p) {
        return createContent(p);
    }

    @RequestMapping(value = {"/filter-customer"})
    public @ResponseBody
    String filterCustomer(String customer) {
        Map<String, String> content = new HashMap<>();

        if (!Strings.isNullOrEmpty(customer)) {
            String response = "";
            List<Vessel> vessels = (!Strings.isNullOrEmpty(customer))
                    ? ((!customer.equals("none"))
                    ? srvProjectManager.getDaoVessel().getByCompany(customer)
                    : null)
                    : null;
            List<Contact> contacts = (vessels != null && !vessels.isEmpty())
                    ? ((!customer.equals("none"))
                    ? srvProjectManager.getDaoContact()
                    .getByCompany(customer)
                    : null)
                    : null;

            if (vessels != null && vessels.isEmpty() == false) {
                for (Vessel vessel : vessels) {
                    response += "<option value='" + vessel.getId() + "'>" + vessel.getName() + "</option>";
                    content.put("vessel", response);
                }
            } else {
                content.put("vessel", "<option value='-1'>Select Vessel</option>");
            }
            response = "";
            if (contacts != null && contacts.isEmpty() == false) {
                for (Contact contact : contacts) {
                    response += "<option value='" + contact.getId() + "'>" + contact.getSurname() + " " + contact.getName() + "</option>";
                    content.put("contact", response);
                }
            } else {
                content.put("contact", "<option value='-1'>Select Contact</option>");
            }
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/view")
    public @ResponseBody
    String getView(ProjectDetail pd, Integer offset, Integer size) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            String projectHeader = createProjectHeader();
            Object[] projectBody = createProjectBody(srvProjectManager, pd, null, null, offset, size);

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());

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
            String projectHeader = createProjectHeader();
            Object[] projectBody = createProjectBody(srvProjectManager, pd, null, null, offset, size);

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());

            return new Gson().toJson(content);
        }

        return "";
    }

    @RequestMapping(value = "/lst-project")
    public @ResponseBody
    String lstProjects(HttpServletRequest request, Project prj) {
        String response = "";

        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                List<Project> prjs = srvProjectManager.getDaoProject().getByStatus(prj.getStatus(), 0, Integer.MAX_VALUE);

                if (prjs != null && !prjs.isEmpty()) {
                    String vessel = "";
                    Long userId = getUser(session.getId()).getId();

                    for (Project p : prjs) {
                        if (isLockProject(userId, p.getId())) {
                            continue;
                        }
                        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getLastByProject(p.getId());

                        if (pd != null) {
                            Vessel v = srvProjectManager.getDaoVessel().getById(pd.getVessel());

                            if (v != null) {
                                vessel = v.getName();
                            }
                        }
                        response += "<input type='radio' id='" + p.getId() + "' name='radio-project' value='" + p.getId()
                                + "'><label for='" + p.getId() + "' class='radio-label'>" + p.getReference() + "-" + vessel
                                + "</label><br>";
                    }
                }
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
                    toString() + "'><label for='" + status.toString() + "' class='radio-label'>" + status.toString()
                    + "</label><br>";
        }

        return response;
    }

    /**
     *
     * @param request
     * @param p
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit-project")
    public @ResponseBody
    String editProject(HttpServletRequest request, Project p, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                p = srvProjectManager.getDaoProject().getByReference(p.getReference());
                if (p != null) {
                    setUserProjectId(session.getId(), p.getId());
                    return "edit-form?id=" + p.getId();
                }
            }
        }

        return "";
    }

    @RequestMapping(value = "/lst-sub-project")
    public @ResponseBody
    String lstSubProjects(HttpServletRequest request, Project p) {
        String response = "";
        Map<String, Object> content = new HashMap<>();

        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());

                if (pds != null && !pds.isEmpty()) {
                    String vessel = "";

                    if (pds.size() > 1) {
                        for (ProjectDetail pd : pds) {
                            Vessel v = srvProjectManager.getDaoVessel().getById(pd.getVessel());

                            if (v != null) {
                                vessel = v.getName();
                            }
                            response += "<input type='radio' id='" + pd.getId() + "' name='radio-sub-project' value='" + pd.getId()
                                    + "'><label for='" + pd.getId() + "' class='radio-label'>" + pd.getReference() + "-" + vessel
                                    + "</label><br>";
                        }
                        content.put("value", response);
                    } else {
                        content.put("value", pds.get(0).getId());
                    }

                    content.put("count", pds.size());
                }
            }
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = {"/edit-sub-project"})
    public @ResponseBody
    String editSubProject(ProjectDetail pd, String dateExpired) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            ProjectDetail dbpd = srvProjectManager.getDaoProjectDetail().getById(pd.getId());

            if (dbpd != null) {
                if (pd.getType() != null) {
                    dbpd.setType(pd.getType());
                }
                if (!Strings.isNullOrEmpty(dateExpired)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date parsedDate = formatter.parse(dateExpired);
                        dbpd.setExpired(parsedDate);
                    } catch (ParseException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
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

                content.put("header", createProjectHeader());
                content.put("body", createProjectRow(srvProjectManager, dbpd));
                content.put("location", "http://localhost:8081/ProjectManager/project/history-new-project");

                return new Gson().toJson(content);
            }
        }

        return "";
    }
}
