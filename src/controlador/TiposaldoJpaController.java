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
import entity.Saldo;
import entity.Tiposaldo;
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
public class TiposaldoJpaController implements Serializable {

    public TiposaldoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TiposaldoJpaController() {
    }

    public void create(Tiposaldo tiposaldo) throws PreexistingEntityException, Exception {
        if (tiposaldo.getSaldoCollection() == null) {
            tiposaldo.setSaldoCollection(new ArrayList<Saldo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Saldo> attachedSaldoCollection = new ArrayList<Saldo>();
            for (Saldo saldoCollectionSaldoToAttach : tiposaldo.getSaldoCollection()) {
                saldoCollectionSaldoToAttach = em.getReference(saldoCollectionSaldoToAttach.getClass(), saldoCollectionSaldoToAttach.getIdsaldo());
                attachedSaldoCollection.add(saldoCollectionSaldoToAttach);
            }
            tiposaldo.setSaldoCollection(attachedSaldoCollection);
            em.persist(tiposaldo);
            for (Saldo saldoCollectionSaldo : tiposaldo.getSaldoCollection()) {
                Tiposaldo oldIdtipocuenta2OfSaldoCollectionSaldo = saldoCollectionSaldo.getIdtipocuenta2();
                saldoCollectionSaldo.setIdtipocuenta2(tiposaldo);
                saldoCollectionSaldo = em.merge(saldoCollectionSaldo);
                if (oldIdtipocuenta2OfSaldoCollectionSaldo != null) {
                    oldIdtipocuenta2OfSaldoCollectionSaldo.getSaldoCollection().remove(saldoCollectionSaldo);
                    oldIdtipocuenta2OfSaldoCollectionSaldo = em.merge(oldIdtipocuenta2OfSaldoCollectionSaldo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiposaldo(tiposaldo.getIdtipocuenta2()) != null) {
                throw new PreexistingEntityException("Tiposaldo " + tiposaldo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tiposaldo tiposaldo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tiposaldo persistentTiposaldo = em.find(Tiposaldo.class, tiposaldo.getIdtipocuenta2());
            Collection<Saldo> saldoCollectionOld = persistentTiposaldo.getSaldoCollection();
            Collection<Saldo> saldoCollectionNew = tiposaldo.getSaldoCollection();
            List<String> illegalOrphanMessages = null;
            for (Saldo saldoCollectionOldSaldo : saldoCollectionOld) {
                if (!saldoCollectionNew.contains(saldoCollectionOldSaldo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Saldo " + saldoCollectionOldSaldo + " since its idtipocuenta2 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Saldo> attachedSaldoCollectionNew = new ArrayList<Saldo>();
            for (Saldo saldoCollectionNewSaldoToAttach : saldoCollectionNew) {
                saldoCollectionNewSaldoToAttach = em.getReference(saldoCollectionNewSaldoToAttach.getClass(), saldoCollectionNewSaldoToAttach.getIdsaldo());
                attachedSaldoCollectionNew.add(saldoCollectionNewSaldoToAttach);
            }
            saldoCollectionNew = attachedSaldoCollectionNew;
            tiposaldo.setSaldoCollection(saldoCollectionNew);
            tiposaldo = em.merge(tiposaldo);
            for (Saldo saldoCollectionNewSaldo : saldoCollectionNew) {
                if (!saldoCollectionOld.contains(saldoCollectionNewSaldo)) {
                    Tiposaldo oldIdtipocuenta2OfSaldoCollectionNewSaldo = saldoCollectionNewSaldo.getIdtipocuenta2();
                    saldoCollectionNewSaldo.setIdtipocuenta2(tiposaldo);
                    saldoCollectionNewSaldo = em.merge(saldoCollectionNewSaldo);
                    if (oldIdtipocuenta2OfSaldoCollectionNewSaldo != null && !oldIdtipocuenta2OfSaldoCollectionNewSaldo.equals(tiposaldo)) {
                        oldIdtipocuenta2OfSaldoCollectionNewSaldo.getSaldoCollection().remove(saldoCollectionNewSaldo);
                        oldIdtipocuenta2OfSaldoCollectionNewSaldo = em.merge(oldIdtipocuenta2OfSaldoCollectionNewSaldo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiposaldo.getIdtipocuenta2();
                if (findTiposaldo(id) == null) {
                    throw new NonexistentEntityException("The tiposaldo with id " + id + " no longer exists.");
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
            Tiposaldo tiposaldo;
            try {
                tiposaldo = em.getReference(Tiposaldo.class, id);
                tiposaldo.getIdtipocuenta2();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposaldo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Saldo> saldoCollectionOrphanCheck = tiposaldo.getSaldoCollection();
            for (Saldo saldoCollectionOrphanCheckSaldo : saldoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tiposaldo (" + tiposaldo + ") cannot be destroyed since the Saldo " + saldoCollectionOrphanCheckSaldo + " in its saldoCollection field has a non-nullable idtipocuenta2 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tiposaldo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tiposaldo> findTiposaldoEntities() {
        return findTiposaldoEntities(true, -1, -1);
    }

    public List<Tiposaldo> findTiposaldoEntities(int maxResults, int firstResult) {
        return findTiposaldoEntities(false, maxResults, firstResult);
    }

    private List<Tiposaldo> findTiposaldoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tiposaldo.class));
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

    public Tiposaldo findTiposaldo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tiposaldo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiposaldoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tiposaldo> rt = cq.from(Tiposaldo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
