/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Asegurado extends Persona{
    private Sexo sexo;
    private String numeroDeCuenta;

    public Asegurado(String nombre, String apellidos, Fecha fechaNacimiento, String nif, Direccion direccion, String telefono, Sexo sexo, String numeroDeCuenta) {
        super(nombre, apellidos, fechaNacimiento, nif, direccion, telefono);
        this.sexo = sexo;
        this.numeroDeCuenta = numeroDeCuenta;
    }
    
}
