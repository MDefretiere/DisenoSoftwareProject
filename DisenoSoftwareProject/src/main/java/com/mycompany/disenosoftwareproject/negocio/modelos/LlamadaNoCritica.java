/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class LlamadaNoCritica extends LlamadaDeAsegurado{
    private boolean esLeve;
    
    public LlamadaNoCritica(int id, String numeroTelefonoOrigen, Fecha fechaInicio, Hora horaInicio, Fecha fechaFin, Hora horaFin, String nombreComunicante, Empleado empleado, String descripcionEmergencia, Asegurado paciente) {
        super(id, numeroTelefonoOrigen, fechaInicio, horaInicio, fechaFin, horaFin, nombreComunicante, empleado, descripcionEmergencia, paciente);
        this.esLeve = esLeve;
    }
}
