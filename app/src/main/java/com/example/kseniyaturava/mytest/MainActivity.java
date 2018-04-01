package com.example.kseniyaturava.mytest;
//import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*
      @author Kseniyaa Turava
      @author Elisenda Coca
     */
    //Menu & Activities code here
    //method Listener
    private ImageView img_horror;//genero
    private ImageView img_drama;
    private ImageView img_comedy;
    private ImageView img_fiction;
    private ImageView img_new2;//interactua
    private ImageView img_new3;//interactua
    private ImageView img_new1;//interactua
    private ImageView visorImatge;
    private ImageView img_news_1, img_news_2, img_news_3, img_news_4, img_news_5,
            img_news_6, img_news_7; //novedades



    final String QUERY_CATEGORY = "http://www.webelicurso.hol.es/homeConnection.php?";
    final String QUERY_NEWS = "http://www.webelicurso.hol.es/homeConnection2.php?";

    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            return true;
                        case R.id.searchItem:
                           startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            return true;
                        case R.id.formItem:
                            startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            return true;
                        case R.id.notificationItem:
                            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            return true;
                        case R.id.profileItem:
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
        setContentView(R.layout.activity_main);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("OurMovie");
        // Ensure correct menu item is selected
        //this part works good- the app starts on index number by case order from 0 to...
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);

        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);

        //Popular listeners on images
        visorImatge = findViewById(R.id.img_news_1);
        visorImatge.setOnClickListener(this);
        visorImatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));

            }
        });
        //News listeners on images

        img_news_1 = findViewById(R.id.img_news_1);
        img_news_2 = findViewById(R.id.img_news_2);
        img_news_3 = findViewById(R.id.img_news_3);
        img_news_4 = findViewById(R.id.img_news_4);
        img_news_5 = findViewById(R.id.img_news_5);
        img_news_6 = findViewById(R.id.img_news_6);
        img_news_7 = findViewById(R.id.img_news_7);


        img_new1 = (ImageView) findViewById(R.id.img_new1);
        img_new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });
         img_new2 = (ImageView) findViewById(R.id.img_new2);
        img_new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });

        img_new3 = (ImageView) findViewById(R.id.img_new3);
        img_new3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });


        //Categories listeners on images
        img_drama = (ImageView) findViewById(R.id.img_drama);
        img_drama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeCategoryActivity.class));
            }
        });

        img_comedy = (ImageView) findViewById(R.id.img_comedy);
        img_comedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeCategoryActivity.class));

            }
        });
        img_fiction = (ImageView) findViewById(R.id.img_fiction);
        img_fiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeCategoryActivity.class));

            }
        });
        img_horror = (ImageView) findViewById(R.id.img_horror);
        img_horror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeCategoryActivity.class));

            }
        });

        //CONNECTION TO DB TODO

        Thread tr = new Thread() {
            @Override
            public void run() {


                final String cathegoryJson = connectDB(QUERY_CATEGORY);//consulta las categorias
                final String newsJson = connectDB(QUERY_NEWS);//consulta las novedades
                 runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //resultado de la conexión
                        int r = 0, r2 = 0, r3 = 0;
                        String accion="";
                        try {
                            //cargamos los resutados de las querys json.toString
                            accion = "categoria";
                            r = objJSON(cathegoryJson, accion);
                            accion = "news";
                            r2 = objJSON(newsJson,accion);
                            //r3 = objJSON(newsJson);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (r > 0) {
                            Toast.makeText(MainActivity.this, "REQUEST CORRECT", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MainActivity.this, "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                        }
                    }


                });
            }
        };
        tr.start();

    }


    //METHODS TO CONNECT WITH BD
    public int objJSON(String respuesta, String accion) throws JSONException {

        int res = 0;
        // function to show json
        showJSON(respuesta, accion);//PICASSO LIBRARY To set images form url

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
    public void showJSON(String respuesta, String accion) throws JSONException {

        JSONArray json = new JSONArray(respuesta);


        if(accion.equalsIgnoreCase("categoria")) {
            Peliculas  peli []= new Peliculas[5];
            //bucle para escribir los valores del json en la clase pelicula
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonArrayChild = json.getJSONObject(i);
                // String imagen = jsonArrayChild.optString("Imagen");//extrae la clave del json
                peli[i] = new Peliculas();
                peli[i].setId_Genero(jsonArrayChild.optString("Id_Genero"));
                peli[i].setImagen(jsonArrayChild.optString("Imagen"));
            }
            //bucle para cargar los valores de la clase en el id de imageView
            //fit estira las imagenes, centercrop, centra pero recorta
            for (int i = 0; i < peli.length; i++) {
                //TODO subir nuevas imagenes
                Picasso.with(MainActivity.this).load(peli[1].getImagen()).fit().centerCrop().into(img_drama);
                Picasso.with(MainActivity.this).load(peli[2].getImagen()).fit().centerCrop().into(img_comedy);
                Picasso.with(MainActivity.this).load(peli[3].getImagen()).fit().centerCrop().into(img_fiction);
                Picasso.with(MainActivity.this).load(peli[4].getImagen()).fit().centerCrop().into(img_horror);
               // Toast.makeText(MainActivity.this, peli[i].getImagen() + peli[1].getId_Genero(), Toast.LENGTH_LONG).show();
                //falta añadir peli de accion TODO
            }

        }
        else if(accion.equalsIgnoreCase("news"))
        {
            Peliculas  peli []= new Peliculas[8];
            //bucle para escribir los valores del json en la clase pelicula
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonArrayChild = json.getJSONObject(i);
                // String imagen = jsonArrayChild.optString("Imagen");//extrae la clave del json
                peli[i] = new Peliculas();
                peli[i].setId_Genero(jsonArrayChild.optString("Id_Film"));
                peli[i].setImagen(jsonArrayChild.optString("Imagen"));

            }//bucle para cargar los valores de la clase en el id de imageView
            //fit estira las imagenes, centercrop, centra pero recorta
            for (int i = 0; i < peli.length; i++) {

                Picasso.with(MainActivity.this).load(peli[0].getImagen()).fit().centerCrop().into(img_news_2);
                Picasso.with(MainActivity.this).load(peli[1].getImagen()).fit().centerCrop().into(img_news_6);
                Picasso.with(MainActivity.this).load(peli[2].getImagen()).fit().centerCrop().into(img_news_3);

                Picasso.with(MainActivity.this).load(peli[3].getImagen()).fit().centerCrop().into(img_news_4);
                Picasso.with(MainActivity.this).load(peli[4].getImagen()).fit().centerCrop().into(img_news_5);
                Picasso.with(MainActivity.this).load(peli[5].getImagen()).fit().centerCrop().into(img_news_1);
                Picasso.with(MainActivity.this).load(peli[6].getImagen()).fit().centerCrop().into(img_news_7);
                //   Toast.makeText(MainActivity.this,  i+peli[i].getImagen() + peli[i].getId_Film(), Toast.LENGTH_LONG).show();
                //falta añadir peli de accion TODO


                //asignaos con el mismo json, urls a las img Interactua
                //TODO asignar imagenes
                Picasso.with(MainActivity.this).load(peli[0].getImagen()).fit().centerCrop().into(img_new1);
                Picasso.with(MainActivity.this).load(peli[1].getImagen()).fit().centerCrop().into(img_new2);
                Picasso.with(MainActivity.this).load(peli[2].getImagen()).fit().centerCrop().into(img_new3);

            }

        }



    }
        @Override
        public void onClick (View view){

        }
    }

   /* @Override
    public void onClick(View v) {
        ImageView visorImatge = findViewById(R.id.imageView2);

       // Intent intent= new Intent(getApplicationContext() ,MovieActivity.class);
        startActivity(new Intent(getApplicationContext(), MovieActivity.class));
       // startActivity(intent);


    }
}*/
    /*     ImageView visorImatge = findViewById(R.id.imageView2);
            visorImatge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), MovieActivity.class));
                }
            });
    }*/




