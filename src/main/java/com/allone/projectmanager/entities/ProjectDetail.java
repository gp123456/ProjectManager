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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "project_detail")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findAll",
                           query = "SELECT p FROM ProjectDetail p"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findById",
                           query = "SELECT p FROM ProjectDetail p WHERE p.id = :id"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByProjectId",
                           query = "SELECT p FROM ProjectDetail p WHERE p.projectId = :projectId"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByStatus",
                           query = "SELECT p FROM ProjectDetail p WHERE p.status = :status"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByType",
                           query = "SELECT p FROM ProjectDetail p WHERE p.type = :type"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByCreator",
                           query = "SELECT p FROM ProjectDetail p WHERE p.creator = :creator"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByCreated",
                           query = "SELECT p FROM ProjectDetail p WHERE p.created = :created"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByExpired",
                           query = "SELECT p FROM ProjectDetail p WHERE p.expired = :expired"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByCompany",
                           query = "SELECT p FROM ProjectDetail p WHERE p.company = :company"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByVessel",
                           query = "SELECT p FROM ProjectDetail p WHERE p.vessel = :vessel"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByCustomer",
                           query = "SELECT p FROM ProjectDetail p WHERE p.customer = :customer"),
               @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByContact",
                           query = "SELECT p FROM ProjectDetail p WHERE p.contact = :contact")})
public class ProjectDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "project_id")
    private long projectId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private long status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "type")
    private long type;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creator")
    private long creator;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "company")
    private String company;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vessel")
    private long vessel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "customer")
    private String customer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contact")
    private long contact;

    public ProjectDetail() {
    }

    public ProjectDetail(Long id) {
        this.id = id;
    }

    public ProjectDetail(Long id, long projectId, long status, long type, long creator, Date created, Date expired,
                         String company, long vessel, String customer, long contact) {
        this.id = id;
        this.projectId = projectId;
        this.status = status;
        this.type = type;
        this.creator = creator;
        this.created = created;
        this.expired = expired;
        this.company = company;
        this.vessel = vessel;
        this.customer = customer;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
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

    public long getVessel() {
        return vessel;
    }

    public void setVessel(long vessel) {
        this.vessel = vessel;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
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
        if (!(object instanceof ProjectDetail)) {
            return false;
        }
        ProjectDetail other = (ProjectDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.ProjectDetail[ id=" + id + " ]";
    }
    
}
