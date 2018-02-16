package com.capgemini.hackaton.hackaton2018;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordActivity extends AppCompatActivity {
    private static final String TAG = RecordActivity.class.getSimpleName();
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    //Buttons labels
    public static final String RECORD_LABEL = "Record";
    public static final String STOP_LABEL = "Stop";
    public static final String PLAY_LABEL = "Play";

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    //Flag for recording
    private boolean recording = false;

    //Flag for playing
    private boolean playing = false;

    //Recorder
    private MediaRecorder recorder;

    //Player
    private MediaPlayer player;

    private String fileName;

    private Button playButton;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        playButton = findViewById(R.id.playButton);
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        fileName = this.getCacheDir().getAbsolutePath();
        fileName = fileName + "/audiorecordtest.3gp";
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        File file = new File(fileName);
        boolean deleted = file.delete();
        Log.d(TAG, "File is deleted? "+ deleted);
    }

    @OnClick(R.id.recordButton)
    public void record(Button button){
        recording = !recording;
        if (recording) {
            button.setText(STOP_LABEL);
            startRecord();
        } else {
            button.setText(RECORD_LABEL);
            stopRecord();
        }
    }

    private void startRecord(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecord(){
        recorder.stop();
        recorder.release();
        recorder = null;
        playButton.setEnabled(true);
    }

    @OnClick(R.id.playButton)
    public void play(Button button){
        playing = !playing;
        if (playing) {
            button.setText(STOP_LABEL);
            startPlay();
        } else {
            button.setText(PLAY_LABEL);
            stopPlay();
        }
    }

    private void startPlay(){
        Log.d(TAG, "Starting playback");
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    playButton.setText(PLAY_LABEL);
                    stopPlay();
                }
            });
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

    }

    private void stopPlay(){
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
        }
    }
}
