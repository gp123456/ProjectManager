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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ProjectDAO {

    private EntityManagerFactory emf;

    public ProjectDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getAll(Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findAll").
                  setFirstResult(offset * size).setMaxResults(size);

            values = query.getResultList();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public Long countAll() {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countAll");

            values = (Long) query.getSingleResult();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public Project add(Project prj) {
        EntityManager em = emf.createEntityManager();

        try {
            if (prj != null) {
                em.getTransaction().begin();
                em.persist(prj);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return prj;
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Query query = (id != null && id.compareTo(0l) >= 0) ?
                  em.createNamedQuery("com.allone.projectmanager.entities.Project.findById").setParameter("id", id) :
                  null;
            Project value = (query != null) ? (Project) query.getSingleResult() : null;
            if (value != null) {
                em.remove(value);
            }
            em.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }

    public List getByStatus(String status, Integer offset, Integer size) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (!Strings.isNullOrEmpty(status)) ?
            em.createNamedQuery("com.allone.projectmanager.entities.Project.findByStatus").
            setParameter("status", status).setFirstResult(offset * size).setMaxResults(size) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    public Long countByStatus(String status) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (!Strings.isNullOrEmpty(status)) ?
            em.createNamedQuery(
                    "com.allone.projectmanager.entities.Project.countByStatus").
            setParameter("status", status) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            Long values = (query != null) ? (Long) query.getSingleResult() :
                 null;

            em.close();

            return values;
        }
    }

    public void edit(Project pr) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pr != null) {
                em.getTransaction().begin();
                em.merge(pr);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }

    public Project getById(Long id) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findById").setParameter("id", id);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            Project value = (query != null) ? (Project) query.getSingleResult() : null;

            em.close();

            return value;
        }
    }
    
    public List getByCriteria(Map<String, String> criteria, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (criteria != null && !criteria.isEmpty()) {
                String qcriteria = "SELECT p FROM com.allone.projectmanager.entities.ProjectDetail p WHERE ";
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
                            default:
                                qcriteria += "p." + key + "='" + criteria.get(key) + "' AND ";
                                break;
                        }
                    }
                }
                qcriteria = qcriteria.substring(0, qcriteria.lastIndexOf("AND"));
                qcriteria += " ORDER BY p.created DESC";

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
                qcriteria = "SELECT count(p) FROM com.allone.projectmanager.entities.wcs.ProjectDetail p WHERE ";
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
                            default:
                                qcriteria += "p." + key + "='" + criteria.get(key) + "' AND ";
                                break;
                        }
                    }
                }
                qcriteria = qcriteria.substring(0, qcriteria.lastIndexOf("AND"));
            } else {
                qcriteria = "SELECT count(p) FROM com.allone.projectmanager.entities.ProjectDetail p";
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
}
