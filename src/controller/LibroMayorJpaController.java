/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.BalanceComprobasion;
import entity.LibroMayor;
import java.util.ArrayList;
import java.util.Collection;
import entity.Partida;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Carlos Rafaelano
 */
public class LibroMayorJpaController implements Serializable {

    public LibroMayorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public LibroMayorJpaController() {
    }

    public void create(LibroMayor libroMayor) throws PreexistingEntityException, Exception {
        if (libroMayor.getBalanceComprobasionCollection() == null) {
            libroMayor.setBalanceComprobasionCollection(new ArrayList<BalanceComprobasion>());
        }
        if (libroMayor.getPartidaCollection() == null) {
            libroMayor.setPartidaCollection(new ArrayList<Partida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BalanceComprobasion idBComp = libroMayor.getIdBComp();
            if (idBComp != null) {
                idBComp = em.getReference(idBComp.getClass(), idBComp.getIdBComp());
                libroMayor.setIdBComp(idBComp);
            }
            Collection<BalanceComprobasion> attachedBalanceComprobasionCollection = new ArrayList<BalanceComprobasion>();
            for (BalanceComprobasion balanceComprobasionCollectionBalanceComprobasionToAttach : libroMayor.getBalanceComprobasionCollection()) {
                balanceComprobasionCollectionBalanceComprobasionToAttach = em.getReference(balanceComprobasionCollectionBalanceComprobasionToAttach.getClass(), balanceComprobasionCollectionBalanceComprobasionToAttach.getIdBComp());
                attachedBalanceComprobasionCollection.add(balanceComprobasionCollectionBalanceComprobasionToAttach);
            }
            libroMayor.setBalanceComprobasionCollection(attachedBalanceComprobasionCollection);
            Collection<Partida> attachedPartidaCollection = new ArrayList<Partida>();
            for (Partida partidaCollectionPartidaToAttach : libroMayor.getPartidaCollection()) {
                partidaCollectionPartidaToAttach = em.getReference(partidaCollectionPartidaToAttach.getClass(), partidaCollectionPartidaToAttach.getIdPartida());
                attachedPartidaCollection.add(partidaCollectionPartidaToAttach);
            }
            libroMayor.setPartidaCollection(attachedPartidaCollection);
            em.persist(libroMayor);
            if (idBComp != null) {
                LibroMayor oldIdLibroMayorOfIdBComp = idBComp.getIdLibroMayor();
                if (oldIdLibroMayorOfIdBComp != null) {
                    oldIdLibroMayorOfIdBComp.setIdBComp(null);
                    oldIdLibroMayorOfIdBComp = em.merge(oldIdLibroMayorOfIdBComp);
                }
                idBComp.setIdLibroMayor(libroMayor);
                idBComp = em.merge(idBComp);
            }
            for (BalanceComprobasion balanceComprobasionCollectionBalanceComprobasion : libroMayor.getBalanceComprobasionCollection()) {
                LibroMayor oldIdLibroMayorOfBalanceComprobasionCollectionBalanceComprobasion = balanceComprobasionCollectionBalanceComprobasion.getIdLibroMayor();
                balanceComprobasionCollectionBalanceComprobasion.setIdLibroMayor(libroMayor);
                balanceComprobasionCollectionBalanceComprobasion = em.merge(balanceComprobasionCollectionBalanceComprobasion);
                if (oldIdLibroMayorOfBalanceComprobasionCollectionBalanceComprobasion != null) {
                    oldIdLibroMayorOfBalanceComprobasionCollectionBalanceComprobasion.getBalanceComprobasionCollection().remove(balanceComprobasionCollectionBalanceComprobasion);
                    oldIdLibroMayorOfBalanceComprobasionCollectionBalanceComprobasion = em.merge(oldIdLibroMayorOfBalanceComprobasionCollectionBalanceComprobasion);
                }
            }
            for (Partida partidaCollectionPartida : libroMayor.getPartidaCollection()) {
                LibroMayor oldIdLibroMayorOfPartidaCollectionPartida = partidaCollectionPartida.getIdLibroMayor();
                partidaCollectionPartida.setIdLibroMayor(libroMayor);
                partidaCollectionPartida = em.merge(partidaCollectionPartida);
                if (oldIdLibroMayorOfPartidaCollectionPartida != null) {
                    oldIdLibroMayorOfPartidaCollectionPartida.getPartidaCollection().remove(partidaCollectionPartida);
                    oldIdLibroMayorOfPartidaCollectionPartida = em.merge(oldIdLibroMayorOfPartidaCollectionPartida);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLibroMayor(libroMayor.getIdLibroMayor()) != null) {
                throw new PreexistingEntityException("LibroMayor " + libroMayor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LibroMayor libroMayor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LibroMayor persistentLibroMayor = em.find(LibroMayor.class, libroMayor.getIdLibroMayor());
            BalanceComprobasion idBCompOld = persistentLibroMayor.getIdBComp();
            BalanceComprobasion idBCompNew = libroMayor.getIdBComp();
            Collection<BalanceComprobasion> balanceComprobasionCollectionOld = persistentLibroMayor.getBalanceComprobasionCollection();
            Collection<BalanceComprobasion> balanceComprobasionCollectionNew = libroMayor.getBalanceComprobasionCollection();
            Collection<Partida> partidaCollectionOld = persistentLibroMayor.getPartidaCollection();
            Collection<Partida> partidaCollectionNew = libroMayor.getPartidaCollection();
            if (idBCompNew != null) {
                idBCompNew = em.getReference(idBCompNew.getClass(), idBCompNew.getIdBComp());
                libroMayor.setIdBComp(idBCompNew);
            }
            Collection<BalanceComprobasion> attachedBalanceComprobasionCollectionNew = new ArrayList<BalanceComprobasion>();
            for (BalanceComprobasion balanceComprobasionCollectionNewBalanceComprobasionToAttach : balanceComprobasionCollectionNew) {
                balanceComprobasionCollectionNewBalanceComprobasionToAttach = em.getReference(balanceComprobasionCollectionNewBalanceComprobasionToAttach.getClass(), balanceComprobasionCollectionNewBalanceComprobasionToAttach.getIdBComp());
                attachedBalanceComprobasionCollectionNew.add(balanceComprobasionCollectionNewBalanceComprobasionToAttach);
            }
            balanceComprobasionCollectionNew = attachedBalanceComprobasionCollectionNew;
            libroMayor.setBalanceComprobasionCollection(balanceComprobasionCollectionNew);
            Collection<Partida> attachedPartidaCollectionNew = new ArrayList<Partida>();
            for (Partida partidaCollectionNewPartidaToAttach : partidaCollectionNew) {
                partidaCollectionNewPartidaToAttach = em.getReference(partidaCollectionNewPartidaToAttach.getClass(), partidaCollectionNewPartidaToAttach.getIdPartida());
                attachedPartidaCollectionNew.add(partidaCollectionNewPartidaToAttach);
            }
            partidaCollectionNew = attachedPartidaCollectionNew;
            libroMayor.setPartidaCollection(partidaCollectionNew);
            libroMayor = em.merge(libroMayor);
            if (idBCompOld != null && !idBCompOld.equals(idBCompNew)) {
                idBCompOld.setIdLibroMayor(null);
                idBCompOld = em.merge(idBCompOld);
            }
            if (idBCompNew != null && !idBCompNew.equals(idBCompOld)) {
                LibroMayor oldIdLibroMayorOfIdBComp = idBCompNew.getIdLibroMayor();
                if (oldIdLibroMayorOfIdBComp != null) {
                    oldIdLibroMayorOfIdBComp.setIdBComp(null);
                    oldIdLibroMayorOfIdBComp = em.merge(oldIdLibroMayorOfIdBComp);
                }
                idBCompNew.setIdLibroMayor(libroMayor);
                idBCompNew = em.merge(idBCompNew);
            }
            for (BalanceComprobasion balanceComprobasionCollectionOldBalanceComprobasion : balanceComprobasionCollectionOld) {
                if (!balanceComprobasionCollectionNew.contains(balanceComprobasionCollectionOldBalanceComprobasion)) {
                    balanceComprobasionCollectionOldBalanceComprobasion.setIdLibroMayor(null);
                    balanceComprobasionCollectionOldBalanceComprobasion = em.merge(balanceComprobasionCollectionOldBalanceComprobasion);
                }
            }
            for (BalanceComprobasion balanceComprobasionCollectionNewBalanceComprobasion : balanceComprobasionCollectionNew) {
                if (!balanceComprobasionCollectionOld.contains(balanceComprobasionCollectionNewBalanceComprobasion)) {
                    LibroMayor oldIdLibroMayorOfBalanceComprobasionCollectionNewBalanceComprobasion = balanceComprobasionCollectionNewBalanceComprobasion.getIdLibroMayor();
                    balanceComprobasionCollectionNewBalanceComprobasion.setIdLibroMayor(libroMayor);
                    balanceComprobasionCollectionNewBalanceComprobasion = em.merge(balanceComprobasionCollectionNewBalanceComprobasion);
                    if (oldIdLibroMayorOfBalanceComprobasionCollectionNewBalanceComprobasion != null && !oldIdLibroMayorOfBalanceComprobasionCollectionNewBalanceComprobasion.equals(libroMayor)) {
                        oldIdLibroMayorOfBalanceComprobasionCollectionNewBalanceComprobasion.getBalanceComprobasionCollection().remove(balanceComprobasionCollectionNewBalanceComprobasion);
                        oldIdLibroMayorOfBalanceComprobasionCollectionNewBalanceComprobasion = em.merge(oldIdLibroMayorOfBalanceComprobasionCollectionNewBalanceComprobasion);
                    }
                }
            }
            for (Partida partidaCollectionOldPartida : partidaCollectionOld) {
                if (!partidaCollectionNew.contains(partidaCollectionOldPartida)) {
                    partidaCollectionOldPartida.setIdLibroMayor(null);
                    partidaCollectionOldPartida = em.merge(partidaCollectionOldPartida);
                }
            }
            for (Partida partidaCollectionNewPartida : partidaCollectionNew) {
                if (!partidaCollectionOld.contains(partidaCollectionNewPartida)) {
                    LibroMayor oldIdLibroMayorOfPartidaCollectionNewPartida = partidaCollectionNewPartida.getIdLibroMayor();
                    partidaCollectionNewPartida.setIdLibroMayor(libroMayor);
                    partidaCollectionNewPartida = em.merge(partidaCollectionNewPartida);
                    if (oldIdLibroMayorOfPartidaCollectionNewPartida != null && !oldIdLibroMayorOfPartidaCollectionNewPartida.equals(libroMayor)) {
                        oldIdLibroMayorOfPartidaCollectionNewPartida.getPartidaCollection().remove(partidaCollectionNewPartida);
                        oldIdLibroMayorOfPartidaCollectionNewPartida = em.merge(oldIdLibroMayorOfPartidaCollectionNewPartida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = libroMayor.getIdLibroMayor();
                if (findLibroMayor(id) == null) {
                    throw new NonexistentEntityException("The libroMayor with id " + id + " no longer exists.");
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
            LibroMayor libroMayor;
            try {
                libroMayor = em.getReference(LibroMayor.class, id);
                libroMayor.getIdLibroMayor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The libroMayor with id " + id + " no longer exists.", enfe);
            }
            BalanceComprobasion idBComp = libroMayor.getIdBComp();
            if (idBComp != null) {
                idBComp.setIdLibroMayor(null);
                idBComp = em.merge(idBComp);
            }
            Collection<BalanceComprobasion> balanceComprobasionCollection = libroMayor.getBalanceComprobasionCollection();
            for (BalanceComprobasion balanceComprobasionCollectionBalanceComprobasion : balanceComprobasionCollection) {
                balanceComprobasionCollectionBalanceComprobasion.setIdLibroMayor(null);
                balanceComprobasionCollectionBalanceComprobasion = em.merge(balanceComprobasionCollectionBalanceComprobasion);
            }
            Collection<Partida> partidaCollection = libroMayor.getPartidaCollection();
            for (Partida partidaCollectionPartida : partidaCollection) {
                partidaCollectionPartida.setIdLibroMayor(null);
                partidaCollectionPartida = em.merge(partidaCollectionPartida);
            }
            em.remove(libroMayor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LibroMayor> findLibroMayorEntities() {
        return findLibroMayorEntities(true, -1, -1);
    }

    public List<LibroMayor> findLibroMayorEntities(int maxResults, int firstResult) {
        return findLibroMayorEntities(false, maxResults, firstResult);
    }

    private List<LibroMayor> findLibroMayorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LibroMayor.class));
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

    public LibroMayor findLibroMayor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LibroMayor.class, id);
        } finally {
            em.close();
        }
    }

    public int getLibroMayorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LibroMayor> rt = cq.from(LibroMayor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
