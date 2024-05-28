/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.interfaz.pares_vista_control;

import com.mycompany.disenosoftwareproject.interfaz.vista.VistaGerente;
import com.mycompany.disenosoftwareproject.interfaz.vista.VistaIdentificarse;
import com.mycompany.disenosoftwareproject.interfaz.vista.VistaInformacion;
import com.mycompany.disenosoftwareproject.interfaz.vista.VistaOperador;
import com.mycompany.disenosoftwareproject.interfaz.vista.VistaPersonalDeOperativo;
import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUIdentificarse;
import java.awt.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author el_pouleto
 */
public class CtrlVistaIdentificarse {

    private static CtrlVistaIdentificarse controladorVista = new CtrlVistaIdentificarse();
    private ControladorCUIdentificarse controlador = ControladorCUIdentificarse.getInstance();

    public static CtrlVistaIdentificarse getInstance() {
        return controladorVista;
    }

    public static void openIdentificarseVista() {
        SwingUtilities.invokeLater(() -> {
            VistaIdentificarse vistaIdentificarse = new VistaIdentificarse();
            vistaIdentificarse.setVisible(true);
        });
    }

    public void introduceNIFYContrasena(String nif, String contrasena) throws Exception {
        try{
            controlador.comprobarEmpleado(nif, contrasena);
        }
        catch(Exception e){
            SwingUtilities.invokeLater(() -> {
                VistaInformacion vistaError = new VistaInformacion();
                vistaError.setErrorMessage(e.getMessage());
                vistaError.setVisible(true);
            });
        }
    }

    public static void openOperadorVista() {
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        SwingUtilities.invokeLater(() -> {
            VistaOperador vistaOperador = new VistaOperador();
            vistaOperador.setVisible(true);
        });
    }

    public static void openGerenteVista() {
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        SwingUtilities.invokeLater(() -> {
            VistaGerente vistaGerente = new VistaGerente();
            vistaGerente.setVisible(true);
        });
    }

    public static void openPersonalDeOperadorVista() {
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
        SwingUtilities.invokeLater(() -> {
            VistaPersonalDeOperativo vistaPersonalDeOperativo = new VistaPersonalDeOperativo();
            vistaPersonalDeOperativo.setVisible(true);
        });
    }

    public void attenderLlamada() throws Exception {
        controlador.attenderLlamada();
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
    }

    public void consultarEmergencia() {
        controlador.consultarEmergencia();
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
    }

    public void modificarOperador() throws Exception {
        controlador.modificarOperador();
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
    }

}
