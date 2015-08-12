/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.enums;

/**
 *
 * @author antonia
 */
public enum ProjectTypeEnum {
    SALE("Sale"), SERVICE("Service"), SALE_SERVICE("SaleSearvice"), MTS("MTS");
    
    private String value;
    
    ProjectTypeEnum(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }

    public String getValue() {
        return value;
    }
}
