package com.example.fmapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.io.IOException;
import java.util.ArrayList;

public class home extends Fragment {
    TextView text;
    ImageView img;
    ProgressBar prog;
    Bitmap notifyimg;
    Button player;
    Boolean butn = true;
    String linkk;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        prog = v.findViewById(R.id.prog);
        img = v.findViewById(R.id.homeimg);
        text = v.findViewById(R.id.show_name);
        player = v.findViewById(R.id.player);

        playsong();
        firebase();

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = new MediaPlayer();
                if(butn)
                {
                    player.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    butn = false;
                    if (linkk != null) {
                        try {
                            mp.setDataSource(linkk);
                            mp.prepare();
                        }
                        catch (IOException ex)
                        {
                            Toast.makeText(getContext(), "not Ready", Toast.LENGTH_SHORT).show();
                        }
                    }
                    mp.start();
                }
                else
                {
                    player.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    butn = true;
                    mp.pause();
                    mp.stop();
                }
            }
        });
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
                       }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

            }
        });}
        public void playsong()
        {
                FirebaseDatabase.getInstance().getReference("playsongs").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                              linkk =  snapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }
}