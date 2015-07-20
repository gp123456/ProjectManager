/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "reports")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.Reports.findAll", query = "SELECT r FROM Reports r")})
public class Reports implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "reference")
    private String reference;
    @Column(name = "status")
    private BigInteger status;
    @Column(name = "handling")
    private String handling;
    @Column(name = "engineer")
    private BigInteger engineer;
    @Column(name = "vessel")
    private BigInteger vessel;
    @Column(name = "customer")
    private BigInteger customer;
    @Column(name = "location")
    private String location;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "completion")
    private String completion;
    @Column(name = "images_count")
    private String imagesCount;
    @Column(name = "document_count")
    private String documentCount;
    @Column(name = "notes")
    private String notes;
    @Column(name = "document")
    private String document;

    public Reports() {
    }

    public Reports(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public String getHandling() {
        return handling;
    }

    public void setHandling(String handling) {
        this.handling = handling;
    }

    public BigInteger getEngineer() {
        return engineer;
    }

    public void setEngineer(BigInteger engineer) {
        this.engineer = engineer;
    }

    public BigInteger getVessel() {
        return vessel;
    }

    public void setVessel(BigInteger vessel) {
        this.vessel = vessel;
    }

    public BigInteger getCustomer() {
        return customer;
    }

    public void setCustomer(BigInteger customer) {
        this.customer = customer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCompletion() {
        return completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(String imagesCount) {
        this.imagesCount = imagesCount;
    }

    public String getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(String documentCount) {
        this.documentCount = documentCount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
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
        if (!(object instanceof Reports)) {
            return false;
        }
        Reports other = (Reports) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Reports[ id=" + id + " ]";
    }
    
}
