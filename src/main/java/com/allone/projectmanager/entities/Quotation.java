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
                    query = "SELECT q FROM Quotation q WHERE q.requestForQuotation = :id")
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

    @Column(name = "discard", columnDefinition = "Bit(1) default 'b0'")
    private Boolean discard;

    @Column(name = "customer")
    @NotNull
    private String customer;

    @Column(name = "request_for_quotation")
    @NotNull
    private Long requestForQuotation;

    @Column(name = "currency")
    @NotNull
    private Integer currency;

    @Column(name = "availability")
    private Integer availability;

    @Column(name = "delivery")
    private Integer delivery;

    @Column(name = "packing")
    private Integer packing;

    @Column(name = "payment")
    private Integer payment;

    @Column(name = "validity")
    private Integer validity;

    @Column(name = "location")
    @NotNull
    private String location;

    @Column(name = "grand_total")
    @Digits(integer = 10, fraction = 2, message = "Validation digits failed for grandTotal")
    private BigDecimal grandTotal;

    @Column(name = "note")
    private String note;

    @Column(name = "customer_note")
    private String customerNote;

    private Quotation(Builder builder) {
        name = builder.name;
        requestForQuotation = builder.requestForQuotation;
        customer = builder.customer;
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
        note = builder.note;
        customerNote = builder.customerNote;
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

    public Long getRequestForQuotation() {
        return requestForQuotation;
    }

    public void setRequestForQuotation(Long requestForQuotation) {
        this.requestForQuotation = requestForQuotation;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
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

        private Long requestForQuotation;

        private Integer currency;

        private Integer availability;

        private Integer delivery;

        private Integer packing;

        private Integer payment;

        private Integer validity;

        private String location;

        private BigDecimal grandTotal;

        private String note;

        private String customerNote;

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

        public Builder setCustomer(Boolean discard) {
            this.discard = discard;

            return this;
        }

        public Builder setRequestForQuotation(Long requestForQuotation) {
            this.requestForQuotation = requestForQuotation;

            return this;
        }

        public Builder setCurrency(Integer currency) {
            this.currency = currency;

            return this;
        }

        public Builder setAvailability(Integer availability) {
            this.availability = availability;

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

        public Builder setLocation(String location) {
            this.location = location;

            return this;
        }

        public Builder setGrandTotal(BigDecimal grandTotal) {
            this.grandTotal = grandTotal;

            return this;
        }

        public Builder setNote(String note) {
            this.note = note;

            return this;
        }

        public Builder setCustomerNote(String customerNote) {
            this.customerNote = customerNote;

            return this;
        }

        public Quotation build() {
            return new Quotation(this);
        }
    }
}
