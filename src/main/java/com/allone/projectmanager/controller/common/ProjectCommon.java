 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.WCSProjectManagerService;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.model.PlotInfoModel;
import com.allone.projectmanager.model.SearchInfo;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antonia
 */
enum ProjectMode {

    VIEW, EDIT, NEW;

}

public class ProjectCommon extends Common {

    private static final Logger logger = Logger.getLogger(ProjectCommon.class.getName());

    private ProjectMode mode;

    private String createSearchStatus(String version) {
        List<SearchInfo> info = getSearchCriteriaStatusProject(version);
        String response = "<option value=\"none\" selected=\"selected\">Select Status</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            response += info.stream()
            .map((si) -> "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>").
            reduce(response, String::concat);
        }

        return response;
    }

    public String createProjectRow(ProjectManagerService srvProjectManager, ProjectDetail pd) {
        String response = "";
        Collabs user = srvProjectManager.getDaoCollab().getById(pd.getCreator());
        Vessel vess = srvProjectManager.getDaoVessel().getById(pd.getVessel());
        Contact cont = srvProjectManager.getDaoContact().getById(pd.getContact());

        response +=
        "<tr>" +
        "<td>" + pd.getReference() + "</td>\n" +
        "<td>" + pd.getType() + "</td>\n" +
        "<td>" + pd.getStatus() + "</td>\n" +
        "<td>" + ((user != null) ? user.getName() + " " + user.getSurname() : "") + "</td>\n" +
        "<td>" + pd.getCreated() + "</td>\n" +
        "<td>" + pd.getExpired() + "</td>\n" +
        "<td>" + pd.getCompany() + "</td>" +
        "<td>" + ((vess != null) ? vess.getName() : "") + "</td>\n" +
        "<td>" + pd.getCustomer() + "</td>" +
        "<td>" + ((cont != null) ? cont.getName() + " " + cont.getSurname() : "") + "</td>\n" +
        "<td></td>\n" +
        "<td><input type='button' id='send-email' onclick='sendEnail(" + pd.getId() + ")'></td>\n" +
        "</tr>\n";

        return response;
    }

    public String getModeEdit() {
        return mode.EDIT.name();
    }

    public String getModeView() {
        return mode.VIEW.name();
    }

    public String createProjectHeader() {
        return "<tr>\n" +
               "<th>Reference</th>\n" +
               "<th>Type</th>\n" +
               "<th>Status</th>\n" +
               "<th>User</th>\n" +
               "<th>Created</th>\n" +
               "<th>Expired</th>\n" +
               "<th>Company</th>\n" +
               "<th>Vessel</th>\n" +
               "<th>Customer</th>\n" +
               "<th>Contact</th>\n" +
               "<th>Print to ...</th>\n" +
               "<th>Send eMail</th>\n" +
               "</tr>\n";
    }

    public String createProjectFooter(Integer offset, Integer last, Integer size) {
        return "<tr>" +
               "<td>" +
               "<div class='img last-page' title='Last Page' onclick=\"projectLastPage('" + last + "," + size + ")\"/>" +
               "<div class='img previous-page' title='Previous Page' onclick=\"projectPreviousPage('" + offset + "," + size + ")\"/>" +
               "<div class='img next-page' title='Next Page' onclick=\"projectNextPage('" + offset + "," + size + ")\"/>" +
               "<div class='img first-page' title='First Page' onclick=\"projectFirstPage('" + 0 + "," + size + ")\"/>" +
               "</td>" +
               "</tr>";
    }

//    public Object[] createProjectBody(ProjectManagerService srvProjectManager, ProjectDetail pd, List<String> statuses, String mode, Integer offset, Integer size) {
//        Boolean navTable = Boolean.FALSE;
//        String response = "";
//
//        if (pd == null) {
//            return new Object[]{navTable, response};
//        }
//
//        List<ProjectDetail> lstPrj = null;
//        Long countPrj = 0l;
//        Long id = pd.getId();
//        ProjectDetail onePrj = (id != null) ? srvProjectManager.getDaoProjectDetail().getById(id) : null;
//
//        if (onePrj == null) {
//            if (lstPrj == null) {
//                String status = pd.getStatus();
//
//                lstPrj = (!Strings.isNullOrEmpty(status) && !status.equals("none")) ?
//                         srvProjectManager.getDaoProjectDetail().getByStatus(status, offset, size) : null;
//                countPrj = (lstPrj != null) ? srvProjectManager.getDaoProjectDetail().countByStatus(status) : null;
//            }
//            if (lstPrj == null) {
//                Long projectId = pd.getProject();
//
//                lstPrj = (projectId != null && !projectId.equals(-1l)) ? srvProjectManager.getDaoProjectDetail().
//                         getByProjectId(projectId) : null;
//            }
//            if (lstPrj == null) {
//                String type = pd.getType();
//
//                lstPrj = (!Strings.isNullOrEmpty(type)) ? srvProjectManager.getDaoProjectDetail().
//                         getByType(type, offset, size) : null;
//                countPrj = (lstPrj != null) ? srvProjectManager.getDaoProjectDetail().countByType(type) : null;
//            }
//            if (lstPrj == null) {
//                Long vessel = pd.getVessel();
//
//                lstPrj = (vessel != null && !vessel.equals(-1)) ? srvProjectManager.getDaoProjectDetail().getByVessel(
//                         vessel, offset, size) : null;
//                countPrj = (lstPrj != null) ? srvProjectManager.getDaoProjectDetail().countByVessel(vessel) : null;
//            }
//            if (lstPrj == null) {
//                String customer = pd.getCustomer();
//
//                lstPrj = (!Strings.isNullOrEmpty(customer)) ? srvProjectManager.getDaoProjectDetail().getByCustomer(
//                         customer, offset, size) : null;
//                countPrj = (lstPrj != null) ? srvProjectManager.getDaoProjectDetail().countByCustomer(customer) : null;
//            }
//            if (lstPrj == null) {
//                String company = pd.getCompany();
//
//                lstPrj = (!Strings.isNullOrEmpty(company)) ? srvProjectManager.getDaoProjectDetail().getByCompany(
//                         company, offset, size) : null;
//                countPrj = (lstPrj != null) ? srvProjectManager.getDaoProjectDetail().countByCompany(company) : null;
//            }
//        }
//
//        if (onePrj != null) {
//            response = createProjectRow(srvProjectManager, onePrj, statuses, mode);
//        } else if (lstPrj != null && !lstPrj.isEmpty() && countPrj != null) {
//            navTable = (countPrj.compareTo(new Long(size)) <= 0) ? Boolean.FALSE : Boolean.TRUE;
//            for (ProjectDetail prj : lstPrj) {
//                response += createProjectRow(srvProjectManager, prj, statuses, mode);
//            }
//        } else {
//            lstPrj = srvProjectManager.getDaoProjectDetail().getAll(offset, size);
//            countPrj = srvProjectManager.getDaoProjectDetail().countAll();
//
//            if (lstPrj != null && !lstPrj.isEmpty()) {
//                navTable = (countPrj.compareTo(new Long(size)) <= 0) ? Boolean.FALSE : Boolean.TRUE;
//                for (ProjectDetail prj : lstPrj) {
//                    response += createProjectRow(srvProjectManager, prj, statuses, mode);
//                }
//            }
//        }
//
//        return new Object[]{navTable, response};
//    }
    public Object[] createProjectBody(ProjectManagerService srvProjectManager, ProjectDetail pd, String date_start, String date_end, String vesselCustom, String customerCustom, Integer offset,
                                      Integer size) {
        Long prjCount = 0l;
        String response = "";

        if (pd == null) {
            return new Object[]{prjCount, response};
        }

        Long id = pd.getId();
        ProjectDetail onePrj = (id != null) ? srvProjectManager.getDaoProjectDetail().getById(id) : null;
        List<ProjectDetail> lstPrj = null;

        if (onePrj == null) {
            Map<String, String> criteria = new HashMap<>();
            String type = pd.getType();
            String status = pd.getStatus();
            Long vessel = pd.getVessel();
            String customer = pd.getCustomer();
            String company = pd.getCompany();

            if (!Strings.isNullOrEmpty(type)) {
                criteria.put("desciptionType", type);
            }
            if (!Strings.isNullOrEmpty(status)) {
                criteria.put("descriptionStatus", status);
            }
            if (vessel != null) {
                criteria.put("vessel", vessel.toString());
            }
            if (!Strings.isNullOrEmpty(vesselCustom)) {
                criteria.put("vesselCustom", vesselCustom);
            }
            if (!Strings.isNullOrEmpty(customerCustom)) {
                criteria.put("customerCustom", customerCustom);
            }
            if (!Strings.isNullOrEmpty(customer)) {
                criteria.put("customer", customer);
            }
            if (!Strings.isNullOrEmpty(company)) {
                criteria.put("company", company);
            }
            if (!Strings.isNullOrEmpty(date_start)) {
                criteria.put("date_start", date_start);
            }
            if (!Strings.isNullOrEmpty(date_end)) {
                criteria.put("date_end", date_end);
            }

            lstPrj = srvProjectManager.getDaoProject().getByCriteria(criteria, offset, size);
            prjCount = srvProjectManager.getDaoProject().getCountByCriteria(criteria);
        }

        if (onePrj != null) {
            response = createProjectRow(srvProjectManager, onePrj);
        } else if (lstPrj != null && !lstPrj.isEmpty()) {
            for (ProjectDetail prj : lstPrj) {
                response += createProjectRow(srvProjectManager, prj);
            }
        }

        return new Object[]{prjCount, response};
    }

    public String searchProject(ProjectManagerService srvProjectManager, ProjectDetail pd, String date_start, String date_end, String vessCustom, String customerCustom, Integer offset, Integer size) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            String projectHeader = null;
            Object[] projectBody = null;
            String projectFooter;

            projectHeader = createProjectHeader();
            projectBody = createProjectBody(srvProjectManager, pd, date_start, date_end, vessCustom, customerCustom, offset, size);

            if (projectBody != null && ((Long) projectBody[0]).compareTo(new Long(size)) > 0) {
                logger.log(Level.INFO, "count all={0}", (Long) projectBody[0]);

                Long last = ((Long) projectBody[0] / size) - 1l;

                logger.log(Level.INFO, "last ={0}", last);

                projectFooter = createProjectFooter(offset, last.intValue(), size);
            } else {
                projectFooter = "";
            }

            content.put("header", projectHeader);
            content.put("body", (projectBody != null) ? projectBody[1].toString() : "");
            content.put("footer", projectFooter);

            return new Gson().toJson(content);
        }

        return "";
    }

    public List<PlotInfoModel> getOpenProjectStatusByType(ProjectManagerService srvProjectManager, String type) {
        Long allOpen = srvProjectManager.getDaoProjectDetail().getTotalOpenByType(type);
        Long allCreate = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.CREATE.toString());
        Long allBill = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.PROJECT_BILL.toString());
        Long allQuota = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.REQUEST_QUOTATION.toString());
        Long allPurchase = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.PURCHASE_ORDER.toString());
        Long allWork = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.WORK_ORDER.toString());
        Long allAck = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.ACK_ORDER.toString());
        Long allPacking = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.PACKING_LIST.toString());
        Long allDelivery = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.DELIVERY_NOTE.toString());
        Long allShipping = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.SHIPPING_INVOICE.toString());
        List<PlotInfoModel> result = new ArrayList<>();

        if (allOpen != null && allOpen.compareTo(0l) > 0) {
            result.add(new PlotInfoModel("All", (allOpen.toString())));
            result.add(new PlotInfoModel("Create", allCreate.toString()));
            result.add(new PlotInfoModel("Bill Material", allBill.toString()));
            result.add(new PlotInfoModel("Request Quota", allQuota.toString()));
            result.add(new PlotInfoModel("Purchase Order", allPurchase.toString()));
            result.add(new PlotInfoModel("Work Order", allWork.toString()));
            result.add(new PlotInfoModel("Ack Order", allAck.toString()));
            result.add(new PlotInfoModel("Packing List", allPacking.toString()));
            result.add(new PlotInfoModel("Delivery Note", allDelivery.toString()));
            result.add(new PlotInfoModel("Ship Invoice", allShipping.toString()));
        }

        return result;
    }

    public List<PlotInfoModel> getOpenProjectCompanyByType(ProjectManagerService srvProjectManager, String type) {
        Long allOpen = srvProjectManager.getDaoProjectDetail().getTotalOpenByType(type);
        Long allMARPO = srvProjectManager.getDaoProjectDetail().getCountByTypeCompany(type, OwnCompanyEnum.MARPO.toString());
        Long allWCSLTD = srvProjectManager.getDaoProjectDetail().getCountByTypeCompany(type, OwnCompanyEnum.WCS_LTD.toString());
        Long allWCSHellas = srvProjectManager.getDaoProjectDetail().getCountByTypeCompany(type, OwnCompanyEnum.WCS_HELLAS.toString());
        Long allMTS = srvProjectManager.getDaoProjectDetail().getCountByTypeCompany(type, OwnCompanyEnum.MTS.toString());

        List<PlotInfoModel> result = new ArrayList<>();

        if (allOpen != null && allOpen.compareTo(0l) > 0) {
            result.add(new PlotInfoModel("ALL", allOpen.toString()));
            result.add(new PlotInfoModel("MARPO", allMARPO.toString()));
            result.add(new PlotInfoModel("WCS HELLAS", allWCSHellas.toString()));
            result.add(new PlotInfoModel("WCS LTD", allWCSLTD.toString()));
            result.add(new PlotInfoModel("MTS", allMTS.toString()));
        }

        return result;
    }

    public String searchCriteria(ProjectManagerService srvProjectManager, WCSProjectManagerService srvWCSProjectManager,
                                 String version) {
        Map<String, String> contentMap = new HashMap<>();

        contentMap.put("type", createSearchType());
        contentMap.put("status", createSearchStatus(version));
        contentMap.put("vessel", createSearchVessel(srvProjectManager, null));
        contentMap.put("customer", createSearchCompany(srvProjectManager, null, CompanyTypeEnum.CUSTOMER));
        contentMap.put("company", createSearchCompany());
        contentMap.put("contact", createSearchContact(srvProjectManager, null));

        return new Gson().toJson(contentMap);
    }
}
