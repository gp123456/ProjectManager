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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "request_quotation")
@XmlRootElement
public class ProjectRequestQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Basic(optional = false)
    @Column(name = "supplier")
    @NotNull
    private String supplier;

    @Basic(optional = false)
    @Column(name = "project_bill_item")
    private Long projectBillItem;

    public ProjectRequestQuotation() {
    }

    private ProjectRequestQuotation(Builder builder) {
        supplier = builder.supplier;
        projectBillItem = builder.projectBillItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectRequestQuotation)) {
            return false;
        }
        ProjectRequestQuotation other = (ProjectRequestQuotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.RequestQuotation[ id=" + id + " ]";
    }
    
    public class Builder {
        private String supplier;
        
        private Long projectBillItem;
        
        public Builder setSupplier(String supplier) {
            this.supplier = supplier;
            
            return this;
        }

        public Builder setProjectBillItem(Long projectBillItem) {
            this.projectBillItem = projectBillItem;
            
            return this;
        }
        
        public ProjectRequestQuotation build() {
            return new ProjectRequestQuotation(this);
        }
    }
}
