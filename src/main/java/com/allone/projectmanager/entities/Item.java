/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "item")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.Item.findAll", query = "SELECT i FROM Item i"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Item.findById",
                           query = "SELECT i FROM Item i WHERE i.id = :id"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Item.findLastId",
                           query = "SELECT MAX(i.id) FROM Item i"),
               @NamedQuery(name = "com.allone.projectmanager.entities.Item.findByImno",
                           query = "SELECT i FROM Item i WHERE i.imno = :imno")})
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private Long location;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Column(name = "currency")
    @NotNull
    private Long currency;

    @Column(name = "note")
    private String note;

    @JoinColumn(nullable = false, insertable = true, updatable = true, name = "company", referencedColumnName = "name")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @NotNull
    private Company company;

    @Column(name = "imno")
    @NotNull
    private String imno;

    @Column(name = "start_quantity")
    @NotNull
    private Integer startQuantity;

    @Column(name = "start_price")
    @NotNull
    private BigDecimal startPrice;

    @Column(name = "offer_quantity")
    @NotNull
    private Integer offerQuantity;

    @Column(name = "inventory_quantity")
    @NotNull
    private Integer inventoryQuantity;

    @Column(name = "inventory_price")
    @NotNull
    private BigDecimal inventoryPrice;

    @Column(name = "inventory_edit")
    @NotNull
    private Boolean inventoryEdit;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "item")
    private List<ProjectBillCustomItem> listProjectBillCustomItem;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "item")
    private List<ProjectBillItem> listProjectBillItem;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "item")
    private List<RequestQuotationItem> listRequestQuotationItem;
    
    @Transient
    private Builder builder;

    private Item(Builder builder) {
        id = builder.getId();
        description = builder.getDescription();
        location = builder.getLocation();
        quantity = builder.getQuantity();
        price = builder.getPrice();
        currency = builder.getCurrency();
        note = builder.getNote();
        company = builder.getCompany();
        imno = builder.getImno();
        startQuantity = builder.getStartQuantity();
        startPrice = builder.getPrice();
        offerQuantity = builder.getOfferQuantity();
        inventoryQuantity = builder.getInventoryQuantity();
        inventoryPrice = builder.getInventoryPrice();
        inventoryEdit = builder.getInventoryEdit();
        listProjectBillCustomItem = builder.getListProjectBillCustomItem();
        listProjectBillItem = builder.getListProjectBillItem();
        listRequestQuotationItem = builder.getListRequestQuotationItem();
    }

    public Item() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
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

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImno() {
        return imno;
    }

    public void setImno(String imno) {
        this.imno = imno;
    }

    public Integer getStartQuantity() {
        return startQuantity;
    }

    public void setStartQuantity(Integer startQuantity) {
        this.startQuantity = startQuantity;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public Integer getOfferQuantity() {
        return offerQuantity;
    }

    public void setOfferQuantity(Integer offerQuantity) {
        this.offerQuantity = offerQuantity;
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public BigDecimal getInventoryPrice() {
        return inventoryPrice;
    }

    public void setInventoryPrice(BigDecimal inventoryPrice) {
        this.inventoryPrice = inventoryPrice;
    }

    public Boolean getInventoryEdit() {
        return inventoryEdit;
    }

    public void setInventoryEdit(Boolean inventoryEdit) {
        this.inventoryEdit = inventoryEdit;
    }

    @XmlTransient
    public List<ProjectBillItem> getListProjectBillItem() {
        return listProjectBillItem;
    }

    public void setListProjectBillItem(List<ProjectBillItem> listProjectBillItem) {
        this.listProjectBillItem = listProjectBillItem;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
    @XmlTransient
    public List<RequestQuotationItem> getListRequestQuotationItem() {
        return listRequestQuotationItem;
    }

    public void setListRequestQuotationItem(List<RequestQuotationItem> listRequestQuotationItem) {
        this.listRequestQuotationItem = listRequestQuotationItem;
    }

    @XmlTransient
    public List<ProjectBillCustomItem> getListProjectBillCustomItem() {
        return listProjectBillCustomItem;
    }

    public void setListProjectBillCustomItem(List<ProjectBillCustomItem> listProjectBillCustomItem) {
        this.listProjectBillCustomItem = listProjectBillCustomItem;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Item[ id=" + id + " ]";
    }
    
    public class Builder {
        private Long id;
        
        private String description;
        
        private Long location;
        
        private Integer quantity;
        
        private BigDecimal price;
        
        private Long currency;
        
        private String note;
        
        private Company company;
        
        private String imno;
        
        private Integer startQuantity;
        
        private BigDecimal startPrice;
        
        private Integer offerQuantity;
        
        private Integer inventoryQuantity;
        
        private BigDecimal inventoryPrice;
        
        private Boolean inventoryEdit;
        
        private List<ProjectBillCustomItem> listProjectBillCustomItem;
        
        private List<ProjectBillItem> listProjectBillItem;
        
        private List<RequestQuotationItem> listRequestQuotationItem;

        public Long getId() {
            return id;
        }

        public Builder setId(Long id) {
            this.id = id;
            
            return this;
        }

        public String getDescription() {
            return description;
        }

        public Builder setDescription(String description) {
            this.description = description;
            
            return this;
        }

        public Long getLocation() {
            return location;
        }

        public Builder setLocation(Long location) {
            this.location = location;
            
            return this;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            
            return this;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            
            return this;
        }

        public Long getCurrency() {
            return currency;
        }

        public Builder setCurrency(Long currency) {
            this.currency = currency;
            
            return this;
        }

        public String getNote() {
            return note;
        }

        public Builder setNote(String note) {
            this.note = note;
            
            return this;
        }

        public Company getCompany() {
            return company;
        }

        public Builder setCompany(Company company) {
            this.company = company;
            
            return this;
        }

        public String getImno() {
            return imno;
        }

        public Builder setImno(String imno) {
            this.imno = imno;
            
            return this;
        }

        public Integer getStartQuantity() {
            return startQuantity;
        }

        public Builder setStartQuantity(Integer startQuantity) {
            this.startQuantity = startQuantity;
            
            return this;
        }

        public BigDecimal getStartPrice() {
            return startPrice;
        }

        public Builder setStartPrice(BigDecimal startPrice) {
            this.startPrice = startPrice;
            
            return this;
        }

        public Integer getOfferQuantity() {
            return offerQuantity;
        }

        public Builder setOfferQuantity(Integer offerQuantity) {
            this.offerQuantity = offerQuantity;
            
            return this;
        }

        public Integer getInventoryQuantity() {
            return inventoryQuantity;
        }

        public Builder setInventoryQuantity(Integer inventoryQuantity) {
            this.inventoryQuantity = inventoryQuantity;
            
            return this;
        }

        public BigDecimal getInventoryPrice() {
            return inventoryPrice;
        }

        public Builder setInventoryPrice(BigDecimal inventoryPrice) {
            this.inventoryPrice = inventoryPrice;
            
            return this;
        }

        public Boolean getInventoryEdit() {
            return inventoryEdit;
        }

        public Builder setInventoryEdit(Boolean inventoryEdit) {
            this.inventoryEdit = inventoryEdit;
            
            return this;
        }

        public List<ProjectBillCustomItem> getListProjectBillCustomItem() {
            return listProjectBillCustomItem;
        }

        public Builder setListProjectBillCustomItem(List<ProjectBillCustomItem> listProjectBillCustomItem) {
            this.listProjectBillCustomItem = listProjectBillCustomItem;
            
            return this;
        }

        public List<ProjectBillItem> getListProjectBillItem() {
            return listProjectBillItem;
        }

        public Builder setListProjectBillItem(List<ProjectBillItem> listProjectBillItem) {
            this.listProjectBillItem = listProjectBillItem;
            
            return this;
        }

        public List<RequestQuotationItem> getListRequestQuotationItem() {
            return listRequestQuotationItem;
        }

        public Builder setListRequestQuotationItem(List<RequestQuotationItem> listRequestQuotationItem) {
            this.listRequestQuotationItem = listRequestQuotationItem;
            
            return this;
        }
        
        public Item build() {
            return new Item(this);
        }
    }
}
