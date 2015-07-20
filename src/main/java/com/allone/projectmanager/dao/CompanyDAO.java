/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

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

    public List getAll(Integer type) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (type != null && type.compareTo(0) >= 0) ? em.createNamedQuery(
                          "com.allone.projectmanager.entities.Company.findAll").setParameter("type", type) : null;
            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }
}
