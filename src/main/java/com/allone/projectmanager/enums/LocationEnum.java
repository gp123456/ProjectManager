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
    GREECE(1, "GREECE"),
    CHINA(2, "CHINA"),
    SKOREA(3, "S KOREA"),
    TAIWAN(4, "TAIWAN");

    private final Integer id;

    private final String name;

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
