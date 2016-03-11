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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "contact")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.Contact.findAll", query = "SELECT c FROM Contact c ORDER BY c.name"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Contact.findById", query = "SELECT c FROM Contact c WHERE c.id = :id"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Contact.findByCompany", query = "SELECT c FROM Contact c WHERE c.company=:company ORDER BY c.name"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Contact.findByVessel", query = "SELECT c FROM Contact c WHERE c.vessel=:vessel ORDER BY c.name")})
public class Contact implements Serializable {

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

    @Column(name = "company")
    private String company;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "note")
    private String note;

    @Column(name = "vessel")
    private Long vessel;
    
    @Column(name = "department")
    private String department;

    private Contact(Builder builder) {
        name = builder.name;
        surname = builder.surname;
        company = builder.company;
        phone = builder.phone;
        email = builder.email;
        note = builder.note;
        vessel = builder.vessel;
        department = builder.department;
    }

    public Contact() {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Contact[ id=" + id + " ]";
    }

    public static class Builder {

        private String name;

        private String surname;

        private String company;

        private String phone;

        private String email;

        private String note;

        private Long vessel;
        
        private String department;

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;

            return this;
        }

        public Builder setCompany(String company) {
            this.company = company;

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

        public Builder setNote(String note) {
            this.note = note;

            return this;
        }

        public Builder setVessel(Long vessel) {
            this.vessel = vessel;

            return this;
        }
        
        public Builder setDepartment(String department) {
            this.department = department;

            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }
}
