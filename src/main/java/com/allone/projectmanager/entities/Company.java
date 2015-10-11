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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "company")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.Company.findAll",
                           query = "SELECT c FROM Company c WHERE c.type = :type ORDER BY c.name"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Company.findByTypeName",
                           query = "SELECT c FROM Company c WHERE c.type = :type AND c.name = :name")})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "type")
    @NotNull
    private String type;

    @Column(name = "address")
    private String address;

    @Column(name = "post_country")
    private String postCountry;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "country")
    private String country;

    @Column(name = "telefone1")
    private String telefone1;

    @Column(name = "telefone2")
    private String telefone2;

    @Column(name = "telefone3")
    private String telefone3;

    @Column(name = "fax1")
    private String fax1;

    @Column(name = "fax2")
    private String fax2;

    @Column(name = "email1")
    private String email1;

    @Column(name = "email2")
    private String email2;

    @Column(name = "email3")
    private String email3;

    @Column(name = "vat")
    private String vat;

    @Column(name = "doy")
    private String doy;

    @Column(name = "note")
    private String note;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
//    private List<Contact> listContact;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
//    private List<Item> listItem;
//    
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
//    private List<ProjectDetail> listProjectDetail;
    
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
//    private List<Vessel> listVessels;

    private Company(Builder builder) {
        name = builder.getName();
        referenceNumber = builder.getReferenceNumber();
        type = builder.getType();
        address = builder.getAddress();
        postCountry = builder.getPostCountry();
        postCode = builder.getPostCode();
        country = builder.getCountry();
        telefone1 = builder.getTelefone1();
        telefone2 = builder.getTelefone2();
        telefone3 = builder.getTelefone3();
        fax1 = builder.getFax1();
        fax2 = builder.getFax2();
        email1 = builder.getEmail1();
        email2 = builder.getEmail2();
        email3 = builder.getEmail3();
        vat = builder.getVat();
        doy = builder.getDoy();
        note = builder.getNote();
//        listContact = builder.getListContact();
//        listItem = builder.getListItem();
//        listProjectDetail = builder.getListProjectDetail();
//        listVessels = builder.getListVessels();
    }

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCountry() {
        return postCountry;
    }

    public void setPostCountry(String postCountry) {
        this.postCountry = postCountry;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getFax1() {
        return fax1;
    }

    public void setFax1(String fax1) {
        this.fax1 = fax1;
    }

    public String getFax2() {
        return fax2;
    }

    public void setFax2(String fax2) {
        this.fax2 = fax2;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getDoy() {
        return doy;
    }

    public void setDoy(String doy) {
        this.doy = doy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
//    public List<Item> getListItem() {
//        return listItem;
//    }

//    public void setListItem(List<Item> listItem) {
//        this.listItem = listItem;
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
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Company)) {
            return false;
        }
        Company other = (Company) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Company[ name=" + name + " ]";
    }

    public class Builder {
        private String name;
        
        private String referenceNumber;
        
        private String type;
        
        private String address;
        
        private String postCountry;
        
        private String postCode;
        
        private String country;
        
        private String telefone1;
        
        private String telefone2;
        
        private String telefone3;
        
        private String fax1;
        
        private String fax2;
        
        private String email1;
        
        private String email2;
        
        private String email3;
        
        private String vat;
        
        private String doy;
        
        private String note;
        
//        private List<Contact> listContact;
//        
//        private List<Item> listItem;
//        
//        private List<ProjectDetail> listProjectDetail;
        
//        private List<Vessel> listVessels;

        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;
            
            return this;
        }

        public String getReferenceNumber() {
            return referenceNumber;
        }

        public Builder setReferenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
            
            return this;
        }

        public String getType() {
            return type;
        }

        public Builder setType(String type) {
            this.type = type;
            
            return this;
        }

        public String getAddress() {
            return address;
        }

        public Builder setAddress(String address) {
            this.address = address;
            
            return this;
        }

        public String getPostCountry() {
            return postCountry;
        }

        public Builder setPostCountry(String postCountry) {
            this.postCountry = postCountry;
            
            return this;
        }

        public String getPostCode() {
            return postCode;
        }

        public Builder setPostCode(String postCode) {
            this.postCode = postCode;
            
            return this;
        }

        public String getCountry() {
            return country;
        }

        public Builder setCountry(String country) {
            this.country = country;
            
            return this;
        }

        public String getTelefone1() {
            return telefone1;
        }

        public Builder setTelefone1(String telefone1) {
            this.telefone1 = telefone1;
            
            return this;
        }

        public String getTelefone2() {
            return telefone2;
        }

        public Builder setTelefone2(String telefone2) {
            this.telefone2 = telefone2;
            
            return this;
        }

        public String getTelefone3() {
            return telefone3;
        }

        public Builder setTelefone3(String telefone3) {
            this.telefone3 = telefone3;
            
            return this;
        }

        public String getFax1() {
            return fax1;
        }

        public Builder setFax1(String fax1) {
            this.fax1 = fax1;
            
            return this;
        }

        public String getFax2() {
            return fax2;
        }

        public Builder setFax2(String fax2) {
            this.fax2 = fax2;
            
            return this;
        }

        public String getEmail1() {
            return email1;
        }

        public Builder setEmail1(String email1) {
            this.email1 = email1;
            
            return this;
        }

        public String getEmail2() {
            return email2;
        }

        public Builder setEmail2(String email2) {
            this.email2 = email2;
            
            return this;
        }

        public String getEmail3() {
            return email3;
        }

        public Builder setEmail3(String email3) {
            this.email3 = email3;
            
            return this;
        }

        public String getVat() {
            return vat;
        }

        public Builder setVat(String vat) {
            this.vat = vat;
            
            return this;
        }

        public String getDoy() {
            return doy;
        }

        public Builder setDoy(String doy) {
            this.doy = doy;
            
            return this;
        }

        public String getNote() {
            return note;
        }

        public Builder setNote(String note) {
            this.note = note;
            
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
//        public List<Item> getListItem() {
//            return listItem;
//        }
//
//        public Builder setListItem(List<Item> listItem) {
//            this.listItem = listItem;
//            
//            return this;
//        }

//        public List<ProjectDetail> getListProjectDetail() {
//            return listProjectDetail;
//        }
//
//        public Builder setListProjectDetail(List<ProjectDetail> listProjectDetail) {
//            this.listProjectDetail = listProjectDetail;
//            
//            return this;
//        }

//        public List<Vessel> getListVessels() {
//            return listVessels;
//        }
//
//        public Builder setListVessels(List<Vessel> listVessels) {
//            this.listVessels = listVessels;
//            
//            return this;
//        }
        
        public Company build() {
            return new Company(this);
        }
    }
}
