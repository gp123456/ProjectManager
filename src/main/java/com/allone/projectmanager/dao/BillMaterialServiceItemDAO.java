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
            if (id != null) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialServiceItem.findById")
                      .setParameter("id", id);

                values = (query != null) ?
                         (BillMaterialServiceItem) query.getSingleResult() :
                         null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return values;
        }
    }

    public List getByBillMaterialService(Long projectBill) {
        List values = null;
        EntityManager em = emf.createEntityManager();

        try {
            if (projectBill != null && projectBill.compareTo(0l) >= 0) {
                Query query = em.createNamedQuery("com.allone.projectmanager.entities.BillMaterialServiceItem.findByBillMaterialService")
                      .setParameter("billMaterialService", projectBill);

                values = (query != null) ?
                         query.getResultList() :
                         null;
            }
        } catch (HibernateException | NoResultException e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
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
                    em.persist(pbi);
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
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
            logger.log(Level.SEVERE, "{0}", e.getMessage());
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
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
    }

    public void edit(Long projectBill, List<BillMaterialServiceItem> pbis) {
        EntityManager em = emf.createEntityManager();
        List<BillMaterialServiceItem> _pbis = getByBillMaterialService(projectBill);

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
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
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

        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();

            return pbi;
        }
    }

    public void delete(Long bmsId) {
        EntityManager em = emf.createEntityManager();
        List<BillMaterialServiceItem> bmsis = getByBillMaterialService(bmsId);

        try {
            if (bmsis != null && !bmsis.isEmpty()) {
                em.getTransaction().begin();

                for (BillMaterialServiceItem bmsi : bmsis) {
                    em.remove(bmsi);
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "{0}", e.getMessage());
        } finally {
            em.close();
        }
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
