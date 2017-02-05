/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Contact;
import com.google.common.base.Strings;
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
public class ContactDAO {

    private static final Logger logger = Logger.getLogger(ContactDAO.class.getName());

    private final EntityManagerFactory emf;

    public ContactDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll() {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Contact.findAll");

            values = query.getResultList();
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Contact getById(Long id) {
        Contact value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Contact.findById").setParameter("id", id);

                value = (query != null) ? (Contact) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public List getByCompany(String name) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(name)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Contact.findByCompany").setParameter("company", name);

                value = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }
        em.close();

        return value;
    }

    public List getByVessel(Long id) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Contact.findByVessel").setParameter("id", id);

                value = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public Contact add(Contact item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.persist(item);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return item;
    }
}
