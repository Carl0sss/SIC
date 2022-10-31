/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.BalanceComprobasion;
import entity.BalanceGeneral;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Carlos Rafaelano
 */
public class BalanceGeneralJpaController implements Serializable {

    public BalanceGeneralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public BalanceGeneralJpaController() {
    }

    public void create(BalanceGeneral balanceGeneral) throws PreexistingEntityException, Exception {
        if (balanceGeneral.getBalanceComprobasionCollection() == null) {
            balanceGeneral.setBalanceComprobasionCollection(new ArrayList<BalanceComprobasion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BalanceComprobasion idBComp = balanceGeneral.getIdBComp();
            if (idBComp != null) {
                idBComp = em.getReference(idBComp.getClass(), idBComp.getIdBComp());
                balanceGeneral.setIdBComp(idBComp);
            }
            Collection<BalanceComprobasion> attachedBalanceComprobasionCollection = new ArrayList<BalanceComprobasion>();
            for (BalanceComprobasion balanceComprobasionCollectionBalanceComprobasionToAttach : balanceGeneral.getBalanceComprobasionCollection()) {
                balanceComprobasionCollectionBalanceComprobasionToAttach = em.getReference(balanceComprobasionCollectionBalanceComprobasionToAttach.getClass(), balanceComprobasionCollectionBalanceComprobasionToAttach.getIdBComp());
                attachedBalanceComprobasionCollection.add(balanceComprobasionCollectionBalanceComprobasionToAttach);
            }
            balanceGeneral.setBalanceComprobasionCollection(attachedBalanceComprobasionCollection);
            em.persist(balanceGeneral);
            if (idBComp != null) {
                BalanceGeneral oldIdBGeneralOfIdBComp = idBComp.getIdBGeneral();
                if (oldIdBGeneralOfIdBComp != null) {
                    oldIdBGeneralOfIdBComp.setIdBComp(null);
                    oldIdBGeneralOfIdBComp = em.merge(oldIdBGeneralOfIdBComp);
                }
                idBComp.setIdBGeneral(balanceGeneral);
                idBComp = em.merge(idBComp);
            }
            for (BalanceComprobasion balanceComprobasionCollectionBalanceComprobasion : balanceGeneral.getBalanceComprobasionCollection()) {
                BalanceGeneral oldIdBGeneralOfBalanceComprobasionCollectionBalanceComprobasion = balanceComprobasionCollectionBalanceComprobasion.getIdBGeneral();
                balanceComprobasionCollectionBalanceComprobasion.setIdBGeneral(balanceGeneral);
                balanceComprobasionCollectionBalanceComprobasion = em.merge(balanceComprobasionCollectionBalanceComprobasion);
                if (oldIdBGeneralOfBalanceComprobasionCollectionBalanceComprobasion != null) {
                    oldIdBGeneralOfBalanceComprobasionCollectionBalanceComprobasion.getBalanceComprobasionCollection().remove(balanceComprobasionCollectionBalanceComprobasion);
                    oldIdBGeneralOfBalanceComprobasionCollectionBalanceComprobasion = em.merge(oldIdBGeneralOfBalanceComprobasionCollectionBalanceComprobasion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBalanceGeneral(balanceGeneral.getIdBGeneral()) != null) {
                throw new PreexistingEntityException("BalanceGeneral " + balanceGeneral + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BalanceGeneral balanceGeneral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BalanceGeneral persistentBalanceGeneral = em.find(BalanceGeneral.class, balanceGeneral.getIdBGeneral());
            BalanceComprobasion idBCompOld = persistentBalanceGeneral.getIdBComp();
            BalanceComprobasion idBCompNew = balanceGeneral.getIdBComp();
            Collection<BalanceComprobasion> balanceComprobasionCollectionOld = persistentBalanceGeneral.getBalanceComprobasionCollection();
            Collection<BalanceComprobasion> balanceComprobasionCollectionNew = balanceGeneral.getBalanceComprobasionCollection();
            if (idBCompNew != null) {
                idBCompNew = em.getReference(idBCompNew.getClass(), idBCompNew.getIdBComp());
                balanceGeneral.setIdBComp(idBCompNew);
            }
            Collection<BalanceComprobasion> attachedBalanceComprobasionCollectionNew = new ArrayList<BalanceComprobasion>();
            for (BalanceComprobasion balanceComprobasionCollectionNewBalanceComprobasionToAttach : balanceComprobasionCollectionNew) {
                balanceComprobasionCollectionNewBalanceComprobasionToAttach = em.getReference(balanceComprobasionCollectionNewBalanceComprobasionToAttach.getClass(), balanceComprobasionCollectionNewBalanceComprobasionToAttach.getIdBComp());
                attachedBalanceComprobasionCollectionNew.add(balanceComprobasionCollectionNewBalanceComprobasionToAttach);
            }
            balanceComprobasionCollectionNew = attachedBalanceComprobasionCollectionNew;
            balanceGeneral.setBalanceComprobasionCollection(balanceComprobasionCollectionNew);
            balanceGeneral = em.merge(balanceGeneral);
            if (idBCompOld != null && !idBCompOld.equals(idBCompNew)) {
                idBCompOld.setIdBGeneral(null);
                idBCompOld = em.merge(idBCompOld);
            }
            if (idBCompNew != null && !idBCompNew.equals(idBCompOld)) {
                BalanceGeneral oldIdBGeneralOfIdBComp = idBCompNew.getIdBGeneral();
                if (oldIdBGeneralOfIdBComp != null) {
                    oldIdBGeneralOfIdBComp.setIdBComp(null);
                    oldIdBGeneralOfIdBComp = em.merge(oldIdBGeneralOfIdBComp);
                }
                idBCompNew.setIdBGeneral(balanceGeneral);
                idBCompNew = em.merge(idBCompNew);
            }
            for (BalanceComprobasion balanceComprobasionCollectionOldBalanceComprobasion : balanceComprobasionCollectionOld) {
                if (!balanceComprobasionCollectionNew.contains(balanceComprobasionCollectionOldBalanceComprobasion)) {
                    balanceComprobasionCollectionOldBalanceComprobasion.setIdBGeneral(null);
                    balanceComprobasionCollectionOldBalanceComprobasion = em.merge(balanceComprobasionCollectionOldBalanceComprobasion);
                }
            }
            for (BalanceComprobasion balanceComprobasionCollectionNewBalanceComprobasion : balanceComprobasionCollectionNew) {
                if (!balanceComprobasionCollectionOld.contains(balanceComprobasionCollectionNewBalanceComprobasion)) {
                    BalanceGeneral oldIdBGeneralOfBalanceComprobasionCollectionNewBalanceComprobasion = balanceComprobasionCollectionNewBalanceComprobasion.getIdBGeneral();
                    balanceComprobasionCollectionNewBalanceComprobasion.setIdBGeneral(balanceGeneral);
                    balanceComprobasionCollectionNewBalanceComprobasion = em.merge(balanceComprobasionCollectionNewBalanceComprobasion);
                    if (oldIdBGeneralOfBalanceComprobasionCollectionNewBalanceComprobasion != null && !oldIdBGeneralOfBalanceComprobasionCollectionNewBalanceComprobasion.equals(balanceGeneral)) {
                        oldIdBGeneralOfBalanceComprobasionCollectionNewBalanceComprobasion.getBalanceComprobasionCollection().remove(balanceComprobasionCollectionNewBalanceComprobasion);
                        oldIdBGeneralOfBalanceComprobasionCollectionNewBalanceComprobasion = em.merge(oldIdBGeneralOfBalanceComprobasionCollectionNewBalanceComprobasion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = balanceGeneral.getIdBGeneral();
                if (findBalanceGeneral(id) == null) {
                    throw new NonexistentEntityException("The balanceGeneral with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BalanceGeneral balanceGeneral;
            try {
                balanceGeneral = em.getReference(BalanceGeneral.class, id);
                balanceGeneral.getIdBGeneral();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The balanceGeneral with id " + id + " no longer exists.", enfe);
            }
            BalanceComprobasion idBComp = balanceGeneral.getIdBComp();
            if (idBComp != null) {
                idBComp.setIdBGeneral(null);
                idBComp = em.merge(idBComp);
            }
            Collection<BalanceComprobasion> balanceComprobasionCollection = balanceGeneral.getBalanceComprobasionCollection();
            for (BalanceComprobasion balanceComprobasionCollectionBalanceComprobasion : balanceComprobasionCollection) {
                balanceComprobasionCollectionBalanceComprobasion.setIdBGeneral(null);
                balanceComprobasionCollectionBalanceComprobasion = em.merge(balanceComprobasionCollectionBalanceComprobasion);
            }
            em.remove(balanceGeneral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BalanceGeneral> findBalanceGeneralEntities() {
        return findBalanceGeneralEntities(true, -1, -1);
    }

    public List<BalanceGeneral> findBalanceGeneralEntities(int maxResults, int firstResult) {
        return findBalanceGeneralEntities(false, maxResults, firstResult);
    }

    private List<BalanceGeneral> findBalanceGeneralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BalanceGeneral.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public BalanceGeneral findBalanceGeneral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BalanceGeneral.class, id);
        } finally {
            em.close();
        }
    }

    public int getBalanceGeneralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BalanceGeneral> rt = cq.from(BalanceGeneral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
