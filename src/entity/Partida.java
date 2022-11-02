/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Carlos Rafaelano
 */
@Entity
@Table(name = "partida")
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findByNumPartida", query = "SELECT p FROM Partida p WHERE p.numPartida = :numPartida"),
    @NamedQuery(name = "Partida.findByFecha", query = "SELECT p FROM Partida p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Partida.findByDescripcion", query = "SELECT p FROM Partida p WHERE p.descripcion = :descripcion")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_partida")
    private Integer numPartida;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "numPartida")
    private Collection<PartidaDetalle> partidaDetalleCollection;

    public Partida() {
    }

    public Partida(Integer numPartida) {
        this.numPartida = numPartida;
    }

    public Integer getNumPartida() {
        return numPartida;
    }

    public void setNumPartida(Integer numPartida) {
        this.numPartida = numPartida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<PartidaDetalle> getPartidaDetalleCollection() {
        return partidaDetalleCollection;
    }

    public void setPartidaDetalleCollection(Collection<PartidaDetalle> partidaDetalleCollection) {
        this.partidaDetalleCollection = partidaDetalleCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numPartida != null ? numPartida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.numPartida == null && other.numPartida != null) || (this.numPartida != null && !this.numPartida.equals(other.numPartida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numPartida + "";
    }

}
