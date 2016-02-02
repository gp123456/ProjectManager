/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.ProjectBillItem;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class ProjectBillItemDAO {

    private EntityManagerFactory emf;

    public ProjectBillItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List getByProjectBill(Long projectBill) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (projectBill != null && projectBill.compareTo(0l) >= 0) ? em.createNamedQuery(
                  "com.allone.projectmanager.entities.ProjectBillItem.findByProjectBill").setParameter(
                          "projectBill", projectBill) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = query.getResultList();
            
            em.close();

            return values;
        }
    }

    public Collection<ProjectBillItem> add(Collection<ProjectBillItem> pbis) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pbis != null && !pbis.isEmpty()) {
                em.getTransaction().begin();
                for (ProjectBillItem pbi : pbis) {
                    em.persist(pbi);
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return pbis;
        }
    }
    
    public ProjectBillItem add(ProjectBillItem pbi) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pbi != null) {
                em.getTransaction().begin();
                em.persist(pbi);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return pbi;
        }
    }

    public void edit(List<ProjectBillItem> pbis) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pbis != null && !pbis.isEmpty()) {
                em.getTransaction().begin();
                for (ProjectBillItem pbi : pbis) {
                    em.refresh(pbi);
                }
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void edit(Long projectBill, List<ProjectBillItem> pbis) {
        EntityManager em = emf.createEntityManager();
        List<ProjectBillItem> _pbis = getByProjectBill(projectBill);

        try {
            em.getTransaction().begin();
            if (_pbis != null && !_pbis.isEmpty()) {
                for (ProjectBillItem pbi : _pbis) {
                    em.remove(pbi);
                }
            }
            if (pbis != null && !pbis.isEmpty()) {
                for (ProjectBillItem pbi : pbis) {
                    em.persist(pbi);
                }
            }
            em.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }
    
    public ProjectBillItem edit(ProjectBillItem pbi) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            if (pbi != null) {
                em.persist(pbi);
            }
            em.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
            
            return pbi;
        }
    }

    public void delete(Long projectBill) {
        EntityManager em = emf.createEntityManager();
        List<ProjectBillItem> pbis = getByProjectBill(projectBill);

        try {
            if (pbis != null && !pbis.isEmpty()) {
                em.getTransaction().begin();

                for (ProjectBillItem pbi : pbis) {
                    em.remove(pbi);
                }
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }
}
