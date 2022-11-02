/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.Cuentas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Saldo;
import entity.PartidaDetalle;
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
public class CuentasJpaController implements Serializable {
    
    public CuentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public CuentasJpaController() {
    }
    
    public void create(Cuentas cuentas) throws PreexistingEntityException, Exception {
        if (cuentas.getPartidaDetalleCollection() == null) {
            cuentas.setPartidaDetalleCollection(new ArrayList<PartidaDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saldo idSaldo = cuentas.getIdSaldo();
            if (idSaldo != null) {
                idSaldo = em.getReference(idSaldo.getClass(), idSaldo.getIdSaldo());
                cuentas.setIdSaldo(idSaldo);
            }
            Collection<PartidaDetalle> attachedPartidaDetalleCollection = new ArrayList<PartidaDetalle>();
            for (PartidaDetalle partidaDetalleCollectionPartidaDetalleToAttach : cuentas.getPartidaDetalleCollection()) {
                partidaDetalleCollectionPartidaDetalleToAttach = em.getReference(partidaDetalleCollectionPartidaDetalleToAttach.getClass(), partidaDetalleCollectionPartidaDetalleToAttach.getId());
                attachedPartidaDetalleCollection.add(partidaDetalleCollectionPartidaDetalleToAttach);
            }
            cuentas.setPartidaDetalleCollection(attachedPartidaDetalleCollection);
            em.persist(cuentas);
            if (idSaldo != null) {
                idSaldo.getCuentasCollection().add(cuentas);
                idSaldo = em.merge(idSaldo);
            }
            for (PartidaDetalle partidaDetalleCollectionPartidaDetalle : cuentas.getPartidaDetalleCollection()) {
                Cuentas oldCodCuentaOfPartidaDetalleCollectionPartidaDetalle = partidaDetalleCollectionPartidaDetalle.getCodCuenta();
                partidaDetalleCollectionPartidaDetalle.setCodCuenta(cuentas);
                partidaDetalleCollectionPartidaDetalle = em.merge(partidaDetalleCollectionPartidaDetalle);
                if (oldCodCuentaOfPartidaDetalleCollectionPartidaDetalle != null) {
                    oldCodCuentaOfPartidaDetalleCollectionPartidaDetalle.getPartidaDetalleCollection().remove(partidaDetalleCollectionPartidaDetalle);
                    oldCodCuentaOfPartidaDetalleCollectionPartidaDetalle = em.merge(oldCodCuentaOfPartidaDetalleCollectionPartidaDetalle);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuentas(cuentas.getCodCuenta()) != null) {
                throw new PreexistingEntityException("Cuentas " + cuentas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Cuentas cuentas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuentas persistentCuentas = em.find(Cuentas.class, cuentas.getCodCuenta());
            Saldo idSaldoOld = persistentCuentas.getIdSaldo();
            Saldo idSaldoNew = cuentas.getIdSaldo();
            Collection<PartidaDetalle> partidaDetalleCollectionOld = persistentCuentas.getPartidaDetalleCollection();
            Collection<PartidaDetalle> partidaDetalleCollectionNew = cuentas.getPartidaDetalleCollection();
            if (idSaldoNew != null) {
                idSaldoNew = em.getReference(idSaldoNew.getClass(), idSaldoNew.getIdSaldo());
                cuentas.setIdSaldo(idSaldoNew);
            }
            Collection<PartidaDetalle> attachedPartidaDetalleCollectionNew = new ArrayList<PartidaDetalle>();
            for (PartidaDetalle partidaDetalleCollectionNewPartidaDetalleToAttach : partidaDetalleCollectionNew) {
                partidaDetalleCollectionNewPartidaDetalleToAttach = em.getReference(partidaDetalleCollectionNewPartidaDetalleToAttach.getClass(), partidaDetalleCollectionNewPartidaDetalleToAttach.getId());
                attachedPartidaDetalleCollectionNew.add(partidaDetalleCollectionNewPartidaDetalleToAttach);
            }
            partidaDetalleCollectionNew = attachedPartidaDetalleCollectionNew;
            cuentas.setPartidaDetalleCollection(partidaDetalleCollectionNew);
            cuentas = em.merge(cuentas);
            if (idSaldoOld != null && !idSaldoOld.equals(idSaldoNew)) {
                idSaldoOld.getCuentasCollection().remove(cuentas);
                idSaldoOld = em.merge(idSaldoOld);
            }
            if (idSaldoNew != null && !idSaldoNew.equals(idSaldoOld)) {
                idSaldoNew.getCuentasCollection().add(cuentas);
                idSaldoNew = em.merge(idSaldoNew);
            }
            for (PartidaDetalle partidaDetalleCollectionOldPartidaDetalle : partidaDetalleCollectionOld) {
                if (!partidaDetalleCollectionNew.contains(partidaDetalleCollectionOldPartidaDetalle)) {
                    partidaDetalleCollectionOldPartidaDetalle.setCodCuenta(null);
                    partidaDetalleCollectionOldPartidaDetalle = em.merge(partidaDetalleCollectionOldPartidaDetalle);
                }
            }
            for (PartidaDetalle partidaDetalleCollectionNewPartidaDetalle : partidaDetalleCollectionNew) {
                if (!partidaDetalleCollectionOld.contains(partidaDetalleCollectionNewPartidaDetalle)) {
                    Cuentas oldCodCuentaOfPartidaDetalleCollectionNewPartidaDetalle = partidaDetalleCollectionNewPartidaDetalle.getCodCuenta();
                    partidaDetalleCollectionNewPartidaDetalle.setCodCuenta(cuentas);
                    partidaDetalleCollectionNewPartidaDetalle = em.merge(partidaDetalleCollectionNewPartidaDetalle);
                    if (oldCodCuentaOfPartidaDetalleCollectionNewPartidaDetalle != null && !oldCodCuentaOfPartidaDetalleCollectionNewPartidaDetalle.equals(cuentas)) {
                        oldCodCuentaOfPartidaDetalleCollectionNewPartidaDetalle.getPartidaDetalleCollection().remove(partidaDetalleCollectionNewPartidaDetalle);
                        oldCodCuentaOfPartidaDetalleCollectionNewPartidaDetalle = em.merge(oldCodCuentaOfPartidaDetalleCollectionNewPartidaDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentas.getCodCuenta();
                if (findCuentas(id) == null) {
                    throw new NonexistentEntityException("The cuentas with id " + id + " no longer exists.");
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
            Cuentas cuentas;
            try {
                cuentas = em.getReference(Cuentas.class, id);
                cuentas.getCodCuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentas with id " + id + " no longer exists.", enfe);
            }
            Saldo idSaldo = cuentas.getIdSaldo();
            if (idSaldo != null) {
                idSaldo.getCuentasCollection().remove(cuentas);
                idSaldo = em.merge(idSaldo);
            }
            Collection<PartidaDetalle> partidaDetalleCollection = cuentas.getPartidaDetalleCollection();
            for (PartidaDetalle partidaDetalleCollectionPartidaDetalle : partidaDetalleCollection) {
                partidaDetalleCollectionPartidaDetalle.setCodCuenta(null);
                partidaDetalleCollectionPartidaDetalle = em.merge(partidaDetalleCollectionPartidaDetalle);
            }
            em.remove(cuentas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Cuentas> findCuentasEntities() {
        return findCuentasEntities(true, -1, -1);
    }
    
    public List<Cuentas> findCuentasEntities(int maxResults, int firstResult) {
        return findCuentasEntities(false, maxResults, firstResult);
    }
    
    private List<Cuentas> findCuentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuentas.class));
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
    
    public Cuentas findCuentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuentas.class, id);
        } finally {
            em.close();
        }
    }
    
    public int getCuentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuentas> rt = cq.from(Cuentas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
