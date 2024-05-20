/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.disenosoftwareproject;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.VistaModificarOperadorEnTurno;
import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUAtenderLlamada;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class DisenoSoftwareProject {
    static ControladorCUAtenderLlamada controlador = new ControladorCUAtenderLlamada();
    public static void main(String[] args) throws SQLException, Exception {
        SwingUtilities.invokeLater(() -> {
            VistaModificarOperadorEnTurno vistaModificadorEnTurno = new VistaModificarOperadorEnTurno();
            vistaModificadorEnTurno.setVisible(true);
        });
        //controlador.start();
    }
}
