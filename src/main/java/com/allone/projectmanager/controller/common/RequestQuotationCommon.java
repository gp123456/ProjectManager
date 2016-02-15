/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.entities.RequestQuotationItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author antonia
 */
public class RequestQuotationCommon extends Common {

    private final Map<Long, List<RequestQuotationItem>> mapRequestQuotationItems = new HashMap<>();

    public Collection<RequestQuotationItem> getRequestQuotationItems(Long billId) {
        return mapRequestQuotationItems.get(billId);
    }

    public Set<Long> getRequestQuotationIds() {
        return mapRequestQuotationItems.keySet();
    }

    public void setVirtualRequestQuotationItem(Long pb, RequestQuotationItem rqi) {
        if (rqi != null && rqi != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(pb);

            if (items != null) {
                items.add(rqi);
            } else {
                mapRequestQuotationItems.put(pb, new ArrayList<>(Arrays.asList(rqi)));
            }
        }
    }

    public void editVirtualRequestQuotationItem(Long pb, RequestQuotationItem rqi) {
        if (rqi != null && rqi != null && rqi.getProjectBillItem() != null) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(pb);

            if (items != null && !items.isEmpty()) {
                items.stream().
                        filter((item) -> (item.getProjectBillItem().equals(rqi.getProjectBillItem()))).
                        forEach((item) -> {
                            item = rqi;
                        });
            }
        }
    }

    public RequestQuotationItem getRequestQuotationItem(Long pbId, Long pbiId) {
        if (!mapRequestQuotationItems.isEmpty()) {
            List<RequestQuotationItem> items = mapRequestQuotationItems.get(pbId);

            if (items != null && !items.isEmpty()) {
                Map<Long, RequestQuotationItem> result = items.stream().collect(Collectors.toMap(
                                                RequestQuotationItem::getProjectBillItem, (c) -> c));

                return result.get(pbiId);
            }
        }

        return null;
    }
}
