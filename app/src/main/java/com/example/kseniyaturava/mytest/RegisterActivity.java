package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    Button btRegister;
    EditText etPassword, etEmail, etUser, etNombre, etEdad, etCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        btRegister = (Button) findViewById(R.id.register_button);
        etPassword = (EditText) findViewById(R.id.password);
        etEmail = (EditText) findViewById(R.id.email);
        etUser = (EditText) findViewById(R.id.user);
        etNombre = (EditText) findViewById(R.id.nombre);
        etEdad = (EditText) findViewById(R.id.edad);
        etCiudad = (EditText) findViewById(R.id.ciudad);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr = new Thread() {
                    @Override
                    public void run() {
                        try {
                            insertarUser(etEmail.getText().toString(), etUser.getText().toString(), etNombre.getText().toString(), etEdad.getText().toString(), etCiudad.getText().toString(), etPassword.getText().toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "Bienvenido a OurMovie!!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
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

    public void insertarUser (String email, String user, String nombre, String edad, String ciudad, String password) throws IOException {
        URL url=null;
        int respuesta;
        HttpURLConnection conection=null;

        try {
            url=new URL("http://www.webelicurso.hol.es/RegisterUser.php?Email_User="+email+"&User="+user+"&Nombre_User="+nombre+"&Edad_User="+edad+"&Ciudad_User="+ciudad+"&Password="+password);
            conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){

            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            conection.disconnect();
        }
    }
}
