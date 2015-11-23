/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.ProjectBill;
import java.util.List;
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

    public List getByProject(Long project) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (project != null && project.compareTo(0l) >= 0) ? em.createNamedQuery(
            "com.allone.projectmanager.entities.ProjectBill.findByProject").setParameter("project", project) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = query.getResultList();

            em.close();

            return values;
        }
    }

    public ProjectBill add(ProjectBill ms) {
        EntityManager em = emf.createEntityManager();

        try {
            if (ms != null) {
                System.out.printf("dbpb is not null\n");
                em.getTransaction().begin();
                System.out.printf("dbpb begin\n");
                em.persist(ms);
                System.out.printf("dbpb persist");
                em.getTransaction().commit();
                System.out.printf("dbpb=%d", ms.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
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
