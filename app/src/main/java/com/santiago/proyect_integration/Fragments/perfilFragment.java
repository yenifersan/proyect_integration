package com.santiago.proyect_integration.Fragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.santiago.proyect_integration.Interfaces.configuration;
import com.santiago.proyect_integration.Models.Participantes;
import com.santiago.proyect_integration.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class perfilFragment extends Fragment {

    ImageView imagenPerfil;
    TextView textCorreo;
    TextView textNombre;
    TextView textCurso;
    TextView textCarrera;
    TextView textUsuario;
    TextView textCiclo;
    TextView texttelefono;
    String email;
    Participantes participante;

    public void perfilFragment(String email){
        this.email=email;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment03, container, false);
        imagenPerfil=v.findViewById(R.id.image_fotoPeril);
        textNombre=v.findViewById(R.id.text_nombrePerfil);
        textCorreo=v.findViewById(R.id.txt_emailPerfil);
        textUsuario=v.findViewById(R.id.txt_usuario);
        textCarrera=v.findViewById(R.id.txt_carrera);
        textCiclo=v.findViewById(R.id.txt_ciclo);
        texttelefono=v.findViewById(R.id.txt_telefono);

        getDataUser();
        //SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getContext());
        //int id=sp.getInt("id",0);
        //String email=sp.getString("email","no hay email");
        //String urlImage=sp.getString("imagen","No hay foto");
        //Log.d("VALRO DE URL",urlImage);
        //textNombre.setText(nombre);
        //textCorreo.setText("Correo: "+email);
        // imagenPerfil.setImageResource(R.drawable.usuario_icon);

        return v;

    }

    private void getDataUser(){
        Call<Participantes> participantesCall= configuration.jsonApiRetrofit().getParticipante(email);
        participantesCall.enqueue(new Callback<Participantes>() {
            @Override
            public void onResponse(Call<Participantes> call, Response<Participantes> response) {
                participante=response.body();
                textNombre.setText(participante.getNombre());
                textCorreo.setText(participante.getEmail());
                textCarrera.setText(participante.getCarrera().getNombre());
                textCiclo.setText(participante.getCiclo());
                texttelefono.setText(participante.getCelular());
                if(participante.getFoto().equals("vacio")){
                    imagenPerfil.setImageResource(R.drawable.usuario_icon);
                }else{
                    Uri uri = Uri.parse(participante.getFoto());
                    Glide.with(getContext()).load(uri).into(imagenPerfil);

                }
            }

            @Override
            public void onFailure(Call<Participantes> call, Throwable t) {

            }
        });
    }
}
