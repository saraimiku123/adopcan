package com.example.adopcanapp;

public class Usuario {
   int id;
     String telefono;

    String nombre;

     String contrasenia;

     String tipo_usuario;

    public Usuario(int id, String telefono, String nombre, String contrasenia, String tipo_usuario) {
        this.id = id;
        this.telefono = telefono;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.tipo_usuario = tipo_usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
