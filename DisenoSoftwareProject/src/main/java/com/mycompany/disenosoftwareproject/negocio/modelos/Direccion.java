/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
