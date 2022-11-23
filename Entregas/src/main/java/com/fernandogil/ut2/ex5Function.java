/*Función que suma el total de Grand Slams ganados por los 3 primeros tenistas del ranking*/
package com.fernandogil.ut2;

import java.sql.*;

public class ex5Function {
    private static Connection getConnection() throws SQLException {      //MÉTODO QUE DEVUELVE LA CONEXIÓN
        return PoolConexiones.getConexion();
    }
    public static void main(String[] args) {
        Connection conexion = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{

            System.out.println("Conectando con la Base de datos...");
            conexion = getConnection();
            System.out.println("Conexión establecida con la Base de datos...");
            stmt = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

            String sql = "select * from tenistas where idranking <=3";
            rs = stmt.executeQuery(sql);
            rs.first();
            int gsId1 = rs.getInt(4);
            rs.next();
            int gsId2 = rs.getInt(4);
            rs.next();
            int gsId3 = rs.getInt(4);
            //FUNCION QUE SUMA LOS GRAND SLAMS GANADOS POR TRES TENISTAS, EN ESTE CASO LOS TRES PRIMEROS
            sql = "SELECT sumagrandslams('"+gsId1+"','"+gsId2+"','"+gsId3+"')";
            pstmt = conexion.prepareStatement(sql);
            rs = pstmt.executeQuery();
            //SOLO HAY UN RESULTADO PERO HACEMOS NEXT PARA POSICIONARNOS EN ÉL
            rs.next();
            if(rs.wasNull())
                System.out.println("No se introdujo ningun dato");
            else{
                int resultado = rs.getInt(1);
                System.out.println("Entre los tres primeros tenistas del ranking han ganado " + resultado + " Grand Slams");
            }
        }catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conexion!=null)
                    conexion.close();
                if(pstmt!=null)
                    pstmt.close();
                if(rs!=null)
                    rs.close();
                System.out.println("Cerrando conexión con la BD");
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

/* ------------------------ FUNCION --------------------------------
SELECT * FROM TENISTAS;
CREATE OR REPLACE FUNCTION SUMAGRANDSLAMS(num1 int, num2 int, num3 int) returns integer
AS
$$

SELECT NUM1 + NUM2 + NUM3;

$$
Language SQL
 */