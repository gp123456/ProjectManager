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
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.ServiceCollab;
import com.allone.projectmanager.entities.Stock;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.enums.CollabRoleEnum;
import com.allone.projectmanager.enums.LocationEnum;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.ProjectModel;
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

        content.put("locations", createLocations());

        content.put("currencies", createCurrency(CurrencyEnum.NONE));

        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(p.getId());
        Long pdId = null;

        response = "";
        if (pds != null && !pds.isEmpty()) {
            pdId = pds.get(0).getId();
            Vessel vessel = srvProjectManager.getDaoVessel().getById(pds.get(0).getVessel());
            Set<ProjectModel> pbKeys = getProjectBillIds();

            for (ProjectDetail pd : pds) {
                response += "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>";
                if (pbKeys == null) {
                    pushProjectBillMaterial(pd.getId());
                }
            }

            BillMaterialService bms = getProjectBill(new ProjectModel(pdId, 1));
            content.put("subprojects", response);
            content.put("billMaterialService", createProjectBill(new ProjectModel(pdId, 1)));
            content.put("noteBillMaterialService", (bms != null) ? bms.getNote() : "");
            content.put("billMaterialServiceItems", createProjectBillItems(new ProjectModel(pdId, 1)));
            content.put("type", pds.get(0).getType());
            content.put("company", pds.get(0).getCompany());
            content.put("customer", pds.get(0).getCustomer());
            if (vessel != null) {
                content.put("vessel", vessel.getName());
            }
        } else {
            content.put("subprojects", response);
            content.put("billMaterialService", response);
            content.put("billMaterialServiceItems", response);
        }

        return new Gson().toJson(content);
    }

    private void pushProjectBillMaterial(Long projectDetailId) {
        List<BillMaterialService> pbs = srvProjectManager.getDaoProjectBill().getByProject(projectDetailId);

        if (pbs != null && !pbs.isEmpty()) {
            pbs.stream().forEach((pb) -> {
                Integer location = getLocationIdByName(pb.getLocation());
                pb.setClassSave("button alarm");
                setVirtualProjectBill(pb, location);
                List<BillMaterialServiceItem> pbis = srvProjectManager.getDaoProjectBillItem().getByProjectBill(pb.getId());
                if (pbis != null && !pbis.isEmpty()) {
                    pbis.stream().map((pbi) -> {
                        pbi.setClassRefresh("button alarm");
                        return pbi;
                    }).map((pbi) -> {
                        pbi.setClassSave("button alarm");
                        return pbi;
                    }).forEach((pbi) -> {
                        setVirtualProjectBillItem(new ProjectModel(pb.getProject(), location), pbi);
                    });
                }
            });
        }
    }

    private String createProjectBill(ProjectModel _pbm) {
        String response = "";
        Set<ProjectModel> pbIds = (_pbm != null) ? getPBMKeysByPDId(_pbm) : getProjectBillIds();

        if (pbIds != null && !pbIds.isEmpty()) {
            for (ProjectModel pbm : pbIds) {
                BillMaterialService pb = getProjectBill(pbm);
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pb.getProject());
                String subproject = (pd != null) ? pd.getReference() : "";
                Long pdid = (pd != null) ? pd.getId() : 0l;

                response += "<tr>\n"
                        + "<td id='m_total_cost'>" + pb.getTotalCost() + "</td>\n"
                        + "<td id='m_average_discount'>" + pb.getAverangeDiscount() + "</td>\n"
                        + "<td id='m_sale_price'>" + pb.getTotalSalePrice() + "</td>\n"
                        + "<td id='m_net_sale_price'>" + pb.getTotalNetPrice() + "</td>\n"
                        + "<td id='currency" + pdid + "'>" + getCurrencyById(pb.getCurrency()) + "</td>\n"
                        + "<td id='m_subproject'>" + subproject + "</td>\n"
                        + "<td><input type='button' value='Delete' class='button' id='delete' onclick='delete(" + pdid + "," + pbm.getLocation() + ")'></td>\n"
                        + "</tr>\n";
            }
        }

        return response;
    }

    private String createProjectBillItems(ProjectModel _pbm) {
        String response = "";
        Set<ProjectModel> pbms = (_pbm != null) ? getPBMIKeysByPDId(_pbm) : getProjectBillDetailIds();

        if (pbms != null && !pbms.isEmpty()) {
            for (ProjectModel pbm : pbms) {
                Collection<BillMaterialServiceItem> pbItems = getProjectBillItems(pbm);
                ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(pbm.getId());

                if (pbItems != null && !pbItems.isEmpty()) {
                    for (BillMaterialServiceItem pbItem : pbItems) {
                        Item item = srvProjectManager.getDaoItem().getById(pbItem.getItem());
                        Stock stock = (item != null) ? srvProjectManager.getDaoStock().getById(item.getLocation()) : null;
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
                        String totalSalePrice = (pbItem.getTotalSalePrice() != null) ? pbItem.getTotalSalePrice().toString() : "";
                        String totalNetPrice = (pbItem.getTotalNetPrice() != null) ? pbItem.getTotalNetPrice().toString() : "";

                        response
                                += "<tr>\n"
                                + "<td>" + imno + "</td>\n"
                                + "<td>" + stockName + "</td>\n"
                                + "<td id='quantity" + pbm.getId() + itemId + "' style='background: #333;color:#E7E5DC'>" + "<div contenteditable></div>" + quantity + "</td>\n"
                                + "<td id='cost" + pbm.getId() + itemId + "' style='background: #333;color:#E7E5DC'>" + "<div contenteditable></div>" + cost + "</td>\n"
                                + "<td id='total_cost" + pbm.getId() + itemId + "'>" + totalCost + "</td>\n"
                                + "<td id='percentage" + pbm.getId() + itemId + "' style='background: #333;color:#E7E5DC'>" + "<div contenteditable></div>" + percentage + "</td>\n"
                                + "<td id='discount" + pbm.getId() + itemId + "' style='background: #333;color:#E7E5DC'>" + "<div contenteditable></div>" + discount + "</td>\n"
                                + "<td id='sale_price" + pbm.getId() + itemId + "'>" + salePrice + "</td>\n"
                                + "<td id='total_sale_price" + pbm.getId() + itemId + "'>" + totalSalePrice + "</td>\n"
                                + "<td id='total_net_price" + pbm.getId() + itemId + "'>" + totalNetPrice + "</td>\n"
                                + "<td>" + pd.getReference() + "</td>\n"
                                + "<td><input type='button' value='Edit' class='button' onclick='editItem(" + pbm.getId() + "," + itemId + ")'></td>\n"
                                + "<td><input type='button' value='Refresh' class='" + pbItem.getClassRefresh() + "' onclick='refreshItem(" + pbm.getId() + "," + itemId + "," + itemQuantity + ")'></td>\n"
                                + "<td><input type='button' value='Save' class='" + pbItem.getClassSave() + "' onclick='saveItem(" + pbm.getId() + "," + itemId + ")'></td>\n"
                                + "<td><input type='button' value='Remove' class='button' onclick='removeItem(" + pbm.getId() + "," + itemId + ")'></td>\n"
                                + "</tr>\n";
                    }
                }
            }
        }

        return response;
    }

    private BillMaterialService getAverangeDiscount(Long pdId, Integer location, Integer currency) {
        Collection<BillMaterialServiceItem> items = getProjectBillItems(new ProjectModel(pdId, location));

        if (items != null && !items.isEmpty()) {
            BigDecimal totalCost = BigDecimal.ZERO;
            BigDecimal averangeDiscount = BigDecimal.ZERO;
            BigDecimal totalSalePrice = BigDecimal.ZERO;
            BigDecimal totalNetPrice = BigDecimal.ZERO;

            for (BillMaterialServiceItem item : items) {
                totalCost = (item.getTotalCost() != null) ? totalCost.add(item.getTotalCost()) : BigDecimal.ZERO;
                averangeDiscount = (item.getDiscount() != null) ? averangeDiscount.add(item.getDiscount()) : BigDecimal.ZERO;
                totalSalePrice = (item.getTotalSalePrice() != null) ? totalSalePrice.add(item.getTotalSalePrice()) : BigDecimal.ZERO;
                totalNetPrice = (item.getTotalNetPrice() != null) ? totalNetPrice.add(item.getTotalNetPrice()) : BigDecimal.ZERO;
                item.setClassSave("button");
            }

            averangeDiscount = averangeDiscount.divide(new BigDecimal(items.size()), 2);

            return (currency != null)
                    ? new BillMaterialService.Builder()
                    .setProject(pdId)
                    .setLocation(getLocationNameById(location))
                    .setTotalCost(totalCost)
                    .setAverangeDiscount(averangeDiscount)
                    .setTotalSalePrice(totalSalePrice)
                    .setTotalNetPrice(totalNetPrice)
                    .setCurrency(currency)
                    .setClassSave("button alarm")
                    .build()
                    : new BillMaterialService.Builder()
                    .setProject(pdId)
                    .setLocation(getLocationNameById(location))
                    .setTotalCost(totalCost)
                    .setAverangeDiscount(averangeDiscount)
                    .setTotalSalePrice(totalSalePrice)
                    .setTotalNetPrice(totalNetPrice)
                    .setClassSave("button alarm")
                    .build();
        } else {
            removeVirtualProjectBill(new ProjectModel(pdId, location));

            return null;
        }
    }

    @RequestMapping(value = "/bill-material-service")
    public String ProjectBill(Project p, String mode, Model model) {
        this.setTitle("Projects - Bill of materials or services");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/BillMaterialService.jsp");
        setHeaderInfo(model);
        p = srvProjectManager.getDaoProject().getById(p.getId());

        if (p != null) {
            model.addAttribute("p_id", p.getId());
            model.addAttribute("project_reference", p.getReference());
            model.addAttribute("button_save", "<input type='button' value='Save' class='button alarm' onclick='saveBillMaterialService(\"" + p.getId() + "\")'>\n");
            model.addAttribute("button_save_pdf", "<input type='button' value='Save PDF' class='button' onclick='savePDF(\"" + p.getReference() + "\")'>\n");
            model.addAttribute("button_save_excel", "<input type='button' value='Save Excel' class='button' onclick='saveXLS(\"" + p.getReference() + "\")'>\n");
            model.addAttribute("button_send_email", "<input type='button' value='Send eMail' class='button' onclick='sendEmail(\"" + p.getId() + "\")'>\n");
        }

        return "index";
    }

    @RequestMapping(value = "/bill-material-service/item/insert")
    public @ResponseBody
    String itemInsert(Long pdId, Integer location, BillMaterialServiceItem pbi) {
        Map<String, String> content = new HashMap<>();
        String availability = "";
        String price = "";

        if (!pbi.getItem().equals(0l) && getProjectBillItem(new ProjectModel(pdId, location), pbi.getItem()) == null) {
            Item item = srvProjectManager.getDaoItem().getById(pbi.getItem());

            if (item != null) {
                pbi.setAvailable(item.getQuantity());
                pbi.setPrice(item.getPrice());
                pbi.setItemImno(item.getImno());
                pbi.setItemDescription(item.getDescription());
                setVirtualProjectBillItem(new ProjectModel(pdId, location), pbi);
                availability = item.getQuantity().toString();
                price = item.getPrice().toString();
            }
        }

        content.put("availability", availability);
        content.put("price", price);
        content.put("item", createProjectBillItems(new ProjectModel(pdId, location)));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/item/save")
    public @ResponseBody
    String itemSave(Long pdId, Integer location, Integer currency, BillMaterialServiceItem pbi) {
        BillMaterialServiceItem temp = getProjectBillItem(new ProjectModel(pdId, location), pbi.getItem());
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
            editVirtualProjectBillItem(new ProjectModel(pdId, location), temp);
        } else {
            editVirtualProjectBillItem(new ProjectModel(pdId, location), pbi);
        }
        BillMaterialService pb = getAverangeDiscount(pdId, location, currency);

        if (pb != null) {
            logger.log(Level.INFO, "save item {0}, {1}", new Object[]{pb.getProject(), location});
            
            setVirtualProjectBill(pb, location);
        }
        saveVirtualProjectBillItem(new ProjectModel(pdId, location));
        content.put("projectBill", createProjectBill(new ProjectModel(pdId, location)));
        content.put("projectBillItems", createProjectBillItems(new ProjectModel(pdId, location)));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/item/remove")
    public @ResponseBody
    String itemRemove(Long pdId, Integer location, BillMaterialServiceItem pbi) {
        Map<String, String> content = new HashMap<>();

        removeVirtualProjectBillItem(srvProjectManager, pdId, location, pbi.getItem());
        setVirtualProjectBill(getAverangeDiscount(pdId, location, null), location);
        content.put("projectBill", createProjectBill(new ProjectModel(pdId, location)));
        content.put("projectBillItems", createProjectBillItems(new ProjectModel(pdId, location)));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/item/refresh")
    public @ResponseBody
    String itemRefresh(Long pdId, Integer location, BillMaterialServiceItem pbi) {
        Boolean findValues = Boolean.FALSE;
        BillMaterialServiceItem temp = getProjectBillItem(new ProjectModel(pdId, location), pbi.getItem());

        if (temp != null) {
            Integer quantity = pbi.getQuantity();
            BigDecimal cost = pbi.getCost();
            BigDecimal percentage = pbi.getPercentage();
            BigDecimal discount = pbi.getDiscount();
            BigDecimal percent = new BigDecimal(100);

            if (quantity != null) {
                temp.setQuantity(quantity);
                findValues = Boolean.TRUE;
            } else {
                findValues = Boolean.FALSE;
            }
            if (cost != null) {
                temp.setCost(cost);
                findValues = Boolean.TRUE;
            } else {
                findValues = Boolean.FALSE;
            }
            if (percentage != null) {
                temp.setPercentage(percentage);
            }
            if (discount != null) {
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
                        temp.setTotalNetPrice(temp.getTotalSalePrice().subtract(temp.getTotalSalePrice().multiply(temp.getDiscount().divide(percent))));
                    } else {
                        temp.setTotalNetPrice(temp.getTotalSalePrice());
                    }
                }
            }

            temp.setClassRefresh(pbi.getClassRefresh());
            temp.setClassSave(pbi.getClassSave());
            editVirtualProjectBillItem(new ProjectModel(pdId, location), temp);

            return createProjectBillItems(new ProjectModel(pdId, location));
        }

        return "";
    }

    @RequestMapping(value = "/bill-material-service/item/edit")
    public @ResponseBody
    String Save(Long pdId, Integer location, BillMaterialServiceItem pbi) {
        BillMaterialServiceItem temp = getProjectBillItem(new ProjectModel(pdId, location), pbi.getItem());

        if (temp != null) {
            temp.setClassRefresh(pbi.getClassRefresh());
            temp.setClassSave(pbi.getClassSave());
            editVirtualProjectBillItem(new ProjectModel(pdId, location), temp);

            return createProjectBillItems(new ProjectModel(pdId, location));
        }

        return "";
    }

    @RequestMapping(value = "/bill-material-service/location")
    public @ResponseBody
    String getBilMaterialService(Long pdId, Integer location) {
        Map<String, String> content = new HashMap<>();

        if (pdId != null && location != null) {
            content.put("billMaterialService", createProjectBill(new ProjectModel(pdId, location)));
            content.put("billMaterialServiceItems", createProjectBillItems(new ProjectModel(pdId, location)));
        } else {
            content.put("billMaterialService", "");
            content.put("billMaterialServiceItems", "");
        }

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/save")
    public @ResponseBody
    String saveBillMaterialService(BillMaterialService pb) {
        String response = "";

        if (pb != null) {
            List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pb.getProject());

            if (pds != null && !pds.isEmpty()) {
                Project p = srvProjectManager.getDaoProject().getById(pb.getProject());
                if (p != null) {
                    p.setStatus(ProjectStatusEnum.PROJECT_BILL.toString());
                    srvProjectManager.getDaoProject().edit(p);
                }
                for (ProjectDetail pd : pds) {
                    pd.setStatus(ProjectStatusEnum.PROJECT_BILL.toString());
                    srvProjectManager.getDaoProjectDetail().edit(pd);
                    ProjectModel pdm = new ProjectModel(pd.getId(), LocationEnum.GREECE.getId());
                    BillMaterialService bms = getBillMaterialServices(pdm);

                    if (bms != null) {
                        bms.setNote(pb.getNote());
                        bms.setComplete(Boolean.FALSE);
                        pdm.setLocation(getLocationIdByName(bms.getLocation()));
                        if (bms.getId() == null) {
                            logger.log(Level.INFO, "complete save {0}", bms.getId());

                            bms = srvProjectManager.getDaoProjectBill().add(bms);
                            setVirtualProjectBillItemBillId(pdm, bms.getId());
                            srvProjectManager.getDaoProjectBillItem().add(getProjectBillItems(pdm));
                        } else {
                            srvProjectManager.getDaoProjectBill().edit(bms);
                            setVirtualProjectBillItemBillId(pdm, bms.getId());
                            Collection<BillMaterialServiceItem> pbis = getProjectBillItems(pdm);

                            for (BillMaterialServiceItem pbi : pbis) {
                                if (pbi.getId() == null) {
                                    srvProjectManager.getDaoProjectBillItem().add(pbi);
                                } else {
                                    srvProjectManager.getDaoProjectBillItem().edit(pbi);
                                }
                            }
                        }
                        clearVirtualProjectBill(pdm);
                        response = "<h1>Bill of Material - REF:" + p.getReference() + " - Complete</h1>\n";
                    }
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
        String htmlStock = "<select id='select-item-location'>\n"
                + "<option value='-1' selected='selected'>Select Location</option>\n";
        String htmlSupplier = "<select id='select-item-supplier'>\n"
                + "<option value='-1' selected='selected'>Select Supplier</option>\n";

        content.put("currency", createCurrency(CurrencyEnum.EUR));
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

    @RequestMapping(value = "/bill-material-service/new-subproject")
    public @ResponseBody
    String newSubProject() {
        Date expired = new Date(new Date().getTime() + getUser().getProject_expired() * 86400000l);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        Map<String, String> content = new HashMap<>();
        String htmlCompany = "<select id='subproject-company'>\n"
                + "<option value='none' selected='selected'>Select</option>\n";
        String htmlType = "<select id='type'>\n"
                + "<option value='none' selected='selected'>Select</option>\n";
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
            
            for (Collabs collab : collabs) {
                htmlTechical += "<option value='" + collab.getId() + "'>" + collab.getSurname() + " " + collab.getName() + "</option>\n";
            }
            content.put("technical", htmlTechical);
        }

        String result = new Gson().toJson(content);

        return result;
    }

    @RequestMapping(value = "/bill-material-service/item/stock")
    public @ResponseBody
    String itemStockInsert(Long pdId, Integer location, Item item) {
        BillMaterialServiceItem temp = getFirstProjectBillItem(new ProjectModel(pdId, location));
        item.setStartQuantity(item.getQuantity());
        item.setOfferQuantity(item.getQuantity());
        item.setInventoryQuantity(item.getQuantity());
        item.setStartPrice(item.getPrice());
        item.setInventoryPrice(item.getPrice());
        item.setInventoryEdit(Boolean.FALSE);
        item = srvProjectManager.getDaoItem().add(item);
        if (item.getId() != null) {
            setVirtualProjectBillItem(new ProjectModel(pdId, LocationEnum.GREECE.getId()),
                    new BillMaterialServiceItem.Builder()
                    .setAvailable(item.getQuantity())
                    .setQuantity(0)
                    .setPrice(item.getPrice())
                    .setItem(item.getId())
                    .setItemImno(item.getImno())
                    .setItemDescription(item.getDescription())
                    .setClassRefresh("button alarm")
                    .setClassSave("button alarm")
                    .build());
        }

        return createProjectBillItems(new ProjectModel(pdId, location));
    }

    @RequestMapping(value = "/bill-material-service/content")
    public @ResponseBody
    String getContent(Project p) {
        return createContent(p);
    }

    @RequestMapping(value = "/bill-material-service/save-subproject")
    public @ResponseBody
    String saveSubproject(ProjectDetail pd, ServiceCollab sc) {
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
                srvProjectManager.getDaoServiceCollab().add(sc);
            }
            
            return "index";
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/bill-material-service/get-bill-material-service-item")
    public @ResponseBody
    String changeProjectBillItems(Long id, Integer location) {
        Map<String, String> content = new HashMap<>();
        ProjectModel pbm = new ProjectModel(id, location);
        ProjectDetail pd = srvProjectManager.getDaoProjectDetail().getById(id);

        if (pd != null) {
            Vessel vessel = srvProjectManager.getDaoVessel().getById(pd.getVessel());
            content.put("type", pd.getType());
            content.put("company", pd.getCompany());
            content.put("customer", pd.getCustomer());
            if (vessel != null) {
                content.put("vessel", vessel.getName());
            }
        }

        content.put("billMaterialService", createProjectBill(pbm));
        content.put("billMaterialServiceItems", createProjectBillItems(pbm));

        return new Gson().toJson(content);
    }

    @RequestMapping(value = "/bill-material-service/view-location")
    public @ResponseBody
    String viewLocation(Integer location) {
        if (location != null) {
            return getLocationNameById(location);
        }

        return "";
    }

    @RequestMapping(value = "/bill-material-service/currency")
    public @ResponseBody
    String viewLocation(Long pdId, Integer location, Integer currency) {
        String response = "";

        if (pdId != null && location != null && currency != null) {
            ProjectModel pbm = new ProjectModel(pdId, location);
            BillMaterialService vpb = getProjectBill(pbm);

            if (vpb != null) {
                vpb.setCurrency(currency);
            }

            response = createProjectBill(pbm);
        }

        return response;
    }
}
