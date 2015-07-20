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
public enum ProjectTypeEnum {
    SALE(1l, "SALE"), SERVICE(2l, "SERVICE");
    
    private Long id;
    private String value;
    
    ProjectTypeEnum(Long id, String value) {
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
