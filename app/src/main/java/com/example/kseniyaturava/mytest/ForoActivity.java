package com.example.kseniyaturava.mytest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForoActivity extends AppCompatActivity {
    private TextView movieDescription;
    private String titulo2, user2, idForo, idUser, res;

    TextView text_movie, text_director, text_year, tvNumAnswers, tvComent, tvDate, tvUserName;
    ImageButton button_info, btSend, btReply, btSendReply;
    AutoCompleteTextView input_message, input_messageReply;
    ImageView imgUser, imgMovie, iconoComents;
    ListView lvForo;
    ArrayList nombreUser=new ArrayList();
    ArrayList fechaComent=new ArrayList();
    ArrayList comentForo=new ArrayList();
    URL url=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);
        setTitle("Foro");

        // Customize action bar title to center and other styles
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_foro);

        //  Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back36);

        input_message = (AutoCompleteTextView) findViewById(R.id.input_message);
        text_movie =(TextView) findViewById(R.id.text_movie);
        text_director =(TextView) findViewById(R.id.text_director);
        text_year =(TextView) findViewById(R.id.text_year);
        button_info = (ImageButton) findViewById(R.id.button_info);
        btSend = (ImageButton) findViewById(R.id.btSend);
        lvForo=(ListView)findViewById(R.id.listview_coments);
        imgMovie=(ImageView) findViewById(R.id.img_movie);
        iconoComents=(ImageView) findViewById(R.id.iconoComents);
        tvNumAnswers= (TextView) findViewById(R.id.tvNumAnswers);

        Bundle bundle=this.getIntent().getExtras();
        if ((bundle!=null)&&(bundle.getString("Titulo")!=null) && (bundle.getString("User")!=null)){
            titulo2=bundle.getString("Titulo");
            user2=bundle.getString("User");
            text_movie.setText(titulo2);
            recogerDatosForo();
        } else{
            Toast.makeText(ForoActivity.this, "Ha ocurrido algún error con la peli o el user", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ForoActivity.this, MainActivity.class);
            startActivity(intent);
        }

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_message.getText().toString().equals("")){
                    Toast.makeText(ForoActivity.this, "El campo de texto no puede estar vacío", Toast.LENGTH_LONG).show();
                }else{
                    String valor=String.valueOf(tvNumAnswers.getText());
                    int num=Integer.parseInt(valor);
                    int numFinal=num+1;
                    String valorFinal=String.valueOf(numFinal);
                    tvNumAnswers.setText(valorFinal);

                    tvDate.setText(getDate());
                    tvUserName.setText(user2);
                    tvComent.setText(input_message.getText().toString());

                    //Si añadimos un comentario por primera vez en un foro, lo pondremos en la primera posición y el mensaje
                    //predeterminado que había, lo borramos
                    if(nombreUser.size()==1 && nombreUser.get(0)=="OurMovie"){
                        fechaComent.add(0,tvDate.getText());
                        comentForo.add(0,tvComent.getText());
                        nombreUser.add(0,tvUserName.getText());
                        fechaComent.remove(1);
                        comentForo.remove(1);
                        nombreUser.remove(1);
                    } else {
                        fechaComent.add(tvDate.getText());
                        comentForo.add(tvComent.getText());
                        nombreUser.add(tvUserName.getText());
                    }

                    Thread tr=new Thread(){
                        @Override
                        public void run() {
                            try {
                                sumarComent(tvNumAnswers.getText().toString(), text_movie.getText().toString());
                                final String res3 = guardarComent(text_movie.getText().toString(), user2);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int r = objJSON(res3);
                                        if (r > 0) {
                                            int inicio=0;
                                            int longitud;
                                            String palabra;
                                            for (int i=0;i<res3.length();i++) {
                                                if ((res3.charAt(i) == (',') && res3.charAt(i + 1) == ('"')) || (res3.charAt(i) == ('}') && res3.charAt(i + 1) == (']'))) {
                                                    longitud = i;
                                                    palabra = res3.substring(inicio, longitud);
                                                    inicio = longitud + 1;
                                                    if (palabra.contains("Id_Foro")) {
                                                        idForo = palabra.substring(13, palabra.length() - 1);
                                                    } else if (palabra.contains("Id_User")) {
                                                        idUser = palabra.substring(11, palabra.length() - 1);
                                                    }
                                                }
                                            }
                                            Thread tr5=new Thread(){
                                                @Override
                                                public void run() {
                                                    try {
                                                        guardarComent2(idForo.toString(), idUser.toString(), input_message.getText().toString(), tvDate.getText().toString());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            tr5.start();
                                            //Notifications
                                            Thread tr6=new Thread(){
                                                @Override
                                                public void run() {
                                                    try {
                                                        createNotification(tvUserName.getText().toString(),text_movie.getText().toString());

                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            tr6.start();
                                        }

                                        Toast.makeText(ForoActivity.this, "Comentario guardado satisfactoriamente", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    tr.start();
                }
            }
        });

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

        final ProgressDialog progressDialog=new ProgressDialog(ForoActivity.this);
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();

        Thread tr2=new Thread(){
            @Override
            public void run() {
                try {
                    res = recogerDatosComents(titulo2);
                    //Si aún no hay comentarios en el foro de la pelicula, da error al intentar añadir el primero en
                    // los ArrayLists, por lo que es necesario inicializarlos aunque sea con un registro de datos vacíos
                    if (res.equals("[]")){
                        String res3="[{'User':'','Fecha':'','Texto':''}]";
                        res=res3;
                    }
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
                                        } else if (palabra.contains("Num_Coments")) {
                                            String numcoments = palabra.substring(15, palabra.length() - 1);
                                            tvNumAnswers.setText(numcoments);
                                        }
                                    }
                                }
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(new String(res));
                                    //si solo hay 1 comentario y es el de los datos vacíos, le asignamos unos valores
                                    //predeterminados que no se guardan en la bbdd. En el momento que se escriba
                                    //un comentario real y se envíe, dejará de aparecer el predeterminado y
                                    // solo saldrán los reales
                                    if (res.equals("[{'User':'','Fecha':'','Texto':''}]")){
                                        nombreUser.add(0,"OurMovie");
                                        comentForo.add(0,"Aún no hay ningún comentario en este foro, no esperes más y escribe algo!!");
                                        fechaComent.add(0,"Ahora");
                                    }else {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            nombreUser.add(jsonArray.getJSONObject(i).getString("User"));
                                            comentForo.add(jsonArray.getJSONObject(i).getString("Texto"));
                                            fechaComent.add(jsonArray.getJSONObject(i).getString("Fecha"));
                                        }
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
        tr2.start();
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
            btReply = (ImageButton) viewGroup.findViewById(R.id.btReply);
            imgUser = (ImageView) viewGroup.findViewById(R.id.imgUser);
            input_messageReply = (AutoCompleteTextView) findViewById(R.id.input_messageReply);
            btSendReply = (ImageButton) viewGroup.findViewById(R.id.btSendReply);
            final LinearLayout acordeon= (LinearLayout) viewGroup.findViewById(R.id.layout_acordeon);
            //final String dateReply=getDate();

            tvUserName.setText(nombreUser.get(position).toString());
            tvDate.setText(fechaComent.get(position).toString());
            tvComent.setText(comentForo.get(position).toString());

            AnimationDrawable imguser=(AnimationDrawable) imgUser.getDrawable();
            imguser.start();

            btReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acordeon.setVisibility(View.VISIBLE);
                }
            });

            btSendReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(input_messageReply.getText().toString().equals("")){
                        Toast.makeText(ForoActivity.this, "El campo de texto no puede estar vacío", Toast.LENGTH_LONG).show();
                    }else{
                        String valor=String.valueOf(tvNumAnswers.getText());
                        int num=Integer.parseInt(valor);
                        int numFinal=num+1;
                        String valorFinal=String.valueOf(numFinal);
                        tvNumAnswers.setText(valorFinal);

                        tvDate.setText(getDate());
                        tvUserName.setText(user2);
                        tvComent.setText(input_messageReply.getText());

                        fechaComent.add(tvDate.getText());
                        comentForo.add(tvComent.getText());
                        nombreUser.add(tvUserName.getText());

                        Thread tr3=new Thread(){
                            @Override
                            public void run() {
                                try {
                                    sumarComent(tvNumAnswers.getText().toString(), text_movie.getText().toString());
                                    final String res4 = guardarComent(text_movie.getText().toString(), user2);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            int r = objJSON(res4);
                                            if (r > 0) {
                                                int inicio=0;
                                                int longitud;
                                                String palabra;
                                                for (int i=0;i<res4.length();i++) {
                                                    if ((res4.charAt(i) == (',') && res4.charAt(i + 1) == ('"')) || (res4.charAt(i) == ('}') && res4.charAt(i + 1) == (']'))) {
                                                        longitud = i;
                                                        palabra = res4.substring(inicio, longitud);
                                                        inicio = longitud + 1;
                                                        if (palabra.contains("Id_Foro")) {
                                                            idForo = palabra.substring(13, palabra.length() - 1);
                                                        } else if (palabra.contains("Id_User")) {
                                                            idUser = palabra.substring(11, palabra.length() - 1);
                                                        }
                                                    }
                                                }
                                                Thread tr7=new Thread(){
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            guardarComent2(idForo.toString(), idUser.toString(), input_messageReply.getText().toString(), tvDate.getText().toString());
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                tr7.start();
                                                //Notifications
                                                Thread tr6=new Thread(){
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            createNotification(tvUserName.getText().toString(),text_movie.getText().toString());
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(ForoActivity.this, "Notif"+tvUserName.getText().toString()+text_movie.getText().toString(), Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                tr6.start();
                                            }
                                            Toast.makeText(ForoActivity.this, "Comentario guardado satisfactoriamente", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        tr3.start();
                    }

                }
            });
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
            url=new URL("http://www.webelicurso.hol.es/ForoNotif.php?user="+user+"&titulo="+titulo);
            HttpURLConnection conection=(HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();
            if (respuesta==HttpURLConnection.HTTP_OK){
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    //
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

    //primera parte para guardar los coments en la tabla comentarios. Aquí usamos título y user para conseguir id_foro y id_user
    public String guardarComent(String titulo, String user)  throws IOException{
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null;

        try {
            url=new URL("http://www.webelicurso.hol.es/MessageInsert.php?Titulo_Film="+titulo+"&User="+user);
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
    //segunda parte para guardar los coments en la tabla comentarios. Aquí usamos id_foro y id_user conseguidos antes, más el
    //comentario y fecha para guardar toda la info en la tabla comentarios
    public void guardarComent2(String idForo, String idUser, String comentario, String fecha) throws IOException{
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/MessageInsert2.php?Id_Foro="+idForo+"&Id_User="+idUser+"&Texto="+comentario+"&Fecha="+fecha);
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
        Locale locale = new Locale ( "es" , "ES" );
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM", locale);
        //SimpleDateFormat dh = new SimpleDateFormat("HH:mm");
        Calendar dh = Calendar.getInstance();
        String date = df.format(dt);
        int hour = dh.get(Calendar.HOUR_OF_DAY);
        int min = dh.get(Calendar.MINUTE);
        return date + " a las " + (hour+2) + ":" + min;
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

    //Button back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
