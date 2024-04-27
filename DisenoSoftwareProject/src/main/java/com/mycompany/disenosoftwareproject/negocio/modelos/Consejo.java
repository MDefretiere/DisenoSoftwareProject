/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class Consejo {
    private String descripcion;
    private String resultado;
    private boolean soluciona;
    private LlamadaNoCritica llamada;
    
    public Consejo(String descripcion, String resultado, boolean soluciona, LlamadaNoCritica llamada) {
        this.descripcion = descripcion;
        this.resultado = resultado;
        this.soluciona = soluciona;
        this.llamada = llamada;
    }
}
