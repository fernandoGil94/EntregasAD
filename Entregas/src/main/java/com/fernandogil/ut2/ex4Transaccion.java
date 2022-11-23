/*Transacción que no se complete al provocar un rollback.*/

package com.fernandogil.ut2;

import java.sql.*;

public class ex4Transaccion {
    private static Connection getConnection() throws SQLException{  //MÉTODO QUE DEVUELVE LA CONEXIÓN
        return PoolConexiones.getConexion();
    }
    public static void main(String[] args) {
        Connection conexion = null;
        PreparedStatement pstmt = null;
        ResultSet ids = null;

        //VARIABLES DADAS POR EL USUARIO
        String nom = "Rod", ape = "Laver";
        int gs = 11;
        String sqlAltaTenista = "INSERT INTO tenistas (idranking,nombre,apellido,grandslams) VALUES (?,?, ?,?)";

        try {
            System.out.println("Estableciendo conexión con la base de datos...");

            conexion = getConnection();
            //INICIAMOS LA TRANSACCION
            conexion.setAutoCommit(false);
            System.out.println("¡Conexión establecida con la base de datos!");

            pstmt = conexion.prepareStatement(sqlAltaTenista, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, 12);
            pstmt.setString(2, nom);
            pstmt.setString(3, ape);
            pstmt.setInt(4, gs);
            pstmt.executeUpdate();
            //OBTENEMOS EL ID QUE ACABAMOS DE GENERAR
            ids = pstmt.getGeneratedKeys();
            //NOS COLOCAMOS EN LA SIGUIENTE POSICIÓN
            ids.next();
            //OBTENEMOS EL ID
            int idRank = ids.getInt(1);
            //CERRAMOS EL OBJETO PREPARED STATEMENT
            pstmt.close();

            pstmt = conexion.prepareStatement(sqlAltaTenista);
            idRank += 1;
            pstmt.setInt(1, idRank);
            pstmt.setString(2, "Bill");
            pstmt.setString(3, "Tilden");
            pstmt.setInt(4, 10);
            pstmt.executeUpdate();

            //VALIDAMOS LA TRANSACCION
            conexion.commit();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            try {
                conexion.rollback();
                conexion.setAutoCommit(true);
                ids.close();
                pstmt.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }
}
