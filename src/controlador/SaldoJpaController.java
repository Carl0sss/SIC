/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Tiposaldo;
import entity.Cuenta;
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
        if (saldo.getCuentaCollection() == null) {
            saldo.setCuentaCollection(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tiposaldo idtipocuenta2 = saldo.getIdtipocuenta2();
            if (idtipocuenta2 != null) {
                idtipocuenta2 = em.getReference(idtipocuenta2.getClass(), idtipocuenta2.getIdtipocuenta2());
                saldo.setIdtipocuenta2(idtipocuenta2);
            }
            Collection<Cuenta> attachedCuentaCollection = new ArrayList<Cuenta>();
            for (Cuenta cuentaCollectionCuentaToAttach : saldo.getCuentaCollection()) {
                cuentaCollectionCuentaToAttach = em.getReference(cuentaCollectionCuentaToAttach.getClass(), cuentaCollectionCuentaToAttach.getIdcuenta());
                attachedCuentaCollection.add(cuentaCollectionCuentaToAttach);
            }
            saldo.setCuentaCollection(attachedCuentaCollection);
            em.persist(saldo);
            if (idtipocuenta2 != null) {
                idtipocuenta2.getSaldoCollection().add(saldo);
                idtipocuenta2 = em.merge(idtipocuenta2);
            }
            for (Cuenta cuentaCollectionCuenta : saldo.getCuentaCollection()) {
                Saldo oldIdsaldoOfCuentaCollectionCuenta = cuentaCollectionCuenta.getIdsaldo();
                cuentaCollectionCuenta.setIdsaldo(saldo);
                cuentaCollectionCuenta = em.merge(cuentaCollectionCuenta);
                if (oldIdsaldoOfCuentaCollectionCuenta != null) {
                    oldIdsaldoOfCuentaCollectionCuenta.getCuentaCollection().remove(cuentaCollectionCuenta);
                    oldIdsaldoOfCuentaCollectionCuenta = em.merge(oldIdsaldoOfCuentaCollectionCuenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSaldo(saldo.getIdsaldo()) != null) {
                throw new PreexistingEntityException("Saldo " + saldo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Saldo saldo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saldo persistentSaldo = em.find(Saldo.class, saldo.getIdsaldo());
            Tiposaldo idtipocuenta2Old = persistentSaldo.getIdtipocuenta2();
            Tiposaldo idtipocuenta2New = saldo.getIdtipocuenta2();
            Collection<Cuenta> cuentaCollectionOld = persistentSaldo.getCuentaCollection();
            Collection<Cuenta> cuentaCollectionNew = saldo.getCuentaCollection();
            if (idtipocuenta2New != null) {
                idtipocuenta2New = em.getReference(idtipocuenta2New.getClass(), idtipocuenta2New.getIdtipocuenta2());
                saldo.setIdtipocuenta2(idtipocuenta2New);
            }
            Collection<Cuenta> attachedCuentaCollectionNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaCollectionNewCuentaToAttach : cuentaCollectionNew) {
                cuentaCollectionNewCuentaToAttach = em.getReference(cuentaCollectionNewCuentaToAttach.getClass(), cuentaCollectionNewCuentaToAttach.getIdcuenta());
                attachedCuentaCollectionNew.add(cuentaCollectionNewCuentaToAttach);
            }
            cuentaCollectionNew = attachedCuentaCollectionNew;
            saldo.setCuentaCollection(cuentaCollectionNew);
            saldo = em.merge(saldo);
            if (idtipocuenta2Old != null && !idtipocuenta2Old.equals(idtipocuenta2New)) {
                idtipocuenta2Old.getSaldoCollection().remove(saldo);
                idtipocuenta2Old = em.merge(idtipocuenta2Old);
            }
            if (idtipocuenta2New != null && !idtipocuenta2New.equals(idtipocuenta2Old)) {
                idtipocuenta2New.getSaldoCollection().add(saldo);
                idtipocuenta2New = em.merge(idtipocuenta2New);
            }
            for (Cuenta cuentaCollectionOldCuenta : cuentaCollectionOld) {
                if (!cuentaCollectionNew.contains(cuentaCollectionOldCuenta)) {
                    cuentaCollectionOldCuenta.setIdsaldo(null);
                    cuentaCollectionOldCuenta = em.merge(cuentaCollectionOldCuenta);
                }
            }
            for (Cuenta cuentaCollectionNewCuenta : cuentaCollectionNew) {
                if (!cuentaCollectionOld.contains(cuentaCollectionNewCuenta)) {
                    Saldo oldIdsaldoOfCuentaCollectionNewCuenta = cuentaCollectionNewCuenta.getIdsaldo();
                    cuentaCollectionNewCuenta.setIdsaldo(saldo);
                    cuentaCollectionNewCuenta = em.merge(cuentaCollectionNewCuenta);
                    if (oldIdsaldoOfCuentaCollectionNewCuenta != null && !oldIdsaldoOfCuentaCollectionNewCuenta.equals(saldo)) {
                        oldIdsaldoOfCuentaCollectionNewCuenta.getCuentaCollection().remove(cuentaCollectionNewCuenta);
                        oldIdsaldoOfCuentaCollectionNewCuenta = em.merge(oldIdsaldoOfCuentaCollectionNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = saldo.getIdsaldo();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saldo saldo;
            try {
                saldo = em.getReference(Saldo.class, id);
                saldo.getIdsaldo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The saldo with id " + id + " no longer exists.", enfe);
            }
            Tiposaldo idtipocuenta2 = saldo.getIdtipocuenta2();
            if (idtipocuenta2 != null) {
                idtipocuenta2.getSaldoCollection().remove(saldo);
                idtipocuenta2 = em.merge(idtipocuenta2);
            }
            Collection<Cuenta> cuentaCollection = saldo.getCuentaCollection();
            for (Cuenta cuentaCollectionCuenta : cuentaCollection) {
                cuentaCollectionCuenta.setIdsaldo(null);
                cuentaCollectionCuenta = em.merge(cuentaCollectionCuenta);
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
