/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.RequestQuotationItem;
import java.util.Collection;
import java.util.List;
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
public class RequestQuotationItemDAO {

    private static final Logger logger = Logger.getLogger(RequestQuotationItemDAO.class.getName());

    private final EntityManagerFactory emf;

    public RequestQuotationItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RequestQuotationItem getById(Long id) {
        RequestQuotationItem value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.RequestQuotationItem.findById").setParameter("id", id);

                value = (query != null) ? (RequestQuotationItem) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public RequestQuotationItem getByBillMaterialServiceItem(Long id) {
        RequestQuotationItem value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.RequestQuotationItem.findByBillMaterialServiceItem").
                        setParameter("billMaterialServiceItem", id);

                value = (query != null) ? (RequestQuotationItem) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public List getByRequestQuotation(Long id) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.RequestQuotationItem.findByRequestQuotation")
                        .setParameter("requestQuotation", id);

                value = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public Collection add(Collection<RequestQuotationItem> items) {
        EntityManager em = emf.createEntityManager();

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();
                items.stream().filter((item) -> (item.getId() == null)).forEach((item) -> {
                    em.persist(item);
                });
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return items;
    }

    public List add(List<RequestQuotationItem> items) {
        EntityManager em = emf.createEntityManager();

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();
                items.stream().forEach((item) -> {
                    em.persist(item);
                });
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return items;
    }

    public void edit(RequestQuotationItem item) {
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

    public void delete(RequestQuotationItem item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.remove(item);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }

    public void delete(List<RequestQuotationItem> items) {
        EntityManager em = emf.createEntityManager();

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();
                items.stream().forEach((item) -> {
                    em.remove(item);
                });
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }
}
