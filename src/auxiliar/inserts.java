/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxiliar;

import java.sql.*;
import java.util.Date;

/**
 *
 * @author Carlos Rafaelano
 */
public class inserts {

    conexionsql con = new conexionsql();

    public void insertDetallePartida(int codCuenta, int numPartida, double debe, double haber) {
        try {
            Connection conexion = con.conectar();
            Statement st = conexion.createStatement();
            String sql = "INSERT INTO public.partida_detalle(debe, haber, cod_cuenta, num_partida) "
                    + "VALUES (" + debe + ", " + haber + ", " + codCuenta + ", " + numPartida + ");";
            st.execute(sql);
            st.close();
        } catch (Exception e) {
        }
    }

    public void insertPartida(Date fecha, String descripcion) {
        try {
            Connection conexion = con.conectar();
            Statement st = conexion.createStatement();
            String sql = "INSERT INTO public.partida(fecha, descripcion)"
                    + "VALUES ('" + fecha + "', '" + descripcion + "');";
            st.execute(sql);
            st.close();
        } catch (Exception e) {
        }
    }
}
