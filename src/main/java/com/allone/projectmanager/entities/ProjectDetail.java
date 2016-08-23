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
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByCreator",
            query = "SELECT p FROM ProjectDetail p WHERE p.creator = :creator AND p.status = :status" + " ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findAll",
            query = "SELECT p FROM ProjectDetail p ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.countByProject",
            query = "SELECT count(p) FROM ProjectDetail p WHERE p.project = :project"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByProjectId",
            query = "SELECT p FROM ProjectDetail p WHERE p.project = :project ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByProjectIdType",
            query = "SELECT p FROM ProjectDetail p WHERE p.project = :project AND p.type = :type ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findById", query = "SELECT p FROM ProjectDetail p WHERE p.id = :id"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findLastByProject",
            query = "SELECT p FROM ProjectDetail p WHERE p.project = :project ORDER BY p.id DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByStatus",
            query = "SELECT p FROM ProjectDetail p WHERE p.status = :status ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByType",
            query = "SELECT p FROM ProjectDetail p WHERE p.type = :type ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.countByType",
            query = "SELECT count(p.id) FROM ProjectDetail p WHERE p.type = :type"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByCreated",
            query = "SELECT p FROM ProjectDetail p WHERE p.created = :created ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByExpired",
            query = "SELECT p FROM ProjectDetail p WHERE p.expired = :expired ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByCompany",
            query = "SELECT p FROM ProjectDetail p WHERE p.company = :company ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByVessel",
            query = "SELECT p FROM ProjectDetail p WHERE p.vessel = :vessel ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByCustomer",
            query = "SELECT p FROM ProjectDetail p WHERE p.customer = :customer ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findByContact",
            query = "SELECT p FROM ProjectDetail p WHERE p.contact = :contact ORDER BY p.created DESC"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.countAll", query = "SELECT count(p) FROM ProjectDetail p"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.countByStatus",
            query = "SELECT count(p) FROM ProjectDetail p WHERE p.status = :status"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.countOpenByType",
            query = "SELECT count(p) FROM ProjectDetail p WHERE p.type = :type AND p.status <> :status"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.countByTypeStatus",
            query = "SELECT count(p) FROM ProjectDetail p WHERE p.type = :type AND p.status = :status"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.findCreatedByProjectExceptId",
            query = "SELECT p FROM ProjectDetail p WHERE p.project = :pId AND p.id != :pdId AND p.status = :status"),
    @NamedQuery(name = "com.allone.projectmanager.entities.ProjectDetail.countByTypeCompany",
            query = "SELECT count(p) FROM ProjectDetail p WHERE p.type = :type AND p.status <> :status AND p.company = :company")})
public class ProjectDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "project")
    private Long project;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private String status;

    @Basic(optional = false)
    @NotNull
    @Column(name = "type")
    private String type;

    @Column(name = "creator")
    private Long creator;

    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Basic(optional = false)
    @Column(name = "expired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;

    @Column(name = "customer")
    private String customer;

    @Column(name = "vessel")
    private Long vessel;

    @Basic(optional = false)
    @Size(min = 1, max = 45)
    @Column(name = "company")
    private String company;

    @Basic(optional = false)
    @Column(name = "contact")
    private Long contact;

    @Basic(optional = false)
    @Column(name = "reference")
    private String reference;

    @Basic(optional = false)
    @Column(name = "vesselName")
    private String vesselName;

    private ProjectDetail(Builder builder) {
        project = builder.project;
        status = builder.status;
        type = builder.type;
        creator = builder.creator;
        created = builder.created;
        expired = builder.expired;
        customer = builder.customer;
        vessel = builder.vessel;
        company = builder.company;
        contact = builder.contact;
        reference = builder.reference;
        vesselName = builder.vesselName;
    }

    public ProjectDetail() {
    }

    public ProjectDetail(ProjectDetail pd) {
        this.company = pd.company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getVessel() {
        return vessel;
    }

    public void setVessel(Long vessel) {
        this.vessel = vessel;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

//    @XmlTransient
//    public List<ItemTrans> getListItemTrans() {
//        return listItemTrans;
//    }
//
//    public void setListItemTrans(List<ItemTrans> listItemTrans) {
//        this.listItemTrans = listItemTrans;
//    }
//
//    @XmlTransient
//    public List<ProjectBill> getListProjectBill() {
//        return listProjectBill;
//    }
//
//    public void setListProjectBill(List<ProjectBill> listProjectBill) {
//        this.listProjectBill = listProjectBill;
//    }
//
//    @XmlTransient
//    public List<PurchaseOrder> getListPurchaseOrders() {
//        return listPurchaseOrders;
//    }
//
//    public void setListPurchaseOrders(List<PurchaseOrder> listPurchaseOrders) {
//        this.listPurchaseOrders = listPurchaseOrders;
//    }
//
//    @XmlTransient
//    public List<Repimage> getListRepimages() {
//        return listRepimages;
//    }
//
//    public void setListRepimages(List<Repimage> listRepimages) {
//        this.listRepimages = listRepimages;
//    }
//
//    @XmlTransient
//    public List<RequestQuotation> getListRequestQuotations() {
//        return listRequestQuotations;
//    }
//
//    public void setListRequestQuotations(List<RequestQuotation> listRequestQuotations) {
//        this.listRequestQuotations = listRequestQuotations;
//    }
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

    public final static class Builder {

        private Long project;

        private String status;

        private String type;

        private Long creator;

        private Date created;

        private Date expired;

        private String customer;

        private Long vessel;

        private String vesselName;

        private String company;

        private Long contact;

        private String reference;

        public Builder setProject(Long project) {
            this.project = project;

            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;

            return this;
        }

        public Builder setType(String type) {
            this.type = type;

            return this;
        }

        public Builder setCreator(Long creator) {
            this.creator = creator;

            return this;
        }

        public Builder setCreated(Date created) {
            this.created = created;

            return this;
        }

        public Builder setExpired(Date expired) {
            this.expired = expired;

            return this;
        }

        public Builder setCustomer(String customer) {
            this.customer = customer;

            return this;
        }

        public Builder setVessel(Long vessel) {
            this.vessel = vessel;

            return this;
        }

        public Builder setVesselName(String vesselName) {
            this.vesselName = vesselName;

            return this;
        }

        public Builder setCompany(String company) {
            this.company = company;

            return this;
        }

        public Builder setContact(Long contact) {
            this.contact = contact;

            return this;
        }

        public Builder setReference(String reference) {
            this.reference = reference;

            return this;
        }

        public ProjectDetail build() {
            return new ProjectDetail(this);
        }
    }
}
