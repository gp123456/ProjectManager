/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.model;

/**
 *
 * @author antonia
 */
public class ProjectBillModel {

    private Long id;

    private Integer location;

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
        
        if (pbm.location != null) {
            return (this.id.equals(pbm.id)) ? this.location.equals(pbm.location) : false;
        } else {
            return (this.id.equals(pbm.id));
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + this.id.intValue() + this.location;

        return result;
    }
}
