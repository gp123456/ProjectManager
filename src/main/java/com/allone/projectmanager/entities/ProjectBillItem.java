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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Long id;

    @Basic(optional = false)
    @Column(name = "available")
    @NotNull
    private Integer available;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Basic(optional = false)
    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @Basic(optional = false)
    @Column(name = "cost")
    @NotNull
    private BigDecimal cost;

    @Basic(optional = false)
    @Column(name = "total_cost")
    @NotNull
    private BigDecimal totalCost;

    @Basic(optional = false)
    @Column(name = "percentage")
    @NotNull
    private BigDecimal percentage;

    @Basic(optional = false)
    @Column(name = "discount")
    @NotNull
    private BigDecimal discount;

    @Basic(optional = false)
    @Column(name = "sale_price")
    @NotNull
    private BigDecimal salePrice;

    @Basic(optional = false)
    @Column(name = "total_sale_price")
    @NotNull
    private BigDecimal totalSalePrice;

    @Basic(optional = false)
    @Column(name = "total_net_price")
    @NotNull
    private BigDecimal totalNetPrice;

    @Basic(optional = false)
    @Column(name = "project_bill")
    @NotNull
    private Long projectBill;

    @Basic(optional = false)
    @Column(name = "item")
    @NotNull
    private Long item;

    @Basic(optional = false)
    @Column(name = "currency")
    @NotNull
    private String currency;
    
    @Transient
    private String classRefresh;
    
    @Transient
    private String classSave;
    
    @Transient
    private String itemImno;

    private ProjectBillItem(Builder builder) {
        available = builder.available;
        price = builder.price;
        quantity = builder.quantity;
        cost = builder.cost;
        totalCost = builder.totalCost;
        percentage = builder.percentage;
        discount = builder.discount;
        salePrice = builder.salePrice;
        totalSalePrice = builder.totalSalePrice;
        totalNetPrice = builder.totalNetPrice;
        projectBill = builder.projectBill;
        item = builder.item;
        currency = builder.currency;
        classRefresh = builder.classRefresh;
        classSave = builder.classSave;
        itemImno = builder.itemImno;
    }

    private ProjectBillItem(ProjectBillItem pbi) {
        available = pbi.available;
        price = pbi.price;
        quantity = pbi.quantity;
        cost = pbi.cost;
        totalCost = pbi.totalCost;
        percentage = pbi.percentage;
        discount = pbi.discount;
        salePrice = pbi.salePrice;
        totalSalePrice = pbi.totalSalePrice;
        totalNetPrice = pbi.totalNetPrice;
        projectBill = pbi.projectBill;
        item = pbi.item;
        currency = pbi.currency;
        itemImno = pbi.itemImno;
    }

    public ProjectBillItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getClassRefresh() {
        return classRefresh;
    }

    public void setClassRefresh(String classRefresh) {
        this.classRefresh = classRefresh;
    }

    public String getClassSave() {
        return classSave;
    }

    public void setClassSave(String classSave) {
        this.classSave = classSave;
    }

    public String getItemImno() {
        return itemImno;
    }

    public void setItemImno(String itemImno) {
        this.itemImno = itemImno;
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
        return "id=" + id + ",available=" + available + ",price=" + price + ",quantity=" + quantity + ",cost=" + cost +
                ",totalCost=" + totalCost + ",percentage=" + percentage + ",discount=" + discount + ",salePrice=" +
                salePrice + ",totalSalePrice=" + totalSalePrice + ",totalNetPrice=" + totalNetPrice + ",projectBill=" +
                projectBill + ",item=" + item + ",currency=" + currency;
    }

    public static class Builder {

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

        private Long projectBill;

        private Long item;

        private String currency;
        
        private String classRefresh;
        
        private String classSave;
    
        private String itemImno;

        public Builder setAvailable(Integer available) {
            this.available = available;

            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;

            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;

            return this;
        }

        public Builder setCost(BigDecimal cost) {
            this.cost = cost;

            return this;
        }

        public Builder setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;

            return this;
        }

        public Builder setPercentage(BigDecimal percentage) {
            this.percentage = percentage;

            return this;
        }

        public Builder setDiscount(BigDecimal discount) {
            this.discount = discount;

            return this;
        }

        public Builder setSalePrice(BigDecimal salePrice) {
            this.salePrice = salePrice;

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

        public Builder setProjectBill(Long projectBill) {
            this.projectBill = projectBill;

            return this;
        }

        public Builder setItem(Long item) {
            this.item = item;

            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }
        
        public Builder setClassRefresh(String classRefresh) {
            this.classRefresh = classRefresh;
            return this;
        }
        public Builder setClassSave(String classSave) {
            this.classSave = classSave;
            return this;
        }
        
        public Builder setItemImno(String itemImno) {
            this.itemImno = itemImno;
            return this;
        }

        public ProjectBillItem build() {
            return new ProjectBillItem(this);
        }

        public ProjectBillItem build(ProjectBillItem pbi) {
            return new ProjectBillItem(pbi);
        }
    }
}
