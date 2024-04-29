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
    private int numero;
    private String nombreVia;
    private String otros;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    
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
        int numero = resultSet.getInt("numero");
        String nombreVia = resultSet.getString("nombre_via");
        String otros = resultSet.getString("otros");
        String codigoPostal = resultSet.getString("codigo_postal");
        String localidad = resultSet.getString("localidad");
        String provincia = resultSet.getString("provincia");
        
        return new Direccion(numero, nombreVia, otros, codigoPostal, localidad, provincia);
    }
}
