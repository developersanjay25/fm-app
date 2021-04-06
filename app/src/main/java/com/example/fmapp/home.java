package com.example.fmapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class home extends Fragment {
    TextView text;
    ImageView img;
    ProgressBar prog;
    Bitmap notifyimg;
    static Button player;
    public String linkk;
    static MediaPlayer mp;
    static MainActivity mainActivity;
    int position;
    long time;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        final ArrayList<uploadsong> mmmarraylist = new ArrayList<>();
        prog = v.findViewById(R.id.prog);
        img = v.findViewById(R.id.homeimg);
        text = v.findViewById(R.id.show_name);
        player = v.findViewById(R.id.player);
        mp = new MediaPlayer();
        mainActivity = new MainActivity();

        FirebaseDatabase.getInstance().getReference("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long tim = snapshot.getValue(Long.class);
                time = System.currentTimeMillis() - tim;
                playsong(time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        DatabaseReference playlist = FirebaseDatabase.getInstance().getReference("playsongs");
        playlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                mmmarraylist.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    uploadsong li = snapshot1.getValue(uploadsong.class);
                    if (li == null)
                    {
                        Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();
                        System.out.println("null");
                    }
                    else
                    {
                        mmmarraylist.add(li);
                        System.out.println("li is not null");
                    }
                }

                System.out.println("your arraylist is : " + mmmarraylist.size());
                Toast.makeText(getContext(), "Arraylist size  is : " + mmmarraylist.size(), Toast.LENGTH_SHORT).show();

                FirebaseDatabase.getInstance().getReference("position").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        position = snapshot.getValue(Integer.class);
                        System.out.println(position);
                        Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                FirebaseDatabase.getInstance().getReference("position").setValue(position+1);
                                }
                        });
                        FirebaseDatabase.getInstance().getReference("playcurrentsong").setValue(mmmarraylist.get(position));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        playsong(0000000);
        firebase();
        final MainActivity mainActivity = new MainActivity();
        mainActivity.storingmediaplayer(mp);


        System.out.println("your arraylist is : " + mmmarraylist.size());
        Toast.makeText(getContext(), "Arraylist size  is : " + mmmarraylist.size(), Toast.LENGTH_SHORT).show();

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()) {
                    player.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
//                    MainActivity.pauseplaynoti = R.drawable.ic_baseline_play_arrow_24;
                    mp.pause();
                    mainActivity.changebuttoniconn();
                    MainActivity.notificationn.build();
                } else {
                    player.setBackgroundResource(R.drawable.ic_baseline_pause_24);
//                    MainActivity.pauseplaynoti = R.drawable.ic_baseline_pause_24;
                    mp.start();
                    mainActivity.changebuttoniconn();
                    MainActivity.notificationn.build();
                }
            }
        });


        return v;
    }

    public static void pauseplayer() {

        if (mp.isPlaying()) {
            player.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
            mp.pause();
            MainActivity.notificationn.build();
            mainActivity.changebuttoniconn();
        } else {
            player.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            mp.start();
            MainActivity.notificationn.build();
            mainActivity.changebuttoniconn();
        }
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
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void playsong(final long seektime) {
        FirebaseDatabase.getInstance().getReference("playcurrentsong").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    linkk = snapshot1.getValue(String.class);
                    if (linkk != null) {
                        try {
                            mp.reset();
                            mp.setDataSource(linkk);
                            mp.prepare();
                            player.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                            mp.seekTo((int) seektime);
                            mp.start();
                        }
                        catch (IOException ex)
                        {
                            Toast.makeText(getContext(), "not Ready", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                            Toast.makeText(getContext(), "Link is empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }
}