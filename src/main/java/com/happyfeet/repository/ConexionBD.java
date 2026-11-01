package com.happyfeet.repository;

import com.happyfeet.util.LoggerUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// conexion unica a mysql (singleton)
public class ConexionBD {
    private static volatile ConexionBD instancia;
    private Connection conexion;
    private String url;
    private String usuario;
    private String clave;

    private ConexionBD() {
        cargarConfiguracion();
    }

    public static ConexionBD obtenerInstancia() {
        if (instancia == null) {
            synchronized (ConexionBD.class) {
                if (instancia == null) {
                    instancia = new ConexionBD();
                }
            }
        }
        return instancia;
    }

    private void cargarConfiguracion() {
        Properties propiedades = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            propiedades.load(fis);
            String host = propiedades.getProperty("db.host", "localhost");
            String port = propiedades.getProperty("db.port", "3306");
            String name = propiedades.getProperty("db.name", "happy_feet_veterinaria");
            usuario = propiedades.getProperty("db.user", "root");
            clave = propiedades.getProperty("db.password", "");
            url = "jdbc:mysql://" + host + ":" + port + "/" + name;
        } catch (IOException e) {
            LoggerUtil.registrarError("Error al cargar configuración de base de datos", e);
            url = "jdbc:mysql://localhost:3306/happy_feet_veterinaria";
            usuario = "root";
            clave = "1234567";
        }
    }

    public Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(url, usuario, clave);
        }
        return conexion;
    }

    public void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}
