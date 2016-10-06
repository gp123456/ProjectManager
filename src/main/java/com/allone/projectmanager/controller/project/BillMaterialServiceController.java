/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.BillMaterialServiceItem;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Stock;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CollabRoleEnum;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class BillMaterialServiceController extends ProjectCommon {

    private static final Logger logger = Logger.getLogger(BillMaterialServiceController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    public ProjectManagerService getSrvProjectManager() {
        return srvProjectManager;
    }

    public void setSrvProjectManager(ProjectManagerService srvProjectManager) {
        this.srvProjectManager = srvProjectManager;
        this.srvProjectManager.loadPropertyValues();
    }

    private Boolean haveBillMaterialServiceItems(String type) {
        return type.equals(ProjectTypeEnum.SALE.toString());
    }

    private String getBillMaterialTitle(String type, String reference,
            Integer sizeSubprojects) {
        String value = "";

        if (type.equals(ProjectTypeEnum.SALE.toString())) {
            value = "Bill of Materials - REF:" + reference + " - Subprojects:" + sizeSubprojects;
        } else if (type.equals(ProjectTypeEnum.SERVICE.toString())) {
            value = "Bill of Services - REF:" + reference + " - Subprojects:" + sizeSubprojects;
        }

        return value;
    }

    private String getBillMaterialSummary(String type) {
        String value = "";

        if (type.equals(ProjectTypeEnum.SALE.toString())) {
            value = "Bill of Materials Summary";
        } else if (type.equals(ProjectTypeEnum.SERVICE.toString())) {
            value = "Bill of Services Summary";
        }

        return value;
    }

    private String getBillMaterialDetail(String type) {
        String value = "";

        if (type.equals(ProjectTypeEnum.SALE.toString())) {
            value = "Bill of Materials Details";
        } else if (type.equals(ProjectTypeEnum.SERVICE.toString())) {
            value = "Bill of Services Details";
        }

        return value;
    }

    private String selectItems() {
        String values = "";
        List<Item> items = srvProjectManager.getDaoItem().getAll();

        if (items != null && !items.isEmpty()) {
            values = "<option value='-1'>Select</option>";
            values = items.stream().map((item) -> "<option value='" + item.getId() + "'>" + item.getDescription() + "[" + item.getImno()
                    + "][" + item.getQuantity() + "]</option>").reduce(values, String::concat);
        }
        return values;
    }

    private String selectSuppliers() {
        String values = "";
        List<Company> suppliers = srvProjectManager.getDaoCompany()
                .getAll(CompanyTypeEnum.SUPPLIER.name());

        if (suppliers != null && !suppliers.isEmpty()) {
            values = "<option value='none'>Select</option>";
            values = suppliers.stream().map((supplier) -> "<option value='" + supplier.getName() + "'>" + supplier.getName() + "</option>")
                    .reduce(values, String::concat);
        }

        return values;
    }

    private SubProjectInfo selectSubprojects(Long pID) {
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pID);
        SubProjectInfo info = new SubProjectInfo();

        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail pd : pds) {
                info.html += "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>";
                pushBillMaterialService(pd.getId());
            }
            info.pd = pds.get(0);
            info.size = pds.size();
        }

        return info;
    }

    private String createContent(Project p) {
        Map<String, String> content = new HashMap<>();
        SubProjectInfo subprojectValues = selectSubprojects(p.getId());
        ProjectDetail pd = subprojectValues.pd;
        String[] bmsInfo = (pd != null) ? createBillMaterialService(pd.getId()) : null;
        Boolean haveBMSI = (pd != null) ? haveBillMaterialServiceItems(pd.getType()) : Boolean.FALSE;
        Vessel vessel = (pd != null) ? srvProjectManager.getDaoVessel().getById(pd.getVessel()) : null;
        Contact contact = (pd != null) ? srvProjectManager.getDaoContact().getById(pd.getContact()) : null;

        content.put("items", selectItems());
        content.put("suppliers", selectSuppliers());
        content.put("subprojects", subprojectValues.html);
        content.put("billMaterialService", (bmsInfo != null) ? bmsInfo[0] : "");
        content.put("note", (bmsInfo != null) ? bmsInfo[1] : "");
        content.put("noItems", (haveBMSI.equals(Boolean.FALSE)) ? "true" : "false");
        content.put("billMaterialServiceItems", (haveBMSI.equals(Boolean.TRUE)) ? createBillMaterialServiceItems(pd.getId()) : "");
        content.put("type", (pd != null) ? pd.getType() : "");
        content.put("company", (pd != null) ? pd.getCompany() : "");
        content.put("customer", (pd != null) ? pd.getCustomer() : "");
        content.put("vessel", (vessel != null) ? vessel.getName() : "");
        content.put("contact", (contact != null) ? contact.getSurname() + " " + contact.getName() : "");
        content.put("BillMaterialTitle", (pd != null) ? getBillMaterialTitle(pd.getType(), pd.getReference(), subprojectValues.size) : "");
        content.put("BillMaterialSummary", (pd != null) ? getBillMaterialSummary(pd.getType()) : "");
        content.put("BillMaterialDetail", (pd != null) ? getBillMaterialDetail(pd.getType()) : "");

        return new Gson().toJson(content);
    }

    private void pushBillMaterialService(Long pdId) {
        BillMaterialService bms = srvProjectManager.getDaoBillMaterialService()
                .getByProject(pdId);

        if (bms != null) {
            clearVirtualBillMaterialService(pdId);

            List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoBillMaterialServiceItem().getByBillMaterialService(bms.getId());
            final Long _pdId = bms.getProject();

            bms.setClassSave("button alarm");
            setVirtualBillMaterialService(bms);

            if (bmsis != null && !bmsis.isEmpty()) {
                bmsis.stream().forEach((bmsi) -> {
                    setVirtualBillMaterialServiceItem(_pdId, bmsi);
                });
            }
        } else {
            bms = new BillMaterialService.Builder().setProject(pdId).setClassSave("button alarm").build();
            setVirtualBillMaterialService(bms);
        }
    }

    private String[] createBillMaterialService(Long pdId) {
        String response[] = new String[]{"", ""};

        if (pdId != null) {
            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);

            if (pd != null) {
                BillMaterialService bms = getBillMaterialService(pd.getId());

                String notes = (bms != null && !Strings.isNullOrEmpty(bms.getNote())) ? bms.getNote() : "";
                String subproject = (!Strings.isNullOrEmpty(pd.getReference())) ? pd.getReference() : "";

                response[0] = "<tr>\n"
                        + "<td id='subproject'>" + subproject + "</td>\n"
                        + "</tr>\n";
                response[1] = notes;
            }
        }

        return response;
    }

    private String createBillMaterialServiceItems(Long pdId) {
        String response = "";

        if (pdId != null) {
            Collection<BillMaterialServiceItem> bmsis = getBillMaterialServiceItems(pdId);

            if (bmsis != null && !bmsis.isEmpty()) {
                for (BillMaterialServiceItem bmsi : bmsis) {
                    Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());
                    Stock stock = (item != null) ? srvProjectManager.getDaoStock().getById(item.getLocation()) : null;
                    String imno = item.getImno();
                    String stockName = (stock != null) ? stock.getLocation() : "";
                    Long itemId = bmsi.getItem();
                    String quantity = (bmsi.getQuantity() != null) ? bmsi.getQuantity().toString() : "";

                    response += "<tr>\n"
                            + "<td>" + imno + "</td>\n"
                            + "<td>" + stockName + "</td>\n"
                            + "<td id='quantity" + itemId + "' style='background: rgb(247, 128, 128);color:rgba(29, 25, 10, 0.84)'>"
                            + "<div contenteditable></div>" + quantity + "</td>\n"
                            + "<td><input type='button' value='Edit' class='button' onclick='editItem(" + itemId + ")'></td>\n"
                            + "<td><input type='button' value='Remove' class='button' onclick='removeItem(" + pdId + "," + itemId + ")'></td>\n"
                            + "</tr>\n";
                }
            }
        }

        return response;
    }

    private Long getSubprojects(Long pId) {
        Long count = srvProjectManager.getDaoProjectDetail()
                .countByProject(pId);

        return (count == null) ? 0 : count;
    }

    @RequestMapping(value = "/bill-material-service")
    public String BillMaterialService(HttpServletRequest request, Project p, String mode, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                this.setTitle("Projects - Bill of materials or services");
                this.setHeader("header.jsp");
                this.setSide_bar("../project/sidebar.jsp");
                this.setContent("../project/BillMaterialService.jsp");
                setHeaderInfo(session, model);
                p = srvProjectManager.getDaoProject().getById(p.getId());

                if (p != null) {
                    if (p.getStatus().equals(ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString())) {
                        p.setStatus(ProjectStatusEnum.CREATE.toString());
                        srvProjectManager.getDaoProject().edit(p);
                    }
                    setUserProjectId(session.getId(), p.getId());
                    model.addAttribute("p_id", p.getId());
                    model.addAttribute("button_save", "<input type='button' value='Save' class='button alarm' onclick='getBillMaterialServiceItems()'>\n");
                    model.addAttribute("button_remove", "<input type='button' value='Remove' class='button alarm' onclick='removeBillMaterialService()'>\n");
                }

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = "/bill-material-service/item/insert")
    public @ResponseBody
    String insertItem(Long pdId, BillMaterialServiceItem bmsi) {
        if (!bmsi.getItem().equals(0l) && getBillMaterialServiceItem(pdId, bmsi.getItem()) == null) {
            Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

            if (item != null) {
                bmsi.setAvailable(item.getQuantity());
                bmsi.setPrice(item.getPrice().setScale(2, RoundingMode.CEILING));
                setVirtualBillMaterialServiceItem(pdId, bmsi);
            }
        }
        if (getBillMaterialService(pdId) == null) {
            setVirtualBillMaterialService(new BillMaterialService.Builder()
                    .setClassSave("button alarm").setComplete(Boolean.FALSE)
                    .setProject(pdId)
                    .build());
        }

        Map<String, String> content = new HashMap<>();
        String[] bmsInfo = createBillMaterialService(pdId);

        content.put("billMaterialService", bmsInfo[0]);
        content.put("billMaterialServiceItems", createBillMaterialServiceItems(pdId));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/item/view")
    public @ResponseBody
    String viewItem(Long itemId) {
        Map<String, String> content = new HashMap<>();
        String availability = "";
        String price = "";

        if (itemId != null) {
            Item item = srvProjectManager.getDaoItem().getById(itemId);

            if (item != null) {
                availability = item.getQuantity().toString();
                price = item.getPrice().toString();
            }
        }

        content.put("availability", availability);
        content.put("price", price);

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/item/remove")
    public @ResponseBody
    String removeItem(Long pdId, BillMaterialServiceItem bmsi) {
        removeVirtualBillMaterialServiceItem(srvProjectManager, pdId, bmsi.getItem());

        return createBillMaterialServiceItems(pdId);
    }

    @RequestMapping(value = "/bill-material-service/remove")
    public @ResponseBody
    String remove(Long pdId) {
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
        Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

        if (pd != null) {
            Map<String, String> content = new HashMap<>();

            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pdId);

            if (bms != null) {
                srvProjectManager.getDaoBillMaterialService().delete(bms);
                srvProjectManager.getDaoBillMaterialServiceItem().delete(bms.getId());
                srvProjectManager.getDaoProjectDetail().delete(pd);

                List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());

                if (pds == null || pds.isEmpty()) {
                    srvProjectManager.getDaoProject().delete(p);
                }
                clearVirtualBillMaterialService(pdId);

                String[] bmsInfo = createBillMaterialService(pdId);

                content.put("billMaterialService", bmsInfo[0]);
                content.put("note", bmsInfo[1]);
                if (pd.getType().equals(ProjectTypeEnum.SERVICE.name())) {
                    content.put("noItems", "true");
                } else {
                    content.put("noItems", "false");
                    content.put("billMaterialServiceItems", createBillMaterialServiceItems(pdId));
                }

                return new Gson().toJson(content);
            }
        }

        return "";
    }

    @RequestMapping(value = "/bill-material-service/get-bill-material-items")
    public @ResponseBody
    String getBMSItems(Long pdId) {
        Collection<BillMaterialServiceItem> items = getBillMaterialServiceItems(pdId);
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
        Set<Long> bmsi = new HashSet<>();
        Map<String, String> content = new HashMap<>();

        content.put("type", pd.getType());
        if (items != null) {
            items.stream().forEach((item) -> {
                bmsi.add(item.getItem());
            });
            String response = new Gson().toJson(bmsi.toArray(), Long[].class);

            content.put("items", response);
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/save")
    public @ResponseBody
    String saveBillMaterialService(BillMaterialService bms, String quantities) {
        Map<String, String> content = new HashMap<>();
        String response = "";

        if (bms != null) {
            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());
            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

            if (p != null && pd != null) {
                p.setStatus(ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString());
                srvProjectManager.getDaoProject().edit(p);
                pd.setStatus(ProjectStatusEnum.BILL_MATERIAL_SERVICE.toString());
                srvProjectManager.getDaoProjectDetail().edit(pd);
                BillMaterialService _bms = getBillMaterialService(pd.getId());

                if (_bms != null) {
                    _bms.setProject(pd.getId());
                    _bms.setName(pd.getReference() + "-" + pd.getCustomer());
                    _bms.setNote(bms.getNote());
                    _bms.setComplete(Boolean.TRUE);
                    if (_bms.getId() == null) {
                        bms = srvProjectManager.getDaoBillMaterialService().add(_bms);
                        if (!Strings.isNullOrEmpty(quantities)) {
                            setBillMaterialServiceItemInfo(pd.getId(), bms.getId(), quantities);
                            srvProjectManager.getDaoBillMaterialServiceItem().add(getBillMaterialServiceItems(pd.getId()));
                        }
                    } else {
                        srvProjectManager.getDaoBillMaterialService().edit(_bms);
                        if (!Strings.isNullOrEmpty(quantities)) {
                            setBillMaterialServiceItemInfo(pd.getId(), _bms.getId(), quantities);
                            Collection<BillMaterialServiceItem> bmsis = getBillMaterialServiceItems(pd.getId());

                            if (bmsis != null && !bmsis.isEmpty()) {
                                for (BillMaterialServiceItem bmsi : bmsis) {
                                    if (bmsi.getId() == null) {
                                        srvProjectManager.getDaoBillMaterialServiceItem().add(bmsi);
                                    } else {
                                        srvProjectManager.getDaoBillMaterialServiceItem().edit(bmsi);
                                    }
                                }
                            }
                        }
                    }
                    clearVirtualBillMaterialService(pd.getId());
                    List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getCreatedByProjectExceptId(pd.getProject(), pd.getId());
                    response = "<h1>Bill of "
                            + ((pd.getType().equals(ProjectTypeEnum.SALE.toString())) ? "Materials" : "Services")
                            + " - REF:" + pd.getReference() + " - Complete " + ((pds != null && !pds.isEmpty()) ? "back to bill of "
                                    + ((pd.getType().equals(ProjectTypeEnum.SALE.toString())) ? "materials" : "services")
                                    + ", you have incomplete sub-project(s) pending" : "") + "</h1>\n";
                    content.put("header", response);
                    content.put("moreBillMaterialService", (pds != null && !pds.isEmpty()) ? "true" : "false");
                    content.put("location", (pds != null && !pds.isEmpty()) ? "" : "http://localhost:8081/ProjectManager/project/history-new-project");
                }
            }
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/search")
    public @ResponseBody
    String openProjects(ProjectDetail pd, Integer offset, Integer size) {
        return searchProject(srvProjectManager, pd, null, null, offset, size);
    }

    @RequestMapping(value = "/bill-material-service/add-item")
    public @ResponseBody
    String addItems() {
        List<Stock> stocks = srvProjectManager.getDaoStock().getAll(0, Integer.MAX_VALUE);
        List<Company> suppliers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.SUPPLIER.toString());
        Map<String, String> content = new HashMap<>();
        String htmlStock = "<select id='select-item-location'>\n"
                + "<option value='-1' selected='selected'>Select Location</option>\n";
        String htmlSupplier = "<select id='select-item-supplier'>\n"
                + "<option value='-1' selected='selected'>Select Supplier</option>\n";

        content.put("currency", createCurrency(CurrencyEnum.EUR));
        if (stocks != null && !stocks.isEmpty()) {
            htmlStock += stocks.stream().map((stock) -> "<option value='" + stock.getId() + "'>" + stock.getLocation() + "</option>\n")
                    .reduce(htmlStock, String::concat);
            htmlStock += "</select>";
            content.put("location", htmlStock);
        }
        if (suppliers != null && !suppliers.isEmpty()) {
            htmlSupplier = suppliers.stream().map((supplier) -> "<option value='" + supplier.getName() + "'>" + supplier.getName() + "</option>\n")
                    .reduce(htmlSupplier, String::concat);
            htmlStock += "</select>";
            content.put("supplier", htmlSupplier);
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/new-subproject")
    public @ResponseBody
    String newSubProject(HttpServletRequest request) {
        Map<String, String> content = new HashMap<>();

        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                Date expired = new Date(new Date().getTime() + getUser(session.getId()).getProject_expired() * 86400000l);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
                String htmlCompany = "<select id='subproject-company'>\n" + "<option value='none' selected='selected'>Select</option>\n";
                String htmlType = "<select id='type'>\n" + "<option value='none' selected='selected'>Select</option>\n";
                List<Collabs> collabs = srvProjectManager.getDaoCollabs().getByRole(CollabRoleEnum.TECHNICAL.getValue());

                for (OwnCompanyEnum company : OwnCompanyEnum.values()) {
                    htmlCompany += "<option value='" + company.toString() + "'>" + company.toString() + "</option>\n";
                }
                htmlCompany += "</select>";
                content.put("company", htmlCompany);

                for (ProjectTypeEnum type : ProjectTypeEnum.values()) {
                    htmlType += "<option value='" + type.toString() + "'>" + type.toString() + "</option>\n";
                }
                htmlType += "</select>";
                content.put("type", htmlType);

                content.put("expired", format.format(expired));

                if (collabs != null && !collabs.isEmpty()) {
                    String htmlTechical = "<option value='none' selected='selected'>Select</option>\n";

                    htmlTechical = collabs.stream().map((collab) -> "<option value='" + collab.getId() + "'>"
                            + collab.getSurname() + " " + collab.getName() + "</option>\n")
                            .reduce(htmlTechical, String::concat);
                    content.put("technical", htmlTechical);
                }
            }
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/item/stock")
    public @ResponseBody
    String itemStockInsert(Long pdId, Integer location, Item item) {
        item.setStartQuantity(item.getQuantity());
        item.setOfferQuantity(item.getQuantity());
        item.setInventoryQuantity(item.getQuantity());
        item.setStartPrice(item.getPrice());
        item.setInventoryPrice(item.getPrice());
        item.setInventoryEdit(Boolean.FALSE);
        item = srvProjectManager.getDaoItem().add(item);
        if (item.getId() != null) {
            setVirtualBillMaterialServiceItem(pdId,
                    new BillMaterialServiceItem.Builder()
                    .setAvailable(item.getQuantity()).setQuantity(0)
                    .setPrice(item.getPrice()).setItem(item.getId())
                    .build());
        }

        return createBillMaterialServiceItems(pdId);
    }

    @RequestMapping(value = "/bill-material-service/content")
    public @ResponseBody
    String getContent(Project p) {
        return createContent(p);
    }

    @RequestMapping(value = "/bill-material-service/save-subproject")
    public @ResponseBody
    String saveSubproject(HttpServletRequest request, ProjectDetail pd) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                if (pd != null) {
                    pd.setStatus(ProjectStatusEnum.CREATE.toString());
                    pd.setCreator(getUser(session.getId()).getId());
                    pd.setCreated(new Date());

                    ProjectDetail dbpd = srvProjectManager.getDaoProjectDetail().getLastByProject(pd.getProject());

                    if (dbpd != null) {
                        String dbpdReference = dbpd.getReference();
                        String reference = dbpdReference.substring(0, dbpdReference.lastIndexOf("/"));
                        String subid = dbpdReference.substring(dbpdReference.lastIndexOf("/") + 1);
                        Integer nextsubid = Integer.parseInt(subid) + 1;

                        pd.setReference(reference + "/" + nextsubid.toString());
                        pd.setCustomer(dbpd.getCustomer());
                        pd.setVessel(dbpd.getVessel());
                        pd.setContact(dbpd.getContact());

                        pd = srvProjectManager.getDaoProjectDetail().add(pd);
                        setVirtualBillMaterialService(new BillMaterialService.Builder()
                                .setClassSave("save alarm")
                                .setProject(pd.getId())
                                .build());
                    }

                    return "index";
                }
            }
        }

        return "";
    }

    @RequestMapping(value = "/bill-material-service/view-subproject")
    public @ResponseBody
    String viewSubproject(Long id) {
        Map<String, String> content = new HashMap<>();
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(id);

        if (pd != null) {
            Vessel vessel = srvProjectManager.getDaoVessel().getById(pd.getVessel());
            Contact contact = srvProjectManager.getDaoContact().getById(pd.getContact());
            String[] bmsInfo = createBillMaterialService(id);
            Boolean haveBMSI = haveBillMaterialServiceItems(pd.getType());

            content.put("type", pd.getType());
            content.put("company", pd.getCompany());
            content.put("customer", pd.getCustomer());
            content.put("vessel", (vessel != null) ? vessel.getName() : "");
            content.put("contact", (contact != null) ? contact.getSurname() + " " + contact.getName() : "");
            content.put("billMaterialService", bmsInfo[0]);
            content.put("note", bmsInfo[1]);
            content.put("BillMaterialTitle", getBillMaterialTitle(pd.getType(), pd.getReference(), getSubprojects(pd.getProject()).intValue()));
            content.put("BillMaterialSummary", getBillMaterialSummary(pd.getType()));
            content.put("BillMaterialDetail", getBillMaterialDetail(pd.getType()));
            content.put("noItems", (haveBMSI.equals(Boolean.FALSE)) ? "true" : "false");
            content.put("billMaterialServiceItems", (haveBMSI.equals(Boolean.TRUE)) ? createBillMaterialServiceItems(id) : "");
        }

        return new Gson().toJson(content);

    }

    private class SubProjectInfo {

        public String html;
        public ProjectDetail pd;
        public Integer size;

        public SubProjectInfo() {
            html = "";
            pd = null;
            size = 0;
        }
    }
}
