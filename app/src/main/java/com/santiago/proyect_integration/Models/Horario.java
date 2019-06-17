package com.santiago.proyect_integration.Models;

public class Horario {
    private int idHorarios;
    private String horaInicio;
    private String horaFin;

    public Horario(int idHorarios) {
        this.idHorarios = idHorarios;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public int getIdHorarios() {
        return idHorarios;
    }

    public void setIdHorarios(int idHorarios) {
        this.idHorarios = idHorarios;
    }
}
