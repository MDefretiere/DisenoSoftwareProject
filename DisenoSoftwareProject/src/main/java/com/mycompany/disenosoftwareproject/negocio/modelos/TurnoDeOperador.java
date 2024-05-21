/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOTurnoDeOperador;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author defre
 */
public class TurnoDeOperador{
    private int idTurno;
    private Fecha fechaCreacion;
    private Fecha fechaTurno;
    private TipoDeTurnoOperador tipoDeTurno;
    private List<Empleado> enTurno;

    public TurnoDeOperador(int idTurno, Fecha fechaCreacion, Fecha fechaTurno, TipoDeTurnoOperador tipoDeTurno, List<Empleado> e) {
        this.idTurno = idTurno;
        this.fechaCreacion = fechaCreacion;
        this.fechaTurno = fechaTurno;
        this.tipoDeTurno = tipoDeTurno;
        enTurno = e;
    }

    public List<Empleado> getOperadores(){
        return enTurno;
    }
    
    public Fecha getFechaTurno(){
        return fechaTurno;
    }
    
    public int getId(){
        return idTurno;
    }
    
    public static List<TurnoDeOperador> getTurnosPorFecha(Fecha fecha) throws SQLException{
        List<TurnoDeOperador> list = DAOTurnoDeOperador.getTurnosPorFecha(fecha);
        return(list);
    }
    
    
    public static void modificarOperadorEnTurno(TurnoDeOperador turnoAModificar, Empleado empleadoACambiar, Empleado nuevoEmpleado) throws SQLException {
        DAOTurnoDeOperador.modificarOperadorEnTurno(turnoAModificar, empleadoACambiar, nuevoEmpleado);
    }
    
    public List<Empleado> getListOperador(){
        return enTurno;
    }

    public void setListEmpleado(List<Empleado> list){
        enTurno = list;
    }
    
    @Override
    public String toString(){
        return ""+fechaTurno+" : "+tipoDeTurno;
    }
}
