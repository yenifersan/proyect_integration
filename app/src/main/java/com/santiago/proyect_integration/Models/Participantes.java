package com.santiago.proyect_integration.Models;

public class Participantes {

   // private int id;
    private Carrera carrera;
    private String ciclo;
    private String celular;
    private String foto;
    private String email;
    private String usuario;
    private String nombre;
    private int idParticipante;

    public Participantes(Carrera carrera, String ciclo, String celular, String foto, String email, String usuario, String nombre) {
        this.carrera = carrera;
        this.ciclo = ciclo;
        this.celular = celular;
        this.foto = foto;
        this.email = email;
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public Participantes(int idParticipante) {
        this.idParticipante = idParticipante;
    }
    /*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }
}
