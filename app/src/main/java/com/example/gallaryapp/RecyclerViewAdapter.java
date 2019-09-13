package com.example.gallaryapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Cell> gallaryList;
    private Context context;

    public RecyclerViewAdapter(Context context , ArrayList<Cell> gallaryList){

        this.context = context;
        this.gallaryList = gallaryList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell,parent,false);


        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {



        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        setImageFromPath(gallaryList.get(position).getPath(), holder.img);
        Picasso.with(context).load(new File(gallaryList.get(position).getPath())).into(holder.img);
//        Picasso.with(context).load(setImageFromPath(gallaryList.get(position).getPath())).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FullScreenImageView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ImagePath" , gallaryList.get(position).getPath());
                Log.d("EXTRA", "onClick: "+intent.putExtra("ImagePath" , gallaryList.get(position).getPath()));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image_view);

        }
    }

    private void setImageFromPath(String path , ImageView image){

        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap bitmap = ImageHelper.decodeSampledBitMapFromPath(imgFile.getAbsolutePath(),200,200);
//            image.setImageBitmap(bitmap);


        }
    }

}
