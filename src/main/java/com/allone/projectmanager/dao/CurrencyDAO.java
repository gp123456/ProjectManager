/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Currency;
import javax.persistence.EntityManager;

/**
 *
 * @author antonia
 */
public class CurrencyDAO extends Currency {
    private EntityManager em;
    
    public CurrencyDAO(EntityManager em) {
        this.em = em;
    }
}
