package com.example.solidplanner.Objetos;

public class Nota {

    //Atributos con los que contará una NOTA
    String id_nota, titulo, descripcion, fecha_nota, estado;

    public Nota() {

    }

    public Nota(String id_nota, String titulo, String descripcion, String fecha_nota) {
        this.id_nota = id_nota;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_nota = fecha_nota;
    }

    public String getId_nota() {
        return id_nota;
    }

    public void setId_nota(String id_nota) {
        this.id_nota = id_nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_nota() {
        return fecha_nota;
    }

    public void setFecha_nota(String fecha_nota) {
        this.fecha_nota = fecha_nota;
    }

    public String getEstado() {
        return estado;
    }

}
