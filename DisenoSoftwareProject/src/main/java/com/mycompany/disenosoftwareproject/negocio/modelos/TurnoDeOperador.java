/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOTurnoDeOperador;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

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
    
    public TipoDeTurnoOperador getTipo(){
        return tipoDeTurno;
    } 
    
    public static List<TurnoDeOperador> getTurnosPorFecha(Fecha fecha) throws SQLException, Exception {
        List<TurnoDeOperador> turnos = new ArrayList<>();
        JsonObject listTurnosJson = DAOTurnoDeOperador.getTurnosPorFecha(fecha);
        JsonArray turnosArray = listTurnosJson.getJsonArray("turnos");

        for (JsonValue turnoJson : turnosArray) {
            JsonObject turnoObj = (JsonObject) turnoJson;
            int idTurno = turnoObj.getInt("IDTURNOOPERADOR");
            Fecha fechaCreacion = Fecha.parseFecha(turnoObj.getString("FECHACREACION"));
            Fecha fechaTurno = Fecha.parseFecha(turnoObj.getString("FECHATURNO"));
            TipoDeTurnoOperador tipoDeTurno;
            switch(turnoObj.getString("NOMBRETIPOTURNO")){
                case "DeManana7" -> tipoDeTurno = TipoDeTurnoOperador.DeManana7;
                case "DeTarde15" -> tipoDeTurno = TipoDeTurnoOperador.DeManana7;
                case "DeNoche23" -> tipoDeTurno = TipoDeTurnoOperador.DeManana7;
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
        return turnos;
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
