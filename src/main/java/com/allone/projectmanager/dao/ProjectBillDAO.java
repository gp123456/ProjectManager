/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.ProjectBill;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ProjectBillDAO extends ProjectBill {

    private EntityManagerFactory emf;

    public ProjectBillDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ProjectBill findByProjectId(Long id) {
        ProjectBill values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (id != null && id.compareTo(0l) >= 0) ? em.createNamedQuery(
                          "com.allone.projectmanager.entities.ProjectBill.findByProjectId").setParameter("id", id) :
                          null;

            values = (ProjectBill) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public ProjectBill add(ProjectBill ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                em.getTransaction().begin();
                em.persist(ms);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return ms;
        }
    }

    public void edit(ProjectBill ms) {
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

    public void delete(ProjectBill ms) {
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
