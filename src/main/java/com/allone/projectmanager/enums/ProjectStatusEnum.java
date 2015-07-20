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
    CREATE(1l, "CREATE"),
    PROJECT_BILL(2l, "PROJECT_BILL"),
    REQUEST_QUOTATION(3l, "REQUEST_QUOTATION"),
    PERCHASE_ORDER(4l, "PERCHASE_ORDER"),
    WORK_ORDER(5l, "WORK_ORDER"),
    ACK_ORDER(6l, "ACK_ORDER"),
    PACKING_LIST(7l, "PACKING_LIST"),
    DELIVERY_NOTE(8l, "DELIVERY_NOTE"),
    SHIPPING_INVOICE(9l, "SHIPPING_INVOICE"),
    BOX_MARKING(10l, "BOX_MARKING"),
    CREDIT_NOTE(11l, "CREDIT_NOTE");
    
    private Long id;
    
    private String value;
    
    ProjectStatusEnum(Long id, String value) {
        this.id = id;
        this.value = value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
