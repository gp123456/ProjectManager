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
@Table(name = "bill_material_service_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.BillMaterialServiceItem.findById", query = "SELECT bmsi FROM BillMaterialServiceItem bmsi WHERE bmsi.id = :id"),
    @NamedQuery(name = "com.allone.projectmanager.entities.BillMaterialServiceItem.findByBillMaterialService", query = "SELECT bmsi FROM BillMaterialServiceItem bmsi WHERE bmsi.billMaterialService = :billMaterialService")
})
public class BillMaterialServiceItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "bill_material_service")
    @NotNull
    private Long billMaterialService;
    
    @Basic(optional = false)
    @Column(name = "item")
    @NotNull
    private Long item;

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

    @Transient
    private String classSave;

    private BillMaterialServiceItem(Builder builder) {
        billMaterialService = builder.billMaterialService;
        item = builder.item;
        available = builder.available;
        price = builder.price;
        quantity = builder.quantity;
        classSave = builder.classSave;
    }

    private BillMaterialServiceItem(BillMaterialServiceItem pbi) {
        billMaterialService = pbi.billMaterialService;
        item = pbi.item;
        available = pbi.available;
        price = pbi.price;
        quantity = pbi.quantity;
    }

    public BillMaterialServiceItem() {
    }

    public Long getId() {
        return id;
    }

    public Long getBillMaterialService() {
        return billMaterialService;
    }

    public void setBillMaterialService(Long billMaterialService) {
        this.billMaterialService = billMaterialService;
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

    public String getClassSave() {
        return classSave;
    }

    public void setClassSave(String classSave) {
        this.classSave = classSave;
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
        if (!(object instanceof BillMaterialServiceItem)) {
            return false;
        }
        
        BillMaterialServiceItem other = (BillMaterialServiceItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.BillMaterialServiceItem[ id=" + id + " ]";
    }

    public static class Builder {

        private Long billMaterialService;
        
        private Long item;
        
        private Integer available;

        private BigDecimal price;

        private Integer quantity;
        
        private String classSave;
        
        public Builder setBillMaterialService(Long billMaterialService) {
            this.billMaterialService = billMaterialService;

            return this;
        }

        public Builder setItem(Long item) {
            this.item = item;

            return this;
        }

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

        public Builder setClassSave(String classSave) {
            this.classSave = classSave;
            return this;
        }

        public BillMaterialServiceItem build() {
            return new BillMaterialServiceItem(this);
        }

        public BillMaterialServiceItem build(BillMaterialServiceItem pbi) {
            return new BillMaterialServiceItem(pbi);
        }
    }
}
