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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "item_trans")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.ItemTrans.findAll",
                           query = "SELECT i FROM ItemTrans i")})
public class ItemTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @JoinColumn(nullable = false, insertable = true, updatable = true, name = "item", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @NotNull
    private Item item;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cost")
    @NotNull
    private BigDecimal cost;

    @Column(name = "notes")
    private String notes;

    @JoinColumn(nullable = false, insertable = true, updatable = true, name = "project", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @NotNull
    private ProjectDetail project;

    @Column(name = "currency")
    @NotNull
    private Long currency;

    @Column(name = "status")
    @NotNull
    private String status;

    @Column(name = "offer_value")
    @NotNull
    private BigDecimal offerValue;

    private ItemTrans(Builder builder) {
        item = builder.getItem();
        date = builder.getDate();
        quantity = builder.getQuantity();
        cost = builder.getCost();
        notes = builder.getNotes();
        project = builder.getProject();
        currency = builder.getCurrency();
        status = builder.getStatus();
        offerValue = builder.getOfferValue();
    }

    public ItemTrans() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(BigDecimal offerValue) {
        this.offerValue = offerValue;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ProjectDetail getProject() {
        return project;
    }

    public void setProject(ProjectDetail project) {
        this.project = project;
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
        if (!(object instanceof ItemTrans)) {
            return false;
        }
        ItemTrans other = (ItemTrans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Itemtrans[ id=" + id + " ]";
    }

    public class Builder {
        private Item item;
        
        private Date date;
        
        private Integer quantity;
        
        private BigDecimal cost;
        
        private String notes;
        
        private ProjectDetail project;
        
        private Long currency;
        
        private String status;
        
        private BigDecimal offerValue;

        public Item getItem() {
            return item;
        }

        public Builder setItem(Item item) {
            this.item = item;
            
            return this;
        }

        public Date getDate() {
            return date;
        }

        public Builder setDate(Date date) {
            this.date = date;
            
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

        public String getNotes() {
            return notes;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            
            return this;
        }

        public ProjectDetail getProject() {
            return project;
        }

        public Builder setProject(ProjectDetail project) {
            this.project = project;
            
            return this;
        }

        public Long getCurrency() {
            return currency;
        }

        public Builder setCurrency(Long currency) {
            this.currency = currency;
            
            return this;
        }

        public String getStatus() {
            return status;
        }

        public Builder setStatus(String status) {
            this.status = status;
            
            return this;
        }

        public BigDecimal getOfferValue() {
            return offerValue;
        }

        public Builder setOfferValue(BigDecimal offerValue) {
            this.offerValue = offerValue;
            
            return this;
        }
        
        public ItemTrans build() {
            return new ItemTrans(this);        }
    }
}
