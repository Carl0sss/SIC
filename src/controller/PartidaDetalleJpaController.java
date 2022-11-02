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
import entity.Cuentas;
import entity.Partida;
import entity.PartidaDetalle;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Carlos Rafaelano
 */
public class PartidaDetalleJpaController implements Serializable {

    public PartidaDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PartidaDetalleJpaController() {
    }

    public void create(PartidaDetalle partidaDetalle) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuentas codCuenta = partidaDetalle.getCodCuenta();
            if (codCuenta != null) {
                codCuenta = em.getReference(codCuenta.getClass(), codCuenta.getCodCuenta());
                partidaDetalle.setCodCuenta(codCuenta);
            }
            Partida numPartida = partidaDetalle.getNumPartida();
            if (numPartida != null) {
                numPartida = em.getReference(numPartida.getClass(), numPartida.getNumPartida());
                partidaDetalle.setNumPartida(numPartida);
            }
            em.persist(partidaDetalle);
            if (codCuenta != null) {
                codCuenta.getPartidaDetalleCollection().add(partidaDetalle);
                codCuenta = em.merge(codCuenta);
            }
            if (numPartida != null) {
                numPartida.getPartidaDetalleCollection().add(partidaDetalle);
                numPartida = em.merge(numPartida);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPartidaDetalle(partidaDetalle.getId()) != null) {
                throw new PreexistingEntityException("PartidaDetalle " + partidaDetalle + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PartidaDetalle partidaDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PartidaDetalle persistentPartidaDetalle = em.find(PartidaDetalle.class, partidaDetalle.getId());
            Cuentas codCuentaOld = persistentPartidaDetalle.getCodCuenta();
            Cuentas codCuentaNew = partidaDetalle.getCodCuenta();
            Partida numPartidaOld = persistentPartidaDetalle.getNumPartida();
            Partida numPartidaNew = partidaDetalle.getNumPartida();
            if (codCuentaNew != null) {
                codCuentaNew = em.getReference(codCuentaNew.getClass(), codCuentaNew.getCodCuenta());
                partidaDetalle.setCodCuenta(codCuentaNew);
            }
            if (numPartidaNew != null) {
                numPartidaNew = em.getReference(numPartidaNew.getClass(), numPartidaNew.getNumPartida());
                partidaDetalle.setNumPartida(numPartidaNew);
            }
            partidaDetalle = em.merge(partidaDetalle);
            if (codCuentaOld != null && !codCuentaOld.equals(codCuentaNew)) {
                codCuentaOld.getPartidaDetalleCollection().remove(partidaDetalle);
                codCuentaOld = em.merge(codCuentaOld);
            }
            if (codCuentaNew != null && !codCuentaNew.equals(codCuentaOld)) {
                codCuentaNew.getPartidaDetalleCollection().add(partidaDetalle);
                codCuentaNew = em.merge(codCuentaNew);
            }
            if (numPartidaOld != null && !numPartidaOld.equals(numPartidaNew)) {
                numPartidaOld.getPartidaDetalleCollection().remove(partidaDetalle);
                numPartidaOld = em.merge(numPartidaOld);
            }
            if (numPartidaNew != null && !numPartidaNew.equals(numPartidaOld)) {
                numPartidaNew.getPartidaDetalleCollection().add(partidaDetalle);
                numPartidaNew = em.merge(numPartidaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partidaDetalle.getId();
                if (findPartidaDetalle(id) == null) {
                    throw new NonexistentEntityException("The partidaDetalle with id " + id + " no longer exists.");
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
            PartidaDetalle partidaDetalle;
            try {
                partidaDetalle = em.getReference(PartidaDetalle.class, id);
                partidaDetalle.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partidaDetalle with id " + id + " no longer exists.", enfe);
            }
            Cuentas codCuenta = partidaDetalle.getCodCuenta();
            if (codCuenta != null) {
                codCuenta.getPartidaDetalleCollection().remove(partidaDetalle);
                codCuenta = em.merge(codCuenta);
            }
            Partida numPartida = partidaDetalle.getNumPartida();
            if (numPartida != null) {
                numPartida.getPartidaDetalleCollection().remove(partidaDetalle);
                numPartida = em.merge(numPartida);
            }
            em.remove(partidaDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PartidaDetalle> findPartidaDetalleEntities() {
        return findPartidaDetalleEntities(true, -1, -1);
    }

    public List<PartidaDetalle> findPartidaDetalleEntities(int maxResults, int firstResult) {
        return findPartidaDetalleEntities(false, maxResults, firstResult);
    }

    private List<PartidaDetalle> findPartidaDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PartidaDetalle.class));
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

    public PartidaDetalle findPartidaDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PartidaDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidaDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PartidaDetalle> rt = cq.from(PartidaDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
