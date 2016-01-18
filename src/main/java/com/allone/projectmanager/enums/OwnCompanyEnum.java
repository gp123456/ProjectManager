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
public enum OwnCompanyEnum {
    MARPO("MARPO"),
    WCS_LTD("WCS LTD"),
    WCS_HELLAS("WCS HELLAS"),
    MTS("MTS");
    
    private final String value;
    
    OwnCompanyEnum(final String value) {
        this.value = value;
    }

    @Override
    public final String toString() {
        return this.value;
    }
}
