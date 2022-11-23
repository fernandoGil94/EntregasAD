/*ejemplo de código susceptible de sufrir ataque por inyección de SQL.*/

package com.fernandogil.ut2;

import java.sql.*;

public class ex2Inject {
    private static Connection getConnection() throws SQLException{      //MÉTODO QUE DEVUELVE LA CONEXIÓN
        return PoolConexiones.getConexion();
    }
    public static void main(String[] args) {
        Connection conexion = null;
        Statement stmt = null;
        String jdbcUrl = "jdbc:postgresql://localhost/fernandoGil";

        try{
            System.out.println("Estableciendo conexión con la base de datos...");
            //conexion = DriverManager.getConnection(jdbcUrl,"root","root");
            conexion = getConnection();
            System.out.println("¡Conexión establecida con la base de datos!");
            stmt = conexion.createStatement();

            String sql = "INSERT INTO TENISTAS VALUES (11,'Carlos','Alcaraz',10)";
            stmt.executeUpdate(sql);
            System.out.println("¡Tenista añadido!");
        }catch(SQLException se) {
            //Errores de JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Errores de Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conexion!=null)
                    conexion.close();
                System.out.println("Conexión cerrada");
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }
}
