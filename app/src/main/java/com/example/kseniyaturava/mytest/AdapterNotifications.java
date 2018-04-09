package com.example.kseniyaturava.mytest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
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
    private final String [] listaCom;
    private final String [] listaSubc;
    private final String [] listaIdForo;

    public AdapterNotifications(Activity context, String[] listaTexto, String[] listaFechas, String[] listaForos,
                                String user, String[] listaCom, String[] listaSubc, String[] listaIdForo) {
        super(context, R.layout.listview_alerts,listaTexto);
        this.context=context;
        this.listaTexto = listaTexto;
        this.listaFechas = listaFechas;
        this.listaForos = listaForos;
        this.user = user;
        this.listaCom = listaCom;
        this.listaSubc = listaSubc;
        this.listaIdForo = listaIdForo;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_alerts, null,true);

        //ImageView imagen = (ImageView) rowView.findViewById(R.id.imagen);
        TextView tv_comment = (TextView) rowView.findViewById(R.id.tv_comment);
        TextView tv_date = (TextView) rowView.findViewById(R.id.tv_date);
        TextView tv_header = (TextView) rowView.findViewById(R.id.tv_header);
         final LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.layout);
        //imagen.setImageDrawable(dir.getImage());
        tv_date.setText(listaFechas[position]);
        tv_comment.setText(listaTexto[position]);
        tv_header.setText(listaForos[position]);
        layout.setBackgroundColor(Color.rgb(218,236,241));
        //Picasso.with(context).load(listaImg[position]).fit().centerInside().into(imagen);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setBackgroundColor(Color.rgb(255,255,255));

                Thread tr = new Thread() {
                    @Override
                    public void run() {


                        try{//QUERYS y CONEXIONES
                        if(listaSubc[position].equalsIgnoreCase("0")) {
//                            Toast.makeText(context, "n"+listaSubc[position], Toast.LENGTH_SHORT).show();
                            try {
                                updateNotification(user, listaCom[position], listaSubc[position], listaIdForo[position]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{}


                        /*try{
                            //Runnable to UPDATE Notification to READ
                            context.runOnUiThread(new Runnable() {
                                public void run() {


                                    Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();

                                }


                            });*/
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

    public void updateNotification(String user, String id_coment, String id_subcom, String id_foro)
            throws IOException{
        URL url=null;
        int respuesta;

        try {
            int foro = Integer.parseInt(id_foro);

            int coment = Integer.parseInt(id_coment);

            String QUERY_NOTIFICATIONS =
                    "http://www.webelicurso.hol.es/NotificatinUpdate.php?foro="+foro+"&coment="+coment;


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

}
