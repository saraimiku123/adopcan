package com.example.adopcanapp;

import java.io.Serializable;

public class Mascotas  implements Serializable{
    private final int id;
    private final String nombre;
    private final int edad;
    private final String sexo;
    private String talla;

    private String caracter;
    private double peso;
    private String tipo_mascota;
    private String raza;
    private String gustos;
    private String imagen;


    public Mascotas(int id, String nombre, int edad, String sexo, String talla, String caracter, double peso, String tipo_mascota, String raza, String gustos, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.talla = talla;
        this.caracter = caracter;
        this.peso = peso;
        this.tipo_mascota = tipo_mascota;
        this.raza = raza;
        this.gustos = gustos;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo;
    }

    public String getTalla() {
        return talla;
    }

    public String getCaracter() {
        return caracter;
    }

    public double getPeso() {
        return peso;
    }

    public String getTipo_mascota() {
        return tipo_mascota;
    }

    public String getRaza() {
        return raza;
    }

    public String getGustos() {
        return gustos;
    }

    public String getImagen() {
        return imagen;
    }
}


