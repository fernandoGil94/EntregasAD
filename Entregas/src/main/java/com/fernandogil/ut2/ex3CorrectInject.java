/*variante que corrije el inconveniente anterior (haciendo uso de PreparedStatement)*/
package com.fernandogil.ut2;

import java.sql.*;

public class ex3CorrectInject {

    private static Connection getConnection() throws SQLException{  //MÉTODO QUE DEVUELVE LA CONEXIÓN
        return PoolConexiones.getConexion();
    }
    public static void main(String[] args) {
        Connection conexion = null;
        PreparedStatement pstmt = null;
        String jdbcUrl = "jdbc:postgresql://localhost/fernandoGil";

        try{
            System.out.println("Estableciendo conexión con la base de datos...");
            conexion = getConnection();

            System.out.println("¡Conexión establecida con la base de datos!");

            String sql = "UPDATE TENISTAS SET NOMBRE=?,APELLIDO=?,GRANDSLAMS=? WHERE IDRANKING=?";

            //CREAMOS EL OBJETO PREPARED STATEMENT
            pstmt = conexion.prepareStatement(sql);

            //AÑADIMOS LOS PARÁMETROS DEL PREPARED STATEMENT
            pstmt.setString(1,"Roy");
            pstmt.setString(2,"Emerson");
            pstmt.setInt(3,12);
            pstmt.setInt(4,11);

            //EJECUTAMOS EL PREPAREDSTATEMENT
            pstmt.executeUpdate();

            System.out.println("¡Tenista modificado!");
        }catch(SQLException se) {
            //Errores de JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Errores de Class.forName
            e.printStackTrace();
        } finally {
            try {
                if(pstmt!=null)
                    pstmt.close();
                if(conexion!=null)
                    conexion.close();
                System.out.println("Conexión cerrada");
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }
}
