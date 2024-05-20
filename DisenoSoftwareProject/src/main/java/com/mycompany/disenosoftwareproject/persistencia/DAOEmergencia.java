package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.DetallesEmergencia;
import com.mycompany.disenosoftwareproject.negocio.modelos.Emergencia;
import com.mycompany.disenosoftwareproject.negocio.modelos.Turno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author younrm
 */
public class DAOEmergencia {
    private final Connection connection;

    public DAOEmergencia(Connection connection) {
        this.connection = connection;
    }
    
    public List<Emergencia> getEmergenciasPorTurno(Turno turno) throws SQLException {
        List<Emergencia> emergencias = new ArrayList<>();
        String query = "SELECT * FROM EMERGENCIAS WHERE TurnoId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, turno.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Emergencia emergencia = convertirResultSetAEmergencia(resultSet);
                    emergencias.add(emergencia);
                }
            }
        }
        return emergencias;
    }
    
    public DetallesEmergencia getDetallesEmergencia(Emergencia emergencia) throws SQLException {
        DetallesEmergencia detallesEmergencia = null;
        String query = "SELECT * FROM DETALLES_EMERGENCIA WHERE IdEmergencia = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, emergencia.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    detallesEmergencia = convertirResultSetADetallesEmergencia(resultSet);
                }
            }
        }
        return detallesEmergencia;
    }

    private Emergencia convertirResultSetAEmergencia(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("IdEmergencia");
        String descripcion = resultSet.getString("Descripcion");
        String fechaActivacion = resultSet.getString("FechaActivacion");
        String horaActivacion = resultSet.getString("HoraActivacion");
        String nombreVia = resultSet.getString("NombreVia");
        String fechaAtencion = resultSet.getString("FechaAtencion");
        String horaAtencion = resultSet.getString("HoraAtencion");
        boolean trasladoHospital = resultSet.getBoolean("TrasladoHospital");
        return new Emergencia(id, descripcion, fechaActivacion, horaActivacion, nombreVia, fechaAtencion, horaAtencion, trasladoHospital);
    }

    private DetallesEmergencia convertirResultSetADetallesEmergencia(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("IdDetalle");
        String descripcion = resultSet.getString("Descripcion");
        String nombreVia = resultSet.getString("NombreVia");
        String fechaAtencionPaciente = resultSet.getString("FechaAtencionPaciente");
        String horaAtencionPaciente = resultSet.getString("HoraAtencionPaciente");
        boolean trasladoHospital = resultSet.getBoolean("TrasladoHospital");
        return new DetallesEmergencia(id, descripcion, nombreVia, fechaAtencionPaciente, horaAtencionPaciente, trasladoHospital);
    }
}