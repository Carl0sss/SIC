/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Carlos Rafaelano
 */
@Entity
@Table(name = "transaccion")
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.findByIdtransaccion", query = "SELECT t FROM Transaccion t WHERE t.idtransaccion = :idtransaccion"),
    @NamedQuery(name = "Transaccion.findByMonto", query = "SELECT t FROM Transaccion t WHERE t.monto = :monto")})
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idtransaccion")
    private Integer idtransaccion;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @JoinColumn(name = "idcuenta", referencedColumnName = "idcuenta")
    @ManyToOne(optional = false)
    private Cuenta idcuenta;
    @JoinColumn(name = "idpartida", referencedColumnName = "idpartida")
    @ManyToOne(optional = false)
    private Partida idpartida;
    @JoinColumn(name = "idtipotransaccion", referencedColumnName = "idtipotransaccion")
    @ManyToOne(optional = false)
    private Tipotransaccion idtipotransaccion;

    public Transaccion() {
    }

    public Transaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public Transaccion(Integer idtransaccion, int monto) {
        this.idtransaccion = idtransaccion;
        this.monto = monto;
    }

    public Integer getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Cuenta getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(Cuenta idcuenta) {
        this.idcuenta = idcuenta;
    }

    public Partida getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(Partida idpartida) {
        this.idpartida = idpartida;
    }

    public Tipotransaccion getIdtipotransaccion() {
        return idtipotransaccion;
    }

    public void setIdtipotransaccion(Tipotransaccion idtipotransaccion) {
        this.idtipotransaccion = idtipotransaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccion != null ? idtransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.idtransaccion == null && other.idtransaccion != null) || (this.idtransaccion != null && !this.idtransaccion.equals(other.idtransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Transaccion[ idtransaccion=" + idtransaccion + " ]";
    }
    
}
