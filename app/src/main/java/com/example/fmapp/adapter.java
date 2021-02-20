package com.example.fmapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.Viewholder> {
    ArrayList<examplecode> examplecode;
    onclickListener onclckk;

    public interface onclickListener {
        void onitemclick(int position);
    }

    void setOnclicklistener(onclickListener onclick) {
        onclckk = onclick;
    }

    public adapter(ArrayList<examplecode> arrayList) {
        examplecode = arrayList;
    }


    public static class Viewholder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView Experiance;
        public TextView howmuchexperiance;


        public Viewholder(@NonNull View itemView, final onclickListener onclicklistener) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            Experiance = itemView.findViewById(R.id.experiance);
            howmuchexperiance = itemView.findViewById(R.id.workedshows);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onclicklistener != null) {
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION) {
                            onclicklistener.onitemclick(postion);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview1, parent, false);
        Viewholder vh = new Viewholder(v, onclckk);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.Viewholder holder, int position) {
        examplecode currentposition = examplecode.get(position);

        holder.img.setImageResource(currentposition.getMimg());
        holder.name.setText(currentposition.getMname());
        holder.Experiance.setText(currentposition.getmExperiance());
        holder.howmuchexperiance.setText(currentposition.getmHowmuchex());

    }

    @Override
    public int getItemCount() {
        return examplecode.size();
    }
}
