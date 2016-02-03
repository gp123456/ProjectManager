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
@Table(name = "VESSEL")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.wcs.WCSVessel.findAll",
                           query = "SELECT v FROM WCSVessel v ORDER BY v.name ASC"),
               @NamedQuery(name = "com.allone.projectmanager.entities.wcs.WCSVessel.findById",
                           query = "SELECT v FROM WCSVessel v WHERE v.id = :id"),})
public class WCSVessel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "VESSID")
    @NotNull
    private String id;

    @Column(name = "VESSNAME")
    private String name;

    @Column(name = "VESSCONAME")
    private String comapnyName;

    @Column(name = "VESSFLAG")
    private String imo;

    @Column(name = "VESSEMAIL1")
    private String email1;
    @Column(name = "VESSEMAIL2")
    private String email2;

    @Column(name = "VESSEMAIL3")
    private String email3;

    @Column(name = "VESSNOTES")
    private String note;

    @Column(name = "VESLOGCOMM")
    private String logComm;

    @Column(name = "VESSNEXTDD")
    private String nextDD;

    @Column(name = "VESSCLASS")
    private Integer glass;

    @Column(name = "VESSDOCUMENT")
    private String document;

    public WCSVessel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComapnyName() {
        return comapnyName;
    }

    public void setComapnyName(String comapnyName) {
        this.comapnyName = comapnyName;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLogComm() {
        return logComm;
    }

    public void setLogComm(String logComm) {
        this.logComm = logComm;
    }

    public String getNextDD() {
        return nextDD;
    }

    public void setNextDD(String nextDD) {
        this.nextDD = nextDD;
    }

    public Integer getGlass() {
        return glass;
    }

    public void setGlass(Integer glass) {
        this.glass = glass;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
