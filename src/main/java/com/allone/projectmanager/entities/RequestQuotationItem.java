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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "request_quotation_item")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "com.allone.projectmanager.entities.RequestQuotationItem.findById", query = "SELECT rqi FROM RequestQuotationItem rqi WHERE rqi.id = :id"),
            @NamedQuery(name = "com.allone.projectmanager.entities.RequestQuotationItem.findByBillMaterialServiceItem", query = "SELECT rqi FROM RequestQuotationItem rqi WHERE rqi.billMaterialServiceItem = :billMaterialServiceItem"),
            @NamedQuery(name = "com.allone.projectmanager.entities.RequestQuotationItem.findByRequestQuotation", query = "SELECT rqi FROM RequestQuotationItem rqi WHERE rqi.requestQuotation = :requestQuotation")})
public class RequestQuotationItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_quotation")
    @NotNull
    private Long requestQuotation;

    @Column(name = "bill_material_service_item")
    @NotNull
    private Long billMaterialServiceItem;

    @Column(name = "unit_price")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for unitPrice")
    private BigDecimal unitPrice;

    @Column(name = "discount")
    @Digits(integer = 2, fraction = 1, message = "Validation digits failed for discount")
    private BigDecimal discount;

    @Column(name = "availability")
    private Integer availability;

    @Column(name = "total")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for total")
    private BigDecimal total;

    public RequestQuotationItem() {
    }

    private RequestQuotationItem(Builder builder) {
        requestQuotation = builder.requestQuotation;
        billMaterialServiceItem = builder.billMaterialServiceItem;
        unitPrice = builder.unitPrice;
        availability = builder.availability;
        discount = builder.discount;
        availability = builder.availability;
        total = builder.total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestQuotation() {
        return requestQuotation;
    }

    public void setRequestQuotation(Long requestQuotation) {
        this.requestQuotation = requestQuotation;
    }

    public Long getBillMaterialServiceItem() {
        return billMaterialServiceItem;
    }

    public void setBillMaterialServiceItem(Long billMaterialServiceItem) {
        this.billMaterialServiceItem = billMaterialServiceItem;
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

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
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
        if ((this.billMaterialServiceItem == null && other.billMaterialServiceItem != null)
                || (this.billMaterialServiceItem != null && !this.billMaterialServiceItem.equals(other.billMaterialServiceItem))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.ProjectRequestQuotationItem[ id=" + id + " ]";
    }

    public static class Builder {

        private Long requestQuotation;

        private Long billMaterialServiceItem;

        private BigDecimal unitPrice;

        private BigDecimal discount;

        private Integer availability;

        private BigDecimal total;

        public Builder setRequestQuotation(Long requestQuotation) {
            this.requestQuotation = requestQuotation;

            return this;
        }

        public Builder setBillMaterialServiceItem(Long billMaterialServiceItem) {
            this.billMaterialServiceItem = billMaterialServiceItem;

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

        public Builder setAvailability(Integer availability) {
            this.availability = availability;

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
