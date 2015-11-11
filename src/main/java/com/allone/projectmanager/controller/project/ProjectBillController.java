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
import com.allone.projectmanager.entities.ProjectBill;
import com.allone.projectmanager.entities.ProjectBillItem;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Stock;
import com.allone.projectmanager.enums.BillLocationEnum;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.ProjectBillModel;
import com.allone.projectmanager.tools.JasperReport;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class ProjectBillController extends ProjectCommon {

    private static final Logger logger = Logger.getLogger(ProjectBillController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    public ProjectManagerService getSrvProjectManager() {
        return srvProjectManager;
    }

    public void setSrvProjectManager(ProjectManagerService srvProjectManager) {
        this.srvProjectManager = srvProjectManager;
        this.srvProjectManager.loadPropertyValues();
    }

    private String createContent(ProjectDetail pd) {
        Map<String, String> content = new HashMap<>();
        List<Item> items = srvProjectManager.getDaoItem().getAll();

        String response = "";

        if (items != null && !items.isEmpty()) {
            for (Item item : items) {
                response += "<option value='" + item.getId() + "'>" + item.getImno() + "[" + item.getQuantity() +
                "," + item.getPrice() + "]</option>";;
            }
        }
        content.put("items", response);

        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pd.getProject());

        response = "";
        if (pds != null && !pds.isEmpty()) {
            for (ProjectDetail _pd : pds) {
                response += "<option value='" + _pd.getId() + "'>" + _pd.getReference() + "-" + _pd.getCompany() +
                "</option>";;
            }
        }
        content.put("subprojects", response);
        content.put("projectBill", createProjectBill());
        content.put("projectBillItems", createProjectBillItems());

        return new Gson().toJson(content);
    }

    private String createProjectBill() {
        String response = "";
        Set<ProjectBillModel> pdIds = getProjectBillIds();

        if (pdIds != null && !pdIds.isEmpty()) {
            for (ProjectBillModel pdm : pdIds) {
                ProjectBill pb = getProjectBill(pdm);
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pb.getProject());
                String subproject = (pd != null) ? pd.getReference() : "";
                Long pdid = (pd != null) ? pd.getId() : 0l;

                response +=
                "<tr>" +
                "<td id='bill-projectdetail-id' style='display:none'>" + pdid + "</td>" +
                "<td id='bill-projectdetail-id' style='display:none'>" + pdid + "</td>" +
                "<td id='m_total_cost'>" + pb.getTotalCost() + "</td>" +
                "<td id='m_average_discount'>" + pb.getAverangeDiscount() + "</td>" +
                "<td id='m_sale_price'>" + pb.getTotalSalePrice() + "</td>" +
                "<td id='m_net_sale_price'>" + pb.getTotalNetPrice() + "</td>" +
                "<td id='m_currency'>" + pb.getCurrency() + "</td>" +
                "<td id='location" + pdid + "'>" + pb.getLocation() + "</td>" +
                "<td id='m_subproject'>" + subproject + "</td>" +
                "<td><input type='button' value='Replace' class='button' onclick='replaceProjectBillItems(" + pdid +
                ")'></td>\n" +
                "<td><input type='button' value='Save' class='button' id='save' onclick='saveProjectBill()'></td>\n" +
                "<td><input type='button' value='Delete' class='button' id='delete' onclick='delete()'></td>\n" +
                "<td><input type='button' value='Save PDF' class='button' id='save-pdf' onclick='savePDF()'></td>\n" +
                "<td><input type='button' value='Print PDF' class='button' id='print-pdf' onclick='printPDF()'></td>\n" +
                "<td><input type='button' value='Save Excel' class='button' id='save-xls' onclick='saveXLS()'></td>\n" +
                "<td><input type='button' value='Send eMail' class='button' id='send-email' onclick='sendEmail()'></td>\n" +
                "</tr>";
            }
        }

        return response;
    }

    private String getCurrencies() {
        String currencies = "<select id='select-new-item-company' style='width:50px;'>\n";

        for (CurrencyEnum cu : CurrencyEnum.values()) {
            if (cu.equals(CurrencyEnum.EUR)) {
                currencies += "<option value='" + cu.toString() + "' selected='selected'>" + cu.toString() + "</option>";
            } else {
                currencies += "<option value='" + cu.toString() + "'>" + cu.toString() + "</option>";
            }
        }
        currencies += "</select>";

        return currencies;
    }
    
    private String getLocations() {
        String locations = "<select id='select-new-location' style='width:50px;'>\n";

        for (BillLocationEnum bl : BillLocationEnum.values()) {
            if (bl.equals(BillLocationEnum.GREECE)) {
                locations += "<option value='" + bl.getId() + "' selected='selected'>" + bl.getName() + "</option>";
            } else {
                locations += "<option value='" + bl.getId() + "'>" + bl.getName() + "</option>";
            }
        }
        locations += "</select>";

        return locations;
    }

    private String createProjectBillItems() {
        String response = "";
        Set<ProjectBillModel> pbms = getProjectBillDetailIds();

        if (pbms != null && pbms.isEmpty() == false) {
            for (ProjectBillModel pbm : pbms) {
                Collection<ProjectBillItem> pbItems = getProjectBillItems(pbm);
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pbm.getId());

                if (pbItems != null && !pbItems.isEmpty()) {
                    for (ProjectBillItem pbItem : pbItems) {
                        Item item = srvProjectManager.getDaoItem().getById(pbItem.getItem());
                        String imno = (item != null) ? item.getImno() : pbItem.getItemImno();
                        Long itemId = (item != null) ? item.getId() : pbItem.getItem();
                        Integer itemQuantity = (item != null) ? item.getQuantity() : pbItem.getAvailable();
                        BigDecimal itemPrice = (item != null) ? item.getPrice() : pbItem.getPrice();
                        String quantity = (pbItem.getQuantity() != null) ? pbItem.getQuantity().toString() : "";
                        String cost = (pbItem.getCost() != null) ? pbItem.getCost().toString() : "";
                        String totalCost = (pbItem.getTotalCost() != null) ? pbItem.getTotalCost().toString() : "";
                        String percentage = (pbItem.getPercentage() != null) ? pbItem.getPercentage().toString() : "";
                        String discount = (pbItem.getDiscount() != null) ? pbItem.getDiscount().toString() : "";
                        String salePrice = (pbItem.getSalePrice() != null) ? pbItem.getSalePrice().toString() : "";
                        String totalSalePrice = (pbItem.getTotalSalePrice() != null) ? pbItem.getTotalSalePrice().
                               toString() : "";
                        String totalNetPrice = (pbItem.getTotalNetPrice() != null) ? pbItem.getTotalNetPrice().
                               toString() : "";

                        response +=
                        "<tr>" +
                        "<td>" + imno + "</td>" +
                        "<td id='available" + pbm.getId() + pbm.getLocation() + itemId + "'>" + itemQuantity + "</td>" +
                        "<td id='price" + pbm.getId() + pbm.getLocation() + itemId + "'>" + itemPrice + "</td>" +
                        "<td id='quantity" + pbm.getId() + pbm.getLocation() + itemId + "'>" + "<div contenteditable></div>" + quantity +
                        "</td>" +
                        "<td id='cost" + pbm.getId() + pbm.getLocation() + itemId + "'><div contenteditable></div>" + cost + "</td>" +
                        "<td id='total_cost" + pbm.getId() + pbm.getLocation() + itemId + "'>" + totalCost + "</td>" +
                        "<td id='percentage" + pbm.getId() + pbm.getLocation() + itemId + "'><div contenteditable></div>" + percentage + "</td>" +
                        "<td id='discount" + pbm.getId() + pbm.getLocation() + itemId + "'><div contenteditable></div>" + discount + "</td>" +
                        "<td id='sale_price" + pbm.getId() + pbm.getLocation() + pbm.getLocation() + itemId + "'>" + salePrice + "</td>" +
                        "<td id='total_sale_price" + pbm.getId() + pbm.getLocation() + itemId + "'>" + totalSalePrice + "</td>" +
                        "<td id='total_net_price" + pbm.getId() + pbm.getLocation() + itemId + "'>" + totalNetPrice + "</td>" +
                        "<td id='currency" + pbm.getId() + pbm.getLocation() + itemId + "'>" + getCurrencies() + "</td>" +
                        "<td>" + pd.getReference() + "</td>" +
                        "<td><input type='button' value='Edit' class='button' onclick='editValues(" + pbm.getId() + "," + pbm.getLocation() + "," +
                        itemId +
                        ")'></td>" +
                        "<td><input type='button' value='Refresh' class='" + pbItem.getClassRefresh() +
                        "' onclick='refreshValues(" + pbm.getId() + "," + pbm.getLocation() + "," + itemId + ")'></td>" +
                        "<td><input type='button' value='Save' class='" + pbItem.getClassSave() +
                        "' onclick='saveValues(" +
                        pbm.getId() + "," + pbm.getLocation() + "," + itemId + ")'></td>" +
                        "<td><input type='button' value='Remove' class='button' onclick='removeValues(" + pbm.getId() + "," + pbm.getLocation() + "," +
                        itemId + ")'></td>" +
                        "</tr>";
                    }
                }
            }
        }

        return response;
    }

    private ProjectBill getAverangeDiscount(Long pdId, Integer location) {
        Collection<ProjectBillItem> items = getProjectBillItems(new ProjectBillModel(pdId, location));

        if (items != null && !items.isEmpty()) {
            BigDecimal totalCost = BigDecimal.ZERO;
            BigDecimal averangeDiscount = BigDecimal.ZERO;
            BigDecimal totalSalePrice = BigDecimal.ZERO;
            BigDecimal totalNetPrice = BigDecimal.ZERO;

            for (ProjectBillItem item : items) {
                totalCost = (item.getTotalCost() != null) ? totalCost.add(item.getTotalCost()) : BigDecimal.ZERO;
                averangeDiscount = (item.getDiscount() != null) ? averangeDiscount.add(item.getDiscount()) :
                BigDecimal.ZERO;
                totalSalePrice = (item.getTotalSalePrice() != null) ? totalSalePrice.add(item.getTotalSalePrice()) :
                BigDecimal.ZERO;
                totalNetPrice = (item.getTotalNetPrice() != null) ? totalNetPrice.add(item.getTotalNetPrice()) :
                BigDecimal.ZERO;
            }

            averangeDiscount = averangeDiscount.divide(new BigDecimal(items.size()));

            return new ProjectBill.Builder()
                    .setProject(pdId)
                    .setTotalCost(totalCost)
                    .setAverangeDiscount(averangeDiscount)
                    .setTotalSalePrice(totalSalePrice)
                    .setTotalNetPrice(totalNetPrice)
                    .setCurrency(CurrencyEnum.EUR.toString())
                    .build();
        } else {
            removeVirtualProjectBill(pdId, location);

            return null;
        }
    }
    
    private String getNameById(Integer id) {
        for (BillLocationEnum pl : BillLocationEnum.values()) {
            if (pl.getId().equals(id)) {
                return pl.getName();
            }
        }
        
        return "";
    }

    @RequestMapping(value = "/project-bill")
    public String ProjectBill(Project p, Long pdId, Model model) {
        this.setTitle("Projects - Bill of Material");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/ProjectBill.jsp");
        setHeaderInfo(model);
        model.addAttribute("pd_id", pdId);
        model.addAttribute("p_id", p.getId());
        model.addAttribute("project_reference", p.getReference());

        return "index";
    }

    @RequestMapping(value = "/project-bill/item/insert")
    public @ResponseBody
    String itemInsert(Long pdId, Integer location, ProjectBillItem pbi, Model model) {
        if (!pbi.getItem().equals(0l) && getProjectBillItem(new ProjectBillModel(pdId, location), pbi.getItem()) == null) {
            setVirtualProjectBillItem(pdId, location, pbi);
        }

        return createProjectBillItems();
    }

    @RequestMapping(value = "/project-bill/item/save")
    public @ResponseBody
    String itemSave(Long pdId, Integer locationId, ProjectBillItem pbi, Model model) {
        ProjectBillItem temp = getProjectBillItem(new ProjectBillModel(pdId, locationId), pbi.getItem());
        Map<String, String> content = new HashMap<>();

        if (temp != null) {
            Integer quantity = pbi.getQuantity();
            BigDecimal cost = pbi.getCost();

            if (!quantity.equals(0)) {
                temp.setQuantity(pbi.getQuantity());
            }
            if (!cost.equals(BigDecimal.ZERO)) {
                temp.setCost(pbi.getCost());
            }

            temp.setClassRefresh(pbi.getClassRefresh());
            temp.setClassSave(pbi.getClassSave());
            temp.setCurrency(pbi.getCurrency());
            editVirtualProjectBillItem(pdId, temp);
        } else {
            editVirtualProjectBillItem(pdId, pbi);
        }
        ProjectBill pb = getAverangeDiscount(pdId, locationId);
        
        pb.setLocation(getNameById(locationId));
        setVirtualProjectBill(pb, locationId);
        content.put("projectBill", createProjectBill());
        content.put("projectBillItems", createProjectBillItems());

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/item/remove")
    public @ResponseBody
    String itemRemove(Long pdId, Integer locationId, ProjectBillItem pbi, Model model) {
        Map<String, String> content = new HashMap<>();

        removeVirtualProjectBillItem(pdId, locationId, pbi.getItem());
        setVirtualProjectBill(getAverangeDiscount(pdId, locationId), locationId);
        content.put("projectBill", createProjectBill());
        content.put("projectBillItems", createProjectBillItems());

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/item/refresh")
    public @ResponseBody
    String itemRefresh(Long pdId, Integer location, ProjectBillItem pbi, Model model) {
        Boolean findValues = Boolean.FALSE;
        ProjectBillItem temp = getProjectBillItem(new ProjectBillModel(pdId, location), pbi.getItem());

        if (temp != null) {
            Integer quantity = pbi.getQuantity();
            BigDecimal cost = pbi.getCost();
            BigDecimal percentage = pbi.getPercentage();
            BigDecimal discount = pbi.getDiscount();
            BigDecimal percent = new BigDecimal(100);

            if (quantity != null && !quantity.equals(0)) {
                temp.setQuantity(quantity);
                findValues = Boolean.TRUE;
            } else {
                findValues = Boolean.FALSE;
            }
            if (cost != null && !cost.equals(BigDecimal.ZERO)) {
                temp.setCost(cost);
                findValues = Boolean.TRUE;
            } else {
                findValues = Boolean.FALSE;
            }
            if (percentage != null && !percentage.equals(BigDecimal.ZERO)) {
                temp.setPercentage(percentage);
            }
            if (discount != null && !discount.equals(BigDecimal.ZERO)) {
                temp.setDiscount(discount);
            }
            if (findValues.equals(Boolean.TRUE)) {
                temp.setTotalCost(temp.getCost().multiply(new BigDecimal(temp.getQuantity())));
                if (temp.getPercentage() != null && temp.getDiscount() != null) {
                    BigDecimal percentagePrice = temp.getCost().multiply(temp.getPercentage().divide(percent));
                    BigDecimal discountPrice = BigDecimal.ONE.subtract(temp.getDiscount().divide(percent));
                    BigDecimal sumSalePrice = temp.getCost().add(percentagePrice);
                    BigDecimal finalValue = sumSalePrice.divide(discountPrice, 2, RoundingMode.HALF_UP);

                    temp.setSalePrice(finalValue);
                } else if (temp.getPercentage() != null) {
                    BigDecimal percentagePrice = temp.getCost().multiply(temp.getPercentage().divide(percent));

                    temp.setSalePrice(temp.getCost().add(percentagePrice));
                } else if (temp.getDiscount() != null) {
                    BigDecimal discountPrice = BigDecimal.ONE.subtract(temp.getDiscount().divide(percent));

                    temp.setSalePrice(temp.getCost().divide(discountPrice));
                } else {
                    temp.setSalePrice(temp.getCost());
                }
            }

            if (findValues.equals(Boolean.TRUE) && temp.getSalePrice() != null) {
                temp.setTotalSalePrice(temp.getSalePrice().multiply(new BigDecimal(temp.getQuantity())));
            }

            if (findValues.equals(Boolean.TRUE)) {
                if (temp.getTotalSalePrice() != null) {
                    if (temp.getDiscount() != null) {
                        temp.setTotalNetPrice(temp.getTotalSalePrice().subtract(temp.getTotalSalePrice().multiply(temp.
                                getDiscount().divide(percent))));
                    } else {
                        temp.setTotalNetPrice(temp.getTotalSalePrice());
                    }
                }
            }

            temp.setClassRefresh(pbi.getClassRefresh());
            temp.setClassSave(pbi.getClassSave());
            editVirtualProjectBillItem(pdId, temp);

            return createProjectBillItems();
        }

        return "";
    }

    @RequestMapping(value = "/project-bill/item/edit")
    public @ResponseBody
    String Save(Long pdId, Integer location, ProjectBillItem pbi) {
        ProjectBillItem temp = getProjectBillItem(new ProjectBillModel(pdId, location), pbi.getItem());

        if (temp != null) {
            temp.setClassRefresh(pbi.getClassRefresh());
            temp.setClassSave(pbi.getClassSave());
            editVirtualProjectBillItem(pdId, temp);

            return createProjectBillItems();
        }

        return "";
    }

    @RequestMapping(value = "/project-bill/save")
    public @ResponseBody
    String Save(ProjectBill pb, Model model) {
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pb.getProject());

        if (pd != null) {
            pd.setStatus(ProjectStatusEnum.PROJECT_BILL.toString());
            srvProjectManager.getDaoProjectDetail().edit(pd);

            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

            if (p != null) {
                p.setStatus(pd.getStatus());
                srvProjectManager.getDaoProject().edit(p);
            }
        }
        pb = srvProjectManager.getDaoProjectBill().add(pb);
        setVirtualProjectBillItemBillId(pb.getProject(), pd.getId());
        srvProjectManager.getDaoProjectBillItem().add(getProjectBillItems(pb.getId()));
        clearVirtualProjectBill();
        model.addAttribute("pd_id", 0);
        model.addAttribute("project_reference", "");

        return "index";
    }

    @RequestMapping(value = "/project-bill/createpdf")
    public String createPDF(ProjectBillController pb, Model model) throws JRException {
        JasperReport.createProjectBillReport(getSrvProjectManager().getPathProjectBill(), getUser().
                                             getProject_reference().replace("/", "_") + ".pdf");

        return "";
    }

    @RequestMapping(value = "/project-bill/search")
    public @ResponseBody
    String openProjects(ProjectDetail pd, Integer offset, Integer size, String mode) {
        return searchProject(srvProjectManager, pd, offset, size, mode);
    }

    @RequestMapping(value = "/project-bill/add-item")
    public @ResponseBody
    String addItems() {
        List<Stock> stocks = srvProjectManager.getDaoStock().getAll(0, Integer.MAX_VALUE);
        List<Company> suppliers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.SUPPLIER.toString());
        Map<String, String> content = new HashMap<>();
        String htmlStock = "<select id='select-item-location'>\n" +
               "<option value='-1' selected='selected'>Select Location</option>\n";
        String htmlSupplier = "<select id='select-item-supplier'>\n" +
               "<option value='-1' selected='selected'>Select Supplier</option>\n";

        if (stocks != null && !stocks.isEmpty()) {
            htmlStock += stocks.stream()
            .map((stock) -> "<option value='" + stock.getId() + "'>" + stock.getLocation() + "</option>\n")
            .reduce(htmlStock, String::concat);
            htmlStock += "</select>";
            content.put("location", htmlStock);
        }
        if (suppliers != null && !suppliers.isEmpty()) {
            for (Company supplier : suppliers) {
                htmlSupplier += "<option value='" +
                supplier.getName() + "'>" + supplier.getName() + "</option>\n";
            }
            htmlStock += "</select>";
            content.put("supplier", htmlSupplier);
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/new-subproject")
    public @ResponseBody
    String newSubProject() {
        Date expired = new Date(new Date().getTime() + getUser().getProject_expired() * 86400000l);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
//        List<Vessel> vessels = srvProjectManager.getDaoVessel().getAll();
//        List<Company> customers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.CUSTOMER.toString());
//        List<Contact> contacts = srvProjectManager.getDaoContact().getAll();
        Map<String, String> content = new HashMap<>();
        String htmlCompany = "<select id='select-new-subproject-company'>\n" +
               "<option value='none' selected='selected'>Select</option>\n";
        String htmlType = "<select id='select-new-subproject-type'>\n" +
               "<option value='none' selected='selected'>Select</option>\n";
//        String htmlVessel = "<select id='select-new-subproject-vessel'>\n" +
//               "<option value='-1' selected='selected'>Select</option>\n";
//        String htmlCustomer = "<select id='select-new-subproject-customer'>\n" +
//               "<option value='none' selected='selected'>Select</option>\n";
//        String htmlContact = "<select id='select-new-subproject-contact'>\n" +
//               "<option value='-1' selected='selected'>Select</option>\n";

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

//        if (vessels != null && !vessels.isEmpty()) {
//            for (Vessel vessel : vessels) {
//                htmlVessel += "<option value='" + vessel.getId() + "'>" + vessel.getName() + "</option>\n";
//            }
//            htmlVessel += "</select>";
//            content.put("vessel", htmlVessel);
//        }
//
//        if (customers != null && !customers.isEmpty()) {
//            for (Company customer : customers) {
//                htmlCustomer += "<option value='" + customer.getName() + "'>" + customer.getName() + "</option>\n";
//            }
//            htmlCustomer += "</select>";
//            content.put("customer", htmlCustomer);
//        }
//
//        if (contacts != null && !contacts.isEmpty()) {
//            for (Contact contact : contacts) {
//                htmlContact += "<option value='" + contact.getId() + "'>" + contact.getName() + "</option>\n";
//            }
//            htmlContact += "</select>";
//            content.put("contact", htmlContact);
//        }
        content.put("expired", format.format(expired));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/item-stock/insert1")
    public @ResponseBody
    String itemStockInsert(Long pdId, Item item) {
        logger.log(Level.INFO, "-------------itemStockInsert");

//        item.setCurrency(0l);
//        item.setStartQuantity(item.getQuantity());
//        item.setOfferQuantity(item.getQuantity());
//        item.setInventoryQuantity(item.getQuantity());
//        item.setStartPrice(item.getPrice());
//        item.setInventoryPrice(item.getPrice());
//        item.setInventoryEdit(Boolean.FALSE);
//        item = srvProjectManager.getDaoItem().add(item);
//        if (item.getId() != null) {
//            setVirtualProjectBillItem(pdId, new ProjectBillItem.Builder().setAvailable(item.getQuantity())
//                                      .setQuantity(0).setPrice(item.getPrice()).setItem(item.getId())
//                                      .setClassRefresh("button alarm").setClassSave("button alarm").build());
//        }
        return "";//createProjectBillItems();
    }

    @RequestMapping(value = "/project-bill/item-nostock/insert")
    public @ResponseBody
    String itemNoStockInsert(Long pdId, Item item, Model model) {
        item.setId(getNextNoStockItemId());
        if (item.getId() != null) {
            setVirtualProjectBillItem(pdId, new ProjectBillItem.Builder().setAvailable(item.getQuantity())
                                      .setPrice(item.getPrice()).setItem(item.getId()).setClassRefresh("button alarm")
                                      .setClassSave("button alarm").setItemImno(item.getImno()).build());
        }

        return createProjectBillItems();
    }

    @RequestMapping(value = "/project-bill/content")
    public @ResponseBody
    String getContent(ProjectDetail pd) {
        return createContent(pd);
    }

    @RequestMapping(value = "/project-bill/subproject-save")
    public @ResponseBody
    String saveSubproject(ProjectDetail pd) {
        if (pd != null) {
            pd.setStatus(ProjectStatusEnum.CREATE.toString());
            pd.setCreator(getUser().getId());
            pd.setCreated(new Date());

            ProjectDetail dbpd = srvProjectManager.getDaoProjectDetail().getLastByProject(pd.getProject());
            Map<String, String> content = new HashMap<>();

            if (dbpd != null) {
                String dbpdReference = dbpd.getReference();
                String subid = dbpdReference.substring(dbpdReference.lastIndexOf("/") + 1);
                Integer nextsubid = Integer.parseInt(subid) + 1;

                pd.setReference(dbpdReference.replace(subid, nextsubid.toString()));
                pd.setCustomer(dbpd.getCustomer());
                pd.setVessel(dbpd.getVessel());
                pd.setContact(dbpd.getContact());

                pd = srvProjectManager.getDaoProjectDetail().add(pd);
            }

            List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pd.getProject());
            String response = "";

            if (pds != null && !pds.isEmpty()) {
                for (ProjectDetail _pd : pds) {
                    response += "<option value='" + _pd.getId() + "'>" + _pd.getReference() + "-" + _pd.getCompany() +
                    "</option>";;
                }
                content.put("projectDetails", response);
                content.put("projectId", pd.getProject().toString());
            }

            return new Gson().toJson(content);
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/project-bill/replace-project-bill-items")
    public @ResponseBody
    String replaceProjectBillItems(Long pdid, Integer location) {
        String response = getLocations();
        Collection<ProjectBillItem> pbis = getProjectBillItems(new ProjectBillModel(pdid, location));

        if (pbis != null && !pbis.isEmpty()) {
            for (ProjectBillItem pbi : pbis) {
                Item item = (pbi.getItem() != null) ?
                     srvProjectManager.getDaoItem().getById(pbi.getItem()) :
                     new Item.Builder().setId(pbi.getItem()).setImno(pbi.getItemImno()).build();
                
                response += "<div class='slideThree'><input type='checkbox' id='" + item.getId() +
                "' name='checkbox-project' value='" +
                item.getId() + "'><label for='" + item.getId() + "'>" + item.getImno() + "</label></div>";
            }
        }
        return response;
    }

}
