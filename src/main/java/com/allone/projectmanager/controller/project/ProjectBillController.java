/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectBill;
import com.allone.projectmanager.entities.ProjectBillItem;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.Stock;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
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
                response += "<option value='" + _pd.getId() + "'>" + _pd.getReference() + "</option>";;
            }
        }
        content.put("subprojects", response);

        return new Gson().toJson(content);
    }

    private String createProjectBill(ProjectBill pb) {
        String response = "";
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pb.getProject());
        String subproject = (pd != null) ? pd.getReference() : "";
        Long pdid = (pd != null) ? pd.getId() : 0l;

        response +=
        "<tr>" +
        "<td id='bill-projectdetail-id' style='display:none'>" + pdid + "</td>" +
        "<td id='m_total_cost'>" + pb.getTotalCost() + "</td>" +
        "<td id='m_average_discount'>" + pb.getAverangeDiscount() + "</td>" +
        "<td id='m_sale_price'>" + pb.getTotalSalePrice() + "</td>" +
        "<td id='m_net_sale_price'>" + pb.getTotalNetPrice() + "</td>" +
        "<td id='m_currency'>" + pb.getCurrency() + "</td>" +
        "<td id='m_subproject'>" + subproject + "</td>" +
        "<td><input type='button' value='Save' class='button' id='save' onclick='saveProjectBill()'></td>\n" +
        "<td><input type='button' value='Delete' class='button' id='delete' onclick='delete()'></td>\n" +
        "<td><input type='button' value='Save PDF' class='button' id='save-pdf' onclick='savePDF()'></td>\n" +
        "<td><input type='button' value='Print PDF' class='button' id='print-pdf' onclick='printPDF()'></td>\n" +
        "<td><input type='button' value='Save Excel' class='button' id='save-xls' onclick='saveXLS()'></td>\n" +
        "<td><input type='button' value='Send eMail' class='button' id='send-email' onclick='sendEmail()'></td>\n" +
        "</tr>";

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

    private String createProjectBillItems() {
        String response = "";
        Set<Long> pdIds = getProjectBillDetailIds();

        if (pdIds != null && pdIds.isEmpty() == false) {
            for (Long pdId : pdIds) {
                Collection<ProjectBillItem> pbItems = getProjectBillItems(pdId);
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pdId);

                if (pbItems != null && !pbItems.isEmpty()) {
                    for (ProjectBillItem pbItem : pbItems) {
                        Item item = srvProjectManager.getDaoItem().getById(pbItem.getItem());
                        String quantity = (!pbItem.getQuantity().equals(0)) ? pbItem.getQuantity().toString() : "";
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
                        "<td>" + item.getImno() + "</td>" +
                        "<td id='available" + pdId + item.getId() + "'>" + item.getQuantity() + "</td>" +
                        "<td id='price" + pdId + item.getId() + "'>" + item.getPrice() + "</td>" +
                        "<td id='quantity" + pdId + item.getId() + "'>" + "<div contenteditable></div>" + quantity +
                        "</td>" +
                        "<td id='cost" + pdId + item.getId() + "'><div contenteditable></div>" + cost + "</td>" +
                        "<td id='total_cost" + pdId + item.getId() + "'>" + totalCost + "</td>" +
                        "<td id='percentage" + pdId + item.getId() + "'><div contenteditable></div>" + percentage +
                        "</td>" +
                        "<td id='discount" + pdId + item.getId() + "'><div contenteditable></div>" + discount + "</td>" +
                        "<td id='sale_price" + pdId + item.getId() + "'>" + salePrice + "</td>" +
                        "<td id='total_sale_price" + pdId + item.getId() + "'>" + totalSalePrice + "</td>" +
                        "<td id='total_net_price" + pdId + item.getId() + "'>" + totalNetPrice + "</td>" +
                        "<td id='currency" + pdId + item.getId() + "'>" + getCurrencies() + "</td>" +
                        "<td>" + pd.getReference() + "</td>" +
                        "<td><input type='button' value='Edit' class='button' onclick='editValues(" + pdId + "," + item.
                        getId() + ")'></td>" +
                        "<td><input type='button' value='Refresh' class='button' onclick='refreshValues(" + pdId + "," +
                        item.getId() + ")'></td>" +
                        "<td><input type='button' value='Save' class='button' onclick='saveValues(" + pdId + "," + item.
                        getId() + ")'></td>" +
                        "<td><input type='button' value='Remove' class='button' onclick='removeValues(" + pdId + "," +
                        item.getId() + ")'></td>" +
                        "</tr>";
                    }
                }
            }
        }

        return response;
    }

    private ProjectBill getAverangeDiscount(Long pdId) {
        Collection<ProjectBillItem> items = getProjectBillItems(pdId);
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal averangeDiscount = BigDecimal.ZERO;
        BigDecimal totalSalePrice = BigDecimal.ZERO;
        BigDecimal totalNetPrice = BigDecimal.ZERO;

        if (items != null && !items.isEmpty()) {
            for (ProjectBillItem item : items) {
                totalCost = totalCost.add(item.getTotalCost());
                averangeDiscount = averangeDiscount.add(item.getDiscount());
                totalSalePrice = totalSalePrice.add(item.getTotalSalePrice());
                totalNetPrice = totalNetPrice.add(item.getTotalNetPrice());
            }

            averangeDiscount = averangeDiscount.divide(new BigDecimal(items.size()));
        }

        return new ProjectBill.Builder().setProject(pdId).setTotalCost(totalCost).setAverangeDiscount(averangeDiscount)
                .setTotalSalePrice(totalSalePrice).setTotalNetPrice(totalNetPrice).build();
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

    @RequestMapping(value = "/project-bill/new")
    public @ResponseBody
    String newItem(Long pdId, Item item, Model model) {
        if (!item.getId().equals(0l)) {
            setVirtualProjectBillInfo(pdId, item.getId(), srvProjectManager);
        }

        return createProjectBillItems();
    }

    @RequestMapping(value = "/project-bill/new/info")
    public @ResponseBody
    String newItemInfo(Long pdId, ProjectBillItem item, Model model) {
        ProjectBillItem temp = getProjectBillItem(pdId, item.getItem());
        Map<String, String> content = new HashMap<>();

        if (temp != null) {
            Integer quantity = item.getQuantity();
            BigDecimal cost = item.getCost();

            if (!quantity.equals(0)) {
                temp.setQuantity(item.getQuantity());
            }
            if (!cost.equals(BigDecimal.ZERO)) {
                temp.setCost(item.getCost());
            }

            editVirtualProjectBillItem(pdId, temp);
        } else {
            editVirtualProjectBillItem(pdId, item);
        }
        content.put("project_bill", createProjectBill(getAverangeDiscount(pdId)));
        content.put("project_bill_items", createProjectBillItems());

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/remove")
    public @ResponseBody
    String removeItemInfo(Long pdId, ProjectBillItem item, Model model) {
        Map<String, String> content = new HashMap<>();

        removeVirtualProjectBillInfo(pdId, item.getId());
        content.put("project_bill", createProjectBill(getAverangeDiscount(pdId)));
        content.put("project_bill_items", createProjectBillItems());

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/refresh")
    public @ResponseBody
    String refreshValues(Long pdId, ProjectBillItem item, Model model) {
        logger.log(Level.INFO, "--------------{0}", item.toString());

        Boolean findValues = Boolean.FALSE;
        ProjectBillItem temp = getProjectBillItem(pdId, item.getItem());

        if (temp != null) {
            Integer quantity = item.getQuantity();
            BigDecimal cost = item.getCost();
            BigDecimal percentage = item.getPercentage();
            BigDecimal discount = item.getDiscount();
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
                htmlSupplier += "<option value='" + supplier.getName() + "'>" + supplier.getName() + "</option>\n";
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
        List<Vessel> vessels = srvProjectManager.getDaoVessel().getAll();
        List<Company> customers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.CUSTOMER.toString());
        List<Contact> contacts = srvProjectManager.getDaoContact().getAll();
        Map<String, String> content = new HashMap<>();
        String htmlCompany = "<select id='select-new-subproject-company'>\n" +
               "<option value='none' selected='selected'>Select</option>\n";
        String htmlType = "<select id='select-new-subproject-type'>\n" +
               "<option value='none' selected='selected'>Select</option>\n";
        String htmlVessel = "<select id='select-new-subproject-vessel'>\n" +
               "<option value='-1' selected='selected'>Select</option>\n";
        String htmlCustomer = "<select id='select-new-subproject-customer'>\n" +
               "<option value='none' selected='selected'>Select</option>\n";
        String htmlContact = "<select id='select-new-subproject-contact'>\n" +
               "<option value='-1' selected='selected'>Select</option>\n";

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

        if (vessels != null && !vessels.isEmpty()) {
            for (Vessel vessel : vessels) {
                htmlVessel += "<option value='" + vessel.getId() + "'>" + vessel.getName() + "</option>\n";
            }
            htmlVessel += "</select>";
            content.put("vessel", htmlVessel);
        }

        if (customers != null && !customers.isEmpty()) {
            for (Company customer : customers) {
                htmlCustomer += "<option value='" + customer.getName() + "'>" + customer.getName() + "</option>\n";
            }
            htmlCustomer += "</select>";
            content.put("customer", htmlCustomer);
        }

        if (contacts != null && !contacts.isEmpty()) {
            for (Contact contact : contacts) {
                htmlContact += "<option value='" + contact.getId() + "'>" + contact.getName() + "</option>\n";
            }
            htmlContact += "</select>";
            content.put("contact", htmlContact);
        }
        content.put("expired", format.format(expired));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/insert-new-item")
    public @ResponseBody
    String insertNewItem(Long pdId, Item item, Model model) {
        item = srvProjectManager.getDaoItem().add(item);
        if (!item.getId().equals(0l)) {
            setVirtualProjectBillInfo(pdId, item.getId(), srvProjectManager);
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

            ProjectDetail dbpd = srvProjectManager.getDaoProjectDetail().getById(pd.getId());

            if (dbpd != null) {
                String dbpdReference = dbpd.getReference();
                String subid = dbpdReference.substring(dbpdReference.lastIndexOf("/") + 1);
                Integer nextsubid = Integer.parseInt(subid) + 1;

                pd.setReference(dbpdReference.replace(subid, nextsubid.toString()));
            } else {
                pd.setReference(getUser().getProject_reference() + "/A");
            }

            pd.setId(null);
            pd = srvProjectManager.getDaoProjectDetail().add(pd);

            List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pd.getProject());
            String response = "";

            if (pds != null && !pds.isEmpty()) {
                for (ProjectDetail _pd : pds) {
                    response += "<option value='" + _pd.getId() + "'>" + _pd.getReference() + "</option>";;
                }
            }

            return response;
        } else {
            return "";
        }
    }
}
