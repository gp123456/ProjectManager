/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.QuotationItem;
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
public class QuotationItemDAO {

    private static final Logger logger = Logger.getLogger(QuotationItemDAO.class.getName());

    private EntityManagerFactory emf;

    public QuotationItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public QuotationItem getById(Long id) {
        QuotationItem value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.QuotationItem.findById").
                        setParameter("id", id);

                value = (query != null) ? (QuotationItem) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public QuotationItem getByBillMaterialServiceItem(Long requestForQuotation) {
        QuotationItem value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (requestForQuotation != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.QuotationItem.findByRequestForQuotation").
                        setParameter("requestForQuotation", requestForQuotation);

                value = (query != null) ? (QuotationItem) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public List getByQuotation(Long q) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (q != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.QuotationItem.findByQuotation").
                        setParameter("quotation", q);

                value = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public Collection add(Collection<QuotationItem> items) {
        EntityManager em = emf.createEntityManager();

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();
                for (QuotationItem item : items) {
                    if (item.getId() == null) {
                        em.persist(items);
                    }
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return items;
        }
    }

    public List add(List<QuotationItem> items) {
        EntityManager em = emf.createEntityManager();

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();
                for (QuotationItem item : items) {
                    em.persist(item);
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return items;
        }
    }

    public void edit(QuotationItem item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.merge(item);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(QuotationItem rqi) {
        EntityManager em = emf.createEntityManager();

        try {
            if (rqi != null) {
                em.getTransaction().begin();
                em.remove(rqi);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(List<QuotationItem> items) {
        EntityManager em = emf.createEntityManager();

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();
                for (QuotationItem item : items) {
                    em.remove(item);
                }
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }
}
