/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import java.util.List;

/**
 *
 * @author defre
 */
public class TurnoDeOperativo {
    private Fecha fechaCreacion;
    private Fecha fechaTurno;
    private TipoDeTurnoOperativo tipoDeTurno;
    private List<Empleado> conductorEnTurno;
    private List<Medico> medicoEnTurno;
    
    TurnoDeOperativo(Fecha fechaCreacion, Fecha fechaTurno, TipoDeTurnoOperativo tipoDeTurno,List<Empleado> conductorEnTurno, List<Medico> medicoEnTurno){
        this.fechaCreacion = fechaCreacion;
        this.fechaTurno = fechaTurno;
        this.tipoDeTurno = tipoDeTurno;
        this.conductorEnTurno = conductorEnTurno;
        this.medicoEnTurno = medicoEnTurno;
    }
    
    public Fecha getFechaTurno(){
        return fechaTurno;
    }
}
