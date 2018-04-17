package com.example.kseniyaturava.mytest.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kseniyaturava.mytest.ForoActivity;
import com.example.kseniyaturava.mytest.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kseniyaturava on 8/4/18.
 */

public class AdapterNotifications extends ArrayAdapter<String> {
    private final Activity context;
    private final String [] listaTexto;
    private final String [] listaFechas;
    private final String [] listaForos;
    private final String user;
    //private final String [] listaCom;
    //private final String [] listaSubc;
    //private final String [] listaIdForo;
    private final String [] listaUser;
    private final String [] listaNotif;

    public AdapterNotifications(Activity context, String[] listaTexto, String[] listaFechas, String[] listaForos,
                                String user, String[] listaUser, String[] listaNotif) {
        super(context, R.layout.listview_alerts,listaTexto);
        this.context=context;
        this.listaTexto = listaTexto;
        this.listaFechas = listaFechas;
        this.listaForos = listaForos;
        this.user = user;
        //this.listaCom = listaCom;
        //this.listaSubc = listaSubc;
       // this.listaIdForo = listaIdForo;
        this.listaUser = listaUser;
        this.listaNotif = listaNotif;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_alerts, null,true);

        //ImageView imagen = (ImageView) rowView.findViewById(R.id.imagen);
        TextView tv_comment = (TextView) rowView.findViewById(R.id.tv_comment);
        TextView tv_date = (TextView) rowView.findViewById(R.id.tv_date);
        TextView tv_header = (TextView) rowView.findViewById(R.id.tv_header);
        TextView tv_user = (TextView) rowView.findViewById(R.id.tv_user);
         final LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.layout);
        //imagen.setImageDrawable(dir.getImage());
        tv_date.setText(listaFechas[position]);
        tv_comment.setText(listaTexto[position]);
        tv_header.setText(listaForos[position]);
        tv_user.setText(listaUser[position]);
        layout.setBackgroundColor(Color.rgb(218,236,241));
        //Picasso.with(context).load(listaImg[position]).fit().centerInside().into(imagen);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(Color.rgb(255,255,255));
                Toast.makeText(context, "Hello"+user+"notif"+listaNotif[position], Toast.LENGTH_SHORT).show();

               final String QUERY_NOTIFICATIONS =
                       "http://www.webelicurso.hol.es/NotificatinUpdate.php?user="+user+"&notificacion="+listaNotif[position];

              // connectDB(QUERY_NOTIFICATIONS);
                Thread tr = new Thread() {
                    @Override
                    public void run() {


                        try{//QUERYS y CONEXIONES


                            updateNotification(QUERY_NOTIFICATIONS);
//                            Toast.makeText(context, "Hello"+user+"notif"+listaNotif[position], Toast.LENGTH_SHORT).show();
                        /*try{
                            //Runnable to UPDATE Notification to READ
                            context.runOnUiThread(new Runnable() {
                                public void run() {

                                    try {
                                        updateNotification(user, listaNotif[position]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();

                                }


                            });  */
                        } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                };
                //Instancia la activity
                tr.start();
                Intent intent = new Intent(context, ForoActivity.class);
                intent.putExtra("Titulo", listaForos[position]);
                context.startActivity(intent);


            }

        });
        //muestra la fila listview

        return rowView;
    }
    //METHODS TO CONNECT WITH BD

    public void updateNotification(String QUERY_NOTIFICATIONS)
            throws IOException{
        URL url=null;
        int respuesta;

        try {
            //int foro = Integer.parseInt(id_foro);

           // int notificacion = Integer.parseInt(notification);

            //String QUERY_NOTIFICATIONS =
              //      "http://www.webelicurso.hol.es/NotificatinUpdate.php?user="+user+"&notificacion="+notificacion;

          //  Toast.makeText(context, "Hello"+user+"notif"+notificacion, Toast.LENGTH_SHORT).show();

           // final String QUERY_NOTIFICATIONS =
             //       "http://www.webelicurso.hol.es/NotificatinUpdate.php?user="+ user;

            url=new URL(QUERY_NOTIFICATIONS);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
