/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "quotation")
@XmlRootElement
@NamedQueries(
        {@NamedQuery(name = "com.allone.projectmanager.entities.Quotation.findByBillMaterialService", query = "SELECT q FROM Quotation q WHERE q.billMaterialService = :billMaterialService"),
         @NamedQuery(name = "com.allone.projectmanager.entities.Quotation.findById", query = "SELECT q FROM Quotation q WHERE q.id = :id")})
public class Quotation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "bill_material_service")
    private Long billMaterialService;
    
    @Basic(optional = false)
    @Column(name = "total_cost")
    @NotNull
    private BigDecimal totalCost;

    @Basic(optional = false)
    @Column(name = "averange_discount")
    @NotNull
    private BigDecimal averangeDiscount;

    @Basic(optional = false)
    @Column(name = "total_sale_price")
    @NotNull
    private BigDecimal totalSalePrice;

    @Basic(optional = false)
    @Column(name = "total_net_price")
    @NotNull
    private BigDecimal totalNetPrice;
    
    @Basic(optional = false)
    @Column(name = "currency")
    @NotNull
    private Integer currency;

    @Basic(optional = false)
    @Column(name = "location")
    private Integer location;
    
    @Basic(optional = false)
    @Column(name = "complete", columnDefinition = "tinyint(1) default 0")
    @NotNull
    private Boolean complete;
    
    @Basic(optional = false)
    @Column(name = "note")
    private String note;

    private Quotation(Builder builder) {
        name = builder.name;
        billMaterialService = builder.billMaterialService;
        totalCost = builder.totalCost;
        averangeDiscount = builder.averangeDiscount;
        totalSalePrice = builder.totalSalePrice;
        totalNetPrice = builder.totalNetPrice;
        currency = builder.currency;
        location = builder.location;
        complete = builder.complete;
        note = builder.note;
    }

    private Quotation(Quotation builder) {
        name = builder.name;
        billMaterialService = builder.billMaterialService;
        totalCost = builder.totalCost;
        averangeDiscount = builder.averangeDiscount;
        totalSalePrice = builder.totalSalePrice;
        totalNetPrice = builder.totalNetPrice;
        currency = builder.currency;
        location = builder.location;
        complete = builder.complete;
        note = builder.note;
    }
    
    public Quotation() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBillMaterialService() {
        return billMaterialService;
    }

    public void setBillMaterialService(Long billMaterialService) {
        this.billMaterialService = billMaterialService;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getAverangeDiscount() {
        return averangeDiscount;
    }

    public void setAverangeDiscount(BigDecimal averangeDiscount) {
        this.averangeDiscount = averangeDiscount;
    }

    public BigDecimal getTotalSalePrice() {
        return totalSalePrice;
    }

    public void setTotalSalePrice(BigDecimal totalSalePrice) {
        this.totalSalePrice = totalSalePrice;
    }

    public BigDecimal getTotalNetPrice() {
        return totalNetPrice;
    }

    public void setTotalNetPrice(BigDecimal totalNetPrice) {
        this.totalNetPrice = totalNetPrice;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Quotation[ id=" + id + " ]";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(obj instanceof Quotation)) {
            return false;
        }
        
        Quotation other = (Quotation) obj;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
    
    public static class Builder {

        private String name;

        private Long billMaterialService;
        
        private BigDecimal totalCost;
        
        private BigDecimal averangeDiscount;

        private BigDecimal totalSalePrice;
        
        private BigDecimal totalNetPrice;
        
        private Integer currency;
        
        private Integer location;
        
        private Boolean complete;

        private String note;
        
        public Builder setName(String name) {
            this.name = name;

            return this;
        }
        
        public Builder setBillMaterialService(Long billMaterialService) {
            this.billMaterialService = billMaterialService;

            return this;
        }
        
        public Builder setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;

            return this;
        }
        
        public Builder setAverangeDiscount(BigDecimal averangeDiscount) {
            this.averangeDiscount = averangeDiscount;

            return this;
        }
        
        public Builder setTotalSalePrice(BigDecimal totalSalePrice) {
            this.totalSalePrice = totalSalePrice;

            return this;
        }
        
        public Builder setTotalNetPrice(BigDecimal totalNetPrice) {
            this.totalNetPrice = totalNetPrice;

            return this;
        }
        
        public Builder setCurrency(Integer currency) {
            this.currency = currency;

            return this;
        }
        
        public Builder setLocation(Integer location) {
            this.location = location;

            return this;
        }
        
        public Builder setComplete(Boolean complete) {
            this.complete = complete;

            return this;
        }
        
        public Builder setNote(String note) {
            this.note = note;

            return this;
        }
        
    }
}
