package com.example.fmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class  pauseplay extends BroadcastReceiver {
//    MediaPlayer mp;
//    pauseplay(MediaPlayer mp)
//    {
//        this.mp = mp;
//    }
    @Override
    public void onReceive(Context context, Intent intent) {
            home.pauseplayer();
          }
        }
