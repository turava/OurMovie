package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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

public class HomeCategoryActivity extends AppCompatActivity {
    private GridView listView;
   // private final String QUERY_CATHEGORY = "http://www.webelicurso.hol.es/Categoria.php?";
    private GridView lv;
    private ImageView imagen;
    private String categoria = "";


    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //we are on the method when the menu's item is selected
                    //type inside the instructions TODO

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
        setContentView(R.layout.activity_home_category);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Categorías");

        // Ensure correct menu item is selected
        //this part works good- the app starts on index number by case order from 0 to...
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);


        categoria = getIntent().getExtras().getString("titulo");
        Toast.makeText(HomeCategoryActivity.this, categoria, Toast.LENGTH_LONG).show();



        //CONNECTION TO DB

        Thread tr = new Thread() {
            @Override
            public void run() {

                final String QUERY_CATHEGORY = "http://www.webelicurso.hol.es/Categoria.php?categoria="+categoria;

                //TODO query con el parametro de la categoria, que busca peliculas donde categoria sea =...
                final String cathegoryJSON = connectDB(QUERY_CATHEGORY);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //result of connection
                        int r = 0;
                        try {
                            r = objJSON(cathegoryJSON);
                            //return Array with data and go to method adapter listview
                            showJSON(cathegoryJSON);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (r < 0) {
                            Toast.makeText(HomeCategoryActivity.this, "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                        }
                    }


                });
            }
        };
        tr.start();

    }


    //METHODS TO CONNECT WITH BD
    public int objJSON(String respuesta) throws JSONException {

        int res = 0;
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
            url = new URL(QUERY);
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

//TODO
    //Get the data from Json and create the object to show with adapter on the screen
    public String[] showJSON(String respuesta) throws JSONException {
        String[] listaImg = new String[7];
        String[] listaTitulo = new String[7];
        JSONArray json = new JSONArray(respuesta);

        Peliculas peli[] = new Peliculas[7];
        //  Peliculas peli = new Peliculas();

        //loop to write values from JSON on object Pelicua

        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonArrayChild = json.getJSONObject(i);
            peli[i] = new Peliculas();
            peli[i].setId_Film(jsonArrayChild.optString("Id_Film"));
            peli[i].setImagen(jsonArrayChild.optString("Imagen"));
            peli[i].setTitulo_FIlm(jsonArrayChild.optString("Titulo_Film"));
        }
        //loop to set the values from object to the array
       // for (int i = 0; i < 7; i++) {
            listaImg[0] = peli[0].getImagen();
            listaTitulo[0] = peli[0].getTitulo_FIlm();
           Toast.makeText(HomeCategoryActivity.this, peli[0].getImagen() + peli[0].getId_Film(),
                    Toast.LENGTH_LONG).show();

        //}

        displayAdapter(listaImg, listaTitulo);// display the data from Array in listview with adapter
        return listaImg;
    }

    private void displayAdapter(final String listaImg[], final String listaTitulo[]) {
        imagen = findViewById(R.id.imagen);
        lv = (GridView) findViewById(R.id.listView);
        AdapterItem adapter = new AdapterItem(this, listaImg);//2nd param. data
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                for (int i = 0; i < 5; i++) {
                    Peliculas peli[] = new Peliculas[5];
                    peli[i] = new Peliculas();
                    peli[i].getId_Film();
                    peli[i].getImagen();

                }
                //Go to MovieActivity with params: title movie
                Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                intent.putExtra("titulo", listaTitulo[position]);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Hiciste click en el número "
                                +listaTitulo[position],
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}




