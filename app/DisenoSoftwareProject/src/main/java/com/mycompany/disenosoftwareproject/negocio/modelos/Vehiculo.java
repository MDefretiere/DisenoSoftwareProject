/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
class Vehiculo {
    private String matricula;
    private EstadoDeVehiculo estado;
    private Fecha fechaAlta;
    private CoordenadaGPS ubicacion;
    private Modelo modelo;
    
    public Vehiculo(String matricula, EstadoDeVehiculo estado, Fecha fechaAlta, CoordenadaGPS ubicacion, Modelo modelo) {
        this.matricula = matricula;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.ubicacion = ubicacion;
        this.modelo = modelo;
    }
}
