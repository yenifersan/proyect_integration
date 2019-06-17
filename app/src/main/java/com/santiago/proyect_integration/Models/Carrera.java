package com.santiago.proyect_integration.Models;

public class Carrera {
    private String nombre;
    private int idCarreras;

    public Carrera(int idCarreras){
        this.idCarreras=idCarreras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCarreras() {
        return idCarreras;
    }

    public void setIdCarreras(int idCarreras) {
        this.idCarreras = idCarreras;
    }
}
