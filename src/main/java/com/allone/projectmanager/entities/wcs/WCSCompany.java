/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities.wcs;

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
 * @author gpap
 */
@Entity
@Table(name = "COMPANY")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.wcs.WCSCompany.findAllByType",
                           query = "SELECT c FROM WCSCompany c WHERE c.typeDescription=:type ORDER BY c.name ASC")})
public class WCSCompany implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CONAME")
    @NotNull
    private String name;
    
    @Column(name = "COREFNUM")
    private String reference;
    
    @Column(name = "COTYPE")
    private Integer type;
    
    @Column(name = "COADDRESS")
    private String address;
    
    @Column(name = "COPOSTCOUNTRY")
    private String postCountry;
    
    @Column(name = "COPOSTCODE")
    private String postCode;
    
    @Column(name = "COCOUNTRY")
    private String country;
    
    @Column(name = "COTEL1")
    private String telephone1;
    
    @Column(name = "COTEL2")
    private String telephone2;
    
    @Column(name = "COTEL3")
    private String telephone3;
    
    @Column(name = "COFAX1")
    private String fax1;
    
    @Column(name = "COFAX2")
    private String fax2;
    
    @Column(name = "COEMAIL1")
    private String email1;
    
    @Column(name = "COEMAIL2")
    private String email2;
    
    @Column(name = "COEMAIL3")
    private String email3;
    
    @Column(name = "COCONTID")
    private String contact;
    
    @Column(name = "COVAT")
    private String vat;
    
    @Column(name = "CODOY")
    private String doy;
    
    @Column(name = "CONOTES")
    private String note;
    
    @Column(name = "COTYPEDESCRIPTION")
    private String typeDescription;
    
    @Column(name = "COCONTDESCR")
    private String contactDescription;

    public WCSCompany() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
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

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getTelephone3() {
        return telephone3;
    }

    public void setTelephone3(String telephone3) {
        this.telephone3 = telephone3;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getContactDescription() {
        return contactDescription;
    }

    public void setContactDescription(String contactDescription) {
        this.contactDescription = contactDescription;
    } 
}
