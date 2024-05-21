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
    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";

    public static List<Empleado> getAllEmpleados() {
        List<Empleado> listEmpleados = new ArrayList<>();
        String query = "SELECT EMPLEADO.NIFCIF, EMPLEADO.PASSWORD, EMPLEADO.FECHAINICIOENEMPRESA, EMPLEADO.NOMBRE, EMPLEADO.APELLIDO, EMPLEADO.FECHANACIMIENTO, EMPLEADO.TELEFONO, ROLEMPLEADO.NOMBREROL, EMPLEADO.DIRECCIONPOSTAL, ROLESENLAEMPRESA.COMIENZOENROL " +
                        "FROM EMPLEADO " +
                        "JOIN ROLESENLAEMPRESA ON EMPLEADO.NIFCIF = ROLESENLAEMPRESA.NIFCIF " +
                        "JOIN ROLEMPLEADO ON ROLESENLAEMPRESA.ROL = ROLEMPLEADO.IDROL ";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                listEmpleados.add(mapperResultSetToEmpleado(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listEmpleados;
    }

    // Mapping ResultSet to Empleado
    private static Empleado mapperResultSetToEmpleado(ResultSet resultSet) throws SQLException {
        String nombre = resultSet.getString("nombre");
        String apellidos = resultSet.getString("apellido");
        String nif = resultSet.getString("NIFCIF");
        String fechaNacimientoString = resultSet.getString("fechaNacimiento");
        java.sql.Date fechaNacimientoSQL = java.sql.Date.valueOf(fechaNacimientoString);
        Fecha fechaNacimiento = Fecha.convertirDateSQLToLocalDate(fechaNacimientoSQL);
        Direccion direccion = Direccion.convertirDireccionSQL(resultSet);
        String telefono = resultSet.getString("telefono");
        String fechaInicioEnEmpresaString = resultSet.getString("fechaINICIOENEMPRESA");
        java.sql.Date fechaInicioEnEmpresaSQL = java.sql.Date.valueOf(fechaInicioEnEmpresaString);
        Fecha fechaInicioEnEmpresa = Fecha.convertirDateSQLToLocalDate(fechaInicioEnEmpresaSQL);
        String fechaInicioEnPuestoString = resultSet.getString("COMIENZOENROL");
        java.sql.Date fechaInicioEnPuestoSQL = java.sql.Date.valueOf(fechaInicioEnPuestoString);
        Fecha fechaInicioEnPuesto = Fecha.convertirDateSQLToLocalDate(fechaInicioEnPuestoSQL);
        String sRol = resultSet.getString("NOMBREROL");
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

    public static Empleado obtenerEmpleadoPorId(int id) {
        Empleado employe = null;
        String query = "SELECT EMPLEADO.NIFCIF, EMPLEADO.PASSWORD, EMPLEADO.FECHAINICIOENEMPRESA, EMPLEADO.NOMBRE, EMPLEADO.APELLIDO, EMPLEADO.FECHANACIMIENTO, EMPLEADO.TELEFONO, ROLEMPLEADO.NOMBREROL, EMPLEADO.DIRECCIONPOSTAL, ROLESENLAEMPRESA.COMIENZOENROL " +
                        "FROM EMPLEADO " +
                        "JOIN ROLESENLAEMPRESA ON EMPLEADO.NIFCIF = ROLESENLAEMPRESA.NIFCIF " +
                        "JOIN ROLEMPLEADO ON ROLESENLAEMPRESA.ROL = ROLEMPLEADO.IDROL " +
                        "WHERE EMPLEADO.NifCif = ?";


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
