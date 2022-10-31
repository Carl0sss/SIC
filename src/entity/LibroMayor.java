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
@Table(name = "libro_mayor")
@NamedQueries({
    @NamedQuery(name = "LibroMayor.findAll", query = "SELECT l FROM LibroMayor l"),
    @NamedQuery(name = "LibroMayor.findByIdLibroMayor", query = "SELECT l FROM LibroMayor l WHERE l.idLibroMayor = :idLibroMayor")})
public class LibroMayor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_libro_mayor")
    private Integer idLibroMayor;
    @OneToMany(mappedBy = "idLibroMayor")
    private Collection<BalanceComprobasion> balanceComprobasionCollection;
    @JoinColumn(name = "id_b_comp", referencedColumnName = "id_b_comp")
    @ManyToOne
    private BalanceComprobasion idBComp;
    @OneToMany(mappedBy = "idLibroMayor")
    private Collection<Partida> partidaCollection;

    public LibroMayor() {
    }

    public LibroMayor(Integer idLibroMayor) {
        this.idLibroMayor = idLibroMayor;
    }

    public Integer getIdLibroMayor() {
        return idLibroMayor;
    }

    public void setIdLibroMayor(Integer idLibroMayor) {
        this.idLibroMayor = idLibroMayor;
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

    public Collection<Partida> getPartidaCollection() {
        return partidaCollection;
    }

    public void setPartidaCollection(Collection<Partida> partidaCollection) {
        this.partidaCollection = partidaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLibroMayor != null ? idLibroMayor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LibroMayor)) {
            return false;
        }
        LibroMayor other = (LibroMayor) object;
        if ((this.idLibroMayor == null && other.idLibroMayor != null) || (this.idLibroMayor != null && !this.idLibroMayor.equals(other.idLibroMayor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LibroMayor[ idLibroMayor=" + idLibroMayor + " ]";
    }
    
}
