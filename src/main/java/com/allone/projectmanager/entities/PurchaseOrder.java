/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "purchase_order")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "PurchaseOrder.findAll", query = "SELECT p FROM PurchaseOrder p"),
               @NamedQuery(name = "PurchaseOrder.findById", query = "SELECT p FROM PurchaseOrder p WHERE p.id = :id"),
               @NamedQuery(name = "PurchaseOrder.findByCustomerOrderNumber", query =
                                                                             "SELECT p FROM PurchaseOrder p WHERE p.customerOrderNumber = :customerOrderNumber"),
               @NamedQuery(name = "PurchaseOrder.findByTransfer", query =
                                                                  "SELECT p FROM PurchaseOrder p WHERE p.transfer = :transfer"),
               @NamedQuery(name = "PurchaseOrder.findByDeliveryDate", query =
                                                                      "SELECT p FROM PurchaseOrder p WHERE p.deliveryDate = :deliveryDate"),
               @NamedQuery(name = "PurchaseOrder.findByDeliveryPlace", query =
                                                                       "SELECT p FROM PurchaseOrder p WHERE p.deliveryPlace = :deliveryPlace"),
               @NamedQuery(name = "PurchaseOrder.findByPayment", query =
                                                                 "SELECT p FROM PurchaseOrder p WHERE p.payment = :payment"),
               @NamedQuery(name = "PurchaseOrder.findByNote", query =
                                                              "SELECT p FROM PurchaseOrder p WHERE p.note = :note")})
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "customer_order_number")
    private String customerOrderNumber;
    @Column(name = "transfer")
    private String transfer;
    @Column(name = "delivery_date")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    @Column(name = "delivery_place")
    private String deliveryPlace;
    @Column(name = "payment")
    private String payment;
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "request_quotation_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RequestQuotation requestQuotationId;
    @JoinColumn(name = "project_bill_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProjectBill projectBillId;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project projectId;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Long id) {
        this.id = id;
    }

    public PurchaseOrder(Long id, String customerOrderNumber) {
        this.id = id;
        this.customerOrderNumber = customerOrderNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    public void setCustomerOrderNumber(String customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RequestQuotation getRequestQuotationId() {
        return requestQuotationId;
    }

    public void setRequestQuotationId(RequestQuotation requestQuotationId) {
        this.requestQuotationId = requestQuotationId;
    }

    public ProjectBill getProjectBillId() {
        return projectBillId;
    }

    public void setProjectBillId(ProjectBill projectBillId) {
        this.projectBillId = projectBillId;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
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
        if (!(object instanceof PurchaseOrder)) {
            return false;
        }
        PurchaseOrder other = (PurchaseOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.PurchaseOrder[ id=" + id + " ]";
    }
    
}
