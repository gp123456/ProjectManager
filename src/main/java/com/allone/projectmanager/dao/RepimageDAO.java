/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author antonia
 */
public class RepimageDAO {
    private EntityManagerFactory emf;
    
    public RepimageDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
