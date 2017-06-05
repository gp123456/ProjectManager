/*
 * To change this license header, choose License Headers in ProjectController Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.User;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
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
                projectBody = pds.stream().map((pd) -> createProjectRow(srvProjectManager, pd)).reduce(projectBody, String::concat);
            }
            content.put("header", createProjectHeader());
            content.put("body", projectBody);
        }

        return new Gson().toJson(content);
    }

    private Map<String, Object> getMenuInfo() {
        Map<String, Object> content = new HashMap<>();

        content.put("sizeNew", "New["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.CREATE.toString()) + "]");
        content.put("sizeBillMaterialService", "Bill of materials or services["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString()) + "]");
        content.put("sizeQuotation", "Quotation["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.QUOTATION.toString()) + "]");
        content.put("sizePurchase", "Purchase Order["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.PURCHASE_ORDER.toString()) + "]");
        content.put("sizeWorkOrder", "Work Order["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.WORK_ORDER.toString()) + "]");
        content.put("sizeOrderAcknowledge", "Order Acknowledge["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.ACK_ORDER.toString()) + "]");
        content.put("sizeForwardingDocs", "Forwarding Documents["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.FORWARDING_DOCUMENTS.toString()) + "]");
        content.put("sizeInvoice", "Invoice["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.INVOICE.toString()) + "]");
        content.put("sizeCreditNote", "Credit Note["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.CREDIT_NOTE.toString()) + "]");
        content.put("sizeFinal", "Final["
                + srvProjectManager.getDaoProject().countByStatus(ProjectStatusEnum.FINAL.toString()) + "]");

        return content;
    }

    private void downloadExcel(final HttpServletRequest request, final HttpServletResponse response, String xlsFile) {
        File file = new File("c:\\ProjectManager\\" + xlsFile);

        try {
            if (request != null) {
                HttpSession session = request.getSession();

                if (session != null) {
                    InputStream in = new FileInputStream(file);
                    try (OutputStream output = response.getOutputStream()) {
                        response.reset();
                        response.setContentType("application/vnd.ms-excel");
                        response.setContentLength((int) (file.length()));
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
                        IOUtils.copyLarge(in, output);
                        output.flush();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QuotationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QuotationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/snapshot")
    public String Snapshot(HttpServletRequest request, Model model) {
        try {
            HttpSession session = request.getSession();

            if (session != null) {
                setUser(srvProjectManager.getUser());
                this.setTitle("Project");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/HistoryNewProject.jsp");
                setHeaderInfo(model);

                return "index";
            }
        } catch (StackOverflowError e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());

            return "login";
        }

        return "";
    }

    @RequestMapping(value = "/new")
    public String NewProject(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                User user = srvProjectManager.getUser();
                
                setUser(user);
                Date expired = new Date(new Date().getTime() + user.getProject_expired() * 86400000l);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");

                this.setTitle("Project - New");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/NewProject.jsp");
                setHeaderInfo(model);
                model.addAttribute("p_id", -1);
                model.addAttribute("project_reference", "New Project - REF:" + user.getProject_reference());
                model.addAttribute("expired", format.format(expired));
                model.addAttribute("button_save", "<input type='button' class='button alarm' id='save' onclick='saveProject(1)' value='Save' />\n");

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
                setUser(srvProjectManager.getUser());
                this.setTitle("Projects - Edit");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/NewProject.jsp");
                setHeaderInfo(model);
                p = srvProjectManager.getDaoProject().getById(p.getId());
                if (p != null) {
                    List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());

                    if (pds != null && !pds.isEmpty()) {
                        model.addAttribute("p_id", p.getId());
                        model.addAttribute("project_reference", "Edit Project - REF:" + p.getReference());
                        model.addAttribute("button_save", "<input type='button' class='button alarm' id='edit' onclick='getSubProject()' value='Edit' />\n");
                    }
                }

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = {"/save"})
    public @ResponseBody
    String saveProject(HttpServletRequest request,
            ProjectDetail pd,
            String dateExpired,
            Boolean searchOpenExist,
            Integer offset,
            Integer size,
            Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                Map<String, Object> content = new HashMap<>();

                if (searchOpenExist.equals(Boolean.TRUE)) {
                    String reference = srvProjectManager.getDaoProjectDetail().getOpenExist(pd.getType(), pd.getVessel(), pd.getCompany());

                    if (!Strings.isNullOrEmpty(reference)) {
                        content.put("exist", "Found open subproject with same type, vessel and company:" + reference);

                        return new Gson().toJson(content);
                    }
                }
                User user = srvProjectManager.getUser();
                Collabs collab = srvProjectManager.getDaoCollabs().getById(user.getId());
                Vessel v = srvProjectManager.getDaoVessel().getById(pd.getVessel());
                Contact c = srvProjectManager.getDaoContact().getById(pd.getContact());

                if (collab != null) {
                    Project p = srvProjectManager.getDaoProject().add(new Project.Builder().setReference(user.
                            getProject_reference()).setStatus(ProjectStatusEnum.CREATE.toString()).build());

                    pd.setProject(p.getId());
                    pd.setStatus(ProjectStatusEnum.CREATE.toString());
                    pd.setCreator(collab.getId());
                    pd.setCreated(new Date());
                    pd.setReference(p.getReference() + "/1");
                    pd.setContact((c != null) ? c.getId() : 0);
                    pd.setVessel((v != null) ? v.getId() : 0);
                    pd.setVesselName((v != null) ? v.getName() : "");
                    if (!Strings.isNullOrEmpty(dateExpired)) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date parsedDate = formatter.parse(dateExpired);
                            pd.setExpiredCreate(parsedDate);
                            pd.setExpired(parsedDate);
                        } catch (ParseException ex) {
                            logger.log(Level.SEVERE, null, ex);
                        }
                    }

                    pd = srvProjectManager.getDaoProjectDetail().add(pd);

                    if (pd != null) {
                        collab = srvProjectManager.getDaoCollabs().updateProjectId(user.getId());
                        user.setProject_reference((collab.getProjectId() + 1) + "/" + collab.getProjectPrefix());

                        String projectHeader = createProjectHeader();
                        Object[] projectBody = createProjectBody(srvProjectManager, pd, null, null, offset, size);

                        content.put("header", projectHeader);
                        content.put("body", projectBody[1]);
                        content.put("projectReference", "New Project - REF:" + user.getProject_reference());
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

    @RequestMapping(value = {"/create-excel"})
    public void createProjectXLS(Long pdId, final HttpServletRequest request, final HttpServletResponse response) {
        downloadExcel(request, response, ExportExcel(srvProjectManager, pdId));
    }

    @RequestMapping(value = {"/create-all-excel"})
    public @ResponseBody
    String createProjectsXLS(ProjectDetail p, String date_start, String date_end) {
        try {
            Date start = !Strings.isNullOrEmpty(date_start) ? new SimpleDateFormat("dd/MM/yyyy").parse(date_start) : null;
            Date end = !Strings.isNullOrEmpty(date_end) ? new SimpleDateFormat("dd/MM/yyyy").parse(date_end) : null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            List<ProjectDetail> pds = getProjectsToExcel(srvProjectManager,
                    p,
                    start != null ? formatter.format(start) : null,
                    end != null ? formatter.format(end) : null);

            if (pds != null && !pds.isEmpty()) {
                pds.stream().forEach((pd) -> {
                    ExportExcel(srvProjectManager, pd.getId());
                });

                return "created all projects[" + pds.size() + "] in root directory ProjectManager of server";
            }
        } catch (ParseException ex) {
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "error to create xls files";
    }

    @RequestMapping(value = {"/content"})
    public @ResponseBody
    String getContent(HttpServletRequest request, Project p) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                return createContent(p);
            }
        }
        
        return "";
    }

    @RequestMapping(value = {"/filter-customer"})
    public @ResponseBody
    String filterCustomer(String customer) {
        Map<String, String> content = new HashMap<>();

        if (!Strings.isNullOrEmpty(customer)) {
            String response = "<option value='0'>Select Vessel</option>";
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
                content.put("vessel", "<option value='0'>Select Vessel</option>");
            }
            response = "<option value='0'>Select Contact</option>";
            if (contacts != null && contacts.isEmpty() == false) {
                for (Contact contact : contacts) {
                    response += "<option value='" + contact.getId() + "'>" + contact.getSurname() + " " + contact.getName() + "</option>";
                    content.put("contact", response);
                }
            } else {
                content.put("contact", "<option value='0'>Select Contact</option>");
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

                    for (Project p : prjs) {
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
        Map<String, String> content = new HashMap<>();

        content.put("status", "");
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                p = srvProjectManager.getDaoProject().getByReference(p.getReference());
                if (p != null) {
                    srvProjectManager.setProjectLock(p.getId());

                    content.put("status", "OK");
                    if (p.getStatus().equals(ProjectStatusEnum.CREATE.toString())) {
                        content.put("value", "edit-form?id=" + p.getId());
                    } else if (p.getStatus().equals(ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString())) {
                        content.put("value", "/bill-material-service?id=" + p.getId() + "&mode=EDIT-PROJECT");
                    } else if (p.getStatus().equals(ProjectStatusEnum.QUOTATION.toString())) {
                        Boolean foundRFQ = Boolean.FALSE;
                        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());

                        if (pds != null && !pds.isEmpty()) {
                            for (ProjectDetail pd : pds) {
                                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId());

                                if (bms != null) {
                                    List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                                    if (rqs != null && !rqs.isEmpty()) {
                                        foundRFQ = Boolean.TRUE;
                                        break;
                                    }
                                }
                            }
                        }
                        content.put("value", (foundRFQ.equals(Boolean.FALSE))
                                ? "/quotation?id=" + p.getId() + "&mode=EDIT-PROJECT"
                                : ProjectStatusEnum.QUOTATION.toString());
                        content.put("pId", p.getId().toString());
                    }
                } else {
                    content.put("value", "No found project");
                }
            } else {
                content.put("value", "expired login");
            }
        } else {
            content.put("value", "expired login");
        }

        String response = new Gson().toJson(content);

        logger.log(Level.INFO, "response={0}", response);

        return response;
    }

    @RequestMapping(value = "/lst-sub-project")
    public @ResponseBody
    String lstSubProjects(HttpServletRequest request, Project p) {
        String response = "";

        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByStatus(p.getStatus());

                if (pds != null && !pds.isEmpty()) {
                    String vessel = "";
                    
                    for (ProjectDetail pd : pds) {
                        Vessel v = srvProjectManager.getDaoVessel().getById(pd.getVessel());
                        String user = srvProjectManager.isProjectLock(srvProjectManager.getDaoCollabs().getAll(), p.getId());

                        if (v != null) {
                            vessel = v.getName();
                        }
                        response += (Strings.isNullOrEmpty(user))
                                ? "<input type='radio' id='" + pd.getId() + "' name='radio-sub-project' value='" + pd.getId()
                                + "'><label for='" + pd.getId() + "' class='radio-label'>" + pd.getReference() + "-" + vessel
                                + "</label><br>"
                                : "<label for='" + pd.getId() + "' class='radio-label'>" + pd.getReference() + "-" + vessel + "[" + user + "]"
                                + "</label><br>";
                    }
                    return response;
                }
            }
        }
        
        return null;
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
                        dbpd.setExpiredCreate(parsedDate);
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

    @RequestMapping(value = {"/check-flag-rfq"})
    public @ResponseBody
    String checkFlagRFQ(Project p) {
        String response = "";

        if (p != null) {
            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getFirstByProjectIdType(p.getId(), ProjectTypeEnum.SALE.toString());

            if (pd != null) {
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId());

                if (bms != null) {
                    response = (bms.getFlagRFQ().equals(Boolean.TRUE)) ? "/ProjectManager/project/request-quotation" : "/ProjectManager/project/quotation";
                }
            }
        }

        return response;
    }

    @RequestMapping(value = {"/lost-project"})
    public @ResponseBody
    String lostProject(Long pdId) {
        logger.log(Level.INFO, "Set lost project detail with id={0}", pdId);
        String response = "";

        if (pdId != null) {
            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);

            if (pd != null) {
                if (!pd.getStatus().equals(ProjectStatusEnum.LOST.toString())) {
                    pd.setTmpStatus(pd.getStatus());
                    pd.setStatus(ProjectStatusEnum.LOST.toString());
                    srvProjectManager.getDaoProjectDetail().edit(pd);

                    Long count = srvProjectManager.getDaoProjectDetail().getByProjectIdNotLost(pdId);

                    if (count == null) {
                        Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

                        if (p != null) {
                            p.setTmpStatus(p.getStatus());
                            p.setStatus(ProjectStatusEnum.LOST.toString());
                            srvProjectManager.getDaoProject().edit(p);
                        }
                    }
                } else {
                    pd.setStatus(pd.getTmpStatus());
                    pd.setTmpStatus(null);
                    srvProjectManager.getDaoProjectDetail().edit(pd);

                    Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

                    p.setStatus(p.getTmpStatus());
                    p.setTmpStatus(null);
                    srvProjectManager.getDaoProject().edit(p);
                }
            }
        }

        return response;
    }
}
