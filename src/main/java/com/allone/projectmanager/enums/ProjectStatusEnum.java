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
public enum ProjectStatusEnum {
    CREATE("CREATE"),
    PROJECT_BILL("PROJECT_BILL"),
    REQUEST_QUOTATION("REQUEST_QUOTATION"),
    PERCHASE_ORDER("PERCHASE_ORDER"),
    WORK_ORDER("WORK_ORDER"),
    ACK_ORDER("ACK_ORDER"),
    PACKING_LIST("PACKING_LIST"),
    DELIVERY_NOTE("DELIVERY_NOTE"),
    SHIPPING_INVOICE("SHIPPING_INVOICE"),
    BOX_MARKING("BOX_MARKING"),
    CREDIT_NOTE("CREDIT_NOTE");
    
    private String value;
    
    ProjectStatusEnum(String value) {
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
