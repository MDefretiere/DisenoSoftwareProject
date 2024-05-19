package com.mycompany.disenosoftwareproject.negocio.modelos;

import com.mycompany.disenosoftwareproject.persistencia.DAOAsegurado;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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

    public JsonObject toJson() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
            .add("nombre", getNombre())
            .add("apellidos", getApellidos())
            .add("fechaNacimiento", getFechaNacimiento().toString())
            .add("nif", getNif())
            .add("direccion", getDireccion().toJson())
            .add("telefono", getTelefono())
            .add("sexo", sexo.toString())
            .add("numeroDeCuenta", numeroDeCuenta);
        return jsonBuilder.build();
    }

    public static Asegurado comprobarAsegurado(String nombre, String apellidos, Fecha fechaNacimiento, Sexo sexo) {
        JsonObject jsonInput = Json.createObjectBuilder()
            .add("nombre", nombre)
            .add("apellidos", apellidos)
            .add("fechaNacimiento", fechaNacimiento.toString())
            .add("sexo", sexo.toString())
            .build();

        JsonObject json = DAOAsegurado.comprobarAsegurado(jsonInput);
        if (json == null) {
            return null;
        }
        return convertirDesdeJson(json);
    }

    public static Asegurado convertirDesdeJson(JsonObject json) {
        String nombre = json.getString("nombre");
        String apellidos = json.getString("apellidos");
        Fecha fechaNacimiento = Fecha.parseFecha(json.getString("fechaNacimiento"));
        String nif = json.getString("nif");
        Direccion direccion = Direccion.fromJson(json.getJsonObject("direccion"));
        String telefono = json.getString("telefono");
        Sexo sexo = Sexo.valueOf(json.getString("sexo"));
        String numeroDeCuenta = json.getString("numeroDeCuenta");

        return new Asegurado(nombre, apellidos, fechaNacimiento, nif, direccion, telefono, sexo, numeroDeCuenta);
    }

    public static boolean comprobarPacienteConPoliza(String nif, String numero) {
        JsonObject jsonInput = Json.createObjectBuilder()
            .add("nif", nif)
            .add("numeroPoliza", numero)
            .build();

        return DAOAsegurado.comprobarPacienteConPoliza(jsonInput).getBoolean("existe");
    }
}
