/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author antonia
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findAll",
                query = "SELECT p FROM Project p"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.countAll",
                query = "SELECT count(p) FROM Project p"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findById",
                query = "SELECT p FROM Project p WHERE p.id = :id"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findByType",
                query = "SELECT p FROM Project p WHERE p.type = :type"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.countByType",
                query = "SELECT count(p) FROM Project p WHERE p.type = :type"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findByStatus",
                query = "SELECT p FROM Project p WHERE p.status = :status"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.countByStatus",
                query = "SELECT count(p) FROM Project p WHERE p.status = :status"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findByVessel",
                query = "SELECT p FROM Project p WHERE p.vessel = :vessel"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.countByVessel",
                query = "SELECT count(p) FROM Project p WHERE p.vessel = :vessel"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findByCustomer",
                query = "SELECT p FROM Project p WHERE p.customer = :customer"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.countByCustomer",
                query = "SELECT count(p) FROM Project p WHERE p.customer = :customer"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findByCompany",
                query = "SELECT p FROM Project p WHERE p.company = :company"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.countByCompany",
                query = "SELECT count(p) FROM Project p WHERE p.company = :company"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findMyProjectType",
                query = "SELECT p FROM Project p WHERE p.status = :status AND p.collab = :collab AND p.type = :type"),
    @NamedQuery(name = "com.allone.projectmanager.entities.Project.findMyProjectCompany",
                query = "SELECT p FROM Project p WHERE p.status = :status AND p.collab = :collab AND p.company = :company")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "reference")
    private String reference;
    @Column(name = "status")
    private Long status;
    @Column(name = "type")
    private Long type;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "collab")
    private Long collab;
    @Column(name = "lost")
    private String lost;
    @Column(name = "customer")
    private String customer;
    @Column(name = "offer_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date offerDate;
    @Column(name = "offer_value")
    private Integer offerValue;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "offer_cost")
    private BigDecimal offerCost;
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Column(name = "order_reference")
    private String orderReference;
    @Column(name = "delivery_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;
    @Column(name = "delivery_place")
    private String deliveryPlace;
    @Column(name = "inventory_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inventoryDate;
    @Column(name = "inventrory_reference")
    private String inventroryReference;
    @Column(name = "pay_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;
    @Column(name = "pay_value")
    private BigDecimal payValue;
    @Column(name = "technician")
    private Long technician;
    @Column(name = "technician_expenses")
    private BigDecimal technicianExpenses;
    @Column(name = "start_service")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startService;
    @Column(name = "end_service")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endService;
    @Column(name = "duration_service")
    private Integer durationService;
    @Column(name = "duration_travel")
    private Integer durationTravel;
    @Column(name = "cost_travel")
    private BigDecimal costTravel;
    @Column(name = "report_service")
    private Long reportService;
    @Column(name = "place_service")
    private String placeService;
    @Column(name = "shipper")
    private String shipper;
    @Column(name = "ship_cost")
    private BigDecimal shipCost;
    @Column(name = "supplier")
    private Long supplier;
    @Column(name = "supply_inventory_reference")
    private String supplyInventoryReference;
    @Column(name = "item_cost")
    private BigDecimal itemCost;
    @Column(name = "other_cost")
    private BigDecimal otherCost;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @Column(name = "vessel")
    private Long vessel;
    @Column(name = "spare")
    private String spare;
    @Column(name = "spare_detais")
    private String spareDetais;
    @Column(name = "mgps_anodes_spare_cost")
    private BigDecimal mgpsAnodesSpareCost;
    @Column(name = "mgps_anodes_other_cost")
    private BigDecimal mgpsAnodesOtherCost;
    @Column(name = "notes")
    private String notes;
    @Basic(optional = false)
    @Column(name = "company")
    private String company;
    @Column(name = "contact")
    private Long contact;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private List<RequestQuotation> requestQuotationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private List<ProjectBill> projectBillList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private List<PurchaseOrder> purchaseOrderList;

    public Project() {
    }

    public Project(Long id) {
        this.id = id;
    }

    public Project(Long id, String company) {
        this.id = id;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCollab() {
        return collab;
    }

    public void setCollab(Long collab) {
        this.collab = collab;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(Date offerDate) {
        this.offerDate = offerDate;
    }

    public Integer getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(Integer offerValue) {
        this.offerValue = offerValue;
    }

    public BigDecimal getOfferCost() {
        return offerCost;
    }

    public void setOfferCost(BigDecimal offerCost) {
        this.offerCost = offerCost;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public String getInventroryReference() {
        return inventroryReference;
    }

    public void setInventroryReference(String inventroryReference) {
        this.inventroryReference = inventroryReference;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getPayValue() {
        return payValue;
    }

    public void setPayValue(BigDecimal payValue) {
        this.payValue = payValue;
    }

    public Long getTechnician() {
        return technician;
    }

    public void setTechnician(Long technician) {
        this.technician = technician;
    }

    public BigDecimal getTechnicianExpenses() {
        return technicianExpenses;
    }

    public void setTechnicianExpenses(BigDecimal technicianExpenses) {
        this.technicianExpenses = technicianExpenses;
    }

    public Date getStartService() {
        return startService;
    }

    public void setStartService(Date startService) {
        this.startService = startService;
    }

    public Date getEndService() {
        return endService;
    }

    public void setEndService(Date endService) {
        this.endService = endService;
    }

    public Integer getDurationService() {
        return durationService;
    }

    public void setDurationService(Integer durationService) {
        this.durationService = durationService;
    }

    public Integer getDurationTravel() {
        return durationTravel;
    }

    public void setDurationTravel(Integer durationTravel) {
        this.durationTravel = durationTravel;
    }

    public BigDecimal getCostTravel() {
        return costTravel;
    }

    public void setCostTravel(BigDecimal costTravel) {
        this.costTravel = costTravel;
    }

    public Long getReportService() {
        return reportService;
    }

    public void setReportService(Long reportService) {
        this.reportService = reportService;
    }

    public String getPlaceService() {
        return placeService;
    }

    public void setPlaceService(String placeService) {
        this.placeService = placeService;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public BigDecimal getShipCost() {
        return shipCost;
    }

    public void setShipCost(BigDecimal shipCost) {
        this.shipCost = shipCost;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public String getSupplyInventoryReference() {
        return supplyInventoryReference;
    }

    public void setSupplyInventoryReference(String supplyInventoryReference) {
        this.supplyInventoryReference = supplyInventoryReference;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public BigDecimal getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(BigDecimal otherCost) {
        this.otherCost = otherCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getVessel() {
        return vessel;
    }

    public void setVessel(Long vessel) {
        this.vessel = vessel;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public String getSpareDetais() {
        return spareDetais;
    }

    public void setSpareDetais(String spareDetais) {
        this.spareDetais = spareDetais;
    }

    public BigDecimal getMgpsAnodesSpareCost() {
        return mgpsAnodesSpareCost;
    }

    public void setMgpsAnodesSpareCost(BigDecimal mgpsAnodesSpareCost) {
        this.mgpsAnodesSpareCost = mgpsAnodesSpareCost;
    }

    public BigDecimal getMgpsAnodesOtherCost() {
        return mgpsAnodesOtherCost;
    }

    public void setMgpsAnodesOtherCost(BigDecimal mgpsAnodesOtherCost) {
        this.mgpsAnodesOtherCost = mgpsAnodesOtherCost;
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

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    @XmlTransient
    public List<RequestQuotation> getRequestQuotationList() {
        return requestQuotationList;
    }

    public void setRequestQuotationList(List<RequestQuotation> requestQuotationList) {
        this.requestQuotationList = requestQuotationList;
    }

    @XmlTransient
    public List<ProjectBill> getProjectBillList() {
        return projectBillList;
    }

    public void setProjectBillList(List<ProjectBill> projectBillList) {
        this.projectBillList = projectBillList;
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.allone.projectmanager.entities.Project[ id=" + id + " ]";
    }
    
}
