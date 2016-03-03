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
public class ProjectModel {

    private Long id;

    private Integer location;

    public ProjectModel(Long id, Integer location) {
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
        ProjectModel pbm = (ProjectModel) obj;
        
        if (pbm.getLocation() != null) {
            return (id.equals(pbm.getId())) ? location.equals(pbm.getLocation()) : false;
        } else {
            return (id.equals(pbm.getId()));
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
