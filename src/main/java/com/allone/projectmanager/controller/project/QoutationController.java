/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project")
public class QoutationController {

    @RequestMapping(value = "/quotation/replace")
    public @ResponseBody
    String replaceProjectBillItems(Long pdid, Integer location) {
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

        return "";
    }

    @RequestMapping(value = "/quotation/replace/add")
    public @ResponseBody
    String addBillMaterialService(Long id, Integer srcLocation, Integer newLocation, Long[] itemIds) {
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

        return "";
    }
}
