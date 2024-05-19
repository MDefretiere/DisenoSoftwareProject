/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOLlamada;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author defre
 */
public class Llamada {
    private int id;
    private String numeroTelefonoOrigen;
    private Fecha fechaInicio;
    private Hora horaInicio;
    private Fecha fechaFin;
    private Hora horaFin;
    private String nombreComunicante;
    private Empleado atiende;
    
    public Llamada(int id, String numeroTelefonoOrigen, Fecha fechaInicio, Hora horaInicio, Fecha fechaFin, Hora horaFin, String nombreComunicante, Empleado empleado) {
        this.id = id;
        this.numeroTelefonoOrigen = numeroTelefonoOrigen;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.fechaFin = fechaFin;
        this.horaFin = horaFin;
        this.nombreComunicante = nombreComunicante;
        this.atiende = empleado;
    }
    
    public int getId() {
        return id;
    }

    public String getNumeroTelefonoOrigen() {
        return numeroTelefonoOrigen;
    }

    public Fecha getFechaInicio() {
        return fechaInicio;
    }

    public Hora getHoraInicio() {
        return horaInicio;
    }

    public Fecha getFechaFin() {
        return fechaFin;
    }

    public Hora getHoraFin() {
        return horaFin;
    }

    public String getNombreComunicante() {
        return nombreComunicante;
    }

    public Empleado getAtiende() {
        return atiende;
    }
    
    public static void grabarNuevaLlamada(Llamada llamada) throws Exception{
        JsonObject jsonLlamada = createJsonLlamada(llamada);
        DAOLlamada.grabarNuevaLlamada(jsonLlamada);
    }

    private static JsonObject createJsonLlamada(Llamada llamada) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("id", llamada.getId());
        jsonBuilder.add("numeroTelefonoOrigen", llamada.getNumeroTelefonoOrigen());
        jsonBuilder.add("fechaInicio", llamada.getFechaInicio().toString());
        jsonBuilder.add("horaInicio", llamada.getHoraInicio().toString());
        jsonBuilder.add("fechaFin", llamada.getFechaFin().toString());
        jsonBuilder.add("horaFin", llamada.getHoraFin().toString());
        jsonBuilder.add("nombreComunicante", llamada.getNombreComunicante()); 
        if (llamada instanceof LlamadaNoCritica) {
            jsonBuilder.add("tipo", "LlamadaNoCritica");
            jsonBuilder.add("pacienteNif", ((LlamadaNoCritica) llamada).getPaciente().getNif());
            jsonBuilder.add("descripcionEmergencia", ((LlamadaNoCritica) llamada).getDescripcionEmergencia());
            jsonBuilder.add("esLeve", ((LlamadaNoCritica) llamada).esLeve());
            jsonBuilder.add("requiereOperativo", ((LlamadaNoCritica) llamada).hayOperativoNecesitado());
            JsonArrayBuilder consejosArrayBuilder = Json.createArrayBuilder();
            for (Consejo c : ((LlamadaNoCritica) llamada).getConsejos()) {
                JsonObjectBuilder jsonBuilderConsejo = Json.createObjectBuilder();
                jsonBuilderConsejo.add("descripcion", c.getDescripcion());
                jsonBuilderConsejo.add("resultado", c.getResultado());
                jsonBuilderConsejo.add("soluciona", c.isSoluciona());
                consejosArrayBuilder.add(jsonBuilderConsejo);
            }
            jsonBuilder.add("consejos", consejosArrayBuilder);
        } else if (llamada instanceof LlamadaCritica) {
            jsonBuilder.add("tipo", "LlamadaCritica"); 
            jsonBuilder.add("pacienteNif", ((LlamadaCritica) llamada).getPaciente().getNif());
            jsonBuilder.add("descripcionEmergencia", ((LlamadaCritica) llamada).getDescripcionEmergencia());
        } else if (llamada instanceof LlamadaInfundada){
            jsonBuilder.add("tipo", "LlamadaInfundada"); 
        }
        return jsonBuilder.build();
    }
    
    public static int getMaxId(){
        JsonObject json = DAOLlamada.getMaxId();
        int maxId = -1;
        if (json != null && json.containsKey("max_id")) {
            maxId = json.getInt("max_id");
        }
        return maxId;
    }
}
