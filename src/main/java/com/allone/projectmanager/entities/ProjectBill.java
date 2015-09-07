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

    @JoinColumn(nullable = false, insertable = true, updatable = true, name = "project", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private ProjectDetail project;

    @Column(name = "express")
    private String express;

    @Column(name = "note")
    private String note;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "projectBill")
    private List<ProjectBillCustomItem> listProjectBillCustomItem;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "projectBill")
    private List<ProjectBillItem> projectBillItemList;
    
    @Transient
    Builder builder;

    private ProjectBill(Builder builder) {
        totalCost = builder.getTotalCost();
        averangeDiscount = builder.getAverangeDiscount();
        totalSalePrice = builder.getTotalSalePrice();
        totalNetPrice = builder.getTotalNetPrice();
        project = builder.getProject();
        express = builder.getExpress();
        note = builder.getNote();
        listProjectBillCustomItem = builder.getListProjectBillCustomItem();
        projectBillItemList = builder.getProjectBillItemList();
    }

    public ProjectBill() {
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

    public ProjectDetail getProject() {
        return project;
    }

    public void setProject(ProjectDetail project) {
        this.project = project;
    }

    @XmlTransient
    public List<ProjectBillItem> getProjectBillItemList() {
        return projectBillItemList;
    }

    public void setProjectBillItemList(List<ProjectBillItem> projectBillItemList) {
        this.projectBillItemList = projectBillItemList;
    }

    @XmlTransient
    public List<ProjectBillCustomItem> getListProjectBillCustomItem() {
        return listProjectBillCustomItem;
    }

    public void setProjectListBillCustomItem(List<ProjectBillCustomItem> listProjectBillCustomItem) {
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

    public class Builder {

        private BigDecimal totalCost;
        
        private BigDecimal averangeDiscount;
        
        private BigDecimal totalSalePrice;
        
        private BigDecimal totalNetPrice;
        
        private ProjectDetail project;

        private String express;

        private String note;

        private List<ProjectBillCustomItem> listProjectBillCustomItem;

        private List<ProjectBillItem> projectBillItemList;

        public BigDecimal getTotalCost() {
            return totalCost;
        }

        public Builder setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
            
            return this;
        }

        public BigDecimal getAverangeDiscount() {
            return averangeDiscount;
        }

        public Builder setAverangeDiscount(BigDecimal averangeDiscount) {
            this.averangeDiscount = averangeDiscount;
            
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

        public ProjectDetail getProject() {
            return project;
        }

        public Builder setProject(ProjectDetail project) {
            this.project = project;
            
            return this;
        }

        public String getExpress() {
            return express;
        }

        public Builder setExpress(String express) {
            this.express = express;
            
            return this;
        }

        public String getNote() {
            return note;
        }

        public Builder setNote(String note) {
            this.note = note;
            
            return this;
        }

        public List<ProjectBillCustomItem> getListProjectBillCustomItem() {
            return listProjectBillCustomItem;
        }

        public Builder setListProjectBillCustomItem(List<ProjectBillCustomItem> listProjectBillCustomItem) {
            this.listProjectBillCustomItem = listProjectBillCustomItem;
            
            return this;
        }

        public List<ProjectBillItem> getProjectBillItemList() {
            return projectBillItemList;
        }

        public Builder setProjectBillItemList(List<ProjectBillItem> projectBillItemList) {
            this.projectBillItemList = projectBillItemList;
            
            return this;
        }
        
        public ProjectBill build() {
            return new ProjectBill(this);
        }
    }
}
