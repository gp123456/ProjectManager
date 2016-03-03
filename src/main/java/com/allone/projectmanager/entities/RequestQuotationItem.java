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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "request_quotation_item")
@XmlRootElement
public class RequestQuotationItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Column(name = "item_bill_material_service")
    @NotNull
    private Long itemBillMaterialService;

    @Column(name = "supplier")
    @NotNull
    private String supplier;

    @Column(name = "availability")
    @NotNull
    private Integer availability;
    
    @Column(name = "delivery_cost")
    @NotNull
    private BigDecimal deliveryCost;
    
    @Column(name = "other_expenses")
    @NotNull
    private BigDecimal otherExpenses;
    
    @Column(name = "unit_price")
    @NotNull
    private BigDecimal unitPrice;

    @Column(name = "discount")
    @NotNull
    private BigDecimal discount;

    @Column(name = "total")
    @NotNull
    private BigDecimal total;

    public RequestQuotationItem() {
    }

    private RequestQuotationItem(Builder builder) {
        itemBillMaterialService = builder.itemBillMaterialService;
        supplier = builder.supplier;
        availability = builder.availability;
        deliveryCost = builder.deliveryCost;
        otherExpenses = builder.otherExpenses;
        unitPrice = builder.unitPrice;
        discount = builder.discount;
        total = builder.total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Long getItemBillMaterialService() {
        return itemBillMaterialService;
    }

    public void setItemBillMaterialService(Long itemBillMaterialService) {
        this.itemBillMaterialService = itemBillMaterialService;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(BigDecimal otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash += (id != null ? id.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestQuotationItem)) {
            return false;
        }

        RequestQuotationItem other = (RequestQuotationItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.ProjectRequestQuotationItem[ id=" + id + " ]";
    }

    public static class Builder {

        private Long itemBillMaterialService;

        private String supplier;

        private Integer availability;
        
        private BigDecimal deliveryCost;
    
        private BigDecimal otherExpenses;
    
        private BigDecimal unitPrice;
    
        private BigDecimal discount;

        private BigDecimal total;

        public Builder setItemBillMaterialService(Long itemBillMaterialService) {
            this.itemBillMaterialService = itemBillMaterialService;

            return this;
        }

        public Builder setSupplier(String supplier) {
            this.supplier = supplier;

            return this;
        }
        
        public Builder setAvailability(Integer availability) {
            this.availability = availability;

            return this;
        }
        
        public Builder setDeliveryCost(BigDecimal deliveryCost) {
            this.deliveryCost = deliveryCost;

            return this;
        }
        
        public Builder setOtherExpenses(BigDecimal otherExpenses) {
            this.otherExpenses = otherExpenses;

            return this;
        }
        
        public Builder setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;

            return this;
        }

        public Builder setDiscount(BigDecimal discount) {
            this.discount = discount;

            return this;
        }

        public Builder setTotal(BigDecimal total) {
            this.total = total;

            return this;
        }

        public RequestQuotationItem build() {
            return new RequestQuotationItem(this);
        }
    }
}
