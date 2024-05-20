    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.servicios;

import com.mycompany.disenosoftwareproject.negocio.modelos.DetallesEmergencia;
import com.mycompany.disenosoftwareproject.negocio.modelos.Emergencia;
import com.mycompany.disenosoftwareproject.persistencia.DAOEmergencia;
import com.mycompany.disenosoftwareproject.negocio.modelos.Turno;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author younrm
 */
public class EmergencyServiceHandler {

    private final DAOEmergencia DAOemergencia;

    public EmergencyServiceHandler(DAOEmergencia DAOemergencia) {
        this.DAOemergencia = DAOemergencia;
    }

    public List<Emergencia> getEmergenciasPorTurno(Turno turno) throws SQLException {
        return DAOemergencia.getEmergenciasPorTurno(turno);
    }

    public DetallesEmergencia getInfoDetalladaEmergencia(Emergencia emergencia) throws SQLException {
        return DAOemergencia.getDetallesEmergencia(emergencia);
    }
}