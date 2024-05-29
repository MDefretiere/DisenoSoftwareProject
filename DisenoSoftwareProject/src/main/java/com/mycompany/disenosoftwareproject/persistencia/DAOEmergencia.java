package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.DetallesEmergencia;
import com.mycompany.disenosoftwareproject.negocio.modelos.Emergencia;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import serviciosComunes.JsonParser;

/**
 *
 * @author younrm
 */
public class DAOEmergencia {

    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";

    public static String getActivacionesPorTurno(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        int idTurno = jsonInput.getInt("idTurno");
        JsonObjectBuilder turnoBuilder = Json.createObjectBuilder();
        JsonArrayBuilder activacionesArrayBuilder = Json.createArrayBuilder();

        String query = "SELECT FechaActivacion, HoraActivacion, ACTIVACIONESDEOPERATIVOS.ID "
                + "FROM ACTIVACIONESDEOPERATIVOS "
                + "JOIN OPERATIVOS ON OPERATIVOS.ID = ACTIVACIONESDEOPERATIVOS.OperativoActivado "
                + "JOIN TURNOSDEOPERATIVO ON OPERATIVOS.Turno = TURNOSDEOPERATIVO.ID "
                + "WHERE TURNOSDEOPERATIVO.ID = ? ";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idTurno);
            ResultSet resultSet = statement.executeQuery();

            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                JsonObjectBuilder activacionBuilder = Json.createObjectBuilder()
                        .add("FechaActivacion", resultSet.getString("FechaActivacion"))
                        .add("HoraActivacion", resultSet.getString("HoraActivacion"))
                        .add("ID", resultSet.getInt("ID"));
                activacionesArrayBuilder.add(activacionBuilder);
            }

            if (!hasResults) {
                JsonObjectBuilder activacionBuilder = Json.createObjectBuilder()
                        .add("IDTurnoOperativo", -1);
                activacionesArrayBuilder.add(activacionBuilder);
            }
        }
        turnoBuilder.add("activaciones", activacionesArrayBuilder);
        JsonObject json = turnoBuilder.build();
        if(json==null){
            return null;
        }
        return json.toString();
    }

    public static String getDetallesActivaciones(String stringInput) throws SQLException {
        JsonObject jsonInput = JsonParser.stringToJson(stringInput);
        int idActivacion = jsonInput.getInt("idActivacion");
        JsonObjectBuilder activacionBuilder = Json.createObjectBuilder();

        String query = "SELECT LLAMADASASEGURADOS.DescripcionDeLaEmergencia, DIRECCIONES.nombreVia, "
                + "ACTIVACIONESDEOPERATIVOS.FechaSeHaceCargoMedico, ACTIVACIONESDEOPERATIVOS.HoraSeHaceCargoMedico, "
                + "ACTIVACIONESDEOPERATIVOS.DecisionTrasladoAHospital "
                + "FROM ACTIVACIONESDEOPERATIVOS "
                + "JOIN LLAMADASNOCRITICAS ON ACTIVACIONESDEOPERATIVOS.ID = LLAMADASNOCRITICAS.RequiereOperativo "
                + "JOIN LLAMADASASEGURADOS ON LLAMADASNOCRITICAS.ID = LLAMADASASEGURADOS.ID "
                + "JOIN DIRECCIONES ON DIRECCIONES.ID = ACTIVACIONESDEOPERATIVOS.DireccionDondeAcudir "
                + "WHERE ACTIVACIONESDEOPERATIVOS.ID = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idActivacion);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                activacionBuilder.add("DescripcionDeLaEmergencia", resultSet.getString("DescripcionDeLaEmergencia"))
                        .add("nombreVia", resultSet.getString("nombreVia"))
                        .add("fechaSeHaceCargoMedico", resultSet.getString("FechaSeHaceCargoMedico"))
                        .add("horaSeHaceCargoMedico", resultSet.getString("HoraSeHaceCargoMedico"))
                        .add("decisionTrasladoAHospital", resultSet.getInt("DecisionTrasladoAHospital"));
            } else {
                activacionBuilder.add("IDActivacion", -1);
            }
        }
        JsonObject json = activacionBuilder.build();
        if(json==null){
            return null;
        }
        return json.toString();
    }
}
