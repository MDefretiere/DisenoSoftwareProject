/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.Direccion;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
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
public class DAOEmpleado {
    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";

    public static JsonObject getAllEmpleados() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String query = "SELECT EMPLEADO.NIFCIF, EMPLEADO.PASSWORD, EMPLEADO.FECHAINICIOENEMPRESA, EMPLEADO.NOMBRE, EMPLEADO.APELLIDO, EMPLEADO.FECHANACIMIENTO, EMPLEADO.TELEFONO, ROLEMPLEADO.NOMBREROL, EMPLEADO.DIRECCIONPOSTAL, ROLESENLAEMPRESA.COMIENZOENROL " +
                       "FROM EMPLEADO " +
                       "JOIN ROLESENLAEMPRESA ON EMPLEADO.NIFCIF = ROLESENLAEMPRESA.NIFCIF " +
                       "JOIN ROLEMPLEADO ON ROLESENLAEMPRESA.ROL = ROLEMPLEADO.IDROL ";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                arrayBuilder.add(mapperResultSetToEmpleado(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JsonObjectBuilder resultBuilder = Json.createObjectBuilder();
        resultBuilder.add("empleados", arrayBuilder);

        return resultBuilder.build();
    }
    
    public static JsonObject getAllOperadores() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        String query = "SELECT EMPLEADO.NIFCIF, EMPLEADO.PASSWORD, EMPLEADO.FECHAINICIOENEMPRESA, EMPLEADO.NOMBRE, EMPLEADO.APELLIDO, EMPLEADO.FECHANACIMIENTO, EMPLEADO.TELEFONO, ROLEMPLEADO.NOMBREROL, EMPLEADO.DIRECCIONPOSTAL, ROLESENLAEMPRESA.COMIENZOENROL " +
                       "FROM EMPLEADO " +
                       "JOIN ROLESENLAEMPRESA ON EMPLEADO.NIFCIF = ROLESENLAEMPRESA.NIFCIF " +
                       "JOIN ROLEMPLEADO ON ROLESENLAEMPRESA.ROL = ROLEMPLEADO.IDROL " +
                       "WHERE ROLEMPLEADO.IDROL=3";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                arrayBuilder.add(mapperResultSetToEmpleado(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JsonObjectBuilder resultBuilder = Json.createObjectBuilder();
        resultBuilder.add("empleados", arrayBuilder);

        return resultBuilder.build();
    }


    // Mapping ResultSet to Empleado
    private static JsonObject mapperResultSetToEmpleado(ResultSet resultSet) throws SQLException {
        String nombre = resultSet.getString("nombre");
        String apellidos = resultSet.getString("apellido");
        String nif = resultSet.getString("NIFCIF");
        String fechaNacimiento = resultSet.getDate("fechaNacimiento").toString();
        Direccion direccion = Direccion.convertirDireccionSQL(resultSet);
        String telefono = resultSet.getString("telefono");
        String fechaInicio = resultSet.getDate("fechaInicioEnEmpresa").toString();
        String fechaInicioEnPuesto = resultSet.getDate("comienzoEnRol").toString();
        String sRol = resultSet.getString("NOMBREROL");

        JsonObjectBuilder rolBuilder = Json.createObjectBuilder();
        switch (sRol) {
            case "Gerente" -> rolBuilder.add("rol", "Gerente").add("fechaInicioEnPuesto", fechaInicioEnPuesto.toString());
            case "Conductor" -> rolBuilder.add("rol", "Conductor").add("fechaInicioEnPuesto", fechaInicioEnPuesto.toString());
            case "Operador" -> rolBuilder.add("rol", "Operador").add("fechaInicioEnPuesto", fechaInicioEnPuesto.toString());
            case "Medico" -> rolBuilder.add("rol", "Medico").add("fechaInicioEnPuesto", fechaInicioEnPuesto.toString());
            default -> rolBuilder.add("rol", "Unknown").add("fechaInicioEnPuesto", fechaInicioEnPuesto.toString());
        }

        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("nombre", nombre)
                .add("apellidos", apellidos)
                .add("nif", nif)
                .add("fechaNacimiento", fechaNacimiento)
                .add("direccion", direccion.toJson())
                .add("telefono", telefono)
                .add("fechaInicioEnEmpresa", fechaInicio)
                .add("rol", rolBuilder);

        return jsonBuilder.build();
    }
    
    public void enregistrerEmploye(Empleado employe) throws SQLException {
        String sql = "INSERT INTO employe (nif, nombre, apellidos, fechanacimiento, telefono) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employe.getApellidos());
            pstmt.setString(2, employe.getNombre());
            java.sql.Date dateNaissance = new java.sql.Date(employe.getFechaNacimiento().getTime());
            pstmt.setDate(3, dateNaissance);
            pstmt.setString(4, employe.getNif());
            pstmt.setString(5, employe.getTelefono());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject obtenerEmpleadoPorId(String id) {
        JsonObject employe = null;
        String query = "SELECT EMPLEADO.NIFCIF, EMPLEADO.PASSWORD, EMPLEADO.FECHAINICIOENEMPRESA, EMPLEADO.NOMBRE, EMPLEADO.APELLIDO, EMPLEADO.FECHANACIMIENTO, EMPLEADO.TELEFONO, ROLEMPLEADO.NOMBREROL, EMPLEADO.DIRECCIONPOSTAL, ROLESENLAEMPRESA.COMIENZOENROL " +
                        "FROM EMPLEADO " +
                        "JOIN ROLESENLAEMPRESA ON EMPLEADO.NIFCIF = ROLESENLAEMPRESA.NIFCIF " +
                        "JOIN ROLEMPLEADO ON ROLESENLAEMPRESA.ROL = ROLEMPLEADO.IDROL " +
                        "WHERE EMPLEADO.NifCif = ?";


        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, id);
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
