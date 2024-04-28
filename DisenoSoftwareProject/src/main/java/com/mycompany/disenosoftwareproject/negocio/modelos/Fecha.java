/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Fecha {
    private int dia;
    private int mes;
    private int ano;
    
    public Fecha(int dia, int mes, int ano){
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }
    
    public boolean entreLasDos(Fecha f1, Fecha f2){
        if ((this.compareTo(f1) >= 0) && (this.compareTo(f2) <= 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    public int compareTo(Fecha otraFecha) {
        if (this.ano != otraFecha.ano) {
            return Integer.compare(this.ano, otraFecha.ano);
        }
        if (this.mes != otraFecha.mes) {
            return Integer.compare(this.mes, otraFecha.mes);
        }
        return Integer.compare(this.dia, otraFecha.dia);
    }
}
