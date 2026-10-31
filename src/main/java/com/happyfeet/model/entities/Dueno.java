package com.happyfeet.model.entities;

import java.time.LocalDateTime;

public class Dueno {
    private int id;
    private String nombreCompleto;
    private String documentoIdentidad;
    private String direccion;
    private String telefono;
    private String email;
    private String contactoEmergencia;
    private LocalDateTime fechaRegistro;
    private boolean activo;

    public Dueno() {
    }

    public Dueno(String nombreCompleto, String documentoIdentidad, String email) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.email = email;
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }

    public Dueno(int id, String nombreCompleto, String documentoIdentidad, String direccion,
                 String telefono, String email, String contactoEmergencia, LocalDateTime fechaRegistro, boolean activo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.contactoEmergencia = contactoEmergencia;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Dueno{" +
                "id=" + id +
                ", nombre='" + nombreCompleto + '\'' +
                ", documento='" + documentoIdentidad + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
