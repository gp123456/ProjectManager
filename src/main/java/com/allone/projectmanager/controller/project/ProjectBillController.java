/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.Root;
import com.allone.projectmanager.controller.common.ProjectCommon;
import com.allone.projectmanager.entities.Collabs;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.ProjectBill;
import com.allone.projectmanager.entities.ProjectBillItem;
import com.allone.projectmanager.tools.JasperReport;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    private static final Logger LOG = Logger.getLogger(Root.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    public ProjectManagerService getSrvProjectManager() {
        return srvProjectManager;
    }

    public void setSrvProjectManager(ProjectManagerService srvProjectManager) {
        this.srvProjectManager = srvProjectManager;
        this.srvProjectManager.loadPropertyValues();
    }

    private void setHeader(String title, String sideBar, String content,
            Boolean itemInfo, Model model) {
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

        response
                += "<tr>"
                + "<td id=\"m_total_cost\">" + pb.getTotalCost() + "</td>"
                + "<td id=\"m_average_discount\">" + pb.getAverangeDiscount() + "</td>"
                + "<td id=\"m_sale_price\">" + pb.getTotalSalePrice() + "</td>"
                + "<td id=\"m_net_sale_price\">" + pb.getTotalNetPrice() + "</td>"
                + "<td><input type=\"button\" value=\"Edit\" style=\"text-align:center; "
                + "vertical-align: middle;\" id=\"edit\" onclick=\"edit()\"></td>\n"
                + "<td><input type=\"button\" value=\"Delete\" style=\"text-align:center; "
                + "vertical-align: middle;\" id=\"delete\" onclick=\"delete()\"></td>\n"
                + "<td><input type=\"button\" value=\"Save PDF\" style=\"text-align:center; "
                + "vertical-align: middle;\" id=\"save-pdf\" onclick=\"savePDF()\"></td>\n"
                + "<td><input type=\"button\" value=\"Print PDF\" style=\"text-align:center; "
                + "vertical-align: middle;\" id=\"print-pdf\" onclick=\"printPDF()\"></td>\n"
                + "<td><input type=\"button\" value=\"Save Excel\" style=\"text-align:center; "
                + "vertical-align: middle;\" id=\"save-xls\" onclick=\"saveXLS()\"></td>\n"
                + "<td><input type=\"button\" value=\"Send eMail\" style=\"text-align:center; "
                + "vertical-align: middle;\" id=\"send-email\" onclick=\"sendEmail()\"></td>\n"
                + "</tr>";

        return response;
    }

    private String createProjectBillItems(Long lastKey, Boolean custom, Boolean disableRefresh, Boolean disableSave) {
        String response = "";
        Set<Long> projectBillKeys = getProjectBillKeys();

        if (projectBillKeys != null && projectBillKeys.isEmpty() == false) {
            for (Long key : projectBillKeys) {
                ProjectBillItem pbi = getProjectBillItem(key);
                Item item = pbi.getItemId();
                Integer quantity = pbi.getQuantity();
                BigDecimal cost = pbi.getCost();
                BigDecimal totalCost = pbi.getTotalCost();
                BigDecimal percentage = pbi.getPercentage();
                BigDecimal discount = pbi.getDiscount();
                BigDecimal salePrice = pbi.getSalePrice();
                BigDecimal totalSalePrice = pbi.getTotalSalePrice();
                BigDecimal totalNetPrice = pbi.getTotalNetPrice();
                String disabledRefresh = (!key.equals(lastKey)) ? "disabled" : (disableRefresh) ? "disabled" : "";
                String disabledSave = (!key.equals(lastKey)) ? "disabled" : (disableSave) ? "disabled" : "";

                if (!custom || !key.equals(lastKey)) {
                    response
                            += "<tr>"
                            + "<td>" + item.getImno() + "</td>"
                            + "<td>" + item.getDescription() + "</td>"
                            + "<td id=\"available" + item.getId() + "\">" + item.getQuantity() + "</td>"
                            + "<td>" + item.getPrice() + "</td>"
                            + "<td id=\"quantity" + item.getId() + "\">" + "<div contenteditable></div>"
                            + ((!quantity.equals(0)) ? quantity : "") + "</td>"
                            + "<td id=\"cost" + item.getId() + "\"><div contenteditable></div>" + ((cost != null) ? cost : "")
                            + "</td>"
                            + "<td id=\"total_cost" + item.getId() + "\">" + ((totalCost != null) ? totalCost : "") + "</td>"
                            + "<td id=\"percentage" + item.getId() + "\"><div contenteditable></div>" + ((percentage != null)
                                    ? percentage : "")
                            + "</td>"
                            + "<td id=\"discount" + item.getId() + "\"><div contenteditable></div>" + ((discount != null)
                                    ? discount : "") + "</td>"
                            + "<td id=\"sale_price" + item.getId() + "\">" + ((salePrice != null) ? salePrice : "") + "</td>"
                            + "<td id=\"total_sale_price" + item.getId() + "\">" + ((totalSalePrice != null) ? totalSalePrice
                                    : "") + "</td>"
                            + "<td id=\"total_net_price" + item.getId() + "\">" + ((totalNetPrice != null) ? totalNetPrice
                                    : "") + "</td>"
                            + "<td><input type=\"button\" value=\"Refresh\" style=\"text-align:center; vertical-align: middle;\" "
                            + "onclick=\"changeValues(" + item.getId() + ")\"" + disabledRefresh + "></td>"
                            + "<td><input type=\"button\" value=\"Save\" style=\"text-align:center;vertical-align:middle;\" "
                            + "onclick=\"newProjectBillInfo(" + item.getId() + ")\" " + disabledSave + "></td>"
                            + "<td><input type=\"checkbox\" value=\"checked\" style=\"text-align:center; vertical-align: middle;\" "
                            + "onclick=\"removeProjectBillInfo(" + item.getId() + ")\"></td>"
                            + "</tr>";
                } else {
                    response
                            += "<tr>"
                            + "<td id=\"code" + key + "\"><div contenteditable></div></td>"
                            + "<td id=\"description" + key + "\"><div contenteditable></div></td>"
                            + "<td id=\"available" + key + "\"><div contenteditable></div></td>"
                            + "<td id=\"price" + key + "\"><div contenteditable></div></td>"
                            + "<td id=\"quantity" + key + "\"><div contenteditable></div></td>"
                            + "<td id=\"cost" + key + "\"><div contenteditable></div>" + ((cost != null) ? cost : "") + "</td>"
                            + "<td id=\"cost_total" + key + "\">" + ((totalCost != null) ? totalCost : "") + "</td>"
                            + "<td id=\"percentage" + key + "\">" + "<div contenteditable></div>" + ((percentage != null)
                                    ? percentage : "") + "</td>"
                            + "<td id=\"discount" + key + "\"><div contenteditable></div>" + ((discount != null) ? discount : "")
                            + "</td>"
                            + "<td id=\"sale_price" + key + "\">" + ((salePrice != null) ? salePrice : "") + "</td>"
                            + "<td id=\"total_sale_price" + key + "\">" + ((totalSalePrice != null) ? totalSalePrice : "")
                            + "</td>"
                            + "<td id=\"total_net_price" + key + "\">" + ((totalNetPrice != null) ? totalNetPrice : "") + "</td>"
                            + "<td><input type=\"button\" value=\"Refresh\" style=\"text-align:center; vertical-align: middle;\" "
                            + "onclick=\"changeValues(" + key + ")\" " + disabledRefresh + "></td>"
                            + "<td><input type=\"button\" value=\"Save\" style=\"text-align:center; vertical-align: middle;\" "
                            + "onclick=\"newProjectBillInfo(" + key + ")\" " + disabledSave + "></td>"
                            + "<td><input type=\"checkbox\" value=\"checked\" style=\"text-align:center; vertical-align: middle;\" "
                            + "onclick=\"removeProjectBillInfo(" + key + ")\"></td>"
                            + "</tr>";
                }
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

        return new ProjectBill(totalCost, averangeDiscount, totalSalePrice, totalNetPrice);
    }

    @RequestMapping(value = "project-bill")
    public String ProjectBill(Model model) {
        setHeader("Projects-Bill", "../project/sidebar.jsp", "../project/ProjectBill.jsp", Boolean.TRUE, model);

        return "index";
    }

    @RequestMapping(value = "new")
    public @ResponseBody
    String newItem(Item item,
            Model model) {
        Long itemId = 0l;
        Boolean custom = Boolean.FALSE;

        if (!item.getId().equals(0l)) {
            setVirtualProjectBillInfo(item.getId(), model, srvProjectManager);
            itemId = item.getId();
        } else {
            itemId = srvProjectManager.getDaoItem().getLastId() + 1l;
            setVirtualProjectBillInfo(itemId, model, srvProjectManager);
            custom = Boolean.TRUE;
        }

        return createProjectBillItems(itemId, custom, Boolean.FALSE, Boolean.FALSE);
    }

    @RequestMapping(value = "new/info")
    public @ResponseBody
    String newItemInfo(ProjectBillItem item,
            Model model) {
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
        content.put("project_bill_items", createProjectBillItems(item.getId(), Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "remove/info")
    public @ResponseBody
    String removeItemInfo(ProjectBillItem item, Model model) {
        Map<String, String> content = new HashMap<>();

        removeVirtualProjectBillInfo(item.getId());
        content.put("project_bill", createProjectBill(getAverangeDiscount()));
        content.put("project_bill_items", createProjectBillItems(item.getId(), Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "changevalues")
    public @ResponseBody
    String changeCost(ProjectBillItem item, String description, String code, Model model) {
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

            if (!Strings.isNullOrEmpty(code)) {
                temp.getItemId().setImno(code);
            }
            if (!Strings.isNullOrEmpty(description)) {
                temp.getItemId().setDescription(description);
            }
            if (available != null && !available.equals(0)) {
                temp.getItemId().setQuantity(available);
            }
            if (price != null && !price.equals(0)) {
                temp.getItemId().setPrice(price);
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

            return createProjectBillItems(item.getId(), Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
        }

        return "";
    }

    @RequestMapping(value = "save")
    public @ResponseBody
    String saveItem(ProjectBill pb, Model model) {
        Collabs user = srvProjectManager.getDaoCollab().getById(getUser().getId());

        if (user != null) {
            pb = srvProjectManager.getDaoProjectBill().add(pb);
            editVirtualProjectBillInfo(pb);
            srvProjectManager.getDaoProjectBillItem().add(getProjectBillItems());
        }
        clearVirtualProjectBill();

        return "index";
    }

    @RequestMapping(value = "createpdf")
    public String createPDF(ProjectBillController pb, Model model) throws JRException {
        JasperReport.createProjectBillReport(getSrvProjectManager().getPathProjectBill(), getUser().
                getProject_reference().replace("/", "_") + ".pdf");

        return "";
    }
}
