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
@Table(name = "quotation")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "com.allone.projectmanager.entities.Quotation.findById",
                    query = "SELECT q FROM Quotation q WHERE q.id = :id"),
            @NamedQuery(name = "com.allone.projectmanager.entities.Quotation.findByRequestForQuotation",
                    query = "SELECT q FROM Quotation q WHERE q.requestQuotation = :id")
        })
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "complete", columnDefinition = "Bit(1) default 'b0'")
    private Boolean complete;

    @Column(name = "discard", columnDefinition = "Bit(1) default 'b1'")
    private Boolean discard;

    @Column(name = "customer")
    @NotNull
    private String customer;

    @Column(name = "customer_reference")
    @NotNull
    private String customerReference;

    @Column(name = "request_quotation")
    @NotNull
    private Long requestQuotation;

    @Column(name = "currency")
    @NotNull
    private Integer currency;

    @Column(name = "availability")
    private String availability;

    @Column(name = "delivery")
    private String delivery;

    @Column(name = "packing")
    private String packing;

    @Column(name = "payment")
    private String payment;

    @Column(name = "validity")
    private String validity;

    @Column(name = "location")
    @NotNull
    private Integer location;

    @Column(name = "grand_total")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for grandTotal")
    private BigDecimal grandTotal;

    @Column(name = "welcome")
    private String welcome;

    @Column(name = "remark")
    private String remark;

    @Column(name = "note")
    private String note;

    private Quotation(Builder builder) {
        name = builder.name;
        requestQuotation = builder.requestQuotation;
        customer = builder.customer;
        customerReference = builder.customerReference;
        currency = builder.currency;
        location = builder.location;
        complete = builder.complete;
        discard = builder.discard;
        availability = builder.availability;
        delivery = builder.delivery;
        packing = builder.packing;
        payment = builder.payment;
        validity = builder.validity;
        grandTotal = builder.grandTotal;
        welcome = builder.welcome;
        remark = builder.remark;
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

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
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

    public Boolean getDiscard() {
        return discard;
    }

    public void setDiscard(Boolean discard) {
        this.discard = discard;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public Long getRequestQuotation() {
        return requestQuotation;
    }

    public void setRequestQuotation(Long requestQuotation) {
        this.requestQuotation = requestQuotation;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

        private Boolean complete;

        private Boolean discard;

        private String customer;

        private String customerReference;

        private Long requestQuotation;

        private Integer currency;

        private String availability;

        private String delivery;

        private String packing;

        private String payment;

        private String validity;

        private Integer location;

        private BigDecimal grandTotal;

        private String welcome;

        private String remark;

        private String note;

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

        public Builder setCustomer(String customer) {
            this.customer = customer;

            return this;
        }

        public Builder setCustomerReference(String customerReference) {
            this.customerReference = customerReference;

            return this;
        }

        public Builder setRequestQuotation(Long requestQuotation) {
            this.requestQuotation = requestQuotation;

            return this;
        }

        public Builder setCurrency(Integer currency) {
            this.currency = currency;

            return this;
        }

        public Builder setAvailability(String availability) {
            this.availability = availability;

            return this;
        }

        public Builder setDelivery(String delivery) {
            this.delivery = delivery;

            return this;
        }

        public Builder setPacking(String packing) {
            this.packing = packing;

            return this;
        }

        public Builder setPayment(String payment) {
            this.payment = payment;

            return this;
        }

        public Builder setValidity(String validity) {
            this.validity = validity;

            return this;
        }

        public Builder setLocation(Integer location) {
            this.location = location;

            return this;
        }

        public Builder setGrandTotal(BigDecimal grandTotal) {
            this.grandTotal = grandTotal;

            return this;
        }

        public Builder setWelcome(String welcome) {
            this.welcome = welcome;

            return this;
        }

        public Builder setRemark(String remark) {
            this.remark = remark;

            return this;
        }

        public Builder setNote(String note) {
            this.note = note;

            return this;
        }

        public Quotation build() {
            return new Quotation(this);
        }
    }
}
