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
public enum CurrencyEnum {
    JPY(1, "&#165"),//¥
    GBP(2, "&#163"),//£
    USD(3, "&#36"),//$
    EUR(4, "&#8364");//€

    private final Integer id;
    
    private final String value;

    private CurrencyEnum(final Integer id, final String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public final String toString() {
        return this.value; //To change body of generated methods, choose Tools | Templates.
    }
}
