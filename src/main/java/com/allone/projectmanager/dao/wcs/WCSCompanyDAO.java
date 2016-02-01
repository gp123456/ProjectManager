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
public class WCSCompanyDAO {
    private static final Logger logger = Logger.getLogger(WCSCompanyDAO.class.getName());
    
    private EntityManagerFactory emf;

    public WCSCompanyDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAllByType(String type) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.wcs.WCSCompany.findAllByType").setParameter("type", type);

            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }
}
