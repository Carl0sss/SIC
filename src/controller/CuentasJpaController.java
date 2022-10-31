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
import entity.Partida;
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
        if (cuentas.getPartidaCollection() == null) {
            cuentas.setPartidaCollection(new ArrayList<Partida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Partida> attachedPartidaCollection = new ArrayList<Partida>();
            for (Partida partidaCollectionPartidaToAttach : cuentas.getPartidaCollection()) {
                partidaCollectionPartidaToAttach = em.getReference(partidaCollectionPartidaToAttach.getClass(), partidaCollectionPartidaToAttach.getIdPartida());
                attachedPartidaCollection.add(partidaCollectionPartidaToAttach);
            }
            cuentas.setPartidaCollection(attachedPartidaCollection);
            em.persist(cuentas);
            for (Partida partidaCollectionPartida : cuentas.getPartidaCollection()) {
                partidaCollectionPartida.getCuentasCollection().add(cuentas);
                partidaCollectionPartida = em.merge(partidaCollectionPartida);
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
            Collection<Partida> partidaCollectionOld = persistentCuentas.getPartidaCollection();
            Collection<Partida> partidaCollectionNew = cuentas.getPartidaCollection();
            Collection<Partida> attachedPartidaCollectionNew = new ArrayList<Partida>();
            for (Partida partidaCollectionNewPartidaToAttach : partidaCollectionNew) {
                partidaCollectionNewPartidaToAttach = em.getReference(partidaCollectionNewPartidaToAttach.getClass(), partidaCollectionNewPartidaToAttach.getIdPartida());
                attachedPartidaCollectionNew.add(partidaCollectionNewPartidaToAttach);
            }
            partidaCollectionNew = attachedPartidaCollectionNew;
            cuentas.setPartidaCollection(partidaCollectionNew);
            cuentas = em.merge(cuentas);
            for (Partida partidaCollectionOldPartida : partidaCollectionOld) {
                if (!partidaCollectionNew.contains(partidaCollectionOldPartida)) {
                    partidaCollectionOldPartida.getCuentasCollection().remove(cuentas);
                    partidaCollectionOldPartida = em.merge(partidaCollectionOldPartida);
                }
            }
            for (Partida partidaCollectionNewPartida : partidaCollectionNew) {
                if (!partidaCollectionOld.contains(partidaCollectionNewPartida)) {
                    partidaCollectionNewPartida.getCuentasCollection().add(cuentas);
                    partidaCollectionNewPartida = em.merge(partidaCollectionNewPartida);
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
            Collection<Partida> partidaCollection = cuentas.getPartidaCollection();
            for (Partida partidaCollectionPartida : partidaCollection) {
                partidaCollectionPartida.getCuentasCollection().remove(cuentas);
                partidaCollectionPartida = em.merge(partidaCollectionPartida);
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
