/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.RequestQuotationItem;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ProjectRequestQuotationItemDAO {

    private EntityManagerFactory emf;

    public ProjectRequestQuotationItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RequestQuotationItem getById(Long id) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectRequestQuotationItem.findById").
            setParameter("id",
                         id);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            RequestQuotationItem values = (query != null) ?
                                         (RequestQuotationItem) query.getSingleResult() : null;

            em.close();

            return values;
        }
    }

    public List getByProjectRequestQuotation(Long projectRequestQuotation) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (projectRequestQuotation != null && projectRequestQuotation.compareTo(0l) >= 0) ?
                    em.createNamedQuery(
                            "com.allone.projectmanager.entities.ProjectRequestQuotationItem.findByProjectRequestQuotation").
                    setParameter("projectRequestQuotation", projectRequestQuotation) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = query.getResultList();

            em.close();

            return values;
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
}
