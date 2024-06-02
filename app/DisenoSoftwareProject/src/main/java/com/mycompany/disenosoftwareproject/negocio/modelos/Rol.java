/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Rol {
    private Fecha fechaInicioEnPuesto;
    
    public Rol(Fecha fechaInicioEnPuesto){
        this.fechaInicioEnPuesto = fechaInicioEnPuesto;
    }
    
    public Fecha getFechaInicioEnPuesto() {
        return fechaInicioEnPuesto;
    }
    
    public static Rol valueOf(String str) {
        Fecha fecha = Fecha.parseFecha(str);
        return new Rol(fecha);
    }
}
