/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author defre
 */
public class Hora {
    private int hh;
    private int mm;
    private int ss;
    
    public Hora(int hour, int minute, int second){
        hh=hour;
        mm=minute;
        ss=second;
    }
    
    public static Hora getCurrentHora() {
        LocalTime currentTime = LocalTime.now();
        return new Hora(currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
    }
    
    public boolean entreLasDos(Hora h1, Hora h2) {
        int currentSeconds = this.toSeconds();
        int startSeconds = h1.toSeconds();
        int endSeconds = h2.toSeconds();
        
        if (startSeconds <= endSeconds) {
            return currentSeconds >= startSeconds && currentSeconds <= endSeconds;
        } else {
            return currentSeconds >= startSeconds || currentSeconds <= endSeconds;
        }
    }

    private int toSeconds() {
        return hh * 3600 + mm * 60 + ss;
    }
    
    public String toString(){
        return ""+hh+":"+mm+":"+ss;
    }
    
    public static Hora parseHora(String horaString) {
        String[] parts = horaString.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid time format, expected HH:MM:SS");
        }
        
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        int second = Integer.parseInt(parts[2]);
        
        return new Hora(hour, minute, second);
    }
    
    public static Time convertirHoraToTimeSQL(Hora hora) {
        LocalTime localTime = LocalTime.of(hora.hh, hora.mm, hora.ss);
        return Time.valueOf(localTime);
    }
}
