package com.santiago.proyect_integration.Models;

public class Curso {

    private int idCursos;
    private String nombre;

    public Curso(int idCursos) {
        this.idCursos = idCursos;
    }

    public int getIdCursos() {
        return idCursos;
    }

    public String getNombre() {
        return nombre;
    }
}
