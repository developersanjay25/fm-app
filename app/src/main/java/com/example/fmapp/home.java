package com.example.fmapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class home extends Fragment {
    //    DatabaseReference firebaseDatabase;
    TextView text;
    ImageView img;
    ProgressBar prog;
    Bitmap notifyimg;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

       prog = v.findViewById(R.id.prog);
        img = v.findViewById(R.id.homeimg);
        text = v.findViewById(R.id.show_name);
        firebase();
        return v;
    }

    public void firebase() {
        FirebaseDatabase.getInstance().getReference("show_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                text.setText(name);
                 }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase.getInstance().getReference("home").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url = snapshot.getValue(String.class);

                notifyimg = BitmapFactory.decodeFile(url);
                Picasso.get().load(url).fit().into(img);
                prog.setVisibility(View.INVISIBLE);

//                Toast.makeText(getContext(), ""+uplo, Toast.LENGTH_LONG).show();
                       }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

            }
        });}
}
