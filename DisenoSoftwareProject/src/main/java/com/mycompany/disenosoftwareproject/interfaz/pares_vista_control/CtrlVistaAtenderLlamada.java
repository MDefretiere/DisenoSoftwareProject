/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.interfaz.pares_vista_control;

import com.mycompany.disenosoftwareproject.interfaz.vista.VistaAtenderLlamada;
import com.mycompany.disenosoftwareproject.interfaz.vista.VistaDescripcionYCriticaLlamada;
import com.mycompany.disenosoftwareproject.interfaz.vista.VistaInformacion;
import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUAtenderLlamada;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Sexo;
import java.awt.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class CtrlVistaAtenderLlamada {
    private ControladorCUAtenderLlamada controlador = ControladorCUAtenderLlamada.getInstance();
    public static void openWindow(){
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        SwingUtilities.invokeLater(() -> {
            VistaAtenderLlamada vistaAtenderLlamada = new VistaAtenderLlamada();
            vistaAtenderLlamada.setVisible(true);
        });
    }
    
    public void introduceInformaciones(String numeroTelefono, String nombreComunicante, String numeroDePoliza, String nombre, String apeellidos, Sexo sexo, Fecha fechaNacimiento) throws Exception{
        try{
            controlador.introduceInformaciones(numeroTelefono, nombreComunicante, numeroDePoliza, nombre, apeellidos, sexo, fechaNacimiento);
        }
        catch(Exception e){
            SwingUtilities.invokeLater(() -> {
                VistaInformacion vistaError = new VistaInformacion();
                vistaError.setErrorMessage(e.getMessage());
                vistaError.setVisible(true);
            });
        }
    }
    
    public static void preguntarDescripcionYEstado(){
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        SwingUtilities.invokeLater(() -> {
            VistaDescripcionYCriticaLlamada vistaDescripcion = new VistaDescripcionYCriticaLlamada();
            vistaDescripcion.setVisible(true);
        });
    }
    
    public void introduceDescripcionYEstado(String descripcion, String estado) throws Exception{
        try{
            controlador.introduceDescripcionYEstado(descripcion, estado);
        }
        catch(Exception e){
            SwingUtilities.invokeLater(() -> {
                VistaInformacion vistaError = new VistaInformacion();
                vistaError.setErrorMessage(e.getMessage());
                vistaError.setVisible(true);
            });
        }
    }
}
