/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import entity.LibroDiario;
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
public class LibroDiarioJpaController implements Serializable {

    public LibroDiarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public LibroDiarioJpaController() {
    }

    public void create(LibroDiario libroDiario) throws PreexistingEntityException, Exception {
        if (libroDiario.getPartidaCollection() == null) {
            libroDiario.setPartidaCollection(new ArrayList<Partida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Partida> attachedPartidaCollection = new ArrayList<Partida>();
            for (Partida partidaCollectionPartidaToAttach : libroDiario.getPartidaCollection()) {
                partidaCollectionPartidaToAttach = em.getReference(partidaCollectionPartidaToAttach.getClass(), partidaCollectionPartidaToAttach.getIdpartida());
                attachedPartidaCollection.add(partidaCollectionPartidaToAttach);
            }
            libroDiario.setPartidaCollection(attachedPartidaCollection);
            em.persist(libroDiario);
            for (Partida partidaCollectionPartida : libroDiario.getPartidaCollection()) {
                LibroDiario oldIdlibrodiarioOfPartidaCollectionPartida = partidaCollectionPartida.getIdlibrodiario();
                partidaCollectionPartida.setIdlibrodiario(libroDiario);
                partidaCollectionPartida = em.merge(partidaCollectionPartida);
                if (oldIdlibrodiarioOfPartidaCollectionPartida != null) {
                    oldIdlibrodiarioOfPartidaCollectionPartida.getPartidaCollection().remove(partidaCollectionPartida);
                    oldIdlibrodiarioOfPartidaCollectionPartida = em.merge(oldIdlibrodiarioOfPartidaCollectionPartida);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLibroDiario(libroDiario.getIdlibrodiario()) != null) {
                throw new PreexistingEntityException("LibroDiario " + libroDiario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LibroDiario libroDiario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LibroDiario persistentLibroDiario = em.find(LibroDiario.class, libroDiario.getIdlibrodiario());
            Collection<Partida> partidaCollectionOld = persistentLibroDiario.getPartidaCollection();
            Collection<Partida> partidaCollectionNew = libroDiario.getPartidaCollection();
            List<String> illegalOrphanMessages = null;
            for (Partida partidaCollectionOldPartida : partidaCollectionOld) {
                if (!partidaCollectionNew.contains(partidaCollectionOldPartida)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partida " + partidaCollectionOldPartida + " since its idlibrodiario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Partida> attachedPartidaCollectionNew = new ArrayList<Partida>();
            for (Partida partidaCollectionNewPartidaToAttach : partidaCollectionNew) {
                partidaCollectionNewPartidaToAttach = em.getReference(partidaCollectionNewPartidaToAttach.getClass(), partidaCollectionNewPartidaToAttach.getIdpartida());
                attachedPartidaCollectionNew.add(partidaCollectionNewPartidaToAttach);
            }
            partidaCollectionNew = attachedPartidaCollectionNew;
            libroDiario.setPartidaCollection(partidaCollectionNew);
            libroDiario = em.merge(libroDiario);
            for (Partida partidaCollectionNewPartida : partidaCollectionNew) {
                if (!partidaCollectionOld.contains(partidaCollectionNewPartida)) {
                    LibroDiario oldIdlibrodiarioOfPartidaCollectionNewPartida = partidaCollectionNewPartida.getIdlibrodiario();
                    partidaCollectionNewPartida.setIdlibrodiario(libroDiario);
                    partidaCollectionNewPartida = em.merge(partidaCollectionNewPartida);
                    if (oldIdlibrodiarioOfPartidaCollectionNewPartida != null && !oldIdlibrodiarioOfPartidaCollectionNewPartida.equals(libroDiario)) {
                        oldIdlibrodiarioOfPartidaCollectionNewPartida.getPartidaCollection().remove(partidaCollectionNewPartida);
                        oldIdlibrodiarioOfPartidaCollectionNewPartida = em.merge(oldIdlibrodiarioOfPartidaCollectionNewPartida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = libroDiario.getIdlibrodiario();
                if (findLibroDiario(id) == null) {
                    throw new NonexistentEntityException("The libroDiario with id " + id + " no longer exists.");
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
            LibroDiario libroDiario;
            try {
                libroDiario = em.getReference(LibroDiario.class, id);
                libroDiario.getIdlibrodiario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The libroDiario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Partida> partidaCollectionOrphanCheck = libroDiario.getPartidaCollection();
            for (Partida partidaCollectionOrphanCheckPartida : partidaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This LibroDiario (" + libroDiario + ") cannot be destroyed since the Partida " + partidaCollectionOrphanCheckPartida + " in its partidaCollection field has a non-nullable idlibrodiario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(libroDiario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LibroDiario> findLibroDiarioEntities() {
        return findLibroDiarioEntities(true, -1, -1);
    }

    public List<LibroDiario> findLibroDiarioEntities(int maxResults, int firstResult) {
        return findLibroDiarioEntities(false, maxResults, firstResult);
    }

    private List<LibroDiario> findLibroDiarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LibroDiario.class));
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

    public LibroDiario findLibroDiario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LibroDiario.class, id);
        } finally {
            em.close();
        }
    }

    public int getLibroDiarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LibroDiario> rt = cq.from(LibroDiario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
