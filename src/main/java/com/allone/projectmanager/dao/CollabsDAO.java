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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TransactionRequiredException;

/**
 *
 * @author antonia
 */
public class CollabsDAO {

    private static final Logger logger = Logger.getLogger(CollabsDAO.class.getName());

    private final EntityManagerFactory emf;

    public CollabsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Collabs login(String username, String password) {
        Collabs value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(username) && !Strings.isNullOrEmpty(password)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Collabs.findByColusernameColpassword")
                        .setParameter("username", username).setParameter("password", password);

                value = (query != null) ? (Collabs) query.getSingleResult() : null;
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public Collabs getById(Long id) {
        Collabs value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Collabs.findById").setParameter("id", id);

                value = (query != null) ? (Collabs) query.getSingleResult() : null;
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public List getByRole(String role) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(role)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Collabs.findByRole").setParameter("role", role);

                values = (query != null) ? query.getResultList() : null;
            }

        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Collabs updateProjectId(Long id) {
        EntityManager em = emf.createEntityManager();
        Collabs user = null;

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                em.getTransaction().begin();
                int countUpdate = em.createNamedQuery("com.allone.projectmanager.entities.Collabs.updateProjectId").
                        setParameter("id", id).executeUpdate();
                em.getTransaction().commit();

                if (countUpdate > 0) {
                    user = getById(id);
                }
            }
        } catch (TransactionRequiredException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return user;
    }
}
