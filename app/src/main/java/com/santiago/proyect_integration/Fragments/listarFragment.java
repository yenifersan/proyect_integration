package com.santiago.proyect_integration.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.santiago.proyect_integration.Adapters.PublicacionesAdapters;
import com.santiago.proyect_integration.Interfaces.configuration;
import com.santiago.proyect_integration.Models.Publicaciones;
import com.santiago.proyect_integration.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listarFragment extends Fragment {
    // List<Publicaciones> publicacionesList;
    RecyclerView recyclerView;

    public listarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_listar, container, false);
        recyclerView=v.findViewById(R.id.publicacioneslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        iniciar();
        return v;
    }

    private void iniciar() {

        Call<List<Publicaciones>> call = configuration.jsonApiRetrofit().getPublicaciones();
        call.enqueue(new Callback<List<Publicaciones>>() {
            @Override
            public void onResponse(Call<List<Publicaciones>> call, Response<List<Publicaciones>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "No se obtubo las carreras", Toast.LENGTH_LONG).show();
                    return;
                }
                //publicacionesList=response.body();
                PublicacionesAdapters adapter = new PublicacionesAdapters();
                adapter.setPostsList(response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Publicaciones>> call, Throwable t) {

            }
        });

    }
}