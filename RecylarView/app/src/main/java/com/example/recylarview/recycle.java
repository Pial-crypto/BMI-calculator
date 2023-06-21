package com.example.recylarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class recycle extends RecyclerView.Adapter<recycle.ViewHolder> {
Context coni;
recycle(Context conf){
    coni=conf;
}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View v=   LayoutInflater.from(coni).inflate(R.layout.contact,parent,false);
ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.img.setImageResource(adapting.getimage);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class  ViewHolder extends  RecyclerView.ViewHolder{
ImageView img;
TextView msg;
TextView ans;
      public ViewHolder(View item){
            super(item);
            img=item.findViewById(R.id.img);
            ans=item.findViewById(R.id.ans);
            msg=item.findViewById(R.id.msg);

        }


    }

}