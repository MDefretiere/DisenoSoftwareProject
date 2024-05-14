/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaModificarOperadorEnTurno;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author defre
 */
public class ControladorCUModificarOperadorEnTurno {
    private static Fecha fechaTurno;
    private static Empleado empleadoACambiar;
    private static Empleado nuevoEmpleado;
    private static List<Empleado> listOperadoresEnTurno = new ArrayList<>();
    private static List<Empleado> listOperadoresDispo;
    private static TurnoDeOperador turnoAModificar;
    private static List<TurnoDeOperador> listTurnosPorFecha;
    
    public static List<Empleado> getOperadoresEnTurnosPorFecha(Fecha fecha) throws SQLException{
        listTurnosPorFecha = TurnoDeOperador.getTurnosPorFecha(fecha);
        for(TurnoDeOperador t : listTurnosPorFecha){
            for(Empleado e : t.getListOperador()){
                listOperadoresEnTurno.add(e);
            }
        }
        fechaTurno = fecha;
        return (listOperadoresEnTurno);
    } 

    public static List<Empleado> getOperadoresDisponibles(String idOperador) {
        for(Empleado e : listOperadoresEnTurno){
            if(e.getNif().equals(idOperador)){
                empleadoACambiar = e;
            }
        }
        for(TurnoDeOperador t : listTurnosPorFecha){
            for(Empleado e : t.getListOperador()){
                if(e.getNif().equals(empleadoACambiar.getNif())){
                    turnoAModificar = t;
                }
            }
        }
        listOperadoresDispo = Empleado.getOperadoresDisponibles(fechaTurno);
        return listOperadoresDispo;
    }

    public static void modificarOperadorEnTurno(String idOperador) {
        for(Empleado e : listOperadoresDispo){
            if(e.getNif().equals(idOperador)){
                nuevoEmpleado = e;
            }
        }
        CtrlVistaModificarOperadorEnTurno.preguntarConfirmacion(turnoAModificar, empleadoACambiar, nuevoEmpleado);
    }
    
    public static void confirmacion(boolean confirmacion) throws SQLException{
        if(confirmacion){
            TurnoDeOperador.modificarOperadorEnTurno(turnoAModificar, empleadoACambiar, nuevoEmpleado);
        }
    }
}
