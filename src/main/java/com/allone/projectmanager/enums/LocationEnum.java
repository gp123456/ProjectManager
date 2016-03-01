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
public enum LocationEnum {
    GREECE(1, "greece"),
    CHINA(2, "china"),
    SKOREA(3, "s korea"),
    TAIWAN(4, "taiwan");
    
    private Integer id;
    
    private String name;

    private LocationEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
