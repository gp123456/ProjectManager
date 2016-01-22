/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.entities.wcs;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "PROJECT")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "com.allone.projectmanager.entities.wcs.WCSProject.findAll",
                           query = "SELECT p FROM WCSProject p ORDER BY p.code DESC"),
               @NamedQuery(name = "com.allone.projectmanager.entities.wcs.WCSProject.findByReference",
                           query = "SELECT p FROM WCSProject p WHERE p.reference = :reference")})
public class WCSProject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRCODE")
    @NotNull
    private Integer code;

    @Column(name = "PRREFERENCE")
    private String reference;

    @Column(name = "PRSTATUS")
    private Integer status;

    @Column(name = "PRTYPE")
    private Integer type;

    @Column(name = "PRREQDATE")
    private String dateRequest;

    @Column(name = "PRUSER")
    private String user;

    @Column(name = "PRLOST")
    private Integer lost;

    @Column(name = "PRCONAME")
    private String company;

    @Column(name = "PRCUSTOMER")
    private String customer;

    @Column(name = "PRCONTID")
    private String contact;

    @Column(name = "PRVESSEL")
    private String vessel;

    @Column(name = "PROFFDATE")
    private String dateOffer;

    @Column(name = "PROFFVALUE")
    private String valueOffer;

    @Column(name = "PROFFVALID")
    private String validOffer;

    @Column(name = "PROFFERCOST")
    private BigDecimal costOffer;

    @Column(name = "PROFFCURR")
    private Integer currencyOffer;

    @Column(name = "PRORDDATE")
    private String dateOrder;

    @Column(name = "PRORDREF")
    private String referenceOrder;

    @Column(name = "PRDELDATE")
    private String dateDelivery;

    @Column(name = "PRDELPLACE")
    private String placeDelivery;

    @Column(name = "PRINVDATE")
    private String dateInvoice;

    @Column(name = "PRINVREF")
    private String referenceInvoice;

    @Column(name = "PRPAYDATE")
    private String datePayment;

    @Column(name = "PRPAYVALUE")
    private String valuePayment;

    @Column(name = "PRTECHNICIAN")
    private String technician;

    @Column(name = "PRSERVICESTART")
    private String startService;

    @Column(name = "PRSERVICEEND")
    private String endService;

    @Column(name = "PRSERVICEDUR")
    private BigDecimal durationService;

    @Column(name = "PRTRAVELDUR")
    private BigDecimal durationTravel;

    @Column(name = "PRSERVICEREPORT")
    private String reportService;

    @Column(name = "PRSERVPLACE")
    private String placeService;

    @Column(name = "PRSHIPPER")
    private String shipper;

    @Column(name = "PRSHIPCOST")
    private BigDecimal costShip;

    @Column(name = "PRSHIPCURR")
    private Integer currencyShip;

    @Column(name = "PRSUPPLIER")
    private String supplier;

    @Column(name = "PRSUPINVREF")
    private String refInvoiceSupplier;

    @Column(name = "PRITEMCOST")
    private BigDecimal costItem;

    @Column(name = "PRITEMCURR")
    private Integer currencyItem;

    @Column(name = "PROTHERCOST")
    private BigDecimal costOther;

    @Column(name = "PROTHERCURR")
    private Integer currencyOther;

    @Column(name = "PRTOTALCOST")
    private BigDecimal costTotal;

    @Column(name = "PRTOTALCURR")
    private Integer currencyTotal;

    @Column(name = "PRNOTES")
    private String note;

    @Column(name = "PRTYPEDESCR")
    private String desciptionType;

    @Column(name = "PRUSERDESCR")
    private String descriptionUser;

    @Column(name = "PRTOTCURRDESCR")
    private String descrCurrTotal;

    @Column(name = "PRSTATUSDESCR")
    private String descriptionStatus;

    @Column(name = "PRVESSNAME")
    private String nameVessel;

    @Column(name = "PROFFERLOCK")
    private String lockOffer;

    @Column(name = "PROFFERREMAIN")
    private Integer remainOffer;

    @Column(name = "PRTECHNEXP")
    private BigDecimal expensesTechnician;

    @Column(name = "PRTRAVCOST")
    private BigDecimal costTravel;

    @Column(name = "PRTECHEXPCURR")
    private Integer expCurrTechnician;

    @Column(name = "PRTRAVCSTCURR")
    private Integer costCurrTravell;

    @Column(name = "PRHASITEMS")
    private String hasItems;

    @Column(name = "PRSPARE")
    private String spare;

    @Column(name = "PRCREDITCOST")
    private BigDecimal costCredit;

    @Column(name = "PRCREDITREF")
    private String referenceCredit;

    @Column(name = "PRCRDCURR")
    private Integer currencyCredit;

    @Column(name = "PRSUPINVNUM")
    private String numberInvoiceSupplier;

    @Column(name = "PRSPAREDETAILS")
    private String detailsSpare;

    @Column(name = "PRSUPPLIERSALES")
    private String salesSupplier;

    @Column(name = "PRMGPSANODESPARECOST")
    private BigDecimal costMGPSAnodeSpare;

    @Column(name = "PRMGPSANODEOTHERCOST")
    private BigDecimal costMGPSAnodeOther;

    @Column(name = "PRMGPSANODESPARECURR")
    private Integer currencyMGPSAnodeSpare;

    @Column(name = "PRMGPSANODEOTHERCURR")
    private Integer currencyMGPSAnodeOther;

    public WCSProject() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(String dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public String getDateOffer() {
        return dateOffer;
    }

    public void setDateOffer(String dateOffer) {
        this.dateOffer = dateOffer;
    }

    public String getValueOffer() {
        return valueOffer;
    }

    public void setValueOffer(String valueOffer) {
        this.valueOffer = valueOffer;
    }

    public String getValidOffer() {
        return validOffer;
    }

    public void setValidOffer(String validOffer) {
        this.validOffer = validOffer;
    }

    public BigDecimal getCostOffer() {
        return costOffer;
    }

    public void setCostOffer(BigDecimal costOffer) {
        this.costOffer = costOffer;
    }

    public Integer getCurrencyOffer() {
        return currencyOffer;
    }

    public void setCurrencyOffer(Integer currencyOffer) {
        this.currencyOffer = currencyOffer;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getReferenceOrder() {
        return referenceOrder;
    }

    public void setReferenceOrder(String referenceOrder) {
        this.referenceOrder = referenceOrder;
    }

    public String getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(String dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getPlaceDelivery() {
        return placeDelivery;
    }

    public void setPlaceDelivery(String placeDelivery) {
        this.placeDelivery = placeDelivery;
    }

    public String getDateInvoice() {
        return dateInvoice;
    }

    public void setDateInvoice(String dateInvoice) {
        this.dateInvoice = dateInvoice;
    }

    public String getReferenceInvoice() {
        return referenceInvoice;
    }

    public void setReferenceInvoice(String referenceInvoice) {
        this.referenceInvoice = referenceInvoice;
    }

    public String getDatePayment() {
        return datePayment;
    }

    public void setDatePayment(String datePayment) {
        this.datePayment = datePayment;
    }

    public String getValuePayment() {
        return valuePayment;
    }

    public void setValuePayment(String valuePayment) {
        this.valuePayment = valuePayment;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getStartService() {
        return startService;
    }

    public void setStartService(String startService) {
        this.startService = startService;
    }

    public String getEndService() {
        return endService;
    }

    public void setEndService(String endService) {
        this.endService = endService;
    }

    public BigDecimal getDurationService() {
        return durationService;
    }

    public void setDurationService(BigDecimal durationService) {
        this.durationService = durationService;
    }

    public BigDecimal getDurationTravel() {
        return durationTravel;
    }

    public void setDurationTravel(BigDecimal durationTravel) {
        this.durationTravel = durationTravel;
    }

    public String getReportService() {
        return reportService;
    }

    public void setReportService(String reportService) {
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

    public BigDecimal getCostShip() {
        return costShip;
    }

    public void setCostShip(BigDecimal costShip) {
        this.costShip = costShip;
    }

    public Integer getCurrencyShip() {
        return currencyShip;
    }

    public void setCurrencyShip(Integer currencyShip) {
        this.currencyShip = currencyShip;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRefInvoiceSupplier() {
        return refInvoiceSupplier;
    }

    public void setRefInvoiceSupplier(String refInvoiceSupplier) {
        this.refInvoiceSupplier = refInvoiceSupplier;
    }

    public BigDecimal getCostItem() {
        return costItem;
    }

    public void setCostItem(BigDecimal costItem) {
        this.costItem = costItem;
    }

    public Integer getCurrencyItem() {
        return currencyItem;
    }

    public void setCurrencyItem(Integer currencyItem) {
        this.currencyItem = currencyItem;
    }

    public BigDecimal getCostOther() {
        return costOther;
    }

    public void setCostOther(BigDecimal costOther) {
        this.costOther = costOther;
    }

    public Integer getCurrencyOther() {
        return currencyOther;
    }

    public void setCurrencyOther(Integer currencyOther) {
        this.currencyOther = currencyOther;
    }

    public BigDecimal getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(BigDecimal costTotal) {
        this.costTotal = costTotal;
    }

    public Integer getCurrencyTotal() {
        return currencyTotal;
    }

    public void setCurrencyTotal(Integer currencyTotal) {
        this.currencyTotal = currencyTotal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDesciptionType() {
        return desciptionType;
    }

    public void setDesciptionType(String desciptionType) {
        this.desciptionType = desciptionType;
    }

    public String getDescriptionUser() {
        return descriptionUser;
    }

    public void setDescriptionUser(String descriptionUser) {
        this.descriptionUser = descriptionUser;
    }

    public String getDescrCurrTotal() {
        return descrCurrTotal;
    }

    public void setDescrCurrTotal(String descrCurrTotal) {
        this.descrCurrTotal = descrCurrTotal;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public void setDescriptionStatus(String descriptionStatus) {
        this.descriptionStatus = descriptionStatus;
    }

    public String getNameVessel() {
        return nameVessel;
    }

    public void setNameVessel(String nameVessel) {
        this.nameVessel = nameVessel;
    }

    public String getLockOffer() {
        return lockOffer;
    }

    public void setLockOffer(String lockOffer) {
        this.lockOffer = lockOffer;
    }

    public Integer getRemainOffer() {
        return remainOffer;
    }

    public void setRemainOffer(Integer remainOffer) {
        this.remainOffer = remainOffer;
    }

    public BigDecimal getExpensesTechnician() {
        return expensesTechnician;
    }

    public void setExpensesTechnician(BigDecimal expensesTechnician) {
        this.expensesTechnician = expensesTechnician;
    }

    public BigDecimal getCostTravel() {
        return costTravel;
    }

    public void setCostTravel(BigDecimal costTravel) {
        this.costTravel = costTravel;
    }

    public Integer getExpCurrTechnician() {
        return expCurrTechnician;
    }

    public void setExpCurrTechnician(Integer expCurrTechnician) {
        this.expCurrTechnician = expCurrTechnician;
    }

    public Integer getCostCurrTravell() {
        return costCurrTravell;
    }

    public void setCostCurrTravell(Integer costCurrTravell) {
        this.costCurrTravell = costCurrTravell;
    }

    public String getHasItems() {
        return hasItems;
    }

    public void setHasItems(String hasItems) {
        this.hasItems = hasItems;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public BigDecimal getCostCredit() {
        return costCredit;
    }

    public void setCostCredit(BigDecimal costCredit) {
        this.costCredit = costCredit;
    }

    public String getReferenceCredit() {
        return referenceCredit;
    }

    public void setReferenceCredit(String referenceCredit) {
        this.referenceCredit = referenceCredit;
    }

    public Integer getCurrencyCredit() {
        return currencyCredit;
    }

    public void setCurrencyCredit(Integer currencyCredit) {
        this.currencyCredit = currencyCredit;
    }

    public String getNumberInvoiceSupplier() {
        return numberInvoiceSupplier;
    }

    public void setNumberInvoiceSupplier(String numberInvoiceSupplier) {
        this.numberInvoiceSupplier = numberInvoiceSupplier;
    }

    public String getDetailsSpare() {
        return detailsSpare;
    }

    public void setDetailsSpare(String detailsSpare) {
        this.detailsSpare = detailsSpare;
    }

    public String getSalesSupplier() {
        return salesSupplier;
    }

    public void setSalesSupplier(String salesSupplier) {
        this.salesSupplier = salesSupplier;
    }

    public BigDecimal getCostMGPSAnodeSpare() {
        return costMGPSAnodeSpare;
    }

    public void setCostMGPSAnodeSpare(BigDecimal costMGPSAnodeSpare) {
        this.costMGPSAnodeSpare = costMGPSAnodeSpare;
    }

    public BigDecimal getCostMGPSAnodeOther() {
        return costMGPSAnodeOther;
    }

    public void setCostMGPSAnodeOther(BigDecimal costMGPSAnodeOther) {
        this.costMGPSAnodeOther = costMGPSAnodeOther;
    }

    public Integer getCurrencyMGPSAnodeSpare() {
        return currencyMGPSAnodeSpare;
    }

    public void setCurrencyMGPSAnodeSpare(Integer currencyMGPSAnodeSpare) {
        this.currencyMGPSAnodeSpare = currencyMGPSAnodeSpare;
    }

    public Integer getCurrencyMGPSAnodeOther() {
        return currencyMGPSAnodeOther;
    }

    public void setCurrencyMGPSAnodeOther(Integer currencyMGPSAnodeOther) {
        this.currencyMGPSAnodeOther = currencyMGPSAnodeOther;
    }
}
