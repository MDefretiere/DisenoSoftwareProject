/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOEmpleado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author defre
 */
public class Empleado extends Persona {

    private Fecha fechaInicioEnEmpresa;
    
    private Rol rol;
    
    private static String login;
    
    private static String password;
    
    private ArrayList<Disponibilidad> historicoDeDisponiblidad = new ArrayList<Disponibilidad>();

    public Empleado(String nombre, String apellidos, Fecha fechaNacimiento, String nif, Direccion direccion, String telefono, Fecha fechaInicioEnEmpresa, Rol rol) {
        super(nombre, apellidos, fechaNacimiento, nif, direccion, telefono);
        this.fechaInicioEnEmpresa = fechaInicioEnEmpresa;
        this.rol = rol;
    }
    
    public boolean estaActivo(){
        return false; ////A FAIRE
    }
    
    public Rol obtenerRolActual(){
        return rol;
    }
    
    public static boolean compruebaCredenciales(String l, String p) {
        return (password.equals(l) && login.equals(p));
    }
    
    public boolean isDisponible(Fecha fecha){
        for(Disponibilidad d : historicoDeDisponiblidad){
            Fecha inicio = d.getFechaInicio();
            Fecha fin = d.getFechaFin();
            if(fecha.compareTo(inicio) >= 0 && fin == null){
                if(d instanceof Disponible){
                    return true;
                }
            }
            else{
                if(fecha.entreLasDos(inicio, fin) && d instanceof Disponible){
                    return true;
                }
            }
        }
        return false;
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
    
    public static List<Empleado> getOperadoresDisponibles(Fecha fechaTurno) {
        List<Empleado> listDispo = new ArrayList<>();
        List<Empleado> allEmpleados = DAOEmpleado.getAllEmpleados();
        /*
        for(Empleado e : allEmpleados){
            if(e.isDisponible(fechaTurno)){
                listDispo.add(e);
            }
        }
*/
        ////FAIRE LA LOGIQUE DES DISPONIBILITES
        listDispo.add(allEmpleados.get(0));
        return listDispo;    
    }
}
