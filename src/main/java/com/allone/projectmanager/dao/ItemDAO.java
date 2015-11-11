/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Item;
import com.google.common.base.Strings;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ItemDAO extends Item {

    private EntityManagerFactory emf;

    public ItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll() {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Item.findAll");
            
            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public Item getByImno(String imno) {
        Item values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (!Strings.isNullOrEmpty(imno)) ? em.createNamedQuery(
                          "com.allone.projectmanager.entities.Item.findByImno").setParameter("imno", imno) :
                          null;
            
            values = (Item) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public Item getById(Long id) {
        Item values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = (id != null && id.compareTo(0l) >= 0) ? em.createNamedQuery(
                          "com.allone.projectmanager.entities.Item.findById").setParameter("id", id) : null;
            
            values = (Item) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public Long getLastId() {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Item.findLastId");
            
            values = (Long) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }
    
    public Item add(Item i) {
        EntityManager em = emf.createEntityManager();

        try {
            if (i != null) {
                em.getTransaction().begin();
                em.persist(i);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return i;
        }
    }
}
