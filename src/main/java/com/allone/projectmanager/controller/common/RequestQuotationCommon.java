/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.entities.RequestQuotationItem;
import com.allone.projectmanager.model.ProjectModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antonia
 */
public class RequestQuotationCommon extends Common {

    private static final Logger logger = Logger.getLogger(RequestQuotationCommon.class.getName());

    private final Map<ProjectModel, List<RequestQuotationItem>> mapRequestQuotationItems = new HashMap<>();

    public Collection<RequestQuotationItem> getRequestQuotationItems(ProjectModel pm) {
        return (mapRequestQuotationItems != null && !mapRequestQuotationItems.isEmpty()) ? mapRequestQuotationItems.get(pm) : null;
    }

    public void setVirtualRequestQuotationItem(ProjectModel pm, RequestQuotationItem rqi) {
        if (pm != null && rqi != null) {
            Collection<RequestQuotationItem> items = getRequestQuotationItems(pm);

            if (items != null && !items.isEmpty()) {
                Map<Long, RequestQuotationItem> temp = new HashMap<>();

                items.stream().
                        forEach((item) -> {
                            temp.put(item.getItemBillMaterialService(), item);
                });

                if (!temp.isEmpty() && temp.get(rqi.getItemBillMaterialService()) == null) {
                    items.add(rqi);
                }
            } else {
                mapRequestQuotationItems.put(pm, new ArrayList<>(Arrays.asList(rqi)));
            }
        }
    }
}
