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
    private RequiereOperativo operativo;
    
    public LlamadaNoCritica(int id, String numeroTelefonoOrigen, Fecha fechaInicio, Hora horaInicio, Fecha fechaFin, Hora horaFin, String nombreComunicante, Empleado empleado, String descripcionEmergencia, Asegurado paciente, boolean esLeve, List<Consejo> consejos, boolean requiereOperativo) {
        super(id, numeroTelefonoOrigen, fechaInicio, horaInicio, fechaFin, horaFin, nombreComunicante, empleado, descripcionEmergencia, paciente);
        this.esLeve = esLeve;
        this.consejos = consejos;
        if(requiereOperativo){
            operativo = new RequiereOperativo(null, null);
        }
    }
    
    public boolean esLeve(){
        return esLeve;
    }
    
    public boolean hayOperativoNecesitado(){
        return operativo==null;
    }
    
    public List<Consejo> getConsejos(){
        return consejos;
    }
}
