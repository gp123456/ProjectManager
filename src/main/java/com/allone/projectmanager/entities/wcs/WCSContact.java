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
 * @author antonia
 */
@Entity
@Table(name = "CONTACT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.wcs.WCSContact.findAll", query = "SELECT c FROM WCSContact c")
})
public class WCSContact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CONTID")
    @NotNull
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "CONTNAME")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "CONTSURNAME")
    private String surname;
    
    @Basic(optional = false)
    @Column(name = "CONTCONAME")
    private String company;
    
    @Basic(optional = false)
    @Column(name = "CONTTITLE")
    private String title;
    
    @Basic(optional = false)
    @Column(name = "CONTPHONE")
    private String phone;
    
    @Basic(optional = false)
    @Column(name = "CONTEMAIL")
    private String email;
    
    @Basic(optional = false)
    @Column(name = "CONTVESSEL")
    private String vessel;
    
    @Basic(optional = false)
    @Column(name = "CONTNOTES")
    private String note;
    
    @Basic(optional = false)
    @Column(name = "CONTVESSDESCR")
    private String vessDescription;

    public WCSContact() {
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
    
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getVessDescription() {
        return vessDescription;
    }

    public void setVessDescription(String vessDescription) {
        this.vessDescription = vessDescription;
    }
}
