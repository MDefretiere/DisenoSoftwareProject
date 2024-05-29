/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.interfaz.pares_vista_control;

import com.mycompany.disenosoftwareproject.interfaz.vista.VistaInformacion;
import java.awt.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class CtrlVistaInformacion {
    public static void mostrarInformacion(String message){
        SwingUtilities.invokeLater(() -> {
            VistaInformacion vistaInformacion = new VistaInformacion();
            vistaInformacion.mostrarInformacion(message);
            vistaInformacion.setVisible(true);
        });
    }
    
    public static void mostrarError(String message){
        SwingUtilities.invokeLater(() -> {
            VistaInformacion vistaInformacion = new VistaInformacion();
            vistaInformacion.mostrarError(message);
            vistaInformacion.setVisible(true);
        });
    }
}
