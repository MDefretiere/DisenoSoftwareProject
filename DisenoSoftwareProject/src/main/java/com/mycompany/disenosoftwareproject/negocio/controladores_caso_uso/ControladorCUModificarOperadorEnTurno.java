/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso;

import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import java.util.Date;
import java.util.List;

/**
 *
 * @author defre
 */
public class ControladorCUModificarOperadorEnTurno {
    public static List<TurnoDeOperador> getTurnosPorFecha(Date fecha){
        List<TurnoDeOperador> list = TurnoDeOperador.getTurnosPorFecha(fecha);
        return (list);
    } 
}
