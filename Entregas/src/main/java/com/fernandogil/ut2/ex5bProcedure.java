/*Procedimiento que devuelve el Id del último registro de la tabla*/
package com.fernandogil.ut2;

import java.sql.*;

public class ex5bProcedure {
    private static Connection getConnection() throws SQLException {      //MÉTODO QUE DEVUELVE LA CONEXIÓN
        return PoolConexiones.getConexion();
    }
    public static void main(String[] args) {
        Connection conexion = null;
        CallableStatement procedimiento = null;
        ResultSet rs = null;
        try {

            System.out.println("Conectando con la Base de datos...");
            conexion = getConnection();
            System.out.println("Conexión establecida con la Base de datos...");

            String sql = "{CALL muestraultimoid()}";
            procedimiento = conexion.prepareCall(sql);

            rs = procedimiento.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            System.out.println("El último id del ranking es: " +id);
        }catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(procedimiento!=null)
                    procedimiento.close();
                if(conexion!=null)
                    conexion.close();
                if(rs!=null)
                    rs.close();
                System.out.println("Cerrando conexión con la BD");
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
/* -------------------------- PROCEDIMIENTO ------------------------------
DECLARE lastid integer;

BEGIN

	SELECT MAX (IDRANKING) INTO lastid FROM tenistas;
	RETURN lastid;
END;
*/