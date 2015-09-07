/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "collabs")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.Collabs.findByColusernameColpassword",
                           query = "SELECT c FROM Collabs c WHERE c.username = :username AND c.password = :password"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Collabs.findById",
                           query = "SELECT c FROM Collabs c WHERE c.id = :id"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Collabs.updateProjectId",
                           query = "UPDATE Collabs c SET c.projectId = c.projectId + 1 WHERE c.id = :id")})
public class Collabs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "type")
    private Long type;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "rights")
    private Long rights;

    @Column(name = "notes")
    private String notes;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "project_prefix")
    @NotNull
    private String projectPrefix;

    @Column(name = "project_id")
    @NotNull
    private Long projectId;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    private List<ProjectDetail> listProjectDetail;
    
    @Transient
    private Builder builder;

    private Collabs(Builder builder) {
        name = builder.getName();
        surname = builder.getSurname();
        type = builder.getType();
        phone = builder.getPhone();
        email = builder.getEmail();
        rights = builder.getRights();
        notes = builder.getNotes();
        username = builder.getUsername();
        password = builder.getPassword();
        projectPrefix = builder.getProjectPrefix();
        projectId = builder.getProjectId();
        listProjectDetail = builder.getListProjectDetail();
    }

    public Collabs() {
        builder = new Builder();
    }

    public Builder getBuilder() {
        return builder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRights() {
        return rights;
    }

    public void setRights(Long rights) {
        this.rights = rights;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProjectPrefix() {
        return projectPrefix;
    }

    public void setProjectPrefix(String projectPrefix) {
        this.projectPrefix = projectPrefix;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @XmlTransient
    public List<ProjectDetail> getListProjectDetail() {
        return listProjectDetail;
    }

    public void setListProjectDetail(List<ProjectDetail> listProjectDetail) {
        this.listProjectDetail = listProjectDetail;
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
        if (!(object instanceof Collabs)) {
            return false;
        }
        Collabs other = (Collabs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Collabs[ id=" + id + " ]";
    }
    
    public class Builder {
        private String name;

        private String surname;

        private Long type;

        private String phone;

        private String email;

        private Long rights;

        private String notes;

        private String username;

        private String password;

        private String projectPrefix;

        private Long projectId;
    
        private List<ProjectDetail> listProjectDetail;

        public Builder setName(String name) {
            this.name = name;
            
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            
            return this;
        }

        public Builder setType(Long type) {
            this.type = type;
            
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            
            return this;
        }

        public Builder setRights(Long rights) {
            this.rights = rights;
            
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            
            return this;
        }

        public Builder setProjectPrefix(String projectPrefix) {
            this.projectPrefix = projectPrefix;
            
            return this;
        }

        public Builder setProjectId(Long projectId) {
            this.projectId = projectId;
            
            return this;
        }

        public Builder setListProjectDetail(List<ProjectDetail> listProjectDetail) {
            this.listProjectDetail = listProjectDetail;
            
            return this;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public Long getType() {
            return type;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public Long getRights() {
            return rights;
        }

        public String getNotes() {
            return notes;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getProjectPrefix() {
            return projectPrefix;
        }

        public Long getProjectId() {
            return projectId;
        }

        public List<ProjectDetail> getListProjectDetail() {
            return listProjectDetail;
        }
        
        public Collabs build() {
            return new Collabs(this);
        }
    }
}
