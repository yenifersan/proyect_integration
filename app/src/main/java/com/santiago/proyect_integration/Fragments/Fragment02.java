package com.santiago.proyect_integration.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.santiago.proyect_integration.Activities.MainActivity;
import com.santiago.proyect_integration.Interfaces.configuration;
import com.santiago.proyect_integration.Models.Curso;
import com.santiago.proyect_integration.Models.Horario;
import com.santiago.proyect_integration.Models.Participantes;
import com.santiago.proyect_integration.Models.Publicaciones;
import com.santiago.proyect_integration.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment02 extends Fragment {
    private static final String TAG = Fragment02.class.getSimpleName();


    private Button registar;
    private String param1;
    private EditText tituloEdit;
    private EditText descripEdit;
    private Spinner spinCurso;
    private Spinner spinHorario;
    private List<Curso> cursoList =new ArrayList<>();
    private List<Horario> horariosList=new ArrayList<>();

    public Fragment02() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        // Get argument param1 if exists
        if (getArguments() != null) {
            this.param1 = getArguments().getString("param1");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_fragment02, container, false);
        tituloEdit=view.findViewById(R.id.titulotext);
        descripEdit=view.findViewById(R.id.descripciontext);
        spinCurso=view.findViewById(R.id.cursospinner);
        spinHorario=view.findViewById(R.id.horariospinner);
        registar=view.findViewById(R.id.postear);
        llenarSpinnerCurso();
        llenarSpinnerHorario();

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPublicaciones();
            }
        });
        return view;
    }

    private void llenarSpinnerCurso(){
        Call<List<Curso>> call= configuration.jsonApiRetrofit().getCursos();
        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"No se obtubo los cursos",Toast.LENGTH_LONG).show();
                }
                cursoList =response.body();
                showListinSpinner();
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });

    }
    private void showListinSpinner(){
        //String array to store all the book names
        String[] items = new String[cursoList.size()];

        //Traversing through the whole list to get all the names
        for(int i = 0; i< cursoList.size(); i++){
            //Storing names to string array
            items[i] = cursoList.get(i).getNombre();
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        spinCurso.setAdapter(adapter);
        //Creating an array adapter for list view

    }
    private void llenarSpinnerHorario(){
        Call<List<Horario>> call= configuration.jsonApiRetrofit().getHorarios();
        call.enqueue(new Callback<List<Horario>>() {
            @Override
            public void onResponse(Call<List<Horario>> call, Response<List<Horario>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"No se obtubo los cursos",Toast.LENGTH_LONG).show();
                }
                horariosList=response.body();
                showListinSpinnerH();
            }

            @Override
            public void onFailure(Call<List<Horario>> call, Throwable t) {

            }
        });

    }
    private void showListinSpinnerH(){
        //String array to store all the book names
        String[] items = new String[horariosList.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<horariosList.size(); i++){
            //Storing names to string array
            items[i] = horariosList.get(i).getHoraInicio()+" - "+horariosList.get(i).getHoraFin();
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        spinHorario.setAdapter(adapter);
        //Creating an array adapter for list view

    }
    private void createPublicaciones(){
        String titulo=tituloEdit.getText().toString();
        String desc=descripEdit.getText().toString();
        String curso=spinCurso.getSelectedItem().toString();
        String horario=spinHorario.getSelectedItem().toString();
        int idCurso = 0;
        int idHorario = 0;
        for(int i = 0; i< cursoList.size(); i++){
            if(curso.equals(cursoList.get(i).getNombre())){
                idCurso= cursoList.get(i).getIdCursos();
            }
        }
        for (int i=0;i<horariosList.size();i++){
            if(horario.contains(horariosList.get(i).getHoraInicio()) && horario.contains(horariosList.get(i).getHoraFin())){
                idHorario=horariosList.get(i).getIdHorarios();
            }
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = dateformat.format(c.getTime());

        Curso cursos=new Curso(idCurso);
        Horario horario1=new Horario(idHorario);
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(getContext());
        //int id= sp.getInt("id",12);
        int id= 12;
        Participantes participante=new Participantes(id);
        Publicaciones publicaciones=new Publicaciones(titulo,desc,"en curso",datetime,cursos,horario1,participante);
        Call<Publicaciones> call=configuration.jsonApiRetrofit().crearPublicacionesCall(publicaciones);
        call.enqueue(new Callback<Publicaciones>() {
            @Override
            public void onResponse(Call<Publicaciones> call, Response<Publicaciones> response) {
                if (!response.isSuccessful()) {
                    Log.d("CONTENIDO", response.errorBody().contentType().toString());
                    Toast.makeText(getContext(), "code..." + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(),"Resgitrado correctamente",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainActivity.class));
            }

            @Override
            public void onFailure(Call<Publicaciones> call, Throwable t) {

            }
        });
    }
}
