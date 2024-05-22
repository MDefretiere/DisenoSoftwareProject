/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaModificarOperadorEnTurno;
import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaRegistrarLlamadaEntrante;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import static com.mycompany.disenosoftwareproject.negocio.modelos.Empleado.jsonToEmpleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.TipoDeTurnoOperador;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import com.mycompany.disenosoftwareproject.persistencia.DAOEmpleado;
import com.mycompany.disenosoftwareproject.persistencia.DAOTurnoDeOperador;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 *
 * @author defre
 */
public class ControladorCUModificarOperadorEnTurno {
    private static ControladorCUModificarOperadorEnTurno controlador = new ControladorCUModificarOperadorEnTurno();
    private static Fecha fechaTurno;
    private static Empleado empleadoACambiar;
    private static Empleado nuevoEmpleado;
    private static List<Empleado> listOperadoresEnTurno = new ArrayList<>();
    private static List<Empleado> listOperadoresDispo = new ArrayList<>();
    private static TurnoDeOperador turnoAModificar;
    private static List<TurnoDeOperador> listTurnosPorFecha = new ArrayList<>();
    private static List<Empleado> allOperadores;
    
    public static ControladorCUModificarOperadorEnTurno getInstance(){
        return controlador;
    }
    
    public static void start() throws Exception{
        CtrlVistaModificarOperadorEnTurno.open();
    }

    public List<Empleado> getOperadoresEnTurnosPorFecha(Fecha fecha) throws SQLException, Exception {
        listTurnosPorFecha.clear();
        listOperadoresEnTurno.clear();
        listTurnosPorFecha = getTurnosPorFecha(fecha);
        if(listTurnosPorFecha.isEmpty()){
            throw new Exception("No hay turno en ese dia.");
        }
        for (TurnoDeOperador t : listTurnosPorFecha) {
            for (Empleado e : t.getListOperador()) {
                listOperadoresEnTurno.add(e);
            }
        }
        fechaTurno = fecha;
        return (listOperadoresEnTurno);
    }

    public List<Empleado> getOperadoresDisponibles(String idOperador) throws SQLException, Exception {
        allOperadores = getAllOperadores();
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
        if (!getTurnosPorFecha(fechaTurno.diaAnterior()).isEmpty()) {
            TurnoDeOperador ultimoTurnoDiaAnterior = getTurnosPorFecha(fechaTurno.diaAnterior()).get(getTurnosPorFecha(fechaTurno.diaAnterior()).size() - 1);
            if (ultimoTurnoDiaAnterior.getTipo() == TipoDeTurnoOperador.DeNoche23) {
                turnoAComprobar.add(ultimoTurnoDiaAnterior);
            }
        }
        if (!getTurnosPorFecha(fechaTurno.proximoDia()).isEmpty()) {
            TurnoDeOperador primeroTurnoProximoDia = getTurnosPorFecha(fechaTurno.proximoDia()).get(0);
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

    public boolean isEmpleadoDispo(Empleado e, List<TurnoDeOperador> turnoAComprobar) {
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

    public void modificarOperadorEnTurno(String idOperador) {
        for (Empleado e : listOperadoresDispo) {
            if (e.getNif().equals(idOperador)) {
                nuevoEmpleado = e;
            }
        }
        CtrlVistaModificarOperadorEnTurno.preguntarConfirmacion(turnoAModificar, empleadoACambiar, nuevoEmpleado);
    }

    public void confirmacion(boolean confirmacion) throws SQLException {
        if (confirmacion) {
            modificarOperadorEnTurno(turnoAModificar, empleadoACambiar, nuevoEmpleado);
        }
    }
    
    public static List<TurnoDeOperador> getTurnosPorFecha(Fecha fecha) throws SQLException, Exception {
        List<TurnoDeOperador> turnos = new ArrayList<>();
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("fecha", fecha.toString());
        JsonArray turnosArray = DAOTurnoDeOperador.getTurnosPorFecha(jsonBuilder.build()).getJsonArray("turnos");

        for (JsonValue turnoJson : turnosArray) {
            boolean exist = false;
            JsonObject turnoObj = (JsonObject) turnoJson;
            int idTurno = turnoObj.getInt("IDTURNOOPERADOR");
            for (TurnoDeOperador t : turnos) {
                if (t.getId() == idTurno) {
                    JsonArray operadoresArray = turnoObj.getJsonArray("OPERADORES");
                    for (JsonValue operadorJson : operadoresArray) {
                        JsonObject operadorObj = (JsonObject) operadorJson;
                        Empleado operador = Empleado.jsonToEmpleado(operadorObj);
                        t.getListOperador().add(operador);
                    }
                    exist = true;
                }
            }
            if (!exist) {
                Fecha fechaCreacion = Fecha.parseFecha(turnoObj.getString("FECHACREACION"));
                Fecha fechaTurno = Fecha.parseFecha(turnoObj.getString("FECHATURNO"));
                TipoDeTurnoOperador tipoDeTurno;
                switch (turnoObj.getString("NOMBRETIPOTURNO")) {
                    case "DeManana7" -> tipoDeTurno = TipoDeTurnoOperador.DeManana7;
                    case "DeTarde15" -> tipoDeTurno = TipoDeTurnoOperador.DeTarde15;
                    case "DeNoche23" -> tipoDeTurno = TipoDeTurnoOperador.DeNoche23;
                    default -> throw new Exception("ERROR tipo de turno de Operador");
                }
                JsonArray operadoresArray = turnoObj.getJsonArray("OPERADORES");
                List<Empleado> operadores = new ArrayList<>();
                for (JsonValue operadorJson : operadoresArray) {
                    JsonObject operadorObj = (JsonObject) operadorJson;
                    Empleado operador = Empleado.jsonToEmpleado(operadorObj);
                    operadores.add(operador);
                }
                TurnoDeOperador turno = new TurnoDeOperador(idTurno, fechaCreacion, fechaTurno, tipoDeTurno, operadores);
                turnos.add(turno);
            }
        }
        return turnos;
    }

    public static List<TurnoDeOperador> getTurnosDelOperadorPorUnDia(Empleado operadorEnTurno, Fecha fechaActual) throws SQLException {
        List<TurnoDeOperador> turnos = new ArrayList<>();
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("fecha", fechaActual.toString());
        jsonBuilder.add("nif", operadorEnTurno.getNif());
        JsonObject jsonParam = jsonBuilder.build();
        JsonObject jsonTurnos = DAOTurnoDeOperador.getTurnosDelOperadorPorUnDia(jsonParam);
        JsonArray turnosArray = jsonTurnos.getJsonArray("turnos");

        for (JsonValue turnoJson : turnosArray) {
            JsonObject turnoObj = (JsonObject) turnoJson;
            int idTurno = turnoObj.getInt("IDTURNOOPERADOR");

            Fecha fechaCreacion = Fecha.parseFecha(turnoObj.getString("FECHACREACION"));
            Fecha fechaTurno = Fecha.parseFecha(turnoObj.getString("FECHATURNO"));

            TipoDeTurnoOperador tipoDeTurno;
            switch (turnoObj.getString("NOMBRETIPOTURNO")) {
                case "DeManana7" -> tipoDeTurno = TipoDeTurnoOperador.DeManana7;
                case "DeTarde15" -> tipoDeTurno = TipoDeTurnoOperador.DeTarde15;
                case "DeNoche23" -> tipoDeTurno = TipoDeTurnoOperador.DeNoche23;
                default -> throw new SQLException("ERROR tipo de turno de Operador");
            }

            List<Empleado> operadores = new ArrayList<>();
            operadores.add(operadorEnTurno);

            TurnoDeOperador turno = new TurnoDeOperador(idTurno, fechaCreacion, fechaTurno, tipoDeTurno, operadores);
            turnos.add(turno);
        }
        return turnos;
    }

    public static void modificarOperadorEnTurno(TurnoDeOperador turnoAModificar, Empleado empleadoACambiar, Empleado nuevoEmpleado) throws SQLException {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("idTurno", turnoAModificar.getId());
        jsonBuilder.add("nifEmpleadoACambiar", empleadoACambiar.getNif());
        jsonBuilder.add("nifNuevoEmpleado", nuevoEmpleado.getNif());
        JsonObject jsonParam = jsonBuilder.build();
        DAOTurnoDeOperador.modificarOperadorEnTurno(jsonParam);
    }
    
    public static List<Empleado> getAllOperadores () throws Exception {
        List<Empleado> empleados = new ArrayList<>();
        JsonObject jsonAllEmpleados = DAOEmpleado.getAllOperadores();
        JsonArray jsonEmpleadosArray = jsonAllEmpleados.getJsonArray("empleados");

        for (int i = 0; i < jsonEmpleadosArray.size(); i++) {
            JsonObject jsonEmpleado = jsonEmpleadosArray.getJsonObject(i);
            Empleado empleado = jsonToEmpleado(jsonEmpleado);
            empleados.add(empleado);
        }

        return empleados;
    }
}
