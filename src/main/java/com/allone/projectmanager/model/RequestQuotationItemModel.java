/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.model;

import java.math.BigDecimal;

/**
 *
 * @author gpatitakis
 */
public class RequestQuotationItemModel {

    public Long id;
    public Integer qty;
    public BigDecimal price;
    public Integer discount;
    public Integer availability;
    public BigDecimal net;

    public RequestQuotationItemModel(Long id, Integer qty) {
        this.id = id;
        this.qty = qty;
    }
}
