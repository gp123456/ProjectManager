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
import com.allone.projectmanager.tools.JasperReport;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private String createContent(Project p) {
        Map<String, String> content = new HashMap<>();
        List<Item> items = srvProjectManager.getDaoItem().getAll();
        String response = "";

        if (items != null && !items.isEmpty()) {
            response = "<option value='-1'>Select</option>";
            response = items.stream()
            .map((item) -> "<option value='" + item.getId() + "'>" + item.getDescription() + "[" + item.getImno() + "]</option>")
            .reduce(response, String::concat);
        }
        content.put("items", response);

        List<Company> suppliers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.SUPPLIER.name());

        if (suppliers != null && !suppliers.isEmpty()) {
            response = "<option value='none'>Select</option>";
            response = suppliers.stream().map((supplier) -> "<option value='" + supplier.getName() + "'>" + supplier.getName() + "</option>").reduce(response, String::concat);
            content.put("suppliers", response);
        }

        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());

        response = "";
        if (pds != null && !pds.isEmpty()) {
            Long pdId = pds.get(0).getId();
            Vessel vessel = srvProjectManager.getDaoVessel().getById(pds.get(0).getVessel());
            Contact contact = srvProjectManager.getDaoContact().getById(pds.get(0).getContact());

            for (ProjectDetail pd : pds) {
                response += "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>";
                pushBillMaterialService(pd.getId());
            }

            String[] bmsInfo = createBillMaterialService(pdId);
            content.put("subprojects", response);
            content.put("billMaterialService", bmsInfo[0]);
            content.put("note", bmsInfo[1]);
            content.put("billMaterialServiceItems", createBillMaterialServiceItems(pdId));
            content.put("type", pds.get(0).getType());
            content.put("company", pds.get(0).getCompany());
            content.put("customer", pds.get(0).getCustomer());
            if (vessel != null) {
                content.put("vessel", vessel.getName());
            }
            if (contact != null) {
                content.put("contact", contact.getSurname() + " " + contact.getName());
            }
        } else {
            content.put("subprojects", response);
            content.put("billMaterialService", response);
            content.put("billMaterialServiceItems", response);
        }

        return new Gson().toJson(content);
    }

    private void pushBillMaterialService(Long pdId) {
        BillMaterialService bms = srvProjectManager.getDaoProjectBill().getByProject(pdId);

        if (bms != null) {
            clearVirtualBillMaterialService(pdId);

            List<BillMaterialServiceItem> bmsis = srvProjectManager.getDaoProjectBillItem().getByBillMaterialService(bms.getId());

            bms.setClassSave("button alarm");
            setVirtualBillMaterialService(bms);

            if (bmsis != null && !bmsis.isEmpty()) {
                bmsis.stream().
                        map((bmsi) -> {
                            bmsi.setClassSave("button alarm");
                            return bmsi;
                        }).
                        forEach((bmsi) -> {
                            setVirtualBillMaterialServiceItem(bms.getProject(), bmsi);
                        });
            }
        }
    }

    private String[] createBillMaterialService(Long pdId) {
        String response[] = new String[]{"", ""};

        if (pdId != null) {
            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);

            if (pd != null) {
                BillMaterialService bms = getBillMaterialService(pd.getId());

                if (bms != null) {
                    String notes = (!Strings.isNullOrEmpty(bms.getNote())) ? bms.getNote() : "";
                    String name = (!Strings.isNullOrEmpty(bms.getName())) ? bms.getName() : "";
                    String subproject = (!Strings.isNullOrEmpty(pd.getReference())) ? pd.getReference() : "";

                    response[0] = "<tr>\n" +
                                  "<td id='name" + pdId +
                                  "' style='background: #333;color:#E7E5DC'><div contenteditable></div>" +
                                  name + "</td>\n<td id='subproject'>" +
                                  subproject + "</td>\n";
                    response[1] = notes;
                }
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

                    response +=
                    "<tr>\n" +
                    "<td>" + imno + "</td>\n" +
                    "<td>" + stockName + "</td>\n" +
                    "<td id='quantity" + pdId + itemId + "' style='background: #333;color:#E7E5DC'>" +
                    "<div contenteditable></div>" + quantity + "</td>\n" +
                    "<td><input type='button' value='Edit' class='button' onclick='editItem(" + pdId + "," + itemId + ")'></td>\n" +
                    "<td><input type='button' value='Save' class='" + bmsi.getClassSave() + "' onclick='saveItem(" + pdId + "," + itemId + ")'></td>\n" +
                    "<td><input type='button' value='Remove' class='button' onclick='removeItem(" + pdId + "," + itemId + ")'></td>\n" +
                    "</tr>\n";
                }
            }
        }

        return response;
    }

    @RequestMapping(value = "/bill-material-service")
    public String BillMaterialService(Project p, String mode, Model model) {
        this.setTitle("Projects - Bill of materials or services");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/BillMaterialService.jsp");
        setHeaderInfo(model);
        p = srvProjectManager.getDaoProject().getById(p.getId());

        if (p != null) {
            model.addAttribute("p_id", p.getId());
            model.addAttribute("project_reference", p.getReference());
            model.addAttribute("button_save", "<input type='button' value='Save' class='button alarm' onclick='saveBillMaterialService()'>\n");
            model.addAttribute("button_remove", "<input type='button' value='Remove' class='button alarm' onclick='removeBillMaterialService()'>\n");
            model.addAttribute("button_save_pdf", "<input type='button' value='Save PDF' class='button' onclick='savePDF(\"" + p.getReference() + "\")'>\n");
            model.addAttribute("button_save_excel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS(\"" + p.getReference() + "\")'>\n");
            model.addAttribute("button_send_email", "<input type='button' value='Send eMail' class='button' onclick='sendEmail(\"" + p.getId() + "\")'>\n");
        }

        return "index";
    }

    @RequestMapping(value = "/bill-material-service/item/insert")
    public @ResponseBody
    String insertItem(Long pdId, BillMaterialServiceItem bmsi) {
        if (!bmsi.getItem().equals(0l) && getBillMaterialServiceItem(pdId, bmsi.getItem()) == null) {
            Item item = srvProjectManager.getDaoItem().getById(bmsi.getItem());

            if (item != null) {
                bmsi.setAvailable(item.getQuantity());
                bmsi.setPrice(item.getPrice());
                setVirtualBillMaterialServiceItem(pdId, bmsi);
            }
        }

        return createBillMaterialServiceItems(pdId);
    }

    @RequestMapping(value = "/bill-material-service/item/change")
    public @ResponseBody
    String changeItem(Long itemId) {
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

    @RequestMapping(value = "/bill-material-service/item/save")
    public @ResponseBody
    String itemSave(Long pdId, BillMaterialServiceItem bmsi) {
        BillMaterialServiceItem temp = getBillMaterialServiceItem(pdId, bmsi.getItem());
        Map<String, String> content = new HashMap<>();

        if (temp != null) {
            Integer quantity = bmsi.getQuantity();

            if (!quantity.equals(0)) {
                temp.setQuantity(bmsi.getQuantity());
            }

            temp.setClassSave(bmsi.getClassSave());
            editVirtualBillMaterialServiceItem(pdId, temp);
        } else {
            editVirtualBillMaterialServiceItem(pdId, bmsi);
        }
        if (getBillMaterialService(pdId) == null) {
            setVirtualBillMaterialService(new BillMaterialService.Builder()
                    .setClassSave("button alarm")
                    .setComplete(Boolean.FALSE)
                    .setProject(pdId)
                    .build());
        }
        saveVirtualBillMaterialServiceItem(pdId);

        String[] bmsInfo = createBillMaterialService(pdId);
        content.put("billMaterialService", bmsInfo[0]);
        content.put("note", bmsInfo[1]);
        content.put("billMaterialServiceItems", createBillMaterialServiceItems(pdId));

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
        logger.log(Level.INFO, "remove bms={0}", pdId);

        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);
        Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

        if (pd != null) {
            Map<String, String> content = new HashMap<>();

            BillMaterialService bms = srvProjectManager.getDaoProjectBill().getByProject(pdId);

            if (bms != null) {
                srvProjectManager.getDaoProjectBill().delete(bms);
                srvProjectManager.getDaoProjectBillItem().delete(bms.getId());
                srvProjectManager.getDaoProjectDetail().delete(pd);

                List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());

                logger.log(Level.INFO, "remove project {0}", pds);

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

    @RequestMapping(value = "/bill-material-service/item/edit")
    public @ResponseBody
    String Save(Long pdId, BillMaterialServiceItem bmsi) {
        BillMaterialServiceItem temp = getBillMaterialServiceItem(pdId, bmsi.getItem());

        if (temp != null) {
            temp.setClassSave(bmsi.getClassSave());
            editVirtualBillMaterialServiceItem(pdId, temp);

            return createBillMaterialServiceItems(pdId);
        }

        return "";
    }

    @RequestMapping(value = "/bill-material-service/save")
    public @ResponseBody
    String saveBillMaterialService(BillMaterialService bms) {
        String response = "";

        if (bms != null) {
            ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(bms.getProject());
            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

            if (p != null && pd != null) {
                p.setStatus(ProjectStatusEnum.PROJECT_BILL.toString());
                srvProjectManager.getDaoProject().edit(p);
                pd.setStatus(ProjectStatusEnum.PROJECT_BILL.toString());
                srvProjectManager.getDaoProjectDetail().edit(pd);
                BillMaterialService _bms = getBillMaterialService(pd.getId());

                if (_bms != null) {
                    _bms.setProject(pd.getId());
                    _bms.setName(bms.getName());
                    _bms.setNote(bms.getNote());
                    _bms.setComplete(Boolean.TRUE);
                    if (_bms.getId() == null) {
                        bms = srvProjectManager.getDaoProjectBill().add(_bms);
                        setVirtualBillMaterialServiceItemBillId(pd.getId(), bms.getId());
                        srvProjectManager.getDaoProjectBillItem().add(getBillMaterialServiceItems(pd.getId()));
                    } else {
                        srvProjectManager.getDaoProjectBill().edit(_bms);
                        setVirtualBillMaterialServiceItemBillId(pd.getId(), _bms.getId());
                        Collection<BillMaterialServiceItem> bmsis = getBillMaterialServiceItems(pd.getId());

                        if (bmsis != null && !bmsis.isEmpty()) {
                            for (BillMaterialServiceItem bmsi : bmsis) {
                                if (bmsi.getId() == null) {
                                    srvProjectManager.getDaoProjectBillItem().add(bmsi);
                                } else {
                                    srvProjectManager.getDaoProjectBillItem().edit(bmsi);
                                }
                            }
                        }
                    }
                    clearVirtualBillMaterialService(pd.getId());
                }
                if (isEmptyBillMaterialService()) {
                    response = "<h1>Bill of Material - REF:" + pd.getReference() + " - Complete</h1>\n";
                } else {
                    response = "index";
                }
            }
        }

        return response;
    }

    @RequestMapping(value = "/bill-material-service/savepdf")
    public String savePDF(String projectReference) throws JRException, FileNotFoundException {
        JasperReport.createProjectBillReport(projectReference.replace("/", "_") + ".pdf");

        return "";
    }

    @RequestMapping(value = "/bill-material-service/search")
    public @ResponseBody
    String openProjects(ProjectDetail pd, Integer offset, Integer size) {
        return searchProject(srvProjectManager, pd, null, null, null, null, offset, size);
    }

    @RequestMapping(value = "/bill-material-service/add-item")
    public @ResponseBody
    String addItems() {
        List<Stock> stocks = srvProjectManager.getDaoStock().getAll(0, Integer.MAX_VALUE);
        List<Company> suppliers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.SUPPLIER.toString());
        Map<String, String> content = new HashMap<>();
        String htmlStock = "<select id='select-item-location'>\n" +
                           "<option value='-1' selected='selected'>Select Location</option>\n";
        String htmlSupplier = "<select id='select-item-supplier'>\n" +
                              "<option value='-1' selected='selected'>Select Supplier</option>\n";

        content.put("currency", createCurrency(CurrencyEnum.EUR));
        if (stocks != null && !stocks.isEmpty()) {
            htmlStock += stocks.stream()
            .map((stock) -> "<option value='" + stock.getId() + "'>" + stock.getLocation() + "</option>\n")
            .reduce(htmlStock, String::concat);
            htmlStock += "</select>";
            content.put("location", htmlStock);
        }
        if (suppliers != null && !suppliers.isEmpty()) {
            htmlSupplier = suppliers.stream().map((supplier) ->
            "<option value='" + supplier.getName() + "'>" + supplier.getName() + "</option>\n")
            .reduce(htmlSupplier, String::concat);
            htmlStock += "</select>";
            content.put("supplier", htmlSupplier);
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/new-subproject")
    public @ResponseBody
    String newSubProject() {
        Date expired = new Date(new Date().getTime() + getUser().getProject_expired() * 86400000l);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        Map<String, String> content = new HashMap<>();
        String htmlCompany = "<select id='subproject-company'>\n" +
                             "<option value='none' selected='selected'>Select</option>\n";
        String htmlType = "<select id='type'>\n" +
                          "<option value='none' selected='selected'>Select</option>\n";
        List<Collabs> collabs = srvProjectManager.getDaoCollab().getByRole(CollabRoleEnum.TECHNICAL.getValue());

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

            htmlTechical = collabs.stream().map((collab) ->
            "<option value='" + collab.getId() + "'>" + collab.getSurname() + " " + collab.getName() + "</option>\n")
            .reduce(htmlTechical, String::concat);
            content.put("technical", htmlTechical);
        }

        String result = new Gson().toJson(content);

        return result;
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
                                              .setAvailable(item.getQuantity())
                                              .setQuantity(0)
                                              .setPrice(item.getPrice())
                                              .setItem(item.getId())
                                              .setClassSave("button alarm")
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
    String saveSubproject(ProjectDetail pd) {
        if (pd != null) {
            pd.setStatus(ProjectStatusEnum.CREATE.toString());
            pd.setCreator(getUser().getId());
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
                setVirtualBillMaterialService(new BillMaterialService.Builder().setClassSave("save alarm").setProject(pd.getId()).build());
            }

            return "index";
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/bill-material-service/change-subproject")
    public @ResponseBody
    String changeSubproject(Long id) {
        Map<String, String> content = new HashMap<>();
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(id);

        if (pd != null) {
            Vessel vessel = srvProjectManager.getDaoVessel().getById(pd.getVessel());
            Contact contact = srvProjectManager.getDaoContact().getById(pd.getContact());
            content.put("type", pd.getType());
            content.put("company", pd.getCompany());
            content.put("customer", pd.getCustomer());
            if (vessel != null) {
                content.put("vessel", vessel.getName());
            }
            if (contact != null) {
                content.put("contact", contact.getSurname() + " " + contact.getName());
            }

            String[] bmsInfo = createBillMaterialService(id);
            content.put("billMaterialService", bmsInfo[0]);
            content.put("note", bmsInfo[1]);
            if (pd.getType().equals(ProjectTypeEnum.SERVICE.name())) {
                content.put("noItems", "true");
            } else {
                content.put("noItems", "false");
                content.put("billMaterialServiceItems", createBillMaterialServiceItems(id));
            }
        }

        return new Gson().toJson(content);
    }
}
