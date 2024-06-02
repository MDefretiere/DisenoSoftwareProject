/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Disponible extends Disponibilidad{

    public Disponible(Fecha fechaInicio, Fecha fechaFin) {
        super(fechaInicio, fechaFin);
    }
    
    public Disponible(Fecha fechaInicio) {
        super(fechaInicio);
    }
}
