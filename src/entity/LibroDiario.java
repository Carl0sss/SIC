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
@Table(name = "libro_diario")
@NamedQueries({
    @NamedQuery(name = "LibroDiario.findAll", query = "SELECT l FROM LibroDiario l"),
    @NamedQuery(name = "LibroDiario.findByIdLibroDiario", query = "SELECT l FROM LibroDiario l WHERE l.idLibroDiario = :idLibroDiario")})
public class LibroDiario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_libro_diario")
    private Integer idLibroDiario;
    @JoinColumn(name = "id_partida", referencedColumnName = "id_partida")
    @ManyToOne
    private Partida idPartida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLibroDiario")
    private Collection<Partida> partidaCollection;

    public LibroDiario() {
    }

    public LibroDiario(Integer idLibroDiario) {
        this.idLibroDiario = idLibroDiario;
    }

    public Integer getIdLibroDiario() {
        return idLibroDiario;
    }

    public void setIdLibroDiario(Integer idLibroDiario) {
        this.idLibroDiario = idLibroDiario;
    }

    public Partida getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Partida idPartida) {
        this.idPartida = idPartida;
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
        hash += (idLibroDiario != null ? idLibroDiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LibroDiario)) {
            return false;
        }
        LibroDiario other = (LibroDiario) object;
        if ((this.idLibroDiario == null && other.idLibroDiario != null) || (this.idLibroDiario != null && !this.idLibroDiario.equals(other.idLibroDiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LibroDiario[ idLibroDiario=" + idLibroDiario + " ]";
    }
    
}
