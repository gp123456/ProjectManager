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
    @NamedQuery(name = "com.allone.projectmanager.entities.QuotationItem.findById", query = "SELECT qi FROM QuotationItem qi WHERE qi.id = :id")
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
    @Column(name = "request_for_quotation_item")
    @NotNull
    private Long requestForQuotationItem;

    @Basic(optional = false)
    @Column(name = "discount")
    @NotNull
    private BigDecimal discount;

    @Basic(optional = false)
    @Column(name = "unit_price")
    @NotNull
    private BigDecimal unitPrice;

    @Basic(optional = false)
    @Column(name = "total")
    @NotNull
    private BigDecimal total;

    private QuotationItem(Builder builder) {
        this.quotation = builder.quotation;
        this.requestForQuotationItem = builder.requestForQuotationItem;
        this.discount = builder.discount;
        this.unitPrice = builder.unitPrice;
        this.total = builder.total;
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

    public Long getRequestForQuotationItem() {
        return requestForQuotationItem;
    }

    public void setRequestForQuotationItem(Long requestForQuotationItem) {
        this.requestForQuotationItem = requestForQuotationItem;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

        private Long requestForQuotationItem;

        private BigDecimal discount;

        private BigDecimal unitPrice;

        private BigDecimal total;

        public Builder setRequestForQuotationItem(Long requestForQuotationItem) {
            this.requestForQuotationItem = requestForQuotationItem;

            return this;
        }

        public Builder setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;

            return this;
        }

        public Builder setTotal(BigDecimal total) {
            this.total = total;

            return this;
        }

        public Builder setDiscount(BigDecimal discount) {
            this.discount = discount;

            return this;
        }

        public QuotationItem build() {
            return new QuotationItem(this);
        }
    }
}
