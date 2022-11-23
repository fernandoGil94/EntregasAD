package com.fernandogil.ut2;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConexiones {
    private static BasicDataSource ds = null;

    public static DataSource getDataSource(){
        String jdbc = "jdbc:postgresql://localhost/fernandogil";
        if(ds == null){
            ds = new BasicDataSource();
            ds.setUsername("root");
            ds.setPassword("root");
            ds.setUrl(jdbc);
            //TAMAÑO DEL POOL
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
        }
        return ds;
    }

    public static Connection getConexion() throws SQLException{
        return getDataSource().getConnection();
    }
}
