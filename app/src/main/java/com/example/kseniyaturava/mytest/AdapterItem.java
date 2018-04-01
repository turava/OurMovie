package com.example.kseniyaturava.mytest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by kseniyaturava on 31/3/18.
 */

public class AdapterItem  extends ArrayAdapter<String> {
    private final Activity context;
    private final String [] listaImg;

    public AdapterItem (Activity context, String [] listaImg) {
        super(context, R.layout.listview_category,listaImg);
        this.context=context;
        this.listaImg = listaImg;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_category, null,true);

        ImageView imagen = (ImageView) rowView.findViewById(R.id.imagen);
        //imagen.setImageDrawable(dir.getImage());
        //imagen.setImageResource(listaImg[position]);
        Picasso.with(context).load(listaImg[position]).into(imagen);

        return rowView;
    }
}
