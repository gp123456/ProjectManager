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
public final class DBFilesEnum {

    public static final String COMPANY = "COMPANY";
    public static final String VESSEL = "VESSEL";
    public static final String CONTACT = "CONTACT";
    
    public static final String[] values() {
        return new String[] {
          COMPANY,
          VESSEL,
          CONTACT
        };
    }
}
