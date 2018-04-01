package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {
    private
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull  MenuItem item) {

                    //we are on the method when the menu's item is selected
                    //type inside the instructions TODO
                    switch (item.getItemId()) {
                        case R.id.homeItem:
                            //setTitle("Explore");//Set the title ActionBar
                            //instance Activity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            // startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            return true;
                        case R.id.searchItem:
                            // setTitle("Search");
                            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
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


    ArrayAdapter<String> adapter;
    ListView lv;
    ArrayList<String> arrayMovies;
    protected String titulo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lv=(ListView)findViewById(R.id.listViewMovies);
        arrayMovies=new ArrayList<>();
        lv.setBackgroundColor(Color.WHITE);


        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Search");//Set the title ActionBar
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView );

        // Ensure correct menu item is selected
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        Thread tr=new Thread(){
            @Override
            public void run() {
                try {
                    final String res=recogerTitulos();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int r=objJSON(res);
                            if(r>0){
                                int inicio=0;
                                int longitud;
                                String palabra;
                                for (int i=0;i<res.length();i++) {
                                    if (res.charAt(i) == ('"') && res.charAt(i + 1) == ('}')) {
                                        longitud = i;
                                        palabra = res.substring(inicio, longitud);
                                        inicio = longitud + 3;
                                        if (palabra.startsWith("[")) {
                                            titulo = palabra.substring(17, palabra.length());
                                            arrayMovies.add(titulo);
                                            adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, arrayMovies);
                                            lv.setAdapter(adapter);
                                        }else if (!palabra.startsWith("[")) {
                                            titulo = palabra.substring(16, palabra.length());
                                            arrayMovies.add(titulo);
                                            adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, arrayMovies);
                                            lv.setAdapter(adapter);
                                        }
                                    }

                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        tr.start();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                titulo = arrayMovies.get(position);
                Intent intent = new Intent(SearchActivity.this, MovieActivity.class);
                intent.putExtra("Titulo", titulo);
                startActivity(intent);
            }
        });
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

    public String recogerTitulos () throws IOException {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;

        try {
            url=new URL("http://www.webelicurso.hol.es/ListaTitulos.php");
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem item=menu.findItem(R.id.menuSearch);
        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
