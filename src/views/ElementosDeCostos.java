/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import java.awt.Color;
import javax.swing.JPanel;
import auxiliar.inserts;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos Rafaelano
 */
public class ElementosDeCostos extends javax.swing.JPanel {

    inserts in = new inserts();

    /**
     * Creates new form ElementosDeCostos
     */
    public ElementosDeCostos() {
        initComponents();
    }

    //Para leer los datos
    private void readData() {
        String nombre = txtNombre1.getText();
        String ocupacion = txtOcupacion.getText();
        double horas = Double.parseDouble(txtHoras.getText());
        double salario = Double.parseDouble(txtSalario.getText());
        int dias = Integer.parseInt(txtDias.getText());
        try {
            in.insertPlanilla(ocupacion, nombre, horas, salario, dias);
            JOptionPane.showMessageDialog(null, "Ingresado correctamente");
            limpiar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo ingresar");
        }

    }

    //para limpiar todo
    private void limpiar() {
        txtDias.setText("");
        txtNombre1.setText("");
        txtOcupacion.setText("");
        txtSalario.setText("");
        txtHoras.setText("");
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

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDias = new javax.swing.JTextField();
        txtNombre1 = new javax.swing.JTextField();
        txtOcupacion = new javax.swing.JTextField();
        txtHoras = new javax.swing.JTextField();
        txtSalario = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(780, 510));
        setMinimumSize(new java.awt.Dimension(780, 510));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setText("Registro de costos de mano de obra");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel1.setBackground(new java.awt.Color(246, 246, 246));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel1.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 110, 30));

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

        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 110, 30));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel1.setText("Dias trabajados:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel2.setText("Nombre empleado:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setText("Ocupación:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setText("Horas trabajadas:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setText("Salario por hora:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, -1, -1));

        txtDias.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel1.add(txtDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 220, -1));

        txtNombre1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel1.add(txtNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 470, -1));

        txtOcupacion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel1.add(txtOcupacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 470, -1));

        txtHoras.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel1.add(txtHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 220, -1));

        txtSalario.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel1.add(txtSalario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 220, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 710, 440));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
        setColorBtnAccept(btnAceptar);
    }//GEN-LAST:event_btnAceptarMouseEntered

    private void btnAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseExited
        resetColorBtnAccept(btnAceptar);
    }//GEN-LAST:event_btnAceptarMouseExited

    private void btnAceptarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMousePressed
        readData();
    }//GEN-LAST:event_btnAceptarMousePressed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        setColorBtnSecundary(btnCancelar);
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        resetColorBtnSecundary(btnCancelar);
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMousePressed
        limpiar();
    }//GEN-LAST:event_btnCancelarMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAceptar;
    private javax.swing.JPanel btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtDias;
    private javax.swing.JTextField txtHoras;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtOcupacion;
    private javax.swing.JTextField txtSalario;
    // End of variables declaration//GEN-END:variables
}
