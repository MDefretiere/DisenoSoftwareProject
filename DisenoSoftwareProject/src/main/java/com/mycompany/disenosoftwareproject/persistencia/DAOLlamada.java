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

/**
 *
 * @author defre
 */
public class DAOLlamada {
    
    //private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String url = "jdbc:postgresql://4.tcp.eu.ngrok.io:13455/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";
    
    public static JsonObject getMaxId() {
        JsonObject json = null;
        String query = "SELECT MAX(id) AS max_id FROM LLAMADAS";
        
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = conn.prepareStatement(query)) {
             
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                int maxId = resultSet.getInt("max_id");
                
                JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                        .add("max_id", maxId);
                
                json = jsonBuilder.build();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return json;
    }
    
    public static void grabarNuevaLlamada(JsonObject jsonInput) throws Exception {
        String query = "INSERT INTO LLAMADAS (ID, NumeroTelefonoOrigen, FechaInicio, HoraInicio, FechaFin, HoraFin, NombreComunicante) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int id = jsonInput.getInt("id");
        String numeroTelefonoOrigen = jsonInput.getString("numeroTelefonoOrigen");
        Fecha fechaInicio = Fecha.parseFecha(jsonInput.getString("fechaInicio"));
        Hora horaInicio = Hora.parseHora(jsonInput.getString("horaInicio"));
        Fecha fechaFin = Fecha.parseFecha(jsonInput.getString("fechaFin"));
        Hora horaFin = Hora.parseHora(jsonInput.getString("horaFin"));
        String nombreComunicante = jsonInput.getString("nombreComunicante");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {
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
                grabarNuevaLlamadaCritica(jsonInput);
                break;
            case "LlamadaNoCritica":
                grabarNuevaLlamadaNoCritica(jsonInput);
                break;
            case "LlamadaInfundada":
                grabarNuevaLlamadaInfundada(jsonInput);
                break;
            default:
                throw new Exception("ERROR: tipo de llamada");
        }
    }
    
    public static void grabarNuevaLlamadaCritica(JsonObject jsonInput) throws SQLException {
        grabarNuevaLlamadaDeAsegurado(jsonInput);

        String query = "INSERT INTO LLAMADASCRITICAS (ID) VALUES (?)";
        int id = jsonInput.getInt("id");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    
    public static void grabarNuevaLlamadaNoCritica(JsonObject jsonInput) throws SQLException {
        grabarNuevaLlamadaDeAsegurado(jsonInput);

        String query = "INSERT INTO LLAMADASNOCRITICAS (ID, EsLeve, RequiereOperativo) VALUES (?, ?, ?)";
        int id = jsonInput.getInt("id");
        boolean esLeve = jsonInput.getBoolean("esLeve");
        boolean requiereOperativo = jsonInput.getBoolean("requiereOperativo");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setBoolean(2, esLeve);
            statement.setBoolean(3, requiereOperativo);
            statement.executeUpdate();
        }

        JsonArray consejos = jsonInput.getJsonArray("consejos");
        for (int i = 0; i < consejos.size(); i++) {
            JsonObject consejo = consejos.getJsonObject(i);
            grabarNuevoConsejo(consejo, id);
        }
    }
    
    public static void grabarNuevaLlamadaDeAsegurado(JsonObject jsonInput) throws SQLException {
        String query = "INSERT INTO LLAMADASASEGURADOS (ID, DescripcionDeLaEmergencia, Paciente) VALUES (?, ?, ?)";
        int id = jsonInput.getInt("id");
        String descripcionEmergencia = jsonInput.getString("descripcionEmergencia");
        String pacienteNif = jsonInput.getString("pacienteNif");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, descripcionEmergencia);
            statement.setString(3, pacienteNif);
            statement.executeUpdate();
        }
    }
    
    public static void grabarNuevaLlamadaInfundada(JsonObject jsonInput) throws SQLException {
        String query = "INSERT INTO LLAMADASINFUNDADAS (ID) VALUES (?)";
        int id = jsonInput.getInt("id");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    
    public static void grabarNuevoConsejo(JsonObject consejo, int idLlamada) throws SQLException {
        String query = "INSERT INTO CONSEJOS (Descripcion, Resultado, Soluciona, Llamada) VALUES (?, ?, ?, ?)";
        String descripcion = consejo.getString("descripcion");
        String resultado = consejo.getString("resultado");
        boolean soluciona = consejo.getBoolean("soluciona");

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, descripcion);
            statement.setString(2, resultado);
            statement.setBoolean(3, soluciona);
            statement.setInt(4, idLlamada);
            statement.executeUpdate();
        }
    }
}