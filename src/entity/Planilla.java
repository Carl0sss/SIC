/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Carlos Rafaelano
 */
@Entity
@Table(name = "planilla")
@NamedQueries({
    @NamedQuery(name = "Planilla.findAll", query = "SELECT p FROM Planilla p"),
    @NamedQuery(name = "Planilla.findByNombreEmpleado", query = "SELECT p FROM Planilla p WHERE p.nombreEmpleado = :nombreEmpleado"),
    @NamedQuery(name = "Planilla.findByOcupacion", query = "SELECT p FROM Planilla p WHERE p.ocupacion = :ocupacion"),
    @NamedQuery(name = "Planilla.findByHorasTrabajadas", query = "SELECT p FROM Planilla p WHERE p.horasTrabajadas = :horasTrabajadas"),
    @NamedQuery(name = "Planilla.findBySalarioHora", query = "SELECT p FROM Planilla p WHERE p.salarioHora = :salarioHora"),
    @NamedQuery(name = "Planilla.findByDiasTrabajados", query = "SELECT p FROM Planilla p WHERE p.diasTrabajados = :diasTrabajados"),
    @NamedQuery(name = "Planilla.findByIdTrabajador", query = "SELECT p FROM Planilla p WHERE p.idTrabajador = :idTrabajador")})
public class Planilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;
    @Column(name = "ocupacion")
    private String ocupacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "horas_trabajadas")
    private Double horasTrabajadas;
    @Column(name = "salario_hora")
    private Double salarioHora;
    @Column(name = "dias_trabajados")
    private Integer diasTrabajados;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_trabajador")
    private Integer idTrabajador;

    public Planilla() {
    }

    public Planilla(Integer idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public Double getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(Double salarioHora) {
        this.salarioHora = salarioHora;
    }

    public Integer getDiasTrabajados() {
        return diasTrabajados;
    }

    public void setDiasTrabajados(Integer diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }

    public Integer getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Integer idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrabajador != null ? idTrabajador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planilla)) {
            return false;
        }
        Planilla other = (Planilla) object;
        if ((this.idTrabajador == null && other.idTrabajador != null) || (this.idTrabajador != null && !this.idTrabajador.equals(other.idTrabajador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Planilla[ idTrabajador=" + idTrabajador + " ]";
    }
    
}
