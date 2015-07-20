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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Item.findLastId", query = "SELECT MAX(i.id) FROM Item i"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Item.findByImno", query = "SELECT i FROM Item i WHERE i.imno = :imno")})
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "location")
    private Long location;
    @Column(name = "quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "currency")
    private Long currency;
    @Column(name = "notes")
    private String notes;
    @Column(name = "company")
    private String company;
    @Column(name = "imno")
    private String imno;
    @Column(name = "start_quantity")
    private Integer startQuantity;
    @Column(name = "start_price")
    private BigDecimal startPrice;
    @Column(name = "offer_quantity")
    private Integer offerQuantity;
    @Column(name = "inventory_quantity")
    private Integer inventoryQuantity;
    @Column(name = "inventory_price")
    private BigDecimal inventoryPrice;
    @Column(name = "inventory_edit")
    private Boolean inventoryEdit;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<ProjectBillItem> projectBillItemList;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "itemId")
    private List<RequestQuotationItem> requestQuotationItemList;

    public Item() {
    }

    public Item(Long id) {
        this.id = id;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
    public List<ProjectBillItem> getProjectBillItemList() {
        return projectBillItemList;
    }

    public void setProjectBillItemList(List<ProjectBillItem> projectBillItemList) {
        this.projectBillItemList = projectBillItemList;
    }

    @XmlTransient
    public List<RequestQuotationItem> getRequestQuotationItemList() {
        return requestQuotationItemList;
    }

    public void setRequestQuotationItemList(List<RequestQuotationItem> requestQuotationItemList) {
        this.requestQuotationItemList = requestQuotationItemList;
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
    
}
