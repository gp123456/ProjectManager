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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "project_request_quotation_item")
@XmlRootElement
public class RequestQuotationItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Column(name = "project_request_quotation")
    @NotNull
    private Long projectRequestQuotation;

    @Column(name = "project_bill_item")
    @NotNull
    private Long projectBillItem;

    @Column(name = "supplier")
    @NotNull
    private String supplier;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Column(name = "discount")
    @NotNull
    private BigDecimal discount;

    @Column(name = "total")
    @NotNull
    private BigDecimal total;

    @Transient
    private String classRefresh;

    @Transient
    private String classSave;

    public RequestQuotationItem() {
    }

    private RequestQuotationItem(Builder builder) {
        projectRequestQuotation = builder.projectRequestQuotation;
        projectBillItem = builder.projectBillItem;
        supplier = builder.supplier;
        quantity = builder.quantity;
        price = builder.price;
        discount = builder.discount;
        total = builder.total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectRequestQuotation() {
        return projectRequestQuotation;
    }

    public void setProjectRequestQuotation(Long projectRequestQuotation) {
        this.projectRequestQuotation = projectRequestQuotation;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Long getProjectBillItem() {
        return projectBillItem;
    }

    public void setProjectBillItem(Long projectBillItem) {
        this.projectBillItem = projectBillItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getClassRefresh() {
        return classRefresh;
    }

    public void setClassRefresh(String classRefresh) {
        this.classRefresh = classRefresh;
    }

    public String getClassSave() {
        return classSave;
    }

    public void setClassSave(String classSave) {
        this.classSave = classSave;
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

        private Long projectRequestQuotation;

        private Long projectBillItem;

        private String supplier;

        private Integer quantity;

        private BigDecimal price;

        private BigDecimal discount;

        private BigDecimal total;

        private String classRefresh;

        private String classSave;

        public Builder setProjectRequestQuotation(Long projectRequestQuotation) {
            this.projectRequestQuotation = projectRequestQuotation;

            return this;
        }

        public Builder setProjectBillItem(Long projectBillItem) {
            this.projectBillItem = projectBillItem;

            return this;
        }

        public Builder setSupplier(String supplier) {
            this.supplier = supplier;

            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;

            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;

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
        
        public Builder setClassRefresh(String classRefresh) {
            this.classRefresh = classRefresh;

            return this;
        }
        
        public Builder setClassSave(String classSave) {
            this.classSave = classSave;

            return this;
        }

        public RequestQuotationItem build() {
            return new RequestQuotationItem(this);
        }
    }
}
