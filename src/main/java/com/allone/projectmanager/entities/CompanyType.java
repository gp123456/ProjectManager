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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "company_type")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "CompanyType.findAll", query =
            "SELECT c FROM CompanyType c"),
    @NamedQuery(name = "CompanyType.findById", query =
            "SELECT c FROM CompanyType c WHERE c.id = :id"),
    @NamedQuery(name = "CompanyType.findByAbbrevation", query =
            "SELECT c FROM CompanyType c WHERE c.abbrevation = :abbrevation"),
    @NamedQuery(name = "CompanyType.findByDescription", query =
            "SELECT c FROM CompanyType c WHERE c.description = :description")})
public class CompanyType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "abbrevation")
    private String abbrevation;
    @Column(name = "description")
    private String description;

    public CompanyType() {
    }

    public CompanyType(Long id) {
        this.id = id;
    }

    public CompanyType(Long id, String abbrevation) {
        this.id = id;
        this.abbrevation = abbrevation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbrevation() {
        return abbrevation;
    }

    public void setAbbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof CompanyType)) {
            return false;
        }
        CompanyType other = (CompanyType) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.CompanyType[ id=" + id + " ]";
    }
    
}
