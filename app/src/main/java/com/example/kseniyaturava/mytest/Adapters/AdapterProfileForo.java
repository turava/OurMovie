package com.example.kseniyaturava.mytest.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kseniyaturava.mytest.ForoActivity;
import com.example.kseniyaturava.mytest.R;
import com.squareup.picasso.Picasso;

/**
 * Created by kseniyaturava on 5/4/18.
 */

public class AdapterProfileForo extends ArrayAdapter<String> {
    private final Activity context;
    private final String [] listaImg;
    private final String [] listaTitulo;
    private final String [] numComents;
    private final String user;

    public AdapterProfileForo(Activity context, String[] listaImg, String[] listaTitulo, String[] numComents, String user) {
        super(context, R.layout.listview_profile_foro,listaImg);
        this.context=context;
        this.listaImg = listaImg;
        this.listaTitulo = listaTitulo;
        this.numComents = numComents;
        this.user = user;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_profile_foro, null,true);

        ImageView imagen = (ImageView) rowView.findViewById(R.id.imagen);
        TextView tv_title = (TextView) rowView.findViewById(R.id.tv_title);
        TextView numComments = (TextView) rowView.findViewById(R.id.numComments);
        LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.layout);

        //imagen.setImageDrawable(dir.getImage());
        numComments.setText(numComents[position]);
        tv_title.setText(listaTitulo[position]);
        Picasso.with(context).load(listaImg[position]).fit().centerInside().into(imagen);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForoActivity.class);
                intent.putExtra("Titulo", listaTitulo[position]);
                intent.putExtra("User", user);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
