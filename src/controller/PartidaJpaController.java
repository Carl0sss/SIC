/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import entity.Partida;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    public void create(Partida partida) {
        if (partida.getPartidaDetalleCollection() == null) {
            partida.setPartidaDetalleCollection(new ArrayList<PartidaDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PartidaDetalle> attachedPartidaDetalleCollection = new ArrayList<PartidaDetalle>();
            for (PartidaDetalle partidaDetalleCollectionPartidaDetalleToAttach : partida.getPartidaDetalleCollection()) {
                partidaDetalleCollectionPartidaDetalleToAttach = em.getReference(partidaDetalleCollectionPartidaDetalleToAttach.getClass(), partidaDetalleCollectionPartidaDetalleToAttach.getId());
                attachedPartidaDetalleCollection.add(partidaDetalleCollectionPartidaDetalleToAttach);
            }
            partida.setPartidaDetalleCollection(attachedPartidaDetalleCollection);
            em.persist(partida);
            for (PartidaDetalle partidaDetalleCollectionPartidaDetalle : partida.getPartidaDetalleCollection()) {
                Partida oldNumPartidaOfPartidaDetalleCollectionPartidaDetalle = partidaDetalleCollectionPartidaDetalle.getNumPartida();
                partidaDetalleCollectionPartidaDetalle.setNumPartida(partida);
                partidaDetalleCollectionPartidaDetalle = em.merge(partidaDetalleCollectionPartidaDetalle);
                if (oldNumPartidaOfPartidaDetalleCollectionPartidaDetalle != null) {
                    oldNumPartidaOfPartidaDetalleCollectionPartidaDetalle.getPartidaDetalleCollection().remove(partidaDetalleCollectionPartidaDetalle);
                    oldNumPartidaOfPartidaDetalleCollectionPartidaDetalle = em.merge(oldNumPartidaOfPartidaDetalleCollectionPartidaDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partida partida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partida persistentPartida = em.find(Partida.class, partida.getNumPartida());
            Collection<PartidaDetalle> partidaDetalleCollectionOld = persistentPartida.getPartidaDetalleCollection();
            Collection<PartidaDetalle> partidaDetalleCollectionNew = partida.getPartidaDetalleCollection();
            Collection<PartidaDetalle> attachedPartidaDetalleCollectionNew = new ArrayList<PartidaDetalle>();
            for (PartidaDetalle partidaDetalleCollectionNewPartidaDetalleToAttach : partidaDetalleCollectionNew) {
                partidaDetalleCollectionNewPartidaDetalleToAttach = em.getReference(partidaDetalleCollectionNewPartidaDetalleToAttach.getClass(), partidaDetalleCollectionNewPartidaDetalleToAttach.getId());
                attachedPartidaDetalleCollectionNew.add(partidaDetalleCollectionNewPartidaDetalleToAttach);
            }
            partidaDetalleCollectionNew = attachedPartidaDetalleCollectionNew;
            partida.setPartidaDetalleCollection(partidaDetalleCollectionNew);
            partida = em.merge(partida);
            for (PartidaDetalle partidaDetalleCollectionOldPartidaDetalle : partidaDetalleCollectionOld) {
                if (!partidaDetalleCollectionNew.contains(partidaDetalleCollectionOldPartidaDetalle)) {
                    partidaDetalleCollectionOldPartidaDetalle.setNumPartida(null);
                    partidaDetalleCollectionOldPartidaDetalle = em.merge(partidaDetalleCollectionOldPartidaDetalle);
                }
            }
            for (PartidaDetalle partidaDetalleCollectionNewPartidaDetalle : partidaDetalleCollectionNew) {
                if (!partidaDetalleCollectionOld.contains(partidaDetalleCollectionNewPartidaDetalle)) {
                    Partida oldNumPartidaOfPartidaDetalleCollectionNewPartidaDetalle = partidaDetalleCollectionNewPartidaDetalle.getNumPartida();
                    partidaDetalleCollectionNewPartidaDetalle.setNumPartida(partida);
                    partidaDetalleCollectionNewPartidaDetalle = em.merge(partidaDetalleCollectionNewPartidaDetalle);
                    if (oldNumPartidaOfPartidaDetalleCollectionNewPartidaDetalle != null && !oldNumPartidaOfPartidaDetalleCollectionNewPartidaDetalle.equals(partida)) {
                        oldNumPartidaOfPartidaDetalleCollectionNewPartidaDetalle.getPartidaDetalleCollection().remove(partidaDetalleCollectionNewPartidaDetalle);
                        oldNumPartidaOfPartidaDetalleCollectionNewPartidaDetalle = em.merge(oldNumPartidaOfPartidaDetalleCollectionNewPartidaDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partida.getNumPartida();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partida partida;
            try {
                partida = em.getReference(Partida.class, id);
                partida.getNumPartida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partida with id " + id + " no longer exists.", enfe);
            }
            Collection<PartidaDetalle> partidaDetalleCollection = partida.getPartidaDetalleCollection();
            for (PartidaDetalle partidaDetalleCollectionPartidaDetalle : partidaDetalleCollection) {
                partidaDetalleCollectionPartidaDetalle.setNumPartida(null);
                partidaDetalleCollectionPartidaDetalle = em.merge(partidaDetalleCollectionPartidaDetalle);
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
