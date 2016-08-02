/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.BillMaterialServiceItem;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.model.PlotInfoModel;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    private final Map<Long, List<BillMaterialServiceItem>> mapBillMaterialServiceItem = new HashMap<>();

    private final Map<Long, BillMaterialService> mapBillMaterialService = new HashMap<>();

    public String createProjectRow(ProjectManagerService srvProjectManager, ProjectDetail pd) {
        String response = "";
        Collabs user = srvProjectManager.getDaoCollab().getById(pd.getCreator());
        Vessel vess = srvProjectManager.getDaoVessel().getById(pd.getVessel());
        Contact cont = srvProjectManager.getDaoContact().getById(pd.getContact());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

        response += "<tr>"
                + "<td>" + pd.getReference() + "</td>\n"
                + "<td>" + pd.getType() + "</td>\n"
                + "<td>" + pd.getStatus() + "</td>\n"
                + "<td>" + ((user != null) ? user.getName() + " " + user.getSurname() : "") + "</td>\n"
                + "<td>" + format.format(pd.getCreated()) + "</td>\n"
                + "<td>" + format.format(pd.getExpired()) + "</td>\n"
                + "<td>" + pd.getCompany() + "</td>"
                + "<td>" + ((vess != null) ? vess.getName() : "") + "</td>\n"
                + "<td>" + pd.getCustomer() + "</td>"
                + "<td>" + ((cont != null) ? cont.getName() + " " + cont.getSurname() : "") + "</td>\n"
                + "</tr>\n";

        return response;
    }

    public String getModeEdit() {
        return mode.EDIT.name();
    }

    public String getModeView() {
        return mode.VIEW.name();
    }

    public String createProjectHeader() {
        return "<tr>\n"
                + "<th>Reference</th>\n"
                + "<th>Type</th>\n"
                + "<th>Status</th>\n"
                + "<th>User</th>\n"
                + "<th>Created</th>\n"
                + "<th>Expired</th>\n"
                + "<th>Company</th>\n"
                + "<th>Vessel</th>\n"
                + "<th>Customer</th>\n"
                + "<th>Contact</th>\n"
                + "</tr>\n";
    }

    public String createProjectFooter(Integer offset, Integer last, Integer size) {
        return "<tr>"
                + "<td>" + "<div class='img last-page' title='Last Page' onclick=\"projectLastPage('" + last + "," + size + ")\"/>"
                + "<div class='img previous-page' title='Previous Page' onclick=\"projectPreviousPage('" + offset + "," + size + ")\"/>"
                + "<div class='img next-page' title='Next Page' onclick=\"projectNextPage('" + offset + "," + size + ")\"/>"
                + "<div class='img first-page' title='First Page' onclick=\"projectFirstPage('" + 0 + "," + size + ")\"/>" + "</td>"
                + "</tr>";
    }

    public Object[] createProjectBody(ProjectManagerService srvProjectManager, ProjectDetail pd, String date_start, String date_end, String vessel,
            Integer offset, Integer size) {
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
            String customer = pd.getCustomer();
            String company = pd.getCompany();

            if (!Strings.isNullOrEmpty(type) && !type.equals("none")) {
                criteria.put("type", type);
            }
            if (!Strings.isNullOrEmpty(status) && !status.equals("none")) {
                criteria.put("status", status);
            }
            if (!Strings.isNullOrEmpty(vessel)) {
                criteria.put("vesselCustom", vessel);
            }
            if (!Strings.isNullOrEmpty(customer)) {
                criteria.put("customerCustom", customer);
            }
            if (!Strings.isNullOrEmpty(customer) && !customer.equals("none")) {
                criteria.put("customer", customer);
            }
            if (!Strings.isNullOrEmpty(company) && !company.equals("none")) {
                criteria.put("company", company);
            }
            if (!Strings.isNullOrEmpty(date_start)) {
                criteria.put("start", date_start);
            }
            if (!Strings.isNullOrEmpty(date_end)) {
                criteria.put("end", date_end);
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

    public String searchProject(ProjectManagerService srvProjectManager, ProjectDetail pd, String date_start, String date_end, String vessel,
            Integer offset, Integer size) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            String header = null;
            Object[] body = null;
            String footer = null;
            Long count = null;

            header = createProjectHeader();
            body = createProjectBody(srvProjectManager, pd, date_start, date_end, vessel, offset, size);

            if (body != null && ((Long) body[0]).compareTo(new Long(size)) > 0) {
                count = (Long) body[0];

                Long last = ((Long) body[0] / size) - 1l;

                footer = createProjectFooter(offset, last.intValue(), size);
            }

            content.put("header", (!Strings.isNullOrEmpty(header)) ? header : "");
            content.put("body", (body != null) ? body[1].toString() : "");
            content.put("footer", (!Strings.isNullOrEmpty(footer)) ? footer : "");

            return new Gson().toJson(content);
        }

        return "";
    }

    public List<PlotInfoModel> getOpenProjectStatusByType(ProjectManagerService srvProjectManager, String type) {
        Long allOpen = srvProjectManager.getDaoProjectDetail().getTotalOpenByType(type);
        Long allCreate = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.CREATE.toString());
        Long allBill = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString());
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

    public String searchCriteria(ProjectManagerService srvProjectManager, String version) {
        Map<String, String> contentMap = new HashMap<>();

        contentMap.put("type", fillSearchType());
        contentMap.put("status", fillSearchStatus(version));
        contentMap.put("vessel", fillSearchVessel(srvProjectManager, null));
        contentMap.put("customer", fillSearchCompany(srvProjectManager, null, CompanyTypeEnum.CUSTOMER));
        contentMap.put("company", fillSearchOwnCompany());
        contentMap.put("contact", fillSearchContact(srvProjectManager, null));

        return new Gson().toJson(contentMap);
    }

    public Collection<BillMaterialServiceItem> getBillMaterialServiceItems(Long pd) {
        return mapBillMaterialServiceItem.get(pd);
    }

    public Set<Long> getProjectDetailIds() {
        return (mapBillMaterialServiceItem != null && !mapBillMaterialServiceItem.isEmpty()) ? mapBillMaterialServiceItem.keySet() : null;
    }

    public Set<Long> getBillMaterialServiceItemsByPDId(Long pd) {
        if (pd != null && !mapBillMaterialServiceItem.isEmpty()) {
            Set<Long> keys = new HashSet<>();

            getProjectDetailIds().stream().filter((key) -> (key.equals(pd))).forEach((key) -> {
                keys.add(key);
            });

            return keys;
        }

        return null;
    }

    public BillMaterialService getBillMaterialService(Long pd) {
        return mapBillMaterialService.get(pd);
    }

    public BillMaterialServiceItem getBillMaterialServiceItem(Long pd, Long itemId) {
        if (!mapBillMaterialServiceItem.isEmpty()) {
            List<BillMaterialServiceItem> items = mapBillMaterialServiceItem.get(pd);

            if (items != null && !items.isEmpty()) {
                Map<Long, BillMaterialServiceItem> result = items.stream().collect(Collectors.toMap(BillMaterialServiceItem::getItem, (c) -> c));

                return result.get(itemId);
            }
        }

        return null;
    }

    public BillMaterialServiceItem getFirstBillMaterialServiceItem(Long pd) {
        if (!mapBillMaterialServiceItem.isEmpty() && pd != null) {
            List<BillMaterialServiceItem> items = mapBillMaterialServiceItem.get(pd);

            if (items != null && !items.isEmpty()) {
                return items.get(0);
            }
        }

        return null;
    }

    public void setVirtualBillMaterialService(BillMaterialService bms) {
        if (bms != null) {
            mapBillMaterialService.put(bms.getProject(), bms);
        }
    }

    public void removeVirtualBillMaterialService(Long pd) {
        if (pd != null) {
            mapBillMaterialService.remove(pd);
        }
    }

    public void setVirtualBillMaterialServiceItem(Long pd,
            BillMaterialServiceItem bmsi) {
        if (pd != null && bmsi != null) {
            List<BillMaterialServiceItem> items = mapBillMaterialServiceItem.get(pd);

            if (items != null) {
                items.add(bmsi);
            } else {
                mapBillMaterialServiceItem.put(pd, new ArrayList<>(Arrays.asList(bmsi)));
            }
        }
    }

    public void editVirtualBillMaterialServiceItem(Long pd, BillMaterialServiceItem bmsi) {
        if (pd != null && bmsi != null && bmsi.getItem() != null) {
            List<BillMaterialServiceItem> items = mapBillMaterialServiceItem.get(pd);

            if (items != null && !items.isEmpty()) {
                items.stream().filter((item) -> (item.getItem().equals(bmsi.getItem()))).forEach((item) -> {
                    item = bmsi;
                });
            }
        }
    }

    public void setBillMaterialServiceItemInfo(Long pd, Long bms, String quantities) {
        if (pd != null && bms != null) {
            String[] values = quantities.split(",");
            Collection<BillMaterialServiceItem> items = mapBillMaterialServiceItem.get(pd);

            if (items != null && !items.isEmpty()) {
                Map<Long, BillMaterialServiceItem> mapItems = new HashMap<>();

                items.stream().forEach((item) -> {
                    mapItems.put(item.getItem(), item);
                });
                for (String value : values) {
                    String[] complete = value.split("-");

                    if (complete != null && complete.length > 0) {
                        String id = complete[0];
                        String val = complete[1];

                        if (!Strings.isNullOrEmpty(id) && !Strings.isNullOrEmpty(val)) {
                            BillMaterialServiceItem item = mapItems.get(Long.valueOf(id));

                            if (item != null) {
                                item.setQuantity(Integer.valueOf(val));
                                item.setBillMaterialService(bms);
                            }
                        }
                    }
                }
            }
        }
    }

    public void removeVirtualBillMaterialServiceItem(ProjectManagerService srvProjectManager, Long pd, Long itemId) {
        List<BillMaterialServiceItem> items = mapBillMaterialServiceItem.get(pd);

        if (items != null && !items.isEmpty()) {
            Map<Long, BillMaterialServiceItem> map = new HashMap<>();

            items.stream().forEach((item) -> {
                map.put(item.getItem(), item);
            });

            BillMaterialServiceItem removeItem = map.remove(itemId);

            if (removeItem != null) {
                removeItem = srvProjectManager.getDaoBillMaterialServiceItem().getById(removeItem.getId());
                srvProjectManager.getDaoBillMaterialServiceItem().delete(removeItem);
            }
            mapBillMaterialServiceItem.replace(pd, new ArrayList<>(map.values()));
        }
    }

    public Boolean clearVirtualBillMaterialService(Long pd) {
        if (pd != null) {
            mapBillMaterialServiceItem.remove(pd);

            return (mapBillMaterialService.remove(pd) != null) ? Boolean.TRUE : Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
