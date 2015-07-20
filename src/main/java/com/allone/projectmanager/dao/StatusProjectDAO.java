/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.StatusProject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class StatusProjectDAO extends StatusProject {
    private EntityManagerFactory emf;
    
    public StatusProjectDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public List getAll() {    
        List values = null;
        EntityManager em = emf.createEntityManager();
        
        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.StatusProject.findAll");
            
            values = query.getResultList();
        } catch ( HibernateException e ) { System.out.printf("%s", e.getMessage()); }
        finally {
            em.close();
            
            return values;
        }
    }
}
