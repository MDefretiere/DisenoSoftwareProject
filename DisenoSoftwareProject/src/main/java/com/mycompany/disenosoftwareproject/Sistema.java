/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject;

import com.mycompany.disenosoftwareproject.negocio.modelos.*;

import java.util.ArrayList;

/**
 *
 * @author defre
 */
public class Sistema {
    ArrayList<TurnoDeOperador> turnosDeOperador = new ArrayList<TurnoDeOperador>();
    ArrayList<Operador> operadores = new ArrayList<Operador>();
    public void startApp(){
        
    }
    /*
    public void introduceCredenciales(String login, String password){
        boolean correcta = Empleado.compruebaCredenciales(login, password);
        if(correcta){
            
        }
    }*/
    
    public boolean checkTurno(Fecha fecha){
        for(TurnoDeOperador t : turnosDeOperador){
            if(t.getFechaTurno().equals(fecha) && t instanceof TurnoDeOperador){
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Operador> getOperadoresEnTurno(Fecha fecha){
        ArrayList<Operador> list = new ArrayList<Operador>(); 
        for(TurnoDeOperador t : turnosDeOperador){
            if(t.getFechaTurno().equals(fecha)){
                for(Operador o : t.getOperadores()){
                    list.add(o);
                }
            }
        }
        return list;
    }
    
    public ArrayList<Operador> getOperadoresDisponible(Fecha fecha){
        ArrayList<Operador> dispo = new ArrayList<Operador>();
        ////A FAIRE
        return dispo;
    }
}
