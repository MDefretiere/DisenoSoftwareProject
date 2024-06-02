/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author defre
 */
public class Direccion {
    private static int numero;
    private static String nombreVia;
    private static String otros;
    private static String codigoPostal;
    private static String localidad;
    private static String provincia;
    
    public Direccion(int numero, String nombreVia, String otros, String codigoPostal, String localidad, String provincia) {
        this.numero = numero;
        this.nombreVia = nombreVia;
        this.otros = otros;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Direccion(int numero, String nombreVia, String codigoPostal, String localidad, String provincia) {
        this.numero = numero;
        this.nombreVia = nombreVia;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.otros = "";
    }
    
    public static Direccion convertirDireccionSQL(ResultSet resultSet) throws SQLException {
        String direccionString = resultSet.getString("direccionpostal");
        String[] parts = direccionString.split(", ");
        
        if (parts.length == 5) {
            numero = Integer.parseInt(parts[0]);
            nombreVia = parts[1];
            codigoPostal = parts[2];
            localidad = parts[3];
            provincia = parts[4];
        } else {
            System.err.println("Format d'adresse invalide.");
        }
        
        return new Direccion(numero, nombreVia, otros, codigoPostal, localidad, provincia);
    }
    
    public JsonObject toJson() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
            .add("numero", numero)
            .add("nombreVia", nombreVia)
            .add("otros", "")
            .add("codigoPostal", codigoPostal)
            .add("localidad", localidad)
            .add("provincia", provincia);

        return jsonBuilder.build();
    }
    
    public static Direccion fromJson(JsonObject jsonDireccion) {
        int numero = jsonDireccion.getInt("numero");
        String nombreVia = jsonDireccion.getString("nombreVia");
        String otros = jsonDireccion.getString("otros", ""); // Utilisez la valeur par défaut si "otros" n'est pas présente dans le JSON
        String codigoPostal = jsonDireccion.getString("codigoPostal");
        String localidad = jsonDireccion.getString("localidad");
        String provincia = jsonDireccion.getString("provincia");

        return new Direccion(numero, nombreVia, otros, codigoPostal, localidad, provincia);
    }

}
