/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOPoliza;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

/**
 *
 * @author defre
 */
public class Poliza {
    private String numeroPoliza;
    private Fecha fechaInicio;
    private Fecha fechaVencimiento;
    
    public Poliza(String numeroPoliza){
        this.numeroPoliza = numeroPoliza;
    }
    
    public Poliza(String numeroPoliza, Fecha fechaInicio, Fecha fechaVencimiento){
        this.numeroPoliza = numeroPoliza;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getNumero(){
        return numeroPoliza;
    }
}