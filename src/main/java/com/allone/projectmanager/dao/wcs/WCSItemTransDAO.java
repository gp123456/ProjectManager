/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao.wcs;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Admin
 */
public class WCSItemTransDAO {
    private EntityManagerFactory emf;

    public WCSItemTransDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
}
