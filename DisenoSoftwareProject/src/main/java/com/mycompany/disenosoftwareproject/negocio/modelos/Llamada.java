/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOLlamada;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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
    
    public int getId() {
        return id;
    }

    public String getNumeroTelefonoOrigen() {
        return numeroTelefonoOrigen;
    }

    public Fecha getFechaInicio() {
        return fechaInicio;
    }

    public Hora getHoraInicio() {
        return horaInicio;
    }

    public Fecha getFechaFin() {
        return fechaFin;
    }

    public Hora getHoraFin() {
        return horaFin;
    }

    public String getNombreComunicante() {
        return nombreComunicante;
    }

    public Empleado getAtiende() {
        return atiende;
    }
}
