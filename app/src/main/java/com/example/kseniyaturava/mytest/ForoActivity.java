package com.example.kseniyaturava.mytest;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
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

    TextView text_movie, text_director, text_year, text_numberAnswers, text_numberAnswers1, text_comment1, text_reply,
            text_reply2, text_comment4, text_date1, text_dateReply, text_username1;
    ImageButton button_info, acordeon, acordeonFiles, acordeonFilesPost, button_send, button_sendReply;
    AutoCompleteTextView input_reply, input_message;
    ImageView img_user1;
    private String user;
    ListView lvForo;
    ArrayList nombreUser=new ArrayList();
    ArrayList fechaComent=new ArrayList();
    ArrayList comentForo=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);
        setTitle("Foro");

        text_numberAnswers = (TextView) findViewById(R.id.text_numberAnswers);
        //text_numberAnswers1 = (TextView) findViewById(R.id.text_numberAnswers1);
        text_comment1 = (TextView) findViewById(R.id.text_comment1);
        //text_reply = (TextView) findViewById(R.id.text_reply);
        //text_reply2 = (TextView) findViewById(R.id.text_reply2);
        text_comment4 = (TextView) findViewById(R.id.text_comment4);
        text_date1 = (TextView) findViewById(R.id.text_date1);
        //text_dateReply = (TextView) findViewById(R.id.text_dateReply);
        text_username1 = (TextView) findViewById(R.id.text_username1);
        input_reply = (AutoCompleteTextView) findViewById(R.id.input_reply);
        input_message = (AutoCompleteTextView) findViewById(R.id.input_message);
        text_movie =(TextView) findViewById(R.id.text_movie);
        text_director =(TextView) findViewById(R.id.text_director);
        text_year =(TextView) findViewById(R.id.text_year);
        button_info = (ImageButton) findViewById(R.id.button_info);
        button_send = (ImageButton) findViewById(R.id.button_send);
        button_sendReply = (ImageButton) findViewById(R.id.button_sendReply);
        lvForo=(ListView)findViewById(R.id.listview_coments);

        user = getIntent().getExtras().getString("User");
        Bundle bundle=this.getIntent().getExtras();
        if ((bundle!=null)&&(bundle.getString("Titulo")!=null)){
            String titulo=bundle.getString("Titulo");
            text_movie.setText(titulo);
            text_username1.setText(user);
        }

        recogerDatosForo();

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor=String.valueOf(text_numberAnswers.getText());
                int num=Integer.parseInt(valor);
                int numFinal=num+1;
                String valorFinal=String.valueOf(numFinal);
                text_numberAnswers.setText(valorFinal);
                text_numberAnswers1.setText(valorFinal);
                //Los 4 campos siguientes deben recogerse de la base, pero de momento los asigno al escribir. Faltará cambiar
                text_date1.setText(getDate());
                text_comment1.setText(input_message.getText());
                //text_username1.setText(user);
                //img_user1.setImage(user);
                Thread tr=new Thread(){
                    @Override
                    public void run() {
                        try {
                            sumarComent(text_numberAnswers.getText().toString(), text_movie.getText().toString());
                            guardarComent(input_message.getText().toString(), text_date1.getText().toString());
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
                tr.start();
            }
        });

        button_sendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor=String.valueOf(text_numberAnswers.getText());
                int num=Integer.parseInt(valor);
                int numFinal=num+1;
                String valorFinal=String.valueOf(numFinal);
                text_numberAnswers.setText(valorFinal);
                text_numberAnswers1.setText(valorFinal);
                text_dateReply.setText(getDate());
                text_reply.setText(input_reply.getText());
                Thread tr=new Thread(){
                    @Override
                    public void run() {
                        try {
                            sumarComent(text_numberAnswers.getText().toString(), text_movie.getText().toString());
                            guardarComent(input_reply.getText().toString(), text_dateReply.getText().toString());
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
                tr.start();
            }
        });

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo=text_movie.getText().toString();
                Intent intent = new Intent(ForoActivity.this, MovieActivity.class);
                intent.putExtra("Titulo", titulo);
                intent.putExtra("User", user);
                startActivity(intent);
            }
        });

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
                                        } else if (palabra.contains("Num_Coments")) {
                                            String num_com = palabra.substring(15, palabra.length() - 1);
                                            text_numberAnswers.setText(num_com);
                                            text_numberAnswers1.setText(num_com);
                                        } else if (palabra.contains("Imagen")) {
                                            String imagen = palabra.substring(10, palabra.length() - 1);
                                            //Falta código aquí, para que muestre la imagen de la pelicula en el lado superior izq.
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

    private void recogerDatosForo(){
        nombreUser.clear();
        comentForo.clear();
        fechaComent.clear();

        final ProgressDialog progressDialog=new ProgressDialog(ForoActivity.this);
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();

        Thread tr=new Thread(){
            @Override
            public void run() {
                try {
                    final String res = recogerDatos("Matrix");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int r = objJSON(res);
                            if (r > 0) {
                                progressDialog.dismiss();
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(new String(res));
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        nombreUser.add(jsonArray.getJSONObject(i).getString("User"));
                                        comentForo.add(jsonArray.getJSONObject(i).getString("Texto"));
                                        fechaComent.add(jsonArray.getJSONObject(i).getString("Fecha"));
                                    }
                                    lvForo.setAdapter(new AdapterForo(getApplicationContext()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
            text_username1 = (TextView) viewGroup.findViewById(R.id.text_username1);
            text_date1 = (TextView) viewGroup.findViewById(R.id.text_date1);
            text_comment1 = (TextView) viewGroup.findViewById(R.id.text_comment1);

            text_username1.setText(nombreUser.get(position).toString());
            text_date1.setText(fechaComent.get(position).toString());
            text_comment1.setText(comentForo.get(position).toString());

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

    public String recogerDatos (String titulo) throws IOException {
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

    public void sumarComent(String comentario, String titulo)  throws IOException{
        URL url=null;
        int respuesta;

        try {
            url=new URL("http://www.webelicurso.hol.es/ComentUpdate.php?Num_Coments="+comentario+"&Titulo_Film="+titulo);
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
