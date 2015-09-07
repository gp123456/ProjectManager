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
 * @author antonia
 */
@Entity
@Table(name = "rights")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.Rights.findAll", query = "SELECT r FROM Rights r")})
public class Rights implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "viewing")
    private Boolean viewing;

    @Column(name = "project")
    private Boolean project;

    @Column(name = "offer")
    private Boolean offer;

    @Column(name = "technician")
    private Boolean technician;

    @Column(name = "order_supplier")
    private Boolean orderSupplier;

    @Column(name = "statistics_view")
    private Boolean statisticsView;

    @Column(name = "user_creator")
    private Boolean userCreator;

    @Column(name = "note")
    private String note;

    @Column(name = "description")
    private String description;

    public Rights() {
    }

    public Rights(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getViewing() {
        return viewing;
    }

    public void setViewing(Boolean viewing) {
        this.viewing = viewing;
    }

    public Boolean getProject() {
        return project;
    }

    public void setProject(Boolean project) {
        this.project = project;
    }

    public Boolean getOffer() {
        return offer;
    }

    public void setOffer(Boolean offer) {
        this.offer = offer;
    }

    public Boolean getTechnician() {
        return technician;
    }

    public void setTechnician(Boolean technician) {
        this.technician = technician;
    }

    public Boolean getOrderSupplier() {
        return orderSupplier;
    }

    public void setOrderSupplier(Boolean orderSupplier) {
        this.orderSupplier = orderSupplier;
    }

    public Boolean getStatisticsView() {
        return statisticsView;
    }

    public void setStatisticsView(Boolean statisticsView) {
        this.statisticsView = statisticsView;
    }

    public Boolean getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(Boolean userCreator) {
        this.userCreator = userCreator;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        if (!(object instanceof Rights)) {
            return false;
        }
        Rights other = (Rights) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Rights[ id=" + id + " ]";
    }

}
