/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import entity.Tipotransaccion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class TipotransaccionJpaController implements Serializable {

    public TipotransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TipotransaccionJpaController() {
    }

    public void create(Tipotransaccion tipotransaccion) throws PreexistingEntityException, Exception {
        if (tipotransaccion.getTransaccionCollection() == null) {
            tipotransaccion.setTransaccionCollection(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Transaccion> attachedTransaccionCollection = new ArrayList<Transaccion>();
            for (Transaccion transaccionCollectionTransaccionToAttach : tipotransaccion.getTransaccionCollection()) {
                transaccionCollectionTransaccionToAttach = em.getReference(transaccionCollectionTransaccionToAttach.getClass(), transaccionCollectionTransaccionToAttach.getIdtransaccion());
                attachedTransaccionCollection.add(transaccionCollectionTransaccionToAttach);
            }
            tipotransaccion.setTransaccionCollection(attachedTransaccionCollection);
            em.persist(tipotransaccion);
            for (Transaccion transaccionCollectionTransaccion : tipotransaccion.getTransaccionCollection()) {
                Tipotransaccion oldIdtipotransaccionOfTransaccionCollectionTransaccion = transaccionCollectionTransaccion.getIdtipotransaccion();
                transaccionCollectionTransaccion.setIdtipotransaccion(tipotransaccion);
                transaccionCollectionTransaccion = em.merge(transaccionCollectionTransaccion);
                if (oldIdtipotransaccionOfTransaccionCollectionTransaccion != null) {
                    oldIdtipotransaccionOfTransaccionCollectionTransaccion.getTransaccionCollection().remove(transaccionCollectionTransaccion);
                    oldIdtipotransaccionOfTransaccionCollectionTransaccion = em.merge(oldIdtipotransaccionOfTransaccionCollectionTransaccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipotransaccion(tipotransaccion.getIdtipotransaccion()) != null) {
                throw new PreexistingEntityException("Tipotransaccion " + tipotransaccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipotransaccion tipotransaccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipotransaccion persistentTipotransaccion = em.find(Tipotransaccion.class, tipotransaccion.getIdtipotransaccion());
            Collection<Transaccion> transaccionCollectionOld = persistentTipotransaccion.getTransaccionCollection();
            Collection<Transaccion> transaccionCollectionNew = tipotransaccion.getTransaccionCollection();
            List<String> illegalOrphanMessages = null;
            for (Transaccion transaccionCollectionOldTransaccion : transaccionCollectionOld) {
                if (!transaccionCollectionNew.contains(transaccionCollectionOldTransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccion " + transaccionCollectionOldTransaccion + " since its idtipotransaccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Transaccion> attachedTransaccionCollectionNew = new ArrayList<Transaccion>();
            for (Transaccion transaccionCollectionNewTransaccionToAttach : transaccionCollectionNew) {
                transaccionCollectionNewTransaccionToAttach = em.getReference(transaccionCollectionNewTransaccionToAttach.getClass(), transaccionCollectionNewTransaccionToAttach.getIdtransaccion());
                attachedTransaccionCollectionNew.add(transaccionCollectionNewTransaccionToAttach);
            }
            transaccionCollectionNew = attachedTransaccionCollectionNew;
            tipotransaccion.setTransaccionCollection(transaccionCollectionNew);
            tipotransaccion = em.merge(tipotransaccion);
            for (Transaccion transaccionCollectionNewTransaccion : transaccionCollectionNew) {
                if (!transaccionCollectionOld.contains(transaccionCollectionNewTransaccion)) {
                    Tipotransaccion oldIdtipotransaccionOfTransaccionCollectionNewTransaccion = transaccionCollectionNewTransaccion.getIdtipotransaccion();
                    transaccionCollectionNewTransaccion.setIdtipotransaccion(tipotransaccion);
                    transaccionCollectionNewTransaccion = em.merge(transaccionCollectionNewTransaccion);
                    if (oldIdtipotransaccionOfTransaccionCollectionNewTransaccion != null && !oldIdtipotransaccionOfTransaccionCollectionNewTransaccion.equals(tipotransaccion)) {
                        oldIdtipotransaccionOfTransaccionCollectionNewTransaccion.getTransaccionCollection().remove(transaccionCollectionNewTransaccion);
                        oldIdtipotransaccionOfTransaccionCollectionNewTransaccion = em.merge(oldIdtipotransaccionOfTransaccionCollectionNewTransaccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipotransaccion.getIdtipotransaccion();
                if (findTipotransaccion(id) == null) {
                    throw new NonexistentEntityException("The tipotransaccion with id " + id + " no longer exists.");
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
            Tipotransaccion tipotransaccion;
            try {
                tipotransaccion = em.getReference(Tipotransaccion.class, id);
                tipotransaccion.getIdtipotransaccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipotransaccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Transaccion> transaccionCollectionOrphanCheck = tipotransaccion.getTransaccionCollection();
            for (Transaccion transaccionCollectionOrphanCheckTransaccion : transaccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipotransaccion (" + tipotransaccion + ") cannot be destroyed since the Transaccion " + transaccionCollectionOrphanCheckTransaccion + " in its transaccionCollection field has a non-nullable idtipotransaccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipotransaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipotransaccion> findTipotransaccionEntities() {
        return findTipotransaccionEntities(true, -1, -1);
    }

    public List<Tipotransaccion> findTipotransaccionEntities(int maxResults, int firstResult) {
        return findTipotransaccionEntities(false, maxResults, firstResult);
    }

    private List<Tipotransaccion> findTipotransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipotransaccion.class));
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

    public Tipotransaccion findTipotransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipotransaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipotransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipotransaccion> rt = cq.from(Tipotransaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
