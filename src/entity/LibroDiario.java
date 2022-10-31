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
@Table(name = "libroDiario")
@NamedQueries({
    @NamedQuery(name = "LibroDiario.findAll", query = "SELECT l FROM LibroDiario l"),
    @NamedQuery(name = "LibroDiario.findByIdlibrodiario", query = "SELECT l FROM LibroDiario l WHERE l.idlibrodiario = :idlibrodiario")})
public class LibroDiario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idlibrodiario")
    private Integer idlibrodiario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idlibrodiario")
    private Collection<Partida> partidaCollection;

    public LibroDiario() {
    }

    public LibroDiario(Integer idlibrodiario) {
        this.idlibrodiario = idlibrodiario;
    }

    public Integer getIdlibrodiario() {
        return idlibrodiario;
    }

    public void setIdlibrodiario(Integer idlibrodiario) {
        this.idlibrodiario = idlibrodiario;
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
        hash += (idlibrodiario != null ? idlibrodiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LibroDiario)) {
            return false;
        }
        LibroDiario other = (LibroDiario) object;
        if ((this.idlibrodiario == null && other.idlibrodiario != null) || (this.idlibrodiario != null && !this.idlibrodiario.equals(other.idlibrodiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LibroDiario[ idlibrodiario=" + idlibrodiario + " ]";
    }
    
}
