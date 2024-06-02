/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import java.util.List;

/**
 *
 * @author defre
 */
public class LlamadaNoCritica extends LlamadaDeAsegurado{
    private boolean esLeve;
    private List<Consejo> consejos;
    private int idOperativo;
    
    public LlamadaNoCritica(int id, String numeroTelefonoOrigen, Fecha fechaInicio, Hora horaInicio, Fecha fechaFin, Hora horaFin, String nombreComunicante, Empleado empleado, String descripcionEmergencia, Asegurado paciente, boolean esLeve, List<Consejo> consejos, int requiereOperativo) {
        super(id, numeroTelefonoOrigen, fechaInicio, horaInicio, fechaFin, horaFin, nombreComunicante, empleado, descripcionEmergencia, paciente);
        this.esLeve = esLeve;
        this.consejos = consejos;
        this.idOperativo = requiereOperativo;
    }
    
    public boolean esLeve(){
        return esLeve;
    }
    
    public int getIdOperativo(){
        return idOperativo;
    }
    
    public List<Consejo> getConsejos(){
        return consejos;
    }
}
