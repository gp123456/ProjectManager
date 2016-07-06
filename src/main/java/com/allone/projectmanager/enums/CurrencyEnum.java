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
    NONE(1, "None", "None"),//None
    JPY(2, "JPY", "JPY"),//¥,&#165
    GBP(3, "GBP", "GBP"),//£,&#163
    USD(4, "USD", "USD"),//$, &#36
    EUR(5, "EUR", "EUR");//€, &#8364

    private final Integer id;
    
    private final String value;
    
    private final String symbol;

    private CurrencyEnum(final Integer id, final String value, final String symbol) {
        this.id = id;
        this.value = value;
        this.symbol = symbol;
    }

    public Integer getId() {
        return id;
    }
    
    public String getSymbol() {
        return symbol;
    }

    @Override
    public final String toString() {
        return this.value; //To change body of generated methods, choose Tools | Templates.
    }
}
