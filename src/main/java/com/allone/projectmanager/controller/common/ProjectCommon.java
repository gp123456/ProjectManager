/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.Root;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.ui.Model;

/**
 *
 * @author antonia
 */
enum ProjectMode {
    VIEW, EDIT;

}

public class ProjectCommon extends Common {

    private static final Logger LOG = Logger.getLogger(Root.class.getName());

    private ProjectMode mode;

    private String getStatusValueById(Long id) {
        for (ProjectStatusEnum item : ProjectStatusEnum.values()) {
            if (item.getId().equals(id)) {
                return item.getValue();
            }
        }

        return "";
    }

    private String createProjectRow(ProjectManagerService srvProjectManager,
                                    Project p,
                                    List<String> statuses,
                                    String mode) {
        String response = "";

        Collabs user = srvProjectManager.getDaoCollab().getById(p.getCollab());
        Vessel vess = srvProjectManager.getDaoVessel().getById(p.getVessel());

        if (mode.equals(this.mode.EDIT.name())) {
            response +=
            "<tr>\n"
            + "<td>"
            + "<div onclick=\"projectPackingList(" + p.getId() + ")\">" + p.getReference() + "</div>"
            + "</td>\n"
            + "<td>"
            + getStatusValueById(p.getStatus())
            + "</td>\n"
            + "<td>"
            + ((user
                != null) ? user.getName()
                           + " "
                           + user.getSurname() : "")
            + "</td>\n"
            + "<td>"
            + p.getCompany()
            + "</td>\n"
            + "<td>"
            + ((vess
                != null) ? vess.getName() : "")
            + "</td>\n"
            + "<td>"
            + p.getCustomer()
            + "</td>"
            + "<td><input type=\"button\" value=\""
            + statuses.get(0)
            + "\" style=\"text-align:center; "
            + "vertical-align: middle;\" id=\"edit-project\" onclick=\"editRow("
            + p.getId()
            + ")\"></td>\n"
            + "<td><input type=\"button\" value=\""
            + statuses.get(1)
            + "\" style=\"text-align:center; "
            + "vertical-align:middle;\" id=\"remove-project\" onclick=\"removeRow("
            + p.getId()
            + ")\"></td>\n"
            + "<td><input type=\"button\" value=\""
            + statuses.get(2)
            + "\" style=\"text-align:center; "
            + "vertical-align:middle;\" id=\"create-pdf\" onclick=\"createPDF("
            + p.getId()
            + ")\"></td>\n"
            + "<td><input type=\"button\" value=\""
            + statuses.get(3)
            + "\" style=\"text-align:center; "
            + "vertical-align: middle;\" id=\"print-pdf\" onclick=\"printPDF("
            + p.getId()
            + ")\"></td>\n"
            + "<td><input type=\"button\" value=\""
            + statuses.get(4)
            + "\" style=\"text-align:center; "
            + "vertical-align:middle;\" id=\"create-xls\" onclick=\"saveXLS("
            + p.getId()
            + ")\"></td>\n"
            + "<td><input type=\"button\" value=\""
            + statuses.get(5)
            + "\" style=\"text-align:center; "
            + "vertical-align:middle;\" id=\"create-xls\" onclick=\"printXLS("
            + p.getId()
            + ")\"></td>\n"
            + "<td><input type=\"button\" value=\""
            + statuses.get(6)
            + "\" style=\"text-align:center; "
            + "vertical-align:middle;\" id=\"send-email\" onclick=\"sendEnail("
            + p.getId()
            + ")\"></td>\n"
            + "</tr>\n";
        } else if (mode.equals(this.mode.VIEW.name())) {
            response +=
            "<tr>"
            + "<td>"
            + p.getReference()
            + "</td>\n"
            + "<td>"
            + getStatusValueById(p.getStatus())
            + "</td>\n"
            + "<td>"
            + ((user
                != null) ? user.getName()
                           + " "
                           + user.getSurname() : "")
            + "</td>\n"
            + "<td>"
            + p.getCompany()
            + "</td>"
            + "<td>"
            + ((vess
                != null) ? vess.getName() : "")
            + "</td>\n"
            + "<td>"
            + p.getCustomer()
            + "</td>"
            + "<td><input type=\"button\" value=\""
            + statuses.get(0)
            + "\" style=\"text-align:center; "
            + "vertical-align: middle;\" id=\"print-pdf\" onclick=\"printPDF("
            + p.getId()
            + ")\"></td>\n"
            + "<td><input type=\"button\" value=\""
            + statuses.get(1)
            + "\" style=\"text-align:center; "
            + "vertical-align:middle;\" id=\"create-xls\" onclick=\"saveXLS("
            + p.getId()
            + ")\"></td>\n"
            + "<td><input type=\"button\" value=\""
            + statuses.get(2)
            + "\" style=\"text-align:center; "
            + "vertical-align:middle;\" id=\"send-email\" onclick=\"sendEnail("
            + p.getId()
            + ")\"></td>\n"
            + "</tr>\n";
        }

        return response;
    }

    public String getModeEdit() {
        return mode.EDIT.name();
    }

    public String getModeView() {
        return mode.VIEW.name();
    }

    public String createProjectHeader(String mode) {
        if (mode.equals(this.mode.EDIT.name())) {
            return "<tr>\n"
                   + "<th>Reference</th>\n"
                   + "<th>Status</th>\n"
                   + "<th>User</th>\n"
                   + "<th>Company</th>\n"
                   + "<th>Vessel</th>\n"
                   + "<th>Customer</th>\n"
                   + "<th>Edit</th>\n"
                   + "<th>Delete</th>\n"
                   + "<th>Save to PDF</th>\n"
                   + "<th>Print to PDF</th>\n"
                   + "<th>Save to Excel</th>\n"
                   + "<th>Print to Excel</th>\n"
                   + "<th>Send eMail</th>\n"
                   + "</tr>\n";
        } else if (mode.equals(this.mode.VIEW.name())) {
            return "<tr>\n"
                   + "<th>Reference</th>\n"
                   + "<th>Status</th>\n"
                   + "<th>User</th>\n"
                   + "<th>Company</th>\n"
                   + "<th>Vessel</th>\n"
                   + "<th>Customer</th>\n"
                   + "<th>Print to PDF</th>\n"
                   + "<th>Print to Excel</th>\n"
                   + "<th>Send eMail</th>\n"
                   + "</tr>\n";
        }

        return null;
    }

    public String createProjectFooter() {
        return "<tr>"
               + "<td>"
               + "<div class=\"img last-page\" title=\"Last Page\" onclick=\"projectLastPage()\"/> "
               + "<div class=\"img previous-page\" title=\"Previous Page\" onclick=\"projectPreviousPage()\"/> "
               + "<div class=\"img next-page\" title=\"Next Page\" onclick=\"projectNextPage()\"/> "
               + "<div class=\"img first-page\" title=\"First Page\" onclick=\"projectFirstPage()\"/> "
               + "</td>"
               + "</tr>";
    }

    public Object[] createProjectBody(ProjectManagerService srvProjectManager,
                                      Project p,
                                      List<String> statuses,
                                      String mode,
                                      Integer offset,
                                      Integer size) {
        Boolean navTable = Boolean.FALSE;
        String response = "";
        List<Project> lstPrj = null;
        Long countPrj = 0l;
        Project onePrj = (!p.getId().equals(-1l)) ? srvProjectManager.getDaoProject().getById(p.getId()) : null;

        if (onePrj == null) {
            lstPrj = (!p.getType().equals(-1l)) ?
                     srvProjectManager.getDaoProject().getByType(p.getType(), offset, size) : null;
            countPrj = (!p.getType().equals(-1l)) ?
                       srvProjectManager.getDaoProject().countByType(p.getType()) : null;

            if (lstPrj == null) {
                lstPrj = (!p.getStatus().equals(-1l)) ?
                         srvProjectManager.getDaoProject().getByStatus(p.getStatus(), offset, size) : null;
                countPrj = (!p.getStatus().equals(-1l)) ?
                           srvProjectManager.getDaoProject().countByStatus(p.getStatus()) : null;
            }
            if (lstPrj == null) {
                lstPrj = (!p.getVessel().equals(-1l)) ?
                         srvProjectManager.getDaoProject().getByVessel(p.getVessel(), offset, size) : null;
                countPrj = (!p.getVessel().equals(-1l)) ?
                           srvProjectManager.getDaoProject().countByVessel(p.getVessel()) : null;
            }
            if (lstPrj == null) {
                lstPrj = (!p.getCustomer().equals("-1")) ?
                         srvProjectManager.getDaoProject().getByCustomer(p.getCustomer(), offset, size) : null;
                countPrj = (!p.getCustomer().equals(-1l)) ?
                           srvProjectManager.getDaoProject().countByCustomer(p.getCustomer()) : null;
            }
            if (lstPrj == null) {
                lstPrj = (!p.getCompany().equals("-1")) ?
                         srvProjectManager.getDaoProject().getByCompany(p.getCompany(), offset, size) : null;
                countPrj = (!p.getCompany().equals(-1l)) ?
                           srvProjectManager.getDaoProject().countByCompany(p.getCompany()) : null;
            }
        }

        if (onePrj
            != null) {
            response = createProjectRow(srvProjectManager, onePrj, statuses, mode);
        } else if (lstPrj != null && !lstPrj.isEmpty() && countPrj != null) {
            navTable = (countPrj.compareTo(new Long(size)) <= 0) ? Boolean.FALSE : Boolean.TRUE;
            for (Project prj : lstPrj) {
                response += createProjectRow(srvProjectManager, prj, statuses, mode);
            }
        } else {
            lstPrj = srvProjectManager.getDaoProject().getAll(offset, size);
            countPrj = srvProjectManager.getDaoProject().countAll();

            if (lstPrj != null && !lstPrj.isEmpty()) {
                navTable = (countPrj.compareTo(new Long(size)) <= 0) ? Boolean.FALSE : Boolean.TRUE;
                for (Project prj : lstPrj) {
                    response += createProjectRow(srvProjectManager, prj, statuses, mode);
                }
            }
        }

        return new Object[]{navTable, response};
    }

    public String searchProject(ProjectManagerService srvProjectManager, Project p, Integer offset, Integer size, String mode, Model model) {
        if (p != null) {
            Map<String, String> content = new HashMap<>();
            String projectHeader;
            Object[] projectBody;
            String projectFooter;
            
            LOG.info("--------------------------" + mode + "-------------------------------- ");
            
            if (mode.equals("view")) {    
                projectHeader = createProjectHeader(getModeView());
                projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
                        "Start", "Start")), getModeView(), offset, size);
            } else {
                projectHeader = createProjectHeader(getModeEdit());
                projectBody = createProjectBody(srvProjectManager, p, new ArrayList<String>(Arrays.asList("Start",
                        "Start", "Start", "Start", "Start", "Start", "Start", "Start")), getModeEdit(), offset, size);
            }
            projectFooter = (projectBody[0].equals(Boolean.TRUE)) ? createProjectFooter() : "";

            content.put("project_header", projectHeader);
            content.put("project_body", projectBody[1].toString());
            content.put("project_footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }
}
