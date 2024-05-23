package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.Direccion;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
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
 * Auteur: defre
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
                       "WHERE ROLEMPLEADO.IDROL=2";

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
    private static JsonObjectBuilder mapperResultSetToEmpleado(ResultSet resultSet) throws SQLException {
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
            case "Gerente" -> rolBuilder.add("rol", "Gerente").add("fechaInicioEnPuesto", fechaInicioEnPuesto);
            case "Conductor" -> rolBuilder.add("rol", "Conductor").add("fechaInicioEnPuesto", fechaInicioEnPuesto);
            case "Operador" -> rolBuilder.add("rol", "Operador").add("fechaInicioEnPuesto", fechaInicioEnPuesto);
            case "Medico" -> rolBuilder.add("rol", "Medico").add("fechaInicioEnPuesto", fechaInicioEnPuesto);
            default -> rolBuilder.add("rol", "Unknown").add("fechaInicioEnPuesto", fechaInicioEnPuesto);
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

        return jsonBuilder;
    }
    
    public void enregistrerEmploye(JsonObject jsonInput) throws SQLException {
        String sql = "INSERT INTO EMPLEADO (nif, nombre, apellidos, fechanacimiento, telefono) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String nif = jsonInput.getString("nif");
            String nombre = jsonInput.getString("nombre");
            String apellidos = jsonInput.getString("apellidos");
            java.sql.Date fechaNacimiento = java.sql.Date.valueOf(jsonInput.getString("fechaNacimiento"));
            String telefono = jsonInput.getString("telefono");

            pstmt.setString(1, nif);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellidos);
            pstmt.setDate(4, fechaNacimiento);
            pstmt.setString(5, telefono);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject obtenerEmpleadoPorId(JsonObject jsonInput) {
        JsonObjectBuilder employe = null;
        String id = jsonInput.getString("nif");
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

        return employe.build();
    }
    
    public static JsonObject comprobarLoginYContrasena(JsonObject jsonInput) {
        JsonObjectBuilder employe = null;
        String id = jsonInput.getString("nif");
        String password = jsonInput.getString("password");
        String query = "SELECT EMPLEADO.NIFCIF, EMPLEADO.PASSWORD, EMPLEADO.FECHAINICIOENEMPRESA, EMPLEADO.NOMBRE, EMPLEADO.APELLIDO, EMPLEADO.FECHANACIMIENTO, EMPLEADO.TELEFONO, ROLEMPLEADO.NOMBREROL, EMPLEADO.DIRECCIONPOSTAL, ROLESENLAEMPRESA.COMIENZOENROL, DISPONIBILIDADEMPLEADO.Estado " +
                       "FROM EMPLEADO " +
                       "JOIN ROLESENLAEMPRESA ON EMPLEADO.NIFCIF = ROLESENLAEMPRESA.NIFCIF " +
                       "JOIN ROLEMPLEADO ON ROLESENLAEMPRESA.ROL = ROLEMPLEADO.IDROL " +
                       "JOIN DISPONIBILIDADEMPLEADO ON DISPONIBILIDADEMPLEADO.Empleado = EMPLEADO.NIFCIF " +
                       "WHERE EMPLEADO.NifCif = ? AND Empleado.password = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employe = mapperResultSetToEmpleado(resultSet);
                employe.add("disponibilidad", resultSet.getInt("Estado"));
            }
            else{
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employe.build();
    }
}
