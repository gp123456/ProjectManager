/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import javax.persistence.EntityManager;

/**
 *
 * @author antonia
 */
public class TypeCompanyDAO {
    EntityManager em;

    public TypeCompanyDAO(EntityManager em) {
        this.em = em;
    }
    
    
}
