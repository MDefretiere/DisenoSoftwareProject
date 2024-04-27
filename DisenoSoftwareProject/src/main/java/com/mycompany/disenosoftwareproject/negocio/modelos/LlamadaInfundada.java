/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class LlamadaInfundada extends Llamada{
    
    public LlamadaInfundada(int id, String numeroTelefonoOrigen, Fecha fechaInicio, Hora horaInicio, Fecha fechaFin, Hora horaFin, String nombreComunicante, Empleado empleado) {
        super(id, numeroTelefonoOrigen, fechaInicio, horaInicio, fechaFin, horaFin, nombreComunicante, empleado);
    }
}
