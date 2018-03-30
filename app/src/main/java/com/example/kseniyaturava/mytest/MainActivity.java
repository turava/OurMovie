package com.example.kseniyaturava.mytest;
//import android.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
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
    private Bitmap bitmap;//variable que guarda datos en hilo de ejecucion?
    private ImageView img_horror;
    private ImageView img_drama;
    private ImageView img_comedy;
    private ImageView img_fiction;

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
                            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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
        ImageView visorImatge = findViewById(R.id.imageView2);
        visorImatge.setOnClickListener(this);
        visorImatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));

            }
        });
        //News listeners on images
        ImageView img_new1 = (ImageView) findViewById(R.id.img_new1);
        img_new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });
        ImageView img_new2 = (ImageView) findViewById(R.id.img_new2);
        img_new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });
        ImageView img_new3 = (ImageView) findViewById(R.id.img_new3);
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


        //PICASSO LIBRARY TODO
        //String urlDrama = "http://www.webelicurso.hol.es/ImagenDrama.jpg";
        //Picasso.with(this).load(urlDrama).into(img_horror);


        //CONNECTION TO DB TODO

        Thread tr = new Thread() {
            @Override
            public void run() {
                final String res = connectDB();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //resultado de la conexión
                        int r = 0;
                        try {
                            r = objJSON(res);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            cargarImagenes(res);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (r > 0) {
                            Toast.makeText(MainActivity.this, "REQUEST CORRECT", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MainActivity.this, "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                        }
                    }

                    private void cargarImagenes(String res) throws JSONException {

                        //  Toast.makeText(MainActivity.this, res, Toast.LENGTH_LONG).show();


                        // JSONObject json = new JSONObject(res);
                        //JSONArray json=new JSONArray(res);


                        // String imagen = json.getString("Imagen");

                        //String urlDrama = imagen;
                        //Picasso.with(MainActivity.this).load(urlDrama).into(img_horror);


                        //String urlDrama = "http://www.webelicurso.hol.es/ImagenDrama.jpg";
                        //Picasso.with(MainActivity.this).load(urlDrama).into(img_horror);
                    }
                });
            }
        };
        tr.start();

    }


    //METHODS TO CONNECT WITH BD
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

    public String connectDB() {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul = null;

        try {
            url = new URL("http://www.webelicurso.hol.es/homeConnection.php?");
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
    public void showJSON(String respuesta) throws JSONException {

        JSONArray json = new JSONArray(respuesta);
        Peliculas  peli = new Peliculas();

        //pruebas
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonArrayChild = json.getJSONObject(i);
           // String imagen = jsonArrayChild.optString("Imagen");//extrae la clave del json
            peli.setImagen(jsonArrayChild.optString("Imagen"));
            //String img = json.getJSONObject(i).toString();//string json
            //String urlDrama = imagen;
            Picasso.with(MainActivity.this).load(peli.getImagen()).into(img_drama);

            Toast.makeText(MainActivity.this, peli.getImagen(), Toast.LENGTH_LONG).show();
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




