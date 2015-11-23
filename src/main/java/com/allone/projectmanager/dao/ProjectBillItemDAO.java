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

    public Collection<ProjectBillItem> add(Collection<ProjectBillItem> mss) {
        EntityManager em = emf.createEntityManager();

        try {
            if (mss != null && !mss.isEmpty()) {
                em.getTransaction().begin();
                for (ProjectBillItem ms : mss) {
                    em.persist(ms);
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.out.printf("%s\n", e.getMessage());
        } finally {
            em.close();

            return mss;
        }
    }

    public void edit(List<ProjectBillItem> mss) {
        EntityManager em = emf.createEntityManager();

        try {
            if (mss != null && !mss.isEmpty()) {
                em.getTransaction().begin();
                for (ProjectBillItem ms : mss) {
                    em.refresh(ms);
                }
                em.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void edit(Long projectBill, List<ProjectBillItem> mss) {
        EntityManager em = emf.createEntityManager();
        List<ProjectBillItem> mi = getByProjectBill(projectBill);

        try {
            em.getTransaction().begin();
            if (mi != null && !mi.isEmpty()) {
                for (ProjectBillItem ms : mi) {
                    em.remove(ms);
                }
            }
            if (mss != null && !mss.isEmpty()) {
                for (ProjectBillItem ms : mss) {
                    em.persist(ms);
                }
            }
            em.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void delete(Long projectBill) {
        EntityManager em = emf.createEntityManager();
        List<ProjectBillItem> mi = getByProjectBill(projectBill);

        try {
            if (mi != null && !mi.isEmpty()) {
                em.getTransaction().begin();

                for (ProjectBillItem ms : mi) {
                    em.remove(ms);
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
