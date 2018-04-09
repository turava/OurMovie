package com.example.kseniyaturava.mytest;
//import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.ArrayList;


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
    private ImageView img_accion;
    private ImageView img_fiction;
    private ImageView img_new2;//interactua
    private ImageView img_new3;//interactua
    private ImageView img_new1;//interactua
    private String user;


    //Recycler
    private static final String TAG = "MainActivity";
    private ArrayList<String> titleList = new ArrayList<>();
    private ArrayList<String> imgList = new ArrayList<>();


    final String QUERY_CATEGORY = "http://www.webelicurso.hol.es/homeConnection.php?";
    final String QUERY_NEWS = "http://www.webelicurso.hol.es/homeConnection2.php?";
    private ListView lv;
    private ImageView imagen;

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
                            //startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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
                            //startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            Intent intent3 = new Intent(getApplicationContext(), AlertsActivity.class);
                            intent3.putExtra("User", user);
                            startActivity(intent3);
                            return true;
                        case R.id.profileItem:
                           // startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
        setContentView(R.layout.activity_main);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //setTitle("OurMovie");


        // Customize action bar title to center and other styles
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_ourmovie);


        // Ensure correct menu item is selected
        //this part works good- the app starts on index number by case order from 0 to...
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);

        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);




        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
            user = bundle.getString("User");
        }
        //Interactua
        img_new1 = (ImageView) findViewById(R.id.img_new1);
        img_new2 = (ImageView) findViewById(R.id.img_new2);
        img_new3 = (ImageView) findViewById(R.id.img_new3);
        //Categories listeners on images
        img_accion = (ImageView) findViewById(R.id.img_accion);
        img_accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeCategoryActivity.class);
                String categoria = "Acción";
                intent.putExtra("titulo", categoria);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });
        img_drama = (ImageView) findViewById(R.id.img_drama);
        img_drama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeCategoryActivity.class);
                String categoria = "Drama";
                intent.putExtra("titulo", categoria);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

        img_comedy = (ImageView) findViewById(R.id.img_comedy);
        img_comedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeCategoryActivity.class);
                String categoria = "Comedia";
                intent.putExtra("titulo", categoria);
                intent.putExtra("User", user);
                startActivity(intent);

            }
        });
        img_fiction = (ImageView) findViewById(R.id.img_fiction);
        img_fiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeCategoryActivity.class);
                String categoria = "Ciencia Ficción";
                intent.putExtra("titulo", categoria);
                intent.putExtra("User", user);
                startActivity(intent);

            }
        });
        img_horror = (ImageView) findViewById(R.id.img_horror);
        img_horror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeCategoryActivity.class);
                String categoria = "Terror";
                intent.putExtra("titulo", categoria);
                intent.putExtra("User", user);
                startActivity(intent);

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
                    try {
                        Toast.makeText(MainActivity.this,
                                user, Toast.LENGTH_LONG).show();


                        final Runnable runnable = new Runnable() {
                            public void run() {
                                int r = 0;
                                try {
                                  String  accion = "categoria";
                                   r = objJSON(cathegoryJson, accion, user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (r < 0) {
                                    Toast.makeText(MainActivity.this,
                                            "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        runnable.run();

                        final Runnable runnable1 = new Runnable() {
                            public void run() {
                                int r2 = 0;
                                try {
                                   String accion = "news";
                                   r2 = objJSON(newsJson, accion, user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (r2 < 0) {
                                    Toast.makeText(MainActivity.this,
                                            "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        //runnable1.wait(1*1000);
                        runnable1.run();
                        final Runnable runnable2= new Runnable() {
                            public void run() {
                                String accion = "foro";int r3 = 0;
                                try {
                                    r3 = objJSON(newsJson, accion, user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (r3 < 0) {
                                    Toast.makeText(MainActivity.this,
                                            "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        runnable2.run();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
        tr.start();
}

    //METHODS TO CONNECT WITH BD
    public int objJSON(String respuesta, String accion, String user) throws JSONException {
        int res = 0;
        // function to show json
        showJSON(respuesta, accion, user);//PICASSO LIBRARY To set images form url
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
    public void showJSON(String respuesta, String accion, final String user) throws JSONException
    {
        JSONArray json = new JSONArray(respuesta);
        if (accion.equalsIgnoreCase("categoria")) {
            Peliculas peli[] = new Peliculas[5];
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
                Picasso.with(MainActivity.this).load(peli[0].getImagen()).fit().centerCrop().into(img_accion);
                Picasso.with(MainActivity.this).load(peli[1].getImagen()).fit().centerCrop().into(img_drama);
                Picasso.with(MainActivity.this).load(peli[2].getImagen()).fit().centerCrop().into(img_fiction);
                Picasso.with(MainActivity.this).load(peli[3].getImagen()).fit().centerCrop().into(img_comedy);
                Picasso.with(MainActivity.this).load(peli[4].getImagen()).fit().centerCrop().into(img_horror);
                // Toast.makeText(MainActivity.this, peli[i].getImagen() + peli[1].getId_Genero(), Toast.LENGTH_LONG).show();
            }

        } else if (accion.equalsIgnoreCase("news")) {
            JSONArray json1 = new JSONArray(respuesta);
           // int count = json1.length();//count objects in json
            // System.out.println("**********"+count);
            int count = 8;
            String[] listaImg = new String[count];
            String[] listaTitulo = new String[count];
            Peliculas peli[] = new Peliculas[count];
            String countStr = Integer.toString(count);


            //  Peliculas peli = new Peliculas();
            //loop to write values from JSON on object Pelicua
            for (int i = 0; i < count; i++) {
                JSONObject jsonArrayChild = json.getJSONObject(i);

                peli[i] = new Peliculas();
                peli[i].setId_Film(jsonArrayChild.optString("Id_Film"));
                peli[i].setImagen(jsonArrayChild.optString("Imagen"));
                peli[i].setTitulo_FIlm(jsonArrayChild.optString("Titulo_Film"));
            }
            //loop to set the values from object to the array
            for (int i = 0; i < count; i++) {
                listaImg[i] = peli[i].getImagen();
                listaTitulo[i] = peli[i].getTitulo_FIlm();
                titleList.add(peli[i].getTitulo_FIlm());
                imgList.add(peli[i].getImagen());
            }
            //muestra las imagenes en horizontal con adapter
            initRecyclerView(imgList, titleList, user);

        } else if (accion.equalsIgnoreCase("foro")) {
            final Peliculas  peli []= new Peliculas[3];
            //bucle para escribir los valores del json en la clase pelicula
            for (int i = 0; i < peli.length; i++) {
                JSONObject jsonArrayChild = json.getJSONObject(i);
                // String imagen = jsonArrayChild.optString("Imagen");//extrae la clave del json
                peli[i] = new Peliculas();
                peli[i].setId_Genero(jsonArrayChild.optString("Id_Film"));
                peli[i].setImagen(jsonArrayChild.optString("Imagen"));
                peli[i].setTitulo_FIlm(jsonArrayChild.optString("Titulo_Film"));

            }//bucle para cargar los valores de la clase en el id de imageView
            //fit estira las imagenes, centercrop, centra pero recorta
            for (int i = 0; i < peli.length; i++) {

                //asignaos con el mismo json, urls a las img Interactua
                //TODO asignar imagenes
                Picasso.with(MainActivity.this).load(peli[0].getImagen()).fit().centerCrop().into(img_new1);
                Picasso.with(MainActivity.this).load(peli[1].getImagen()).fit().centerCrop().into(img_new2);
                Picasso.with(MainActivity.this).load(peli[2].getImagen()).fit().centerCrop().into(img_new3);

            }
            img_new3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ForoActivity.class);
                    intent.putExtra("Titulo", peli[2].getTitulo_FIlm());
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
            });
            img_new2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ForoActivity.class);
                    intent.putExtra("Titulo", peli[1].getTitulo_FIlm());
                    intent.putExtra("User", user);
                    startActivity(intent);

                }
            });
            img_new1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ForoActivity.class);
                    intent.putExtra("Titulo", peli[0].getTitulo_FIlm());
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
    }
    private void initRecyclerView(ArrayList<String> imgList, ArrayList<String> titleList, String user){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,user, titleList, imgList);
        recyclerView.setAdapter(adapter);

        //Event onclick in RecyclerViewAdapter

    }
}



