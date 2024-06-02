/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaConsultarEmergencias;
import com.mycompany.disenosoftwareproject.negocio.modelos.Activacion;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Hora;
import com.mycompany.disenosoftwareproject.persistencia.DAOEmergencia;
import com.mycompany.disenosoftwareproject.persistencia.DAOTurnoDeOperativo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import serviciosComunes.JsonParser;

/**
 *
 * @author defre
 */
public class ControladorCUConsultarEmergencias {
    private static ControladorCUConsultarEmergencias controlador = new ControladorCUConsultarEmergencias();
    private Empleado empleadoIdentificado;
    
    public static ControladorCUConsultarEmergencias getInstance(){
        return controlador;
    }
    
    public void setEmpleadoIdentificado(Empleado e){
        empleadoIdentificado=e;
    }
    
    public void start(){
        CtrlVistaConsultarEmergencias.open();
    }

    public List<Activacion> getActivacionesPorFecha(Fecha fecha) throws SQLException, Exception {
        JsonObjectBuilder jsonInputFecha = Json.createObjectBuilder();
        jsonInputFecha.add("nif", empleadoIdentificado.getNif());
        jsonInputFecha.add("fechaTurno", fecha.toString());
        JsonObject jsonTurno = JsonParser.stringToJson(DAOTurnoDeOperativo.getTurnosPorConductorYFecha(jsonInputFecha.build().toString()));
        int idTurno = jsonTurno.getInt("IDTurnoOperativo");
        if(idTurno==-1){
            throw new Exception("El empleado no tiene turno en ese dia");
        }
        JsonObjectBuilder jsonInputTurno = Json.createObjectBuilder();
        jsonInputTurno.add("idTurno", idTurno);
        JsonObject jsonActivaciones = JsonParser.stringToJson(DAOEmergencia.getActivacionesPorTurno(jsonInputTurno.build().toString()));

        List<Activacion> activaciones = new ArrayList<>();

        JsonArray jsonArrayActivaciones = jsonActivaciones.getJsonArray("activaciones");

        for (JsonObject jsonActivacion : jsonArrayActivaciones.getValuesAs(JsonObject.class)) {
            if (jsonActivacion.containsKey("IDTurnoOperativo") && jsonActivacion.getInt("IDTurnoOperativo") == -1) {
                continue; 
            }
            int id = jsonActivacion.getInt("ID");
            Fecha fechaActivacion = Fecha.parseFecha(jsonActivacion.getString("FechaActivacion"));
            Hora hora = Hora.parseHora(jsonActivacion.getString("HoraActivacion"));
            Activacion activacion = new Activacion(id, fechaActivacion, hora);
            activaciones.add(activacion);
        }
        return activaciones;
    }

    public void getDetallesActivacion(Activacion a) throws SQLException, Exception {
        JsonObjectBuilder jsonInputActivacion = Json.createObjectBuilder();
        jsonInputActivacion.add("idActivacion", a.getId());
        JsonObject jsonActivaciones = JsonParser.stringToJson(DAOEmergencia.getDetallesActivaciones(jsonInputActivacion.build().toString()));
        if (jsonActivaciones.containsKey("IDActivacion") && jsonActivaciones.getInt("IDActivacion") == -1) {
            throw new Exception("No hay detalles para esa activacion.");
        } else {
            String descripcionDeLaEmergencia = jsonActivaciones.getString("DescripcionDeLaEmergencia", null);
            String nombreVia = jsonActivaciones.getString("nombreVia", null);
            String fechaSeHaceCargoMedico = jsonActivaciones.getString("fechaSeHaceCargoMedico", null);
            String horaSeHaceCargoMedico = jsonActivaciones.getString("horaSeHaceCargoMedico", null);
            int decisionTrasladoAHospital = jsonActivaciones.getInt("decisionTrasladoAHospital", -1);
            boolean decision = decisionTrasladoAHospital!=-1;
            CtrlVistaConsultarEmergencias.muestrarDetallesActivacion(descripcionDeLaEmergencia, nombreVia, Fecha.parseFecha(fechaSeHaceCargoMedico), Hora.parseHora(horaSeHaceCargoMedico), decision);
        }
    }
}