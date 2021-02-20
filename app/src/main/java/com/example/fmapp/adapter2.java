package com.example.fmapp;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class adapter2 extends RecyclerView.Adapter<adapter2.exampleViewholder> {
    ArrayList<upload2> arrayList;
    adapter.onclickListener onClickListener;

    adapter2(ArrayList<upload2> arrayList) {
        this.arrayList = arrayList;
    }

    public class exampleViewholder extends adapter.Viewholder {
        ImageView img;
        TextView textt;

        public exampleViewholder(@NonNull View itemView, adapter.onclickListener onclicklistener) {
            super(itemView, onclicklistener);
            img = itemView.findViewById(R.id.imgview_card);
            textt = itemView.findViewById(R.id.textt);
        }
    }

    @NonNull
    @Override
    public exampleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2, null, false);
        return new exampleViewholder(v, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final exampleViewholder holder, int position) {
        upload2 currentposition = arrayList.get(position);
        Picasso.get().load(currentposition.getImg()).placeholder(R.drawable.loadinggif).fit().into(holder.img);
        holder.textt.setText(" ");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
