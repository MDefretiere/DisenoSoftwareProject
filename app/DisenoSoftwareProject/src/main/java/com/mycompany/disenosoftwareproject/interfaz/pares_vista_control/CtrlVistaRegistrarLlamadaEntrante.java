package com.mycompany.disenosoftwareproject.interfaz.pares_vista_control;

import com.mycompany.disenosoftwareproject.interfaz.vista.VistaInformacion;
import com.mycompany.disenosoftwareproject.interfaz.vista.VistaRegistrarLlamadaEntrante;
import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUAtenderLlamada;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author defre
 */
public class CtrlVistaRegistrarLlamadaEntrante {
    private ControladorCUAtenderLlamada controlador = ControladorCUAtenderLlamada.getInstance();
    
    public void registrarNuevaLlamada() throws Exception{
        try{
            controlador.registrarNuevaLlamada();
        }
        catch(Exception e){
            CtrlVistaInformacion.mostrarError(e.getMessage());
        }
    }
    
    public static void open(){
        SwingUtilities.invokeLater(() -> {
            VistaRegistrarLlamadaEntrante vistaRegistrarLlamada = new VistaRegistrarLlamadaEntrante();
            vistaRegistrarLlamada.setVisible(true);
        });
    }
}
