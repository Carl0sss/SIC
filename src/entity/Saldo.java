/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "saldo")
@NamedQueries({
    @NamedQuery(name = "Saldo.findAll", query = "SELECT s FROM Saldo s"),
    @NamedQuery(name = "Saldo.findByIdsaldo", query = "SELECT s FROM Saldo s WHERE s.idsaldo = :idsaldo"),
    @NamedQuery(name = "Saldo.findByTotalsaldo", query = "SELECT s FROM Saldo s WHERE s.totalsaldo = :totalsaldo")})
public class Saldo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idsaldo")
    private Integer idsaldo;
    @Basic(optional = false)
    @Column(name = "totalsaldo")
    private int totalsaldo;
    @OneToMany(mappedBy = "idsaldo")
    private Collection<Cuenta> cuentaCollection;
    @JoinColumn(name = "idtipocuenta2", referencedColumnName = "idtipocuenta2")
    @ManyToOne(optional = false)
    private Tiposaldo idtipocuenta2;

    public Saldo() {
    }

    public Saldo(Integer idsaldo) {
        this.idsaldo = idsaldo;
    }

    public Saldo(Integer idsaldo, int totalsaldo) {
        this.idsaldo = idsaldo;
        this.totalsaldo = totalsaldo;
    }

    public Integer getIdsaldo() {
        return idsaldo;
    }

    public void setIdsaldo(Integer idsaldo) {
        this.idsaldo = idsaldo;
    }

    public int getTotalsaldo() {
        return totalsaldo;
    }

    public void setTotalsaldo(int totalsaldo) {
        this.totalsaldo = totalsaldo;
    }

    public Collection<Cuenta> getCuentaCollection() {
        return cuentaCollection;
    }

    public void setCuentaCollection(Collection<Cuenta> cuentaCollection) {
        this.cuentaCollection = cuentaCollection;
    }

    public Tiposaldo getIdtipocuenta2() {
        return idtipocuenta2;
    }

    public void setIdtipocuenta2(Tiposaldo idtipocuenta2) {
        this.idtipocuenta2 = idtipocuenta2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsaldo != null ? idsaldo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saldo)) {
            return false;
        }
        Saldo other = (Saldo) object;
        if ((this.idsaldo == null && other.idsaldo != null) || (this.idsaldo != null && !this.idsaldo.equals(other.idsaldo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Saldo[ idsaldo=" + idsaldo + " ]";
    }
    
}
