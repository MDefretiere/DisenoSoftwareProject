package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOAsegurado;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * Auteur: defre
 */
public class Asegurado extends Persona {
    private Sexo sexo;
    private String numeroDeCuenta;

    public Asegurado(String nombre, String apellidos, Fecha fechaNacimiento, String nif, Direccion direccion, String telefono, Sexo sexo, String numeroDeCuenta) {
        super(nombre, apellidos, fechaNacimiento, nif, direccion, telefono);
        this.sexo = sexo;
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }
}
