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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.Project.findAll",
                           query = "SELECT p FROM Project p"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Project.countAll",
                           query = "SELECT count(p) FROM Project p"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Project.findById",
                           query = "SELECT p FROM Project p WHERE p.id = :id"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Project.findByStatus",
                           query = "SELECT DISTINCT p FROM Project p, ProjectDetail pd WHERE p.id = pd.project AND p.status = :status ORDER BY p.id DESC"),
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
    
    @Transient
    private Long projectDetail;

    private Project(Builder builder) {
        reference = builder.getReference();
        status = builder.getStatus();
        projectDetail = builder.getProjectDetail();
    }

    public Project() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(Long projectDetail) {
        this.projectDetail = projectDetail;
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

    public final static class Builder {
        private String reference;
        
        private String status;
        
        private Long projectDetail;
        
        public String getReference() {
            return reference;
        }

        public Builder setReference(String reference) {
            this.reference = reference;
            
            return this;
        }

        public String getStatus() {
            return status;
        }

        public Builder setStatus(String status) {
            this.status = status;
            
            return this;
        }

        public Long getProjectDetail() {
            return projectDetail;
        }

        public Builder setProjectDetail(Long projectDetail) {
            this.projectDetail = projectDetail;
            
            return this;
        }

        public Project build() {
            return new Project(this);
        }
    }
}
