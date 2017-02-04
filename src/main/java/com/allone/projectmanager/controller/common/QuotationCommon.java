/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.entities.Quotation;
import com.allone.projectmanager.entities.QuotationItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author gpatitakis
 */
public class QuotationCommon extends Common {

    private static final Logger logger = Logger.getLogger(QuotationCommon.class.getName());

    private final Map<QuotationKey, Quotation> mapQuotation = new HashMap<>();

    private final Map<QuotationKey, List<QuotationItem>> mapQuotationItems = new HashMap<>();

    public void resetCommonValues() {
        mapQuotation.clear();
        mapQuotationItems.clear();
    }

    public void setMapQuotation(final Quotation q) {
        if (q != null) {
            mapQuotation.put(new QuotationKey(q.getBillMaterialService(), q.getLocation()), q);
        }
    }

    public Quotation getMapQuotation(final Long rq, final Integer location) {
        return (rq != null && location != null) ? mapQuotation.get(new QuotationKey(rq, location)) : null;
    }

    public Collection getMapQuotation() {
        return mapQuotation.values();
    }

    public Quotation removeMapQuotation(final Long rq, final Integer location) {
        return (rq != null && location != null) ? mapQuotation.remove(new QuotationKey(rq, location)) : null;
    }

    public void editMapQuotation(final Long rq, final Integer location, final Quotation item) {
        if (rq != null && location != null && item != null) {
            Quotation q = mapQuotation.get(new QuotationKey(rq, location));

            if (q != null) {
                if (item.getGrandTotal() != null) {
                    q.setGrandTotal(item.getGrandTotal());
                }
                if (item.getCurrency() != null) {
                    q.setCurrency(item.getCurrency());
                }
            }
        }
    }

    public void setMapQuotationItem(final Long rq, final Integer location, final QuotationItem item) {
        if (rq != null && location != null && item != null) {
            List<QuotationItem> items = mapQuotationItems.get(new QuotationKey(rq, location));

            if (items != null && !items.isEmpty()) {
                items.add(item);
            } else {
                mapQuotationItems.put(new QuotationKey(rq, location), new ArrayList<>(Arrays.asList(item)));
            }
        }
    }

    public List getMapQuotationItem(final Long rq, final Integer location) {
        List<QuotationItem> items = (rq != null && location != null) ? mapQuotationItems.get(new QuotationKey(rq, location)) : null;

        return items;
    }

    public void editMapQuotationItem(final Long rq, final Integer location, final QuotationItem item) {
        if (rq != null && location != null && item != null) {
            List<QuotationItem> items = getMapQuotationItem(rq, location);

            if (items != null && !items.isEmpty()) {
                items.stream().filter((qi) -> (qi.equals(item))).forEach((qi) -> {
                    qi.setUnitPrice(item.getUnitPrice());
                    qi.setDiscount(item.getDiscount());
                    qi.setTotal(item.getTotal());
                    qi.setEdit(item.getEdit());
                });
            }
        }
    }

    public List removeMapQuotationItem(final Long rq, final Integer location, QuotationItem item) {
        if (rq != null && location != null && item != null) {
            List<QuotationItem> items = mapQuotationItems.get(new QuotationKey(rq, location));

            if (items != null && !items.isEmpty()) {
                items.remove(item);

                return items;
            }
        }

        return null;
    }
}

class QuotationKey {

    private Integer location;

    private Long rq;

    public QuotationKey(final Long rq, final Integer location) {
        this.location = location;
        this.rq = rq;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(final Integer location) {
        this.location = location;
    }

    public Long getRq() {
        return rq;
    }

    public void setRq(final Long rq) {
        this.rq = rq;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null) {
            final QuotationKey qk = (QuotationKey) obj;

            return (qk.getRq().equals(this.rq)) ? qk.getLocation().equals(this.location) : false;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return rq.intValue() + location;
    }
}

class RequestQuotationInfo {

    private final Long pd;

    private final Long rq;

    public RequestQuotationInfo(final Long pd, final Long rq) {
        this.pd = pd;
        this.rq = rq;
    }

    public Long getPd() {
        return pd;
    }

    public Long getRq() {
        return rq;
    }
}
