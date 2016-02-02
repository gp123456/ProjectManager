/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao.wcs;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author gpap
 */
public class WCSVesselDAO {
    private static final Logger logger = Logger.getLogger(WCSVesselDAO.class.getName());
    
    private EntityManagerFactory emf;

    public WCSVesselDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll() {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.wcs.WCSVessel.findAll");

            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }
    
    public List getByCompany(String company) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.wcs.WCSVessel.findByCompany")
            .setParameter("company", company);
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
}
