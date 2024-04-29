/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.Conductor;
import com.mycompany.disenosoftwareproject.negocio.modelos.Direccion;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Gerente;
import com.mycompany.disenosoftwareproject.negocio.modelos.Medico;
import com.mycompany.disenosoftwareproject.negocio.modelos.Operador;
import com.mycompany.disenosoftwareproject.negocio.modelos.Rol;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author defre
 */
public class DAOEmpleado {
    private final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private final String utilisateur = "";
    private final String motDePasse = "";

    public List<Empleado> obtenirTousLesEmployes() {
        List<Empleado> listeEmployes = new ArrayList<>();
        String query = "SELECT EMPLEADOS.NIF as NIF, EMPLEADOS.PASSWORD, EMPLEADOS.FECHAINICIOENEMPRESA, PERSONAS.NOMBRE, PERSONAS.APELLIDOS, PERSONAS.FECHADENACIMIENTO, PERSONAS.TELEFONO, TIPOSDEROL.NOMBRETIPO \n" +
            "FROM PERSONAS, EMPLEADOS, TIPOSDEROL, ROLESENEMPRESA \n" +
            "WHERE PERSONAS.NIF=EMPLEADOS.NIF, TIPOSDEROL.IDTIPO=ROLESENEMPRESA.IDTIPO, ROLESENEMPRESA.NIF=EMPLEADOS.NIF;";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Créer un objet Empleado à partir des données de la base de données
                Empleado employe = mapperResultSetToEmpleado(resultSet);
                listeEmployes.add(employe);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listeEmployes;
    }

    // Mapping ResultSet to Empleado
    private Empleado mapperResultSetToEmpleado(ResultSet resultSet) throws SQLException {
        String nombre = resultSet.getString("nombre");
        String apellidos = resultSet.getString("apellidos");
        String nif = resultSet.getString("NIF");
        String fechaNacimientoString = resultSet.getString("fechaNacimiento");
        java.sql.Date fechaNacimientoSQL = java.sql.Date.valueOf(fechaNacimientoString);
        Fecha fechaNacimiento = Fecha.convertirDateSQLToLocalDate(fechaNacimientoSQL);
        Direccion direccion = Direccion.convertirDireccionSQL(resultSet);
        String telefono = resultSet.getString("telefono");
        String fechaInicioEnEmpresaString = resultSet.getString("fechaNacimiento");
        java.sql.Date fechaInicioEnEmpresaSQL = java.sql.Date.valueOf(fechaInicioEnEmpresaString);
        Fecha fechaInicioEnEmpresa = Fecha.convertirDateSQLToLocalDate(fechaInicioEnEmpresaSQL);

        String sRol = resultSet.getString("Rol");
        String fechaInicioEnPuestoString = resultSet.getString("fechaNacimiento");
        java.sql.Date fechaInicioEnPuestoSQL = java.sql.Date.valueOf(fechaInicioEnPuestoString);
        Fecha fechaInicioEnPuesto = Fecha.convertirDateSQLToLocalDate(fechaInicioEnPuestoSQL);
        Rol rol=null;
        switch(sRol){
            case "Gerente" -> {
                rol = new Gerente(fechaInicioEnPuesto);
            }
            case "Conductor" -> {
                rol = new Conductor(fechaInicioEnPuesto);
            }
            case "Operador" -> {
                rol = new Operador(fechaInicioEnPuesto);
            }
            case "Medico" -> {
                rol = new Medico(fechaInicioEnPuesto);
            }
                
        }
        Empleado employe = new Empleado(nombre, apellidos, fechaNacimiento, nif, direccion, telefono, fechaInicioEnEmpresa, rol);
        return employe;
    }

    
    public void enregistrerEmploye(Empleado employe) {
        /*
        String query = "INSERT INTO empleados ( ) VALUES ()";
        
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, employe.getNombre());
            statement.setString(2, employe.getApellidos());
            // Autres valeurs...
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }*/
    }

    
    public Empleado obtenirEmployeParId(int id) {
        Empleado employe = null;
        String query = "SELECT * FROM empleados WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employe = mapperResultSetToEmpleado(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employe;
    }
}
