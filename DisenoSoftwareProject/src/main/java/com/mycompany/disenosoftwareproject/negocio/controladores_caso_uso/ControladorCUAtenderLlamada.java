/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaAtenderLlamada;
import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaConsejosYResultados;
import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaInformacion;
import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.CtrlVistaRegistrarLlamadaEntrante;
import com.mycompany.disenosoftwareproject.negocio.modelos.Asegurado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Consejo;
import com.mycompany.disenosoftwareproject.negocio.modelos.Direccion;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import static com.mycompany.disenosoftwareproject.negocio.modelos.Empleado.jsonToEmpleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Hora;
import com.mycompany.disenosoftwareproject.negocio.modelos.Llamada;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaCritica;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaInfundada;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaNoCritica;
import com.mycompany.disenosoftwareproject.negocio.modelos.Poliza;
import com.mycompany.disenosoftwareproject.negocio.modelos.Sexo;
import com.mycompany.disenosoftwareproject.negocio.modelos.TipoDeTurnoOperador;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import com.mycompany.disenosoftwareproject.persistencia.DAOAsegurado;
import com.mycompany.disenosoftwareproject.persistencia.DAOEmpleado;
import com.mycompany.disenosoftwareproject.persistencia.DAOLlamada;
import com.mycompany.disenosoftwareproject.persistencia.DAOPoliza;
import com.mycompany.disenosoftwareproject.persistencia.DAOTurnoDeOperador;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 *
 * @author defre
 */
public class ControladorCUAtenderLlamada {
    private static final ControladorCUAtenderLlamada controlador = new ControladorCUAtenderLlamada();
    private static Empleado operadorEnTurno;
    private String telefonoLlamada;
    private String nombreComunicante;
    private Hora horaInicioLlamada;
    private Fecha fechaInicioLlamada;
    private Hora horaFinLlamada;
    private Fecha fechaFinLlamada;
    private Asegurado paciente;
    private Poliza poliza;
    private String descripcion;
    private int idLlamada;
    private List<Consejo> consejos = new ArrayList<>();
    
    //SINGLETON DEL CONTROLADOR
    public static ControladorCUAtenderLlamada getInstance(){
        return controlador;
    }
    
    //EMPIENZA EL CASO DE USO
    public void start() throws Exception{
        CtrlVistaRegistrarLlamadaEntrante.open();
    }
    
    public void setEmpleadoIdentificado(Empleado e){
        operadorEnTurno = e;
    }
    
    public void registrarNuevaLlamada() throws SQLException, Exception {
        List<TurnoDeOperador> turnosDelOperador = getTurnosDelOperadorPorUnDia(operadorEnTurno, Fecha.getFechaActual());
        if(turnosDelOperador.isEmpty()){
            throw new Exception("ERROR : el operador no tiene turno hoy");
        }
        boolean isOperadorEnTurno = false;
        for(TurnoDeOperador t : turnosDelOperador){
            switch (t.getTipo()) {
                case DeManana7 -> {
                    if (Hora.getCurrentHora().entreLasDos(new Hora(7, 0, 0), new Hora(15, 0, 0))) {
                        isOperadorEnTurno = true;
                    }
                }
                case DeTarde15 -> {
                    if (Hora.getCurrentHora().entreLasDos(new Hora(15, 0, 0), new Hora(23, 0, 0))) {
                        isOperadorEnTurno = true;
                    }
                }
                case DeNoche23 -> {
                    if (Hora.getCurrentHora().entreLasDos(new Hora(23, 0, 0), new Hora(7, 0, 0))) {
                        isOperadorEnTurno = true;
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + t.getTipo());
            }
        }
        if(!isOperadorEnTurno){
            throw new Exception("ERROR : el operador no esta en turno ahora");
        }
        fechaInicioLlamada = Fecha.getFechaActual();
        horaInicioLlamada = Hora.getCurrentHora();
        idLlamada = getMaxId()+1;
        CtrlVistaAtenderLlamada.openWindow();
    }
    
    public void introduceInformaciones(String numeroTelefono, String nombreComunicante, String numeroDePoliza, String nombre, String apellidos, Sexo sexo, Fecha fechaNacimiento) throws Exception{
        telefonoLlamada = numeroTelefono;
        this.nombreComunicante = nombreComunicante;
        List<Poliza> listPoliza = getAllPolizas();
        boolean verifNumeroPoliza = false;
        for(Poliza p : listPoliza){
            if(p.getNumero().equals(numeroDePoliza)){
                verifNumeroPoliza=true;
                poliza = p;
            }
        }
        if(verifNumeroPoliza==false){
            fechaFinLlamada = Fecha.getFechaActual();
            horaFinLlamada = Hora.getCurrentHora();
            LlamadaInfundada llamadaInfundada = new LlamadaInfundada(idLlamada, telefonoLlamada, fechaInicioLlamada, horaInicioLlamada, fechaFinLlamada, horaFinLlamada, nombreComunicante, operadorEnTurno);
            grabarNuevaLlamada(llamadaInfundada);
            CtrlVistaInformacion.mostrarInformacion("ERROR : numero de poliza no exista : Redirijar llamada al 112");
        }
        paciente = comprobarAsegurado(nombre, apellidos, fechaNacimiento, sexo);
        if(paciente==null){
            CtrlVistaInformacion.mostrarInformacion("ERROR : la persona indicada en el formulario no es asegurada.");
        }
        boolean checkPacientePoliza = comprobarPacienteConPoliza(paciente.getNif(), poliza.getNumero());
        if(!checkPacientePoliza){
            fechaFinLlamada = Fecha.getFechaActual();
            horaFinLlamada = Hora.getCurrentHora();
            LlamadaInfundada llamadaInfundada = new LlamadaInfundada(idLlamada, telefonoLlamada, fechaInicioLlamada, horaInicioLlamada, fechaFinLlamada, horaFinLlamada, nombreComunicante, operadorEnTurno);
            grabarNuevaLlamada(llamadaInfundada);
            CtrlVistaInformacion.mostrarInformacion("ERROR : numero de poliza no corresponde con el paciente : Redirijar llamada al 112");
        }
        else{
            CtrlVistaAtenderLlamada.preguntarDescripcionYEstado();
        }
    }

    public void introduceDescripcionYEstado(String descripcion, String estado) throws Exception {
        this.descripcion = descripcion;
        if(estado.equals("Critica")){
            fechaFinLlamada = Fecha.getFechaActual();
            horaFinLlamada = Hora.getCurrentHora();
            LlamadaCritica llamadaCritica = new LlamadaCritica(idLlamada, telefonoLlamada, fechaInicioLlamada, horaInicioLlamada, fechaFinLlamada, horaFinLlamada, nombreComunicante, operadorEnTurno, descripcion, paciente);
            grabarNuevaLlamada(llamadaCritica);
            CtrlVistaInformacion.mostrarInformacion("ATENCION : Emergencia critica : Redirijar llamada al 112");
        }
        else{
            CtrlVistaConsejosYResultados.openWindow();
        }
        
    }

    public void introduceInformacionesConsejos(boolean necesitaOperativa, String descripcionConsejo, String resultado, boolean soluciona) throws Exception {
        if(necesitaOperativa){
            fechaFinLlamada = Fecha.getFechaActual();
            horaFinLlamada = Hora.getCurrentHora();
            LlamadaNoCritica llamada = new LlamadaNoCritica(idLlamada, telefonoLlamada, fechaInicioLlamada, horaInicioLlamada, fechaFinLlamada, horaFinLlamada, nombreComunicante, operadorEnTurno, descripcion, paciente, true, consejos, 1);
            grabarNuevaLlamada(llamada);
            CtrlVistaInformacion.mostrarInformacion("Llamada grabada");
        }
        else{
            consejos.add(new Consejo(descripcionConsejo, resultado, soluciona));
            if(soluciona){
                fechaFinLlamada = Fecha.getFechaActual();
                horaFinLlamada = Hora.getCurrentHora();
                LlamadaNoCritica llamada = new LlamadaNoCritica(idLlamada, telefonoLlamada, fechaInicioLlamada, horaInicioLlamada, fechaFinLlamada, horaFinLlamada, nombreComunicante, operadorEnTurno, descripcion, paciente, false, consejos, -1);
                grabarNuevaLlamada(llamada);
                CtrlVistaInformacion.mostrarInformacion("Llamada grabada");
            }
            else{
                CtrlVistaConsejosYResultados.openWindow();
            }
        }
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
            jsonBuilder.add("requiereOperativo", ((LlamadaNoCritica) llamada).getIdOperativo());
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
    
    public static List<Poliza> getAllPolizas(){
        JsonObject json = DAOPoliza.getAllPoliza();
        JsonArray jsonArray = json.getJsonArray("polizas"); // Utilise la clé correcte
        
        List<Poliza> polizas = new ArrayList<>();
        for (JsonObject polizaJson : jsonArray.getValuesAs(JsonObject.class)) {
            String numeroPoliza = polizaJson.getString("numeroPoliza");
            Fecha fechaInicio = Fecha.parseFecha(polizaJson.getString("fechaInicio"));
            Fecha fechaVencimiento = Fecha.parseFecha(polizaJson.getString("fechaVencimiento"));

            Poliza poliza = new Poliza(numeroPoliza, fechaInicio, fechaVencimiento);
            polizas.add(poliza);
        }

        return polizas;
    }
    
    public JsonObject toJson(Asegurado a) {
        JsonObjectBuilder jsonBuilder;
        jsonBuilder = Json.createObjectBuilder()
                .add("nombre", a.getNombre())
                .add("apellidos", a.getApellidos())
                .add("fechaNacimiento", a.getFechaNacimiento().toString())
                .add("nif", a.getNif())
                .add("direccion", a.getDireccion().toJson())
                .add("telefono", a.getTelefono())
                .add("sexo", a.getSexo().toString())
                .add("numeroDeCuenta", a.getNumeroDeCuenta());
        return jsonBuilder.build();
    }

    public static Asegurado comprobarAsegurado(String nombre, String apellidos, Fecha fechaNacimiento, Sexo sexo) {
        JsonObject jsonInput = Json.createObjectBuilder()
            .add("nombre", nombre)
            .add("apellidos", apellidos)
            .add("fechaNacimiento", fechaNacimiento.toString())
            .add("sexo", sexo.toString())
            .build();

        JsonObject json = DAOAsegurado.comprobarAsegurado(jsonInput);
        if (json == null) {
            return null;
        }
        return convertirDesdeJson(json);
    }

    public static Asegurado convertirDesdeJson(JsonObject json) {
        String nombre = json.getString("nombre");
        String apellidos = json.getString("apellidos");
        Fecha fechaNacimiento = Fecha.parseFecha(json.getString("fechaNacimiento"));
        String nif = json.getString("nif");
        Direccion direccion = Direccion.fromJson(json.getJsonObject("direccion"));
        String telefono = json.getString("telefono");
        Sexo sexo = Sexo.valueOf(json.getString("sexo"));
        String numeroDeCuenta = json.getString("numeroDeCuenta");

        return new Asegurado(nombre, apellidos, fechaNacimiento, nif, direccion, telefono, sexo, numeroDeCuenta);
    }

    public static boolean comprobarPacienteConPoliza(String nif, String numero) {
        JsonObject jsonInput = Json.createObjectBuilder()
            .add("nif", nif)
            .add("numeroPoliza", numero)
            .build();

        return DAOAsegurado.comprobarPacienteConPoliza(jsonInput).getBoolean("existe");
    }
    
    public static Empleado getEmpleadoPorId(String nifcif) throws Exception {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("nif", nifcif);
        JsonObject jsonParam = jsonBuilder.build();
        JsonObject jsonEmpleado = DAOEmpleado.obtenerEmpleadoPorId(jsonParam);
        return jsonToEmpleado(jsonEmpleado);
    }
    
    public static List<TurnoDeOperador> getTurnosDelOperadorPorUnDia(Empleado operadorEnTurno, Fecha fechaActual) throws SQLException {
        List<TurnoDeOperador> turnos = new ArrayList<>();
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        jsonBuilder.add("fecha", fechaActual.toString());
        jsonBuilder.add("nif", operadorEnTurno.getNif());
        JsonObject jsonParam = jsonBuilder.build();
        JsonObject jsonTurnos = DAOTurnoDeOperador.getTurnosDelOperadorPorUnDia(jsonParam);
        JsonArray turnosArray = jsonTurnos.getJsonArray("turnos");

        for (JsonValue turnoJson : turnosArray) {
            JsonObject turnoObj = (JsonObject) turnoJson;
            int idTurno = turnoObj.getInt("IDTURNOOPERADOR");

            Fecha fechaCreacion = Fecha.parseFecha(turnoObj.getString("FECHACREACION"));
            Fecha fechaTurno = Fecha.parseFecha(turnoObj.getString("FECHATURNO"));

            TipoDeTurnoOperador tipoDeTurno;
            switch (turnoObj.getString("NOMBRETIPOTURNO")) {
                case "DeManana7" -> tipoDeTurno = TipoDeTurnoOperador.DeManana7;
                case "DeTarde15" -> tipoDeTurno = TipoDeTurnoOperador.DeTarde15;
                case "DeNoche23" -> tipoDeTurno = TipoDeTurnoOperador.DeNoche23;
                default -> throw new SQLException("ERROR tipo de turno de Operador");
            }

            List<Empleado> operadores = new ArrayList<>();
            operadores.add(operadorEnTurno);

            TurnoDeOperador turno = new TurnoDeOperador(idTurno, fechaCreacion, fechaTurno, tipoDeTurno, operadores);
            turnos.add(turno);
        }
        return turnos;
    }
}
