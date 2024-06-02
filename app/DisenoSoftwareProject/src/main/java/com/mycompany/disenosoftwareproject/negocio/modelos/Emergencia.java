/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author younrm
 */
public class Emergencia {
    private int id;
    private String descripcion;
    private String fechaActivacion;
    private String horaActivacion;
    private String nombreVia;
    private String fechaAtencion;
    private String horaAtencion;
    private boolean trasladoHospital;

    public Emergencia(int id, String descripcion, String fechaActivacion, String horaActivacion, String nombreVia,
                      String fechaAtencion, String horaAtencion, boolean trasladoHospital) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaActivacion = fechaActivacion;
        this.horaActivacion = horaActivacion;
        this.nombreVia = nombreVia;
        this.fechaAtencion = fechaAtencion;
        this.horaAtencion = horaAtencion;
        this.trasladoHospital = trasladoHospital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(String fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getHoraActivacion() {
        return horaActivacion;
    }

    public void setHoraActivacion(String horaActivacion) {
        this.horaActivacion = horaActivacion;
    }

    public String getNombreVia() {
        return nombreVia;
    }

    public void setNombreVia(String nombreVia) {
        this.nombreVia = nombreVia;
    }

    public String getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(String fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getHoraAtencion() {
        return horaAtencion;
    }

    public void setHoraAtencion(String horaAtencion) {
        this.horaAtencion = horaAtencion;
    }

    public boolean isTrasladoHospital() {
        return trasladoHospital;
    }

    public void setTrasladoHospital(boolean trasladoHospital) {
        this.trasladoHospital = trasladoHospital;
    }

    @Override
    public String toString() {
        return "Emergencia{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaActivacion='" + fechaActivacion + '\'' +
                ", horaActivacion='" + horaActivacion + '\'' +
                ", nombreVia='" + nombreVia + '\'' +
                ", fechaAtencion='" + fechaAtencion + '\'' +
                ", horaAtencion='" + horaAtencion + '\'' +
                ", trasladoHospital=" + trasladoHospital +
                '}';
    }
}