/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Salestrans;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author antonia
 */
public class SaleTransDAO extends Salestrans {
    private EntityManagerFactory emf;
    
    public SaleTransDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
