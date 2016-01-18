/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao.wcs;

import com.allone.projectmanager.entities.wcs.WCSProject;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author Admin
 */
public class WCSProjectDAO {
    private EntityManagerFactory emf;

    public WCSProjectDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public List getAll(Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.wcs.WCSProject.findAll");
            
            if (offset != null && offset.compareTo(0) >= 0 && size != null && size.compareTo(0) > 0) {
                query.setFirstResult(offset * size).setMaxResults(size);
            }

            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }
    
    public List getByCriteria(Map<String, String> criteria, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.wcs.WCSProject.findByCriteria+Q");
            
            if (offset != null && offset.compareTo(0) >= 0 && size != null && size.compareTo(0) > 0) {
                query.setFirstResult(offset * size).setMaxResults(size);
            }

            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }
    
    public WCSProject getByReference(String reference) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.wcs.WCSProject.findByReference").
                    setParameter("reference", reference);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            WCSProject value = (query != null) ? (WCSProject) query.getSingleResult() : null;

            em.close();

            return value;
        }
    }
}
