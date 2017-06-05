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
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Quotation;
import com.allone.projectmanager.entities.QuotationItem;
import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.RequestQuotationItem;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.PlotInfoModel;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

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

    private Object[] getProjectsByCriteria(ProjectManagerService srvProjectManager, ProjectDetail pd, String date_start, String date_end, Integer offset,
            Integer size) {
        if (pd == null) {
            return new Object[]{0l, ""};
        }

        String ref = pd.getReference();
        @SuppressWarnings("UnusedAssignment")
        Long prjCount = 0l;
        List<ProjectDetail> lstPrj = (!Strings.isNullOrEmpty(ref)) ? Arrays.asList(srvProjectManager.getDaoProjectDetail().getByReference(ref)) : null;

        if (lstPrj != null) {
            prjCount = new Long(lstPrj.size());
        } else {
            Map<String, String> criteria = new HashMap<>();
            String type = pd.getType();
            String status = pd.getStatus();
            String customer = pd.getCustomer();
            String company = pd.getCompany();
            String vessel = pd.getVesselName();

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

        return new Object[]{prjCount, lstPrj};
    }

    public String createProjectRow(ProjectManagerService srvProjectManager, ProjectDetail pd) {
        String response = "";
        String user = srvProjectManager.isProjectLock(srvProjectManager.getDaoCollabs().getAll(), pd.getId());
        Vessel vess = srvProjectManager.getDaoVessel().getById(pd.getVessel());
        Contact cont = srvProjectManager.getDaoContact().getById(pd.getContact());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        Date current = new Date();
        long diffQCurrent = pd.getExpiredCreate().getTime() - current.getTime();
        long diffFCurrent = pd.getExpired().getTime() - current.getTime();
        String alarm = (!pd.getStatus().equals(ProjectStatusEnum.FINAL.toString()))
                ? ((!pd.getStatus().equals(ProjectStatusEnum.QUOTATION.toString()))
                ? ((diffQCurrent > 0)
                        ? "/ProjectManager/images/projectmanager/common/bullet-green.png"
                        : "/ProjectManager/images/projectmanager/common/bullet-yellow.png")
                : ((diffFCurrent > 0)
                        ? "/ProjectManager/images/projectmanager/common/bullet-green.png"
                        : "/ProjectManager/images/projectmanager/common/bullet-red.png"))
                : "/ProjectManager/images/projectmanager/common/bullet-black.png";

        response += "<tr>"
                + ((pd.getType().equals(ProjectTypeEnum.SALE.toString()))
                ? "<td><a href='#' onclick='dlgViewProject(" + pd.getId() + ")'>" + pd.getReference() + "</a></td>\n"
                : "<td>" + pd.getReference() + "</td>\n")
                + "<td>" + pd.getType() + "</td>\n"
                + "<td>" + pd.getStatus() + "</td>\n"
                + "<td>" + ((!Strings.isNullOrEmpty(user)) ? user : "") + "</td>\n"
                + "<td>" + format.format(pd.getCreated()) + "</td>\n"
                + "<td>" + format.format(pd.getExpiredCreate()) + "</td>\n"
                + "<td>" + format.format(pd.getExpired()) + "</td>\n"
                + "<td>" + pd.getCompany() + "</td>"
                + "<td>" + ((vess != null) ? vess.getName() : "") + "</td>\n"
                + "<td>" + pd.getCustomer() + "</td>"
                + "<td>" + ((cont != null) ? cont.getName() + " " + cont.getSurname() : "") + "</td>\n"
                + "<td  align='center'><img src=" + alarm + " /></td>\n"
                + "<td  align='center'><input type='button' class='button' value='Yes/No' onclick=\"lostProject(" + pd.getId() + ",'" + pd.getReference()
                + "')\"/></td>\n"
                + "</tr>\n";

        return response;
    }

    @SuppressWarnings("static-access")
    public String getModeEdit() {
        return mode.EDIT.name();
    }

    @SuppressWarnings("static-access")
    public String getModeView() {
        return mode.VIEW.name();
    }

    public String createProjectHeader() {
        return "<tr>\n"
                + "<th>Reference</th>\n"
                + "<th>Type</th>\n"
                + "<th>Status</th>\n"
                + "<th>Lock User</th>\n"
                + "<th>Created</th>\n"
                + "<th>Quotation Expired</th>\n"
                + "<th>Final Expired</th>\n"
                + "<th>Company</th>\n"
                + "<th>Vessel</th>\n"
                + "<th>Customer</th>\n"
                + "<th>Contact</th>\n"
                + "<th>Alarm</th>\n"
                + "<th>Lost</th>\n"
                + "</tr>\n";
    }

    public String createProjectFooter(Integer offset, Integer last, Integer size) {
        return "<tr>"
                + "<td colspan='2' align='center'>" + "<div class='img last-page' title='Last Page' onclick=\"projectLastPage(" + last + "," + size + ")\"/>"
                + "<div class='img previous-page' title='Previous Page' onclick=\"projectPreviousPage(" + offset + "," + size + ")\"/>"
                + "<div class='img next-page' title='Next Page' onclick=\"projectNextPage(" + offset + "," + size + ")\"/>"
                + "<div class='img first-page' title='First Page' onclick=\"projectFirstPage(" + 0 + "," + size + ")\"/>" + "</td>"
                + "</tr>";
    }

    public Object[] createProjectBody(ProjectManagerService srvProjectManager, ProjectDetail pd, String date_start, String date_end, Integer offset,
            Integer size) {
        String response = "";
        Object[] obj = getProjectsByCriteria(srvProjectManager, pd, date_start, date_end, offset, size);

        Long prjCount = (Long) obj[0];
        List<ProjectDetail> lstPrj = (List<ProjectDetail>) obj[1];

        if (lstPrj != null && !lstPrj.isEmpty()) {
            response = lstPrj.stream().map((prj) -> createProjectRow(srvProjectManager, prj)).reduce(response, String::concat);
        }

        return new Object[]{prjCount, response};
    }

    public List<ProjectDetail> getProjectsToExcel(ProjectManagerService srvProjectManager, ProjectDetail pd, String date_start, String date_end) {
        Object[] obj = getProjectsByCriteria(srvProjectManager, pd, date_start, date_end, 0, Integer.MAX_VALUE);
        List<ProjectDetail> lst = (List<ProjectDetail>) obj[1];

        logger.log(Level.INFO, "size={0}", lst.size());

        return lst;
    }

    public String searchProject(ProjectManagerService srvProjectManager, ProjectDetail pd, String date_start, String date_end, Integer offset,
            Integer size) {
        if (pd != null) {
            Map<String, String> content = new HashMap<>();
            @SuppressWarnings("UnusedAssignment")
            String header = null;
            @SuppressWarnings("UnusedAssignment")
            Object[] body = null;
            String footer = null;
            Long count = null;

            header = createProjectHeader();
            body = createProjectBody(srvProjectManager, pd, date_start, date_end, offset, size);

            if (body != null) {
                count = (Long) body[0];
                if (count.compareTo(new Long(size)) > 0) {

                    Long last = (count / size);

                    footer = createProjectFooter(offset, last.intValue(), size);
                }
            }

            content.put("header", (!Strings.isNullOrEmpty(header)) ? header : "");
            content.put("body", (body != null) ? body[1].toString() : "");
            content.put("footer", (!Strings.isNullOrEmpty(footer)) ? footer : "");
            content.put("count", (count != null) ? count.toString() : "0");

            return new Gson().toJson(content);
        }

        return "";
    }

    public List<PlotInfoModel> getOpenProjectStatusByType(ProjectManagerService srvProjectManager, String type) {
        Long allOpen = srvProjectManager.getDaoProjectDetail().getTotalOpenByType(type);
        Long allCreate = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.CREATE.toString());
        Long allBill = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString());
        Long allQuota = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.QUOTATION.toString());
        Long allPurchase = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.PURCHASE_ORDER.toString());
        Long allWork = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.WORK_ORDER.toString());
        Long allAck = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.ACK_ORDER.toString());
        Long allForwarding = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.FORWARDING_DOCUMENTS.toString());
        Long allInvoice = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.INVOICE.toString());
        Long allCreditNote = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.CREDIT_NOTE.toString());
        Long allFinal = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.FINAL.toString());
        Long allLost = srvProjectManager.getDaoProjectDetail().getCountByTypeStatus(type, ProjectStatusEnum.LOST.toString());
        List<PlotInfoModel> result = new ArrayList<>();

        if (allOpen != null && allOpen.compareTo(0l) > 0) {
            result.add(new PlotInfoModel("All", (allOpen.toString())));
            result.add(new PlotInfoModel("Create", allCreate.toString()));
            result.add(new PlotInfoModel("Bill Material", allBill.toString()));
            result.add(new PlotInfoModel("Quotation", allQuota.toString()));
            result.add(new PlotInfoModel("Purchase Order", allPurchase.toString()));
            result.add(new PlotInfoModel("Work Order", allWork.toString()));
            result.add(new PlotInfoModel("Ack Order", allAck.toString()));
            result.add(new PlotInfoModel("Forwarding Documents", allForwarding.toString()));
            result.add(new PlotInfoModel("Invoice", allInvoice.toString()));
            result.add(new PlotInfoModel("Credit Note", allCreditNote.toString()));
            result.add(new PlotInfoModel("Final", allFinal.toString()));
            result.add(new PlotInfoModel("Lost", allLost.toString()));
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

    private void ProjectSheet(WritableWorkbook workbook, ProjectManagerService srvProjectManager, ProjectDetail pd) {
        try {
            WritableSheet sheet = workbook.createSheet("Project", 0);
            WritableCellFormat cf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true));
            Collabs user = srvProjectManager.getDaoCollabs().getById(pd.getCreator());
            Contact contact = srvProjectManager.getDaoContact().getById(pd.getContact());

            sheet.addCell(new Label(0, 0, "REFERENCE", cf));
            sheet.addCell(new Label(1, 0, "STATUS", cf));
            sheet.addCell(new Label(2, 0, "TYPE", cf));
            sheet.addCell(new Label(3, 0, "USER", cf));
            sheet.addCell(new Label(4, 0, "HANDLING COMPANY", cf));
            sheet.addCell(new Label(5, 0, "CREATE DATE", cf));
            sheet.addCell(new Label(6, 0, "EXPIRED DATE", cf));
            sheet.addCell(new Label(7, 0, "CUSTOMER", cf));
            sheet.addCell(new Label(8, 0, "VESSEL", cf));
            sheet.addCell(new Label(9, 0, "CONTACT", cf));
            sheet.addCell(new Label(0, 1, !Strings.isNullOrEmpty(pd.getReference()) ? pd.getReference() : ""));
            sheet.addCell(new Label(1, 1, !Strings.isNullOrEmpty(pd.getStatus()) ? pd.getStatus() : ""));
            sheet.addCell(new Label(2, 1, !Strings.isNullOrEmpty(pd.getType()) ? pd.getType() : ""));
            sheet.addCell(new Label(3, 1, (user != null) ? user.getSurname() + " " + user.getName() : ""));
            sheet.addCell(new Label(4, 1, !Strings.isNullOrEmpty(pd.getCompany()) ? pd.getCompany() : ""));
            sheet.addCell(new Label(5, 1, (pd.getCreated() != null) ? pd.getCreated().toString() : ""));
            sheet.addCell(new Label(6, 1, (pd.getExpired() != null) ? pd.getExpired().toString() : ""));
            sheet.addCell(new Label(7, 1, !Strings.isNullOrEmpty(pd.getCustomer()) ? pd.getCustomer() : ""));
            sheet.addCell(new Label(8, 1, !Strings.isNullOrEmpty(pd.getVesselName()) ? pd.getVesselName() : ""));
            sheet.addCell(new Label(9, 1, (contact != null) ? contact.getSurname() + " " + contact.getName() : ""));
        } catch (WriteException ex) {
            Logger.getLogger(ProjectCommon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void BMSSheet(WritableWorkbook workbook, ProjectManagerService srvProjectManager, BillMaterialService bms) {
        try {
            WritableSheet sheet = workbook.createSheet("Bill Materials or Services", 1);
            WritableCellFormat cf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true));
            List<BillMaterialServiceItem> items = srvProjectManager.getDaoBillMaterialServiceItem().getByBillMaterialService(bms.getId());

            sheet.addCell(new Label(0, 0, "NAME", cf));
            sheet.addCell(new Label(1, 0, "COMPLETE", cf));
            sheet.addCell(new Label(2, 0, "NOTES", cf));
            sheet.addCell(new Label(0, 1, !Strings.isNullOrEmpty(bms.getName()) ? bms.getName() : ""));
            sheet.addCell(new Label(1, 1, (bms.getComplete().equals(Boolean.TRUE)) ? "TRUE" : "FALSE"));
            sheet.addCell(new Label(2, 1, !Strings.isNullOrEmpty(bms.getNote()) ? bms.getNote() : ""));

            if (items != null && !items.isEmpty()) {
                Integer rowItems = 4;

                sheet.mergeCells(0, 2, 2, 2).getTopLeft();
                sheet.addCell(new Label(0, 3, "IMNO", cf));
                sheet.addCell(new Label(1, 3, "DESCRIPTION", cf));
                sheet.addCell(new Label(2, 3, "QUANTITY", cf));
                sheet.addCell(new Label(3, 3, "PRICE", cf));
                sheet.addCell(new Label(4, 3, "AVAILABILITY", cf));
                for (BillMaterialServiceItem bmsi : items) {
                    Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                    if (item != null) {
                        sheet.addCell(new Label(0, rowItems, !Strings.isNullOrEmpty(item.getImno()) ? item.getImno() : ""));
                        sheet.addCell(new Label(1, rowItems, !Strings.isNullOrEmpty(item.getDescription()) ? item.getDescription() : ""));
                        sheet.addCell(new Label(2, rowItems, bmsi.getQuantity() != null ? bmsi.getQuantity().toString() : ""));
                        sheet.addCell(new Label(3, rowItems, bmsi.getPrice() != null ? bmsi.getPrice().toString() : ""));
                        sheet.addCell(new Label(4, rowItems++, bmsi.getAvailable() != null ? bmsi.getAvailable().toString() : ""));
                    }
                }
            }
        } catch (WriteException ex) {
            Logger.getLogger(ProjectCommon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void RQSheet(WritableWorkbook workbook, ProjectManagerService srvProjectManager, RequestQuotation rq, Integer sheetNo) {
        try {
            WritableSheet sheet = workbook.createSheet("Request for Quotation " + rq.getId(), sheetNo);
            WritableCellFormat cf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true));
            List<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());

            sheet.addCell(new Label(0, 0, "NAME", cf));
            sheet.addCell(new Label(1, 0, "COMPLETE", cf));
            sheet.addCell(new Label(2, 0, "DISCARD", cf));
            sheet.addCell(new Label(3, 0, "SUPPLIER", cf));
            sheet.addCell(new Label(4, 0, "CURRENCY", cf));
            sheet.addCell(new Label(5, 0, "MATERIAL COST", cf));
            sheet.addCell(new Label(6, 0, "DELIVERY COST", cf));
            sheet.addCell(new Label(7, 0, "OTHER EXPENSES", cf));
            sheet.addCell(new Label(8, 0, "GRAND TOTAL", cf));
            sheet.addCell(new Label(9, 0, "NOTES", cf));
            sheet.addCell(new Label(10, 0, "SUPPLIER NOTES", cf));
            sheet.addCell(new Label(0, 1, !Strings.isNullOrEmpty(rq.getName()) ? rq.getName() : ""));
            sheet.addCell(new Label(1, 1, rq.getComplete().toString()));
            sheet.addCell(new Label(2, 1, rq.getDiscard().toString()));
            sheet.addCell(new Label(3, 1, !Strings.isNullOrEmpty(rq.getSupplier()) ? rq.getSupplier() : ""));
            sheet.addCell(new Label(4, 1, rq.getCurrency() != null ? getCurrencyById(rq.getCurrency()) : ""));
            sheet.addCell(new Label(5, 1, rq.getMaterialCost() != null ? rq.getMaterialCost().toString() : ""));
            sheet.addCell(new Label(6, 1, rq.getDeliveryCost() != null ? rq.getDeliveryCost().toString() : ""));
            sheet.addCell(new Label(7, 1, rq.getOtherExpenses() != null ? rq.getOtherExpenses().toString() : ""));
            sheet.addCell(new Label(8, 1, rq.getGrandTotal() != null ? rq.getGrandTotal().toString() : ""));
            sheet.addCell(new Label(9, 1, !Strings.isNullOrEmpty(rq.getNote()) ? rq.getNote() : ""));
            sheet.addCell(new Label(10, 1, !Strings.isNullOrEmpty(rq.getSupplierNote()) ? rq.getSupplierNote() : ""));

            if (items != null && !items.isEmpty()) {
                Integer rowItems = 4;

                sheet.mergeCells(0, 2, 10, 2).getTopLeft();
                sheet.addCell(new Label(0, 3, "IMNO", cf));
                sheet.addCell(new Label(1, 3, "DESCRIPTION", cf));
                sheet.addCell(new Label(2, 3, "UNIT PRICE", cf));
                sheet.addCell(new Label(3, 3, "DISCOUNT", cf));
                sheet.addCell(new Label(4, 3, "NET TOTAL", cf));
                sheet.addCell(new Label(5, 3, "AVAILABILITY", cf));
                for (RequestQuotationItem rqi : items) {
                    BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(rqi.getBillMaterialServiceItem());

                    if (bmsi != null) {
                        Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                        if (item != null) {
                            sheet.addCell(new Label(0, rowItems, !Strings.isNullOrEmpty(item.getImno()) ? item.getImno() : ""));
                            sheet.addCell(new Label(1, rowItems, !Strings.isNullOrEmpty(item.getDescription()) ? item.getDescription() : ""));
                            sheet.addCell(new Label(2, rowItems, rqi.getUnitPrice() != null ? rqi.getUnitPrice().toString() : ""));
                            sheet.addCell(new Label(3, rowItems, rqi.getDiscount() != null ? rqi.getDiscount().toString() : ""));
                            sheet.addCell(new Label(4, rowItems, rqi.getTotal() != null ? rqi.getTotal().toString() : ""));
                            sheet.addCell(new Label(5, rowItems++, rqi.getAvailability() != null ? rqi.getAvailability().toString() : ""));
                        }
                    }
                }
            }
        } catch (WriteException ex) {
            Logger.getLogger(ProjectCommon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void QSheet(WritableWorkbook workbook, ProjectManagerService srvProjectManager, Quotation q, Integer sheetNo) {
        try {
            WritableSheet sheet = workbook.createSheet("Quotation " + ((q.getLocation() != null) ? getLocationNameById(q.getLocation()) : q.getId()),
                    sheetNo);
            WritableCellFormat cf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, true));
            List<QuotationItem> items = srvProjectManager.getDaoQuotationItem().getByQuotation(q.getId());

            sheet.addCell(new Label(0, 0, "NAME", cf));
            sheet.addCell(new Label(1, 0, "COMPLETE", cf));
            sheet.addCell(new Label(2, 0, "DISCARD", cf));
            sheet.addCell(new Label(3, 0, "CUSTOMER", cf));
            sheet.addCell(new Label(3, 0, "CUSTOMER REFERENCE", cf));
            sheet.addCell(new Label(4, 0, "CURRENCY", cf));
            sheet.addCell(new Label(5, 0, "AVAILABILITY", cf));
            sheet.addCell(new Label(6, 0, "DELIVERY", cf));
            sheet.addCell(new Label(7, 0, "PACKING", cf));
            sheet.addCell(new Label(8, 0, "PAYMENT", cf));
            sheet.addCell(new Label(9, 0, "VALIDITY", cf));
            sheet.addCell(new Label(10, 0, "LOCATION", cf));
            sheet.addCell(new Label(11, 0, "GRAND TOTAL", cf));
            sheet.addCell(new Label(12, 0, "WELCOME", cf));
            sheet.addCell(new Label(13, 0, "REMARK", cf));
            sheet.addCell(new Label(14, 0, "NOTES", cf));
            sheet.addCell(new Label(0, 1, !Strings.isNullOrEmpty(q.getName()) ? q.getName() : ""));
            sheet.addCell(new Label(1, 1, q.getComplete().toString()));
            sheet.addCell(new Label(2, 1, q.getDiscard().toString()));
            sheet.addCell(new Label(3, 1, !Strings.isNullOrEmpty(q.getCustomer()) ? q.getCustomer() : ""));
            sheet.addCell(new Label(3, 1, !Strings.isNullOrEmpty(q.getCustomerReference()) ? q.getCustomerReference() : ""));
            sheet.addCell(new Label(4, 1, q.getCurrency() != null ? getCurrencyById(q.getCurrency()) : ""));
            sheet.addCell(new Label(5, 1, !Strings.isNullOrEmpty(q.getAvailability()) ? q.getAvailability() : ""));
            sheet.addCell(new Label(6, 1, !Strings.isNullOrEmpty(q.getDelivery()) ? q.getDelivery() : ""));
            sheet.addCell(new Label(7, 1, !Strings.isNullOrEmpty(q.getPacking()) ? q.getPacking() : ""));
            sheet.addCell(new Label(8, 1, !Strings.isNullOrEmpty(q.getPayment()) ? q.getPayment() : ""));
            sheet.addCell(new Label(9, 1, !Strings.isNullOrEmpty(q.getValidity()) ? q.getValidity() : ""));
            sheet.addCell(new Label(10, 1, q.getLocation() != null ? getLocationNameById(q.getLocation()) : ""));
            sheet.addCell(new Label(11, 1, q.getGrandTotal() != null ? q.getGrandTotal().toString() : ""));
            sheet.addCell(new Label(12, 1, !Strings.isNullOrEmpty(q.getWelcome()) ? q.getWelcome() : ""));
            sheet.addCell(new Label(13, 1, !Strings.isNullOrEmpty(q.getRemark()) ? q.getRemark() : ""));
            sheet.addCell(new Label(14, 1, !Strings.isNullOrEmpty(q.getNote()) ? q.getNote() : ""));

            if (items != null && !items.isEmpty()) {
                Integer rowItems = 4;

                sheet.mergeCells(0, 2, 10, 2).getTopLeft();
                sheet.addCell(new Label(0, 3, "IMNO", cf));
                sheet.addCell(new Label(1, 3, "DESCRIPTION", cf));
                sheet.addCell(new Label(2, 3, "UNIT PRICE", cf));
                sheet.addCell(new Label(3, 3, "DISCOUNT", cf));
                sheet.addCell(new Label(4, 3, "TOTAL", cf));
                for (QuotationItem qi : items) {
                    BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(qi.getBillMaterialServiceItem());

                    if (bmsi != null) {
                        Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                        if (item != null) {
                            sheet.addCell(new Label(0, rowItems, !Strings.isNullOrEmpty(item.getImno()) ? item.getImno() : ""));
                            sheet.addCell(new Label(1, rowItems, !Strings.isNullOrEmpty(item.getDescription()) ? item.getDescription() : ""));
                            sheet.addCell(new Label(2, rowItems, qi.getUnitPrice() != null ? qi.getUnitPrice().toString() : ""));
                            sheet.addCell(new Label(3, rowItems, qi.getDiscount() != null ? qi.getDiscount().toString() : ""));
                            sheet.addCell(new Label(4, rowItems, qi.getTotal() != null ? qi.getTotal().toString() : ""));
                            rowItems++;
                        }
                    }
                }
            }
        } catch (WriteException ex) {
            Logger.getLogger(ProjectCommon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String ExportExcel(ProjectManagerService srvProjectManager, Long pdId) {
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);

        if (pd != null) {
            String strPath = "C:\\ProjectManager\\";
            File f = new File(strPath);
            if (f.exists() == false) {
                f.mkdirs();
            }
            String strStoreFile = "Project" + pd.getReference().replace("/", "_") + ".xls";

            try {
                WritableWorkbook workbook = Workbook.createWorkbook(new File(strPath + strStoreFile));
                BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pd.getId());

                ProjectSheet(workbook, srvProjectManager, pd);
                if (bms != null) {
                    BMSSheet(workbook, srvProjectManager, bms);

                    List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                    if (rqs != null && !rqs.isEmpty()) {
                        Integer sheetNo = 2;

                        for (RequestQuotation rq : rqs) {
                            RQSheet(workbook, srvProjectManager, rq, sheetNo++);
                        }
                    }
                    List<Quotation> qs = srvProjectManager.getDaoQuotation().getByBillMaterialService(bms.getId());

                    if (qs != null && !qs.isEmpty()) {
                        Integer sheetNo = 2;

                        for (Quotation q : qs) {
                            QSheet(workbook, srvProjectManager, q, sheetNo++);
                        }
                    }
                }
                workbook.write();
                workbook.close();
            } catch (IOException | WriteException e) {
                return "error create xls file: " + strPath + strStoreFile;
            }

            return strStoreFile;
        }

        return "no found project with id:" + pdId;
    }
}
