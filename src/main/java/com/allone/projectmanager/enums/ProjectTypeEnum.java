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
    SALE("Sale"),
    SERVICE("Service"),
    SALE_SERVICE("Stock Order");

    private final String value;

    ProjectTypeEnum(final String value) {
        this.value = value;
    }

    @Override
    public final String toString() {
        return this.value;
    }
}
