package com.koverets.lab6dubl2;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AudioActivity extends AppCompatActivity {

    private ListView songListView;
    private Button playButton, pauseButton;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;

    private TextView currentTrackText;
    private int[] audioResources = {R.raw.song1, R.raw.song2, R.raw.song3, R.raw.song4};
    private String[] songTitles;
    private int[] songImages = {R.drawable.music_icon, R.drawable.music_icon,R.drawable.music_icon,R.drawable.music_icon};

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        songTitles = getResources().getStringArray(R.array.songs_titles);

        songListView = findViewById(R.id.song_list);
        playButton = findViewById(R.id.play_button);
        pauseButton = findViewById(R.id.pause_button);
        seekBar = findViewById(R.id.seek_bar);
        currentTrackText = findViewById(R.id.current_track_text);


        CustomAdapter adapter = new CustomAdapter(this, songTitles, songImages);
        songListView.setAdapter(adapter);

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startAudio(position);

                // Устанавливаем текст TextView на основе выбранной дорожки
                currentTrackText.setText(songTitles[position]);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    updateSeekBar();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mediaPlayer = MediaPlayer.create(this, audioResources[0]);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currentPosition = 0;
            };
        });
    }

    private void startAudio(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.reset(); // Reset the MediaPlayer
            mediaPlayer.release(); // Release the MediaPlayer after resetting
            mediaPlayer = null; // Set it to null after releasing
        }

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + audioResources[position]));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    updateSeekBar();
                }
            });
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
