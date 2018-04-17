package com.example.kseniyaturava.mytest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ForoActivity extends AppCompatActivity {
    private TextView movieDescription;
    private String titulo2;

    TextView text_movie, text_director, text_year, tvNumAnswers, text_numberAnswers1, tvComent, text_reply,
            text_reply2, text_comment4, tvDate, text_dateReply, tvUserName;
    ImageButton button_info, acordeon, acordeonFiles, acordeonFilesPost, button_send, btReply;
    AutoCompleteTextView input_reply, input_message;
    ImageView imgUser, imgMovie, iconoComents;
    ListView lvForo;
    ArrayList nombreUser=new ArrayList();
    ArrayList fechaComent=new ArrayList();
    ArrayList comentForo=new ArrayList();
    ArrayList numAnswers=new ArrayList();
    URL url=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);
        setTitle("Foro");

        //text_numberAnswers1 = (TextView) findViewById(R.id.text_numberAnswers1);
        //text_reply = (TextView) findViewById(R.id.text_reply);
        //text_reply2 = (TextView) findViewById(R.id.text_reply2);
        //text_comment4 = (TextView) findViewById(R.id.text_comment4);
        //text_dateReply = (TextView) findViewById(R.id.text_dateReply);
        input_reply = (AutoCompleteTextView) findViewById(R.id.input_reply);
        input_message = (AutoCompleteTextView) findViewById(R.id.input_message);
        text_movie =(TextView) findViewById(R.id.text_movie);
        text_director =(TextView) findViewById(R.id.text_director);
        text_year =(TextView) findViewById(R.id.text_year);
        button_info = (ImageButton) findViewById(R.id.button_info);
        button_send = (ImageButton) findViewById(R.id.button_send);
        lvForo=(ListView)findViewById(R.id.listview_coments);
        imgMovie=(ImageView) findViewById(R.id.img_movie);

        Bundle bundle=this.getIntent().getExtras();
        if ((bundle!=null)&&(bundle.getString("Titulo")!=null)){
                //continua del If: &&(bundle.getString("User")!=null)){
            titulo2=bundle.getString("Titulo");
            //String user=bundle.getString("User");
            text_movie.setText(titulo2);
            //tvUserName.setText(user);
        }

        recogerDatosForo();

        //button_send.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //String valor=String.valueOf(tvNumAnswers.getText());
                //int num=Integer.parseInt(valor);
                //int numFinal=num+1;
                //String valorFinal=String.valueOf(numFinal);
                //tvNumAnswers.setText(valorFinal);
                //text_numberAnswers1.setText(valorFinal);
                //Los 4 campos siguientes deben recogerse de la base, pero de momento los asigno al escribir. Faltará cambiar
                //tvDate.setText(getDate());
                //tvComent.setText(input_message.getText());
                //text_username1.setText(user);
                //img_user1.setImage(user);
                //Thread tr=new Thread(){
                    //@Override
                    //public void run() {
                        //try {
                            //sumarComent(tvNumAnswers.getText().toString(), text_movie.getText().toString());
                            //guardarComent(input_message.getText().toString(), tvDate.getText().toString());
                            //runOnUiThread(new Runnable() {
                                //@Override
                                //public void run() {
                                    //Toast.makeText(ForoActivity.this, "Comentario guardado satisfactoriamente", Toast.LENGTH_LONG).show();
                                //}
                            //});
                        //} catch (IOException e) {
                            //e.printStackTrace();
                        //}
                    //}
                //};
                //tr.start();
            //}
        //});

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo=text_movie.getText().toString();
                String user=tvUserName.getText().toString();
                Intent intent = new Intent(ForoActivity.this, MovieActivity.class);
                intent.putExtra("Titulo", titulo);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

    }

    private void recogerDatosForo(){
        nombreUser.clear();
        comentForo.clear();
        fechaComent.clear();
        numAnswers.clear();

        final ProgressDialog progressDialog=new ProgressDialog(ForoActivity.this);
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();

        Thread tr=new Thread(){
            @Override
            public void run() {
                try {
                    final String res = recogerDatosComents(titulo2);
                    final String res2 = recogerDatosPelis(titulo2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int r = objJSON(res2);
                            if (r > 0) {
                                progressDialog.dismiss();
                                int inicio=0;
                                int longitud;
                                String palabra;
                                for (int i=0;i<res2.length();i++) {
                                    if ((res2.charAt(i)==(',')&& res2.charAt(i+1)==('"')) || (res2.charAt(i)==('}')&& res2.charAt(i+1)==(']'))) {
                                        longitud = i;
                                        palabra = res2.substring(inicio, longitud);
                                        inicio = longitud + 1;
                                        if (palabra.contains("Anyo_Film")) {
                                            String anyo = palabra.substring(13, palabra.length() - 1);
                                            text_year.setText(anyo);
                                        } else if (palabra.contains("Director_Film")) {
                                            String director = palabra.substring(17, palabra.length() - 1);
                                            text_director.setText(director);
                                        } else if (palabra.contains("Imagen")) {
                                            String imagen = palabra.substring(10, palabra.length() - 1);
                                            try{
                                                url = new URL(imagen);
                                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                conn.connect();
                                                Picasso.with(ForoActivity.this).load(String.valueOf(url)).into(imgMovie);
                                            } catch (IOException e) {
                                                Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+e.getMessage(),Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(new String(res));
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        nombreUser.add(jsonArray.getJSONObject(i).getString("User"));
                                        comentForo.add(jsonArray.getJSONObject(i).getString("Texto"));
                                        fechaComent.add(jsonArray.getJSONObject(i).getString("Fecha"));
                                        numAnswers.add(jsonArray.getJSONObject(i).getString("numComents"));
                                    }
                                    lvForo.setAdapter(new AdapterForo(getApplicationContext()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else{
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

    private class AdapterForo extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;

        public AdapterForo(Context applicationContext) {
            this.context = applicationContext;
            layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return comentForo.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.listview_coments, null);
            tvUserName = (TextView) viewGroup.findViewById(R.id.tvUserName);
            tvDate = (TextView) viewGroup.findViewById(R.id.tvDate);
            tvComent = (TextView) viewGroup.findViewById(R.id.tvComent);
            tvNumAnswers= (TextView) viewGroup.findViewById(R.id.tvNumAnswers);
            btReply = (ImageButton) viewGroup.findViewById(R.id.btReply);
            iconoComents=(ImageView) viewGroup.findViewById(R.id.iconoComents);
            imgUser = (ImageView) viewGroup.findViewById(R.id.imgUser);

            AnimationDrawable imguser=(AnimationDrawable) imgUser.getDrawable();
            imguser.start();
            imgUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user=tvUserName.getText().toString();
                    Intent intent = new Intent(ForoActivity.this, ProfileActivity.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
            });

            btReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String valor=String.valueOf(tvNumAnswers.getText());
                    int num=Integer.parseInt(valor);
                    int numFinal=num+1;
                    String valorFinal=String.valueOf(numFinal);
                    tvNumAnswers.setText(valorFinal);
                    //text_numberAnswers1.setText(valorFinal);
                    //text_dateReply.setText(getDate());
                    //text_reply.setText(input_reply.getText());
                    Thread tr2=new Thread(){
                        @Override
                        public void run() {
                            try {
                                sumarComent(tvNumAnswers.getText().toString(), text_movie.getText().toString());
                                //guardarComent(input_reply.getText().toString(), text_dateReply.getText().toString());
                                createNotification(tvUserName.getText().toString().trim(),text_movie.getText().toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ForoActivity.this, "Comentario guardado satisfactoriamente", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    tr2.start();

                }
            });

            tvUserName.setText(nombreUser.get(position).toString());
            tvDate.setText(fechaComent.get(position).toString());
            tvComent.setText(comentForo.get(position).toString());
            tvNumAnswers.setText(numAnswers.get(position).toString());

            return viewGroup;
        }
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
    //After the coment was uploaded to bd, insert notification to users from the same foro
    public void createNotification(String user, String titulo)
    {
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/ForoNotif.php?user="+user+"titulo="+titulo);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //el valor de numComents que coje es de la columna Id_Subcomentario de la tabla comentarios, ya que son los
    //subcomentarios escritos sobre un comentario, no el total de comentarios escritos del foro
    public String recogerDatosComents (String titulo) throws IOException {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;

        try {
            url=new URL("http://www.webelicurso.hol.es/ForoComents.php?Titulo_Film="+titulo);
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

    public String recogerDatosPelis (String titulo) throws IOException {
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

    //suma 1 al campo Num_Coments de la tabla peliculas cada vez que alguien añade un comentario al foro
    public void sumarComent(String comentario, String titulo)  throws IOException{
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/ComentUpdate2.php?Num_Coments="+comentario+"&Titulo_Film="+titulo);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarComent(String mensaje, String fecha)  throws IOException{
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/MessageInsert.php?Texto="+mensaje+"&Fecha="+fecha);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDate(){
        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(dt);
        return date;
    }
    //Override back button android to do something
    /*@Override
    public void onBackPressed()
    {
        Intent intent = new Intent(ForoActivity.this, MovieActivity.class);
        intent.putExtra("Titulo", text_movie.getText().toString());
        intent.putExtra("User", user);
        startActivity(intent);
        super.onBackPressed();
    }*/
}
