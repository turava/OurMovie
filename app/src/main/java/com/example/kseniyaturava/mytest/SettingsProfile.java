package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kseniyaturava.mytest.Objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsProfile extends AppCompatActivity {
    private String user;
    private EditText et_user;
    private EditText et_description;
    private EditText et_email;
    private EditText et_city;
    private EditText et_age;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_profile);
        setTitle("Redactar Perfil");

        // Customize action bar title to center and other styles
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_settprofile);

        //  Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back36);



        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
            user = bundle.getString("User");
        }
        //Inicializacion de eventos

        et_user = (EditText) findViewById(R.id.et_user);
        et_user.setText(user);
        et_description = (EditText) findViewById(R.id.et_description);
        et_email = (EditText) findViewById(R.id.et_email);
        et_city = (EditText) findViewById(R.id.et_city);
        et_age = (EditText) findViewById(R.id.et_age);

        btn_save = (Button) findViewById(R.id.btn_save);

        //CONNECTION TO DB to get User Data

        Thread tr = new Thread() {
            @Override
            public void run() {
                final String QUERY_GET_USERDATA = "http://www.webelicurso.hol.es/SettingsProfile.php?user="+user;
                final String userDataJson = connectDB(QUERY_GET_USERDATA);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Runnable runnable = new Runnable() {
                                public void run() {

                                    int r = 0;
                                    try {
                                        String  accion = "categoria";
                                        r = objJSON(userDataJson);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (r < 0) {
                                        Toast.makeText(SettingsProfile.this,
                                                "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            runnable.run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        tr.start();
        //CONNECTION TO DB to update User Data

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Thread tr = new Thread() {
            @Override
            public void run()
            {
                try
                {
                    String email = et_email.getText().toString().trim();
                    String age = et_age.getText().toString().trim();
                    String city = et_city.getText().toString().trim();
                    String description = et_description.getText().toString().trim();
                    String userName = et_user.getText().toString().trim();
                    insertarDatos(user, userName, description, email, age, city);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Toast.makeText(SettingsProfile.this,
                                    "Se han guardado los cambios", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
        tr.start();
                //Return to profile activity after update
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("User", et_user.getText().toString().trim());
                startActivity(intent);
            }
        });

        }

    //Update query for boton "save" event
    public void insertarDatos(String user, String userName, String description, String email, String age, String city)
            throws IOException{
        URL url=null;
        int respuesta;

        try {
            //String with url and data from form
            final String QUERY_GET_USERDATA = "http://www.webelicurso.hol.es/SettingsUpdateProfile.php?user="
                    +user+"&userName="+userName+"&description="+description+"&email="+email+"&age="+age+"&city="+city;
            url=new URL(QUERY_GET_USERDATA);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //METHODS TO CONNECT WITH BD to get UserData
    public int objJSON(String respuesta) throws JSONException {
        int res = 0;
        // function to show json
        showJSON(respuesta);
        try {
            JSONArray json = new JSONArray(respuesta);
            if (json.length() > 0) {
                res = 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String connectDB(String QUERY) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;

        try {
            url = new URL(QUERY);//Pasamos la url por parametro, dependiendo del caso
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            respuesta = conection.getResponseCode();
            resul = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((linea = reader.readLine()) != null) {
                    resul.append(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resul.toString();
    }
    //Muestra los datos recogidos de la BD
    public void showJSON(String respuesta) throws JSONException
    {
        JSONArray json = new JSONArray(respuesta);
            //bucle para escribir los valores del json en la clase pelicula
        User usuario = new User();
        JSONObject jsonArrayChild = json.getJSONObject(0);
        usuario.setDescripcion(jsonArrayChild.optString("Descripcion"));
        usuario.setEmail_User(jsonArrayChild.optString("Email_User"));
        usuario.setEdad_User(jsonArrayChild.optString("Edad_User"));
        usuario.setCiudad_User(jsonArrayChild.optString("Ciudad_User"));


        et_email.setText(usuario.getEmail_User());
        et_city.setText(usuario.getCiudad_User());
        et_age.setText(usuario.getEdad_User());
        if(!usuario.getDescripcion().trim().equalsIgnoreCase("null")) {
            et_description.setText(usuario.getDescripcion());//comprueba que no sea null
        }


    }

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



}
