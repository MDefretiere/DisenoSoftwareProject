/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Persona {
    private String nombre;
    private String apellidos;
    private Fecha fechaNacimiento;
    private String nif;
    private Direccion direccion;
    private String telefono;
    
    public Persona(String nombre, String apellidos, Fecha fechaNacimiento, String nif, Direccion direccion, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.nif = nif;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}
