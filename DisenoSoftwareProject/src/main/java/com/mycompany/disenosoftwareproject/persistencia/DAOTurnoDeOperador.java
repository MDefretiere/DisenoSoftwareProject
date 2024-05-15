/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.*;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author defre
 */
public class DAOTurnoDeOperador {

    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";

    public static JsonObject getTurnosPorFecha(Fecha fecha) throws SQLException {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder turnosArrayBuilder = Json.createArrayBuilder();
        String query = "SELECT TURNODEOPERADOR.IDTURNOOPERADOR, FECHATURNO, FECHACREACION, NOMBRETIPOTURNO, NIFCIF, OPERADORESENTURNO.NIFCIF " +
                       "FROM TURNODEOPERADOR " +
                       "JOIN TIPODETURNODEOPERADOR ON TURNODEOPERADOR.TIPOTURNO = TIPODETURNODEOPERADOR.IDTIPOTURNO " +
                       "JOIN OPERADORESENTURNO ON TURNODEOPERADOR.IDTURNOOPERADOR = OPERADORESENTURNO.IDTURNOOPERADOR " +
                       "WHERE FECHATURNO=?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setDate(1, Fecha.convertirLocalDateToDateSQL(fecha));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                JsonObjectBuilder turnoBuilder = Json.createObjectBuilder();
                turnoBuilder.add("IDTURNOOPERADOR", resultSet.getInt("IDTURNOOPERADOR"));
                turnoBuilder.add("FECHATURNO", resultSet.getDate("FECHATURNO").toString());
                turnoBuilder.add("FECHACREACION", resultSet.getDate("FECHACREACION").toString());
                turnoBuilder.add("NOMBRETIPOTURNO", resultSet.getString("NOMBRETIPOTURNO"));
                turnoBuilder.add("NIFCIF", resultSet.getString("NIFCIF"));

                JsonArrayBuilder operadoresArrayBuilder = Json.createArrayBuilder();
                String operadorId = resultSet.getString("NIFCIF");
                JsonObject operadorJson = DAOEmpleado.obtenerEmpleadoPorId(operadorId);
                operadoresArrayBuilder.add(operadorJson);

                turnoBuilder.add("OPERADORES", operadoresArrayBuilder);
                turnosArrayBuilder.add(turnoBuilder);
            }
        }
        jsonBuilder.add("turnos", turnosArrayBuilder);
        return jsonBuilder.build();
    }
    public static JsonObject mapperResultSetToTurnoDeOperador(ResultSet resultSet) throws SQLException {
        int idTurno = resultSet.getInt("IDTURNOOPERADOR");
        String fechaCreaciontoString = resultSet.getString("fechaCreacion");
        java.sql.Date fechaCreacionSQL = java.sql.Date.valueOf(fechaCreaciontoString);
        Fecha fechaCreacion = Fecha.convertirDateSQLToLocalDate(fechaCreacionSQL);
        String fechaTurnotoString = resultSet.getString("fechaTurno");
        java.sql.Date fechaTurnoSQL = java.sql.Date.valueOf(fechaTurnotoString);
        Fecha fechaTurno = Fecha.convertirDateSQLToLocalDate(fechaTurnoSQL);
        String sTipo = resultSet.getString("NOMBRETIPOTURNO");
        JsonObject jsonEmpleado = DAOEmpleado.obtenerEmpleadoPorId(resultSet.getString("NIFCIF"));

        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("idTurno", idTurno)
                .add("fechaCreacion", fechaCreacion.toString())
                .add("fechaTurno", fechaTurno.toString())
                .add("tipoTurno", sTipo)
                .add("operadores", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("id", jsonEmpleado.getString("NifCif"))
                                .add("nombre", jsonEmpleado.getString("nombre"))
                        )
                );

        return jsonBuilder.build();
    }

    public static void modificarOperadorEnTurno(TurnoDeOperador turnoAModificar, Empleado empleadoACambiar, Empleado nuevoEmpleado) throws SQLException {
        String query = "UPDATE OPERADORESENTURNO SET NifCif = ? WHERE IdTurnoOperador = ? AND NifCif = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nuevoEmpleado.getNif());
            statement.setInt(2, turnoAModificar.getId());
            statement.setString(3, empleadoACambiar.getNif());
            statement.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
