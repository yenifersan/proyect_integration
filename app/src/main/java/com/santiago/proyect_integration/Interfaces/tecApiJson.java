package com.santiago.proyect_integration.Interfaces;


import com.santiago.proyect_integration.Models.Carrera;
import com.santiago.proyect_integration.Models.Curso;
import com.santiago.proyect_integration.Models.Horario;
import com.santiago.proyect_integration.Models.Participantes;
import com.santiago.proyect_integration.Models.Publicaciones;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface tecApiJson {

    @POST("participante/")
    Call<Participantes> creaParticipantesCall(@Body Participantes participantes);
    @POST("publicacion/")
    Call<Publicaciones> crearPublicacionesCall(@Body Publicaciones publicaciones);
    @GET("publicaciones/")
    Call<List<Publicaciones>> getPublicaciones();
    @GET("carreras/")
    Call<List<Carrera>> getCarreras();
    @GET("cursos/")
    Call<List<Curso>> getCursos();
    @GET("horarios/")
    Call<List<Horario>> getHorarios();
    @GET("participanteCorreo/{email}")
    Call<Participantes> getParticipante(@Path("email") String email);
}
