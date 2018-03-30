package com.example.kseniyaturava.mytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FormActivity extends AppCompatActivity {

      /*
      @author Kseniyaa Turava
      @author Elisenda Coca
     */
    //Menu & Activities code here
    //method Listener

    Button sendForm;
    EditText etTitulo, etDirector, etActor1, etActor2, etActor3, etActor4, etAño, etGenero, etDescripcion;

    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //we are on the method when the menu's item is selected
                    //type inside the instructions TODO
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            //setTitle("Explore");//Set the title ActionBar
                            //instance Activity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                           return true;
                        case R.id.searchItem:
                            // setTitle("Search");
                            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                            //startActivity(new Intent(MainActivity.this, SearchActivity.class));
                            return true;
                        case R.id.formItem:
                            // setTitle("Form");
                            startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            // startActivity(new Intent(MainActivity.this, FormActivity.class));
                            return true;
                        case R.id.notificationItem:
                            // setTitle("Notifications");
                            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            return true;
                        case R.id.profileItem:
                            // setTitle("Profile");
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            return true;
                    }
                    // finish();
                    return false;
                }

            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sendForm = (Button) findViewById(R.id.sendForm);
        etTitulo = (EditText) findViewById(R.id.etTitulo);
        etDirector = (EditText) findViewById(R.id.etDirector);
        etActor1 =(EditText) findViewById(R.id.etActor1);
        etActor2 =(EditText) findViewById(R.id.etActor2);
        etActor3 =(EditText) findViewById(R.id.etActor3);
        etActor4 =(EditText) findViewById(R.id.etActor4);
        etAño =(EditText) findViewById(R.id.etAño);
        etGenero =(EditText) findViewById(R.id.etGenero);
        etDescripcion =(EditText) findViewById(R.id.etDescripcion);

        setTitle("Form");
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView );

        sendForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr=new Thread(){
                    @Override
                    public void run() {
                        try {
                            insertarDatos(etTitulo.getText().toString(),etAño.getText().toString(),etGenero.getText().toString(),etDirector.getText().toString(),etActor1.getText().toString(),etActor2.getText().toString(),etActor3.getText().toString(),etActor4.getText().toString(),etDescripcion.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FormActivity.this, "Insertada pelicula en la base", Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                tr.start();
            }
        });
    }

    public void insertarDatos(String titulo, String año, String genero, String director, String actor1, String actor2, String actor3, String actor4, String descripcion)  throws IOException{
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/FormInsert.php?Titulo_Film="+titulo+"&Anyo_Film="+año+"&Genero_Film="+genero+"&Director_Film="+director+"&Actor1="+actor1+"&Actor2="+actor2+"&Actor3="+actor3+"&Actor4="+actor4+"&Descripcion_Film="+descripcion);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
