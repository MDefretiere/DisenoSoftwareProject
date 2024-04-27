/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Disponibilidad {
    private Fecha fechaInicio;
    private Fecha fechaFin;
    
    public Disponibilidad(Fecha fechaInicio, Fecha fechaFin){
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    public Disponibilidad(Fecha fechaInicio){
        this.fechaInicio = fechaInicio;
        this.fechaFin = null;
    }
}
