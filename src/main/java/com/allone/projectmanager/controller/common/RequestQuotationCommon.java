/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.entities.BillMaterialService;
import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.RequestQuotationItem;
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

    private final Map<Long, RequestQuotation> mapRequestQuotation = new HashMap<>();

    private final Map<Long, List<RequestQuotationItem>> mapRequestQuotationItems = new HashMap<>();

    public Collection<RequestQuotationItem> getRequestQuotationItems(Long bms) {
        return (mapRequestQuotationItems != null && !mapRequestQuotationItems.isEmpty()) ? mapRequestQuotationItems.get(bms) : null;
    }

    public void setVirtualRequestQuotation(ProjectManagerService srvProjectManager, Long bmsId, RequestQuotation rq) {
        logger.log(Level.INFO, "setVirtualRequestQuotation-start:{0}", bmsId);

        if (bmsId != null
                && rq != null
                && (mapRequestQuotation.get(bmsId) == null)) {
            BillMaterialService bms = srvProjectManager.getDaoBillMaterialService().getById(bmsId);

            rq.setNote(bms.getNote());
            mapRequestQuotation.put(bmsId, rq);
        }

        logger.log(Level.INFO, "setVirtualRequestQuotation-end:{0}", mapRequestQuotation.size());
    }

    public void removeVirtualRequestQuotation(Long bms) {
        if (bms != null) {
            RequestQuotation rq = mapRequestQuotation.get(bms);

            if (rq != null) {
                mapRequestQuotation.remove(bms);
            }

            List<RequestQuotationItem> items = mapRequestQuotationItems.get(bms);

            if (items != null && !items.isEmpty()) {
                mapRequestQuotationItems.remove(bms);
            }
        }
    }

    public void setVirtualRequestQuotationItem(Long bms, RequestQuotationItem rqi) {
        logger.log(Level.INFO, "setVirtualRequestQuotationItem-start:{0}", bms);

        if (bms != null
                && rqi != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(bms);

            if (items != null && !items.isEmpty()) {
                items.add(rqi);
            } else {
                mapRequestQuotationItems.put(bms, new ArrayList<>(Arrays.asList(rqi)));
            }
        }
    }

    public void removeVirtualRequestQuotationItem(Long bms, RequestQuotationItem rqi) {
        if (bms != null
                && rqi != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(bms);

            if (items != null && !items.isEmpty()) {
                items.remove(rqi);
            }
        }
    }

    public Boolean findVirtualItem(Long bms, Long itemId) {
        if (bms != null && itemId != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(bms);

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

    public RequestQuotationItem getRequestQoutationItem(Long bms, Long itemId) {
        if (bms != null && itemId != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(bms);

            if (items != null && !items.isEmpty()) {
                for (RequestQuotationItem item : items) {
                    if (item.getBillMaterialServiceItem().equals(itemId)) {
                        return item;
                    }
                }
            }
        }

        return null;
    }

    public RequestQuotation getRequestQuotation(Long bms) {
        return (mapRequestQuotation != null && !mapRequestQuotation.isEmpty()) ? mapRequestQuotation.get(bms) : null;
    }

    public List<RequestQuotationItem> getRequestQoutationItems(Long bms) {
        return (mapRequestQuotationItems != null && !mapRequestQuotationItems.isEmpty())
                ? mapRequestQuotationItems.get(bms)
                : null;
    }
}
