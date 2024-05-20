/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.Turno;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.TipoDeTurno;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Direccion;
import com.mycompany.disenosoftwareproject.negocio.modelos.Rol;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author younrm
 */
public class DAOTurno {
    private final Connection connection;

    public DAOTurno(Connection connection) {
        this.connection = connection;
    }

    public List<Turno> getTurnosPorFecha(String fecha) throws SQLException {
        List<Turno> turnos = new ArrayList<>();
        String query = "SELECT * FROM TURNOS WHERE FechaTurno = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, java.sql.Date.valueOf(fecha));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Turno turno = convertirResultSetATurno(resultSet);
                    turnos.add(turno);
                }
            }
        }
        return turnos;
    }

    public List<Empleado> getEmpleadosPorTurno(Turno turno) throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT e.* FROM EMPLEADO e JOIN OPERADORESENTURNO te ON e.NifCif = te.IdTurnoOperador WHERE te.IdTurnoOperador = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, turno.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Empleado empleado = convertirResultSetAEmpleado(resultSet);
                    empleados.add(empleado);
                }
            }
        }
        return empleados;
    }

    private Turno convertirResultSetATurno(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("IdTurno");
        String fechaTurno = resultSet.getString("FechaTurno");
        TipoDeTurno tipoTurno = TipoDeTurno.valueOf(resultSet.getString("TipoTurno"));
        return new Turno(id, fechaTurno, tipoTurno, null);
    }

    private Empleado convertirResultSetAEmpleado(ResultSet resultSet) throws SQLException {
        String nif = resultSet.getString("NifCif");
        String nombre = resultSet.getString("Nombre");
        String apellidos = resultSet.getString("Apellido");
        Fecha fechaNacimiento = Fecha.parseFecha(resultSet.getString("FechaNacimiento"));

        // Corriger la conversion de chaîne en JsonObject
        String direccionString = resultSet.getString("Direccion");
        JsonObject direccion = Json.createReader(new StringReader(direccionString)).readObject();

        String telefono = resultSet.getString("Telefono");
        Fecha fechaInicioEnEmpresa = Fecha.parseFecha(resultSet.getString("FechaInicioEnEmpresa"));
        Rol rol = Rol.valueOf(resultSet.getString("Rol"));

        return new Empleado(nombre, apellidos, fechaNacimiento, nif, Direccion.fromJson(direccion), telefono, fechaInicioEnEmpresa, rol);
    }
}