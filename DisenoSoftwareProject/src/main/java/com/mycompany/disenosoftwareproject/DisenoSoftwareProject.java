/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.disenosoftwareproject;

import com.mycompany.disenosoftwareproject.negocio.controladores_caso_uso.ControladorCUIdentificarse;
import java.sql.SQLException;

/**
 *
 * @author defre
 */
public class DisenoSoftwareProject {
    private static ControladorCUIdentificarse controladorIdentificarse = ControladorCUIdentificarse.getInstance();
    public static void main(String[] args) throws SQLException, Exception {
        controladorIdentificarse.start();
    }
}
