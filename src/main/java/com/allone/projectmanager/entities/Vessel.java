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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "vessel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.Vessel.findAll",
                query = "SELECT v FROM Vessel v ORDER BY v.name"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Vessel.findById",
                query = "SELECT v FROM Vessel v WHERE v.id = :id")})
public class Vessel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "company")
    private String company;

    @NotNull
    @Column(name = "flag")
    private String flag;

    @Column(name = "email1")
    private String email1;

    @Column(name = "email3")
    private String email3;

    @Column(name = "note")
    private String note;

    @Column(name = "log")
    private String log;

    @Column(name = "email2")
    private String email2;

    @Column(name = "next_dd")
    private String nextDd;

    @Column(name = "class")
    private String class1;

    @Column(name = "document")
    private String document;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "vessel")
//    private List<Contact> listContact;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "vessel")
//    private List<ProjectDetail> listProjectDetail;
    private Vessel(Builder builder) {
        name = builder.getName();
        company = builder.getCompany();
        flag = builder.getFlag();
        email1 = builder.getEmail1();
        email2 = builder.getEmail2();
        email3 = builder.getEmail3();
        note = builder.getNote();
        log = builder.getLog();
        nextDd = builder.getNextDd();
        class1 = builder.getClass1();
        document = builder.getDocument();
//        listContact = builder.getListContact();
//        listProjectDetail = builder.getListProjectDetail();
    }

    public Vessel() {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getNextDd() {
        return nextDd;
    }

    public void setNextDd(String nextDd) {
        this.nextDd = nextDd;
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

//    @XmlTransient
//    public List<Contact> getListContact() {
//        return listContact;
//    }
//
//    public void setListContact(List<Contact> listContact) {
//        this.listContact = listContact;
//    }
//
//    @XmlTransient
//    public List<ProjectDetail> getListProjectDetail() {
//        return listProjectDetail;
//    }
//
//    public void setListProjectDetail(List<ProjectDetail> listProjectDetail) {
//        this.listProjectDetail = listProjectDetail;
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
        if (!(object instanceof Vessel)) {
            return false;
        }
        Vessel other = (Vessel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Vessel[ id=" + id + " ]";
    }

    public class Builder {

        private String name;

//        private Company company;
        private String company;

        private String flag;

        private String email1;

        private String email3;

        private String note;

        private String log;

        private String email2;

        private String nextDd;

        private String class1;

        private String document;

//        private List<Contact> listContact;
//
//        private List<ProjectDetail> listProjectDetail;
        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public String getCompany() {
            return company;
        }

        public Builder setCompany(String company) {
            this.company = company;

            return this;
        }

        public String getFlag() {
            return flag;
        }

        public Builder setFlag(String flag) {
            this.flag = flag;

            return this;
        }

        public String getEmail1() {
            return email1;
        }

        public Builder setEmail1(String email1) {
            this.email1 = email1;

            return this;
        }

        public String getEmail3() {
            return email3;
        }

        public Builder setEmail3(String email3) {
            this.email3 = email3;

            return this;
        }

        public String getNote() {
            return note;
        }

        public Builder setNote(String note) {
            this.note = note;

            return this;
        }

        public String getLog() {
            return log;
        }

        public Builder setLog(String log) {
            this.log = log;

            return this;
        }

        public String getEmail2() {
            return email2;
        }

        public Builder setEmail2(String email2) {
            this.email2 = email2;

            return this;
        }

        public String getNextDd() {
            return nextDd;
        }

        public Builder setNextDd(String nextDd) {
            this.nextDd = nextDd;

            return this;
        }

        public String getClass1() {
            return class1;
        }

        public Builder setClass1(String class1) {
            this.class1 = class1;

            return this;
        }

        public String getDocument() {
            return document;
        }

        public Builder setDocument(String document) {
            this.document = document;

            return this;
        }

//        public List<Contact> getListContact() {
//            return listContact;
//        }
//
//        public Builder setListContact(List<Contact> listContact) {
//            this.listContact = listContact;
//
//            return this;
//        }
//
//        public List<ProjectDetail> getListProjectDetail() {
//            return listProjectDetail;
//        }
//
//        public Builder setListProjectDetail(List<ProjectDetail> listProjectDetail) {
//            this.listProjectDetail = listProjectDetail;
//
//            return this;
//        }
        public Vessel build() {
            return new Vessel(this);
        }
    }
}
