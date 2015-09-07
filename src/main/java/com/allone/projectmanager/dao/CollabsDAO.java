/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import com.allone.projectmanager.entities.Collabs;
import com.google.common.base.Strings;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TransactionRequiredException;

/**
 *
 * @author antonia
 */
public class CollabsDAO {

    private final EntityManagerFactory emf;

    public CollabsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Collabs login(String username, String password) {
        Collabs value = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) ? em.createNamedQuery(
                  "com.allone.projectmanager.entities.Collabs.findByColusernameColpassword").setParameter("username",
                                                                                                          username)
                  .setParameter("password", password) : null;
            value = (Collabs) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public Collabs getById(Long id) {
        Collabs value = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (id != null && id.compareTo(0l) >= 0) ? em.createNamedQuery(
                  "com.allone.projectmanager.entities.Collabs.findById").setParameter("id", id) : null;
            value = (Collabs) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public Collabs updateProjectId(Long id) {
        EntityManager em = emf.createEntityManager();
        Collabs user = null;

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                em.getTransaction().begin();
                int countUpdate = em.createNamedQuery("com.allone.projectmanager.entities.Collabs.updatePr").
                    setParameter("id", id).executeUpdate();
                em.getTransaction().commit();

                if (countUpdate > 0) {
                    user = getById(id);
                }
            }
        } catch (TransactionRequiredException e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return user;
        }
    }
}
