/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Contact;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ContactDAO {

    private final Logger logger = Logger.getLogger(ContactDAO.class.getName());

    private EntityManagerFactory emf;

    public ContactDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll() {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Contact.findAll");
            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public Contact getById(Long id) {
        Contact value = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (id != null && id.compareTo(0l) >= 0) ? em.createNamedQuery(
                  "com.allone.projectmanager.entities.Contact.findById").setParameter("id", id) : null;
            value = (Contact) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return value;
        }
    }

    public List getByVessel(Long vessel) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.Contact.findByVessel").
            setParameter("vessel", vessel);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List value = null;

            try {
                value = (query != null) ? query.getResultList() : null;
            } catch (HibernateException e) {
                System.out.printf("%s", e.getMessage());
            } finally {
                em.close();

                return value;
            }
        }
    }

    public Contact add(Contact c) {
        EntityManager em = emf.createEntityManager();

        try {
            if (c != null) {
                em.getTransaction().begin();
                em.persist(c);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return c;
        }
    }
}
