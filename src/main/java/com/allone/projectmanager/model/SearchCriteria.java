/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.model;

import java.util.List;

/**
 *
 * @author antonia
 */
public class SearchCriteria {

    private final List<SearchInfo> project;
    private final List<SearchInfo> type;
    private final List<SearchInfo> status;
    private final List<SearchInfo> vessel;
    private final List<SearchInfo> customer;
    private final List<SearchInfo> contact;
    private final List<SearchInfo> company;

    public SearchCriteria(List<SearchInfo> project,
                          List<SearchInfo> type,
                          List<SearchInfo> status,
                          List<SearchInfo> vessel,
                          List<SearchInfo> customer,
                          List<SearchInfo> contact,
                          List<SearchInfo> company) {
        this.project = project;
        this.type = type;
        this.status = status;
        this.vessel = vessel;
        this.customer = customer;
        this.contact = contact;
        this.company = company;
    }

    public List<SearchInfo> getProject() {
        return project;
    }

    public List<SearchInfo> getType() {
        return type;
    }

    public List<SearchInfo> getStatus() {
        return status;
    }

    public List<SearchInfo> getVessel() {
        return vessel;
    }

    public List<SearchInfo> getCustomer() {
        return customer;
    }

    public List<SearchInfo> getContact() {
        return contact;
    }

    public List<SearchInfo> getCompany() {
        return company;
    }
}
