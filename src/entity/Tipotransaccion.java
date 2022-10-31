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
@Table(name = "tipotransaccion")
@NamedQueries({
    @NamedQuery(name = "Tipotransaccion.findAll", query = "SELECT t FROM Tipotransaccion t"),
    @NamedQuery(name = "Tipotransaccion.findByIdtipotransaccion", query = "SELECT t FROM Tipotransaccion t WHERE t.idtipotransaccion = :idtipotransaccion"),
    @NamedQuery(name = "Tipotransaccion.findByNombretipotransaccion", query = "SELECT t FROM Tipotransaccion t WHERE t.nombretipotransaccion = :nombretipotransaccion")})
public class Tipotransaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idtipotransaccion")
    private Integer idtipotransaccion;
    @Basic(optional = false)
    @Column(name = "nombretipotransaccion")
    private String nombretipotransaccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipotransaccion")
    private Collection<Transaccion> transaccionCollection;

    public Tipotransaccion() {
    }

    public Tipotransaccion(Integer idtipotransaccion) {
        this.idtipotransaccion = idtipotransaccion;
    }

    public Tipotransaccion(Integer idtipotransaccion, String nombretipotransaccion) {
        this.idtipotransaccion = idtipotransaccion;
        this.nombretipotransaccion = nombretipotransaccion;
    }

    public Integer getIdtipotransaccion() {
        return idtipotransaccion;
    }

    public void setIdtipotransaccion(Integer idtipotransaccion) {
        this.idtipotransaccion = idtipotransaccion;
    }

    public String getNombretipotransaccion() {
        return nombretipotransaccion;
    }

    public void setNombretipotransaccion(String nombretipotransaccion) {
        this.nombretipotransaccion = nombretipotransaccion;
    }

    public Collection<Transaccion> getTransaccionCollection() {
        return transaccionCollection;
    }

    public void setTransaccionCollection(Collection<Transaccion> transaccionCollection) {
        this.transaccionCollection = transaccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipotransaccion != null ? idtipotransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipotransaccion)) {
            return false;
        }
        Tipotransaccion other = (Tipotransaccion) object;
        if ((this.idtipotransaccion == null && other.idtipotransaccion != null) || (this.idtipotransaccion != null && !this.idtipotransaccion.equals(other.idtipotransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tipotransaccion[ idtipotransaccion=" + idtipotransaccion + " ]";
    }
    
}
