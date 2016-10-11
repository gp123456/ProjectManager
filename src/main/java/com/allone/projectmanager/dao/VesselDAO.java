/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.controller.Root;
import com.allone.projectmanager.entities.Vessel;
import com.google.common.base.Strings;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class VesselDAO {

    private static final Logger logger = Logger.getLogger(Root.class.getName());

    private final EntityManagerFactory emf;

    public VesselDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll() {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Vessel.findAll");

            values = (query != null) ? query.getResultList() : null;
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Vessel getById(Long id) {
        Vessel value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Vessel.findById").setParameter("id", id);

                value = (query != null) ? (Vessel) query.getSingleResult() : null;
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public List getByCompany(String company) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Vessel.findByCompany").setParameter("company", company);

                value = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        }

        em.close();

        return value;
    }

    public Vessel add(Vessel item) {
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
