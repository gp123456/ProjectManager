/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.RequestQuotation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ProjectRequestQuotationDAO {

    private EntityManagerFactory emf;

    public ProjectRequestQuotationDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RequestQuotation getById(Long id) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectRequestQuotation.findById").
            setParameter("id", id);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            RequestQuotation values = (query != null) ? (RequestQuotation) query.getSingleResult() : null;

            em.close();

            return values;
        }
    }

    public List getByBillMaterialService(Long projectBill) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (projectBill != null && projectBill.compareTo(0l) >= 0) ? em.createNamedQuery(
                    "com.allone.projectmanager.entities.ProjectRequestQuotationItem.findByProjectBill").
                    setParameter("projectBill", projectBill) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = query.getResultList();

            em.close();

            return values;
        }
    }

    public RequestQuotation add(RequestQuotation ms) {
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

    public void edit(RequestQuotation ms) {
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

    public void delete(RequestQuotation ms) {
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
