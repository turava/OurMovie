package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kseniyaturava.mytest.Adapters.AdapterProfileFavorites;
import com.example.kseniyaturava.mytest.Adapters.AdapterProfileForo;
import com.example.kseniyaturava.mytest.Objects.Peliculas;

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
    private TextView tvName, tvDescription;
    private String user;
    private GridView lv;
    private ListView listview;
    private ImageView imagen;
    private TextView tv_title, numComments;
    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Intent intent0 = new Intent(getApplicationContext(), MainActivity.class);
                            intent0.putExtra("User", user);
                            startActivity(intent0);
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
        setContentView(R.layout.activity_profile);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Profile");
        // Customize action bar title to center and other styles
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_profile);

        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);

        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null) && (bundle.getString("User") != null)) {
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

        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(user);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        //CONNECTION TO DB
        //  ProfileUserDescription

        Thread tr = new Thread() {
            @Override
            public void run() {
                //QUERYS y CONEXIONES
                final String QUERY_FAVOURITES = "http://www.webelicurso.hol.es/ProfileGetTtitleFavorites.php?user=" + user;
                final String titlesJson = connectDB(QUERY_FAVOURITES);
                final String QUERY_USER_DESCR = "http://www.webelicurso.hol.es/ProfileUserDescription.php?user=" + user;
                final String DescripJson = connectDB(QUERY_USER_DESCR);
                final String QUERY_FORO = "http://www.webelicurso.hol.es/ProfileForos.php?user=" + user;
                final String forosJson = connectDB(QUERY_FORO);
                //TODO comentario foro
                //final String QUERY_FORO_COMM = "http://www.webelicurso.hol.es/ProfileForoComments.php?user=" + user;
                //final String commJson = connectDB(QUERY_FORO_COMM);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Get Data for "Favoritas" TAB
                            final Runnable runnable = new Runnable() {
                                public void run() {
                                    int r = 0;
                                    try {
                                        r = objJSON(titlesJson);
                                        //Get movie's title and Url
                                        GetFavorites(titlesJson, user);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (r < 0) {
                                        Toast.makeText(ProfileActivity.this,
                                                "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            runnable.run();

                            //Get User's description
                            final Runnable runnable1 = new Runnable() {
                                public void run() {
                                    int r = 0;
                                    try {
                                        r = objJSON(DescripJson);

                                        GetDescription(DescripJson, user);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (r < 0) {
                                        Toast.makeText(ProfileActivity.this,
                                                "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            runnable1.run();
                            //Get Data for "Foros" TAB
                            final Runnable runnable2 = new Runnable() {
                                public void run() {
                                    int r = 0;
                                    try {
                                        //TODO comentario en el foro
                                       // r = objJSON(commJson);
                                       // GetComentario(commJson);
                                        r = objJSON(DescripJson);
                                        GetForos(forosJson, user);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (r < 0) {
                                        Toast.makeText(ProfileActivity.this,
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
    public void GetFavorites(String respuesta, String user) throws JSONException {
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


        }

        displayAdapter(listaImg, listaTitulo);// display the data from Array in gridview with adapter
    }


    private void displayAdapter(final String listaImg[], final String listaTitulo[]) {
        imagen = findViewById(R.id.imagen);
        lv = (GridView) findViewById(R.id.gridView);
        AdapterProfileFavorites adapter = new AdapterProfileFavorites(this, listaImg, listaTitulo);//2nd param. data
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                //Go to MovieActivity with params: title movie
                Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                intent.putExtra("Titulo", listaTitulo[position]);
                startActivity(intent);
            }
        });


    }

    private void GetDescription(String descripJson, String user) throws JSONException {
        JSONArray json = new JSONArray(descripJson);
        JSONObject jsonArrayChild = json.getJSONObject(0);
        String descripcion = jsonArrayChild.optString("Descripcion");
        if (!(descripcion.trim().isEmpty() || descripcion.trim().equalsIgnoreCase("NULL"))) {
            tvDescription.setText(descripcion);
        }
    }
    /*private void GetComentario(String respuesta) throws JSONException {
        JSONArray json = new JSONArray(respuesta);
        int count = json.length();//count objects in json

        String[] ultimoComment = new String[count];
        Peliculas peli[] = new Peliculas[count];
        //String countStr = Integer.toString(count);

        for (int i = 0; i < count; i++) {
            JSONObject jsonArrayChild = json.getJSONObject(i);

            ultimoComment[i]=(jsonArrayChild.optString("Texto"));
            Toast.makeText(ProfileActivity.this,
                    "coment"+ultimoComment[i], Toast.LENGTH_LONG).show();

        }
        //loop to set the values from object to the array

    }*/


    private void GetForos(String respuesta, String user) throws JSONException {
        JSONArray json = new JSONArray(respuesta);
        int count = json.length();//count objects in json

        String[] listaImg = new String[count];
        String[] listaTitulo = new String[count];
        String[] numComents = new String[count];
        Peliculas peli[] = new Peliculas[count];
        //String countStr = Integer.toString(count);

        for (int i = 0; i < count; i++) {
            JSONObject jsonArrayChild = json.getJSONObject(i);
            peli[i] = new Peliculas();
            peli[i].setImagen(jsonArrayChild.optString("Imagen"));
            peli[i].setTitulo_FIlm(jsonArrayChild.optString("Titulo_Film"));
            peli[i].setNum_Coments(jsonArrayChild.optString("Num_Coments"));
        }
        //loop to set the values from object to the array
        for (int i = 0; i < count; i++) {
            listaImg[i] = peli[i].getImagen();
            listaTitulo[i] = peli[i].getTitulo_FIlm();
            numComents[i] = peli[i].getNum_Coments();


        }
        displayAdapterForo(listaImg, listaTitulo, numComents, user);

    }

    private void displayAdapterForo(final String[] listaImg, final String[] listaTitulo, final String[] numComents, String user) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        imagen = findViewById(R.id.imagen);
        listview = (ListView) findViewById(R.id.listView);
        numComments = (TextView) findViewById(R.id.numComments);
        AdapterProfileForo adapter = new AdapterProfileForo(this, listaImg, listaTitulo, numComents, user);//2nd param. data
        listview.setAdapter(adapter);
        //Listvie ONCLICK in Adapter, don't works good here
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
