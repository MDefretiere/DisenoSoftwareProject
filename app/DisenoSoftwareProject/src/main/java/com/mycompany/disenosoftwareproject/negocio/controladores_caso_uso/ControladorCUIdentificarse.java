/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaIdentificarse;
import com.mycompany.disenosoftwareproject.negocio.modelos.Conductor;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Gerente;
import com.mycompany.disenosoftwareproject.negocio.modelos.Medico;
import com.mycompany.disenosoftwareproject.negocio.modelos.Operador;
import com.mycompany.disenosoftwareproject.persistencia.DAOEmpleado;
import javax.json.Json;
import javax.json.JsonObject;
import serviciosComunes.JsonParser;

/**
 *
 * @author defre
 */
public class ControladorCUIdentificarse {

    private static ControladorCUIdentificarse controlador = new ControladorCUIdentificarse();
    private Empleado empleadoIdentificado;

    public static ControladorCUIdentificarse getInstance() {
        return controlador;
    }

    public void start() {
        CtrlVistaIdentificarse.openIdentificarseVista();
    }

    public void comprobarEmpleado(String nif, String contrasena) throws Exception {
        JsonObject jsonLogin = Json.createObjectBuilder()
                .add("nif", nif)
                .add("password", contrasena)
                .build();
        String jsonSEmpleado = DAOEmpleado.obtenerEmpleadoPorId(jsonLogin.toString());
        if(jsonSEmpleado==null || jsonSEmpleado.equals("")){
            throw new Exception("ERROR : NIF no existe en la base de datos.");
        }
        String jsonS = DAOEmpleado.comprobarLoginYContrasena(jsonLogin.toString());
        if(jsonS==null || jsonS.equals("")){
            throw new Exception("ERROR : Contrasena no valid.");
        }
        JsonObject json = JsonParser.stringToJson(jsonS);
        if (json == null) {
            throw new Exception("ERROR : NIF o contrasena no valid.");
        }
        if(json.getInt("disponibilidad")!=1){
            throw new Exception("El empleado no esta disponible ahora");
        }
        empleadoIdentificado = Empleado.jsonToEmpleado(json);
        if (empleadoIdentificado.getRol() instanceof Operador) {
            CtrlVistaIdentificarse.openOperadorVista();
        } else if (empleadoIdentificado.getRol() instanceof Medico) {
            CtrlVistaIdentificarse.openPersonalDeOperadorVista();
        } else if (empleadoIdentificado.getRol() instanceof Conductor) {
            CtrlVistaIdentificarse.openPersonalDeOperadorVista();
        } else if (empleadoIdentificado.getRol() instanceof Gerente) {
            CtrlVistaIdentificarse.openGerenteVista();
        }
    }

    public void consultarEmergencia() {
        ControladorCUConsultarEmergencias controladorCU = ControladorCUConsultarEmergencias.getInstance();
        controladorCU.setEmpleadoIdentificado(empleadoIdentificado);
        controladorCU.start();
    }

    public void attenderLlamada() throws Exception {
        ControladorCUAtenderLlamada controladorCU = ControladorCUAtenderLlamada.getInstance();
        controladorCU.setEmpleadoIdentificado(empleadoIdentificado);
        controladorCU.start();
    }

    public void modificarOperador() throws Exception {
        ControladorCUModificarOperadorEnTurno controladorCU = ControladorCUModificarOperadorEnTurno.getInstance();
        controladorCU.setEmpleadoIdentificado(empleadoIdentificado);
        controladorCU.start();
    }
}
