package com.fernandogil.ut2;

import com.fernandogil.ut1.Tenista;

import java.sql.*;
import java.util.ArrayList;

public class ex1ResultSet {     //PARA ACCEDER A LA CONEXIÓN ESTABLECIDA EN LA POOL CONNECTION EN SU MÉTODO ESTÁTICO GETCONEXION()
    private static Connection getConnection() throws SQLException{
        return PoolConexiones.getConexion();
    }
    public static void main(String[] args) {
        // TODO code application logic here
        Connection conexion = null;
        Statement stmt = null;
        ResultSet rs = null;
        //TENISTAS QUE AÑADIMOS DE INICIO A LA TABLA
        ArrayList<Tenista> listaTenistas = new ArrayList<>();
        listaTenistas.add(new Tenista(1,"Rafa","Nadal",22));
        listaTenistas.add(new Tenista(2,"Novak","Djokovic",21));
        listaTenistas.add(new Tenista(3,"Roger","Federer",20));
        listaTenistas.add(new Tenista(4,"Pete","Sampras",14));
        listaTenistas.add(new Tenista(5,"Bjorn","Borg",11));
        listaTenistas.add(new Tenista(6,"Ivan","Lendl",8));
        listaTenistas.add(new Tenista(7,"Jimmy","Connors",8));
        listaTenistas.add(new Tenista(8,"Andre","Agassi",8));
        listaTenistas.add(new Tenista(9,"John","McKenroe",7));
        listaTenistas.add(new Tenista(10,"Mats","Wilander",7));
        try {

            //CONECTAMOS CON LA BASE DE DATOS POSTGRES PARA PODER CREAR UNA NUEVA
            System.out.println("Conectando con la Base de datos...");
            conexion = getConnection();

            System.out.println("Conexión establecida con la Base de datos...");

            //CREAMOS UN OBJETO STATEMENT QUE CREARÁ LA BBDD
            stmt = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            //CREAMOS LA TABLA CONECTANDO CON LA BASE DE DATOS QUE USAREMOS EN ADELANTE
            conexion = getConnection();
            String sql = "CREATE TABLE tenistas(IDRANKING int NOT NULL,NOMBRE char(20) NOT NULL, APELLIDO char(20) NOT NULL, GRANDSLAMS int NULL, CONSTRAINT PK_IDRANKING PRIMARY KEY(IDRANKING) )";
            stmt.executeUpdate(sql);
            System.out.println("¡Tabla creada con éxito!");
            //INSERTAMOS LOS TENISTAS A LA TABLA
            for(Tenista t : listaTenistas) {
                sql = "INSERT INTO TENISTAS VALUES (" +t.getIdRanking()+",'"+t.getNom()+"', '"+ t.getApellido()+"'," +t.getGrandSlams()+")";
                stmt.executeUpdate(sql);
            }

            System.out.println("¡Registros añadidos!");

            sql = "SELECT * FROM TENISTAS";
            rs = stmt.executeQuery(sql);

            //BORRAMOS EL ÚLTIMO REGISTRO
            rs.last();
            rs.deleteRow();
            System.out.println("¡Registro borrado!");

            //MODIFICAMOS EL REGISTRO EN LA QUINTA POSICION
            rs.absolute(5);
            rs.updateString(2,"Roy");
            rs.updateString("Apellido","Emerson");
            rs.updateInt(4,12);
            rs.updateRow();
            System.out.println("¡Registro modificado!\n");

            //IMPRIME EL TENISTA EN EL PRIMER RESULTADO
            rs.first();
            int rank = rs.getInt("idranking");
            String nom = rs.getString("nombre");
            String ape = rs.getString(3);
            int gs = rs.getInt(4);

            System.out.println("Posicion ranking: "+rank);
            System.out.println("Nombre: "+nom);
            System.out.println("Apellido: " +ape);
            System.out.println("GrandSlams: " +gs+"\n");

            //IMPRIME EL RESULTADO DEL TENISTA QUE ESTÁ DOS POSICIONES HACIA ADELANTE(TERCERA POSICION)
            rs.relative(2);
            rank = rs.getInt(1);
            nom = rs.getString("nombre");
            ape = rs.getString("apellido");
            gs = rs.getInt(4);
            System.out.println("Posicion ranking: "+rank);
            System.out.println("Nombre: "+nom);
            System.out.println("Apellido: " +ape);
            System.out.println("GrandSlams: " +gs+"\n");

            //IMPRIMIMOS EL TENISTA EN LA POSICION NUMERO DOS
            rs.previous();
            rank = rs.getInt(1);
            nom = rs.getString("nombre");
            ape = rs.getString("apellido");
            gs = rs.getInt(4);
            System.out.println("Posicion ranking: "+rank);
            System.out.println("Nombre: "+nom);
            System.out.println("Apellido: " +ape);
            System.out.println("GrandSlams: " +gs);

        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
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
            }
        }
    }

}
