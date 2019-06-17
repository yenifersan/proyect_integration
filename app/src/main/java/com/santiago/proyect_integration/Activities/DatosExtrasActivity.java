    package com.santiago.proyect_integration.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.santiago.proyect_integration.Interfaces.configuration;
import com.santiago.proyect_integration.Models.Carrera;
import com.santiago.proyect_integration.Models.Participantes;
import com.santiago.proyect_integration.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class DatosExtrasActivity extends AppCompatActivity {
        private Spinner cicloSpinner;
        private Spinner carreraSpinner;
        private EditText usuario;
        private EditText telefono;
        private Button guardarb;

        int idCarrera;
        String nombre;
        String apellidos;
        String email;
        String usuarioId;
        Uri imagenUrl;
        String urlImagen;
        List<Carrera> carreraList=new ArrayList<>();
        GoogleSignInClient mGoogleSignInClient;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_datos_extras);

            usuario=findViewById(R.id.usuarioText);
            telefono=findViewById(R.id.telefonoText);
            cicloSpinner = findViewById(R.id.spinnerCiclo);
            carreraSpinner = findViewById(R.id.spinnerCarrera);
            guardarb=findViewById(R.id.guardarButton);
            llenarSpinnerCarrera();

            guardarb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guardaDatos();
                }
            });
        }
        private void llenarSpinnerCarrera(){
            Call<List<Carrera>> call= configuration.jsonApiRetrofit().getCarreras();
            call.enqueue(new Callback<List<Carrera>>() {
                @Override
                public void onResponse(Call<List<Carrera>> call, Response<List<Carrera>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"No se obtubo las carreras",Toast.LENGTH_LONG).show();
                    }
                    carreraList=response.body();
                    showListinSpinner();
                }

                @Override
                public void onFailure(Call<List<Carrera>> call, Throwable t) {

                }
            });

        }
        private void showListinSpinner(){
            //String array to store all the book names
            String[] items = new String[carreraList.size()];

            //Traversing through the whole list to get all the names
            for(int i=0; i<carreraList.size(); i++){
                //Storing names to string array
                items[i] = carreraList.get(i).getNombre();
            }

            //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
            //setting adapter to spinner
            carreraSpinner.setAdapter(adapter);
            //Creating an array adapter for list view

        }
        private void guardaDatos(){
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(DatosExtrasActivity.this);

            if (account != null) {
                nombre = account.getGivenName();
                apellidos = account.getFamilyName();
                email = account.getEmail();
                // usuarioId = nombre.replaceAll(" .*", "") + (Math.random() * 1000) + 1;
                ;
                imagenUrl = account.getPhotoUrl();

                if (imagenUrl == null) {
                    urlImagen = "vacio";
                } else {
                    urlImagen = imagenUrl.toString();
                }

                String user=usuario.getText().toString();
                String tel=telefono.getText().toString();
                String carrera=carreraSpinner.getSelectedItem().toString();
                String ciclo=cicloSpinner.getSelectedItem().toString();
                for(int i=0;i<carreraList.size();i++){
                    if(carrera.equals(carreraList.get(i).getNombre())){
                        idCarrera=carreraList.get(i).getIdCarreras();
                    }
                }
                Carrera carrera1=new Carrera(idCarrera);
                Participantes participantes = new Participantes(carrera1,ciclo,tel,urlImagen,email,user,nombre+" "+apellidos);
                Call<Participantes> participantesCall = configuration.jsonApiRetrofit().creaParticipantesCall(participantes);
                participantesCall.enqueue(new Callback<Participantes>() {
                    @Override
                    public void onResponse(Call<Participantes> call, Response<Participantes> response) {
                        if (!response.isSuccessful()) {
                            Log.d("CONTENIDO", response.errorBody().contentType().toString());
                            Toast.makeText(getApplicationContext(), "code..." + response.code(), Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Participantes participanteResponse = response.body();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            boolean success = sharedPreferences.edit()
                                    .putString("name", nombre + " " + apellidos)
                                    .putString("email", email)
                                    .putInt("id", participanteResponse.getIdParticipante())
                                    .putString("imagen", urlImagen)
                                    .commit();

                            // Log.d("URL",imagenUrl.toString());
                            Toast.makeText(getApplicationContext(), "Usuario agregado correctamente", Toast.LENGTH_LONG).show();
                            showPrincipal();
                        }
                    }

                    @Override
                    public void onFailure(Call<Participantes> call, Throwable t) {

                    }
                });
            }
        }
        private void showPrincipal(){
            startActivity(new Intent(this,MainActivity.class));
        }
    }
