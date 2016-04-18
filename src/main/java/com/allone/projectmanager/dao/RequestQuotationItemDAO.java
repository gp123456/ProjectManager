/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.RequestQuotationItem;
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

    private EntityManagerFactory emf;

    public RequestQuotationItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RequestQuotationItem getById(Long id) {
        RequestQuotationItem value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.RequestQuotationItem.findById").
                      setParameter("id", id);

                value = (query != null) ?
                        (RequestQuotationItem) query.getSingleResult() :
                        null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public List getByRequestQoutation(Long rq) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (rq != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.RequestQuotationItem.findByRequestQuotation").
                      setParameter("requestQoutation", rq);

                value = (query != null) ?
                        query.getResultList() :
                        null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public RequestQuotationItem add(RequestQuotationItem ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                em.getTransaction().begin();
                em.persist(ms);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();

            return ms;
        }
    }

    public List add(List<RequestQuotationItem> ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null && !ms.isEmpty()) {
                em.getTransaction().begin();
                for (RequestQuotationItem item : ms) {
                    em.persist(item);
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();

            return ms;
        }
    }

    public void edit(RequestQuotationItem ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                em.getTransaction().begin();
                em.refresh(ms);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(RequestQuotationItem ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                em.getTransaction().begin();
                em.remove(ms);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(List<RequestQuotationItem> ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null && !ms.isEmpty()) {
                em.getTransaction().begin();
                for (RequestQuotationItem item : ms) {
                    em.remove(item);
                }
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }
}
