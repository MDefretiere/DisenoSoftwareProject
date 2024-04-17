/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Empleado extends Persona{
    private Fecha fechaInicioEnEmpresa;

    public Empleado(String nombre, String apellidos, Fecha fechaNacimiento, String nif, Direccion direccion, String telefono, Fecha fechaInicioEnEmpresa) {
        super(nombre, apellidos, fechaNacimiento, nif, direccion, telefono);
        this.fechaInicioEnEmpresa = fechaInicioEnEmpresa;
    }
}
