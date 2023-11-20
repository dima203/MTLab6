package com.koverets.lab6dubl2;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;
    private Button fullscreenButton;
    private boolean isFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        fullscreenButton = findViewById(R.id.fullscreenButton);
        Spinner videoSpinner = findViewById(R.id.videoSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.video_titles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        videoSpinner.setAdapter(adapter);

        // Свяжите VideoView с MediaController, чтобы обеспечить основные функции управления видео.
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1);
        videoView.setVideoURI(videoUri);

        // Изначально установите текст кнопки "Полноэкранный режим".
        fullscreenButton.setText("Полноэкранный режим");

        // Обработчик кнопки для переключения в полноэкранный режим
        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFullscreen();
            }
        });

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedVideoPosition = videoSpinner.getSelectedItemPosition();
                int videoResource = 0;

                // Определите ресурс видео в зависимости от выбора пользователя
                switch (selectedVideoPosition) {
                    case 0:
                        videoResource = R.raw.video1;
                        break;
                    case 1:
                        videoResource = R.raw.video2;
                        break;
                    // Добавьте дополнительные варианты для других видео, если необходимо
                }

                if (videoResource != 0) {
                    Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResource);
                    videoView.setVideoURI(videoUri);
                    videoView.start();
                }
            }
        });

        // Запустите воспроизведение видео.
        videoView.start();
    }

    private void toggleFullscreen() {
        Spinner videoSpinner = findViewById(R.id.videoSpinner);
        Button playButton = findViewById(R.id.playButton);
        if (isFullscreen) {
            // Exit fullscreen mode and set the layout back to normal.

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Vertical orientation
            isFullscreen = false;

            // Set text of the fullscreen button.
            fullscreenButton.setText("Полноэкранный режим");

            // Show the Spinner and "Воспроизвести" button.
            videoSpinner.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);
        } else {
            // Enter fullscreen mode and set the orientation to landscape.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Horizontal orientation
            isFullscreen = true;

            // Set text of the fullscreen button.
            fullscreenButton.setText("Выйти из полноэкранный режим");

            // Hide the Spinner and "Воспроизвести" button.
            videoSpinner.setVisibility(View.GONE);
            playButton.setVisibility(View.GONE);
        }
    }

}
