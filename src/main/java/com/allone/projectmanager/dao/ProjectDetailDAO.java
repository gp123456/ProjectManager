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

    private void checkPagingAttributes(Integer offset, Integer size) {
        offset = (offset.compareTo(0) < 0 || offset.compareTo(Integer.MAX_VALUE) == 0) ? 0 : offset;
        size = (size.compareTo(0) < 0 || size.compareTo(Integer.MAX_VALUE) == 0) ? 10 : size;
    }

    public ProjectDetailDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @SuppressWarnings("unchecked")
    public List getByCreator(Long creator, ProjectStatusEnum status) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (creator != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByCreator")
                        .setParameter("creator", creator)
                        .setParameter("status", status.toString());

                values = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
        return values;
    }

    @SuppressWarnings("unchecked")
    public List getAll(Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            checkPagingAttributes(offset, size);

            Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findAll")
                    .setFirstResult(offset * size)
                    .setMaxResults(size);

            values = (query != null)
                    ? query.getResultList()
                    : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    @SuppressWarnings("unchecked")
    public Long countByProject(Long pId) {
        Long count = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByProject")
                    .setParameter("project", pId);

            count = (query != null)
                    ? (Long) query.getSingleResult()
                    : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return count;
    }

    @SuppressWarnings("unchecked")
    public List getByProjectId(Long project) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (project != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByProjectId")
                        .setParameter("project", project);

                value = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return value;
    }

    @SuppressWarnings("unchecked")
    public List getByProjectIdType(Long project, String type) {
        List value = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (project != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByProjectIdType")
                        .setParameter("project", project)
                        .setParameter("type", type);

                value = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return value;
    }

    @SuppressWarnings("unchecked")
    public List getByType(String type, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (type != null) {
                checkPagingAttributes(offset, size);

                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByType")
                        .setParameter("type", type)
                        .setFirstResult(offset * size)
                        .setMaxResults(size);

                values = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public Long countByType(String type) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByType")
                        .setParameter("type", type);

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public List getMyProjectType(Long creator, String type) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (creator != null && !Strings.isNullOrEmpty(type)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findMyProjectType")
                        .setParameter("status", ProjectStatusEnum.CREATE.toString())
                        .setParameter("creator", creator)
                        .setParameter("type", type);

                values = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public List getMyProjectCompany(Long creator, String company) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (creator != null && creator.compareTo(0l) >= 0 && !Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findMyProjectCompany")
                        .setParameter("status", ProjectStatusEnum.CREATE.toString())
                        .setParameter("creator", creator)
                        .setParameter("company", company);

                values = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public List getByVessel(Long vessel, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (vessel != null && vessel.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findByVessel")
                        .setParameter("vessel", vessel)
                        .setFirstResult(offset * size)
                        .setMaxResults(size);

                values = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public Long countByVessel(Long vessel) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (vessel != null && vessel.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countByVessel")
                        .setParameter("vessel", vessel);

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public List getByCustomer(String customer, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(customer)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findByCustomer")
                        .setParameter("customer", customer)
                        .setFirstResult(offset * size).setMaxResults(size);

                values = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public Long countByCustomer(String customer) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(customer)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countByCustomer")
                        .setParameter("customer", customer);

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public List getByCompany(String company, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.findByCompany")
                        .setParameter("company", company)
                        .setFirstResult(offset * size).setMaxResults(size);

                values = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public Long countByCompany(String company) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.Project.countByCompany")
                        .setParameter("company", company);

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public List getByStatus(String status, Integer offset, Integer size) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(status)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findByStatus")
                        .setParameter("status", status)
                        .setFirstResult(offset * size)
                        .setMaxResults(size);

                values = (query != null)
                        ? query.getResultList()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public Long countByStatus(String status) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(status)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByStatus")
                        .setParameter("status", status);

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public ProjectDetail getById(Long id) {
        ProjectDetail values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findById")
                        .setParameter("id", id);

                values = (query != null)
                        ? (ProjectDetail) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public ProjectDetail getLastByProject(Long project) {
        ProjectDetail values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (project != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findLastByProject")
                        .setParameter("project", project).setMaxResults(1);

                values = (query != null)
                        ? (ProjectDetail) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
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
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return pd;
    }

    public void edit(ProjectDetail pd) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pd != null) {
                em.getTransaction().begin();
                em.merge(pd);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }

    public Long countAll() {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countAll");

            values = (query != null)
                    ? (Long) query.getSingleResult()
                    : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public List<ProjectDetail> getCreatedByProjectExceptId(Long pId, Long pdId) {
        List<ProjectDetail> values = null;
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.findCreatedByProjectExceptId")
                    .setParameter("pId", pId)
                    .setParameter("pdId", pdId)
                    .setParameter("status", ProjectStatusEnum.CREATE.toString());

            values = (query != null)
                    ? query.getResultList()
                    : null;
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public Long getTotalOpenByType(String type) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countOpenByType")
                        .setParameter("type", type)
                        .setParameter("status", ProjectStatusEnum.INVOICE.toString());

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public Long getCountByTypeStatus(String type, String status) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type) && !Strings.isNullOrEmpty(status)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByTypeStatus")
                        .setParameter("type", type)
                        .setParameter("status", status);

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public Long getCountByTypeCompany(String type, String company) {
        Long values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (!Strings.isNullOrEmpty(type) && !Strings.isNullOrEmpty(company)) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.ProjectDetail.countByTypeCompany")
                        .setParameter("type", type)
                        .setParameter("status", ProjectStatusEnum.INVOICE.toString())
                        .setParameter("company", company);

                values = (query != null)
                        ? (Long) query.getSingleResult()
                        : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }

        return values;
    }

    public void delete(ProjectDetail pd) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pd != null) {
                em.getTransaction().begin();
                em.remove(em.contains(pd) ? pd : em.merge(pd));
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }
}
