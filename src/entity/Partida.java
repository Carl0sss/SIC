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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Partida.findByDebe", query = "SELECT p FROM Partida p WHERE p.debe = :debe"),
    @NamedQuery(name = "Partida.findByHaber", query = "SELECT p FROM Partida p WHERE p.haber = :haber"),
    @NamedQuery(name = "Partida.findByFecha", query = "SELECT p FROM Partida p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Partida.findByIdPartida", query = "SELECT p FROM Partida p WHERE p.idPartida = :idPartida")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "debe")
    private Double debe;
    @Column(name = "haber")
    private Double haber;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Id
    @Basic(optional = false)
    @Column(name = "id_partida")
    private Integer idPartida;
    @ManyToMany(mappedBy = "partidaCollection")
    private Collection<Cuentas> cuentasCollection;
    @OneToMany(mappedBy = "idPartida")
    private Collection<LibroDiario> libroDiarioCollection;
    @JoinColumn(name = "id_libro_diario", referencedColumnName = "id_libro_diario")
    @ManyToOne(optional = false)
    private LibroDiario idLibroDiario;
    @JoinColumn(name = "id_libro_mayor", referencedColumnName = "id_libro_mayor")
    @ManyToOne
    private LibroMayor idLibroMayor;

    public Partida() {
    }

    public Partida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Double getDebe() {
        return debe;
    }

    public void setDebe(Double debe) {
        this.debe = debe;
    }

    public Double getHaber() {
        return haber;
    }

    public void setHaber(Double haber) {
        this.haber = haber;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Collection<Cuentas> getCuentasCollection() {
        return cuentasCollection;
    }

    public void setCuentasCollection(Collection<Cuentas> cuentasCollection) {
        this.cuentasCollection = cuentasCollection;
    }

    public Collection<LibroDiario> getLibroDiarioCollection() {
        return libroDiarioCollection;
    }

    public void setLibroDiarioCollection(Collection<LibroDiario> libroDiarioCollection) {
        this.libroDiarioCollection = libroDiarioCollection;
    }

    public LibroDiario getIdLibroDiario() {
        return idLibroDiario;
    }

    public void setIdLibroDiario(LibroDiario idLibroDiario) {
        this.idLibroDiario = idLibroDiario;
    }

    public LibroMayor getIdLibroMayor() {
        return idLibroMayor;
    }

    public void setIdLibroMayor(LibroMayor idLibroMayor) {
        this.idLibroMayor = idLibroMayor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPartida != null ? idPartida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.idPartida == null && other.idPartida != null) || (this.idPartida != null && !this.idPartida.equals(other.idPartida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Partida[ idPartida=" + idPartida + " ]";
    }
    
}
