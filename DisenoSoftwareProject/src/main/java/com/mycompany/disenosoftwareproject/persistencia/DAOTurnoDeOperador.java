/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.Empleado;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author defre
 */
public class DAOTurnoDeOperador {
    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "";
    private static final String motDePasse = "";
    
    public static List<TurnoDeOperador> getTurnosPorFecha(Date fecha){
        List<TurnoDeOperador> list = new ArrayList<TurnoDeOperador>();
        String query = "SELECT FECHATURNO, FECHACREACION, NOMBRETIPO"
                + "FROM TURNODEOPERADOR, TIPOTURNODEOPERADOR"
                + "WHERE TURNODEOPERADOR.IDTIPO=TIPOTURNODEOPERADOR.IDTIPO";
        
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement statement = conn.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
/*
            while (resultSet.next()) {
                TurnoDeOperador turno = mapperResultSetToEmpleado(resultSet);
                list.add(turno);
            }*/
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return list;
    }
}
