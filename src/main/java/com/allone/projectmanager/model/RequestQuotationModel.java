/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.model;

import com.google.common.base.Strings;

/**
 *
 * @author antonia
 */
public class RequestQuotationModel {
    
    private Long projectDetailId;

    private String supplier;

    public RequestQuotationModel(Long projectDetailId, String supplier) {
        this.projectDetailId = projectDetailId;
        this.supplier = supplier;
    }

    public Long getProjectDetailId() {
        return projectDetailId;
    }

    public void setProjectDetailId(Long projectDetailId) {
        this.projectDetailId = projectDetailId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object obj) {
        RequestQuotationModel rqm = (RequestQuotationModel) obj;
        
        if (!Strings.isNullOrEmpty(rqm.getSupplier())) {
            return (projectDetailId.equals(rqm.getProjectDetailId())) ? supplier.equals(rqm.getSupplier()) : false;
        } else {
            return (projectDetailId.equals(rqm.getProjectDetailId()));
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + this.projectDetailId.intValue() + this.supplier.length();

        return result;
    }
}
