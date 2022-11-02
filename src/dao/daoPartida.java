/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.sun.jdi.connect.spi.Connection;
import controller.PartidaDetalleJpaController;
import controller.PartidaJpaController;
import entity.Cuentas;
import entity.Partida;
import entity.PartidaDetalle;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import auxiliar.inserts;

/**
 *
 * @author Carlos Rafaelano
 */
public class daoPartida {

    inserts in = new inserts();
    Partida partida = new Partida();
    PartidaDetalle detalle = new PartidaDetalle();
    PartidaDetalleJpaController pdjc = new PartidaDetalleJpaController();
    PartidaJpaController pjc = new PartidaJpaController();
    Cuentas cuenta = new Cuentas();

    //Para listar las partidas en la una JTable usando entidades
//    public void listPartida(JTable tabla) {
//        DefaultTableModel model;
//        String[] titulo = {"Nº Partida", "Fecha", "Cuenta", "Descripcion", "Debe", "Haber"};
//        model = new DefaultTableModel(null, titulo);
//        tabla.setModel(model);
//        List<PartidaDetalle> datos = pdjc.findPartidaDetalleEntities();
//        String[] datosTabla = new String[6];
//        for (PartidaDetalle ptd : datos) {
//            datosTabla[0] = ptd.getNumPartida().toString();
//            datosTabla[1] = ptd.getNumPartida().getFecha().toString();
//            datosTabla[2] = ptd.getCodCuenta().toString();
//            datosTabla[3] = ptd.getNumPartida().getDescripcion();
//            datosTabla[4] = ptd.getDebe().toString();
//            datosTabla[5] = ptd.getHaber().toString();
//            model.addRow(datosTabla);
//        }
//        tabla.setModel(model);
//    }
    //Para listar partidas en una jtable usando NativeQuery 
    public void listarDiario(JTable tabla) {
        DefaultTableModel model;
        String[] titulo = {"Nº Partida", "Fecha", "Cuenta", "Descripcion", "Debe", "Haber"};
        model = new DefaultTableModel(null, titulo);
        tabla.setModel(model);
        EntityManager em = pdjc.getEntityManager();
        Query query = em.createNativeQuery("select partida.num_partida, partida.fecha, cuentas.nombre_cuenta, "
                + "partida.descripcion, partida_detalle.debe, partida_detalle.haber from partida_detalle "
                + "inner join partida ON partida.num_partida = partida_detalle.num_partida "
                + "inner join cuentas ON cuentas.cod_cuenta = partida_detalle.cod_cuenta");
        List<Object[]> datos = query.getResultList();
        String[] datosTabla = new String[6];
        for (Object[] dato : datos) {
            datosTabla[0] = dato[0].toString();
            datosTabla[1] = dato[1].toString();
            datosTabla[2] = dato[2].toString();
            datosTabla[3] = dato[3].toString();
            datosTabla[4] = dato[4].toString();
            datosTabla[5] = dato[5].toString();
            model.addRow(datosTabla);
        }
        tabla.setModel(model);
    }

    //Para obtener el ultimo numero de partida 
    public int getLastNumPartida() {
        int numPartida;
        EntityManager em = pdjc.getEntityManager();
        Query query = em.createNativeQuery("select max(num_partida) from partida");
        String dato = query.getSingleResult().toString();
        numPartida = Integer.parseInt(dato);
        return numPartida;
    }

    //Para insertar una partida
    public void insertPartida(Date fecha, String descripcion) {
        in.insertPartida(fecha, descripcion);
    }

    //para insertar un detalle
    public void insertDetallePartida(int codCuenta, int numPartida, double debe, double haber) {
        in.insertDetallePartida(codCuenta, numPartida, debe, haber);
    }
}
