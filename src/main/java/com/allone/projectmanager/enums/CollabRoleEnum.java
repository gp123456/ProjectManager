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
public enum CollabRoleEnum {
    SUPER_ADMIN("SUPER ADMIN"),
    ADMIN("ADMIN"),
    TECHNICAL("TECHNICAL"),
    ACCOUNTER("ACCOUNTER"),
    STOREKEEPER("STOREKEEPER"),
    GUEST("GUEST");

    private String value;

    private CollabRoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
