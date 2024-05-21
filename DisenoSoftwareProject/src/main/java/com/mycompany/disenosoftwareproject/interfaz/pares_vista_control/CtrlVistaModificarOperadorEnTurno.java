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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class CtrlVistaModificarOperadorEnTurno {
    private ControladorCUModificarOperadorEnTurno controladorCU = ControladorCUModificarOperadorEnTurno.getInstance();
    private static CtrlVistaModificarOperadorEnTurno controladorVista = new CtrlVistaModificarOperadorEnTurno();
    
    public static CtrlVistaModificarOperadorEnTurno getInstance(){
        return controladorVista;
    }

    public static void open(){
        SwingUtilities.invokeLater(() -> {
            VistaModificarOperadorEnTurno vistaModificarOperadorEnTurno = new VistaModificarOperadorEnTurno();
            vistaModificarOperadorEnTurno.setVisible(true);
        });
    }

    public List<Empleado> getTurnosPorFecha(Date date) throws Exception {
        List<Empleado> list=null;
        try {
            if (date == null) {
                throw new Exception("Fecha no valida");
            } else {
                Fecha fecha = Fecha.convertirLocalDateToFecha(date);
                list = controladorCU.getOperadoresEnTurnosPorFecha(fecha);
                return list;
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                VistaInformacion vistaError = new VistaInformacion();
                vistaError.setErrorMessage(e.getMessage());
                vistaError.setVisible(true);
            });
        }
        return list;
    }

    public List<Empleado> getOperadoresDispo(Object selectedItem) throws Exception {
        List<Empleado> dispos;
        if (selectedItem != null) {
            Pattern pattern = Pattern.compile("NIF:(\\w+)");
            Matcher matcher = pattern.matcher(selectedItem.toString());
            String nif=null;
            if (matcher.find()) {
                nif = matcher.group(1);
            } else {
            }
            dispos = controladorCU.getOperadoresDisponibles(nif);
            return dispos;
        } else {
            throw new Exception("No empleado seleccionado");
        }
    }

    public void modificarOperadorEnTurno(Object selectedItem) throws Exception {
        if (selectedItem != null) {
            Pattern pattern = Pattern.compile("NIF:(\\w+)");
            Matcher matcher = pattern.matcher(selectedItem.toString());
            String nif=null;
            if (matcher.find()) {
                nif = matcher.group(1);
            } else {
            }
            controladorCU.modificarOperadorEnTurno(nif);
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

    public void confirmacion(boolean confirmacion) throws SQLException {
        controladorCU.confirmacion(confirmacion);
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
