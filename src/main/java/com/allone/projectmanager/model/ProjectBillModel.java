/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.model;

import java.math.BigDecimal;

/**
 *
 * @author antonia
 */
public class ProjectBillModel {

    Long id;

    Integer location;

    public ProjectBillModel(Long id, Integer location) {
        this.id = id;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        ProjectBillModel pbm = (ProjectBillModel) obj;

        return (this.id.equals(pbm.id)) ? this.location.equals(pbm.location) : false;
    }
    
    public int compareTo(ProjectBillModel pbm) {
        return (this.id.equals(pbm.id)) ? this.location.compareTo(pbm.location) : this.id.compareTo(pbm.id);
    }
}
