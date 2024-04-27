/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.disenosoftwareproject.negocio.modelos;

/**
 *
 * @author defre
 */
public class RequiereOperativo {
    private Direccion direccionDondeAcudir;
    private Operativo operativo;
    
    RequiereOperativo(Direccion direccionDondeAcudir, Operativo operativo){
        this.direccionDondeAcudir = direccionDondeAcudir;
        this.operativo = operativo;
    }
}
