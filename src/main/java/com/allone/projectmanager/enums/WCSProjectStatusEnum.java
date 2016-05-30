/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.enums;

/**
 *
 * @author Admin
 */
public enum WCSProjectStatusEnum {
    CREATE("CREATE"),
    ORDER("ORDER"),
    DELIVERY("DELIVERY"),
    INVOICING("INVOICING"),
    VALIDATE("VALIDATE"),
    PAYMENT("PAYMENT"),
    COMPLETE("COMPLETE"),
    CANCEL("CANCEL"),
    WAITING("WAITING"),
    OFFER("OFFER"),
    CLAIM("CLAIM"),
    NONE("NONE"),
    BALANCE_WAREHOUSE("BALANCE WAREHOUSE");
    
    private String value;

    private WCSProjectStatusEnum(String value) {
        this.value = value;
    }
}
