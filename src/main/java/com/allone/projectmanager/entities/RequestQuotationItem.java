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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "request_quotation_item")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "RequestQuotationItem.findAll", query = "SELECT r FROM RequestQuotationItem r"),
               @NamedQuery(name = "RequestQuotationItem.findById", query =
                                                                   "SELECT r FROM RequestQuotationItem r WHERE r.id = :id"),
               @NamedQuery(name = "RequestQuotationItem.findByQuantity", query =
                                                                         "SELECT r FROM RequestQuotationItem r WHERE r.quantity = :quantity"),
               @NamedQuery(name = "RequestQuotationItem.findByPrice", query =
                                                                      "SELECT r FROM RequestQuotationItem r WHERE r.price = :price"),
               @NamedQuery(name = "RequestQuotationItem.findByDiscount", query =
                                                                         "SELECT r FROM RequestQuotationItem r WHERE r.discount = :discount"),
               @NamedQuery(name = "RequestQuotationItem.findByCost", query =
                                                                     "SELECT r FROM RequestQuotationItem r WHERE r.cost = :cost")})
public class RequestQuotationItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "price")
    private int price;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "discount")
    private BigDecimal discount;
    @Basic(optional = false)
    @Column(name = "cost")
    private BigDecimal cost;
    @JoinColumn(nullable = false, name = "request_quotation_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RequestQuotation requestQuotationId;
    @JoinColumn(nullable = false, name = "item_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Item itemId;

    public RequestQuotationItem() {
    }

    public RequestQuotationItem(Long id) {
        this.id = id;
    }

    public RequestQuotationItem(Long id, int quantity, int price, BigDecimal discount, BigDecimal cost) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public RequestQuotation getRequestQuotationId() {
        return requestQuotationId;
    }

    public void setRequestQuotationId(RequestQuotation requestQuotationId) {
        this.requestQuotationId = requestQuotationId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
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
        if (!(object instanceof RequestQuotationItem)) {
            return false;
        }
        RequestQuotationItem other = (RequestQuotationItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.RequestQuotationItem[ id=" + id + " ]";
    }
    
}
