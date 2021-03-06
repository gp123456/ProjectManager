/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "repimage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.Repimage.findAll", query = "SELECT r FROM Repimage r")})
public class Repimage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "type")
    private String type;

    @Column(name = "code")
    private String code;

    @Column(name = "reference")
    private String reference;

    @Column(name = "filename")
    private String filename;

    @Column(name = "url")
    private String url;
    
    @JoinColumn(nullable = false, insertable = true,updatable = true, name = "project", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private ProjectDetail project;

    private Repimage(Builder builder) {
        type = builder.getType();
        code = builder.getCode();
        reference = builder.getReference();
        filename = builder.getFilename();
        url = builder.getUrl();
        project = builder.getProject();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProjectDetail getProject() {
        return project;
    }

    public void setProject(ProjectDetail project) {
        this.project = project;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Repimage)) {
            return false;
        }
        Repimage other = (Repimage) object;
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Repimage[ type=" + type + " ]";
    }

    public class Builder {
        private String type;
        
        private String code;
        
        private String reference;
        
        private String filename;
        
        private String url;
        
        private ProjectDetail project;

        public String getType() {
            return type;
        }

        public Builder setType(String type) {
            this.type = type;
            
            return this;
        }

        public String getCode() {
            return code;
        }

        public Builder setCode(String code) {
            this.code = code;
            
            return this;
        }

        public String getReference() {
            return reference;
        }

        public Builder setReference(String reference) {
            this.reference = reference;
            
            return this;
        }

        public String getFilename() {
            return filename;
        }

        public Builder setFilename(String filename) {
            this.filename = filename;
            
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Builder setUrl(String url) {
            this.url = url;
            
            return this;
        }

        public ProjectDetail getProject() {
            return project;
        }

        public Builder setProject(ProjectDetail project) {
            this.project = project;
            
            return this;
        }
        
        public Repimage build() {
            return new Repimage(this);
        }
    }
}
