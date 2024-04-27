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
    
    private Rol rol;

    public Empleado(String nombre, String apellidos, Fecha fechaNacimiento, String nif, Direccion direccion, String telefono, Fecha fechaInicioEnEmpresa, Rol rol) {
        super(nombre, apellidos, fechaNacimiento, nif, direccion, telefono);
        this.fechaInicioEnEmpresa = fechaInicioEnEmpresa;
        this.rol = rol;
    }
    
    public boolean estaActivo(){
        return false; ////A FAIRE
    }
    
    public Rol obtenerRolActual(){
        return rol;
    }
}
