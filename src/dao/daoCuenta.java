/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.CuentasJpaController;
import entity.Cuentas;

/**
 *
 * @author Carlos Rafaelano
 */
public class daoCuenta {

    private CuentasJpaController cjc = new CuentasJpaController();
    private Cuentas cuenta = new Cuentas();
    private String msg = "";

    //Para llenar el comboBox con las cuentas
    public void getCuentaCmb(JComboBox<Cuentas> cmbCuenta) {
        EntityManager em = cjc.getEntityManager();
        Iterator it = em.createQuery("SELECT c FROM Cuentas c").getResultList().iterator();
        Cuentas t;
        try {
            while (it.hasNext()) {
                t = (Cuentas) it.next();
                cmbCuenta.addItem(t);
            }
        } catch (Exception e) {
        }
    }

    //Recuperar id saldo de una cuenta
    public int getIdSaldo(int cod) {
        int id;
        EntityManager em = cjc.getEntityManager();
        Query query = em.createNativeQuery("select cuentas.id_saldo from cuentas where cuentas.cod_cuenta = " + cod);
        String ls = query.getSingleResult().toString();
        id = Integer.parseInt(ls);
        return id;
    }
}
