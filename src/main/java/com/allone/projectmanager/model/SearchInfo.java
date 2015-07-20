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
public class SearchInfo {
    private final Long id;
    private final String name;
    private Boolean selected;
    
    public SearchInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
