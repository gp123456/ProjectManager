/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.controller.common.QuotationCommon;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.BillMaterialServiceItem;
import com.allone.projectmanager.entities.Item;
import com.allone.projectmanager.entities.Project;
import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.RequestQuotationItem;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project")
public class QuotationController extends QuotationCommon {

    private static final Logger logger = Logger.getLogger(RequestQuotationController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    private String getRequestQuotation(RequestQuotation item) {
        return "<tr>"
                + "<td>" + item.getDeliveryCost() + "</td>"
                + "<td>" + item.getOtherExpenses() + "</td>"
                + "<td>" + item.getMaterialCost() + "</td>"
                + "<td>" + item.getGrandTotal() + "</td>"
                + "</tr>";
    }

    private String getRequestQuotationItems(RequestQuotation rq) {
        List<RequestQuotationItem> items = srvProjectManager.getDaoRequestQuotationItem().getByRequestQuotation(rq.getId());
        String html = "";

        if (items != null && !items.isEmpty()) {
            Integer index = 1;

            for (RequestQuotationItem item : items) {
                BillMaterialServiceItem bmsi = srvProjectManager.getDaoBillMaterialServiceItem().getById(item.getBillMaterialServiceItem());

                if (bmsi != null) {
                    Item i = srvProjectManager.getDaoItem().getById(bmsi.getItem());

                    if (i != null) {
                        html += "<tr>"
                                + "<td>" + index++ + "</td>"
                                + "<td>" + i.getImno() + "</td>"
                                + "<td>" + i.getDescription() + "</td>"
                                + "<td>" + bmsi.getQuantity() + "</td>"
                                + "<td>" + item.getUnitPrice() + "</td>"
                                + "<td>" + item.getDiscount() + "</td>"
                                + "<td>" + item.getAvailability() + "</td>"
                                + "<td>" + item.getTotal() + "</td>"
                                + "</tr>";
                    }
                }
            }
        }

        return html;
    }

    private String[] selectInfo(Long pId) {
        List<ProjectDetail> pds = srvProjectManager.getDaoProjectDetail().getByProjectId(pId);
        String[] html = new String[]{"", "", "", "", "", "", "", ""};

        if (pds != null && !pds.isEmpty()) {
            html[0] = pds.stream().map((pd) -> "<option value='" + pd.getId() + "'>" + pd.getReference() + "</option>").reduce(html[0], String::concat);

            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getByProject(pds.get(0).getId());

            if (bms != null) {
                List<RequestQuotation> rqs = srvProjectManager.getDaoRequestQuotation().getByBillMaterialService(bms.getId());

                if (rqs != null && !rqs.isEmpty()) {
                    html[1] = rqs.stream().map((rq) -> "<option value='" + rq.getId() + "'>" + rq.getName() + "</option>").reduce(html[1], String::concat);
                    html[2] = rqs.get(0).getSupplier();
                    html[3] = getCurrencyById(rqs.get(0).getCurrency());
                    html[4] = getRequestQuotation(rqs.get(0));
                    html[5] = getRequestQuotationItems(rqs.get(0));
                    html[6] = rqs.get(0).getNote();
                    html[7] = rqs.get(0).getSupplierNote();
                }
            }
        }

        return html;
    }

    @RequestMapping(value = "/quotation")
    public String Quotation(HttpServletRequest request, Project p, String mode, Model model) {
        if (request != null) {
//            String queryString = request.getQueryString();

//            if (!Strings.isNullOrEmpty(queryString)) {
            HttpSession session = request.getSession();
            String[] modeInfo = mode.split("-");
            String _mode = (modeInfo != null) ? modeInfo[0] : mode;

            this.setTitle("Quotation");
            this.setHeader("header.jsp");
            this.setSide_bar("../project/sidebar.jsp");
            this.setContent("../project/QuotationSelectRFQ.jsp");
            this.setClassContent("content");
            p = srvProjectManager.getDaoProject().getById(p.getId());
            if (p != null) {
                String[] info = selectInfo(p.getId());

                setHeaderInfo(session, model);
                model.addAttribute("projectId", p.getId());
                model.addAttribute("projectReference", p.getReference());
                model.addAttribute("is_quotation", 1);
                if (info != null && info.length == 6) {
                    model.addAttribute("subprojects", info[0]);
                    model.addAttribute("requestQuotations", info[1]);
                    model.addAttribute("supplier", info[2]);
                    model.addAttribute("currency", info[3]);
                    model.addAttribute("requestQuotation", info[4]);
                    model.addAttribute("itemRequestQuotation", info[5]);
                    model.addAttribute("noteRequestQuotation", info[6]);
                    model.addAttribute("noteSupplierRequestQuotation", info[7]);
                }
            }

            return "index";
//            } else {
//                return "login";
//            }
        }

        return "";
    }

//    @RequestMapping(value = "/quotation/replace")
//    public @ResponseBody
//    String replaceProjectBillItems(Long pdid, Integer location) {
//        String response = "<input type='text' value='" + getLocationNameById(location) + "' readonly>\n";
//        ProjectModel pbm = getFirstLocation(pdid);
//        Collection<BillMaterialServiceItem> pbis = getProjectBillItems(pbm);
//        Map<String, String> content = new HashMap<>();
//
//        if (pbis != null && !pbis.isEmpty()) {
//            response = pbis.stream()
//            .map((pbi) -> {
//                Item item = srvProjectManager.getDaoItem().getById(pbi.getItem());
//                if (item == null) {
//                    item = new Item.Builder().setId(pbi.getItem()).setImno(pbi.getItemImno()).build();
//                }
//                return item;
//            })
//            .map((item) ->
//                    "<div class='slideThree'>\n" +
//                    "<input type='checkbox' id='" + item.getId() + "' name='checkbox-project' value='" + item.getId() + "'>\n" +
//                    "<label for='" + item.getId() + "'>" + item.getImno() + "</label>\n" +
//                    "</div>").reduce(response, String::concat);
//            content.put("items", response);
//            content.put("location", pbm.getLocation().toString());
//        }
//
//        return new Gson().toJson(content);
//        return "";
//    }
//    @RequestMapping(value = "/quotation/replace/add")
//    public @ResponseBody
//    String addBillMaterialService(Long id, Integer srcLocation, Integer newLocation, Long[] itemIds) {
//        if (id != null && srcLocation != null && newLocation != null && itemIds != null && itemIds.length > 0) {
//            ProjectModel pbm = new ProjectModel(id, srcLocation);
//
//            for (Long itemId : itemIds) {
//                BillMaterialServiceItem item = new BillMaterialServiceItem.Builder().build(getProjectBillItem(pbm, itemId));
//
//                if (item != null) {
//                    item.setClassRefresh("button");
//                    item.setClassSave("button alarm");
//                    setVirtualProjectBillItem(new ProjectModel(id, newLocation), item);
//                }
//            }
//
//            return createProjectBillItems(new ProjectModel(id, newLocation));
//        }
//        return "";
//    }
}
