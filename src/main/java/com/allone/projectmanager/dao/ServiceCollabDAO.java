/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.ServiceCollab;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author antonia
 */
public class ServiceCollabDAO {
    private EntityManagerFactory emf;
    
    public ServiceCollabDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ServiceCollab add(ServiceCollab sc) {
        EntityManager em = emf.createEntityManager();

        try {
            if (sc != null) {
                em.getTransaction().begin();
                em.persist(sc);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return sc;
        }
    }
}
