/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxiliar;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Carlos Rafaelano
 */
public class conexionsql {
//    Connection conectar = null;
//    String url = "jdbc:postgresql://localhost:5433/persistencia_contable";
//    String user = "postgres";
//    String pass = "123";
    Connection conectar = null;
    String url = "jdbc:mysql://localhost:3306/contabilidad";
    String user = "root";
    String pass = "";
    
//    public Connection conectar(){
//        try {
//            Class.forName("org.postgresql.Driver");
//            conectar = DriverManager.getConnection(url,user,pass);
//        } catch (Exception e) {
//        }
//        return conectar;
//    }
    public Connection conectar(){
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conectar = DriverManager.getConnection(url,user,pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return conectar;
    }
}
