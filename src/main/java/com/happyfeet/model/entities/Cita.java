package com.happyfeet.model.entities;

import java.time.LocalDateTime;

public class Cita {
    private int id;
    private int mascotaId;
    private Integer veterinarioId;
    private LocalDateTime fechaHora;
    private String motivo;
    private int estadoId;
    private String observaciones;
    private LocalDateTime fechaCreacion;

    public Cita() {
    }

    public Cita(int mascotaId, LocalDateTime fechaHora, String motivo, int estadoId) {
        this.mascotaId = mascotaId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estadoId = estadoId;
        this.fechaCreacion = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Integer getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Integer veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", mascotaId=" + mascotaId +
                ", fechaHora=" + fechaHora +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
