package com.example.fmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.LogRecord;

import io.grpc.okhttp.internal.framed.FrameReader;

public class MainActivity extends AppCompatActivity {
    NotificationManagerCompat notificationManager;
    String name;
    Notification notificationn;
    Bitmap notifyimg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getfireimg();
        getfire();

        notificationManager = NotificationManagerCompat.from(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new home()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfrag = null;
                switch (item.getItemId()) {
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new home()).commit();
                        break;
                    case R.id.info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new info()).commit();
                        break;
                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedfrag).commit();
                return true;
            }
        });
    }

    public void notifications() {
        Intent startintent = new Intent(this, MainActivity.class);
        PendingIntent startactivity = PendingIntent.getActivity(this, 0, startintent, 0);
        
        notificationn = new NotificationCompat.Builder(this, app.CHANNEL_1)
                    .setSmallIcon(R.drawable.imggg)
                    .setAutoCancel(false)
                    .setLargeIcon(notifyimg)
                    .setContentTitle("Tamil fm")
                    .setContentText(name)
                    .setContentIntent(startactivity)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setChannelId(app.CHANNEL_1)
                    .setOngoing(true)
                    .addAction(R.drawable.ic_baseline_play_arrow_24, "Play", startactivity)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0))
                    .build();

            notificationManager.notify(0, notificationn);
    }

    public void getfire() {

        FirebaseDatabase.getInstance().getReference("show_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name1 = snapshot.getValue(String.class);
                name = name1;
                notifications();
                getfireimg();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}

        public void getfireimg(){
        FirebaseDatabase.getInstance().getReference("home").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            Picasso.get().load(snapshot.getValue().toString()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    // loaded bitmap is here (bitmap)
                    System.out.println("inside bitmap");
                    notifyimg = bitmap;
                    notifications();
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });

        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });}
}
