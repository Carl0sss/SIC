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
import entity.LibroDiario;
import entity.Partida;
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
public class PartidaJpaController implements Serializable {

    public PartidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PartidaJpaController() {
    }

    public void create(Partida partida) throws PreexistingEntityException, Exception {
        if (partida.getTransaccionCollection() == null) {
            partida.setTransaccionCollection(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LibroDiario idlibrodiario = partida.getIdlibrodiario();
            if (idlibrodiario != null) {
                idlibrodiario = em.getReference(idlibrodiario.getClass(), idlibrodiario.getIdlibrodiario());
                partida.setIdlibrodiario(idlibrodiario);
            }
            Collection<Transaccion> attachedTransaccionCollection = new ArrayList<Transaccion>();
            for (Transaccion transaccionCollectionTransaccionToAttach : partida.getTransaccionCollection()) {
                transaccionCollectionTransaccionToAttach = em.getReference(transaccionCollectionTransaccionToAttach.getClass(), transaccionCollectionTransaccionToAttach.getIdtransaccion());
                attachedTransaccionCollection.add(transaccionCollectionTransaccionToAttach);
            }
            partida.setTransaccionCollection(attachedTransaccionCollection);
            em.persist(partida);
            if (idlibrodiario != null) {
                idlibrodiario.getPartidaCollection().add(partida);
                idlibrodiario = em.merge(idlibrodiario);
            }
            for (Transaccion transaccionCollectionTransaccion : partida.getTransaccionCollection()) {
                Partida oldIdpartidaOfTransaccionCollectionTransaccion = transaccionCollectionTransaccion.getIdpartida();
                transaccionCollectionTransaccion.setIdpartida(partida);
                transaccionCollectionTransaccion = em.merge(transaccionCollectionTransaccion);
                if (oldIdpartidaOfTransaccionCollectionTransaccion != null) {
                    oldIdpartidaOfTransaccionCollectionTransaccion.getTransaccionCollection().remove(transaccionCollectionTransaccion);
                    oldIdpartidaOfTransaccionCollectionTransaccion = em.merge(oldIdpartidaOfTransaccionCollectionTransaccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartida(partida.getIdpartida()) != null) {
                throw new PreexistingEntityException("Partida " + partida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partida partida) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partida persistentPartida = em.find(Partida.class, partida.getIdpartida());
            LibroDiario idlibrodiarioOld = persistentPartida.getIdlibrodiario();
            LibroDiario idlibrodiarioNew = partida.getIdlibrodiario();
            Collection<Transaccion> transaccionCollectionOld = persistentPartida.getTransaccionCollection();
            Collection<Transaccion> transaccionCollectionNew = partida.getTransaccionCollection();
            List<String> illegalOrphanMessages = null;
            for (Transaccion transaccionCollectionOldTransaccion : transaccionCollectionOld) {
                if (!transaccionCollectionNew.contains(transaccionCollectionOldTransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccion " + transaccionCollectionOldTransaccion + " since its idpartida field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idlibrodiarioNew != null) {
                idlibrodiarioNew = em.getReference(idlibrodiarioNew.getClass(), idlibrodiarioNew.getIdlibrodiario());
                partida.setIdlibrodiario(idlibrodiarioNew);
            }
            Collection<Transaccion> attachedTransaccionCollectionNew = new ArrayList<Transaccion>();
            for (Transaccion transaccionCollectionNewTransaccionToAttach : transaccionCollectionNew) {
                transaccionCollectionNewTransaccionToAttach = em.getReference(transaccionCollectionNewTransaccionToAttach.getClass(), transaccionCollectionNewTransaccionToAttach.getIdtransaccion());
                attachedTransaccionCollectionNew.add(transaccionCollectionNewTransaccionToAttach);
            }
            transaccionCollectionNew = attachedTransaccionCollectionNew;
            partida.setTransaccionCollection(transaccionCollectionNew);
            partida = em.merge(partida);
            if (idlibrodiarioOld != null && !idlibrodiarioOld.equals(idlibrodiarioNew)) {
                idlibrodiarioOld.getPartidaCollection().remove(partida);
                idlibrodiarioOld = em.merge(idlibrodiarioOld);
            }
            if (idlibrodiarioNew != null && !idlibrodiarioNew.equals(idlibrodiarioOld)) {
                idlibrodiarioNew.getPartidaCollection().add(partida);
                idlibrodiarioNew = em.merge(idlibrodiarioNew);
            }
            for (Transaccion transaccionCollectionNewTransaccion : transaccionCollectionNew) {
                if (!transaccionCollectionOld.contains(transaccionCollectionNewTransaccion)) {
                    Partida oldIdpartidaOfTransaccionCollectionNewTransaccion = transaccionCollectionNewTransaccion.getIdpartida();
                    transaccionCollectionNewTransaccion.setIdpartida(partida);
                    transaccionCollectionNewTransaccion = em.merge(transaccionCollectionNewTransaccion);
                    if (oldIdpartidaOfTransaccionCollectionNewTransaccion != null && !oldIdpartidaOfTransaccionCollectionNewTransaccion.equals(partida)) {
                        oldIdpartidaOfTransaccionCollectionNewTransaccion.getTransaccionCollection().remove(transaccionCollectionNewTransaccion);
                        oldIdpartidaOfTransaccionCollectionNewTransaccion = em.merge(oldIdpartidaOfTransaccionCollectionNewTransaccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partida.getIdpartida();
                if (findPartida(id) == null) {
                    throw new NonexistentEntityException("The partida with id " + id + " no longer exists.");
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
            Partida partida;
            try {
                partida = em.getReference(Partida.class, id);
                partida.getIdpartida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partida with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Transaccion> transaccionCollectionOrphanCheck = partida.getTransaccionCollection();
            for (Transaccion transaccionCollectionOrphanCheckTransaccion : transaccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partida (" + partida + ") cannot be destroyed since the Transaccion " + transaccionCollectionOrphanCheckTransaccion + " in its transaccionCollection field has a non-nullable idpartida field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            LibroDiario idlibrodiario = partida.getIdlibrodiario();
            if (idlibrodiario != null) {
                idlibrodiario.getPartidaCollection().remove(partida);
                idlibrodiario = em.merge(idlibrodiario);
            }
            em.remove(partida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partida> findPartidaEntities() {
        return findPartidaEntities(true, -1, -1);
    }

    public List<Partida> findPartidaEntities(int maxResults, int firstResult) {
        return findPartidaEntities(false, maxResults, firstResult);
    }

    private List<Partida> findPartidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partida.class));
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

    public Partida findPartida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partida.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partida> rt = cq.from(Partida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
