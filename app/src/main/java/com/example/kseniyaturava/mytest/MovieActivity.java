package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class MovieActivity extends AppCompatActivity {

    Button button_foro;
    ImageButton button_comments, button_star;
    TextView text_title, text_date, text_genero, text_director, text_1actor, text_2actor, text_3actor, text_4actor, text_description, text_numCom, text_numStar;
    ImageButton img_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setTitle("Movie");
        //News listeners on images

        img_movie = (ImageButton) findViewById(R.id.img_movie);
        button_comments = (ImageButton) findViewById(R.id.button_comments);
        button_star = (ImageButton) findViewById(R.id.button_star);
        button_foro = (Button) findViewById(R.id.button_foro);
        text_title =(TextView) findViewById(R.id.text_title);
        text_date =(TextView) findViewById(R.id.text_date);
        text_genero =(TextView) findViewById(R.id.text_genero);
        text_director =(TextView) findViewById(R.id.text_director);
        text_1actor =(TextView) findViewById(R.id.text_1actor);
        text_2actor =(TextView) findViewById(R.id.text_2actor);
        text_3actor =(TextView) findViewById(R.id.text_3actor);
        text_4actor =(TextView) findViewById(R.id.text_4actor);
        text_description =(TextView) findViewById(R.id.text_description);
        text_numCom =(TextView) findViewById(R.id.text_numCom);
        text_numStar =(TextView) findViewById(R.id.text_numStar);

        //Faltará modificar esta inicialización, en lugar de 0 deberá ser el valor que tenga guardado en la bbdd
        text_numCom.setText("0");
        text_numStar.setText("0");

        button_foro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor=String.valueOf(text_numCom.getText());
                int num=Integer.parseInt(valor);
                int numFinal=num+1;
                String valorFinal=String.valueOf(numFinal);
                text_numCom.setText(valorFinal);
                startActivity(new Intent(getApplicationContext(), ForoActivity.class));
            }
        });

        button_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor=String.valueOf(text_numStar.getText());
                int num=Integer.parseInt(valor);
                int numFinal=num+1;
                String valorFinal=String.valueOf(numFinal);
                text_numStar.setText(valorFinal);
            }
        });

        Bundle bundle=this.getIntent().getExtras();
        if ((bundle!=null)&&(bundle.getString("Titulo")!=null)){
            String titulo=bundle.getString("Titulo");
            //Hasta que no funcione el que recoja bien el titulo del Search, pongo a mano un título de la bbdd
            text_title.setText("Matrix");
        }
        Thread tr=new Thread(){
            @Override
            public void run() {
                try {
                    final String res=recogerDatos(text_title.getText().toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int r=objJSON(res);
                            if(r>0){
                                int inicio=0;
                                int longitud;
                                String palabra;
                                for (int i=0;i<res.length();i++) {
                                    if (res.charAt(i)==(',')) {
                                        longitud=i;
                                        palabra=res.substring(inicio, longitud);
                                        inicio=longitud+1;
                                        if(palabra.contains("Anyo_Film")){
                                            String anyo=palabra.substring(13,palabra.length()-1);
                                            text_date.setText(anyo);
                                        }else if(palabra.contains("Genero_Film")){
                                            String genero=palabra.substring(15,palabra.length()-1);
                                            text_genero.setText(genero);
                                        }else if(palabra.contains("Director_Film")){
                                            String director=palabra.substring(17,palabra.length()-1);
                                            text_director.setText(director);
                                        }else if(palabra.contains("Actor1")){
                                            String actor1=palabra.substring(10,palabra.length()-1);
                                            text_1actor.setText(actor1);
                                        }else if(palabra.contains("Actor2")){
                                            String actor2=palabra.substring(10,palabra.length()-1);
                                            text_2actor.setText(actor2);
                                        }else if(palabra.contains("Actor3")){
                                            String actor3=palabra.substring(10,palabra.length()-1);
                                            text_3actor.setText(actor3);
                                        }else if(palabra.contains("Actor4")){
                                            String actor4=palabra.substring(10,palabra.length()-1);
                                            text_4actor.setText(actor4);
                                        }else if(palabra.contains("Descripcion_Film")) {
                                            String descripcion=palabra.substring(20,palabra.length()-1);
                                            text_description.setText(descripcion);
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(MovieActivity.this, "Esta pelicula no está en la base", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        tr.start();
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

    public String recogerDatos (String titulo) throws IOException {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;

        try {
            url=new URL("http://www.webelicurso.hol.es/MovieDatos.php?Titulo_Film="+titulo);
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
}
