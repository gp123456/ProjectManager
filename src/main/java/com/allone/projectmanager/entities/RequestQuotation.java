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
@Table(name = "request_quotation")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "com.allone.projectmanager.entities.RequestQuotation.findByBillMaterialService",
                    query = "SELECT rq FROM RequestQuotation rq WHERE rq.billMaterialService=:billMaterialService AND rq.complete=1 AND rq.discard=0"
                    + " ORDER BY rq.id DESC"),
            @NamedQuery(name = "com.allone.projectmanager.entities.RequestQuotation.findById",
                    query = "SELECT rq FROM RequestQuotation rq WHERE rq.id = :id")
        })
public class RequestQuotation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "complete", columnDefinition = "Bit(1) default 'b0'")
    private Boolean complete;

    @Column(name = "discard", columnDefinition = "Bit(1) default 'b0'")
    private Boolean discard;

    @Column(name = "supplier")
    @NotNull
    private String supplier;

    @Column(name = "bill_material_service")
    @NotNull
    private Long billMaterialService;

    @Column(name = "currency")
    @NotNull
    private Integer currency;

    @Column(name = "material_cost")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for materialCost")
    private BigDecimal materialCost;

    @Column(name = "grand_total")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for grandTotal")
    private BigDecimal grandTotal;

    @Column(name = "delivery_cost")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for deliveryCost")
    private BigDecimal deliveryCost;

    @Column(name = "other_expenses")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for otherExpenses")
    private BigDecimal otherExpenses;

    @Column(name = "note")
    private String note;

    @Column(name = "supplier_note")
    private String supplierNote;

    public RequestQuotation() {
    }

    private RequestQuotation(RequestQuotation.Builder builder) {
        name = builder.name;
        complete = builder.complete;
        discard = builder.discard;
        supplier = builder.supplier;
        billMaterialService = builder.billMaterialService;
        currency = builder.currency;
        materialCost = builder.materialCost;
        grandTotal = builder.grandTotal;
        deliveryCost = builder.deliveryCost;
        otherExpenses = builder.otherExpenses;
        note = builder.note;
        supplierNote = builder.supplierNote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean getDiscard() {
        return discard;
    }

    public void setDiscard(Boolean discard) {
        this.discard = discard;
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

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSupplierNote() {
        return supplierNote;
    }

    public void setSupplierNote(String supplierNote) {
        this.supplierNote = supplierNote;
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
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    public static class Builder {

        private String name;

        private Boolean complete;

        private Boolean discard;

        private String supplier;

        private Long billMaterialService;

        private Integer currency;

        private BigDecimal materialCost;

        private BigDecimal grandTotal;

        private BigDecimal deliveryCost;

        private BigDecimal otherExpenses;

        private String note;

        private String supplierNote;

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public Builder setComplete(Boolean complete) {
            this.complete = complete;

            return this;
        }

        public Builder setDiscard(Boolean discard) {
            this.discard = discard;

            return this;
        }

        public RequestQuotation.Builder setSupplier(String supplier) {
            this.supplier = supplier;

            return this;
        }

        public Builder setBillMaterialService(Long billMaterialService) {
            this.billMaterialService = billMaterialService;

            return this;
        }

        public Builder setMaterialCost(BigDecimal materialCost) {
            this.materialCost = materialCost;

            return this;
        }

        public Builder setCurrency(Integer currency) {
            this.currency = currency;

            return this;
        }

        public Builder setGrandTotal(BigDecimal grandTotal) {
            this.grandTotal = grandTotal;

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

        public Builder setNote(String note) {
            this.note = note;

            return this;
        }

        public Builder setSupplierNote(String supplierNote) {
            this.supplierNote = supplierNote;

            return this;
        }

        public RequestQuotation build() {
            return new RequestQuotation(this);
        }
    }
}
