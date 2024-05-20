/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso;

import com.mycompany.disenosoftwareproject.negocio.modelos.DetallesEmergencia;
import com.mycompany.disenosoftwareproject.negocio.modelos.Emergencia;
import com.mycompany.disenosoftwareproject.negocio.modelos.Turno;
import com.mycompany.disenosoftwareproject.persistencia.DAOEmergencia;
import com.mycompany.disenosoftwareproject.persistencia.DAOTurno;
import com.mycompany.disenosoftwareproject.servicios.AuthenticationService;

import java.util.List;

/**
 *
 * @author younrm
 */
public class ControladorCUConsultarEmergencias {
    private final AuthenticationService authenticationService;
    private final DAOTurno DAOturno;
    private final DAOEmergencia DAOemergencia;

    public ControladorCUConsultarEmergencias(AuthenticationService authenticationService, DAOTurno DAOturno, DAOEmergencia DAOemergencia) {
        this.authenticationService = authenticationService;
        this.DAOturno = DAOturno;
        this.DAOemergencia = DAOemergencia;
    }

    public boolean verificarCredenciales(String login, String password) {
        return authenticationService.autenticarUsuario(login, password);
    }

    public List<Turno> obtenerTurnosPorFecha(String fecha) throws Exception {
        return DAOturno.getTurnosPorFecha(fecha);
    }

    public List<Emergencia> obtenerDetallesEmergencias(Turno turno) throws Exception {
        return DAOemergencia.getEmergenciasPorTurno(turno);
    }

    public DetallesEmergencia obtenerInfoDetalladaEmergencia(Emergencia emergencia) throws Exception {
        return DAOemergencia.getDetallesEmergencia(emergencia);
    }
}