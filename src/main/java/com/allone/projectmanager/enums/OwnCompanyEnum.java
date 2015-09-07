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
    WCS("wcs"),
    WCS_LCD("wcs lcd"),
    WCS_HELLAS("wcs hellas"),
    MTS("mts");
    
    private final String value;
    
    OwnCompanyEnum(final String value) {
        this.value = value;
    }

    @Override
    public final String toString() {
        return this.value;
    }
}
