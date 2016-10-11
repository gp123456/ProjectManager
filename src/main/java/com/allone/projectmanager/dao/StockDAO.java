/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Stock;
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
public class StockDAO extends Stock {

    private static final Logger logger = Logger.getLogger(StockDAO.class.getName());

    private final EntityManagerFactory emf;

    private Integer[] checkPagingAttributes(final Integer offset, final Integer size) {
        Integer[] values = new Integer[]{0, 10};

        values[0] = (offset != null && offset.compareTo(0) > 0) ? offset : 0;
        values[1] = (size != null && size.compareTo(0) > 0) ? size : 10;

        return values;
    }

    public StockDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @SuppressWarnings("unchecked")
    public List getAll(Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Integer[] limits = checkPagingAttributes(offset, size);
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Stock.findAll").setFirstResult(limits[0] * limits[1])
                    .setMaxResults(limits[1]);

            values = (query != null) ? query.getResultList() : null;
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Stock getById(Long id) {
        Stock values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Stock.findById").setParameter("id", id);

                values = (Stock) query.getSingleResult();
            }
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }
}
