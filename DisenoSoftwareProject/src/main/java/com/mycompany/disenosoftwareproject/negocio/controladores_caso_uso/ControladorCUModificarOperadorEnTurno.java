/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaModificarOperadorEnTurno;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.TipoDeTurnoOperador;
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
    private static List<Empleado> listOperadoresDispo = new ArrayList<>();
    private static TurnoDeOperador turnoAModificar;
    private static List<TurnoDeOperador> listTurnosPorFecha = new ArrayList<>();
    private static List<Empleado> allOperadores;

    public static List<Empleado> getOperadoresEnTurnosPorFecha(Fecha fecha) throws SQLException, Exception {
        listTurnosPorFecha.clear();
        listOperadoresEnTurno.clear();
        listTurnosPorFecha = TurnoDeOperador.getTurnosPorFecha(fecha);
        for (TurnoDeOperador t : listTurnosPorFecha) {
            for (Empleado e : t.getListOperador()) {
                listOperadoresEnTurno.add(e);
            }
        }
        fechaTurno = fecha;
        return (listOperadoresEnTurno);
    }

    public static List<Empleado> getOperadoresDisponibles(String idOperador) throws SQLException, Exception {
        allOperadores = Empleado.getAllOperadores();
        for (Empleado e : listOperadoresEnTurno) {
            if (e.getNif().equals(idOperador)) {
                empleadoACambiar = e;
            }
        }
        //Get el turno donde hay el empleado a modificar
        for (TurnoDeOperador t : listTurnosPorFecha) {
            for (Empleado e : t.getListOperador()) {
                if (e.getNif().equals(empleadoACambiar.getNif())) {
                    turnoAModificar = t;
                }
            }
        }
        listOperadoresDispo.clear();
        //Get turnos a comprobar para los operadores disponibles
        List<TurnoDeOperador> turnoAComprobar = listTurnosPorFecha;
        if (!TurnoDeOperador.getTurnosPorFecha(fechaTurno.diaAnterior()).isEmpty()) {
            TurnoDeOperador ultimoTurnoDiaAnterior = TurnoDeOperador.getTurnosPorFecha(fechaTurno.diaAnterior()).get(TurnoDeOperador.getTurnosPorFecha(fechaTurno.diaAnterior()).size() - 1);
            if (ultimoTurnoDiaAnterior.getTipo() == TipoDeTurnoOperador.DeNoche23) {
                turnoAComprobar.add(ultimoTurnoDiaAnterior);
            }
        }
        if (!TurnoDeOperador.getTurnosPorFecha(fechaTurno.proximoDia()).isEmpty()) {
            TurnoDeOperador primeroTurnoProximoDia = TurnoDeOperador.getTurnosPorFecha(fechaTurno.proximoDia()).get(0);
            if (primeroTurnoProximoDia.getTipo() == TipoDeTurnoOperador.DeManana7) {
                turnoAComprobar.add(primeroTurnoProximoDia);
            }
        }
        //Anadir Empleados disponibles
        for (Empleado e : allOperadores) {
            boolean dispo = isEmpleadoDispo(e, turnoAComprobar);
            if (dispo) {
                listOperadoresDispo.add(e);
            }
        }
        return listOperadoresDispo;
    }

    public static boolean isEmpleadoDispo(Empleado e, List<TurnoDeOperador> turnoAComprobar) {
        boolean dispo=true;
        for (TurnoDeOperador t : turnoAComprobar) {
            for (Empleado test : t.getListOperador()) {
                if (e.getNif().equals(test.getNif())) {
                    dispo = false;
                }
            }
        }
        return dispo;
    }

    public static void modificarOperadorEnTurno(String idOperador) {
        for (Empleado e : listOperadoresDispo) {
            if (e.getNif().equals(idOperador)) {
                nuevoEmpleado = e;
            }
        }
        CtrlVistaModificarOperadorEnTurno.preguntarConfirmacion(turnoAModificar, empleadoACambiar, nuevoEmpleado);
    }

    public static void confirmacion(boolean confirmacion) throws SQLException {
        if (confirmacion) {
            TurnoDeOperador.modificarOperadorEnTurno(turnoAModificar, empleadoACambiar, nuevoEmpleado);
        }
    }
}
