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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findAll",
            query = "SELECT p FROM Project p"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.countAll",
            query = "SELECT count(p) FROM Project p"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findById",
            query = "SELECT p FROM Project p WHERE p.id = :id"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findByReference",
            query = "SELECT p FROM Project p WHERE p.reference = :reference"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findByStatus",
            query = "SELECT p FROM Project p WHERE p.status = :status"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.countByStatus",
            query = "SELECT count(p) FROM Project p WHERE p.status = :status")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reference")
    private String reference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creator")
    private Long creator;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = true)
    @Column(name = "expired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;
    @Basic(optional = false)
    @NotNull
    @Column(name = "company")
    private String company;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vessel")
    private Long vessel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "customer")
    private String customer;
    @Basic(optional = true)
    @Column(name = "contact")
    private Long contact;

    public Project() {
    }

    public Project(Long id) {
        this.id = id;
    }

    public Project(Long id, String company) {
        this.id = id;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getVessel() {
        return vessel;
    }

    public void setVessel(Long vessel) {
        this.vessel = vessel;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null &&
                !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Project[ id=" + id + " ]";
    }

}
