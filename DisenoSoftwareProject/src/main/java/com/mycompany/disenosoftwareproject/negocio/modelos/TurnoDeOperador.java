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
public class TurnoDeOperador{
    private Fecha fechaCreacion;
    private Fecha fechaTurno;
    private TipoDeTurnoOperador tipoDeTurno;
    private List<Operador> enTurno;

    public TurnoDeOperador(Fecha fechaCreacion, Fecha fechaTurno, TipoDeTurnoOperador tipoDeTurno, List<Operador> e) {
        this.fechaCreacion = fechaCreacion;
        this.fechaTurno = fechaTurno;
        this.tipoDeTurno = tipoDeTurno;
        enTurno = e;
    }

    public List<Operador> getOperadores(){
        return enTurno;
    }
    
    public Fecha getFechaTurno(){
        return fechaTurno;
    }
    
    
}
