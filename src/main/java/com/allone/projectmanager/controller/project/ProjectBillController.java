/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.Root;
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
import java.util.Collection;
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

    private static final Logger logger = Logger.getLogger(Root.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    public ProjectManagerService getSrvProjectManager() {
        return srvProjectManager;
    }

    public void setSrvProjectManager(ProjectManagerService srvProjectManager) {
        this.srvProjectManager = srvProjectManager;
        this.srvProjectManager.loadPropertyValues();
    }

    private void setHeader(String title, String sideBar, String content, Boolean itemInfo, Model model) {
        this.setTitle(title);
        this.setSide_bar(sideBar);
        this.setContent(content);
        setHeaderInfo(model);
        if (itemInfo.equals(Boolean.TRUE)) {
            setItemInfo(model, srvProjectManager);
        }
    }

    private String createProjectBill(ProjectBill pb) {
        String response = "";
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pb.getProject());
        String subproject = (pd != null) ? pd.getReference() : "";
        

        response +=
        "<tr>" +
        "<td id='m_total_cost'>" + pb.getTotalCost() + "</td>" +
        "<td id='m_average_discount'>" + pb.getAverangeDiscount() + "</td>" +
        "<td id='m_sale_price'>" + pb.getTotalSalePrice() + "</td>" +
        "<td id='m_net_sale_price'>" + pb.getTotalNetPrice() + "</td>" +
        "<td id='m_currency'>" + pb.getCurrency() + "</td>" +
        "<td id='m_subproject'>" + subproject + "</td>" +
        "<td><input type='button' value='Delete' style='text-align:center; " +
        "vertical-align: middle;' id='delete' onclick='delete()'></td>\n" +
        "<td><input type='button' value='Save PDF' style='text-align:center; " +
        "vertical-align: middle;' id='save-pdf' onclick='savePDF()'></td>\n" +
        "<td><input type='button' value='Print PDF' style='text-align:center; " +
        "vertical-align: middle;' id='print-pdf' onclick='printPDF()'></td>\n" +
        "<td><input type='button' value='Save Excel' style='text-align:center; " +
        "vertical-align: middle;' id='save-xls' onclick='saveXLS()'></td>\n" +
        "<td><input type='button' value='Send eMail' style='text-align:center; " +
        "vertical-align: middle;' id='send-email' onclick='sendEmail()'></td>\n" +
        "</tr>";

        return response;
    }
    
    private String getCurrencies() {
        String currencies = "<select id='select-new-item-company' style='width:50px;'>\n" +
               "<option value='-1' selected='selected'>Select</option>\n";
        
        for (CurrencyEnum cu : CurrencyEnum.values()) {
            currencies += "<option value=\"" + cu.toString()+ "\">" + cu.toString()+ "</option>";
        }
        currencies += "</select>";
        
        return currencies;
    }
    
    private String createProjectBillItems() {
        String response = "";
        Set<Long> projectBillKeys = getProjectBillKeys();

        if (projectBillKeys != null && projectBillKeys.isEmpty() == false) {
            for (Long key : projectBillKeys) {
                ProjectBillItem pbi = getProjectBillItem(key);
                Item item = srvProjectManager.getDaoItem().getById(pbi.getItem());
                Integer quantity = pbi.getQuantity();
                BigDecimal cost = pbi.getCost();
                BigDecimal totalCost = pbi.getTotalCost();
                BigDecimal percentage = pbi.getPercentage();
                BigDecimal discount = pbi.getDiscount();
                BigDecimal salePrice = pbi.getSalePrice();
                BigDecimal totalSalePrice = pbi.getTotalSalePrice();
                BigDecimal totalNetPrice = pbi.getTotalNetPrice();

                response +=
                "<tr>" +
                "<td>" + item.getImno() + "</td>" +
                "<td>" + item.getDescription() + "</td>" +
                "<td id='available" + item.getId() + "'>" + item.getQuantity() + "</td>" +
                "<td>" + item.getPrice() + "</td>" +
                "<td id='quantity" + item.getId() + "'>" + "<div contenteditable></div>" + (!quantity.equals(0) ?
                quantity : "") + "</td>" +
                "<td id='cost" + item.getId() + "'><div contenteditable></div>" + ((cost != null) ? cost : "") + "</td>"
                + "<td id='total_cost" + item.getId() + "'>" + ((totalCost != null) ? totalCost : "") + "</td>" +
                "<td id='percentage" + item.getId() + "'><div contenteditable></div>" + ((percentage != null) ?
                        percentage : "") + "</td>" +
                "<td id='discount" + item.getId() + "'><div contenteditable></div>" + ((discount != null) ?
                        discount : "") + "</td>" +
                "<td id='sale_price" + item.getId() + "'>" + ((salePrice != null) ? salePrice : "") + "</td>" +
                "<td id='total_sale_price" + item.getId() + "'>" + ((totalSalePrice != null) ? totalSalePrice :
                        "") + "</td>" +
                "<td id='total_net_price" + item.getId() + "'>" + ((totalNetPrice != null) ? totalNetPrice :
                        "") + "</td>" +
                "<td id='currency" + item.getId() + "'>" + getCurrencies() + "</td>" +
                "<td id='subproject" + item.getId() + "'></td>" +
                "<td><input type='button' value='Edit' style='text-align:center; vertical-align: middle;' " +
                "onclick='editValues(" + item.getId() + ")'></td>" +
                "<td><input type='button' value='Refresh' style='text-align:center; vertical-align: middle;' " +
                "onclick='changeValues(" + item.getId() + ")'></td>" +
                "<td><input type='button' value='Save' style='text-align:center;vertical-align:middle;' " +
                "onclick='newProjectBillInfo(" + item.getId() + ")'></td>" +
                "<td><input type='checkbox' value='checked' style='text-align:center; vertical-align: middle;' " +
                "onclick='removeProjectBillInfo(" + item.getId() + ")'></td>" +
                "</tr>";
            }
        }

        return response;
    }

    private ProjectBill getAverangeDiscount() {
        Collection<ProjectBillItem> items = getProjectBillItems();
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

        return new ProjectBill.Builder().setTotalCost(totalCost).setAverangeDiscount(averangeDiscount).
                setTotalSalePrice(totalSalePrice).setTotalNetPrice(totalNetPrice).build();
    }

    @RequestMapping(value = "/project-bill")
    public String ProjectBill(Project p, Long pdId, Model model) {
        setHeader("Projects - Bill of Material", "../project/sidebar.jsp", "../project/ProjectBill.jsp", Boolean.TRUE, model);
        model.addAttribute("pd_id", pdId);
        model.addAttribute("project_reference", p.getReference());

        return "index";
    }

    @RequestMapping(value = "/project-bill/new")
    public @ResponseBody
    String newItem(Item item, Model model) {
        if (!item.getId().equals(0l)) {
            setVirtualProjectBillInfo(item.getId(), model, srvProjectManager);
        }

        return createProjectBillItems();
    }

    @RequestMapping(value = "/project-bill/new/info")
    public @ResponseBody
    String newItemInfo(ProjectBillItem item, Model model) {
        ProjectBillItem temp = getProjectBillItem(item.getId());
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

            editVirtualProjectBillInfo(temp);
        } else {
            editVirtualProjectBillInfo(item);
        }
        content.put("project_bill", createProjectBill(getAverangeDiscount()));
        content.put("project_bill_items",
                    createProjectBillItems());

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/remove/info")
    public @ResponseBody
    String removeItemInfo(ProjectBillItem item, Model model) {
        Map<String, String> content = new HashMap<>();

        removeVirtualProjectBillInfo(item.getId());
        content.put("project_bill", createProjectBill(getAverangeDiscount()));
        content.put("project_bill_items",
                    createProjectBillItems());

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/project-bill/changevalues")
    public @ResponseBody
    String changeCost(ProjectBillItem item, Long itemId, Model model) {
        Boolean findValues = Boolean.FALSE;
        ProjectBillItem temp = getProjectBillItem(item.getId());

        if (temp != null) {
            Integer available = item.getAvailable();
            BigDecimal price = item.getPrice();
            Integer quantity = item.getQuantity();
            BigDecimal cost = item.getCost();
            BigDecimal percentage = item.getPercentage();
            BigDecimal discount = item.getDiscount();
            BigDecimal percent = new BigDecimal(100);

            if (itemId != null) {
                temp.setItem(itemId);
            }
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

            editVirtualProjectBillInfo(temp);
//            srvProjectManager.getDaoItem().update(temp.getItemId());

            return createProjectBillItems();
        }

        return "";
    }

    @RequestMapping(value = "/project-bill/save")
    public @ResponseBody
    String saveItem(ProjectBill pb, Model model) {
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
        editVirtualProjectBillInfo(pb);
        srvProjectManager.getDaoProjectBillItem().add(getProjectBillItems());
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
               "<option value='-1' selected='selected'>Select</option>\n";
        String htmlSupplier = "<select id='select-item-supplier'>\n" +
               "<option value='-1' selected='selected'>Select</option>\n";

        if (stocks != null && !stocks.isEmpty()) {
            for (Stock stock : stocks) {
                htmlStock += "<option value='" + stock.getId() + "'>" + stock.getLocation() + "</option>\n";
            }
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
        List<Vessel> vessels = srvProjectManager.getDaoVessel().getAll();
        List<Company> customers = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.CUSTOMER.toString());
        List<Contact> contacts = srvProjectManager.getDaoContact().getAll();
        Map<String, String> content = new HashMap<>();
        String htmlCompany = "<select id='select-new-subproject-company'>\n" +
               "<option value='-1' selected='selected'>Select</option>\n";
        String htmlType = "<select id='select-new-subproject-type'>\n" +
               "<option value='-1' selected='selected'>Select</option>\n";
        String htmlVessel = "<select id='select-new-subproject-vessel'>\n" +
               "<option value='-1' selected='selected'>Select</option>\n";
        String htmlCustomer = "<select id='select-new-subproject-customer'>\n" +
               "<option value='-1' selected='selected'>Select</option>\n";
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
                htmlCustomer += "<option value='" + customer.getName()+ "'>" + customer.getName() + "</option>\n";
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

        return new Gson().toJson(content);
    }
    
    @RequestMapping(value = "/project-bill/insert-new-item")
    public @ResponseBody
    String insertNewItem(Item item, Model model) {
        item = srvProjectManager.getDaoItem().add(item);
        if (!item.getId().equals(0l)) {
            setVirtualProjectBillInfo(item.getId(), model, srvProjectManager);
        }

        return createProjectBillItems();
    }
}
