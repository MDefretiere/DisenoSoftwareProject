/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Llamada {
    private int id;
    private String numeroTelefonoOrigen;
    private Fecha fechaInicio;
    private Hora horaInicio;
    private Fecha fechaFin;
    private Hora horaFin;
    private String nombreComunicante;
    private Empleado atiende;
    
    public Llamada(int id, String numeroTelefonoOrigen, Fecha fechaInicio, Hora horaInicio, Fecha fechaFin, Hora horaFin, String nombreComunicante, Empleado empleado) {
        this.id = id;
        this.numeroTelefonoOrigen = numeroTelefonoOrigen;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.fechaFin = fechaFin;
        this.horaFin = horaFin;
        this.nombreComunicante = nombreComunicante;
        this.atiende = empleado;
    }
}
