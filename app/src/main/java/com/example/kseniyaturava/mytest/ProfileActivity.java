package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
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

public class ProfileActivity extends AppCompatActivity {
    TabHost Tabs;
    private final int PETICION_ACTIVITY_SEGUNDA = 1;
    private TextView tvName;
    private String user;
    private GridView lv;
    private ImageView imagen;
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
                            break;
                        case R.id.searchItem:
                            // setTitle("Search");
                            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            //startActivity(new Intent(MainActivity.this, SearchActivity.class));
                            break;
                        case R.id.formItem:
                            // setTitle("Form");
                            startActivity(new Intent(getApplicationContext(), FormActivity.class));
                            // startActivity(new Intent(MainActivity.this, FormActivity.class));
                            break;
                        case R.id.notificationItem:
                            // setTitle("Notifications");
                            startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                            break;
                        case R.id.profileItem:
                            // setTitle("Profile");
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            break;
                    }
                    finish();
                    return true;
                }

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Profile");
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);

        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
            user = bundle.getString("User");
        }

        //Functionality of TabsHost here
        Tabs = (TabHost) findViewById(R.id.tabs); //llamamos al Tabhost
        Tabs.setup();  //lo activamos

        TabHost.TabSpec tab1 = Tabs.newTabSpec("tab2");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = Tabs.newTabSpec("tab1");

        tab1.setIndicator("Foros");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.tab2); //definimos el id de cada Tab (pestaña)

        tab2.setIndicator("Favoritas");
        tab2.setContent(R.id.tab1);


        Tabs.addTab(tab1); //añadimos los tabs ya programados
        Tabs.addTab(tab2);

        /*Tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < Tabs.getTabWidget().getChildCount(); i++) {
                    Tabs.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#FF0000")); // unselected
                }

                Tabs.getTabWidget().getChildAt(Tabs.getCurrentTab())
                        .setBackgroundColor(Color.parseColor("#0000FF")); // selected

            }
        });*/



        //Listeners on click list "foros" selected
       /* ImageView img_goForo = (ImageView) findViewById(R.id.img_GoForo);
        img_goForo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForoActivity.class));

            }
        });

        //Listeners on click list "Favoritos" selected
        ImageView img_goMovie = (ImageView) findViewById(R.id.img_favourite1);
        img_goMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));

            }
        });
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(user);
*/
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(user);
        //CONNECTION TO DB


        Thread tr = new Thread() {
            @Override
            public void run() {

                final String QUERY_GET_TITLES_FAVORITES = "http://www.webelicurso.hol.es/ProfileGetTtitleFavorites.php?user="+user;
                final String titlesJson = connectDB(QUERY_GET_TITLES_FAVORITES);//consulta las categorias

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //result of connection
                        int r = 0;
                        try {
                            r = objJSON(titlesJson);
                            //Get movie's title and Url
                            TitleFavoritesJSON(titlesJson,user);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (r < 0) {
                            Toast.makeText(ProfileActivity.this,
                                    "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
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


    //Get the data from Json and create the object to show with adapter on the screen
    public void TitleFavoritesJSON(String respuesta,String user) throws JSONException {
        JSONArray json = new JSONArray(respuesta);
        int count = json.length();//count objects in json

        String[] listaImg = new String[count];
        String[] listaTitulo = new String[count];
        Peliculas peli[] = new Peliculas[count];
        //String countStr = Integer.toString(count);

        //GET movie's title  from JSON
        for (int i = 0; i < count; i++) {
            JSONObject jsonArrayChild = json.getJSONObject(i);
            peli[i] = new Peliculas();
            peli[i].setImagen(jsonArrayChild.optString("Imagen"));
            peli[i].setTitulo_FIlm(jsonArrayChild.optString("Titulo_Film"));
        }
        //loop to set the values from object to the array
        for (int i = 0; i < count; i++) {
            listaImg[i] = peli[i].getImagen();
            listaTitulo[i] = peli[i].getTitulo_FIlm();

          // Toast.makeText(ProfileActivity.this, "loop"+peli[i].getImagen() + peli[i].getTitulo_FIlm(),
            //       Toast.LENGTH_LONG).show();

        }

        displayAdapter(listaImg, listaTitulo);// display the data from Array in gridview with adapter
    }


    private void displayAdapter(final String listaImg[], final String listaTitulo[]) {
        imagen = findViewById(R.id.imagen);
        lv = (GridView) findViewById(R.id.listView);
        AdapterProfileForo adapter = new AdapterProfileForo(this, listaImg);//2nd param. data
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                //Go to MovieActivity with params: title movie
                Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                intent.putExtra("Titulo", listaTitulo[position]);
                startActivity(intent);
               /* Toast.makeText(getApplicationContext(),
                                listaTitulo[position],
                        Toast.LENGTH_LONG).show();*/
            }
        });


    }



    //MENU EVENTS
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        final MenuItem item=menu.findItem(R.id.menuProfile);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuProfile:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra("User", user);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
