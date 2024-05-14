/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.interfaz.pares_vista_control;

import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUModificarOperadorEnTurno;
import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import java.awt.Window;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class CtrlVistaModificarOperadorEnTurno {
    public static List<Empleado> getTurnosPorFecha(Date date) throws Exception{
        if(date==null){
            throw new Exception("Fecha no valida");
        }
        else{
            Fecha fecha = Fecha.convertirLocalDateToFecha(date);
            List<Empleado> list = ControladorCUModificarOperadorEnTurno.getOperadoresEnTurnosPorFecha(fecha);
            return list;
        }
    }

    public List<Empleado> getOperadoresDispo(Object selectedItem) throws Exception {
        List<Empleado> dispos;
        if (selectedItem != null) {
            String selectedString = selectedItem.toString();
            String[] parts = selectedString.split(":");
            dispos = ControladorCUModificarOperadorEnTurno.getOperadoresDisponibles(parts[1]);
            return dispos;
        } else {
            throw new Exception("No empleado seleccionado");
        }
    }
    
    public void modificarOperadorEnTurno(Object selectedItem)throws Exception {
        if (selectedItem != null) {
            String selectedString = selectedItem.toString();
            String[] parts = selectedString.split(":");
            ControladorCUModificarOperadorEnTurno.modificarOperadorEnTurno(parts[1]);
        } else {
            throw new Exception("No empleado seleccionado");
        }
    }
    
    public static void preguntarConfirmacion(TurnoDeOperador turnoAModificar, Empleado empleadoACambiar, Empleado nuevoEmpleado) {
        SwingUtilities.invokeLater(() -> {
            VistaConfirmacionModificarOperadorEnTurno vistaConfirmacionModificarOperadorEnTurno = new VistaConfirmacionModificarOperadorEnTurno();
            vistaConfirmacionModificarOperadorEnTurno.modificarInformacion(turnoAModificar, empleadoACambiar, nuevoEmpleado);
            vistaConfirmacionModificarOperadorEnTurno.setVisible(true);
        });
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
    }
    
    public static void confirmacion(boolean confirmacion) throws SQLException{
        ControladorCUModificarOperadorEnTurno.confirmacion(confirmacion);
        SwingUtilities.invokeLater(() -> {
            VistaInformacionConfirmacion vistaInformacion = new VistaInformacionConfirmacion();
            vistaInformacion.setVisible(true);
        });
        for (Window window : Window.getWindows()) {
            if (window.isShowing()) {
                window.dispose();
            }
        }
    }
}
