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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos Rafaelano
 */
@Entity
@Table(name = "tiposaldo")
@NamedQueries({
    @NamedQuery(name = "Tiposaldo.findAll", query = "SELECT t FROM Tiposaldo t"),
    @NamedQuery(name = "Tiposaldo.findByIdtipocuenta2", query = "SELECT t FROM Tiposaldo t WHERE t.idtipocuenta2 = :idtipocuenta2"),
    @NamedQuery(name = "Tiposaldo.findByNombretiposaldo", query = "SELECT t FROM Tiposaldo t WHERE t.nombretiposaldo = :nombretiposaldo")})
public class Tiposaldo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idtipocuenta2")
    private Integer idtipocuenta2;
    @Basic(optional = false)
    @Column(name = "nombretiposaldo")
    private String nombretiposaldo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipocuenta2")
    private Collection<Saldo> saldoCollection;

    public Tiposaldo() {
    }

    public Tiposaldo(Integer idtipocuenta2) {
        this.idtipocuenta2 = idtipocuenta2;
    }

    public Tiposaldo(Integer idtipocuenta2, String nombretiposaldo) {
        this.idtipocuenta2 = idtipocuenta2;
        this.nombretiposaldo = nombretiposaldo;
    }

    public Integer getIdtipocuenta2() {
        return idtipocuenta2;
    }

    public void setIdtipocuenta2(Integer idtipocuenta2) {
        this.idtipocuenta2 = idtipocuenta2;
    }

    public String getNombretiposaldo() {
        return nombretiposaldo;
    }

    public void setNombretiposaldo(String nombretiposaldo) {
        this.nombretiposaldo = nombretiposaldo;
    }

    public Collection<Saldo> getSaldoCollection() {
        return saldoCollection;
    }

    public void setSaldoCollection(Collection<Saldo> saldoCollection) {
        this.saldoCollection = saldoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocuenta2 != null ? idtipocuenta2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiposaldo)) {
            return false;
        }
        Tiposaldo other = (Tiposaldo) object;
        if ((this.idtipocuenta2 == null && other.idtipocuenta2 != null) || (this.idtipocuenta2 != null && !this.idtipocuenta2.equals(other.idtipocuenta2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tiposaldo[ idtipocuenta2=" + idtipocuenta2 + " ]";
    }
    
}
