package com.sid20010266.mathapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    static MediaPlayer bgMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgMusic = MediaPlayer.create(this, R.raw.wii_shop_music);
        bgMusic.setLooping(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bgMusic.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bgMusic.start();
    }
}