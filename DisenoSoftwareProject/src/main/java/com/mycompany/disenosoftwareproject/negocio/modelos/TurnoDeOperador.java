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
