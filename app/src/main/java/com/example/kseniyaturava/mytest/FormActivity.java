package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private String user, idGenero;

    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //we are on the method when the menu's item is selected
                    //type inside the instructions
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Intent intent0 = new Intent(getApplicationContext(), MainActivity.class);
                            intent0.putExtra("User", user);
                            startActivity(intent0);
                           return true;
                        case R.id.searchItem:
                           // startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                            Intent intent1 = new Intent(getApplicationContext(), SearchActivity.class);
                            intent1.putExtra("User", user);
                            startActivity(intent1);
                            return true;
                        case R.id.formItem:
                            //startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            Intent intent2 = new Intent(getApplicationContext(), FormActivity.class);
                            intent2.putExtra("User", user);
                            startActivity(intent2);
                            return true;
                        case R.id.notificationItem:
                           // startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            Intent intent3 = new Intent(getApplicationContext(), AlertsActivity.class);
                            intent3.putExtra("User", user);
                            startActivity(intent3);
                            return true;
                        case R.id.profileItem:
                            Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent4.putExtra("User", user);
                            startActivity(intent4);
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

        // Customize action bar title to center and other styles
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_form);

        //  Back Button
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back36);


        //Recoge user
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
           user = bundle.getString("User");
          //  Toast.makeText(FormActivity.this,user, Toast.LENGTH_LONG).show();
        }

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
                            actualizarIdForoPeli(etTitulo.getText().toString());
                            final String id = encontrarIdGeneroPeli(etTitulo.getText().toString(), etGenero.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int r = objJSON(id);
                                    if (r > 0) {
                                        int inicio = 0;
                                        int longitud= id.length()-1;
                                        String palabra;
                                        palabra = id.substring(inicio, longitud);
                                        idGenero = palabra.substring(15, palabra.length() - 2);

                                        Thread trUp = new Thread() {
                                            @Override
                                            public void run() {
                                                try {
                                                    actualizarIdGeneroPeli(etTitulo.getText().toString(), idGenero);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };
                                        trUp.start();
                                    }
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

    public int objJSON(String respuesta) {
        int res=0;
        try{
            JSONArray json=new JSONArray(respuesta);
            if(json.length()>0){
                res=1;
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    //Insertamos los datos que ha introducido el user en la tabla peliculas
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

    //En el formulario de nueva pelicula que rellena el user, no se le piden algunos campos internos de la base como los id y
    // quedan con valor 0.
    //Es el caso de Id_Foro, que debe ser el mismo que el campo autocompletable de la tabla peliculas Id_Film y aquí hacemos
    // que se actualice una vez el registro de nueva peli ya ha volcado a la tabla.
    public void actualizarIdForoPeli (String titulo) throws IOException{
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/PeliUpdate.php?Titulo_Film="+titulo);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Lo mismo ocurre con el Id_Genero. En este caso, necesitamos primero hacer un select para encontrar el id que le corresponde
    //en la tabla genero.
    public String encontrarIdGeneroPeli(String titulo, String genero)  throws IOException{
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;

        try {
            url=new URL("http://www.webelicurso.hol.es/PeliUpdate2.php?Titulo_Film="+titulo+"&Genero_Film="+genero);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            resul=new StringBuilder();
            if (respuesta==HttpURLConnection.HTTP_OK){
                InputStream in=new BufferedInputStream(conection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                while((linea=reader.readLine())!=null){
                    resul.append(linea);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resul.toString();
    }

    //Por último, actualizamos en la tabla peliculas el Id_Genero encontrado para dejar el nuevo registro creado con todos
    //los datos completos.
    public void actualizarIdGeneroPeli(String titulo, String idGenero) throws IOException{
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/PeliUpdate3.php?Titulo_Film="+titulo+"&Id_Genero="+idGenero);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
      //Button back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
     */

}
