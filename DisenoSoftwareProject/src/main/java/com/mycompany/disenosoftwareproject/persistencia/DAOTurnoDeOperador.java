/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.persistencia;

import com.mycompany.disenosoftwareproject.negocio.modelos.*;
import com.mycompany.disenosoftwareproject.negocio.modelos.TurnoDeOperador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author defre
 */
public class DAOTurnoDeOperador {

    private static final String url = "jdbc:derby://localhost:1527/DBEmpresa";
    private static final String utilisateur = "root";
    private static final String motDePasse = "0000";

    public static List<TurnoDeOperador> getTurnosPorFecha(Fecha fecha) throws SQLException {
        List<TurnoDeOperador> list = new ArrayList<TurnoDeOperador>();
        String query = "SELECT TURNODEOPERADOR.IDTURNOOPERADOR, FECHATURNO, FECHACREACION, NOMBRETIPOTURNO, NIFCIF\n"
                + "FROM TURNODEOPERADOR\n"
                + "JOIN TIPODETURNODEOPERADOR ON TURNODEOPERADOR.TIPOTURNO = TIPODETURNODEOPERADOR.IDTIPOTURNO\n"
                + "JOIN OPERADORESENTURNO ON TURNODEOPERADOR.IDTURNOOPERADOR = OPERADORESENTURNO.IDTURNOOPERADOR\n"
                + "WHERE FECHATURNO=?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setDate(1, Fecha.convertirLocalDateToDateSQL(fecha));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TurnoDeOperador turno = mapperResultSetToTurnoDeOperador(resultSet);
                if (!list.contains(turno)) {
                    list.add(turno);
                } else {
                    list.remove(turno);
                    List<Empleado> listEnTurno = turno.getListOperador();
                    listEnTurno.add(DAOEmpleado.obtenerEmpleadoPorId((resultSet.getInt("IDOperador"))));
                    turno.setListEmpleado(listEnTurno);
                    list.add(turno);
                }

            }
        }
        return list;
    }

    public static TurnoDeOperador mapperResultSetToTurnoDeOperador(ResultSet resultSet) throws SQLException {
        int idTurno = resultSet.getInt("IDTURNOOPERADOR");
        String fechaCreaciontoString = resultSet.getString("fechaCreacion");
        java.sql.Date fechaCreacionSQL = java.sql.Date.valueOf(fechaCreaciontoString);
        Fecha fechaCreacion = Fecha.convertirDateSQLToLocalDate(fechaCreacionSQL);
        String fechaTurnotoString = resultSet.getString("fechaTurno");
        java.sql.Date fechaTurnoSQL = java.sql.Date.valueOf(fechaTurnotoString);
        Fecha fechaTurno = Fecha.convertirDateSQLToLocalDate(fechaTurnoSQL);
        String sTipo = resultSet.getString("NOMBRETIPOTURNO");
        List<Empleado> listOperador = new ArrayList<>();
        Empleado e = DAOEmpleado.obtenerEmpleadoPorId(resultSet.getInt("NIFCIF"));
        listOperador.add(e);
        switch (sTipo) {
            case "DeManana7" -> {
                return new TurnoDeOperador(idTurno, fechaCreacion, fechaTurno, TipoDeTurnoOperador.DeManana7, listOperador);
            }
            case "DeTarde15" -> {
                return new TurnoDeOperador(idTurno, fechaCreacion, fechaTurno, TipoDeTurnoOperador.DeTarde15, listOperador);
            }
            case "DeNoche23" -> {
                return new TurnoDeOperador(idTurno, fechaCreacion, fechaTurno, TipoDeTurnoOperador.DeNoche23, listOperador);
            }
            default ->
                throw new Error("No Tipo de Operador");
        }
    }

    public static void modificarOperadorEnTurno(TurnoDeOperador turnoAModificar, Empleado empleadoACambiar, Empleado nuevoEmpleado) throws SQLException {
        String query = "UPDATE OPERADORESENTURNO SET NifCif = ? WHERE IdTurnoOperador = ? AND NifCif = ?";

        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nuevoEmpleado.getNif());
            statement.setInt(2, turnoAModificar.getId());
            statement.setString(3, empleadoACambiar.getNif());
            statement.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
