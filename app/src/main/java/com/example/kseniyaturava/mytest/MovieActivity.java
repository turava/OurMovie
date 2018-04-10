package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
    ImageView img_movie;
    URL url2=null;
    Bitmap loadImage;
    private String user, titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        setTitle("Movie");
        //News listeners on images

        img_movie = (ImageView) findViewById(R.id.img_movie);
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

        user = getIntent().getExtras().getString("User");
        Bundle bundle=this.getIntent().getExtras();
        if ((bundle!=null)&&(bundle.getString("Titulo")!=null)){
            titulo=bundle.getString("Titulo");
            text_title.setText(titulo);
        }

        button_foro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo=String.valueOf(text_title.getText());
                Intent intent = new Intent(MovieActivity.this, ForoActivity.class);
                intent.putExtra("Titulo", titulo);
                intent.putExtra("User", user);
                startActivity(intent);
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
                Thread tr=new Thread(){
                    @Override
                    public void run() {
                        try {
                            sumarVoto(text_numStar.getText().toString(), text_title.getText().toString());
                            //INSERT Estrellas
                            insertVoto(text_title.getText().toString(), user);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MovieActivity.this, "Gracias por dar una Estrella!!", Toast.LENGTH_LONG).show();
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
                                    if ((res.charAt(i)==(',')&& res.charAt(i+1)==('"')) || (res.charAt(i)==('}')&& res.charAt(i+1)==(']'))) {
                                        longitud = i;
                                        palabra = res.substring(inicio, longitud);
                                        inicio = longitud + 1;
                                        if (palabra.contains("Anyo_Film")) {
                                            String anyo = palabra.substring(13, palabra.length() - 1);
                                            text_date.setText(anyo);
                                        } else if (palabra.contains("Genero_Film")) {
                                            String genero = palabra.substring(15, palabra.length() - 1);
                                            text_genero.setText(genero);
                                        } else if (palabra.contains("Director_Film")) {
                                            String director = palabra.substring(17, palabra.length() - 1);
                                            text_director.setText(director);
                                        } else if (palabra.contains("Actor1")) {
                                            String actor1 = palabra.substring(10, palabra.length() - 1);
                                            text_1actor.setText(actor1);
                                        } else if (palabra.contains("Actor2")) {
                                            String actor2 = palabra.substring(10, palabra.length() - 1);
                                            text_2actor.setText(actor2);
                                        } else if (palabra.contains("Actor3")) {
                                            String actor3 = palabra.substring(10, palabra.length() - 1);
                                            text_3actor.setText(actor3);
                                        } else if (palabra.contains("Actor4")) {
                                            String actor4 = palabra.substring(10, palabra.length() - 1);
                                            text_4actor.setText(actor4);
                                        } else if (palabra.contains("Descripcion_Film")) {
                                            String descripcion = palabra.substring(20, palabra.length() - 1);
                                            text_description.setText(descripcion);
                                        } else if (palabra.contains("Votos_Estrella")) {
                                            String num_star = palabra.substring(18, palabra.length() - 1);
                                            text_numStar.setText(num_star);
                                        } else if (palabra.contains("Num_Coments")) {
                                            String num_com = palabra.substring(15, palabra.length() - 1);
                                            text_numCom.setText(num_com);
                                        } else if (palabra.contains("Imagen")) {
                                            String imagen = palabra.substring(10, palabra.length() - 1);
                                            try{
                                                url2 = new URL(imagen);
                                                HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                                                conn.connect();
                                                Picasso.with(MovieActivity.this).load(String.valueOf(url2)).into(img_movie);
                                            } catch (IOException e) {
                                                Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+e.getMessage(),Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }
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

    @Override
    protected void onResume() {
        super.onResume();
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
                                    if ((res.charAt(i)==(',')&& res.charAt(i+1)==('"')) || (res.charAt(i)==('}')&& res.charAt(i+1)==(']'))) {
                                        longitud = i;
                                        palabra = res.substring(inicio, longitud);
                                        inicio = longitud + 1;
                                        if (palabra.contains("Num_Coments")) {
                                            String num_com = palabra.substring(15, palabra.length() - 1);
                                            text_numCom.setText(num_com);

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

    public void sumarVoto(String estrella, String titulo)  throws IOException{
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/VotoUpdate.php?Votos_Estrella="+estrella+"&Titulo_Film="+titulo);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void insertVoto(String titulo, String user)  throws IOException{
       URL url=null;
        int respuesta;
        try {
            url=new URL("http://www.webelicurso.hol.es/VotoInsert.php?titulo="+titulo+"&user="+user);
            HttpURLConnection  conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //Override back button android to do something
  /* @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(MovieActivity.this, MainActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
        super.onBackPressed();
    }*/

}
