/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
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
 *
 * @author younrm
 */
public class DAOTurnoDeOperativo {

    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";

    public static String getTurnosPorConductorYFecha(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        String nif = jsonInput.getString("nif");
        Fecha fechaTurno = Fecha.parseFecha(jsonInput.getString("fechaTurno"));
        JsonObjectBuilder turnoBuilder = Json.createObjectBuilder();

        String query = "SELECT ID "
                + "FROM TURNOSDEOPERATIVO "
                + "JOIN CONDUCTORESENTURNO ON TURNOSDEOPERATIVO.ID = CONDUCTORESENTURNO.Turno "
                + "WHERE CONDUCTORESENTURNO.Conductor = ? AND TURNOSDEOPERATIVO.FechaTurno = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nif);
            statement.setDate(2, Fecha.convertirLocalDateToDateSQL(fechaTurno));
            ResultSet resultSet = statement.executeQuery();

            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                turnoBuilder.add("IDTurnoOperativo", resultSet.getInt("ID"));
            }

            if (!hasResults) {
                turnoBuilder.add("IDTurnoOperativo", -1);
            }
        }
        JsonObject json = turnoBuilder.build();
        if(json==null){
            return null;
        }
        return json.toString();
    }

}
