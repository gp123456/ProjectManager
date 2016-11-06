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
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
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
    @NamedQuery(name = "com.allone.projectmanager.entities.QuotationItem.findById",
            query = "SELECT qi FROM QuotationItem qi WHERE qi.id = :id"),
    @NamedQuery(name = "com.allone.projectmanager.entities.QuotationItem.findByQuotation",
            query = "SELECT qi FROM QuotationItem qi WHERE qi.quotation = :quotation")
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
    @Column(name = "request_quotation_item")
    @NotNull
    private Long requestQuotationItem;

    @Basic(optional = false)
    @Column(name = "discount")
    @Digits(integer = 10, fraction = 1, message = "Validation digits failed for discount")
    private BigDecimal discount;

    @Basic(optional = false)
    @Column(name = "unit_price")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for unitPrice")
    private BigDecimal unitPrice;

    @Basic(optional = false)
    @Column(name = "total")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for total")
    private BigDecimal total;

    @Transient
    private Boolean edit;

    private QuotationItem(Builder builder) {
        this.quotation = builder.quotation;
        this.requestQuotationItem = builder.requestQuotationItem;
        this.discount = builder.discount;
        this.unitPrice = builder.unitPrice;
        this.total = builder.total;
        this.edit = builder.edit;
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

    public Long getRequestQuotationItem() {
        return requestQuotationItem;
    }

    public void setRequestQuotationItem(Long requestQuotationItem) {
        this.requestQuotationItem = requestQuotationItem;
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

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
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
        return (this.id == null || other.id == null)
                ? (other.requestQuotationItem != null && this.requestQuotationItem != null
                && this.requestQuotationItem.equals(other.requestQuotationItem)) ? true : false
                : (this.id.equals(other.id)) ? true : false;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.QuotationItem[ id=" + id + " ]";
    }

    public static class Builder {

        private Long quotation;

        private Long requestQuotationItem;

        private BigDecimal discount;

        private BigDecimal unitPrice;

        private BigDecimal total;

        private Boolean edit;

        public Builder setRequestQuotationItem(Long requestQuotationItem) {
            this.requestQuotationItem = requestQuotationItem;

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

        public Builder setEdit(Boolean edit) {
            this.edit = edit;

            return this;
        }

        public QuotationItem build() {
            return new QuotationItem(this);
        }
    }
}
