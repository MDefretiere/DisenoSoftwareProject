/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject;

import com.mycompany.disenosoftwareproject.negocio.modelos.*;
import com.mycompany.disenosoftwareproject.persistencia.DAOEmpleado;
import java.sql.SQLException;

import java.util.ArrayList;

/**
 *
 * @author defre
 */
public class Sistema {
    ArrayList<TurnoDeOperador> turnosDeOperador = new ArrayList<TurnoDeOperador>();
    ArrayList<Operador> operadores = new ArrayList<Operador>();
    public void startApp() throws SQLException{
        Empleado e = new Empleado("Matthieu", "Defretiere", new Fecha(16, 04, 2003), "010204564", new Direccion(5, "Oui", "Non", "Peut-erte", "oui", "non"), "064056533", new Fecha(16, 04, 2003), new Gerente(new Fecha(16, 04, 2003)));
        DAOEmpleado daoEmpleado = new DAOEmpleado();
        daoEmpleado.enregistrerEmploye(e);
        System.out.println("Employé enregistré");
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
