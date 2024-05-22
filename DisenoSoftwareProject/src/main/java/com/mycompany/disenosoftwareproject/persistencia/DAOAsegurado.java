package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.Direccion;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Sexo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author defre
 */
public class DAOAsegurado {
    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";
    
    public static JsonObject comprobarAsegurado(JsonObject jsonInput) {
        JsonObject json = null;
        String nombre = jsonInput.getString("nombre");
        String apellidos = jsonInput.getString("apellidos");
        Fecha fechaNacimiento = Fecha.parseFecha(jsonInput.getString("fechaNacimiento"));
        Sexo sexo = Sexo.valueOf(jsonInput.getString("sexo"));
        
        String query = "SELECT nombre, apellidos, fechaNacimiento, NIFCIF, direccionPostal, telefono, numerodecuenta " +
                       "FROM ASEGURADOS " +
                       "WHERE nombre=? AND apellidos=? AND fechaNacimiento=? AND sexo=?";
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setDate(3, Fecha.convertirLocalDateToDateSQL(fechaNacimiento));
            statement.setString(4, sexo.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nif = resultSet.getString("NIFCIF");
                Direccion direccion = Direccion.convertirDireccionSQL(resultSet);
                String telefono = resultSet.getString("telefono");
                String numeroDeCuenta = resultSet.getString("numerodecuenta");
                
                JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                        .add("nombre", nombre)
                        .add("apellidos", apellidos)
                        .add("nif", nif)
                        .add("fechaNacimiento", fechaNacimiento.toString())
                        .add("direccion", direccion.toJson())
                        .add("telefono", telefono)
                        .add("sexo", sexo.toString())
                        .add("numeroDeCuenta", numeroDeCuenta);
                json = jsonBuilder.build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JsonObject comprobarPacienteConPoliza(JsonObject jsonInput) {
        JsonObject json = null;

        // Extraire les valeurs du JsonObject
        String nif = jsonInput.getString("nif");
        String numero = jsonInput.getString("numeroPoliza");

        String query = "SELECT COUNT(*) AS count FROM POLIZASCONTRATADAS WHERE Asegurado=? AND Poliza=?";
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nif);
            statement.setString(2, numero);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                boolean existe = count == 1;

                json = Json.createObjectBuilder()
                        .add("existe", existe)
                        .build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
