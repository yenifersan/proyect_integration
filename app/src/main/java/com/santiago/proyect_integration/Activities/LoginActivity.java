package com.santiago.proyect_integration.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.santiago.proyect_integration.Interfaces.configuration;
import com.santiago.proyect_integration.Models.Participantes;
import com.santiago.proyect_integration.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    Participantes participante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing Views
        signInButton = findViewById(R.id.sign_in_button);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            if(account.getEmail().contains("@tecsup.edu.pe")){
                verficacionDatos(account.getEmail());
            }else {
                signOut();
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            verficacionDatos(account.getEmail());
        }
        super.onStart();
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoginActivity.this,"No es una cuenta Institucional",Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
    }
    private void verficacionDatos(String email){
        Call<Participantes> participantesCall= configuration.jsonApiRetrofit().getParticipante(email);
        participantesCall.enqueue(new Callback<Participantes>() {
            @Override
            public void onResponse(Call<Participantes> call, Response<Participantes> response) {
                participante=response.body();
                //condicional();
                if(participante.getUsuario().equals(null) && participante.getCelular().equals(null)
                        && participante.getCiclo().equals(null) && participante.getCarrera().equals(null)){
                    startActivity(new Intent(LoginActivity.this, DatosExtrasActivity.class));
                    Log.d("LOGIN","FALTA COMPLETAR DATOS");
                }if(!participante.getUsuario().equals(null) && !participante.getCelular().equals(null)
                        && !participante.getCiclo().equals(null) && !participante.getCarrera().equals(null)){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Log.d("LOGIN","TIENE DATOS");
                }if(response.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, DatosExtrasActivity.class));
                    Log.d("LOGIN", "CREAR NUEVO USUARIO");
                }
            }

            @Override
            public void onFailure(Call<Participantes> call, Throwable t) {

            }
        });
    }
    private void condicional(){

        if(participante.getUsuario().equals(null) && participante.getCelular().equals(null)
                && participante.getCiclo().equals(null) && participante.getCarrera().equals(null)){
            startActivity(new Intent(LoginActivity.this, DatosExtrasActivity.class));
            Log.d("LOGIN","FALTA COMPLETAR DATOS");
        }if(!participante.getUsuario().equals(null) && !participante.getCelular().equals(null)
                && !participante.getCiclo().equals(null) && !participante.getCarrera().equals(null)){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            Log.d("LOGIN","TIENE DATOS");
        }if(participante==null){
            startActivity(new Intent(LoginActivity.this, DatosExtrasActivity.class));
            Log.d("LOGIN", "CREAR NUEVO USUARIO");
        }
    }
}
