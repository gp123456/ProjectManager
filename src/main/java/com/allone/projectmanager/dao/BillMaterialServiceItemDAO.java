/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.dao;

import com.allone.projectmanager.entities.BillMaterialServiceItem;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class BillMaterialServiceItemDAO {

    private static final Logger logger = Logger.getLogger(BillMaterialServiceItemDAO.class.getName());

    private EntityManagerFactory emf;

    public BillMaterialServiceItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public BillMaterialServiceItem getById(Long id) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (id != null) ? em.createNamedQuery(
                    "com.allone.projectmanager.entities.BillMaterialServiceItem.findById").setParameter("id", id) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            BillMaterialServiceItem values = (BillMaterialServiceItem) query.getSingleResult();

            em.close();

            return values;
        }
    }

    public List getByProjectBill(Long projectBill) {
        Query query = null;
        EntityManager em = emf.createEntityManager();

        try {
            query = (projectBill != null && projectBill.compareTo(0l) >= 0)
                    ? em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialServiceItem.findByProjectBill").setParameter("projectBill", projectBill) : null;
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            List values = query.getResultList();

            em.close();

            return values;
        }
    }

    public Collection<BillMaterialServiceItem> add(Collection<BillMaterialServiceItem> pbis) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pbis != null && !pbis.isEmpty()) {
                em.getTransaction().begin();
                for (BillMaterialServiceItem pbi : pbis) {

                    logger.log(Level.INFO, "{0}", pbi.getItem());

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

    public BillMaterialServiceItem add(BillMaterialServiceItem pbi) {
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

    public void edit(List<BillMaterialServiceItem> pbis) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pbis != null && !pbis.isEmpty()) {
                em.getTransaction().begin();
                for (BillMaterialServiceItem pbi : pbis) {
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

    public void edit(Long projectBill, List<BillMaterialServiceItem> pbis) {
        EntityManager em = emf.createEntityManager();
        List<BillMaterialServiceItem> _pbis = getByProjectBill(projectBill);

        try {
            em.getTransaction().begin();
            if (_pbis != null && !_pbis.isEmpty()) {
                for (BillMaterialServiceItem pbi : _pbis) {
                    em.remove(pbi);
                }
            }
            if (pbis != null && !pbis.isEmpty()) {
                for (BillMaterialServiceItem pbi : pbis) {
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

    public BillMaterialServiceItem edit(BillMaterialServiceItem pbi) {
        EntityManager em = emf.createEntityManager();

        try {
            if (pbi != null) {
                em.getTransaction().begin();
                em.merge(pbi);
                em.getTransaction().commit();
            }

        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();

            return pbi;
        }
    }

    public void delete(Long projectBill) {
        EntityManager em = emf.createEntityManager();
        List<BillMaterialServiceItem> pbis = getByProjectBill(projectBill);

        try {
            if (pbis != null && !pbis.isEmpty()) {
                em.getTransaction().begin();

                for (BillMaterialServiceItem pbi : pbis) {
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

    public void delete(BillMaterialServiceItem item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.remove(item);
                em.getTransaction().commit();
            }
            em.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.printf("%s", e.getMessage());
        } finally {
            em.close();
        }
    }
}
