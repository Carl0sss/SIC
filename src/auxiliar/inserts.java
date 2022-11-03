/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxiliar;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Carlos Rafaelano
 */
public class inserts {

    //Creando un objeto conexion para una conexion auxiliar
    conexionsql con = new conexionsql();

    //Insercion en la base de datos a traves de sentencias sql con una conexion auxiliar
    public void insertDetallePartida(int codCuenta, int numPartida, double debe, double haber) {
        try {
            Connection conexion = con.conectar();
            Statement st = conexion.createStatement();
            String sql = "INSERT INTO partida_detalle(debe, haber, cod_cuenta, num_partida) "
                    + "VALUES (" + debe + ", " + haber + ", " + codCuenta + ", " + numPartida + ");";
            st.execute(sql);
            st.close();
        } catch (Exception e) {
        }
    }

    //Insercion en la base de datos a traves de sentencias sql con una conexion auxiliar
    public void insertPartida(LocalDate fecha, String descripcion) {
        try {

            Connection conexion = con.conectar();
            Statement st = conexion.createStatement();
            String sql = "INSERT INTO partida(fecha, descripcion)\n"
                    + "VALUES ('" + fecha + "','" + descripcion + "');";
            st.execute(sql);
            st.close();
        } catch (Exception e) {
        }
    }

    public void insertPlanilla(String ocupacion, String nombre, double horas, double salario, int dias) {
        try {

            Connection conexion = con.conectar();
            Statement st = conexion.createStatement();
            String sql = "INSERT INTO planilla(\n"
                    + "	nombre_empleado, ocupacion, horas_trabajadas, salario_hora, dias_trabajados)\n"
                    + "	VALUES ('" + nombre + "','" + ocupacion + "' ," + horas + " ," + salario + " ," + dias + " );";
            st.execute(sql);
            st.close();
        } catch (Exception e) {
        }
    }
}
