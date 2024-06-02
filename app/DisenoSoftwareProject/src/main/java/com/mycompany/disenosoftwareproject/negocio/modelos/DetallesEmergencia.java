/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author younrm
 */
public class DetallesEmergencia {
    private int id;
    private String descripcion;
    private String nombreVia;
    private String fechaAtencionPaciente;
    private String horaAtencionPaciente;
    private boolean trasladoHospital;

    public DetallesEmergencia(int id, String descripcion, String nombreVia, String fechaAtencionPaciente, String horaAtencionPaciente, boolean trasladoHospital) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombreVia = nombreVia;
        this.fechaAtencionPaciente = fechaAtencionPaciente;
        this.horaAtencionPaciente = horaAtencionPaciente;
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

    public String getNombreVia() {
        return nombreVia;
    }

    public void setNombreVia(String nombreVia) {
        this.nombreVia = nombreVia;
    }

    public String getFechaAtencionPaciente() {
        return fechaAtencionPaciente;
    }

    public void setFechaAtencionPaciente(String fechaAtencionPaciente) {
        this.fechaAtencionPaciente = fechaAtencionPaciente;
    }

    public String getHoraAtencionPaciente() {
        return horaAtencionPaciente;
    }

    public void setHoraAtencionPaciente(String horaAtencionPaciente) {
        this.horaAtencionPaciente = horaAtencionPaciente;
    }

    public boolean isTrasladoHospital() {
        return trasladoHospital;
    }

    public void setTrasladoHospital(boolean trasladoHospital) {
        this.trasladoHospital = trasladoHospital;
    }

    @Override
    public String toString() {
        return "DetallesEmergencia{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", nombreVia='" + nombreVia + '\'' +
                ", fechaAtencionPaciente='" + fechaAtencionPaciente + '\'' +
                ", horaAtencionPaciente='" + horaAtencionPaciente + '\'' +
                ", trasladoHospital=" + trasladoHospital +
                '}';
    }
}