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
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Hora;
import com.mycompany.disenosoftwareproject.negocio.modelos.Llamada;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaCritica;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaInfundada;
import com.mycompany.disenosoftwareproject.negocio.modelos.LlamadaNoCritica;
import com.mycompany.disenosoftwareproject.negocio.modelos.Poliza;
import com.mycompany.disenosoftwareproject.negocio.modelos.Sexo;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public static void start() throws Exception{
        CtrlVistaRegistrarLlamadaEntrante.open();
        operadorEnTurno = Empleado.getEmpleadoPorId("123456789");
    }
    
    public void registrarNuevaLlamada() throws SQLException, Exception {
        List<TurnoDeOperador> turnosDelOperador = TurnoDeOperador.getTurnosDelOperadorPorUnDia(operadorEnTurno, Fecha.getFechaActual());
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
        idLlamada = Llamada.getMaxId()+1;
        CtrlVistaAtenderLlamada.openWindow();
    }
    
    public void introduceInformaciones(String numeroTelefono, String nombreComunicante, String numeroDePoliza, String nombre, String apellidos, Sexo sexo, Fecha fechaNacimiento) throws Exception{
        telefonoLlamada = numeroTelefono;
        this.nombreComunicante = nombreComunicante;
        List<Poliza> listPoliza = Poliza.getAllPolizas();
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
            LlamadaInfundada.grabarNuevaLlamada(llamadaInfundada);
            CtrlVistaInformacion.mostrarInformacion("ERROR : numero de poliza no exista : Redirijar llamada al 112");
        }
        paciente = Asegurado.comprobarAsegurado(nombre, apellidos, fechaNacimiento, sexo);
        if(paciente==null){
            CtrlVistaInformacion.mostrarInformacion("ERROR : la persona indicada en el formulario no es asegurada.");
        }
        boolean checkPacientePoliza = Asegurado.comprobarPacienteConPoliza(paciente.getNif(), poliza.getNumero());
        if(!checkPacientePoliza){
            fechaFinLlamada = Fecha.getFechaActual();
            horaFinLlamada = Hora.getCurrentHora();
            LlamadaInfundada llamadaInfundada = new LlamadaInfundada(idLlamada, telefonoLlamada, fechaInicioLlamada, horaInicioLlamada, fechaFinLlamada, horaFinLlamada, nombreComunicante, operadorEnTurno);
            LlamadaInfundada.grabarNuevaLlamada(llamadaInfundada);
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
            LlamadaCritica.grabarNuevaLlamada(llamadaCritica);
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
            LlamadaNoCritica llamada = new LlamadaNoCritica(idLlamada, telefonoLlamada, fechaInicioLlamada, horaInicioLlamada, fechaFinLlamada, horaFinLlamada, nombreComunicante, operadorEnTurno, descripcion, paciente, true, consejos, necesitaOperativa);
            LlamadaNoCritica.grabarNuevaLlamada(llamada);
            CtrlVistaInformacion.mostrarInformacion("Llamada grabada");
        }
        else{
            consejos.add(new Consejo(descripcionConsejo, resultado, soluciona));
            if(soluciona){
                fechaFinLlamada = Fecha.getFechaActual();
                horaFinLlamada = Hora.getCurrentHora();
                LlamadaNoCritica llamada = new LlamadaNoCritica(idLlamada, telefonoLlamada, fechaInicioLlamada, horaInicioLlamada, fechaFinLlamada, horaFinLlamada, nombreComunicante, operadorEnTurno, descripcion, paciente, false, consejos, necesitaOperativa);
                LlamadaNoCritica.grabarNuevaLlamada(llamada);
                CtrlVistaInformacion.mostrarInformacion("Llamada grabada");
            }
            else{
                CtrlVistaConsejosYResultados.openWindow();
            }
        }
    }
}
