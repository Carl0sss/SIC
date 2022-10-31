/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Cuenta;
import entity.Tipocuenta;
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
public class TipocuentaJpaController implements Serializable {

    public TipocuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TipocuentaJpaController() {
    }

    public void create(Tipocuenta tipocuenta) throws PreexistingEntityException, Exception {
        if (tipocuenta.getCuentaCollection() == null) {
            tipocuenta.setCuentaCollection(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cuenta> attachedCuentaCollection = new ArrayList<Cuenta>();
            for (Cuenta cuentaCollectionCuentaToAttach : tipocuenta.getCuentaCollection()) {
                cuentaCollectionCuentaToAttach = em.getReference(cuentaCollectionCuentaToAttach.getClass(), cuentaCollectionCuentaToAttach.getIdcuenta());
                attachedCuentaCollection.add(cuentaCollectionCuentaToAttach);
            }
            tipocuenta.setCuentaCollection(attachedCuentaCollection);
            em.persist(tipocuenta);
            for (Cuenta cuentaCollectionCuenta : tipocuenta.getCuentaCollection()) {
                Tipocuenta oldIdtipocuentaOfCuentaCollectionCuenta = cuentaCollectionCuenta.getIdtipocuenta();
                cuentaCollectionCuenta.setIdtipocuenta(tipocuenta);
                cuentaCollectionCuenta = em.merge(cuentaCollectionCuenta);
                if (oldIdtipocuentaOfCuentaCollectionCuenta != null) {
                    oldIdtipocuentaOfCuentaCollectionCuenta.getCuentaCollection().remove(cuentaCollectionCuenta);
                    oldIdtipocuentaOfCuentaCollectionCuenta = em.merge(oldIdtipocuentaOfCuentaCollectionCuenta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipocuenta(tipocuenta.getIdtipocuenta()) != null) {
                throw new PreexistingEntityException("Tipocuenta " + tipocuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipocuenta tipocuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipocuenta persistentTipocuenta = em.find(Tipocuenta.class, tipocuenta.getIdtipocuenta());
            Collection<Cuenta> cuentaCollectionOld = persistentTipocuenta.getCuentaCollection();
            Collection<Cuenta> cuentaCollectionNew = tipocuenta.getCuentaCollection();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaCollectionOldCuenta : cuentaCollectionOld) {
                if (!cuentaCollectionNew.contains(cuentaCollectionOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaCollectionOldCuenta + " since its idtipocuenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cuenta> attachedCuentaCollectionNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaCollectionNewCuentaToAttach : cuentaCollectionNew) {
                cuentaCollectionNewCuentaToAttach = em.getReference(cuentaCollectionNewCuentaToAttach.getClass(), cuentaCollectionNewCuentaToAttach.getIdcuenta());
                attachedCuentaCollectionNew.add(cuentaCollectionNewCuentaToAttach);
            }
            cuentaCollectionNew = attachedCuentaCollectionNew;
            tipocuenta.setCuentaCollection(cuentaCollectionNew);
            tipocuenta = em.merge(tipocuenta);
            for (Cuenta cuentaCollectionNewCuenta : cuentaCollectionNew) {
                if (!cuentaCollectionOld.contains(cuentaCollectionNewCuenta)) {
                    Tipocuenta oldIdtipocuentaOfCuentaCollectionNewCuenta = cuentaCollectionNewCuenta.getIdtipocuenta();
                    cuentaCollectionNewCuenta.setIdtipocuenta(tipocuenta);
                    cuentaCollectionNewCuenta = em.merge(cuentaCollectionNewCuenta);
                    if (oldIdtipocuentaOfCuentaCollectionNewCuenta != null && !oldIdtipocuentaOfCuentaCollectionNewCuenta.equals(tipocuenta)) {
                        oldIdtipocuentaOfCuentaCollectionNewCuenta.getCuentaCollection().remove(cuentaCollectionNewCuenta);
                        oldIdtipocuentaOfCuentaCollectionNewCuenta = em.merge(oldIdtipocuentaOfCuentaCollectionNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipocuenta.getIdtipocuenta();
                if (findTipocuenta(id) == null) {
                    throw new NonexistentEntityException("The tipocuenta with id " + id + " no longer exists.");
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
            Tipocuenta tipocuenta;
            try {
                tipocuenta = em.getReference(Tipocuenta.class, id);
                tipocuenta.getIdtipocuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipocuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cuenta> cuentaCollectionOrphanCheck = tipocuenta.getCuentaCollection();
            for (Cuenta cuentaCollectionOrphanCheckCuenta : cuentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipocuenta (" + tipocuenta + ") cannot be destroyed since the Cuenta " + cuentaCollectionOrphanCheckCuenta + " in its cuentaCollection field has a non-nullable idtipocuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipocuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipocuenta> findTipocuentaEntities() {
        return findTipocuentaEntities(true, -1, -1);
    }

    public List<Tipocuenta> findTipocuentaEntities(int maxResults, int firstResult) {
        return findTipocuentaEntities(false, maxResults, firstResult);
    }

    private List<Tipocuenta> findTipocuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipocuenta.class));
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

    public Tipocuenta findTipocuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipocuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipocuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipocuenta> rt = cq.from(Tipocuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
