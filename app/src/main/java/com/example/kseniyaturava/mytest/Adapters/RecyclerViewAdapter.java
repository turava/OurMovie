package com.example.kseniyaturava.mytest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kseniyaturava.mytest.MovieActivity;
import com.example.kseniyaturava.mytest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kseniyaturava on 3/4/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    private String user = "";

    public RecyclerViewAdapter(Context context,String user, ArrayList<String> names, ArrayList<String> imageUrls) {
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
        this.user = user;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        /* Da errores de compilación en el gradle dependencies
        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);
                */
        Picasso.with(mContext).load(mImageUrls.get(position)).into(holder.image);

       // holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));

                Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra("Titulo", mNames.get(position));
                intent.putExtra("User", user);
                mContext.startActivity(intent);
               // Toast.makeText(mContext,user+"pruebas", Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
       // TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
           // name = itemView.findViewById(R.id.name);
        }
    }
}
