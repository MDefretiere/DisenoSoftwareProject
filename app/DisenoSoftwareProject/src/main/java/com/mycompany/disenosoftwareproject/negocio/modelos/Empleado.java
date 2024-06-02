/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOEmpleado;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author el_pouleto
 */
public class Empleado extends Persona {

    private Fecha fechaInicioEnEmpresa;
    
    private Rol rol;
    
    private static String login;
    
    private static String password;
    
    private ArrayList<Disponibilidad> historicoDeDisponiblidad = new ArrayList<>();

    public Empleado(String nombre, String apellidos, Fecha fechaNacimiento, String nif, Direccion direccion, String telefono, Fecha fechaInicioEnEmpresa, Rol rol) {
        super(nombre, apellidos, fechaNacimiento, nif, direccion, telefono);
        this.fechaInicioEnEmpresa = fechaInicioEnEmpresa;
        this.rol = rol;
    }
    
    public boolean estaActivo(){
        return false;
    }
    
    public Rol obtenerRolActual(){
        return rol;
    }
    
    public static boolean compruebaCredenciales(String l, String p) {
        return (password.equals(l) && login.equals(p));
    }
    
    public Rol getRol() {
        return rol;
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public ArrayList<Disponibilidad> getHistoricoDeDisponiblidad() {
        return historicoDeDisponiblidad;
    }
    
    public static Empleado jsonToEmpleado(JsonObject jsonEmpleado) throws Exception {
        String nif = jsonEmpleado.getString("nif");
        String nombre = jsonEmpleado.getString("nombre");
        String apellidos = jsonEmpleado.getString("apellidos");
        Fecha fechaNacimiento = Fecha.parseFecha(jsonEmpleado.getString("fechaNacimiento"));
        Direccion direccion = Direccion.fromJson(jsonEmpleado.getJsonObject("direccion"));
        String telefono = jsonEmpleado.getString("telefono");
        Fecha fechaInicioEnEmpresa = Fecha.parseFecha(jsonEmpleado.getString("fechaInicioEnEmpresa"));

        JsonObject jsonRol = jsonEmpleado.getJsonObject("rol");
        String nombreRol = jsonRol.getString("rol");
        String fechaInicioEnPuestoString = jsonRol.getString("fechaInicioEnPuesto");
        Fecha fechaInicioEnPuesto = Fecha.parseFecha(fechaInicioEnPuestoString);

        Rol rol;
        switch(nombreRol){
            case "Gerente":
                rol = new Gerente(fechaInicioEnPuesto);
                break;
            case "Conductor":
                rol = new Conductor(fechaInicioEnPuesto);
                break;
            case "Operador":
                rol = new Operador(fechaInicioEnPuesto);
                break;
            case "Medico":
                rol = new Medico(fechaInicioEnPuesto);
                break;
            default:
                throw new Exception("Error rol del operador");   
        }

        return new Empleado(nombre, apellidos, fechaNacimiento, nif, direccion, telefono, fechaInicioEnEmpresa, rol);
    }
}