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
@Table(name = "project_bill_custom_item")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "ProjectBillCustomItem.findAll", query = "SELECT p FROM ProjectBillCustomItem p"),
               @NamedQuery(name = "ProjectBillCustomItem.findById",
                           query = "SELECT p FROM ProjectBillCustomItem p WHERE p.id = :id"),
               @NamedQuery(name = "ProjectBillCustomItem.findByAvailable",
                           query = "SELECT p FROM ProjectBillCustomItem p WHERE p.available = :available"),
               @NamedQuery(name = "ProjectBillCustomItem.findByQuantity",
                           query = "SELECT p FROM ProjectBillCustomItem p WHERE p.quantity = :quantity"),
               @NamedQuery(name = "ProjectBillCustomItem.findByPrice",
                           query = "SELECT p FROM ProjectBillCustomItem p WHERE p.price = :price"),
               @NamedQuery(name = "ProjectBillCustomItem.findByCost",
                           query = "SELECT p FROM ProjectBillCustomItem p WHERE p.cost = :cost")})
public class ProjectBillCustomItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Basic(optional = false)
    @Column(name = "available")
    @NotNull
    private Integer available;

    @Basic(optional = false)
    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Basic(optional = false)
    @Column(name = "cost")
    @NotNull
    private BigDecimal cost;

    @Basic(optional = false)
    @Column(name = "project_bill")
    @NotNull
    private Long projectBill;
    
    @Basic(optional = false)
    @Column(name = "item")
    @NotNull
    private Long item;

    private ProjectBillCustomItem(Builder builder) {
        available = builder.available;
        quantity = builder.quantity;
        price = builder.price;
        cost = builder.cost;
        projectBill = builder.projectBill;
        item = builder.item;
    }

    public ProjectBillCustomItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getProjectBill() {
        return projectBill;
    }

    public void setProjectBill(Long projectBill) {
        this.projectBill = projectBill;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
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
        if (!(object instanceof ProjectBillCustomItem)) {
            return false;
        }
        ProjectBillCustomItem other = (ProjectBillCustomItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.ProjectBillCustomItem[ id=" + id + " ]";
    }

    public class Builder {
        private Integer available;
        
        private Integer quantity;
        
        private BigDecimal price;
        
        private BigDecimal cost;
        
        private Long projectBill;
        
        private Long item;

        public Builder setAvailable(Integer available) {
            this.available = available;
            
            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            
            return this;
        }

        public Builder setCost(BigDecimal cost) {
            this.cost = cost;
            
            return this;
        }

        public Builder setProjectBill(Long projectBill) {
            this.projectBill = projectBill;
            
            return this;
        }

        public Builder setItem(Long item) {
            this.item = item;
            
            return this;
        }
        
        public ProjectBillCustomItem build() {
            return new ProjectBillCustomItem(this);
        }
    }
}
