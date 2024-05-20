/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import java.util.List;

/**
 *
 * @author younrm
 */
public class Turno {
    
    private int id;
    private String fechaTurno;
    private TipoDeTurno tipoTurno;
    private List<DetallesEmergencia> emergencias;
    
    public Turno(int id, String fechaTurno, TipoDeTurno tipoTurno, List<DetallesEmergencia> emergencias) {
        this.id = id;
        this.fechaTurno = fechaTurno;
        this.tipoTurno = tipoTurno;
        this.emergencias = emergencias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(String fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public TipoDeTurno getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(TipoDeTurno tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public List<DetallesEmergencia> getEmergencias() {
        return emergencias;
    }

    public void setEmergencias(List<DetallesEmergencia> emergencias) {
        this.emergencias = emergencias;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", fechaTurno='" + fechaTurno + '\'' +
                ", tipoTurno=" + tipoTurno +
                ", emergencias=" + emergencias +
                '}';
    }
}