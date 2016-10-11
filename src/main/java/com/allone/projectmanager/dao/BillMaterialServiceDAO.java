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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class BillMaterialServiceDAO extends BillMaterialService {

    private static final Logger logger = Logger.getLogger(BillMaterialServiceDAO.class.getName());

    private final EntityManagerFactory emf;

    public BillMaterialServiceDAO(final EntityManagerFactory emf) {
        this.emf = emf;
    }

    public BillMaterialService getById(Long id) {
        BillMaterialService value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialService.findById")
                        .setParameter("id", id);

                value = (query != null)
                        ? (BillMaterialService) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public BillMaterialService getByProject(Long id) {
        BillMaterialService value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialService.findByProject").setParameter("id", id);

                value = (query != null) ? (BillMaterialService) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public BillMaterialService add(BillMaterialService item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.persist(item);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return item;
    }

    public void edit(BillMaterialService item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.merge(item);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }

    public void delete(BillMaterialService item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.remove(em.contains(item) ? item : em.merge(item));
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }
}
