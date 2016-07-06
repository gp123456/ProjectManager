/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
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
@Table(name = "request_quotation")
@XmlRootElement
@NamedQueries(
        {@NamedQuery(name = "com.allone.projectmanager.entities.RequestQuotation.findByBillMaterialService", query = "SELECT rq FROM RequestQuotation rq WHERE rq.billMaterialService = :billMaterialService"),
         @NamedQuery(name = "com.allone.projectmanager.entities.RequestQuotation.findById", query = "SELECT rq FROM RequestQuotation rq WHERE rq.id = :id")})
public class RequestQuotation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

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
    private Integer materialCost;
    
//    @Column(name = "expenses_cost")
//    private Integer expensesCost;

    @Column(name = "grand_total")
    private Integer grandTotal;
    
    @Column(name = "delivery_cost")
    private Integer deliveryCost;

    @Column(name = "other_expenses")
    private Integer otherExpenses;
    
    @Column(name = "note")
    private String note;
    
    @Column(name = "complete", columnDefinition="Bit(1) default 'b0'")
    private Boolean complete;

    public RequestQuotation() {
    }

    private RequestQuotation(RequestQuotation.Builder builder) {
        supplier = builder.supplier;
        billMaterialService = builder.billMaterialService;
        currency = builder.currency;
        materialCost = builder.materialCost;
//        expensesCost = builder.expensesCost;
        grandTotal = builder.grandTotal;
        deliveryCost = builder.deliveryCost;
        otherExpenses = builder.otherExpenses;
        note = builder.note;
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

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(Integer materialCost) {
        this.materialCost = materialCost;
    }

//    public Integer getExpensesCost() {
//        return expensesCost;
//    }
//
//    public void setExpensesCost(Integer expensesCost) {
//        this.expensesCost = expensesCost;
//    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Integer deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Integer getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(Integer otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public static class Builder {

        private String supplier;

        private Long billMaterialService;

        private Integer currency;
        
        private Integer materialCost;
        
//        private Integer expensesCost;
        
        private Integer grandTotal;
        
        private Integer deliveryCost;
        
        private Integer otherExpenses;

        private String note;

        private Boolean complete;

        public RequestQuotation.Builder setSupplier(String supplier) {
            this.supplier = supplier;

            return this;
        }

        public Builder setBillMaterialService(Long billMaterialService) {
            this.billMaterialService = billMaterialService;

            return this;
        }

        public Builder setMaterialCost(Integer materiaCost) {
            this.materialCost = materialCost;

            return this;
        }
        
        public Builder setCurrency(Integer currency) {
            this.currency = currency;

            return this;
        }
        
//        public Builder setExpensesCost(Integer expensesCost) {
//            this.expensesCost = expensesCost;
//
//            return this;
//        }
        
        public Builder setGrandTotal(Integer grandTotal) {
            this.grandTotal = grandTotal;

            return this;
        }
        
        public Builder setDeliveryCost(Integer deliveryCost) {
            this.deliveryCost = deliveryCost;

            return this;
        }

        public Builder setOtherExpenses(Integer otherExpenses) {
            this.otherExpenses = otherExpenses;

            return this;
        }

        public Builder setNote(String note) {
            this.note = note;

            return this;
        }

        public Builder setComplete(Boolean compelte) {
            this.complete = complete;

            return this;
        }

        public RequestQuotation build() {
            return new RequestQuotation(this);
        }
    }
}
