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
public class ProjectDetailDAO {

    private static final Logger logger = Logger.getLogger(ProjectDetailDAO.class.getName());

    private final EntityManagerFactory emf;

    private Integer[] checkPagingAttributes(Integer offset, Integer size) {
        Integer[] values = new Integer[]{0, 10};

        values[0] = (offset != null && offset.compareTo(0) > 0) ? offset : 0;
        values[1] = (size != null && size.compareTo(0) > 0) ? size : 10;

        return values;
    }

    public ProjectDetailDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @SuppressWarnings("unchecked")
    public List getByCreator(Long creator, ProjectStatusEnum status) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (creator != null && status != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByCreator").setParameter("creator", creator)
                        .setParameter("status", status.toString());

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    @SuppressWarnings("unchecked")
    public List getAll(Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Integer[] limits = checkPagingAttributes(offset, size);

            Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findAll").setFirstResult(limits[0] * limits[1])
                    .setMaxResults(limits[1]);

            values = (query != null) ? query.getResultList() : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    @SuppressWarnings("unchecked")
    public Long countByProject(Long id) {
        Long count = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByProject").setParameter("project", id);

                count = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return count;
    }

    @SuppressWarnings("unchecked")
    public List getByProjectId(Long id) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByProjectId").setParameter("project", id);

                value = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    @SuppressWarnings("unchecked")
    public List getByProjectIdType(Long id, String type) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByProjectIdType").setParameter("project", id)
                        .setParameter("type", type);

                value = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    @SuppressWarnings("unchecked")
    public ProjectDetail getFirstByProjectIdType(Long id, String type) {
        ProjectDetail value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByProjectIdType").setParameter("project", id)
                        .setParameter("type", type);

                value = (query != null) ? (ProjectDetail) query.getResultList().get(0) : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    @SuppressWarnings("unchecked")
    public List getByType(String type, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type)) {
                Integer[] limits = checkPagingAttributes(offset, size);

                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByType").setParameter("type", type)
                        .setFirstResult(limits[0] * limits[1]).setMaxResults(limits[1]);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long countByType(String type) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByType").setParameter("type", type);

                values = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public List getMyProjectType(Long id, String type) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0 && !Strings.isNullOrEmpty(type)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findMyProjectType")
                        .setParameter("status", ProjectStatusEnum.CREATE.toString()).setParameter("creator", id).setParameter("type", type);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public List getMyProjectCompany(Long id, String company) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0 && !Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findMyProjectCompany")
                        .setParameter("status", ProjectStatusEnum.CREATE.toString()).setParameter("creator", id).setParameter("company", company);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public List getByVessel(Long id, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Integer[] limits = checkPagingAttributes(offset, size);

                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findByVessel").setParameter("vessel", id)
                        .setFirstResult(limits[0] * limits[1]).setMaxResults(limits[1]);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long countByVessel(Long id) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countByVessel").setParameter("vessel", id);

                values = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public List getByCustomer(String customer, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(customer)) {
                Integer[] limits = checkPagingAttributes(offset, size);

                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findByCustomer").setParameter("customer", customer)
                        .setFirstResult(limits[0] * limits[1]).setMaxResults(limits[1]);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long countByCustomer(String customer) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(customer)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countByCustomer").setParameter("customer", customer);

                values = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public List getByCompany(String company, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(company)) {
                Integer[] limits = checkPagingAttributes(offset, size);
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findByCompany").setParameter("company", company)
                        .setFirstResult(limits[0] * limits[1]).setMaxResults(limits[1]);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long countByCompany(String company) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countByCompany").setParameter("company", company);

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
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByStatus").setParameter("status", status)
                        .setFirstResult(limits[0] * limits[1]).setMaxResults(limits[1]);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }
    
    public List getByStatus(String status) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(status)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByStatus").setParameter("status", status);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long getByProjectIdNotLost(Long pId) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (pId != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findNotLost")
                        .setParameter("status", ProjectStatusEnum.LOST.toString())
                        .setParameter("project", pId);

                values = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long countByStatus(String status) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(status)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByStatus").setParameter("status", status);

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public ProjectDetail getById(Long id) {
        ProjectDetail value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findById").setParameter("id", id);

                value = (query != null) ? (ProjectDetail) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public ProjectDetail getByReference(String reference) {
        ProjectDetail value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(reference)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByReference")
                        .setParameter("reference", "%" + reference + "%");

                value = (query != null) ? (ProjectDetail) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public ProjectDetail getLastByProject(Long id) {
        ProjectDetail values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findLastByProject").setParameter("project", id)
                        .setMaxResults(1);

                values = (query != null) ? (ProjectDetail) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public String getOpenExist(String type, Long vessel, String company) {
        String value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type) && vessel != null && vessel.compareTo(0l) > 0 && !Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findOpenExist")
                        .setParameter("type", type)
                        .setParameter("vessel", vessel)
                        .setParameter("company", company)
                        .setMaxResults(1);

                value = (query != null) ? (String) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return value;
    }

    public ProjectDetail add(ProjectDetail item) {
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

    public void edit(ProjectDetail item) {
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

    public Long countAll() {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countAll");

            values = (query != null) ? (Long) query.getSingleResult() : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public List<ProjectDetail> getCreatedByProjectExceptId(Long pId, Long pdId) {
        List<ProjectDetail> values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (pId != null && pId.compareTo(0l) > 0 && pdId != null && pdId.compareTo(0l) > 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findCreatedByProjectExceptId").setParameter("pId", pId)
                        .setParameter("pdId", pdId).setParameter("status", ProjectStatusEnum.CREATE.toString());

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long getTotalOpenByType(String type) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countOpenByType").setParameter("type", type)
                        .setParameter("status", ProjectStatusEnum.INVOICE.toString());

                values = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long getCountByTypeStatus(String type, String status) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type) && !Strings.isNullOrEmpty(status)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByTypeStatus").setParameter("type", type)
                        .setParameter("status", status);

                values = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Long getCountByTypeCompany(String type, String company) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type) && !Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByTypeCompany").setParameter("type", type)
                        .setParameter("status", ProjectStatusEnum.INVOICE.toString()).setParameter("company", company);

                values = (query != null) ? (Long) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public void delete(ProjectDetail item) {
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
}
