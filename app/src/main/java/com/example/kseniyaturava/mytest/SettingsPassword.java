package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsPassword extends AppCompatActivity {
    private String user;
    private Button btn_save;
    private EditText et_pass1;
    private EditText et_pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_password);
        setTitle("Cambiar Contraseña");

        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
            user = bundle.getString("User");
        }
        //Inicializacion de eventos

        et_pass1 = (EditText) findViewById(R.id.et_pass1);
        et_pass2 = (EditText) findViewById(R.id.et_pass2);
        btn_save = (Button) findViewById(R.id.btn_save);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validacion
               String pass1 = et_pass1.getText().toString().trim();
               String pass2 = et_pass2.getText().toString().trim();
             boolean validation =   validatePassword(pass1, pass2);

        if(validation == true)
        {
            Thread tr = new Thread() {
                @Override
                public void run() {
                    try {
                        String password = et_pass1.getText().toString().trim();
                        insertarDatos(user, password);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SettingsPassword.this,
                                        "Se han guardado los cambios", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            tr.start();
            //Return to profile activity after update
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
        }
            }

        });

    }


    private boolean validatePassword(String pass1, String pass2) {
        boolean validation = false;

        if(pass1.isEmpty() || pass2.isEmpty())
        {
            Toast.makeText(SettingsPassword.this,
                    "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
        validation = false;
        }
        else if ( pass1.compareTo(pass2) < 0 || pass1.compareTo(pass2) > 0){
            validation= false;
            Toast.makeText(SettingsPassword.this,
                    "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
        else if (pass1.length() < 6 || pass1.length() < 6){
            Toast.makeText(SettingsPassword.this,
                    "Mínimo 6 caracteres", Toast.LENGTH_LONG).show();
            validation= false;
        }
        else if (pass1.length() > 15 || pass1.length() > 15){
            Toast.makeText(SettingsPassword.this,
                    "Máximo 15 caracteres", Toast.LENGTH_LONG).show();
            validation= false;
        }
        else if (pass1.compareTo(pass2) == 0){
            validation= true;
        }


     return validation;
    }

    //Update query for boton "save" event
    public void insertarDatos(String user, String password)
            throws IOException{
        URL url=null;
        int respuesta;

        try {
            //String with url and data from form
            final String QUERY_UPDATE_PASS =
                    "http://www.webelicurso.hol.es/SettingsUpdatePassword.php?user="+user+"&password="+password;
            url=new URL(QUERY_UPDATE_PASS);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

