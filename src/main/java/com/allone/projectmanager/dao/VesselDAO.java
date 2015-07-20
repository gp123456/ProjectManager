/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Vessel;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author antonia
 */
public class VesselDAO extends Vessel {
    private EntityManagerFactory emf;
    
    public VesselDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public List getAll() {
        List values = null;
        EntityManager em = emf.createEntityManager();
        
        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Vessel.findAll");
            
            values = query.getResultList();
        } catch (HibernateException e ) { System.out.printf("%s", e.getMessage()); }
        finally {
            em.close();
            
            return values;
        }
    }
    
    public Vessel getById(Long id) {
        Vessel value = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (id != null && id.compareTo(0l) >= 0) ? em.createNamedQuery("com.allone.projectmanager.entities.Vessel.findById").setParameter("id", id) : null;
            
            value = (Vessel) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }
}
