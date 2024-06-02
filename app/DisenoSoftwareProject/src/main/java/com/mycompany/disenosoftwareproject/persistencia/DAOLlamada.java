/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.Consejo;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Hora;
import com.mycompany.disenosoftwareproject.negocio.modelos.Llamada;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaCritica;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaDeAsegurado;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaInfundada;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaNoCritica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import serviciosComunes.JsonParser;

/**
 *
 * @author defre
 */
public class DAOLlamada {

    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";

    public static String getMaxId() {
        String query = "SELECT MAX(id) AS max_id FROM LLAMADAS";
        JsonObjectBuilder jsonBuilder=null;

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = conn.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int maxId = resultSet.getInt("max_id");

                jsonBuilder = Json.createObjectBuilder()
                        .add("max_id", maxId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject json = jsonBuilder.build();
        if(json==null){
            return null;
        }
        return json.toString();
    }

    public static void grabarNuevaLlamada(String stringInput) throws Exception {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        String query = "INSERT INTO LLAMADAS (ID, NumeroTelefonoOrigen, FechaInicio, HoraInicio, FechaFin, HoraFin, NombreComunicante) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int id = jsonInput.getInt("id");
        String numeroTelefonoOrigen = jsonInput.getString("numeroTelefonoOrigen");
        Fecha fechaInicio = Fecha.parseFecha(jsonInput.getString("fechaInicio"));
        Hora horaInicio = Hora.parseHora(jsonInput.getString("horaInicio"));
        Fecha fechaFin = Fecha.parseFecha(jsonInput.getString("fechaFin"));
        Hora horaFin = Hora.parseHora(jsonInput.getString("horaFin"));
        String nombreComunicante = jsonInput.getString("nombreComunicante");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, numeroTelefonoOrigen);
            statement.setDate(3, Fecha.convertirLocalDateToDateSQL(fechaInicio));
            statement.setTime(4, Hora.convertirHoraToTimeSQL(horaInicio));
            statement.setDate(5, Fecha.convertirLocalDateToDateSQL(fechaFin));
            statement.setTime(6, Hora.convertirHoraToTimeSQL(horaFin));
            statement.setString(7, nombreComunicante);
            statement.executeUpdate();
        }

        String tipoLlamada = jsonInput.getString("tipo");
        switch (tipoLlamada) {
            case "LlamadaCritica":
                grabarNuevaLlamadaCritica(jsonInput.toString());
                break;
            case "LlamadaNoCritica":
                grabarNuevaLlamadaNoCritica(jsonInput.toString());
                break;
            case "LlamadaInfundada":
                grabarNuevaLlamadaInfundada(jsonInput.toString());
                break;
            default:
                throw new Exception("ERROR: tipo de llamada");
        }
    }

    public static void grabarNuevaLlamadaCritica(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        grabarNuevaLlamadaDeAsegurado(jsonInput.toString());

        String query = "INSERT INTO LLAMADASCRITICAS (ID) VALUES (?)";
        int id = jsonInput.getInt("id");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public static void grabarNuevaLlamadaNoCritica(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        grabarNuevaLlamadaDeAsegurado(jsonInput.toString());

        String query= null;
        int id = jsonInput.getInt("id");
        boolean esLeve = jsonInput.getBoolean("esLeve");
        int requiereOperativo = jsonInput.getInt("requiereOperativo");

        if (requiereOperativo == -1) {
            query = "INSERT INTO LLAMADASNOCRITICAS (ID, EsLeve) VALUES (?, ?)";
            try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse); 
                    PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.setBoolean(2, esLeve);
                statement.executeUpdate();
            }
        } else {
            query = "INSERT INTO LLAMADASNOCRITICAS (ID, EsLeve, RequiereOperativo) VALUES (?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse); 
                    PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.setBoolean(2, esLeve);
                statement.setInt(3, requiereOperativo);
                statement.executeUpdate();
            }
        }

        JsonArray consejos = jsonInput.getJsonArray("consejos");
        for (int i = 0; i < consejos.size(); i++) {
            JsonObject consejo = consejos.getJsonObject(i);
            grabarNuevoConsejo(consejo.toString());
        }
    }

    public static void grabarNuevaLlamadaDeAsegurado(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        String query = "INSERT INTO LLAMADASASEGURADOS (ID, DescripcionDeLaEmergencia, Paciente) VALUES (?, ?, ?)";
        int id = jsonInput.getInt("id");
        String descripcionEmergencia = jsonInput.getString("descripcionEmergencia");
        String pacienteNif = jsonInput.getString("pacienteNif");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, descripcionEmergencia);
            statement.setString(3, pacienteNif);
            statement.executeUpdate();
        }
    }

    public static void grabarNuevaLlamadaInfundada(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        String query = "INSERT INTO LLAMADASINFUNDADAS (ID) VALUES (?)";
        int id = jsonInput.getInt("id");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public static void grabarNuevoConsejo(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        String query = "INSERT INTO CONSEJOS (Descripcion, Resultado, Soluciona, Llamada) VALUES (?, ?, ?, ?)";
        String descripcion = jsonInput.getString("descripcion");
        String resultado = jsonInput.getString("resultado");
        boolean soluciona = jsonInput.getBoolean("soluciona");
        int idLlamada = jsonInput.getInt("idLlamada");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, descripcion);
            statement.setString(2, resultado);
            statement.setBoolean(3, soluciona);
            statement.setInt(4, idLlamada);
            statement.executeUpdate();
        }
    }
}