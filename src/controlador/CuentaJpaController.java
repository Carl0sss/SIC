/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import entity.Cuenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Saldo;
import entity.Tipocuenta;
import entity.Transaccion;
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
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CuentaJpaController() {
    }

    public void create(Cuenta cuenta) throws PreexistingEntityException, Exception {
        if (cuenta.getTransaccionCollection() == null) {
            cuenta.setTransaccionCollection(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Saldo idsaldo = cuenta.getIdsaldo();
            if (idsaldo != null) {
                idsaldo = em.getReference(idsaldo.getClass(), idsaldo.getIdsaldo());
                cuenta.setIdsaldo(idsaldo);
            }
            Tipocuenta idtipocuenta = cuenta.getIdtipocuenta();
            if (idtipocuenta != null) {
                idtipocuenta = em.getReference(idtipocuenta.getClass(), idtipocuenta.getIdtipocuenta());
                cuenta.setIdtipocuenta(idtipocuenta);
            }
            Collection<Transaccion> attachedTransaccionCollection = new ArrayList<Transaccion>();
            for (Transaccion transaccionCollectionTransaccionToAttach : cuenta.getTransaccionCollection()) {
                transaccionCollectionTransaccionToAttach = em.getReference(transaccionCollectionTransaccionToAttach.getClass(), transaccionCollectionTransaccionToAttach.getIdtransaccion());
                attachedTransaccionCollection.add(transaccionCollectionTransaccionToAttach);
            }
            cuenta.setTransaccionCollection(attachedTransaccionCollection);
            em.persist(cuenta);
            if (idsaldo != null) {
                idsaldo.getCuentaCollection().add(cuenta);
                idsaldo = em.merge(idsaldo);
            }
            if (idtipocuenta != null) {
                idtipocuenta.getCuentaCollection().add(cuenta);
                idtipocuenta = em.merge(idtipocuenta);
            }
            for (Transaccion transaccionCollectionTransaccion : cuenta.getTransaccionCollection()) {
                Cuenta oldIdcuentaOfTransaccionCollectionTransaccion = transaccionCollectionTransaccion.getIdcuenta();
                transaccionCollectionTransaccion.setIdcuenta(cuenta);
                transaccionCollectionTransaccion = em.merge(transaccionCollectionTransaccion);
                if (oldIdcuentaOfTransaccionCollectionTransaccion != null) {
                    oldIdcuentaOfTransaccionCollectionTransaccion.getTransaccionCollection().remove(transaccionCollectionTransaccion);
                    oldIdcuentaOfTransaccionCollectionTransaccion = em.merge(oldIdcuentaOfTransaccionCollectionTransaccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuenta(cuenta.getIdcuenta()) != null) {
                throw new PreexistingEntityException("Cuenta " + cuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getIdcuenta());
            Saldo idsaldoOld = persistentCuenta.getIdsaldo();
            Saldo idsaldoNew = cuenta.getIdsaldo();
            Tipocuenta idtipocuentaOld = persistentCuenta.getIdtipocuenta();
            Tipocuenta idtipocuentaNew = cuenta.getIdtipocuenta();
            Collection<Transaccion> transaccionCollectionOld = persistentCuenta.getTransaccionCollection();
            Collection<Transaccion> transaccionCollectionNew = cuenta.getTransaccionCollection();
            List<String> illegalOrphanMessages = null;
            for (Transaccion transaccionCollectionOldTransaccion : transaccionCollectionOld) {
                if (!transaccionCollectionNew.contains(transaccionCollectionOldTransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccion " + transaccionCollectionOldTransaccion + " since its idcuenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idsaldoNew != null) {
                idsaldoNew = em.getReference(idsaldoNew.getClass(), idsaldoNew.getIdsaldo());
                cuenta.setIdsaldo(idsaldoNew);
            }
            if (idtipocuentaNew != null) {
                idtipocuentaNew = em.getReference(idtipocuentaNew.getClass(), idtipocuentaNew.getIdtipocuenta());
                cuenta.setIdtipocuenta(idtipocuentaNew);
            }
            Collection<Transaccion> attachedTransaccionCollectionNew = new ArrayList<Transaccion>();
            for (Transaccion transaccionCollectionNewTransaccionToAttach : transaccionCollectionNew) {
                transaccionCollectionNewTransaccionToAttach = em.getReference(transaccionCollectionNewTransaccionToAttach.getClass(), transaccionCollectionNewTransaccionToAttach.getIdtransaccion());
                attachedTransaccionCollectionNew.add(transaccionCollectionNewTransaccionToAttach);
            }
            transaccionCollectionNew = attachedTransaccionCollectionNew;
            cuenta.setTransaccionCollection(transaccionCollectionNew);
            cuenta = em.merge(cuenta);
            if (idsaldoOld != null && !idsaldoOld.equals(idsaldoNew)) {
                idsaldoOld.getCuentaCollection().remove(cuenta);
                idsaldoOld = em.merge(idsaldoOld);
            }
            if (idsaldoNew != null && !idsaldoNew.equals(idsaldoOld)) {
                idsaldoNew.getCuentaCollection().add(cuenta);
                idsaldoNew = em.merge(idsaldoNew);
            }
            if (idtipocuentaOld != null && !idtipocuentaOld.equals(idtipocuentaNew)) {
                idtipocuentaOld.getCuentaCollection().remove(cuenta);
                idtipocuentaOld = em.merge(idtipocuentaOld);
            }
            if (idtipocuentaNew != null && !idtipocuentaNew.equals(idtipocuentaOld)) {
                idtipocuentaNew.getCuentaCollection().add(cuenta);
                idtipocuentaNew = em.merge(idtipocuentaNew);
            }
            for (Transaccion transaccionCollectionNewTransaccion : transaccionCollectionNew) {
                if (!transaccionCollectionOld.contains(transaccionCollectionNewTransaccion)) {
                    Cuenta oldIdcuentaOfTransaccionCollectionNewTransaccion = transaccionCollectionNewTransaccion.getIdcuenta();
                    transaccionCollectionNewTransaccion.setIdcuenta(cuenta);
                    transaccionCollectionNewTransaccion = em.merge(transaccionCollectionNewTransaccion);
                    if (oldIdcuentaOfTransaccionCollectionNewTransaccion != null && !oldIdcuentaOfTransaccionCollectionNewTransaccion.equals(cuenta)) {
                        oldIdcuentaOfTransaccionCollectionNewTransaccion.getTransaccionCollection().remove(transaccionCollectionNewTransaccion);
                        oldIdcuentaOfTransaccionCollectionNewTransaccion = em.merge(oldIdcuentaOfTransaccionCollectionNewTransaccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getIdcuenta();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getIdcuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Transaccion> transaccionCollectionOrphanCheck = cuenta.getTransaccionCollection();
            for (Transaccion transaccionCollectionOrphanCheckTransaccion : transaccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Transaccion " + transaccionCollectionOrphanCheckTransaccion + " in its transaccionCollection field has a non-nullable idcuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Saldo idsaldo = cuenta.getIdsaldo();
            if (idsaldo != null) {
                idsaldo.getCuentaCollection().remove(cuenta);
                idsaldo = em.merge(idsaldo);
            }
            Tipocuenta idtipocuenta = cuenta.getIdtipocuenta();
            if (idtipocuenta != null) {
                idtipocuenta.getCuentaCollection().remove(cuenta);
                idtipocuenta = em.merge(idtipocuenta);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
