/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.controller.Root;
import com.allone.projectmanager.entities.Vessel;
import com.google.common.base.Strings;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class VesselDAO {

    private static final Logger LOG = Logger.getLogger(Root.class.getName());
    
    private EntityManagerFactory emf;
    
    public VesselDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public List getAll() {
        List values = null;
        EntityManager em = emf.createEntityManager();
        
        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Vessel.findAll");
            
            values = (query != null) ? query.getResultList(): null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
            
            return values;
        }
    }
    
    public Vessel getById(Long id) {
        Query query = null;
        EntityManager em = emf.createEntityManager();
        
        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.Vessel.findById").setParameter("id", id);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            Vessel value = (query != null) ? (Vessel) query.getSingleResult(): null;
            
            em.close();
            
            return value;
        }
    }
}
