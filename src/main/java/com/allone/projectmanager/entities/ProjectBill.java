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
@Table(name = "project_bill")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "ProjectBill.findAll", query = "SELECT p FROM ProjectBill p"),
               @NamedQuery(name = "ProjectBill.findById", query = "SELECT p FROM ProjectBill p WHERE p.id = :id"),
               @NamedQuery(name = "ProjectBill.findByTotalCost", query =
                                                                 "SELECT p FROM ProjectBill p WHERE p.totalCost = :totalCost"),
               @NamedQuery(name = "ProjectBill.findByAverangeDiscount", query =
                                                                        "SELECT p FROM ProjectBill p WHERE p.averangeDiscount = :averangeDiscount"),
               @NamedQuery(name = "ProjectBill.findByTotalSalePrice", query =
                                                                      "SELECT p FROM ProjectBill p WHERE p.totalSalePrice = :totalSalePrice"),
               @NamedQuery(name = "ProjectBill.findByTotalNetPrice", query =
                                                                     "SELECT p FROM ProjectBill p WHERE p.totalNetPrice = :totalNetPrice"),
               @NamedQuery(name = "ProjectBill.findByExpress", query =
                                                               "SELECT p FROM ProjectBill p WHERE p.express = :express"),
               @NamedQuery(name = "ProjectBill.findByNotes", query =
                                                             "SELECT p FROM ProjectBill p WHERE p.notes = :notes")})
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
    private BigDecimal totalCost;
    @Basic(optional = false)
    @Column(name = "averange_discount")
    private BigDecimal averangeDiscount;
    @Basic(optional = false)
    @Column(name = "total_sale_price")
    private BigDecimal totalSalePrice;
    @Basic(optional = false)
    @Column(name = "total_net_price")
    private BigDecimal totalNetPrice;
    @Column(name = "express")
    private String express;
    @Column(name = "notes")
    private String notes;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "projectBillId")
    private List<ProjectBillItem> projectBillItemList;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "projectBillId")
    private List<ProjectBillCustomItem> projectBillCustomItemList;
    @JoinColumn(nullable = false, name = "project_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project projectId;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "projectBillId")
    private List<PurchaseOrder> purchaseOrderList;

    public ProjectBill() {
        totalCost = averangeDiscount = totalSalePrice = totalNetPrice = BigDecimal.ZERO;
        express = notes = "";
    }

    public ProjectBill(Long id) {
        this.id = id;
    }

    public ProjectBill(Long id, BigDecimal totalCost, BigDecimal averangeDiscount, BigDecimal totalSalePrice,
                       BigDecimal totalNetPrice) {
        this.id = id;
        this.totalCost = totalCost;
        this.averangeDiscount = averangeDiscount;
        this.totalSalePrice = totalSalePrice;
        this.totalNetPrice = totalNetPrice;
    }
    
    public ProjectBill(BigDecimal totalCost, BigDecimal averangeDiscount, BigDecimal totalSalePrice,
                       BigDecimal totalNetPrice) {
        this.totalCost = totalCost;
        this.averangeDiscount = averangeDiscount;
        this.totalSalePrice = totalSalePrice;
        this.totalNetPrice = totalNetPrice;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @XmlTransient
    public List<ProjectBillItem> getProjectBillItemList() {
        return projectBillItemList;
    }

    public void setProjectBillItemList(List<ProjectBillItem> projectBillItemList) {
        this.projectBillItemList = projectBillItemList;
    }

    @XmlTransient
    public List<ProjectBillCustomItem> getProjectBillCustomItemList() {
        return projectBillCustomItemList;
    }

    public void setProjectBillCustomItemList(List<ProjectBillCustomItem> projectBillCustomItemList) {
        this.projectBillCustomItemList = projectBillCustomItemList;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    @XmlTransient
    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
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
    
}
