/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Exchange;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author antonia
 */
public class ExchangeDAO extends Exchange {
    private EntityManagerFactory emf;
    
    public ExchangeDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
