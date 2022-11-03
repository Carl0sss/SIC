/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

//import dao.old.cuentaDAO;
//import entity.old.Cuenta;
import dao.daoCuenta;
import dao.daoPartida;
import entity.Cuentas;
import entity.Partida;
import entity.Saldo;
import java.awt.Color;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultButtonModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos Rafaelano
 */
public class Registro extends javax.swing.JPanel {

    private daoCuenta cDAO = new daoCuenta();
    daoPartida pDAO = new daoPartida();
    Cuentas cuenta = new Cuentas();
//    private daoPartida pDAO = new daoPartida();
    ButtonGroup group = new ButtonGroup();
    DefaultTableModel model;

    /**
     * Creates new form Registro
     */
    public Registro() {
        initComponents();
        //Iniciar principales

        setModel();

        group.add(rdbDebe);
        group.add(rdbHaber);
//        mostrarTabla();
        getCuentas(cmbCuenta);

    }

    /**
     * METODOS
     */
    //Setea el modelo de la tabla
    private void setModel() {
        String[] titulo = {"Codigo", "Cuenta", "Descripcion", "Debe", "Haber"};
        model = new DefaultTableModel(null, titulo);
        tblPartida.setModel(model);
    }

    //Llena el comboBox con las cuentas
    private void getCuentas(JComboBox cmb) {
        cDAO.getCuentaCmb(cmb);
    }

    //Para el cambio de colores en los elementos de la vista
    void setColorBtnAccept(JPanel panel) {
        panel.setBackground(new Color(0, 90, 225));
    }

    void resetColorBtnAccept(JPanel panel) {
        panel.setBackground(new Color(0, 102, 255));
    }

    void setColorBtnSecundary(JPanel panel) {
        panel.setBackground(new Color(210, 210, 210));
    }

    void resetColorBtnSecundary(JPanel panel) {
        panel.setBackground(new Color(224, 224, 224));
    }

    //Lee los datos del formulario y los almacena en una tabla
    void ReadData() {
        double debe = 0.0;
        double haber = 0.0;
        cuenta = (Cuentas) cmbCuenta.getSelectedItem();
        String cta = this.cuenta.getNombreCuenta();
        int id = this.cuenta.getCodCuenta();
        String descripcion = txtDescripcion.getText();
        if (rdbDebe.isSelected() == true) {
            debe = (double) numMonto.getValue();
        } else {
            haber = (double) numMonto.getValue();
        }

        System.out.println(debe + " " + haber + " " + cta + " " + id + "" + descripcion);
        String[] sd = {"" + id, cta, descripcion, "" + debe, "" + haber};
        model.addRow(sd);
        tblPartida.setModel(model);
    }

    //Lee los datos almacenados en una tabla
    void ReadDataTable() {
        if (dualidad()) {
            double haber = 0.0;
            double debe = 0.0;
            String descripcion;
            int codCuenta;
            int numPartida;
            LocalDate fecha = LocalDate.now();
            descripcion = tblPartida.getValueAt(0, 2).toString();
            pDAO.insertPartida(fecha, descripcion);
            //Leyendo cada fila de la tabla
            if (tblPartida.getRowCount() > 0) {
                for (int i = 0; i < tblPartida.getRowCount(); i++) {
                    debe = Double.parseDouble(tblPartida.getValueAt(i, 3).toString());
                    haber = Double.parseDouble(tblPartida.getValueAt(i, 4).toString());
                    codCuenta = Integer.parseInt(tblPartida.getValueAt(i, 0).toString());
                    numPartida = pDAO.getLastNumPartida();
                    pDAO.insertDetallePartida(codCuenta, numPartida, debe, haber);

                }
            }
            JOptionPane.showMessageDialog(null, "Transaccíon registrada correctamente");
            limpiar();
            txtDescripcion.setText("");
        }

    }

    //Limpia el formulario
    private void limpiar() {
        numMonto.setValue(0.0);
        group.clearSelection();
        cmbCuenta.setSelectedIndex(0);
    }

    //Verifica la dualidad de una partida
    boolean dualidad() {
        double sum1 = 0;
        double sum2 = 0;
        double a, b;
        if (tblPartida.getRowCount() > 0) {
            for (int i = 0; i < tblPartida.getRowCount(); i++) {
                a = (double) Double.parseDouble(tblPartida.getValueAt(i, 3).toString());
                b = (double) Double.parseDouble(tblPartida.getValueAt(i, 4).toString());
                sum2 += b;
                sum1 += a;
            }
        }
        if (sum1 != sum2) {
            JOptionPane.showMessageDialog(null, "No existe dualidad");
            return false;
        }
        return true;
    }
//    private void mostrarTabla(){
//        cDAO.listCuenta(tblPartida);
//    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbCuenta = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        numMonto = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        rdbDebe = new javax.swing.JRadioButton();
        rdbHaber = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPartida = new javax.swing.JTable();
        btnRegistrar = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(780, 510));
        setPreferredSize(new java.awt.Dimension(780, 510));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel1.setText("Resumen");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jPanel1.setBackground(new java.awt.Color(246, 246, 246));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel2.setText("$");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 20, 20));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setText("Transacción");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        cmbCuenta.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel1.add(cmbCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 280, 30));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setText("Descripción");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));

        numMonto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        numMonto.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));
        jPanel1.add(numMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 230, 30));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setText("Cuenta");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtDescripcion);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 300, 50));

        rdbDebe.setBackground(new java.awt.Color(246, 246, 246));
        rdbDebe.setText("Debe");
        jPanel1.add(rdbDebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, -1, -1));

        rdbHaber.setBackground(new java.awt.Color(246, 246, 246));
        rdbHaber.setText("Haber");
        jPanel1.add(rdbHaber, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel7.setText("Monto");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, -1, -1));

        btnAceptar.setBackground(new java.awt.Color(0, 102, 255));
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAceptarMousePressed(evt);
            }
        });
        btnAceptar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Aceptar");
        btnAceptar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel1.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 110, 30));

        btnCancelar.setBackground(new java.awt.Color(224, 224, 224));
        btnCancelar.setToolTipText("");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCancelarMousePressed(evt);
            }
        });
        btnCancelar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel9.setText("Cancelar");
        btnCancelar.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 5, -1, -1));

        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 110, 30));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 710, 220));

        jPanel2.setBackground(new java.awt.Color(246, 246, 246));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPartida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblPartida);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 690, 140));

        btnRegistrar.setBackground(new java.awt.Color(0, 102, 255));
        btnRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRegistrarMousePressed(evt);
            }
        });
        btnRegistrar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Registrar");
        btnRegistrar.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel2.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, 110, 30));

        btnBorrar.setBackground(new java.awt.Color(224, 224, 224));
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBorrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBorrarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnBorrarMousePressed(evt);
            }
        });
        btnBorrar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel11.setText("Borrar");
        btnBorrar.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jPanel2.add(btnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 110, 30));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 710, 200));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setText("Registro de Transacciones");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
        setColorBtnAccept(btnAceptar);
    }//GEN-LAST:event_btnAceptarMouseEntered

    private void btnAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseExited
        resetColorBtnAccept(btnAceptar);
    }//GEN-LAST:event_btnAceptarMouseExited

    private void btnRegistrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarMouseEntered
        setColorBtnAccept(btnRegistrar);
    }//GEN-LAST:event_btnRegistrarMouseEntered

    private void btnRegistrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarMouseExited
        resetColorBtnAccept(btnRegistrar);
    }//GEN-LAST:event_btnRegistrarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        setColorBtnSecundary(btnCancelar);
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        resetColorBtnSecundary(btnCancelar);
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnBorrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarMouseEntered
        setColorBtnSecundary(btnBorrar);
    }//GEN-LAST:event_btnBorrarMouseEntered

    private void btnBorrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarMouseExited
        resetColorBtnSecundary(btnBorrar);
    }//GEN-LAST:event_btnBorrarMouseExited

    private void btnCancelarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMousePressed
        limpiar();
        txtDescripcion.setText("");
    }//GEN-LAST:event_btnCancelarMousePressed

    private void btnAceptarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMousePressed
        ReadData();
        limpiar();
    }//GEN-LAST:event_btnAceptarMousePressed

    private void btnRegistrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarMousePressed
        ReadDataTable();
    }//GEN-LAST:event_btnRegistrarMousePressed

    private void btnBorrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarMousePressed
        tblPartida.setModel(new DefaultTableModel());
        setModel();
    }//GEN-LAST:event_btnBorrarMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAceptar;
    private javax.swing.JPanel btnBorrar;
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.JComboBox<Cuentas> cmbCuenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner numMonto;
    private javax.swing.JRadioButton rdbDebe;
    private javax.swing.JRadioButton rdbHaber;
    private javax.swing.JTable tblPartida;
    private javax.swing.JTextArea txtDescripcion;
    // End of variables declaration//GEN-END:variables
}
