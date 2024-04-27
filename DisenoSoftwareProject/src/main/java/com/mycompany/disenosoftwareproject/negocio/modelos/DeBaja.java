/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class DeBaja extends Disponibilidad{
    
    public DeBaja(Fecha fechaInicio, Fecha fechaFin) {
        super(fechaInicio, fechaFin);
    }
    
    public DeBaja(Fecha fechaInicio) {
        super(fechaInicio);
    }    
}
