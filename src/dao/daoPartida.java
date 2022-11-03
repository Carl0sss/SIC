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
    public void insertPartida(LocalDate fecha, String descripcion) {
        in.insertPartida(fecha, descripcion);
    }

    //para insertar un detalle
    public void insertDetallePartida(int codCuenta, int numPartida, double debe, double haber) {
        in.insertDetallePartida(codCuenta, numPartida, debe, haber);
    }

    public void listarMayor(JTable tabla, int codCuenta) {
        DefaultTableModel model;
        String[] titulo = {"Nº Partida", "Fecha", "Cuenta", "Descripcion", "Debe", "Haber"};
        model = new DefaultTableModel(null, titulo);
        tabla.setModel(model);
        EntityManager em = pdjc.getEntityManager();
        Query query = em.createNativeQuery("select partida_detalle.num_partida,partida.fecha, cuentas.nombre_cuenta,partida.descripcion, partida_detalle.debe, partida_detalle.haber from cuentas "
                + "inner join partida_detalle ON partida_detalle.cod_cuenta = cuentas.cod_cuenta "
                + "inner join partida ON partida.num_partida = partida_detalle.num_partida "
                + "where cuentas.cod_cuenta = " + codCuenta + " order by cuentas.cod_cuenta");
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

    public void listarComprobacion(JTable tabla) {
        DefaultTableModel model;
        String[] titulo = {"Codigo", "Cuenta", "Deudor", "Acreedor"};
        model = new DefaultTableModel(null, titulo);
        tabla.setModel(model);
        EntityManager em = pdjc.getEntityManager();
        Query query = em.createNativeQuery("SELECT\n"
                + "	c.cod_cuenta,\n"
                + "	c.NOMBRE_CUENTA,\n"
                + "IF\n"
                + "	( c.ID_SALDO = 1, ( sum( pd.debe ) - sum( pd.haber )), 0 ) AS DEUDOR,\n"
                + "IF\n"
                + "	( c.ID_SALDO = 2, ( sum( pd.haber ) - sum( pd.debe )), 0 ) AS ACREEDOR \n"
                + "FROM\n"
                + "	cuentas c\n"
                + "	INNER JOIN partida_detalle pd ON pd.cod_cuenta = c.cod_cuenta \n"
                + "GROUP BY\n"
                + "	c.cod_cuenta;");
        List<Object[]> datos = query.getResultList();
        String[] datosTabla = new String[4];
        for (Object[] dato : datos) {
            datosTabla[0] = dato[0].toString();
            datosTabla[1] = dato[1].toString();
            datosTabla[2] = dato[2].toString();
            datosTabla[3] = dato[3].toString();
            model.addRow(datosTabla);
        }
        tabla.setModel(model);
    }

    public void listarBalance(JTable tabla) {
        DefaultTableModel model;
        String[] titulo = {"Codigo", "Cuenta", "Debe", "Haber"};
        model = new DefaultTableModel(null, titulo);
        tabla.setModel(model);
        EntityManager em = pdjc.getEntityManager();
        Query query = em.createNativeQuery("SELECT\n"
                + "	c.cod_cuenta,\n"
                + "	c.NOMBRE_CUENTA,\n"
                + "IF\n"
                + "	( c.ID_SALDO = 1, ( sum( pd.debe ) - sum( pd.haber )), 0 ) AS DEUDOR,\n"
                + "IF\n"
                + "	( c.ID_SALDO = 2, ( sum( pd.haber ) - sum( pd.debe )), 0 ) AS ACREEDOR \n"
                + "FROM\n"
                + "	cuentas c\n"
                + "	INNER JOIN partida_detalle pd ON pd.cod_cuenta = c.cod_cuenta \n"
                + "GROUP BY\n"
                + "	c.cod_cuenta \n"
                + "ORDER BY\n"
                + "	c.ID_SALDO;");
        List<Object[]> datos = query.getResultList();
        String[] datosTabla = new String[4];
        for (Object[] dato : datos) {
            datosTabla[0] = dato[0].toString();
            datosTabla[1] = dato[1].toString();
            datosTabla[2] = dato[2].toString();
            datosTabla[3] = dato[3].toString();
            model.addRow(datosTabla);
        }
        tabla.setModel(model);
    }

    public void planilla(JTable tabla) {
        DefaultTableModel model;
        String[] titulo = {"Nombre empleado", "Salario hora", "Salario diario", "Salario Semanal", "Salario mensual", "ISSS", "AFP"};
        model = new DefaultTableModel(null, titulo);
        tabla.setModel(model);
        EntityManager em = pdjc.getEntityManager();
        Query query = em.createNativeQuery("SELECT\n"
                + "	NOMBRE_EMPLEADO,\n"
                + "	SALARIO_HORA,\n"
                + "	( SALARIO_HORA * HORAS_TRABAJADAS ) AS \"Salario Diario\",\n"
                + "	( HORAS_TRABAJADAS * SALARIO_HORA * 5 ) AS \"Salario Semanal\",\n"
                + "	( HORAS_TRABAJADAS * SALARIO_HORA * DIAS_TRABAJADOS ) AS \"Total Salario\",\n"
                + "	( ( HORAS_TRABAJADAS * SALARIO_HORA * DIAS_TRABAJADOS ) * 0.0775 ) AS \"ISSS\",\n"
                + "	( ( HORAS_TRABAJADAS * SALARIO_HORA * DIAS_TRABAJADOS ) * 0.075 ) AS \"AFP\" \n"
                + "FROM\n"
                + "	planilla");
        List<Object[]> datos = query.getResultList();
        String[] datosTabla = new String[7];
        for (Object[] dato : datos) {
            datosTabla[0] = dato[0].toString();
            datosTabla[1] = dato[1].toString();
            datosTabla[2] = dato[2].toString();
            datosTabla[3] = dato[3].toString();
            datosTabla[4] = dato[4].toString();
            datosTabla[5] = dato[5].toString();
            datosTabla[6] = dato[6].toString();
            model.addRow(datosTabla);
        }
        tabla.setModel(model);
    }
}
