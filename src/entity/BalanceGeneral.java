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
@Table(name = "balance_general")
@NamedQueries({
    @NamedQuery(name = "BalanceGeneral.findAll", query = "SELECT b FROM BalanceGeneral b"),
    @NamedQuery(name = "BalanceGeneral.findByIdBGeneral", query = "SELECT b FROM BalanceGeneral b WHERE b.idBGeneral = :idBGeneral")})
public class BalanceGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_b_general")
    private Integer idBGeneral;
    @OneToMany(mappedBy = "idBGeneral")
    private Collection<BalanceComprobasion> balanceComprobasionCollection;
    @JoinColumn(name = "id_b_comp", referencedColumnName = "id_b_comp")
    @ManyToOne
    private BalanceComprobasion idBComp;

    public BalanceGeneral() {
    }

    public BalanceGeneral(Integer idBGeneral) {
        this.idBGeneral = idBGeneral;
    }

    public Integer getIdBGeneral() {
        return idBGeneral;
    }

    public void setIdBGeneral(Integer idBGeneral) {
        this.idBGeneral = idBGeneral;
    }

    public Collection<BalanceComprobasion> getBalanceComprobasionCollection() {
        return balanceComprobasionCollection;
    }

    public void setBalanceComprobasionCollection(Collection<BalanceComprobasion> balanceComprobasionCollection) {
        this.balanceComprobasionCollection = balanceComprobasionCollection;
    }

    public BalanceComprobasion getIdBComp() {
        return idBComp;
    }

    public void setIdBComp(BalanceComprobasion idBComp) {
        this.idBComp = idBComp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBGeneral != null ? idBGeneral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BalanceGeneral)) {
            return false;
        }
        BalanceGeneral other = (BalanceGeneral) object;
        if ((this.idBGeneral == null && other.idBGeneral != null) || (this.idBGeneral != null && !this.idBGeneral.equals(other.idBGeneral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BalanceGeneral[ idBGeneral=" + idBGeneral + " ]";
    }
    
}
