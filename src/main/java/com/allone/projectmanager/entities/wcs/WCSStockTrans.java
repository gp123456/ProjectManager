/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities.wcs;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "SALESTRANS")
public class WCSStockTrans implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STCODE")
    @NotNull
    private String code;
    
    @Column(name = "STIMCODE")
    private Integer itemCode;
    
    @Column(name = "STDATE")
    private String date;
    
    @Column(name = "STQUANTITY")
    private BigDecimal quantity;
    
    @Column(name = "STPRICE")
    private BigDecimal price;
    
    @Column(name = "STSUPPLIER")
    private String supplier;
    
    @Column(name = "STSTOCK")
    private String stock;
    
    @Column(name = "STNOTES")
    private String note;
    
    @Column(name = "STIMNO")
    private String itemNo;
    
    @Column(name = "STSTATUS")
    private Integer status;
    
    @Column(name = "STSTDESCR")
    private String descriptionStatus;
    
    @Column(name = "STNOCALCPRICE")
    private Integer noCalcPrice;
    
    @Column(name = "STSORTDATE")
    private Long sortDate;

    public WCSStockTrans() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public void setDescriptionStatus(String descriptionStatus) {
        this.descriptionStatus = descriptionStatus;
    }

    public Integer getNoCalcPrice() {
        return noCalcPrice;
    }

    public void setNoCalcPrice(Integer noCalcPrice) {
        this.noCalcPrice = noCalcPrice;
    }

    public Long getSortDate() {
        return sortDate;
    }

    public void setSortDate(Long sortDate) {
        this.sortDate = sortDate;
    }
}
