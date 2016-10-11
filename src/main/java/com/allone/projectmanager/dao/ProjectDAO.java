/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.Project;
import com.google.common.base.Strings;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ProjectDAO {

    private static final Logger logger = Logger.getLogger(ProjectDAO.class.getName());

    private final EntityManagerFactory emf;

    private Integer[] checkPagingAttributes(final Integer offset, final Integer size) {
        Integer[] values = new Integer[]{0, 10};

        values[0] = (offset != null && offset.compareTo(0) > 0) ? offset : 0;
        values[1] = (size != null && size.compareTo(0) > 0) ? size : 10;

        return values;
    }

    public ProjectDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll(Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Integer[] limits = checkPagingAttributes(offset, size);
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findAll").setFirstResult(limits[0] * limits[1])
                    .setMaxResults(limits[1]);

            values = (query != null) ? query.getResultList() : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long countAll() {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countAll");

            values = (query != null) ? (Long) query.getSingleResult() : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Project add(Project item) {
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

    public void delete(Project item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.remove(em.contains(item) ? item : em.merge(item));
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }

    public Long countByStatus(String status) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(status)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countByStatus").setParameter("status", status);

                values = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public List getByStatus(String status, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(status)) {
                Integer[] limits = checkPagingAttributes(offset, size);
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findByStatus").setParameter("status", status)
                        .setFirstResult(limits[0] * limits[1]).setMaxResults(limits[1]);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public void edit(Project item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.merge(item);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }

    public Project getByReference(String reference) {
        Project value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(reference)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findByReference")
                        .setParameter("reference", "%" + reference + "%");

                value = (query != null) ? (Project) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public Project getById(Long id) {
        Project value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findById").setParameter("id", id);

                value = (query != null) ? (Project) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public List getByCriteria(Map<String, String> criteria, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();
        String qcriteria = "SELECT p FROM com.allone.projectmanager.entities.ProjectDetail p";

        try {
            Integer[] limits = checkPagingAttributes(offset, size);

            if (criteria != null && !criteria.isEmpty()) {
                qcriteria += " WHERE ";
                Set<String> keys = criteria.keySet();

                if (keys != null && !keys.isEmpty()) {
                    for (String key : keys) {
                        switch (key) {
                            case "vesselCustom":
                                qcriteria += "p.vesselName LIKE '%" + criteria.get(key) + "%' AND ";
                                break;
                            case "customerCustom":
                                qcriteria += "p.company LIKE '%" + criteria.get(key) + "%' AND ";
                                break;
                            case "start":
                                qcriteria += "p.created BETWEEN '" + criteria.get(key) + " 00:00:00' AND ";
                                break;
                            case "end":
                                qcriteria += "'" + criteria.get(key) + " 23:59:59' AND ";
                                break;
                            default:
                                qcriteria += "p." + key + "='" + criteria.get(key) + "' AND ";
                                break;
                        }
                    }
                }

                qcriteria = qcriteria.substring(0, qcriteria.lastIndexOf("AND"));
            }
            qcriteria += " ORDER BY p.created DESC";

            Query query = em.createQuery(qcriteria).setFirstResult(limits[0] * limits[1]).setMaxResults(limits[1]);

            values = (query != null) ? query.getResultList() : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long getCountByCriteria(Map<String, String> criteria) {
        Long values = 0l;
        EntityManager em = emf.createEntityManager();
        String qcriteria = "SELECT count(p) FROM com.allone.projectmanager.entities.ProjectDetail p";

        try {
            if (criteria != null && !criteria.isEmpty()) {
                qcriteria += " WHERE ";
                Set<String> keys = criteria.keySet();

                if (keys != null && !keys.isEmpty()) {
                    for (String key : keys) {
                        switch (key) {
                            case "vesselCustom":
                                qcriteria += "p.vessel=" + criteria.get(key) + " AND ";
                                break;
                            case "customerCustom":
                                qcriteria += "p.company LIKE '%" + criteria.get(key) + "%' AND ";
                                break;
                            case "start":
                                qcriteria += "p.created BETWEEN '" + criteria.get(key) + " 00:00:00' AND ";
                                break;
                            case "end":
                                qcriteria += "'" + criteria.get(key) + " 23:59:59' AND ";
                                break;
                            default:
                                qcriteria += "p." + key + "='" + criteria.get(key) + "' AND ";
                                break;
                        }
                    }
                }
                qcriteria = qcriteria.substring(0, qcriteria.lastIndexOf("AND"));
            }

            Query query = em.createQuery(qcriteria);

            values = (query != null) ? (Long) query.getSingleResult() : 0l;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }
}
