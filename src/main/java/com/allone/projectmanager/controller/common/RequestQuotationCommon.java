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

/**
 *
 * @author antonia
 */
public class RequestQuotationCommon extends Common {

    private final Map<ProjectModel, List<RequestQuotationItem>> mapRequestQuotationItems = new HashMap<>();

    public Collection<RequestQuotationItem> getRequestQuotationItems(ProjectModel pm) {
        return (mapRequestQuotationItems != null && !mapRequestQuotationItems.isEmpty()) ? mapRequestQuotationItems.get(pm) : null;
    }

    public void setVirtualRequestQuotationItem(ProjectModel pm, RequestQuotationItem rqi) {
        if (rqi != null && rqi != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(pm);

            if (items != null) {
                items.add(rqi);
            } else {
                mapRequestQuotationItems.put(pm, new ArrayList<>(Arrays.asList(rqi)));
            }
        }
    }
}
