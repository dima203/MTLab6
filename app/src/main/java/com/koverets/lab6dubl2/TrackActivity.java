package com.koverets.lab6dubl2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class TrackActivity extends AppCompatActivity {
    MediaPlayer mPlayer;
    Button playButton, pauseButton, stopButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        mPlayer = MediaPlayer.create(this, R.raw.sound);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);
    }
    private void stopPlay(){
        mPlayer.stop();
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            playButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void play(View view){
        mPlayer.start();
    }

    public void stop(View view){
        stopPlay();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
    }
}
