package com.santiago.proyect_integration.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.santiago.proyect_integration.Fragments.Fragment02;
import com.santiago.proyect_integration.Fragments.listarFragment;
import com.santiago.proyect_integration.Fragments.perfilFragment;
import com.santiago.proyect_integration.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button sign_out;
    TextView nombreNav;
    ImageView imagenNav;
    NavigationView navView;
    private View headerView;


    String nombre;
    String apellidos;
    String email;
    String usuarioId;
    Uri imagenUrl;
    String urlImagen;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_out = findViewById(R.id.cerrar_sesion);
        navView=findViewById(R.id.nav_view);
        headerView=navView.getHeaderView(0);
        nombreNav=headerView.findViewById(R.id.textNameNav);
        imagenNav=headerView.findViewById(R.id.imagePerfilNav);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(MainActivity.this);

        if(account!=null){
            nombre=account.getGivenName();
            apellidos=account.getFamilyName();
            email=account.getEmail();
            usuarioId=nombre.replaceAll(" .*","")+(Math.random() * 1000) + 1;;
            imagenUrl=account.getPhotoUrl();


            if(imagenUrl==null){
                urlImagen="vacio";
            }else{
                urlImagen=imagenUrl.toString();
            }

            //Log.d("NOMBRE",nombre);
            nombreNav.setText(nombre);
            if (imagenUrl!=null) {
                Glide.with(this).load(imagenUrl).into(imagenNav);
            }
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //para el primer fragmen01 y fragment02


    public void showFirstFragment(View view){
        // Get FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Create FirstFragment
        Fragment fragment = new listarFragment();

        // Replace content
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack("tag").commit();
    }

    public void showSecondFragment(View view){
        // Get FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Create FirstFragment
        Fragment fragment = new Fragment02();

        // Send 'param1' argument to Fragment


        // Replace content
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack("tag").commit();
    }
    public void showTreeFragment(){
        // Get FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Create FirstFragment
        perfilFragment fragment = new perfilFragment();
        fragment.perfilFragment(email);

        // Send 'param1' argument to Fragment/*  Bundle args = new Bundle();
        //        args.putString("param1", "Hello Fragment!");
        //        fragment.setArguments(args);*/


        // Replace content
        fragmentManager.beginTransaction().replace(R.id.content, fragment).addToBackStack("tag").commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();


        if (id == R.id.nav_share) {
            //fragmentManager.beginTransaction().replace(R.id.conte,new Fragment01()).commit();

        } else if (id == R.id.nav_notifications) {
            //  fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment02()).commit();
        } else if (id == R.id.nav_Cuenta) {
            showTreeFragment();

        } else if (id == R.id.nav_favourite) {
            //  fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment01()).commit();

        }else if (id == R.id.nav_record) {
            //  fragmentManager.beginTransaction().replace(R.id.contenedor,new Fragment01()).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.cerrar_sesion) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPreferences.edit().clear().commit();
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this,"Cerrando Sesion",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
}
