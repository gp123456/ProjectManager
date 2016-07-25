/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.RequestQuotation;
import java.util.Set;
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
public class RequestQuotationDAO {

    private static final Logger logger = Logger.getLogger(RequestQuotationDAO.class.getName());

    private EntityManagerFactory emf;

    public RequestQuotationDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RequestQuotation getById(Long id) {
        RequestQuotation value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.RequestQuotation.findById").
                        setParameter("id", id);

                value = (query != null)
                        ? (RequestQuotation) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public RequestQuotation getByBillMaterialService(Long bms) {
        RequestQuotation value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (bms != null) {
                Query query = (bms != null && bms.compareTo(0l) >= 0)
                        ? em.createNamedQuery("com.allone.projectmanager.entities.RequestQuotation.findByBillMaterialService").
                        setParameter("billMaterialService", bms) : null;
                value = (query != null)
                        ? (RequestQuotation) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public RequestQuotation add(RequestQuotation rq) {
        EntityManager em = emf.createEntityManager();

        try {
            if (rq != null) {
                em.getTransaction().begin();
                em.persist(rq);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return rq;
        }
    }

    public void edit(RequestQuotation ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                em.getTransaction().begin();
                em.refresh(ms);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(RequestQuotation ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                em.getTransaction().begin();
                em.remove(ms);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }
}
