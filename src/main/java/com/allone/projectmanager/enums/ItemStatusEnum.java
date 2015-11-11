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
public enum ItemStatusEnum {
    VALID("valid"),
    RETURN("return"),
    CANCEL("cancel");
    
    private final String value;

    private ItemStatusEnum(final String value) {
        this.value = value;
    }

    @Override
    public final String toString() {
        return this.value; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
