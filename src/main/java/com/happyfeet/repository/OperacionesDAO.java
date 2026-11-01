package com.happyfeet.repository;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class OperacionesDAO {
    
    protected Connection obtenerConexion() throws SQLException {
        return ConexionBD.obtenerInstancia().obtenerConexion();
    }
}
