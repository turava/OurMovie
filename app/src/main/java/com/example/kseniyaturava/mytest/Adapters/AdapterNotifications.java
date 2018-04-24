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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kseniyaturava on 8/4/18.
 */

public class AdapterNotifications extends ArrayAdapter<String> {
    private final Activity context;
    private final String [] listaTexto;
    private final String [] listaFechas;
    private final String [] listaForos;
    private final String user;
    private final String [] listaUser;
    private final String [] listaNotif;
    private final String [] listaLeido;

    public AdapterNotifications(Activity context, String[] listaTexto, String[] listaFechas, String[] listaForos,
                                String user, String[] listaUser, String[] listaNotif, String[] listaLeido) {
        super(context, R.layout.listview_alerts,listaTexto);
        this.context=context;
        this.listaTexto = listaTexto;
        this.listaFechas = listaFechas;
        this.listaForos = listaForos;
        this.user = user;
        this.listaUser = listaUser;
        this.listaNotif = listaNotif;
        this.listaLeido = listaLeido;
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

       //Write
        tv_date.setText(parseDate(listaFechas[position]));//parsed date from function
        tv_comment.setText(listaTexto[position]);
        tv_header.setText(listaForos[position]);
        tv_user.setText(listaUser[position]);
        Toast.makeText(context, "1"+listaLeido[position], Toast.LENGTH_SHORT).show();

        if(Integer.parseInt(listaLeido[position]) == 0)
        {
            layout.setBackgroundColor(Color.rgb(218,236,241));
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(Color.rgb(255,255,255));
               // Toast.makeText(context, "Hello"+user+"notif"+listaNotif[position], Toast.LENGTH_SHORT).show();

               final String QUERY_NOTIFICATIONS =
                       "http://www.webelicurso.hol.es/NotificatinUpdate.php?user="+user+"&notificacion="+listaNotif[position];
                Thread tr = new Thread() {
                    @Override
                    public void run() {
                        try{//QUERYS y CONEXIONES
                            updateNotification(QUERY_NOTIFICATIONS);
                        } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                };
                //Instancia la activity
                tr.start();
                Intent intent = new Intent(context, ForoActivity.class);
                intent.putExtra("Titulo", listaForos[position]);
                intent.putExtra("User", user);
                context.startActivity(intent);


            }

        });
        //muestra la fila listview

        return rowView;
    }
    //Parse Date and Time
    private String parseDate(String listaFecha) {
        //Calendar in spanish
        Locale locale = new Locale ( "es" , "ES" );
       // el que parsea
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat parseadorHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // el que formatea
        //SimpleDateFormat formateador = new SimpleDateFormat("EEEE MMM dd/MM/yy", locale);
        SimpleDateFormat formateador = new SimpleDateFormat("dd MMMM", locale);
        SimpleDateFormat formateadorHora = new SimpleDateFormat("HH:mm");

        Date date = null;
        try {
            date = parseador.parse(listaFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date hora = null;
        try {
            hora = parseadorHora.parse(listaFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formateador.format(date)+" a las "+formateadorHora.format(hora);
    }

    //METHODS TO CONNECT WITH BD

    public void updateNotification(String QUERY_NOTIFICATIONS)
            throws IOException{
        URL url=null;
        int respuesta;
        try {
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
