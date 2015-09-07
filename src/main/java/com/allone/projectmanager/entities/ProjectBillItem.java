/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "project_bill_item")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "ProjectBillItem.findAll", query = "SELECT p FROM ProjectBillItem p"),
               @NamedQuery(name = "ProjectBillItem.findById", query = "SELECT p FROM ProjectBillItem p WHERE p.id = :id"),
               @NamedQuery(name = "ProjectBillItem.findByAvailable",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.available = :available"),
               @NamedQuery(name = "ProjectBillItem.findByPrice",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.price = :price"),
               @NamedQuery(name = "ProjectBillItem.findByQuantity",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.quantity = :quantity"),
               @NamedQuery(name = "ProjectBillItem.findByCost",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.cost = :cost"),
               @NamedQuery(name = "ProjectBillItem.findByTotalCost",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.totalCost = :totalCost"),
               @NamedQuery(name = "ProjectBillItem.findByPercentage",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.percentage = :percentage"),
               @NamedQuery(name = "ProjectBillItem.findByDiscount",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.discount = :discount"),
               @NamedQuery(name = "ProjectBillItem.findBySalePrice",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.salePrice = :salePrice"),
               @NamedQuery(name = "ProjectBillItem.findByTotalSalePrice",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.totalSalePrice = :totalSalePrice"),
               @NamedQuery(name = "ProjectBillItem.findByTotalNetPrice",
                           query = "SELECT p FROM ProjectBillItem p WHERE p.totalNetPrice = :totalNetPrice")})
public class ProjectBillItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "available")
    private Integer available;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "price")
    private BigDecimal price;

    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;

    @Basic(optional = false)
    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "percentage")
    private BigDecimal percentage;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "total_sale_price")
    private BigDecimal totalSalePrice;

    @Column(name = "total_net_price")
    private BigDecimal totalNetPrice;

    @JoinColumn(nullable = false, name = "project_bill", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private ProjectBill projectBill;

    @JoinColumn(nullable = false, name = "item", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Item item;
    
    @Transient
    private Builder builder;
    
    private ProjectBillItem(Builder builder) {
        available = builder.getAvailable();
        price = builder.getPrice();
        quantity = builder.getQuantity();
        cost = builder.getCost();
        totalCost = builder.getTotalCost();
        percentage = builder.getPercentage();
        discount = builder.getDiscount();
        salePrice = builder.getSalePrice();
        totalSalePrice = builder.getTotalSalePrice();
        totalNetPrice = builder.getTotalNetPrice();
        projectBill = builder.getProjectBill();
        item = builder.getItem();
    }
    
    public ProjectBillItem() {
        builder = new Builder();
    }

    public Builder getBuilder() {
        return builder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
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

    public ProjectBill getProjectBill() {
        return projectBill;
    }

    public void setProjectBill(ProjectBill projectBill) {
        this.projectBill = projectBill;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
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
        if (!(object instanceof ProjectBillItem)) {
            return false;
        }
        ProjectBillItem other = (ProjectBillItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.ProjectBillItem[ id=" + id + " ]";
    }

    public class Builder {
        private Integer available;
        
        private BigDecimal price;
        
        private Integer quantity;
        
        private BigDecimal cost;
        
        private BigDecimal totalCost;
        
        private BigDecimal percentage;
        
        private BigDecimal discount;
        
        private BigDecimal salePrice;
        
        private BigDecimal totalSalePrice;
        
        private BigDecimal totalNetPrice;
        
        private ProjectBill projectBill;
        
        private Item item;

        public Builder() {
        }

        public Integer getAvailable() {
            return available;
        }

        public Builder setAvailable(Integer available) {
            this.available = available;
            
            return this;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            
            return this;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            
            return this;
        }

        public BigDecimal getCost() {
            return cost;
        }

        public Builder setCost(BigDecimal cost) {
            this.cost = cost;
            
            return this;
        }

        public BigDecimal getTotalCost() {
            return totalCost;
        }

        public Builder setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
            
            return this;
        }

        public BigDecimal getPercentage() {
            return percentage;
        }

        public Builder setPercentage(BigDecimal percentage) {
            this.percentage = percentage;
            
            return this;
        }

        public BigDecimal getDiscount() {
            return discount;
        }

        public Builder setDiscount(BigDecimal discount) {
            this.discount = discount;
            
            return this;
        }

        public BigDecimal getSalePrice() {
            return salePrice;
        }

        public Builder setSalePrice(BigDecimal salePrice) {
            this.salePrice = salePrice;
            
            return this;
        }

        public BigDecimal getTotalSalePrice() {
            return totalSalePrice;
        }

        public Builder setTotalSalePrice(BigDecimal totalSalePrice) {
            this.totalSalePrice = totalSalePrice;
            
            return this;
        }

        public BigDecimal getTotalNetPrice() {
            return totalNetPrice;
        }

        public Builder setTotalNetPrice(BigDecimal totalNetPrice) {
            this.totalNetPrice = totalNetPrice;
            
            return this;
        }

        public ProjectBill getProjectBill() {
            return projectBill;
        }

        public Builder setProjectBill(ProjectBill projectBill) {
            this.projectBill = projectBill;
            
            return this;
        }

        public Item getItem() {
            return item;
        }

        public Builder setItem(Item item) {
            this.item = item;
            
            return this;
        }
        
        public ProjectBillItem build() {
            return new ProjectBillItem(this);
        }
    }
}
