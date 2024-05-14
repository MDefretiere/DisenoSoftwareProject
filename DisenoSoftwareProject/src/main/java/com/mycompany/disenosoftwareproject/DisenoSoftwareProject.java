/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.disenosoftwareproject;

import com.mycompany.disenosoftwareproject.interfaz.pares_vista_control.VistaModificarOperadorEnTurno;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

/**
 *
 * @author defre
 */
public class DisenoSoftwareProject {

    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            VistaModificarOperadorEnTurno vistaModificadorEnTurno = new VistaModificarOperadorEnTurno();
            vistaModificadorEnTurno.setVisible(true);
        });
    }
}
