/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author administrator
 */
@Entity
@Table(name = "service_collab")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServiceCollab.findAll", query = "SELECT s FROM ServiceCollab s"),
    @NamedQuery(name = "ServiceCollab.findById", query = "SELECT s FROM ServiceCollab s WHERE s.id = :id"),
    @NamedQuery(name = "ServiceCollab.findByCollabId", query = "SELECT s FROM ServiceCollab s WHERE s.collabId = :collabId"),
    @NamedQuery(name = "ServiceCollab.findByStart", query = "SELECT s FROM ServiceCollab s WHERE s.start = :start"),
    @NamedQuery(name = "ServiceCollab.findByEnd", query = "SELECT s FROM ServiceCollab s WHERE s.end = :end"),
    @NamedQuery(name = "ServiceCollab.findByTravelDuration", query = "SELECT s FROM ServiceCollab s WHERE s.travelDuration = :travelDuration"),
    @NamedQuery(name = "ServiceCollab.findByTravelCost", query = "SELECT s FROM ServiceCollab s WHERE s.travelCost = :travelCost"),
    @NamedQuery(name = "ServiceCollab.findByServiceDuration", query = "SELECT s FROM ServiceCollab s WHERE s.serviceDuration = :serviceDuration"),
    @NamedQuery(name = "ServiceCollab.findByServiceCostHour", query = "SELECT s FROM ServiceCollab s WHERE s.serviceCostHour = :serviceCostHour")})
public class ServiceCollab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "collab_id")
    private Long collabId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "start")
    @Temporal(TemporalType.DATE)
    private Date start;

    @Column(name = "end")
    @Temporal(TemporalType.DATE)
    private Date end;

    @Column(name = "travel_duration")
    private Integer travelDuration;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "travel_cost")
    private BigDecimal travelCost;

    @Column(name = "service_duration")
    private Integer serviceDuration;

    @Column(name = "service_cost_hour")
    private BigDecimal serviceCostHour;

    public ServiceCollab() {
    }

    private ServiceCollab(Builder builder) {
        collabId = builder.collabId;
        projectId = builder.projectId;
        start = builder.start;
        end = builder.end;
        travelDuration = builder.travelDuration;
        travelCost = builder.travelCost;
        serviceDuration = builder.serviceDuration;
        serviceCostHour = builder.serviceCostHour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollabId() {
        return collabId;
    }

    public void setCollabId(Long collabId) {
        this.collabId = collabId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getTravelDuration() {
        return travelDuration;
    }

    public void setTravelDuration(Integer travelDuration) {
        this.travelDuration = travelDuration;
    }

    public BigDecimal getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(BigDecimal travelCost) {
        this.travelCost = travelCost;
    }

    public Integer getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(Integer serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public BigDecimal getServiceCostHour() {
        return serviceCostHour;
    }

    public void setServiceCostHour(BigDecimal serviceCostHour) {
        this.serviceCostHour = serviceCostHour;
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
        if (!(object instanceof ServiceCollab)) {
            return false;
        }
        ServiceCollab other = (ServiceCollab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.ServiceCollab[ id=" + id + " ]";
    }

    public static class Builder {

        private Long collabId;

        private Long projectId;

        private Date start;

        private Date end;

        private Integer travelDuration;

        private BigDecimal travelCost;

        private Integer serviceDuration;

        private BigDecimal serviceCostHour;
        
        public Builder setCollabId(Long collabId) {
            this.collabId = collabId;
            
            return this;
        }
        
        public Builder setProjectId(Long projectId) {
            this.projectId = projectId;
            
            return this;
        }
        
        public Builder setStart(Date start) {
            this.start = start;
            
            return this;
        }
        
        public Builder setEnd(Date end) {
            this.end = end;
            
            return this;
        }
        
        public Builder setTravelDuration(Integer travelDuration) {
            this.travelDuration = travelDuration;
            
            return this;
        }
        
        public Builder setTravelCost(BigDecimal travelCost) {
            this.travelCost = travelCost;
            
            return this;
        }
        
        public Builder setServiceDuration(Integer serviceDuration) {
            this.serviceDuration = serviceDuration;
            
            return this;
        }
        
        public Builder setServiceCostHour(BigDecimal serviceCostHour) {
            this.serviceCostHour = serviceCostHour;
            
            return this;
        }
        
        public ServiceCollab build() {
            return new ServiceCollab(this);
        }
    }
}
