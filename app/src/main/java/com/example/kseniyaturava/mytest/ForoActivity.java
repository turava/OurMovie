package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ForoActivity extends AppCompatActivity {
    private TextView movieDescription;

    TextView text_movie, text_director, text_year;
    ImageButton button_info;

/*
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

            };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);
        setTitle("Foro");
        /*BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView );
*/
        //Info Listener
        ImageButton button_info = (ImageButton) findViewById(R.id.button_info);
        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MovieActivity.class));
            }
        });
        //User img listener
        ImageView img_user = (ImageView) findViewById(R.id.img_user1);
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
        //acordeon appear on click icon comment/replies
        ImageButton acordeon = (ImageButton) findViewById(R.id.button_comments);
        acordeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout findMagicLl = (LinearLayout) findViewById(R.id.layout_acordeon);
                if (findMagicLl.getVisibility() == View.VISIBLE) {
                    findMagicLl.setVisibility(View.GONE);
                } else {
                    findMagicLl.setVisibility(View.VISIBLE);
                }
            }
        });
        //acordeon appear on click icon atach files in Reply
        ImageButton acordeonFiles = (ImageButton) findViewById(R.id.button_addFilesReply);
        acordeonFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout findMagicLl = (LinearLayout) findViewById(R.id.layout_fileReply);
                if (findMagicLl.getVisibility() == View.VISIBLE) {
                    findMagicLl.setVisibility(View.GONE);
                } else {
                    findMagicLl.setVisibility(View.VISIBLE);
                }
            }
        });
        //acordeon appear on click icon atach files on post
        ImageButton acordeonFilesPost = (ImageButton) findViewById(R.id.button_addFiles);
        acordeonFilesPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout findMagicLl = (LinearLayout) findViewById(R.id.layout_file);
                if (findMagicLl.getVisibility() == View.VISIBLE) {
                    findMagicLl.setVisibility(View.GONE);
                } else {
                    findMagicLl.setVisibility(View.VISIBLE);
                }
            }
        });

        text_movie =(TextView) findViewById(R.id.text_movie);
        text_director =(TextView) findViewById(R.id.text_director);
        text_year =(TextView) findViewById(R.id.text_year);
        button_info = (ImageButton) findViewById(R.id.button_info);

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo=text_movie.getText().toString();
                Intent intent = new Intent(ForoActivity.this, MovieActivity.class);
                intent.putExtra("Titulo", titulo);
                startActivity(intent);
            }
        });

        Bundle bundle=this.getIntent().getExtras();
        if ((bundle!=null)&&(bundle.getString("Titulo")!=null)){
            String titulo=bundle.getString("Titulo");
            text_movie.setText(titulo);
        }

        Thread tr=new Thread(){
            @Override
            public void run() {
                try {
                    final String res=recogerDatos(text_movie.getText().toString());
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
                                            text_year.setText(anyo);
                                        } else if (palabra.contains("Director_Film")) {
                                            String director = palabra.substring(17, palabra.length() - 1);
                                            text_director.setText(director);
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(ForoActivity.this, "Esta pelicula no está en la base", Toast.LENGTH_LONG).show();
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
