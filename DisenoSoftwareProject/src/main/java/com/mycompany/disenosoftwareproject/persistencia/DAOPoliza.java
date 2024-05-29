/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author defre
 */
public class DAOPoliza {
    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";
    
    public static String getAllPoliza() {
        JsonObjectBuilder resultBuilder = Json.createObjectBuilder(); 
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); 
        String query = "SELECT numeroPoliza, fechaInicio, fechaVencimiento FROM POLIZAS";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String numeroPoliza = resultSet.getString("numeroPoliza");
                String fechaInicio = resultSet.getTimestamp("fechaInicio").toString();
                String fechaVencimiento = resultSet.getTimestamp("fechaVencimiento").toString();

                JsonObjectBuilder polizaBuilder = Json.createObjectBuilder()
                        .add("numeroPoliza", numeroPoliza)
                        .add("fechaInicio", fechaInicio)
                        .add("fechaVencimiento", fechaVencimiento);

                arrayBuilder.add(polizaBuilder);
            }
            resultBuilder.add("polizas", arrayBuilder); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JsonObject json = resultBuilder.build();
        if(json==null){
            return null;
        }
        return json.toString();
    }
}
