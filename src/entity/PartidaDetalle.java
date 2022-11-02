/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Carlos Rafaelano
 */
@Entity
@Table(name = "partida_detalle")
@NamedQueries({
    @NamedQuery(name = "PartidaDetalle.findAll", query = "SELECT p FROM PartidaDetalle p"),
    @NamedQuery(name = "PartidaDetalle.findByDebe", query = "SELECT p FROM PartidaDetalle p WHERE p.debe = :debe"),
    @NamedQuery(name = "PartidaDetalle.findByHaber", query = "SELECT p FROM PartidaDetalle p WHERE p.haber = :haber"),
    @NamedQuery(name = "PartidaDetalle.findById", query = "SELECT p FROM PartidaDetalle p WHERE p.id = :id")})
public class PartidaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "debe")
    private Double debe;
    @Column(name = "haber")
    private Double haber;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "cod_cuenta", referencedColumnName = "cod_cuenta")
    @ManyToOne
    private Cuentas codCuenta;
    @JoinColumn(name = "num_partida", referencedColumnName = "num_partida")
    @ManyToOne
    private Partida numPartida;

    public PartidaDetalle() {
    }

    public PartidaDetalle(Integer id) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cuentas getCodCuenta() {
        return codCuenta;
    }

    public void setCodCuenta(Cuentas codCuenta) {
        this.codCuenta = codCuenta;
    }

    public Partida getNumPartida() {
        return numPartida;
    }

    public void setNumPartida(Partida numPartida) {
        this.numPartida = numPartida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartidaDetalle)) {
            return false;
        }
        PartidaDetalle other = (PartidaDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PartidaDetalle[ id=" + id + " ]";
    }
    
}
