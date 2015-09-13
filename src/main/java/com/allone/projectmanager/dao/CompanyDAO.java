/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class CompanyDAO {

    private EntityManagerFactory emf;

    public CompanyDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll(String type) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.Company.findAll").setParameter("type", type);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    public Company getByTypeName(CompanyTypeEnum type, String name) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.Company.findByTypeName").
            setParameter("type", type.toString()).setParameter("name", name);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            Company values = (query != null) ? (Company)query.getSingleResult(): null;

            em.close();

            return values;
        }
    }
    
    public Company add(Company c) {
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
