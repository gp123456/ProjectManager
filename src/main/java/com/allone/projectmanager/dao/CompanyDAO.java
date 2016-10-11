/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.google.common.base.Strings;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class CompanyDAO {

    private static final Logger logger = Logger.getLogger(CollabsDAO.class.getName());

    private final EntityManagerFactory emf;

    public CompanyDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll(String type) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Company.findAll").setParameter("type", type);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Company getByTypeName(CompanyTypeEnum type, String name) {
        Company values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (type != null && !Strings.isNullOrEmpty(name)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Company.findByTypeName").setParameter("type", type.toString())
                        .setParameter("name", name);

                values = (query != null) ? (Company) query.getSingleResult() : null;
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Company add(Company item) {
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
