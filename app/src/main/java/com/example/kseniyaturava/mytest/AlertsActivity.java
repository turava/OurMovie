package com.example.kseniyaturava.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kseniyaturava.mytest.Adapters.AdapterNotifications;
import com.example.kseniyaturava.mytest.Objects.Notifications;

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

public class AlertsActivity extends AppCompatActivity {
   private String user;
   private ListView listView;
    private String titulo;
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


   // private LinearLayout la1;
   // private LinearLayout la2;
   // private LinearLayout la4;
   // private LinearLayout la5;
   // private ImageButton im;
   // private ImageButton im4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        BottomNavigationView BottomNavigationView = findViewById(R.id.bottomNavigationView);
        BottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle("Notificaciones");
        //here the icon change color
        Menu menu = BottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        //disabled shift mode
        BottomNavigationViewHelper.removeShiftMode(BottomNavigationView);

        //Recoge user del Login
        Bundle bundle = this.getIntent().getExtras();
        if ((bundle != null)&&(bundle.getString("User")!=null)){
            user = bundle.getString("User");
        }
        Toast.makeText(AlertsActivity.this,
                "user"+user, Toast.LENGTH_LONG).show();


        /*la1=(LinearLayout) findViewById(R.id.layout_alert1);
        la2=(LinearLayout) findViewById(R.id.layout_alert2);
        la4=(LinearLayout) findViewById(R.id.layout_alert4);
        la5=(LinearLayout) findViewById(R.id.layout_alert5);
        im=(ImageButton) findViewById(R.id.button_comments);
        im4=(ImageButton) findViewById(R.id.button_comments4);
*/
       /* la1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                la1.setBackgroundColor(Color.rgb(218,236,241));
                im.setBackgroundColor(Color.rgb(218,236,241));
                Intent intent = new Intent(AlertsActivity.this, ForoActivity.class);
                startActivity(intent);
            }
        });

        la2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                la2.setBackgroundColor(Color.rgb(218,236,241));
            }
        });

        la4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                la4.setBackgroundColor(Color.rgb(218,236,241));
                im4.setBackgroundColor(Color.rgb(218,236,241));
                Intent intent = new Intent(AlertsActivity.this, ForoActivity.class);
                startActivity(intent);
            }
        });

        la5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                la5.setBackgroundColor(Color.rgb(218,236,241));
            }
        });*/

        Thread tr = new Thread() {
            @Override
            public void run() {
                //QUERYS y CONEXIONES
                final String QUERY_NOTIFICATIONS = "http://www.webelicurso.hol.es/Notifications.php?user="+user;
                final String notificationsJson = connectDB(QUERY_NOTIFICATIONS);
                //final String QUERY_USER_DESCR = "http://www.webelicurso.hol.es/ProfileUserDescription.php?user=" + user;
                //final String DescripJson = connectDB(QUERY_USER_DESCR);
                //final String QUERY_FORO = "http://www.webelicurso.hol.es/ProfileForos.php?user=" + user;
                //final String forosJson = connectDB(QUERY_FORO);

                //TODO
                //user="eli.coca";
                // final String QUERY_insert = "http://www.webelicurso.hol.es/ForoNotif.php?user="+user+"titulo="+titulo;
                //connectDB(QUERY_insert);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Get notifications where read = 0 AND user = user
                            final Runnable runnable = new Runnable() {
                                public void run() {
                                    int r = 0;
                                    try {
                                        r = objJSON(notificationsJson);

                                      GetNotifications(notificationsJson, user);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (r < 0) {
                                        Toast.makeText(AlertsActivity.this,
                                                "No se puede establecer la conexión a internet", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            runnable.run();

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
    public void GetNotifications(String respuesta, String user) throws JSONException {
        JSONArray json = new JSONArray(respuesta);
        int count = json.length();//count objects in json

        String[] listaTexto = new String[count];
        String[] listaFechas = new String[count];
        String[] listaForos = new String[count];
        //String[] listaCom = new String[count];
        //String[] listaSubc = new String[count];
        //String[] listaIdForo = new String[count];
        String[] listaUser = new String[count];
        String[] listaNotif = new String[count];
        Notifications notifications[] = new Notifications[count];
        //String countStr = Integer.toString(count);

        //GET movie's title  from JSON
        for (int i = 0; i < count; i++) {
            JSONObject jsonArrayChild = json.getJSONObject(i);
            notifications[i] = new Notifications();
            notifications[i].setTexto(jsonArrayChild.optString("Texto"));
            notifications[i].setFecha(jsonArrayChild.optString("Fecha"));
            notifications[i].setTitulo_Film(jsonArrayChild.optString("Titulo_Film"));
            //notifications[i].setId_Comentario(jsonArrayChild.optString("Id_Comentario"));
            //notifications[i].setId_Subcomentario(jsonArrayChild.optString("Id_Subcomentario"));
            //notifications[i].setId_Foro(jsonArrayChild.optString("Id_Foro"));
            notifications[i].setUser(jsonArrayChild.optString("User"));
            notifications[i].setId_Notificacion(jsonArrayChild.optString("Id_Notificacion"));

          //  Toast.makeText(AlertsActivity.this, "notif"+notifications[i].getId_Notificacion(), Toast.LENGTH_LONG).show();

        }
        //loop to set the values from object to the array
        for (int i = 0; i < count; i++) {
            listaTexto[i] = notifications[i].getTexto();
            listaFechas[i] = notifications[i].getFecha();
            listaForos[i] = notifications[i].getTitulo_Film();
            //listaCom[i] = notifications[i].getId_Comentario();
            //listaSubc[i] = notifications[i].getId_Subcomentario();
            //listaIdForo[i] = notifications[i].getId_Foro();
            listaUser[i] = notifications[i].getUser();
            listaNotif[i] = notifications[i].getId_Notificacion();

        }

        displayAdapterUnread(listaTexto, listaFechas, listaForos, user, listaUser, listaNotif);// display the data from Array in gridview with adapter
    }
    private void displayAdapterUnread(final String listaTexto[], final String listaFechas[], final String listaForos[],
                                      String user, final String listaUser[], final String listaNotif[]) {
        listView = (ListView) findViewById(R.id.listView);
        AdapterNotifications adapter = new AdapterNotifications(this, listaTexto, listaFechas, listaForos, user, listaUser, listaNotif);//2nd param. data
        listView.setAdapter(adapter);
        //Listvie ONCLICK in Adapter, don't works good here


    }

}
