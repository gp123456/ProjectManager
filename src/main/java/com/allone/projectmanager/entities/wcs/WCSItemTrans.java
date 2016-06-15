/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities.wcs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "ITEMTRANS")
public class WCSItemTrans implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITTRCODE")
    @NotNull
    private Integer code;
    
    @Column(name = "ITTRIMCODE")
    @NotNull
    Integer itemCode;
    
    @Column(name = "ITTRDATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(name = "ITTRQUANTITY")
    BigDecimal quantity;
    
    @Column(name = "ITTRCOST")
    BigDecimal cost;
    
    @Column(name = "ITTRNOTES")
    BigDecimal notes;
    
    @Column(name = "ITTRIMDESCR")
    BigDecimal itemDescription;
    
    @Column(name = "ITTRPRCODE")
    String prjCode;
    
    @Column(name = "ITTRCURR")
    String currency;
    
    @Column(name = "ITTRIMNO")
    String itemNo;
    
    @Column(name = "ITTRSTATUS")
    Integer status;
    
    @Column(name = "ITTRSTDESC")
    Integer stDescription;
    
    @Column(name = "ITTROFFERVALUE")
    BigDecimal offer;

    public WCSItemTrans() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getNotes() {
        return notes;
    }

    public void setNotes(BigDecimal notes) {
        this.notes = notes;
    }

    public BigDecimal getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(BigDecimal itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getPrjCode() {
        return prjCode;
    }

    public void setPrjCode(String prjCode) {
        this.prjCode = prjCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Integer getStDescription() {
        return stDescription;
    }

    public void setStDescription(Integer stDescription) {
        this.stDescription = stDescription;
    }

    public BigDecimal getOffer() {
        return offer;
    }

    public void setOffer(BigDecimal offer) {
        this.offer = offer;
    }
}
