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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "request_quotation")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "RequestQuotation.findAll", query = "SELECT r FROM RequestQuotation r"),
               @NamedQuery(name = "RequestQuotation.findById",
                           query = "SELECT r FROM RequestQuotation r WHERE r.id = :id"),
               @NamedQuery(name = "RequestQuotation.findByTotalQuantity",
                           query = "SELECT r FROM RequestQuotation r WHERE r.totalQuantity = :totalQuantity"),
               @NamedQuery(name = "RequestQuotation.findByTotalPrice",
                           query = "SELECT r FROM RequestQuotation r WHERE r.totalPrice = :totalPrice"),
               @NamedQuery(name = "RequestQuotation.findByAverangeDiscount",
                           query = "SELECT r FROM RequestQuotation r WHERE r.averangeDiscount = :averangeDiscount"),
               @NamedQuery(name = "RequestQuotation.findByTotalCost",
                           query = "SELECT r FROM RequestQuotation r WHERE r.totalCost = :totalCost"),
               @NamedQuery(name = "RequestQuotation.findByNote",
                           query = "SELECT r FROM RequestQuotation r WHERE r.note = :note")})
public class RequestQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "total_quantity")
    private int totalQuantity;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Basic(optional = false)
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Basic(optional = false)
    @Column(name = "averange_discount")
    private BigDecimal averangeDiscount;

    @Basic(optional = false)
    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "note")
    private String note;

    @JoinColumn(nullable = false, insertable = true, updatable = true, name = "project", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private ProjectDetail project;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "requestQuotation")
    private List<RequestQuotationItem> listRequestQuotationItems;

    private RequestQuotation(Builder builder) {
        totalQuantity = builder.getTotalQuantity();
        totalPrice = builder.getTotalPrice();
        averangeDiscount = builder.getAverangeDiscount();
        totalCost = builder.getTotalCost();
        note = builder.getNote();
        project = builder.getProject();
        listRequestQuotationItems = builder.getListRequestQuotationItems();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getAverangeDiscount() {
        return averangeDiscount;
    }

    public void setAverangeDiscount(BigDecimal averangeDiscount) {
        this.averangeDiscount = averangeDiscount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ProjectDetail getProject() {
        return project;
    }

    public void setProject(ProjectDetail project) {
        this.project = project;
    }

    @XmlTransient
    public List<RequestQuotationItem> getListRequestQuotationItems() {
        return listRequestQuotationItems;
    }

    public void setListRequestQuotationItems(List<RequestQuotationItem> listRequestQuotationItems) {
        this.listRequestQuotationItems = listRequestQuotationItems;
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
        if (!(object instanceof RequestQuotation)) {
            return false;
        }
        RequestQuotation other = (RequestQuotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.RequestQuotation[ id=" + id + " ]";
    }
    
    public class Builder {
        private int totalQuantity;
        
        private BigDecimal totalPrice;
        
        private BigDecimal averangeDiscount;
        
        private BigDecimal totalCost;
        
        private String note;
        
        private ProjectDetail project;
        
        private List<RequestQuotationItem> listRequestQuotationItems;

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public Builder setTotalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
            
            return this;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public Builder setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            
            return this;
        }

        public BigDecimal getAverangeDiscount() {
            return averangeDiscount;
        }

        public Builder setAverangeDiscount(BigDecimal averangeDiscount) {
            this.averangeDiscount = averangeDiscount;
            
            return this;
        }

        public BigDecimal getTotalCost() {
            return totalCost;
        }

        public Builder setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
            
            return this;
        }

        public String getNote() {
            return note;
        }

        public Builder setNote(String note) {
            this.note = note;
            
            return this;
        }

        public ProjectDetail getProject() {
            return project;
        }

        public Builder setProject(ProjectDetail project) {
            this.project = project;
            
            return this;
        }

        public List<RequestQuotationItem> getListRequestQuotationItems() {
            return listRequestQuotationItems;
        }

        public Builder setListRequestQuotationItems(List<RequestQuotationItem> listRequestQuotationItems) {
            this.listRequestQuotationItems = listRequestQuotationItems;
            
            return this;
        }
        
        public RequestQuotation build() {
            return new RequestQuotation(this);
        }
    }
}
