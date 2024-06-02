/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Operativo {
    private Fecha fechaCreacion;
    private EstadoDeOperativo estado;
    private Vehiculo vehiculo;
    private Consultorio consultorio;
    private Empleado medico;
    private Empleado conductor;
    private TurnoDeOperativo turno;
    
    public Operativo(Fecha fechaCreacion, EstadoDeOperativo estado, Vehiculo vehiculo, Consultorio consultorio, Empleado medico, Empleado conductor, TurnoDeOperativo turno) {
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.vehiculo = vehiculo;
        this.consultorio = consultorio;
        this.medico = medico;
        this.conductor = conductor;
        this.turno = turno;
    }
}
