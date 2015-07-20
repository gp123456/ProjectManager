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
public enum CompanyEnum {
    WCS("WCS"),
    WCS_LCD("WCS LCD"),
    WCS_HELLAS("WCS HELLAS"),
    MTS("MTS");
    
    private String value;
    
    CompanyEnum(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
