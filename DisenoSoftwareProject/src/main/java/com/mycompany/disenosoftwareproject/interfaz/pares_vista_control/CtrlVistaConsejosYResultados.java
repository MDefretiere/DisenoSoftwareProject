/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.interfaz.pares_vista_control;

import com.mycompany.disenosoftwareproject.interfaz.vista.VistaConsejosYResultados;
import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUAtenderLlamada;
import java.awt.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class CtrlVistaConsejosYResultados {

    private ControladorCUAtenderLlamada controlador = ControladorCUAtenderLlamada.getInstance();
    
    public static void openWindow(){
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        SwingUtilities.invokeLater(() -> {
            VistaConsejosYResultados vistaConsejos = new VistaConsejosYResultados();
            vistaConsejos.setVisible(true);
        });
    }
    
    public void introduceInformacionesConsejos(boolean necesitaOperativa, String descripcion, String resultado, boolean requiereIntervencion) throws Exception{
        controlador.introduceInformacionesConsejos(necesitaOperativa, descripcion, resultado, requiereIntervencion);
    }
}
