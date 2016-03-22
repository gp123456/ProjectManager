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
@Table(name = "quotation_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.QuotationItem.findById", query = "SELECT qi FROM QuotationItem qi WHERE qi.id = :id"),
    @NamedQuery(name = "com.allone.projectmanager.entities.BillMaterialServiceItem.findByQuotation", query = "SELECT qi FROM QuotationItem qi WHERE qi.quotation = :quotation")
})
public class QuotationItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "quotation")
    @NotNull
    private Long quotation;
    
    @Basic(optional = false)
    @Column(name = "bill_material_service_item")
    @NotNull
    private Long billMaterialServiceItem;
    
    @Basic(optional = false)
    @Column(name = "cost")
    @NotNull
    private BigDecimal cost;

    @Basic(optional = false)
    @Column(name = "total_cost")
    @NotNull
    private BigDecimal totalCost;

    @Basic(optional = false)
    @Column(name = "percentage")
    @NotNull
    private BigDecimal percentage;

    @Basic(optional = false)
    @Column(name = "discount")
    @NotNull
    private BigDecimal discount;

    @Basic(optional = false)
    @Column(name = "sale_price")
    @NotNull
    private BigDecimal salePrice;

    @Basic(optional = false)
    @Column(name = "total_sale_price")
    @NotNull
    private BigDecimal totalSalePrice;

    @Column(name = "total_net_price")
    private BigDecimal totalNetPrice;

    private QuotationItem(Builder builder) {
        this.quotation = builder.quotation;
        this.billMaterialServiceItem = builder.billMaterialServiceItem;
        this.cost = builder.cost;
        this.totalCost = builder.totalCost;
        this.percentage = builder.percentage;
        this.discount = builder.discount;
        this.salePrice = builder.salePrice;
        this.totalSalePrice = builder.totalSalePrice;
        this.totalNetPrice = builder.totalNetPrice;
    }

    private QuotationItem(QuotationItem builder) {
        this.quotation = builder.quotation;
        this.billMaterialServiceItem = builder.billMaterialServiceItem;
        this.cost = builder.cost;
        this.totalCost = builder.totalCost;
        this.percentage = builder.percentage;
        this.discount = builder.discount;
        this.salePrice = builder.salePrice;
        this.totalSalePrice = builder.totalSalePrice;
        this.totalNetPrice = builder.totalNetPrice;
    }
    
    public QuotationItem() {
    }

    public Long getId() {
        return id;
    }

    public Long getQuotation() {
        return quotation;
    }

    public void setQuotation(Long quotation) {
        this.quotation = quotation;
    }

    public Long getBillMaterialServiceItem() {
        return billMaterialServiceItem;
    }

    public void setBillMaterialServiceItem(Long billMaterialServiceItem) {
        this.billMaterialServiceItem = billMaterialServiceItem;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuotationItem)) {
            return false;
        }
        
        QuotationItem other = (QuotationItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.QuotationItem[ id=" + id + " ]";
    }
    
    public static class Builder {

        private Long quotation;
        
        private Long billMaterialServiceItem;
        
        private BigDecimal cost;
        
        private BigDecimal totalCost;
        
        private BigDecimal percentage;
        
        private BigDecimal discount;
        
        private BigDecimal salePrice;
        
        private BigDecimal totalSalePrice;
        
        private BigDecimal totalNetPrice;
        
        public Builder setBillMaterialServiceItem(Long billMaterialServiceItem) {
            this.billMaterialServiceItem = billMaterialServiceItem;

            return this;
        }

        public Builder setCost(BigDecimal cost) {
            this.cost = cost;

            return this;
        }

        public Builder setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;

            return this;
        }
        
        public Builder setPercentage(BigDecimal percentage) {
            this.percentage = percentage;

            return this;
        }
        
        public Builder setSalePrice(BigDecimal salePrice) {
            this.salePrice = salePrice;

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
        
        public Builder setDiscount(BigDecimal discount) {
            this.discount = discount;

            return this;
        }
        
        public QuotationItem build() {
            return new QuotationItem(this);
        }

        public QuotationItem build(QuotationItem qi) {
            return new QuotationItem(qi);
        }
    }
    
}
