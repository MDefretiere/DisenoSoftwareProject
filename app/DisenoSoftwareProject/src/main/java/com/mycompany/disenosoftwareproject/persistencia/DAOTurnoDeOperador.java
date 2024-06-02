package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.*;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import serviciosComunes.JsonParser;

/**
 * Auteur: defre
 */
public class DAOTurnoDeOperador {
    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";

    public static String getTurnosPorFecha(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder turnosArrayBuilder = Json.createArrayBuilder();

        Fecha fecha = Fecha.parseFecha(jsonInput.getString("fecha"));

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
                String operadorJson = DAOEmpleado.obtenerEmpleadoPorId(Json.createObjectBuilder().add("nif", operadorId).build().toString());
                operadoresArrayBuilder.add(operadorJson);

                turnoBuilder.add("OPERADORES", operadoresArrayBuilder);
                turnosArrayBuilder.add(turnoBuilder);
            }
        }
        jsonBuilder.add("turnos", turnosArrayBuilder);
        JsonObject json = jsonBuilder.build();
        if(json==null){
            return null;
        }
        return json.toString();
    }
    
    public static String mapperResultSetToTurnoDeOperador(ResultSet resultSet) throws SQLException {
        int idTurno = resultSet.getInt("IDTURNOOPERADOR");
        String fechaCreaciontoString = resultSet.getString("fechaCreacion");
        java.sql.Date fechaCreacionSQL = java.sql.Date.valueOf(fechaCreaciontoString);
        Fecha fechaCreacion = Fecha.convertirDateSQLToLocalDate(fechaCreacionSQL);
        String fechaTurnotoString = resultSet.getString("fechaTurno");
        java.sql.Date fechaTurnoSQL = java.sql.Date.valueOf(fechaTurnotoString);
        Fecha fechaTurno = Fecha.convertirDateSQLToLocalDate(fechaTurnoSQL);
        String sTipo = resultSet.getString("NOMBRETIPOTURNO");
        JsonObject jsonEmpleado = JsonParser.stringToJson(DAOEmpleado.obtenerEmpleadoPorId(Json.createObjectBuilder().add("id", resultSet.getString("NIFCIF")).build().toString()));
        
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("idTurno", idTurno)
                .add("fechaCreacion", fechaCreacion.toString())
                .add("fechaTurno", fechaTurno.toString())
                .add("tipoTurno", sTipo)
                .add("operadores", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("id", jsonEmpleado.getString("nif"))
                                .add("nombre", jsonEmpleado.getString("nombre"))
                        )
                );
        JsonObject json = jsonBuilder.build();
        if(json==null){
            return null;
        }
        return json.toString();
    }

    public static void modificarOperadorEnTurno(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        String query = "UPDATE OPERADORESENTURNO SET NifCif = ? WHERE IdTurnoOperador = ? AND NifCif = ?";

        String nuevoNif = jsonInput.getString("nifNuevoEmpleado");
        int idTurnoOperador = jsonInput.getInt("idTurno");
        String antiguoNif = jsonInput.getString("nifEmpleadoACambiar");

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse); 
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nuevoNif);
            statement.setInt(2, idTurnoOperador);
            statement.setString(3, antiguoNif);
            statement.executeUpdate();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static String getTurnosDelOperadorPorUnDia(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder turnosArrayBuilder = Json.createArrayBuilder();

        String nif = jsonInput.getString("nif");
        Fecha fechaActual = Fecha.parseFecha(jsonInput.getString("fecha"));

        String query = "SELECT TURNODEOPERADOR.IDTURNOOPERADOR, FECHATURNO, FECHACREACION, NOMBRETIPOTURNO, NIFCIF, OPERADORESENTURNO.NIFCIF " +
                       "FROM TURNODEOPERADOR " +
                       "JOIN TIPODETURNODEOPERADOR ON TURNODEOPERADOR.TIPOTURNO = TIPODETURNODEOPERADOR.IDTIPOTURNO " +
                       "JOIN OPERADORESENTURNO ON TURNODEOPERADOR.IDTURNOOPERADOR = OPERADORESENTURNO.IDTURNOOPERADOR " +
                       "WHERE FECHATURNO=? AND OPERADORESENTURNO.NIFCIF=?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setDate(1, Fecha.convertirLocalDateToDateSQL(fechaActual));
            statement.setString(2, nif);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                JsonObjectBuilder turnoBuilder = Json.createObjectBuilder();
                turnoBuilder.add("IDTURNOOPERADOR", resultSet.getInt("IDTURNOOPERADOR"));
                turnoBuilder.add("FECHATURNO", resultSet.getDate("FECHATURNO").toString());
                turnoBuilder.add("FECHACREACION", resultSet.getDate("FECHACREACION").toString());
                turnoBuilder.add("NOMBRETIPOTURNO", resultSet.getString("NOMBRETIPOTURNO"));
                turnoBuilder.add("NIFCIF", resultSet.getString("NIFCIF"));
                turnosArrayBuilder.add(turnoBuilder);
            }
        }
        jsonBuilder.add("turnos", turnosArrayBuilder);
        JsonObject json = jsonBuilder.build();
        if(json==null){
            return null;
        }
        return json.toString();
    }
}
