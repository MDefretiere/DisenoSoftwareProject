/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.interfaz.pares_vista_control;

import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUConsultarEmergencias;
import com.mycompany.disenosoftwareproject.negocio.modelos.Activacion;
import com.mycompany.disenosoftwareproject.negocio.modelos.Fecha;
import com.mycompany.disenosoftwareproject.negocio.modelos.Hora;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class CtrlVistaConsultarEmergencias {
    private static CtrlVistaConsultarEmergencias controlador = new CtrlVistaConsultarEmergencias();

    public static void open() {
        SwingUtilities.invokeLater(() -> {
            VistaConsultarEmergencias vistaConsultarEmergencias = new VistaConsultarEmergencias();
            vistaConsultarEmergencias.setVisible(true);
        });
    }
    private ControladorCUConsultarEmergencias controladorCU = ControladorCUConsultarEmergencias.getInstance();
    
    public static CtrlVistaConsultarEmergencias getInstance(){
        return controlador;
    }

    List<Activacion> getActivacionesPorFecha(Date fecha) throws SQLException, Exception {
        try{
            Fecha f = Fecha.convertirLocalDateToFecha(fecha);
            return controladorCU.getActivacionesPorFecha(f);
        }
        catch(Exception e){
            SwingUtilities.invokeLater(() -> {
                VistaInformacion vistaError = new VistaInformacion();
                vistaError.setErrorMessage(e.getMessage());
                vistaError.setVisible(true);
            });
        }
        return null;
    }

    void getDetallesActivacion(String string) throws SQLException {
        Activacion a = Activacion.stringToActivacion(string);
        controladorCU.getDetallesActivacion(a);
    }
    
    public static void muestrarDetallesActivacion(String descripcion, String nombreVia, Fecha fecha, Hora hora, boolean trasladoHospital){
        SwingUtilities.invokeLater(() -> {
            VistaDetallesActivacion vistaDetallesActivacion = new VistaDetallesActivacion();
            vistaDetallesActivacion.setInfos(descripcion, nombreVia, fecha, hora, trasladoHospital);
            vistaDetallesActivacion.setVisible(true);
        });
    }
}
