/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "project_bill")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "ProjectBill.findAll", query = "SELECT p FROM ProjectBill p"),
               @NamedQuery(name = "ProjectBill.findById", query = "SELECT p FROM ProjectBill p WHERE p.id = :id"),
               @NamedQuery(name = "ProjectBill.findByTotalCost",
                           query = "SELECT p FROM ProjectBill p WHERE p.totalCost = :totalCost"),
               @NamedQuery(name = "ProjectBill.findByAverangeDiscount",
                           query = "SELECT p FROM ProjectBill p WHERE p.averangeDiscount = :averangeDiscount"),
               @NamedQuery(name = "ProjectBill.findByTotalSalePrice",
                           query = "SELECT p FROM ProjectBill p WHERE p.totalSalePrice = :totalSalePrice"),
               @NamedQuery(name = "ProjectBill.findByTotalNetPrice",
                           query = "SELECT p FROM ProjectBill p WHERE p.totalNetPrice = :totalNetPrice")})
public class ProjectBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total_cost")
    @NotNull
    private BigDecimal totalCost;

    @Basic(optional = false)
    @Column(name = "averange_discount")
    @NotNull
    private BigDecimal averangeDiscount;

    @Basic(optional = false)
    @Column(name = "total_sale_price")
    @NotNull
    private BigDecimal totalSalePrice;

    @Basic(optional = false)
    @Column(name = "total_net_price")
    @NotNull
    private BigDecimal totalNetPrice;

    @Basic(optional = false)
    @Column(name = "project")
    @NotNull
    private Long project;

    @Column(name = "express")
    private String express;

    @Column(name = "note")
    private String note;
    
    @Basic(optional = false)
    @Column(name = "currency")
    @NotNull
    private String currency;
    
    @Basic(optional = false)
    @Column(name = "location")
    private String location;

    private ProjectBill(Builder builder) {
        totalCost = builder.totalCost;
        averangeDiscount = builder.averangeDiscount;
        totalSalePrice = builder.totalSalePrice;
        totalNetPrice = builder.totalNetPrice;
        project = builder.project;
        express = builder.express;
        note = builder.note;
        currency = builder.currency;
        location = builder.location;
    }

    public ProjectBill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getAverangeDiscount() {
        return averangeDiscount;
    }

    public void setAverangeDiscount(BigDecimal averangeDiscount) {
        this.averangeDiscount = averangeDiscount;
    }

    public BigDecimal getTotalSalePrice() {
        return totalSalePrice;
    }

    public void setTotalSalePrice(BigDecimal totalSalePrice) {
        this.totalSalePrice = totalSalePrice;
    }

    public BigDecimal getTotalNetPrice() {
        return totalNetPrice;
    }

    public void setTotalNetPrice(BigDecimal totalNetPrice) {
        this.totalNetPrice = totalNetPrice;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getProject() {
        return project;
    }

    public void setProject(Long project) {
        this.project = project;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        if (!(object instanceof ProjectBill)) {
            return false;
        }
        ProjectBill other = (ProjectBill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.ProjectBill[ id=" + id + " ]";
    }

    public static class Builder {

        private BigDecimal totalCost;

        private BigDecimal averangeDiscount;

        private BigDecimal totalSalePrice;

        private BigDecimal totalNetPrice;

        private Long project;

        private String express;

        private String note;

        private String currency;
     
        private String location;

        public Builder setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;

            return this;
        }

        public Builder setAverangeDiscount(BigDecimal averangeDiscount) {
            this.averangeDiscount = averangeDiscount;

            return this;
        }

        public Builder setTotalSalePrice(BigDecimal totalSalePrice) {
            this.totalSalePrice = totalSalePrice;

            return this;
        }

        public Builder setTotalNetPrice(BigDecimal totalNetPrice) {
            this.totalNetPrice = totalNetPrice;

            return this;
        }

        public Builder setProject(Long project) {
            this.project = project;

            return this;
        }

        public Builder setExpress(String express) {
            this.express = express;

            return this;
        }

        public Builder setNote(String note) {
            this.note = note;

            return this;
        }
        
        public Builder setCurrency(String currency) {
            this.currency = currency;

            return this;
        }
        
        public Builder setLocation(String location) {
            this.location = location;

            return this;
        }

        public ProjectBill build() {
            return new ProjectBill(this);
        }
    }
}
