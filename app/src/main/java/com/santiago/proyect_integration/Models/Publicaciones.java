package com.santiago.proyect_integration.Models;

public class Publicaciones {

    private String titulo;
    private String descripcion;
    private String etiqueta;
    private String estado;
    private String fecha;
    private String tipo;
    private Curso curso;
    private Horario horario;
    private Participantes participante;

    public Publicaciones(String titulo, String descripcion, String estado, String fecha, Curso curso, Horario horario, Participantes participante) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.curso = curso;
        this.horario = horario;
        this.participante = participante;
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

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Participantes getParticipante() {
        return participante;
    }

    public void setParticipante(Participantes participante) {
        this.participante = participante;
    }


}
