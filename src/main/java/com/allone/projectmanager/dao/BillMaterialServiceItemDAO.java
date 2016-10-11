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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.HibernateException;

/**
 *
 * @author antonia
 */
public class BillMaterialServiceItemDAO {

    private static final Logger logger = Logger.getLogger(BillMaterialServiceItemDAO.class.getName());

    private final EntityManagerFactory emf;

    public BillMaterialServiceItemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public BillMaterialServiceItem getById(Long id) {
        BillMaterialServiceItem values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialServiceItem.findById").setParameter("id", id);

                values = (query != null) ? (BillMaterialServiceItem) query.getSingleResult() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public List getByBillMaterialService(Long id) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (id != null && id.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialServiceItem.findByBillMaterialService")
                        .setParameter("id", id);

                values = (query != null) ? query.getResultList() : null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return values;
    }

    public Collection<BillMaterialServiceItem> add(Collection<BillMaterialServiceItem> items) {
        EntityManager em = emf.createEntityManager();

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();
                items.stream().forEach((item) -> {
                    em.persist(item);
                });
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();

        return items;
    }

    public BillMaterialServiceItem add(BillMaterialServiceItem item) {
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

    public void edit(List<BillMaterialServiceItem> items) {
        EntityManager em = emf.createEntityManager();

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();
                items.stream().forEach((item) -> {
                    em.refresh(item);
                });
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }

    public void edit(Long id, List<BillMaterialServiceItem> _items) {
        EntityManager em = emf.createEntityManager();
        List<BillMaterialServiceItem> items = (id != null && id.compareTo(0l) >= 0) ? getByBillMaterialService(id) : null;

        try {
            em.getTransaction().begin();
            if (items != null && !items.isEmpty()) {
                items.stream().forEach((item) -> {
                    em.remove(item);
                });
            }
            if (items != null && !items.isEmpty()) {
                items.stream().forEach((item) -> {
                    em.persist(item);
                });
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }

    public BillMaterialServiceItem edit(BillMaterialServiceItem item) {
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

        return item;
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        List<BillMaterialServiceItem> items = (id != null && id.compareTo(0l) >= 0) ? getByBillMaterialService(id) : null;

        try {
            if (items != null && !items.isEmpty()) {
                em.getTransaction().begin();

                items.stream().forEach((item) -> {
                    em.remove(item);
                });
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        }

        em.close();
    }

    public void delete(BillMaterialServiceItem item) {
        EntityManager em = emf.createEntityManager();

        try {
            if (item != null) {
                em.getTransaction().begin();
                em.remove(em.contains(item) ? item : em.merge(item));
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }
}
