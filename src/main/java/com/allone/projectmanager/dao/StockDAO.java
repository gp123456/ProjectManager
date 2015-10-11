/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Stock;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class StockDAO extends Stock {

    private EntityManagerFactory emf;

    public StockDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @SuppressWarnings("unchecked")
    public List getAll(Integer offset, Integer size) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.Stock.findAll").setFirstResult(
            offset * size).setMaxResults(size);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = query.getResultList();

            em.close();

            return values;
        }
    }
}
