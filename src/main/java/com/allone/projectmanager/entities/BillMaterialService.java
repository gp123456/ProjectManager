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
@Table(name = "bill_material_service")
@XmlRootElement
@NamedQueries(
        {@NamedQuery(name = "com.allone.projectmanager.entities.BillMaterialService.findByProject", query = "SELECT p FROM BillMaterialService p WHERE p.project = :project"),
         @NamedQuery(name = "com.allone.projectmanager.entities.BillMaterialService.findById", query = "SELECT p FROM BillMaterialService p WHERE p.id = :id")})
public class BillMaterialService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "bill_material_service")
    @NotNull
    private Long project;

    @Column(name = "note")
    private String note;
    
    @Basic(optional = false)
    @Column(name = "complete", columnDefinition="Bit(1) default 'b0'")
    private Boolean complete;

    @Transient
    private String classSave;

    private BillMaterialService(Builder builder) {
        name = builder.name;
        project = builder.project;
        note = builder.note;
        complete = builder.complete;
        classSave = builder.classSave;
    }

    private BillMaterialService(BillMaterialService builder) {
        id = builder.id;
        name = builder.name;
        project = builder.project;
        note = builder.note;
        complete = builder.complete;
        classSave = builder.classSave;
    }

    public BillMaterialService() {
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

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClassSave() {
        return classSave;
    }

    public void setClassSave(String classSave) {
        this.classSave = classSave;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
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
        if (!(object instanceof BillMaterialService)) {
            return false;
        }
        
        BillMaterialService other = (BillMaterialService) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.BillMaterialService[ id=" + id + " ]";
    }

    public static class Builder {

        private String name;

        private Long project;

        private String note;

        private Boolean complete;
     
        private String classSave;

        public Builder setName(String name) {
            this.name = name;

            return this;
        }
        
        public Builder setProject(Long project) {
            this.project = project;

            return this;
        }

        public Builder setNote(String note) {
            this.note = note;

            return this;
        }

        public Builder setComplete(Boolean complete) {
            this.complete = complete;

            return this;
        }
        
        public Builder setClassSave(String classSave) {
            this.classSave = classSave;

            return this;
        }

        public BillMaterialService build() {
            return new BillMaterialService(this);
        }

        public BillMaterialService build(BillMaterialService pb) {
            return new BillMaterialService(pb);
        }
    }
}
