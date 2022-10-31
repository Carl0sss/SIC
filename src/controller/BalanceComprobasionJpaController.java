/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.BalanceComprobasion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.BalanceGeneral;
import entity.LibroMayor;
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
public class BalanceComprobasionJpaController implements Serializable {

    public BalanceComprobasionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public BalanceComprobasionJpaController() {
    }

    public void create(BalanceComprobasion balanceComprobasion) throws PreexistingEntityException, Exception {
        if (balanceComprobasion.getBalanceGeneralCollection() == null) {
            balanceComprobasion.setBalanceGeneralCollection(new ArrayList<BalanceGeneral>());
        }
        if (balanceComprobasion.getLibroMayorCollection() == null) {
            balanceComprobasion.setLibroMayorCollection(new ArrayList<LibroMayor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BalanceGeneral idBGeneral = balanceComprobasion.getIdBGeneral();
            if (idBGeneral != null) {
                idBGeneral = em.getReference(idBGeneral.getClass(), idBGeneral.getIdBGeneral());
                balanceComprobasion.setIdBGeneral(idBGeneral);
            }
            LibroMayor idLibroMayor = balanceComprobasion.getIdLibroMayor();
            if (idLibroMayor != null) {
                idLibroMayor = em.getReference(idLibroMayor.getClass(), idLibroMayor.getIdLibroMayor());
                balanceComprobasion.setIdLibroMayor(idLibroMayor);
            }
            Collection<BalanceGeneral> attachedBalanceGeneralCollection = new ArrayList<BalanceGeneral>();
            for (BalanceGeneral balanceGeneralCollectionBalanceGeneralToAttach : balanceComprobasion.getBalanceGeneralCollection()) {
                balanceGeneralCollectionBalanceGeneralToAttach = em.getReference(balanceGeneralCollectionBalanceGeneralToAttach.getClass(), balanceGeneralCollectionBalanceGeneralToAttach.getIdBGeneral());
                attachedBalanceGeneralCollection.add(balanceGeneralCollectionBalanceGeneralToAttach);
            }
            balanceComprobasion.setBalanceGeneralCollection(attachedBalanceGeneralCollection);
            Collection<LibroMayor> attachedLibroMayorCollection = new ArrayList<LibroMayor>();
            for (LibroMayor libroMayorCollectionLibroMayorToAttach : balanceComprobasion.getLibroMayorCollection()) {
                libroMayorCollectionLibroMayorToAttach = em.getReference(libroMayorCollectionLibroMayorToAttach.getClass(), libroMayorCollectionLibroMayorToAttach.getIdLibroMayor());
                attachedLibroMayorCollection.add(libroMayorCollectionLibroMayorToAttach);
            }
            balanceComprobasion.setLibroMayorCollection(attachedLibroMayorCollection);
            em.persist(balanceComprobasion);
            if (idBGeneral != null) {
                idBGeneral.getBalanceComprobasionCollection().add(balanceComprobasion);
                idBGeneral = em.merge(idBGeneral);
            }
            if (idLibroMayor != null) {
                idLibroMayor.getBalanceComprobasionCollection().add(balanceComprobasion);
                idLibroMayor = em.merge(idLibroMayor);
            }
            for (BalanceGeneral balanceGeneralCollectionBalanceGeneral : balanceComprobasion.getBalanceGeneralCollection()) {
                BalanceComprobasion oldIdBCompOfBalanceGeneralCollectionBalanceGeneral = balanceGeneralCollectionBalanceGeneral.getIdBComp();
                balanceGeneralCollectionBalanceGeneral.setIdBComp(balanceComprobasion);
                balanceGeneralCollectionBalanceGeneral = em.merge(balanceGeneralCollectionBalanceGeneral);
                if (oldIdBCompOfBalanceGeneralCollectionBalanceGeneral != null) {
                    oldIdBCompOfBalanceGeneralCollectionBalanceGeneral.getBalanceGeneralCollection().remove(balanceGeneralCollectionBalanceGeneral);
                    oldIdBCompOfBalanceGeneralCollectionBalanceGeneral = em.merge(oldIdBCompOfBalanceGeneralCollectionBalanceGeneral);
                }
            }
            for (LibroMayor libroMayorCollectionLibroMayor : balanceComprobasion.getLibroMayorCollection()) {
                BalanceComprobasion oldIdBCompOfLibroMayorCollectionLibroMayor = libroMayorCollectionLibroMayor.getIdBComp();
                libroMayorCollectionLibroMayor.setIdBComp(balanceComprobasion);
                libroMayorCollectionLibroMayor = em.merge(libroMayorCollectionLibroMayor);
                if (oldIdBCompOfLibroMayorCollectionLibroMayor != null) {
                    oldIdBCompOfLibroMayorCollectionLibroMayor.getLibroMayorCollection().remove(libroMayorCollectionLibroMayor);
                    oldIdBCompOfLibroMayorCollectionLibroMayor = em.merge(oldIdBCompOfLibroMayorCollectionLibroMayor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBalanceComprobasion(balanceComprobasion.getIdBComp()) != null) {
                throw new PreexistingEntityException("BalanceComprobasion " + balanceComprobasion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BalanceComprobasion balanceComprobasion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BalanceComprobasion persistentBalanceComprobasion = em.find(BalanceComprobasion.class, balanceComprobasion.getIdBComp());
            BalanceGeneral idBGeneralOld = persistentBalanceComprobasion.getIdBGeneral();
            BalanceGeneral idBGeneralNew = balanceComprobasion.getIdBGeneral();
            LibroMayor idLibroMayorOld = persistentBalanceComprobasion.getIdLibroMayor();
            LibroMayor idLibroMayorNew = balanceComprobasion.getIdLibroMayor();
            Collection<BalanceGeneral> balanceGeneralCollectionOld = persistentBalanceComprobasion.getBalanceGeneralCollection();
            Collection<BalanceGeneral> balanceGeneralCollectionNew = balanceComprobasion.getBalanceGeneralCollection();
            Collection<LibroMayor> libroMayorCollectionOld = persistentBalanceComprobasion.getLibroMayorCollection();
            Collection<LibroMayor> libroMayorCollectionNew = balanceComprobasion.getLibroMayorCollection();
            if (idBGeneralNew != null) {
                idBGeneralNew = em.getReference(idBGeneralNew.getClass(), idBGeneralNew.getIdBGeneral());
                balanceComprobasion.setIdBGeneral(idBGeneralNew);
            }
            if (idLibroMayorNew != null) {
                idLibroMayorNew = em.getReference(idLibroMayorNew.getClass(), idLibroMayorNew.getIdLibroMayor());
                balanceComprobasion.setIdLibroMayor(idLibroMayorNew);
            }
            Collection<BalanceGeneral> attachedBalanceGeneralCollectionNew = new ArrayList<BalanceGeneral>();
            for (BalanceGeneral balanceGeneralCollectionNewBalanceGeneralToAttach : balanceGeneralCollectionNew) {
                balanceGeneralCollectionNewBalanceGeneralToAttach = em.getReference(balanceGeneralCollectionNewBalanceGeneralToAttach.getClass(), balanceGeneralCollectionNewBalanceGeneralToAttach.getIdBGeneral());
                attachedBalanceGeneralCollectionNew.add(balanceGeneralCollectionNewBalanceGeneralToAttach);
            }
            balanceGeneralCollectionNew = attachedBalanceGeneralCollectionNew;
            balanceComprobasion.setBalanceGeneralCollection(balanceGeneralCollectionNew);
            Collection<LibroMayor> attachedLibroMayorCollectionNew = new ArrayList<LibroMayor>();
            for (LibroMayor libroMayorCollectionNewLibroMayorToAttach : libroMayorCollectionNew) {
                libroMayorCollectionNewLibroMayorToAttach = em.getReference(libroMayorCollectionNewLibroMayorToAttach.getClass(), libroMayorCollectionNewLibroMayorToAttach.getIdLibroMayor());
                attachedLibroMayorCollectionNew.add(libroMayorCollectionNewLibroMayorToAttach);
            }
            libroMayorCollectionNew = attachedLibroMayorCollectionNew;
            balanceComprobasion.setLibroMayorCollection(libroMayorCollectionNew);
            balanceComprobasion = em.merge(balanceComprobasion);
            if (idBGeneralOld != null && !idBGeneralOld.equals(idBGeneralNew)) {
                idBGeneralOld.getBalanceComprobasionCollection().remove(balanceComprobasion);
                idBGeneralOld = em.merge(idBGeneralOld);
            }
            if (idBGeneralNew != null && !idBGeneralNew.equals(idBGeneralOld)) {
                idBGeneralNew.getBalanceComprobasionCollection().add(balanceComprobasion);
                idBGeneralNew = em.merge(idBGeneralNew);
            }
            if (idLibroMayorOld != null && !idLibroMayorOld.equals(idLibroMayorNew)) {
                idLibroMayorOld.getBalanceComprobasionCollection().remove(balanceComprobasion);
                idLibroMayorOld = em.merge(idLibroMayorOld);
            }
            if (idLibroMayorNew != null && !idLibroMayorNew.equals(idLibroMayorOld)) {
                idLibroMayorNew.getBalanceComprobasionCollection().add(balanceComprobasion);
                idLibroMayorNew = em.merge(idLibroMayorNew);
            }
            for (BalanceGeneral balanceGeneralCollectionOldBalanceGeneral : balanceGeneralCollectionOld) {
                if (!balanceGeneralCollectionNew.contains(balanceGeneralCollectionOldBalanceGeneral)) {
                    balanceGeneralCollectionOldBalanceGeneral.setIdBComp(null);
                    balanceGeneralCollectionOldBalanceGeneral = em.merge(balanceGeneralCollectionOldBalanceGeneral);
                }
            }
            for (BalanceGeneral balanceGeneralCollectionNewBalanceGeneral : balanceGeneralCollectionNew) {
                if (!balanceGeneralCollectionOld.contains(balanceGeneralCollectionNewBalanceGeneral)) {
                    BalanceComprobasion oldIdBCompOfBalanceGeneralCollectionNewBalanceGeneral = balanceGeneralCollectionNewBalanceGeneral.getIdBComp();
                    balanceGeneralCollectionNewBalanceGeneral.setIdBComp(balanceComprobasion);
                    balanceGeneralCollectionNewBalanceGeneral = em.merge(balanceGeneralCollectionNewBalanceGeneral);
                    if (oldIdBCompOfBalanceGeneralCollectionNewBalanceGeneral != null && !oldIdBCompOfBalanceGeneralCollectionNewBalanceGeneral.equals(balanceComprobasion)) {
                        oldIdBCompOfBalanceGeneralCollectionNewBalanceGeneral.getBalanceGeneralCollection().remove(balanceGeneralCollectionNewBalanceGeneral);
                        oldIdBCompOfBalanceGeneralCollectionNewBalanceGeneral = em.merge(oldIdBCompOfBalanceGeneralCollectionNewBalanceGeneral);
                    }
                }
            }
            for (LibroMayor libroMayorCollectionOldLibroMayor : libroMayorCollectionOld) {
                if (!libroMayorCollectionNew.contains(libroMayorCollectionOldLibroMayor)) {
                    libroMayorCollectionOldLibroMayor.setIdBComp(null);
                    libroMayorCollectionOldLibroMayor = em.merge(libroMayorCollectionOldLibroMayor);
                }
            }
            for (LibroMayor libroMayorCollectionNewLibroMayor : libroMayorCollectionNew) {
                if (!libroMayorCollectionOld.contains(libroMayorCollectionNewLibroMayor)) {
                    BalanceComprobasion oldIdBCompOfLibroMayorCollectionNewLibroMayor = libroMayorCollectionNewLibroMayor.getIdBComp();
                    libroMayorCollectionNewLibroMayor.setIdBComp(balanceComprobasion);
                    libroMayorCollectionNewLibroMayor = em.merge(libroMayorCollectionNewLibroMayor);
                    if (oldIdBCompOfLibroMayorCollectionNewLibroMayor != null && !oldIdBCompOfLibroMayorCollectionNewLibroMayor.equals(balanceComprobasion)) {
                        oldIdBCompOfLibroMayorCollectionNewLibroMayor.getLibroMayorCollection().remove(libroMayorCollectionNewLibroMayor);
                        oldIdBCompOfLibroMayorCollectionNewLibroMayor = em.merge(oldIdBCompOfLibroMayorCollectionNewLibroMayor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = balanceComprobasion.getIdBComp();
                if (findBalanceComprobasion(id) == null) {
                    throw new NonexistentEntityException("The balanceComprobasion with id " + id + " no longer exists.");
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
            BalanceComprobasion balanceComprobasion;
            try {
                balanceComprobasion = em.getReference(BalanceComprobasion.class, id);
                balanceComprobasion.getIdBComp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The balanceComprobasion with id " + id + " no longer exists.", enfe);
            }
            BalanceGeneral idBGeneral = balanceComprobasion.getIdBGeneral();
            if (idBGeneral != null) {
                idBGeneral.getBalanceComprobasionCollection().remove(balanceComprobasion);
                idBGeneral = em.merge(idBGeneral);
            }
            LibroMayor idLibroMayor = balanceComprobasion.getIdLibroMayor();
            if (idLibroMayor != null) {
                idLibroMayor.getBalanceComprobasionCollection().remove(balanceComprobasion);
                idLibroMayor = em.merge(idLibroMayor);
            }
            Collection<BalanceGeneral> balanceGeneralCollection = balanceComprobasion.getBalanceGeneralCollection();
            for (BalanceGeneral balanceGeneralCollectionBalanceGeneral : balanceGeneralCollection) {
                balanceGeneralCollectionBalanceGeneral.setIdBComp(null);
                balanceGeneralCollectionBalanceGeneral = em.merge(balanceGeneralCollectionBalanceGeneral);
            }
            Collection<LibroMayor> libroMayorCollection = balanceComprobasion.getLibroMayorCollection();
            for (LibroMayor libroMayorCollectionLibroMayor : libroMayorCollection) {
                libroMayorCollectionLibroMayor.setIdBComp(null);
                libroMayorCollectionLibroMayor = em.merge(libroMayorCollectionLibroMayor);
            }
            em.remove(balanceComprobasion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BalanceComprobasion> findBalanceComprobasionEntities() {
        return findBalanceComprobasionEntities(true, -1, -1);
    }

    public List<BalanceComprobasion> findBalanceComprobasionEntities(int maxResults, int firstResult) {
        return findBalanceComprobasionEntities(false, maxResults, firstResult);
    }

    private List<BalanceComprobasion> findBalanceComprobasionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BalanceComprobasion.class));
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

    public BalanceComprobasion findBalanceComprobasion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BalanceComprobasion.class, id);
        } finally {
            em.close();
        }
    }

    public int getBalanceComprobasionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BalanceComprobasion> rt = cq.from(BalanceComprobasion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
