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
import java.io.FileNotFoundException;
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

    private String getCurrencyById(Integer id) {
        String currency = CurrencyEnum.EUR.toString();

        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.getId().equals(id)) {
                currency = value.toString();
            }
        }

        return currency;
    }

    private String createContent(Project p) {
        Map<String, String> content = new HashMap<>();
        List<Item> items = srvProjectManager.getDaoItem().getAll();
        String response = "";

        if (items != null && !items.isEmpty()) {
            response = "<option value='-1'>Select</option>";
            for (Item item : items) {
                response += "<option value='" + item.getId() + "'>" + item.getImno() + "[" + item.getQuantity() +
                "," + item.getPrice() + "]</option>";
            }
        }
        content.put("items", response);

        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());
        Long pdId = null;

        response = "";
        if (pds != null && !pds.isEmpty()) {
            pdId = pds.get(0).getId();
            Set<ProjectBillModel> pbKeys = getProjectBillIds();

            for (ProjectDetail pd : pds) {
                response += "<option value='" + pd.getId() + "'>" + pd.getReference() + "-" + pd.getCompany() +
                "</option>";
                if (pbKeys == null) {
                    pushProjectBillMaterial(pd.getId());
                }
            }
            content.put("subprojects", response);
            content.put("projectBill", createProjectBill(new ProjectBillModel(pdId, null)));
            content.put("projectBillItems", createProjectBillItems(new ProjectBillModel(pdId, null)));
        } else {
            content.put("subprojects", response);
            content.put("projectBill", response);
            content.put("projectBillItems", response);
        }

        return new Gson().toJson(content);
    }

    private void pushProjectBillMaterial(Long projectDetailId) {
        List<ProjectBill> pbs = srvProjectManager.getDaoProjectBill().getByProject(projectDetailId);

        if (pbs != null && !pbs.isEmpty()) {
            for (ProjectBill pb : pbs) {
                Integer location = getLocationIdByName(pb.getLocation());

                pb.setClassSave("button alarm");
                setVirtualProjectBill(pb, location);
                List<ProjectBillItem> pbis = srvProjectManager.getDaoProjectBillItem().getByProjectBill(pb.getId());

                if (pbis != null && !pbis.isEmpty()) {
                    for (ProjectBillItem pbi : pbis) {
                        pbi.setClassRefresh("button alarm");
                        pbi.setClassSave("button alarm");
                        setVirtualProjectBillItem(new ProjectBillModel(pb.getProject(), location), pbi);
                    }
                }
            }
        }
    }

    private String createProjectBill(ProjectBillModel _pbm) {
        String response = "";
        Set<ProjectBillModel> pbIds = (_pbm != null) ? getPBMKeysByPDId(_pbm) : getProjectBillIds();

        if (pbIds != null && !pbIds.isEmpty()) {
            for (ProjectBillModel pbm : pbIds) {
                ProjectBill pb = getProjectBill(pbm);
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pb.getProject());
                String subproject = (pd != null) ? pd.getReference() : "";
                Long pdid = (pd != null) ? pd.getId() : 0l;

                response += "<tr>" +
                "<td id='m_total_cost'>" + pb.getTotalCost() + "</td>" +
                "<td id='m_average_discount'>" + pb.getAverangeDiscount() + "</td>" +
                "<td id='m_sale_price'>" + pb.getTotalSalePrice() + "</td>" +
                "<td id='m_net_sale_price'>" + pb.getTotalNetPrice() + "</td>" +
                "<td id='m_currency'>" + getCurrencyById(pb.getCurrency()) + "</td>" +
                "<td id='location" + pdid + "'>" + pb.getLocation() + "</td>" +
                "<td id='m_subproject'>" + subproject + "</td>" +
                "<td><input type='button' value='Replace' class='button' onclick='replaceProjectBillItems(" + pdid +
                "," + pbm.getLocation() + ")'></td>\n" +
                "<td><input type='button' value='Save' class='" + pb.getClassSave() + "' onclick='saveProjectBill(" +
                pdid + "," + pbm.getLocation() + ")'></td>\n" +
                "<td><input type='button' value='Delete' class='button' id='delete' onclick='delete(" + pdid + "," +
                pbm.getLocation() + ")'></td>\n" +
                "</tr>";
            }
        }

        return response;
    }

    private String getCurrencies(Long pdId, Integer location, Long itemId, Integer currency, Boolean first) {
        String currencies = "<select id='currency" + pdId + location + itemId + "' style='width:50px;'>\n";

        for (CurrencyEnum cu : CurrencyEnum.values()) {
            if (cu.getId().equals(currency)) {
                currencies += "<option value='" + cu.getId() + "' selected='selected'>" + cu.toString() + "</option>";
            } else if (first == true) {
                currencies += "<option value='" + cu.getId() + "'>" + cu.toString() + "</option>";
            }
        }
        currencies += "</select>";

        return currencies;
    }

    private String getLocations() {
        String locations = "<label class='custom-select'><select id='select-new-location'>\n";

        for (BillLocationEnum bl : BillLocationEnum.values()) {
            if (!bl.equals(BillLocationEnum.GREECE)) {
                if (bl.equals(BillLocationEnum.CHINA)) {
                    locations += "<option value='" + bl.getId() + "' selected='selected'>" + bl.getName() + "</option>";
                } else {
                    locations += "<option value='" + bl.getId() + "'>" + bl.getName() + "</option>";
                }
            }
        }
        locations += "</select></label>";

        return locations;
    }

    private String createProjectBillItems(ProjectBillModel _pbm) {
        String response = "";
        Set<ProjectBillModel> pbms = (_pbm != null) ? getPBMIKeysByPDId(_pbm) : getProjectBillDetailIds();

        if (pbms != null && !pbms.isEmpty()) {
            for (ProjectBillModel pbm : pbms) {
                Collection<ProjectBillItem> pbItems = getProjectBillItems(pbm);
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pbm.getId());
                Integer count = 0;

                if (pbItems != null && !pbItems.isEmpty()) {
                    for (ProjectBillItem pbItem : pbItems) {
                        Item item = srvProjectManager.getDaoItem().getById(pbItem.getItem());
                        Stock stock = (item != null) ? srvProjectManager.getDaoStock().getById(item.getLocation()) :
                              null;
                        String imno = (item != null) ? item.getImno() : pbItem.getItemImno();
                        String stockName = (stock != null) ? stock.getLocation() : "";
                        Long itemId = (item != null) ? item.getId() : pbItem.getItem();
                        Integer itemQuantity = (item != null) ? item.getQuantity() : pbItem.getAvailable();
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
                        Integer currency = (pbItem.getCurrency() == null) ? CurrencyEnum.EUR.getId() : pbItem.
                                getCurrency();
                        Boolean firstItem = count.equals(0);

                        count++;
                        response +=
                        "<tr>" +
                        "<td>" + imno + "</td>" +
                        "<td>" + stockName + "</td>" +
                        "<td id='quantity" + pbm.getId() + pbm.getLocation() + itemId +
                        "' style='background: #333;color:#E7E5DC'>" +
                        "<div contenteditable></div>" + quantity + "</td>" +
                        "<td id='cost" + pbm.getId() + pbm.getLocation() + itemId +
                        "' style='background: #333;color:#E7E5DC'>" +
                        "<div contenteditable></div>" + cost + "</td>" +
                        "<td id='total_cost" + pbm.getId() + pbm.getLocation() + itemId + "'>" + totalCost + "</td>" +
                        "<td id='percentage" + pbm.getId() + pbm.getLocation() + itemId +
                        "' style='background: #333;color:#E7E5DC'>" +
                        "<div contenteditable></div>" + percentage + "</td>" +
                        "<td id='discount" + pbm.getId() + pbm.getLocation() + itemId +
                        "' style='background: #333;color:#E7E5DC'>" +
                        "<div contenteditable></div>" + discount + "</td>" +
                        "<td id='sale_price" + pbm.getId() + pbm.getLocation() + pbm.getLocation() + itemId + "'>" +
                        salePrice + "</td>" +
                        "<td id='total_sale_price" + pbm.getId() + pbm.getLocation() + itemId + "'>" + totalSalePrice +
                        "</td>" +
                        "<td id='total_net_price" + pbm.getId() + pbm.getLocation() + itemId + "'>" + totalNetPrice +
                        "</td>" +
                        "<td>" + getCurrencies(pbm.getId(), pbm.getLocation(), itemId, currency, firstItem) + "</td>" +
                        "<td>" + pd.getReference() + "</td>" +
                        "<td>" + getLocationNameById(pbm.getLocation()) + "</td>" +
                        "<td><input type='button' id='Edit" + pbm.getId() + pbm.getLocation() + itemId +
                        "' value='Edit' class='button' onclick='editValues(" + pbm.getId() + "," + pbm.getLocation() +
                        "," + itemId + ")' onmouseover='viewLocation(" + pbm.getId() + "," + pbm.getLocation() + "," +
                        itemId + ")' title=''></td>" +
                        "<td><input type='button' value='Refresh' class='" + pbItem.getClassRefresh() +
                        "' onclick='refreshValues(" + pbm.getId() + "," + pbm.getLocation() + "," + itemId + "," +
                        itemQuantity + ")'></td>" +
                        "<td><input type='button' value='Save' class='" + pbItem.getClassSave() +
                        "' onclick='saveValues(" + pbm.getId() + "," + pbm.getLocation() + "," + itemId + ")'></td>" +
                        "<td><input type='button' value='Remove' class='button' onclick='removeValues(" + pbm.getId() +
                        "," + pbm.getLocation() + "," + itemId + ")'></td>" + "</tr>";
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
            Integer currency = 0;

            for (ProjectBillItem item : items) {
                totalCost = (item.getTotalCost() != null) ? totalCost.add(item.getTotalCost()) : BigDecimal.ZERO;
                averangeDiscount = (item.getDiscount() != null) ? averangeDiscount.add(item.getDiscount()) :
                BigDecimal.ZERO;
                totalSalePrice = (item.getTotalSalePrice() != null) ? totalSalePrice.add(item.getTotalSalePrice()) :
                BigDecimal.ZERO;
                totalNetPrice = (item.getTotalNetPrice() != null) ? totalNetPrice.add(item.getTotalNetPrice()) :
                BigDecimal.ZERO;
                item.setClassSave("button");
                currency = item.getCurrency();
            }

            averangeDiscount = averangeDiscount.divide(new BigDecimal(items.size()), 2);

            return new ProjectBill.Builder()
                    .setProject(pdId)
                    .setTotalCost(totalCost)
                    .setAverangeDiscount(averangeDiscount)
                    .setTotalSalePrice(totalSalePrice)
                    .setTotalNetPrice(totalNetPrice)
                    .setCurrency(currency)
                    .build();
        } else {
            removeVirtualProjectBill(new ProjectBillModel(pdId, location));

            return null;
        }
    }

    private String getLocationNameById(Integer id) {
        for (BillLocationEnum pl : BillLocationEnum.values()) {
            if (pl.getId().equals(id)) {
                return pl.getName();
            }
        }

        return "";
    }

    private Integer getLocationIdByName(String name) {
        for (BillLocationEnum pl : BillLocationEnum.values()) {
            if (pl.getName().equals(name)) {
                return pl.getId();
            }
        }

        return 0;
    }

    @RequestMapping(value = "/project-bill")
    public String ProjectBill(Project p, Model model) {
        this.setTitle("Projects - Bill of Material or Service");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/ProjectBill.jsp");
        setHeaderInfo(model);
        p = srvProjectManager.getDaoProject().getById(p.getId());

        if (p != null) {
            model.addAttribute("p_id", p.getId());
            model.addAttribute("project_reference", p.getReference());
            model.addAttribute("button_save_pdf", "<input type='button' value='Save PDF' class='button' onclick='savePDF(\"" +
                    p.getReference() + "\")'>");
            model.addAttribute("button_save_excel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS(\"" +
                    p.getReference() + "\")'>");
            model.addAttribute("button_send_email", "<input type='button' value='Send eMail' class='button' onclick='sendEmail(\"" +
                    p.getId() + "\")'>");
        }

        return "index";
    }

    @RequestMapping(value = "/project-bill/item/insert")
    public @ResponseBody
    String itemInsert(Long pdId, Integer location, ProjectBillItem pbi, Model model) {
        if (!pbi.getItem().equals(0l) && getProjectBillItem(new ProjectBillModel(pdId, location), pbi.getItem()) == null) {
            Item item = srvProjectManager.getDaoItem().getById(pbi.getItem());
            ProjectBillItem temp = getFirstProjectBillItem(new ProjectBillModel(pdId, location));
            Integer currency = (temp != null) ? temp.getCurrency() : null;

            if (item != null) {
                pbi.setAvailable(item.getQuantity());
                pbi.setPrice(item.getPrice());
                pbi.setItemImno(item.getImno());
                pbi.setItemDescription(item.getDescription());
                pbi.setCurrency((currency != null) ? currency : pbi.getCurrency());
                setVirtualProjectBillItem(new ProjectBillModel(pdId, location), pbi);
            }
        }

        return createProjectBillItems(new ProjectBillModel(pdId, null));
    }

    @RequestMapping(value = "/project-bill/item/save")
    public @ResponseBody
    String itemSave(Long pdId, Integer location, ProjectBillItem pbi, Model model) {
        ProjectBillItem temp = getProjectBillItem(new ProjectBillModel(pdId, location), pbi.getItem());
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
            editVirtualProjectBillItem(new ProjectBillModel(pdId, location), temp);
        } else {
            editVirtualProjectBillItem(new ProjectBillModel(pdId, location), pbi);
        }
        ProjectBill pb = getAverangeDiscount(pdId, location);

        pb.setClassSave("button alarm");
        pb.setLocation(getLocationNameById(location));
        setVirtualProjectBill(pb, location);
        saveVirtualProjectBillItem(new ProjectBillModel(pdId, location));
        content.put("projectBill", createProjectBill(new ProjectBillModel(pdId, null)));
        content.put("projectBillItems", createProjectBillItems(new ProjectBillModel(pdId, null)));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/item/remove")
    public @ResponseBody
    String itemRemove(Long pdId, Integer location, ProjectBillItem pbi, Model model) {
        Map<String, String> content = new HashMap<>();

        removeVirtualProjectBillItem(pdId, location, pbi.getItem());
        setVirtualProjectBill(getAverangeDiscount(pdId, location), location);
        content.put("projectBill", createProjectBill(new ProjectBillModel(pdId, null)));
        content.put("projectBillItems", createProjectBillItems(new ProjectBillModel(pdId, null)));

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

            if (changeCurrencyFirstItem(new ProjectBillModel(pdId, location), pbi)) {
                editCurrencyVirtualProjectBillItems(new ProjectBillModel(pdId, location), pbi.getCurrency());
            }
            temp.setCurrency(pbi.getCurrency());
            temp.setClassRefresh(pbi.getClassRefresh());
            temp.setClassSave(pbi.getClassSave());
            editVirtualProjectBillItem(new ProjectBillModel(pdId, location), temp);

            return createProjectBillItems(new ProjectBillModel(pdId, null));
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
            editVirtualProjectBillItem(new ProjectBillModel(pdId, location), temp);

            return createProjectBillItems(new ProjectBillModel(pdId, null));
        }

        return "";
    }

    @RequestMapping(value = "/project-bill/save")
    public @ResponseBody
    String saveProjectBill(Integer location, ProjectBill pb, Model model) {
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pb.getProject());
        ProjectBillModel pbm = new ProjectBillModel(pb.getProject(), location);
        ProjectBill vpb = getProjectBill(pbm);
        Map<String, String> content = new HashMap<>();

        if (pd != null) {
            pd.setStatus(ProjectStatusEnum.PROJECT_BILL.toString());
            srvProjectManager.getDaoProjectDetail().edit(pd);
            model.addAttribute("pd_id", pd.getId());

            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());

            if (p != null) {
                p.setStatus(pd.getStatus());
                srvProjectManager.getDaoProject().edit(p);
                model.addAttribute("project_reference", p.getReference());
            }
        }
        pb.setLocation(getLocationNameById(location));
        pb.setCurrency(vpb.getCurrency());
        pb = srvProjectManager.getDaoProjectBill().add(pb);

        setVirtualProjectBillItemBillId(pbm, pd.getId());
        srvProjectManager.getDaoProjectBillItem().add(getProjectBillItems(pbm));
        clearVirtualProjectBill(pbm);

//        return "index";
        Set<ProjectBillModel> pbKeys = getProjectBillIds();
        
        if (pbKeys != null && !pbKeys.isEmpty()) {
            String response = "";

            for (ProjectBillModel pbKey : pbKeys) {
                vpb = getProjectBill(pbKey);
                ProjectDetail dbpd = srvProjectManager.getDaoProjectDetail().getById(vpb.getProject());
                response += "<option value='" + dbpd.getId() + "'>" + dbpd.getReference() + "-" + dbpd.getCompany() +
                "</option>";
            }
            content.put("subproject", response);
            content.put("projectBill", createProjectBill(new ProjectBillModel(pb.getProject(), null)));
            content.put("projectBillItems", createProjectBillItems(new ProjectBillModel(pb.getProject(), null)));
        } else {
            Project p = srvProjectManager.getDaoProject().getById(pd.getProject());
            content.put("billHeader", "<h1>Bill of Material - REF:" + p.getReference() + " - Complete</h1>");
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/savepdf")
    public String savePDF(String projectReference) throws JRException, FileNotFoundException {
//        model.addAttribute("button_action_message", "save the pdf by named " + projectReference.replace("/", "_") + ".pdf");
        
        JasperReport.createProjectBillReport(projectReference.replace("/", "_") + ".pdf");
        
        return "";
    }

    @RequestMapping(value = "/project-bill/search")
    public @ResponseBody
    String openProjects(ProjectDetail pd, Integer offset, Integer size, String mode) {
        return searchProject(srvProjectManager, null, "new", pd, null, null, offset, size, mode);
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
        Map<String, String> content = new HashMap<>();
        String htmlCompany = "<select id='select-new-subproject-company'>\n" +
               "<option value='none' selected='selected'>Select</option>\n";
        String htmlType = "<select id='select-new-subproject-type'>\n" +
               "<option value='none' selected='selected'>Select</option>\n";

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

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/item/stock")
    public @ResponseBody
    String itemStockInsert(Long pdId, Integer location, Item item) {
        ProjectBillItem temp = getFirstProjectBillItem(new ProjectBillModel(pdId, location));
        Integer currency = (temp != null) ? temp.getCurrency() : null;

        item.setCurrency((currency != null) ? currency : CurrencyEnum.EUR.getId().longValue());
        item.setStartQuantity(item.getQuantity());
        item.setOfferQuantity(item.getQuantity());
        item.setInventoryQuantity(item.getQuantity());
        item.setStartPrice(item.getPrice());
        item.setInventoryPrice(item.getPrice());
        item.setInventoryEdit(Boolean.FALSE);
        item = srvProjectManager.getDaoItem().add(item);
        if (item.getId() != null) {
            setVirtualProjectBillItem(new ProjectBillModel(pdId, BillLocationEnum.GREECE.getId()),
                                      new ProjectBillItem.Builder().setAvailable(item.getQuantity()).setQuantity(0)
                                      .setPrice(item.getPrice()).setItem(item.getId()).setItemImno(item.getImno())
                                      .setItemDescription(item.getDescription()).setCurrency(item.getCurrency()
                                      .intValue()).setClassRefresh("button alarm").setClassSave("button alarm").build());
        }

        return createProjectBillItems(new ProjectBillModel(pdId, null));
    }

    @RequestMapping(value = "/project-bill/item/nostock")
    public @ResponseBody
    String itemNoStockInsert(Long pdId, Integer location, Item item, Model model) {
        ProjectBillItem temp = getFirstProjectBillItem(new ProjectBillModel(pdId, location));
        Integer currency = (temp != null) ? temp.getCurrency() : null;

        item.setCurrency((currency != null) ? currency : CurrencyEnum.EUR.getId().longValue());
        item.setId(getNextNoStockItemId());
        if (item.getId() != null) {
            setVirtualProjectBillItem(new ProjectBillModel(pdId, location), new ProjectBillItem.Builder()
                                      .setAvailable(item.getQuantity()).setPrice(item.getPrice()).setItem(item.getId())
                                      .setItemImno(item.getImno()).setItemDescription(item.getDescription())
                                      .setCurrency(item.getCurrency().intValue()).setClassRefresh("button alarm")
                                      .setClassSave("button alarm").build());
        }

        return createProjectBillItems(new ProjectBillModel(pdId, null));
    }

    @RequestMapping(value = "/project-bill/content")
    public @ResponseBody
    String getContent(Project pd) {
        return createContent(pd);
    }

    @RequestMapping(value = "/project-bill/save-subproject")
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
                String reference = dbpdReference.substring(0, dbpdReference.lastIndexOf("/"));
                String subid = dbpdReference.substring(dbpdReference.lastIndexOf("/") + 1);
                Integer nextsubid = Integer.parseInt(subid) + 1;

                pd.setReference(reference + "/" + nextsubid.toString());
                pd.setCustomer(dbpd.getCustomer());
                pd.setVessel(dbpd.getVessel());
                pd.setContact(dbpd.getContact());

                pd = srvProjectManager.getDaoProjectDetail().add(pd);
            }

//            List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pd.getProject());
//            String response = "";
//
//            if (pds != null && !pds.isEmpty()) {
//                for (ProjectDetail _pd : pds) {
//                    response += "<option value='" + _pd.getId() + "'>" + _pd.getReference() + "-" + _pd.getCompany() +
//                    "</option>";;
//                }
//                content.put("projectDetails", response);
//                content.put("projectId", pd.getProject().toString());
//            }
//
//            return new Gson().toJson(content);
            return "index";
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
                Item item = srvProjectManager.getDaoItem().getById(pbi.getItem());

                if (item == null) {
                    item = new Item.Builder().setId(pbi.getItem()).setImno(pbi.getItemImno()).build();
                }

                response += "<div class='slideThree'><input type='checkbox' id='" + item.getId() +
                "' name='checkbox-project' value='" + item.getId() + "'><label for='" + item.getId() + "'>" +
                item.getImno() + "</label></div>";
            }
        }
        return response;
    }

    @RequestMapping(value = "/project-bill/get-project-bill-items")
    public @ResponseBody
    String changeProjectBillItems(Long id) {
        Map<String, String> content = new HashMap<>();
        ProjectBillModel pbm = new ProjectBillModel(id, null);

        content.put("projectBill", createProjectBill(pbm));
        content.put("projectBillItems", createProjectBillItems(pbm));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/add-project-bill-items")
    public @ResponseBody
    String addProjectBillItems(Long id, Integer location, Long[] itemIds) {
        if (id != null && location != null && itemIds != null) {
            ProjectBillModel pbmGreece = new ProjectBillModel(id, BillLocationEnum.GREECE.getId());

            for (Long itemId : itemIds) {
                ProjectBillItem item = new ProjectBillItem.Builder().build(getProjectBillItem(pbmGreece, itemId));

                if (item != null) {
                    item.setClassRefresh("button");
                    item.setClassSave("button alarm");
                    setVirtualProjectBillItem(new ProjectBillModel(id, location), item);
                }
            }

            return createProjectBillItems(new ProjectBillModel(id, null));
        }

        return "";
    }

    @RequestMapping(value = "/project-bill/item/view-location")
    public @ResponseBody
    String viewLocation(Integer location) {
        if (location != null) {
            return getLocationNameById(location);
        }

        return "";
    }
}
