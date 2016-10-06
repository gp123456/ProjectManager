/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.entities.RequestQuotation;
import com.allone.projectmanager.entities.RequestQuotationItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author antonia
 */
public class RequestQuotationCommon extends Common {

    private static final Logger logger = Logger.getLogger(RequestQuotationCommon.class.getName());

    private final Map<Long, RequestQuotation> mapRequestQuotation = new HashMap<>();

    private final Map<Long, List<RequestQuotationItem>> mapRequestQuotationItems = new HashMap<>();

//    private final Map<Long, List<String>> mapSuppliers = new HashMap<>();
    public Collection<RequestQuotationItem> getRequestQuotationItems(Long bms) {
        return (mapRequestQuotationItems != null && !mapRequestQuotationItems.isEmpty()) ? mapRequestQuotationItems.get(bms) : null;
    }

    public void setRequestQuotation(Long bmsId, RequestQuotation rq) {
        if (bmsId != null && rq != null && (mapRequestQuotation.get(bmsId) == null)) {
            mapRequestQuotation.put(bmsId, rq);
        }
    }

    public void removeRequestQuotation(Long bms) {
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

    public void setRequestQuotationItem(Long bms, RequestQuotationItem rqi) {
        if (bms != null && rqi != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(bms);

            if (items != null && !items.isEmpty()) {
                items.add(rqi);
            } else {
                mapRequestQuotationItems.put(bms, new ArrayList<>(Arrays.asList(rqi)));
            }
        }
    }

    public void removeRequestQuotationItem(Long bms, RequestQuotationItem rqi) {
        if (bms != null && rqi != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(bms);

            if (items != null && !items.isEmpty()) {
                items.remove(rqi);
            }
        }
    }

    public void removeRequestQuotationItems(Long bms) {
        if (bms != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(bms);

            if (items != null && !items.isEmpty()) {
                items.clear();
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

//    public Collection<String> addSupplier(Long bms, String supplier) {
//        if (bms != null && !Strings.isNullOrEmpty(supplier)) {
//            List<String> suppliers = mapSuppliers.get(bms);
//
//            if (suppliers != null && !suppliers.isEmpty()) {
//                suppliers.add(supplier);
//            } else {
//                mapSuppliers.put(bms, new ArrayList<>(Arrays.asList(supplier)));
//            }
//        }
//
//        return mapSuppliers.get(bms);
//    }
//
//    public Collection<String> getSuppliers(Long bms) {
//        return mapSuppliers.get(bms);
//    }
//
//    public Collection<String> removeVirtualSupplier(Long bms, String supplier) {
//        if (bms != null && !Strings.isNullOrEmpty(supplier)) {
//            List<String> suppliers = mapSuppliers.get(bms);
//
//            if (suppliers != null && !suppliers.isEmpty()) {
//                suppliers.remove(supplier);
//            }
//        }
//
//        return mapSuppliers.get(bms);
//    }
}
