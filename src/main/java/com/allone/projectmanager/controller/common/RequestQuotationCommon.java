/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.RequestQuotationItem;
import com.allone.projectmanager.model.RequestQuotationModel;
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

    private final Map<RequestQuotationModel, RequestQuotation> mapRequestQuotation = new HashMap<>();

    private final Map<RequestQuotationModel, List<RequestQuotationItem>> mapRequestQuotationItems = new HashMap<>();

    public Collection<RequestQuotationItem> getRequestQuotationItems(RequestQuotationModel pm) {
        return (mapRequestQuotationItems != null && !mapRequestQuotationItems.isEmpty()) ? mapRequestQuotationItems.get(pm) : null;
    }

    public void setVirtualRequestQuotation(RequestQuotationModel rqm, RequestQuotation rq) {
        if (rqm != null && rq != null &&
            (mapRequestQuotation.get(rqm) == null)) {
            mapRequestQuotation.put(rqm, rq);
        }
    }
    
    public void removeVirtualRequestQuotation(RequestQuotationModel rqm, RequestQuotation rq) {
        if (rqm != null && rq != null &&
            (mapRequestQuotation.get(rqm) != null)) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(rqm);
            
            if (items == null || items.isEmpty()) {
                mapRequestQuotation.remove(rqm);
            }
        }
    }

    public void setVirtualRequestQuotationItem(RequestQuotationModel rqm, RequestQuotationItem rqi) {
        if (rqm != null && rqi != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(rqm);

            if (items != null && !items.isEmpty()) {
                items.add(rqi);
            } else {
                mapRequestQuotationItems.put(rqm, new ArrayList<>(Arrays.asList(rqi)));
            }
        }
    }
    
    public void removeVirtualRequestQuotationItem(RequestQuotationModel rqm, RequestQuotationItem rqi) {
        if (rqm != null && rqi != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(rqm);

            if (items != null && !items.isEmpty()) {
                items.remove(rqi);
            }
        }
    }

    public Boolean findVirtualItem(RequestQuotationModel rqm, Long itemId) {
        if (rqm != null && itemId != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(rqm);

            if (items != null && !items.isEmpty()) {
                for (RequestQuotationItem item : items) {
                    if (item.getBillMaterialServiceItem().equals(itemId)) {
                        return Boolean.TRUE;
                    }
                }
            }
        }

        return Boolean.FALSE;
    }
    
    public RequestQuotation getRequestQuotation(RequestQuotationModel rqm) {
        return (mapRequestQuotation != null && !mapRequestQuotation.isEmpty() ) ? mapRequestQuotation.get(rqm) : null;
    }
}
