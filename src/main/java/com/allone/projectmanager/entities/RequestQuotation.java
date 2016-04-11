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
@Table(name = "request_quotation")
@XmlRootElement
public class RequestQuotation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Column(name = "supplier")
    @NotNull
    private String supplier;

    @Column(name = "bill_material_service")
    @NotNull
    private Long billMaterialService;

    @Column(name = "delivery")
    @NotNull
    private Integer delivery;

    @Column(name = "packing")
    @NotNull
    private Integer packing;

    @Column(name = "payment")
    @NotNull
    private Integer payment;

    @Column(name = "validity")
    @NotNull
    private Integer validity;

    @Column(name = "discount")
    @NotNull
    private BigDecimal discount;

    @Column(name = "payable")
    @NotNull
    private BigDecimal payable;

    @Column(name = "charges")
    @NotNull
    private Integer charges;

    @Column(name = "total")
    @NotNull
    private BigDecimal total;

    @Column(name = "complete")
    @NotNull
    private Boolean complete;

    public RequestQuotation() {
    }

    private RequestQuotation(RequestQuotation.Builder builder) {
        supplier = builder.supplier;
        this.billMaterialService = builder.billMaterialService;
        this.delivery = builder.delivery;
        this.packing = builder.packing;
        this.payment = builder.payment;
        this.validity = builder.validity;
        this.discount = builder.discount;
        this.payable = builder.payable;
        this.charges = builder.charges;
        this.total = builder.total;
        this.complete = builder.complete;
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

    public Long getBillMaterialService() {
        return billMaterialService;
    }

    public void setBillMaterialService(Long billMaterialService) {
        this.billMaterialService = billMaterialService;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public Integer getPacking() {
        return packing;
    }

    public void setPacking(Integer packing) {
        this.packing = packing;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public Integer getCharges() {
        return charges;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.ProjectRequestQuotation[ id=" + id + " ]";
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash += (id != null ? id.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RequestQuotation)) {
            return false;
        }

        RequestQuotation other = (RequestQuotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    public class Builder {

        private String supplier;

        private Long billMaterialService;

        private Integer delivery;

        private Integer packing;

        private Integer payment;

        private Integer validity;

        private BigDecimal discount;

        private BigDecimal payable;

        private Integer charges;

        private BigDecimal total;

        private Boolean complete;

        public RequestQuotation.Builder setSupplier(String supplier) {
            this.supplier = supplier;

            return this;
        }

        public Builder setBillMaterialService(Long billMaterialService) {
            this.billMaterialService = billMaterialService;

            return this;
        }

        public Builder setDelivery(Integer delivery) {
            this.delivery = delivery;

            return this;
        }

        public Builder setPacking(Integer packing) {
            this.packing = packing;

            return this;
        }

        public Builder setPayment(Integer payment) {
            this.payment = payment;

            return this;
        }

        public Builder setValidity(Integer validity) {
            this.validity = validity;

            return this;
        }

        public Builder setDiscount(BigDecimal discount) {
            this.discount = discount;

            return this;
        }

        public Builder setPayable(BigDecimal payable) {
            this.payable = payable;

            return this;
        }

        public Builder setCharges(Integer charges) {
            this.charges = charges;

            return this;
        }

        public Builder setTotal(BigDecimal total) {
            this.total = total;

            return this;
        }

        public Builder setComplete(Boolean complete) {
            this.complete = complete;

            return this;
        }

        public RequestQuotation build() {
            return new RequestQuotation(this);
        }
    }
}
