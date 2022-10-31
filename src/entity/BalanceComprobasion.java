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
@Table(name = "balance_comprobasion")
@NamedQueries({
    @NamedQuery(name = "BalanceComprobasion.findAll", query = "SELECT b FROM BalanceComprobasion b"),
    @NamedQuery(name = "BalanceComprobasion.findByIdBComp", query = "SELECT b FROM BalanceComprobasion b WHERE b.idBComp = :idBComp")})
public class BalanceComprobasion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_b_comp")
    private Integer idBComp;
    @JoinColumn(name = "id_b_general", referencedColumnName = "id_b_general")
    @ManyToOne
    private BalanceGeneral idBGeneral;
    @JoinColumn(name = "id_libro_mayor", referencedColumnName = "id_libro_mayor")
    @ManyToOne
    private LibroMayor idLibroMayor;
    @OneToMany(mappedBy = "idBComp")
    private Collection<BalanceGeneral> balanceGeneralCollection;
    @OneToMany(mappedBy = "idBComp")
    private Collection<LibroMayor> libroMayorCollection;

    public BalanceComprobasion() {
    }

    public BalanceComprobasion(Integer idBComp) {
        this.idBComp = idBComp;
    }

    public Integer getIdBComp() {
        return idBComp;
    }

    public void setIdBComp(Integer idBComp) {
        this.idBComp = idBComp;
    }

    public BalanceGeneral getIdBGeneral() {
        return idBGeneral;
    }

    public void setIdBGeneral(BalanceGeneral idBGeneral) {
        this.idBGeneral = idBGeneral;
    }

    public LibroMayor getIdLibroMayor() {
        return idLibroMayor;
    }

    public void setIdLibroMayor(LibroMayor idLibroMayor) {
        this.idLibroMayor = idLibroMayor;
    }

    public Collection<BalanceGeneral> getBalanceGeneralCollection() {
        return balanceGeneralCollection;
    }

    public void setBalanceGeneralCollection(Collection<BalanceGeneral> balanceGeneralCollection) {
        this.balanceGeneralCollection = balanceGeneralCollection;
    }

    public Collection<LibroMayor> getLibroMayorCollection() {
        return libroMayorCollection;
    }

    public void setLibroMayorCollection(Collection<LibroMayor> libroMayorCollection) {
        this.libroMayorCollection = libroMayorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBComp != null ? idBComp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BalanceComprobasion)) {
            return false;
        }
        BalanceComprobasion other = (BalanceComprobasion) object;
        if ((this.idBComp == null && other.idBComp != null) || (this.idBComp != null && !this.idBComp.equals(other.idBComp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BalanceComprobasion[ idBComp=" + idBComp + " ]";
    }
    
}
