/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.ProjectDetail;
import com.allone.projectmanager.enums.ProjectStatusEnum;
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
public class ProjectDetailDAO {

    private EntityManagerFactory emf;

    private void checkPagingAttributes(Integer offset, Integer size) {
        offset = (offset.compareTo(0) < 0 || offset.compareTo(Integer.MAX_VALUE) == 0) ? 0 : offset;
        size = (size.compareTo(0) < 0 || size.compareTo(Integer.MAX_VALUE) == 0) ? 10 : size;
    }

    public ProjectDetailDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @SuppressWarnings("unchecked")
    public List getByCreator(Long creator, ProjectStatusEnum status) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByCreator").setParameter(
            "creator", creator).setParameter("status", status.toString());
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    @SuppressWarnings("unchecked")
    public List getAll(Integer offset, Integer size) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            checkPagingAttributes(offset, size);
            query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findAll").
            setFirstResult(offset * size).setMaxResults(size);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = query.getResultList();

            em.close();

            return values;
        }
    }

    @SuppressWarnings("unchecked")
    public List getByProjectId(Long project) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByProjectId").
            setParameter("project", project);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List value = (query != null) ? query.getResultList() : null;

            em.close();

            return value;
        }
    }

    @SuppressWarnings("unchecked")
    public List getByType(String type, Integer offset, Integer size) {
        Query query = null;

        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByType").setFirstResult(
            offset * size).setMaxResults(size);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    public Long countByType(String type) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (!Strings.isNullOrEmpty(type)) ?
            em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByType").setParameter("type",
                                                                                                             type) :
            null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            Long values = (query != null) ? (Long) query.getSingleResult() :
                 null;

            em.close();

            return values;
        }
    }

    public List getMyProjectType(Long creator, String type) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (creator != null && !Strings.isNullOrEmpty(type)) ?
            em.createNamedQuery("com.allone.projectmanager.entities.Project.findMyProjectType").
            setParameter("status", ProjectStatusEnum.CREATE.toString()).setParameter("creator", creator).
            setParameter("type", type) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    public List getMyProjectCompany(Long creator, String company) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (creator != null && creator.compareTo(0l) >= 0 && !Strings.
            isNullOrEmpty(company)) ?
            em.createNamedQuery("com.allone.projectmanager.entities.Project.findMyProjectCompany").
            setParameter("status", ProjectStatusEnum.CREATE.toString()).setParameter("creator", creator).
            setParameter("company", company) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    public List getByVessel(Long vessel, Integer offset, Integer size) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (vessel != null && vessel.compareTo(0l) >= 0) ?
            em.createNamedQuery("com.allone.projectmanager.entities.Project.findByVessel").
            setParameter("vessel", vessel).setFirstResult(offset * size).
            setMaxResults(size) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    public Long countByVessel(Long vessel) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (vessel != null && vessel.compareTo(0l) >= 0) ?
            em.createNamedQuery("com.allone.projectmanager.entities.Project.countByVessel").
            setParameter("vessel", vessel) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            Long values = (query != null) ? (Long) query.getSingleResult() :
                 null;

            em.close();

            return values;
        }
    }

    public List getByCustomer(String customer, Integer offset, Integer size) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (!Strings.isNullOrEmpty(customer)) ?
            em.createNamedQuery(
                    "com.allone.projectmanager.entities.Project.findByCustomer").
            setParameter("customer", customer).setFirstResult(offset *
            size).setMaxResults(size) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    public Long countByCustomer(String customer) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (!Strings.isNullOrEmpty(customer)) ?
            em.createNamedQuery(
                    "com.allone.projectmanager.entities.Project.countByCustomer").
            setParameter("customer", customer) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            Long values = (query != null) ? (Long) query.getSingleResult() :
                 null;

            em.close();

            return values;
        }
    }

    public List getByCompany(String company, Integer offset, Integer size) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (!Strings.isNullOrEmpty(company)) ? em.createNamedQuery(
            "com.allone.projectmanager.entities.Project.findByCompany").
            setParameter("company", company).
            setFirstResult(offset * size).setMaxResults(size) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = (query != null) ? query.getResultList() : null;

            em.close();

            return values;
        }
    }

    public Long countByCompany(String company) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (!Strings.isNullOrEmpty(company)) ?
            em.createNamedQuery(
                    "com.allone.projectmanager.entities.Project.countByCompany").
            setParameter("company", company) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            Long values = (query != null) ? (Long) query.getSingleResult() :
                 null;

            em.close();

            return values;
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

    public ProjectDetail getById(Long id) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findById").setParameter("id", id);
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            ProjectDetail values = (query != null) ? (ProjectDetail) query.getSingleResult() : null;

            em.close();

            return values;
        }
    }

    public ProjectDetail add(ProjectDetail pd) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pd != null) {
                em.getTransaction().begin();
                em.persist(pd);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return pd;
        }
    }

    public void edit(ProjectDetail pd) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pd != null) {
                em.getTransaction().begin();
                em.merge(pd);
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }
}
