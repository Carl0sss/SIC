/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import controlador.CuentaJpaController;
import entity.Cuenta;
import entity.Saldo;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos Rafaelano
 */
public class cuentaDAO {

    private CuentaJpaController cjc = new CuentaJpaController();
    private Cuenta cuenta = new Cuenta();
    private String msg = "";

    public String insertCuenta(String nombrecuenta) {
        try {
            cuenta.setIdcuenta(10000);
            cuenta.setNombrecuenta(nombrecuenta);
            cjc.create(cuenta);
            msg = "Guardado correctamente";
        } catch (Exception e) {
            msg = "No se puedo guardar\n" + e.getMessage();
        }
        return msg;
    }

    public String updateCuenta(int id, String nombrecuenta) {
        try {
            cuenta.setIdcuenta(id);
            cuenta.setNombrecuenta(nombrecuenta);
            cjc.edit(cuenta);
            msg = "Actualizado correctamente";
        } catch (Exception e) {
            msg = "No se puedo actualizar\n" + e.getMessage();
        }
        return msg;
    }

    public String deleteCuenta(int id) {
        try {
            cjc.destroy(id);
            msg = "Eliminado correctamente";
        } catch (Exception e) {
            msg = "No se puedo eliminar\n" + e.getMessage();
        }
        return null;
    }

//    public void listCuenta(JTable tabla) {
//        DefaultTableModel model;
////        String[] titulo = {"Codigo", "Cuenta", "Descripcion", "Debe", "Haber"};
////        model = new DefaultTableModel(null, titulo);
//        String[] titulo = {"Codigo", "Tipo Cuenta", "Saldo", "Cuenta"};
//        model = new DefaultTableModel(null, titulo);
//        //List<Cuenta> datos = cjc.findCuentaEntities();
//        List<Cuenta> datos = findCuenta(1);
//        String[] datosTabla = new String[4];
//        for (Cuenta c : datos) {
//            datosTabla[0] = c.getIdcuenta().toString();
//            datosTabla[1] = c.getIdtipocuenta().toString();
//            datosTabla[2] = c.getIdsaldo() + "";
//            datosTabla[3] = c.getNombrecuenta();
//            model.addRow(datosTabla);
//        }
//        tabla.setModel(model);
//    }

//    private List<Cuenta> findCuenta(int id) {
//        Cuenta cta;
//        EntityManager em = cjc.getEntityManager();
//        Query query = em.createQuery("SELECT c FROM Cuenta c WHERE c.idcuenta=:id");
//        query.setParameter("id", id);
//        List<Cuenta> lista = query.getResultList();
//        return lista;
//
//    }

    public void getCuentaCmb(JComboBox<Cuenta> cmbCuenta) {
        EntityManager em = cjc.getEntityManager();
        Iterator it = em.createQuery("SELECT c FROM Cuenta c").getResultList().iterator();
        Cuenta t;
        try {
            while (it.hasNext()) {
                t = (Cuenta) it.next();
                cmbCuenta.addItem(t);
            }
        } catch (Exception e) {
        }
    }
    

//    public Cuenta findCuentaId(int id) {
//        Cuenta cta = new Cuenta();
//        EntityManager em = cjc.getEntityManager();
//        try {
//            Query query = em.createQuery("SELECT c FROM Cuenta c WHERE c.idcuenta=:id");
//            query.setParameter("id", id);
//            cta = (Cuenta) query.getSingleResult();
//
//        } catch (Exception e) {
//            if (cjc.findCuenta(id) == null) {
//                msg = "Codigo no encontrado";
//                JOptionPane.showMessageDialog(null, msg);
//            }
//        }
//        return cta;
//    }
}
