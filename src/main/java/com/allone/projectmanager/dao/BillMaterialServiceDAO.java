/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.BillMaterialService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class BillMaterialServiceDAO extends BillMaterialService {
    
    private static final Logger logger = Logger.getLogger(BillMaterialServiceDAO.class.getName());

    private EntityManagerFactory emf;

    public BillMaterialServiceDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public BillMaterialService getById(Long id) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialService.findById").setParameter("id", id);
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            BillMaterialService value = (query != null) ? (BillMaterialService) query.getSingleResult() : null;

            em.close();

            return value;
        }
    }

    public BillMaterialService getByProject(Long project) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (project != null && project.compareTo(0l) >= 0) ? em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialService.findByProject").setParameter("project", project) : null;
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            BillMaterialService value = (query != null) ? (BillMaterialService) query.getSingleResult() : null;

            em.close();

            return value;
        }
    }

    public BillMaterialService add(BillMaterialService ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                em.getTransaction().begin();
                em.persist(ms);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return ms;
        }
    }

    public void edit(BillMaterialService ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                em.getTransaction().begin();
                em.merge(ms);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(BillMaterialService bms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (bms != null) {
                em.getTransaction().begin();
                em.remove(em.contains(bms) ? bms : em.merge(bms));
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }
}
