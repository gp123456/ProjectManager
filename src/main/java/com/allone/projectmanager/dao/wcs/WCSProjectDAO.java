/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao.wcs;

import com.allone.projectmanager.entities.wcs.WCSProject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author Admin
 */
public class WCSProjectDAO {

    private static final Logger logger = Logger.getLogger(WCSProjectDAO.class.getName());
    
    private EntityManagerFactory emf;

    public WCSProjectDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll(Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.wcs.WCSProject.findAll");

            if (offset != null && offset.compareTo(0) >= 0 && size != null && size.compareTo(0) > 0) {
                query.setFirstResult(offset * size).setMaxResults(size);
            }

            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public List getByCriteria(Map<String, String> criteria, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (criteria != null && !criteria.isEmpty()) {
                String qcriteria = "SELECT p FROM com.allone.projectmanager.entities.wcs.WCSProject p WHERE ";
                Set<String> keys = criteria.keySet();
                
                if (keys != null && !keys.isEmpty()) {
                    for (String key : keys) {
                        switch (key) {
                            case "vesselCustom":
                                qcriteria += "p.nameVessel LIKE '%" + criteria.get(key) + "%' AND ";
                                break;
                            case "customerCustom":
                                qcriteria += "p.customer LIKE '%" + criteria.get(key) + "%' AND ";
                                break;
                            default:
                                qcriteria += "p." + key + "='" + criteria.get(key) + "' AND ";
                                break;
                        }
                    }
                }
                qcriteria = qcriteria.substring(0, qcriteria.lastIndexOf("AND"));
                qcriteria += " ORDER BY p.code DESC";

                Query query = em.createQuery(qcriteria);

                if (offset != null && offset.compareTo(0) >= 0 && size != null && size.compareTo(0) > 0) {
                    query.setFirstResult(offset * size).setMaxResults(size);
                }

                values = query.getResultList();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public Long getCountByCriteria(Map<String, String> criteria) {
        Long values = null;
        EntityManager em = emf.createEntityManager();
        String qcriteria = "";

        try {
            if (criteria != null && !criteria.isEmpty()) {
                qcriteria = "SELECT count(p) FROM com.allone.projectmanager.entities.wcs.WCSProject p WHERE ";
                Set<String> keys = criteria.keySet();

                if (keys != null && !keys.isEmpty()) {
                    for (String key : keys) {
                        switch (key) {
                            case "vesselCustom":
                                qcriteria += "p.nameVessel LIKE '%" + criteria.get(key) + "%' AND ";
                                break;
                            case "customerCustom":
                                qcriteria += "p.customer LIKE '%" + criteria.get(key) + "%' AND ";
                                break;
                            default:
                                qcriteria += "p." + key + "='" + criteria.get(key) + "' AND ";
                                break;
                        }
                    }
                }
                qcriteria = qcriteria.substring(0, qcriteria.lastIndexOf("AND"));
            } else {
                qcriteria = "SELECT count(p) FROM com.allone.projectmanager.entities.wcs.WCSProject p";
            }

            Query query = em.createQuery(qcriteria);

            values = (Long) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public WCSProject getByReference(String reference) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.wcs.WCSProject.findByReference").
            setParameter("reference", "%" + reference + "%");
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            WCSProject value = (query != null) ? (WCSProject) query.getResultList().get(0): null;

            em.close();

            return value;
        }
    }
}
