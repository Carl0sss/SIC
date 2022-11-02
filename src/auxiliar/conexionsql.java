/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxiliar;
import java.sql.*;
/**
 *
 * @author Carlos Rafaelano
 */
public class conexionsql {
    Connection conectar = null;
    String url = "jdbc:postgresql://localhost:5433/persistencia_contable";
    String user = "postgres";
    String pass = "123";
    
    public Connection conectar(){
        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(url,user,pass);
        } catch (Exception e) {
        }
        return conectar;
    }
}
