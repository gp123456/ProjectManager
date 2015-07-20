/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.ProjectBillCustomItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author qbsstation5
 */
public class ProjectBillCustomItemDAO {

    private EntityManagerFactory emf;

    public ProjectBillCustomItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ProjectBillCustomItem getById(Long id) {
        ProjectBillCustomItem value = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (id != null && id.compareTo(0l) >= 0) ? em.createNamedQuery(
                    "com.allone.projectmanager.entities.ProjectBillCustomItems.findById").setParameter("id", id) : null;
            
            value = (ProjectBillCustomItem) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public ProjectBillCustomItem add(ProjectBillCustomItem mss) {
        EntityManager em = emf.createEntityManager();

        try {
            if (mss != null) {
                em.getTransaction().begin();
                em.persist(mss);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return mss;
        }
    }

    public void edit(ProjectBillCustomItem mss) {
        EntityManager em = emf.createEntityManager();

        try {
            if (mss != null) {
                em.getTransaction().begin();
                em.refresh(mss);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(Long itemId) {
        EntityManager em = emf.createEntityManager();
        ProjectBillCustomItem mi = getById(itemId);

        try {
            if (itemId != null && itemId.compareTo(0l) >= 0) {
                em.getTransaction().begin();
                em.remove(mi);
            }
            em.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }
}
