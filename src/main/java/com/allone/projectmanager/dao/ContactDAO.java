/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Contact;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ContactDAO {

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
}
