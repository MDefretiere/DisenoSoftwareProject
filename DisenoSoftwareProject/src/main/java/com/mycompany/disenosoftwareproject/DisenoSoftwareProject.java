/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.disenosoftwareproject;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.VistaModificarOperadorEnTurno;
import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUAtenderLlamada;
import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUModificarOperadorEnTurno;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class DisenoSoftwareProject {
    private static ControladorCUAtenderLlamada controladorCULlamada = ControladorCUAtenderLlamada.getInstance();
    private static ControladorCUModificarOperadorEnTurno controladorModificarOperadorEnTurno = ControladorCUModificarOperadorEnTurno.getInstance();
    public static void main(String[] args) throws SQLException, Exception {
        controladorModificarOperadorEnTurno.start();
        //controladorCULlamada.start();
    }
}
