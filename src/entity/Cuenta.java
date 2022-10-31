/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos Rafaelano
 */
@Entity
@Table(name = "cuenta")
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByIdcuenta", query = "SELECT c FROM Cuenta c WHERE c.idcuenta = :idcuenta"),
    @NamedQuery(name = "Cuenta.findByNombrecuenta", query = "SELECT c FROM Cuenta c WHERE c.nombrecuenta = :nombrecuenta")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idcuenta")
    private Integer idcuenta;
    @Basic(optional = false)
    @Column(name = "nombrecuenta")
    private String nombrecuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcuenta")
    private Collection<Transaccion> transaccionCollection;
    @JoinColumn(name = "idsaldo", referencedColumnName = "idsaldo")
    @ManyToOne
    private Saldo idsaldo;
    @JoinColumn(name = "idtipocuenta", referencedColumnName = "idtipocuenta")
    @ManyToOne(optional = false)
    private Tipocuenta idtipocuenta;

    public Cuenta() {
    }

    public Cuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }

    public Cuenta(Integer idcuenta, String nombrecuenta) {
        this.idcuenta = idcuenta;
        this.nombrecuenta = nombrecuenta;
    }

    public Integer getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(Integer idcuenta) {
        this.idcuenta = idcuenta;
    }

    public String getNombrecuenta() {
        return nombrecuenta;
    }

    public void setNombrecuenta(String nombrecuenta) {
        this.nombrecuenta = nombrecuenta;
    }

    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    public Saldo getIdsaldo() {
        return idsaldo;
    }

    public void setIdsaldo(Saldo idsaldo) {
        this.idsaldo = idsaldo;
    }

    public Tipocuenta getIdtipocuenta() {
        return idtipocuenta;
    }

    public void setIdtipocuenta(Tipocuenta idtipocuenta) {
        this.idtipocuenta = idtipocuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcuenta != null ? idcuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.idcuenta == null && other.idcuenta != null) || (this.idcuenta != null && !this.idcuenta.equals(other.idcuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + idcuenta + "  " + nombrecuenta;
    }

}
