/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.LibroDiario;
import entity.LibroMayor;
import entity.Cuentas;
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

    public void create(Partida partida) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (partida.getCuentasCollection() == null) {
            partida.setCuentasCollection(new ArrayList<Cuentas>());
        }
        if (partida.getLibroDiarioCollection() == null) {
            partida.setLibroDiarioCollection(new ArrayList<LibroDiario>());
        }
        List<String> illegalOrphanMessages = null;
        LibroDiario idLibroDiarioOrphanCheck = partida.getIdLibroDiario();
        if (idLibroDiarioOrphanCheck != null) {
            Partida oldIdPartidaOfIdLibroDiario = idLibroDiarioOrphanCheck.getIdPartida();
            if (oldIdPartidaOfIdLibroDiario != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The LibroDiario " + idLibroDiarioOrphanCheck + " already has an item of type Partida whose idLibroDiario column cannot be null. Please make another selection for the idLibroDiario field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LibroDiario idLibroDiario = partida.getIdLibroDiario();
            if (idLibroDiario != null) {
                idLibroDiario = em.getReference(idLibroDiario.getClass(), idLibroDiario.getIdLibroDiario());
                partida.setIdLibroDiario(idLibroDiario);
            }
            LibroMayor idLibroMayor = partida.getIdLibroMayor();
            if (idLibroMayor != null) {
                idLibroMayor = em.getReference(idLibroMayor.getClass(), idLibroMayor.getIdLibroMayor());
                partida.setIdLibroMayor(idLibroMayor);
            }
            Collection<Cuentas> attachedCuentasCollection = new ArrayList<Cuentas>();
            for (Cuentas cuentasCollectionCuentasToAttach : partida.getCuentasCollection()) {
                cuentasCollectionCuentasToAttach = em.getReference(cuentasCollectionCuentasToAttach.getClass(), cuentasCollectionCuentasToAttach.getCodCuenta());
                attachedCuentasCollection.add(cuentasCollectionCuentasToAttach);
            }
            partida.setCuentasCollection(attachedCuentasCollection);
            Collection<LibroDiario> attachedLibroDiarioCollection = new ArrayList<LibroDiario>();
            for (LibroDiario libroDiarioCollectionLibroDiarioToAttach : partida.getLibroDiarioCollection()) {
                libroDiarioCollectionLibroDiarioToAttach = em.getReference(libroDiarioCollectionLibroDiarioToAttach.getClass(), libroDiarioCollectionLibroDiarioToAttach.getIdLibroDiario());
                attachedLibroDiarioCollection.add(libroDiarioCollectionLibroDiarioToAttach);
            }
            partida.setLibroDiarioCollection(attachedLibroDiarioCollection);
            em.persist(partida);
            if (idLibroDiario != null) {
                idLibroDiario.setIdPartida(partida);
                idLibroDiario = em.merge(idLibroDiario);
            }
            if (idLibroMayor != null) {
                idLibroMayor.getPartidaCollection().add(partida);
                idLibroMayor = em.merge(idLibroMayor);
            }
            for (Cuentas cuentasCollectionCuentas : partida.getCuentasCollection()) {
                cuentasCollectionCuentas.getPartidaCollection().add(partida);
                cuentasCollectionCuentas = em.merge(cuentasCollectionCuentas);
            }
            for (LibroDiario libroDiarioCollectionLibroDiario : partida.getLibroDiarioCollection()) {
                Partida oldIdPartidaOfLibroDiarioCollectionLibroDiario = libroDiarioCollectionLibroDiario.getIdPartida();
                libroDiarioCollectionLibroDiario.setIdPartida(partida);
                libroDiarioCollectionLibroDiario = em.merge(libroDiarioCollectionLibroDiario);
                if (oldIdPartidaOfLibroDiarioCollectionLibroDiario != null) {
                    oldIdPartidaOfLibroDiarioCollectionLibroDiario.getLibroDiarioCollection().remove(libroDiarioCollectionLibroDiario);
                    oldIdPartidaOfLibroDiarioCollectionLibroDiario = em.merge(oldIdPartidaOfLibroDiarioCollectionLibroDiario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartida(partida.getIdPartida()) != null) {
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
            Partida persistentPartida = em.find(Partida.class, partida.getIdPartida());
            LibroDiario idLibroDiarioOld = persistentPartida.getIdLibroDiario();
            LibroDiario idLibroDiarioNew = partida.getIdLibroDiario();
            LibroMayor idLibroMayorOld = persistentPartida.getIdLibroMayor();
            LibroMayor idLibroMayorNew = partida.getIdLibroMayor();
            Collection<Cuentas> cuentasCollectionOld = persistentPartida.getCuentasCollection();
            Collection<Cuentas> cuentasCollectionNew = partida.getCuentasCollection();
            Collection<LibroDiario> libroDiarioCollectionOld = persistentPartida.getLibroDiarioCollection();
            Collection<LibroDiario> libroDiarioCollectionNew = partida.getLibroDiarioCollection();
            List<String> illegalOrphanMessages = null;
            if (idLibroDiarioNew != null && !idLibroDiarioNew.equals(idLibroDiarioOld)) {
                Partida oldIdPartidaOfIdLibroDiario = idLibroDiarioNew.getIdPartida();
                if (oldIdPartidaOfIdLibroDiario != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The LibroDiario " + idLibroDiarioNew + " already has an item of type Partida whose idLibroDiario column cannot be null. Please make another selection for the idLibroDiario field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idLibroDiarioNew != null) {
                idLibroDiarioNew = em.getReference(idLibroDiarioNew.getClass(), idLibroDiarioNew.getIdLibroDiario());
                partida.setIdLibroDiario(idLibroDiarioNew);
            }
            if (idLibroMayorNew != null) {
                idLibroMayorNew = em.getReference(idLibroMayorNew.getClass(), idLibroMayorNew.getIdLibroMayor());
                partida.setIdLibroMayor(idLibroMayorNew);
            }
            Collection<Cuentas> attachedCuentasCollectionNew = new ArrayList<Cuentas>();
            for (Cuentas cuentasCollectionNewCuentasToAttach : cuentasCollectionNew) {
                cuentasCollectionNewCuentasToAttach = em.getReference(cuentasCollectionNewCuentasToAttach.getClass(), cuentasCollectionNewCuentasToAttach.getCodCuenta());
                attachedCuentasCollectionNew.add(cuentasCollectionNewCuentasToAttach);
            }
            cuentasCollectionNew = attachedCuentasCollectionNew;
            partida.setCuentasCollection(cuentasCollectionNew);
            Collection<LibroDiario> attachedLibroDiarioCollectionNew = new ArrayList<LibroDiario>();
            for (LibroDiario libroDiarioCollectionNewLibroDiarioToAttach : libroDiarioCollectionNew) {
                libroDiarioCollectionNewLibroDiarioToAttach = em.getReference(libroDiarioCollectionNewLibroDiarioToAttach.getClass(), libroDiarioCollectionNewLibroDiarioToAttach.getIdLibroDiario());
                attachedLibroDiarioCollectionNew.add(libroDiarioCollectionNewLibroDiarioToAttach);
            }
            libroDiarioCollectionNew = attachedLibroDiarioCollectionNew;
            partida.setLibroDiarioCollection(libroDiarioCollectionNew);
            partida = em.merge(partida);
            if (idLibroDiarioOld != null && !idLibroDiarioOld.equals(idLibroDiarioNew)) {
                idLibroDiarioOld.setIdPartida(null);
                idLibroDiarioOld = em.merge(idLibroDiarioOld);
            }
            if (idLibroDiarioNew != null && !idLibroDiarioNew.equals(idLibroDiarioOld)) {
                idLibroDiarioNew.setIdPartida(partida);
                idLibroDiarioNew = em.merge(idLibroDiarioNew);
            }
            if (idLibroMayorOld != null && !idLibroMayorOld.equals(idLibroMayorNew)) {
                idLibroMayorOld.getPartidaCollection().remove(partida);
                idLibroMayorOld = em.merge(idLibroMayorOld);
            }
            if (idLibroMayorNew != null && !idLibroMayorNew.equals(idLibroMayorOld)) {
                idLibroMayorNew.getPartidaCollection().add(partida);
                idLibroMayorNew = em.merge(idLibroMayorNew);
            }
            for (Cuentas cuentasCollectionOldCuentas : cuentasCollectionOld) {
                if (!cuentasCollectionNew.contains(cuentasCollectionOldCuentas)) {
                    cuentasCollectionOldCuentas.getPartidaCollection().remove(partida);
                    cuentasCollectionOldCuentas = em.merge(cuentasCollectionOldCuentas);
                }
            }
            for (Cuentas cuentasCollectionNewCuentas : cuentasCollectionNew) {
                if (!cuentasCollectionOld.contains(cuentasCollectionNewCuentas)) {
                    cuentasCollectionNewCuentas.getPartidaCollection().add(partida);
                    cuentasCollectionNewCuentas = em.merge(cuentasCollectionNewCuentas);
                }
            }
            for (LibroDiario libroDiarioCollectionOldLibroDiario : libroDiarioCollectionOld) {
                if (!libroDiarioCollectionNew.contains(libroDiarioCollectionOldLibroDiario)) {
                    libroDiarioCollectionOldLibroDiario.setIdPartida(null);
                    libroDiarioCollectionOldLibroDiario = em.merge(libroDiarioCollectionOldLibroDiario);
                }
            }
            for (LibroDiario libroDiarioCollectionNewLibroDiario : libroDiarioCollectionNew) {
                if (!libroDiarioCollectionOld.contains(libroDiarioCollectionNewLibroDiario)) {
                    Partida oldIdPartidaOfLibroDiarioCollectionNewLibroDiario = libroDiarioCollectionNewLibroDiario.getIdPartida();
                    libroDiarioCollectionNewLibroDiario.setIdPartida(partida);
                    libroDiarioCollectionNewLibroDiario = em.merge(libroDiarioCollectionNewLibroDiario);
                    if (oldIdPartidaOfLibroDiarioCollectionNewLibroDiario != null && !oldIdPartidaOfLibroDiarioCollectionNewLibroDiario.equals(partida)) {
                        oldIdPartidaOfLibroDiarioCollectionNewLibroDiario.getLibroDiarioCollection().remove(libroDiarioCollectionNewLibroDiario);
                        oldIdPartidaOfLibroDiarioCollectionNewLibroDiario = em.merge(oldIdPartidaOfLibroDiarioCollectionNewLibroDiario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partida.getIdPartida();
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
                partida.getIdPartida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partida with id " + id + " no longer exists.", enfe);
            }
            LibroDiario idLibroDiario = partida.getIdLibroDiario();
            if (idLibroDiario != null) {
                idLibroDiario.setIdPartida(null);
                idLibroDiario = em.merge(idLibroDiario);
            }
            LibroMayor idLibroMayor = partida.getIdLibroMayor();
            if (idLibroMayor != null) {
                idLibroMayor.getPartidaCollection().remove(partida);
                idLibroMayor = em.merge(idLibroMayor);
            }
            Collection<Cuentas> cuentasCollection = partida.getCuentasCollection();
            for (Cuentas cuentasCollectionCuentas : cuentasCollection) {
                cuentasCollectionCuentas.getPartidaCollection().remove(partida);
                cuentasCollectionCuentas = em.merge(cuentasCollectionCuentas);
            }
            Collection<LibroDiario> libroDiarioCollection = partida.getLibroDiarioCollection();
            for (LibroDiario libroDiarioCollectionLibroDiario : libroDiarioCollection) {
                libroDiarioCollectionLibroDiario.setIdPartida(null);
                libroDiarioCollectionLibroDiario = em.merge(libroDiarioCollectionLibroDiario);
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
