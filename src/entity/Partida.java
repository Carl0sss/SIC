/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
    @NamedQuery(name = "Partida.findByIdpartida", query = "SELECT p FROM Partida p WHERE p.idpartida = :idpartida"),
    @NamedQuery(name = "Partida.findByFechapartida", query = "SELECT p FROM Partida p WHERE p.fechapartida = :fechapartida"),
    @NamedQuery(name = "Partida.findByDescripcionpartida", query = "SELECT p FROM Partida p WHERE p.descripcionpartida = :descripcionpartida")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idpartida")
    private Integer idpartida;
    @Basic(optional = false)
    @Column(name = "fechapartida")
    @Temporal(TemporalType.DATE)
    private Date fechapartida;
    @Basic(optional = false)
    @Column(name = "descripcionpartida")
    private String descripcionpartida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idpartida")
    private Collection<Transaccion> transaccionCollection;
    @JoinColumn(name = "idlibrodiario", referencedColumnName = "idlibrodiario")
    @ManyToOne(optional = false)
    private LibroDiario idlibrodiario;

    public Partida() {
    }

    public Partida(Integer idpartida) {
        this.idpartida = idpartida;
    }

    public Partida(Integer idpartida, Date fechapartida, String descripcionpartida) {
        this.idpartida = idpartida;
        this.fechapartida = fechapartida;
        this.descripcionpartida = descripcionpartida;
    }

    public Integer getIdpartida() {
        return idpartida;
    }

    public void setIdpartida(Integer idpartida) {
        this.idpartida = idpartida;
    }

    public Date getFechapartida() {
        return fechapartida;
    }

    public void setFechapartida(Date fechapartida) {
        this.fechapartida = fechapartida;
    }

    public String getDescripcionpartida() {
        return descripcionpartida;
    }

    public void setDescripcionpartida(String descripcionpartida) {
        this.descripcionpartida = descripcionpartida;
    }

    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    public LibroDiario getIdlibrodiario() {
        return idlibrodiario;
    }

    public void setIdlibrodiario(LibroDiario idlibrodiario) {
        this.idlibrodiario = idlibrodiario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpartida != null ? idpartida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.idpartida == null && other.idpartida != null) || (this.idpartida != null && !this.idpartida.equals(other.idpartida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Partida[ idpartida=" + idpartida + " ]";
    }
    
}
