/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Cuenta;
import entity.Partida;
import entity.Tipotransaccion;
import entity.Transaccion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Carlos Rafaelano
 */
public class TransaccionJpaController implements Serializable {

    public TransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaContableV1PruebaPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TransaccionJpaController() {
    }

    public void create(Transaccion transaccion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta idcuenta = transaccion.getIdcuenta();
            if (idcuenta != null) {
                idcuenta = em.getReference(idcuenta.getClass(), idcuenta.getIdcuenta());
                transaccion.setIdcuenta(idcuenta);
            }
            Partida idpartida = transaccion.getIdpartida();
            if (idpartida != null) {
                idpartida = em.getReference(idpartida.getClass(), idpartida.getIdpartida());
                transaccion.setIdpartida(idpartida);
            }
            Tipotransaccion idtipotransaccion = transaccion.getIdtipotransaccion();
            if (idtipotransaccion != null) {
                idtipotransaccion = em.getReference(idtipotransaccion.getClass(), idtipotransaccion.getIdtipotransaccion());
                transaccion.setIdtipotransaccion(idtipotransaccion);
            }
            em.persist(transaccion);
            if (idcuenta != null) {
                idcuenta.getTransaccionCollection().add(transaccion);
                idcuenta = em.merge(idcuenta);
            }
            if (idpartida != null) {
                idpartida.getTransaccionCollection().add(transaccion);
                idpartida = em.merge(idpartida);
            }
            if (idtipotransaccion != null) {
                idtipotransaccion.getTransaccionCollection().add(transaccion);
                idtipotransaccion = em.merge(idtipotransaccion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransaccion(transaccion.getIdtransaccion()) != null) {
                throw new PreexistingEntityException("Transaccion " + transaccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaccion transaccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion persistentTransaccion = em.find(Transaccion.class, transaccion.getIdtransaccion());
            Cuenta idcuentaOld = persistentTransaccion.getIdcuenta();
            Cuenta idcuentaNew = transaccion.getIdcuenta();
            Partida idpartidaOld = persistentTransaccion.getIdpartida();
            Partida idpartidaNew = transaccion.getIdpartida();
            Tipotransaccion idtipotransaccionOld = persistentTransaccion.getIdtipotransaccion();
            Tipotransaccion idtipotransaccionNew = transaccion.getIdtipotransaccion();
            if (idcuentaNew != null) {
                idcuentaNew = em.getReference(idcuentaNew.getClass(), idcuentaNew.getIdcuenta());
                transaccion.setIdcuenta(idcuentaNew);
            }
            if (idpartidaNew != null) {
                idpartidaNew = em.getReference(idpartidaNew.getClass(), idpartidaNew.getIdpartida());
                transaccion.setIdpartida(idpartidaNew);
            }
            if (idtipotransaccionNew != null) {
                idtipotransaccionNew = em.getReference(idtipotransaccionNew.getClass(), idtipotransaccionNew.getIdtipotransaccion());
                transaccion.setIdtipotransaccion(idtipotransaccionNew);
            }
            transaccion = em.merge(transaccion);
            if (idcuentaOld != null && !idcuentaOld.equals(idcuentaNew)) {
                idcuentaOld.getTransaccionCollection().remove(transaccion);
                idcuentaOld = em.merge(idcuentaOld);
            }
            if (idcuentaNew != null && !idcuentaNew.equals(idcuentaOld)) {
                idcuentaNew.getTransaccionCollection().add(transaccion);
                idcuentaNew = em.merge(idcuentaNew);
            }
            if (idpartidaOld != null && !idpartidaOld.equals(idpartidaNew)) {
                idpartidaOld.getTransaccionCollection().remove(transaccion);
                idpartidaOld = em.merge(idpartidaOld);
            }
            if (idpartidaNew != null && !idpartidaNew.equals(idpartidaOld)) {
                idpartidaNew.getTransaccionCollection().add(transaccion);
                idpartidaNew = em.merge(idpartidaNew);
            }
            if (idtipotransaccionOld != null && !idtipotransaccionOld.equals(idtipotransaccionNew)) {
                idtipotransaccionOld.getTransaccionCollection().remove(transaccion);
                idtipotransaccionOld = em.merge(idtipotransaccionOld);
            }
            if (idtipotransaccionNew != null && !idtipotransaccionNew.equals(idtipotransaccionOld)) {
                idtipotransaccionNew.getTransaccionCollection().add(transaccion);
                idtipotransaccionNew = em.merge(idtipotransaccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaccion.getIdtransaccion();
                if (findTransaccion(id) == null) {
                    throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.");
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
            Transaccion transaccion;
            try {
                transaccion = em.getReference(Transaccion.class, id);
                transaccion.getIdtransaccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.", enfe);
            }
            Cuenta idcuenta = transaccion.getIdcuenta();
            if (idcuenta != null) {
                idcuenta.getTransaccionCollection().remove(transaccion);
                idcuenta = em.merge(idcuenta);
            }
            Partida idpartida = transaccion.getIdpartida();
            if (idpartida != null) {
                idpartida.getTransaccionCollection().remove(transaccion);
                idpartida = em.merge(idpartida);
            }
            Tipotransaccion idtipotransaccion = transaccion.getIdtipotransaccion();
            if (idtipotransaccion != null) {
                idtipotransaccion.getTransaccionCollection().remove(transaccion);
                idtipotransaccion = em.merge(idtipotransaccion);
            }
            em.remove(transaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaccion> findTransaccionEntities() {
        return findTransaccionEntities(true, -1, -1);
    }

    public List<Transaccion> findTransaccionEntities(int maxResults, int firstResult) {
        return findTransaccionEntities(false, maxResults, firstResult);
    }

    private List<Transaccion> findTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaccion.class));
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

    public Transaccion findTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaccion> rt = cq.from(Transaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
