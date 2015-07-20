/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.model;

import java.math.BigDecimal;

/**
 *
 * @author antonia
 */
public class ProjectBillModel {
    Long id;
    String code;
    String description;
    Integer available;
    Integer qiantity;
    BigDecimal price;
    BigDecimal cost;

    public ProjectBillModel(Long id, String code, String description, Integer available, BigDecimal price) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.available = available;
        this.price = price;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getQiantity() {
        return qiantity;
    }

    public void setQiantity(Integer qiantity) {
        this.qiantity = qiantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
