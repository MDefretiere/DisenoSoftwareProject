/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

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
    
    
    public static Fecha convertirDateSQLToLocalDate(java.sql.Date dateSQL) {
        int year = dateSQL.getYear() + 1900; // Ajoutez 1900 à l'année car java.sql.Date utilise un an - 1900
        int month = dateSQL.getMonth() + 1; // Ajoutez 1 au mois car java.sql.Date commence à compter les mois à partir de 0
        int day = dateSQL.getDate();
        return new Fecha(day, month, year);
    }
    
    public static java.sql.Date convertirLocalDateToDateSQL(Fecha fecha) {
        int year = fecha.ano;
        int month = fecha.mes;
        int day = fecha.dia;
        return new java.sql.Date(year - 1900, month - 1, day);
    }
    
    public static Fecha convertirLocalDateToFecha(Date date) {
        return new Fecha(date.getDate(), date.getMonth()+1, date.getYear()+1900);
    }

    
    public long getTime() {
        LocalDateTime localDateTime = LocalDate.of(ano, mes, dia).atStartOfDay();
        return localDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;
    }
    
    @Override
    public String toString(){
        return ""+dia+"/"+mes+"/"+ano;
    }
}
