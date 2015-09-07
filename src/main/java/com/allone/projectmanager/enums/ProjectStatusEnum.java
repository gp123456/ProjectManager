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
    CREATE("Create"),
    PROJECT_BILL("Project Bill"),
    REQUEST_QUOTATION("Request Quotation"),
    PERCHASE_ORDER("Purchase Order"),
    WORK_ORDER("Work Order"),
    ACK_ORDER("Acknowledge Order"),
    PACKING_LIST("Packing List"),
    DELIVERY_NOTE("Delivery Note"),
    SHIPPING_INVOICE("Shipping Invoice"),
    BOX_MARKING("Box Marking"),
    CREDIT_NOTE("Credit Note");
    
    private final String value;
    
    ProjectStatusEnum(final String value) {
        this.value = value;
    }
    
    @Override
    public final String toString() {
        return this.value;
    }
}
