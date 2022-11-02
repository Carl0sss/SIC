/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Cuentas;
import entity.Saldo;
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
public class SaldoJpaController implements Serializable {

    public SaldoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public SaldoJpaController() {
    }

    public void create(Saldo saldo) throws PreexistingEntityException, Exception {
        if (saldo.getCuentasCollection() == null) {
            saldo.setCuentasCollection(new ArrayList<Cuentas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cuentas> attachedCuentasCollection = new ArrayList<Cuentas>();
            for (Cuentas cuentasCollectionCuentasToAttach : saldo.getCuentasCollection()) {
                cuentasCollectionCuentasToAttach = em.getReference(cuentasCollectionCuentasToAttach.getClass(), cuentasCollectionCuentasToAttach.getCodCuenta());
                attachedCuentasCollection.add(cuentasCollectionCuentasToAttach);
            }
            saldo.setCuentasCollection(attachedCuentasCollection);
            em.persist(saldo);
            for (Cuentas cuentasCollectionCuentas : saldo.getCuentasCollection()) {
                Saldo oldIdSaldoOfCuentasCollectionCuentas = cuentasCollectionCuentas.getIdSaldo();
                cuentasCollectionCuentas.setIdSaldo(saldo);
                cuentasCollectionCuentas = em.merge(cuentasCollectionCuentas);
                if (oldIdSaldoOfCuentasCollectionCuentas != null) {
                    oldIdSaldoOfCuentasCollectionCuentas.getCuentasCollection().remove(cuentasCollectionCuentas);
                    oldIdSaldoOfCuentasCollectionCuentas = em.merge(oldIdSaldoOfCuentasCollectionCuentas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaldo(saldo.getIdSaldo()) != null) {
                throw new PreexistingEntityException("Saldo " + saldo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Saldo saldo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saldo persistentSaldo = em.find(Saldo.class, saldo.getIdSaldo());
            Collection<Cuentas> cuentasCollectionOld = persistentSaldo.getCuentasCollection();
            Collection<Cuentas> cuentasCollectionNew = saldo.getCuentasCollection();
            List<String> illegalOrphanMessages = null;
            for (Cuentas cuentasCollectionOldCuentas : cuentasCollectionOld) {
                if (!cuentasCollectionNew.contains(cuentasCollectionOldCuentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuentas " + cuentasCollectionOldCuentas + " since its idSaldo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cuentas> attachedCuentasCollectionNew = new ArrayList<Cuentas>();
            for (Cuentas cuentasCollectionNewCuentasToAttach : cuentasCollectionNew) {
                cuentasCollectionNewCuentasToAttach = em.getReference(cuentasCollectionNewCuentasToAttach.getClass(), cuentasCollectionNewCuentasToAttach.getCodCuenta());
                attachedCuentasCollectionNew.add(cuentasCollectionNewCuentasToAttach);
            }
            cuentasCollectionNew = attachedCuentasCollectionNew;
            saldo.setCuentasCollection(cuentasCollectionNew);
            saldo = em.merge(saldo);
            for (Cuentas cuentasCollectionNewCuentas : cuentasCollectionNew) {
                if (!cuentasCollectionOld.contains(cuentasCollectionNewCuentas)) {
                    Saldo oldIdSaldoOfCuentasCollectionNewCuentas = cuentasCollectionNewCuentas.getIdSaldo();
                    cuentasCollectionNewCuentas.setIdSaldo(saldo);
                    cuentasCollectionNewCuentas = em.merge(cuentasCollectionNewCuentas);
                    if (oldIdSaldoOfCuentasCollectionNewCuentas != null && !oldIdSaldoOfCuentasCollectionNewCuentas.equals(saldo)) {
                        oldIdSaldoOfCuentasCollectionNewCuentas.getCuentasCollection().remove(cuentasCollectionNewCuentas);
                        oldIdSaldoOfCuentasCollectionNewCuentas = em.merge(oldIdSaldoOfCuentasCollectionNewCuentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saldo.getIdSaldo();
                if (findSaldo(id) == null) {
                    throw new NonexistentEntityException("The saldo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saldo saldo;
            try {
                saldo = em.getReference(Saldo.class, id);
                saldo.getIdSaldo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saldo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cuentas> cuentasCollectionOrphanCheck = saldo.getCuentasCollection();
            for (Cuentas cuentasCollectionOrphanCheckCuentas : cuentasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Saldo (" + saldo + ") cannot be destroyed since the Cuentas " + cuentasCollectionOrphanCheckCuentas + " in its cuentasCollection field has a non-nullable idSaldo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(saldo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Saldo> findSaldoEntities() {
        return findSaldoEntities(true, -1, -1);
    }

    public List<Saldo> findSaldoEntities(int maxResults, int firstResult) {
        return findSaldoEntities(false, maxResults, firstResult);
    }

    private List<Saldo> findSaldoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Saldo.class));
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

    public Saldo findSaldo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Saldo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSaldoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Saldo> rt = cq.from(Saldo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
