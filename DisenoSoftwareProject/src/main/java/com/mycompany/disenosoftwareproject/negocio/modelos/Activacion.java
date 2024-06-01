/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author younrm
 */
public class Activacion {
    private int id;
    private Fecha fecha;
    private Hora hora;
    
    public Activacion(int id, Fecha fecha, Hora hora) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
    }
    
    public int getId() {
        return id;
    }
    
    public Fecha getFecha() {
        return fecha;
    }
    
    public Hora getHora() {
        return hora;
    }
    
    public String toString(){
        return ""+fecha+","+hora+", ID:"+id;
    }
    
    public static Activacion stringToActivacion(String activacionString){
    String[] parts = activacionString.split(", ID:");
    String fechaHoraPart = parts[0];
    int id = Integer.parseInt(parts[1]);
    String[] fechaHoraParts = fechaHoraPart.split(",");
    String fechaStr = fechaHoraParts[0];
    String horaStr = fechaHoraParts[1];
    Fecha fecha = Fecha.parseFecha(fechaStr);
    Hora hora = Hora.parseHora(horaStr);
    return new Activacion(id, fecha, hora);
    }
}
