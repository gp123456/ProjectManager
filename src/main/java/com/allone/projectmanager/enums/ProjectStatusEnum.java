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
    BILL_MATERIAL_SERVICE("Bill of Materials or Services"),
    QUOTATION("Quotation"),
    PURCHASE_ORDER("Purchase Order"),
    WORK_ORDER("Work Order"),
    ACK_ORDER("Acknowledge Order"),
    FORWARDING_DOCUMENTS("Forwarding Documents"),
    INVOICE("Invoice"),
    CREDIT_NOTE("Credit Note"),
    FINAL("Final");

    private final String value;

    ProjectStatusEnum(final String value) {
        this.value = value;
    }

    @Override
    public final String toString() {
        return this.value;
    }
}
