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
@Table(name = "cuentas")
@NamedQueries({
    @NamedQuery(name = "Cuentas.findAll", query = "SELECT c FROM Cuentas c"),
    @NamedQuery(name = "Cuentas.findByCodCuenta", query = "SELECT c FROM Cuentas c WHERE c.codCuenta = :codCuenta"),
    @NamedQuery(name = "Cuentas.findByNombreCuenta", query = "SELECT c FROM Cuentas c WHERE c.nombreCuenta = :nombreCuenta")})
public class Cuentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_cuenta")
    private Integer codCuenta;
    @Column(name = "nombre_cuenta")
    private String nombreCuenta;
    @OneToMany(mappedBy = "codCuenta")
    private Collection<PartidaDetalle> partidaDetalleCollection;
    @JoinColumn(name = "id_saldo", referencedColumnName = "id_saldo")
    @ManyToOne(optional = false)
    private Saldo idSaldo;

    public Cuentas() {
    }

    public Cuentas(Integer codCuenta) {
        this.codCuenta = codCuenta;
    }

    public Integer getCodCuenta() {
        return codCuenta;
    }

    public void setCodCuenta(Integer codCuenta) {
        this.codCuenta = codCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Collection<PartidaDetalle> getPartidaDetalleCollection() {
        return partidaDetalleCollection;
    }

    public void setPartidaDetalleCollection(Collection<PartidaDetalle> partidaDetalleCollection) {
        this.partidaDetalleCollection = partidaDetalleCollection;
    }

    public Saldo getIdSaldo() {
        return idSaldo;
    }

    public void setIdSaldo(Saldo idSaldo) {
        this.idSaldo = idSaldo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCuenta != null ? codCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuentas)) {
            return false;
        }
        Cuentas other = (Cuentas) object;
        if ((this.codCuenta == null && other.codCuenta != null) || (this.codCuenta != null && !this.codCuenta.equals(other.codCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codCuenta + "  " + nombreCuenta;
    }

}
