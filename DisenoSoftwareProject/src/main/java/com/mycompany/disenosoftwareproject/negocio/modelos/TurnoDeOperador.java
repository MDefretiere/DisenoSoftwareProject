package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOTurnoDeOperador;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * Auteur: defre
 */
public class TurnoDeOperador {
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

    public Fecha getFechaTurno() {
        return fechaTurno;
    }

    public int getId() {
        return idTurno;
    }

    public TipoDeTurnoOperador getTipo() {
        return tipoDeTurno;
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

    public List<Empleado> getListOperador() {
        return enTurno;
    }

    public void setListEmpleado(List<Empleado> list) {
        enTurno = list;
    }

    @Override
    public String toString() {
        return "" + fechaTurno + " : " + tipoDeTurno;
    }
}
