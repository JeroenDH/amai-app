package com.capgemini.hackaton.hackaton2018;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capgemini.hackaton.hackaton2018.retrofit.MessageDTO;
import com.capgemini.hackaton.hackaton2018.retrofit.MessagePushedDTO;
import com.capgemini.hackaton.hackaton2018.retrofit.MessagingService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    @Bind(R.id.playButton) ImageButton playButton;
    @Bind(R.id.yes_record_sendButton) Button sendButton;
    @Bind(R.id.no_record_textview) TextView noRecordTextView;
    @Bind(R.id.yes_record_edittext) EditText receiverId;
    @Bind(R.id.yes_record_spinner) Spinner location;


    private MessagingService messagingService;

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
        initRetrofit();
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        fileName = this.getCacheDir().getAbsolutePath();
        fileName = fileName + "/audio.amr";
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        File file = new File(fileName);
        boolean deleted = file.delete();
        Log.d(TAG, "File is deleted? "+ deleted);
    }

    @OnClick(R.id.recordButton)
    public void record(ImageButton button){
        recording = !recording;
        if (recording) {
            button.setImageResource(R.drawable.stop_record);
            startRecord();
        } else {
            button.setImageResource(R.drawable.start_record);
            stopRecord();
        }
    }

    private void startRecord(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
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
        playButton.setVisibility(View.VISIBLE);
        noRecordTextView.setVisibility(View.GONE);
        receiverId.setVisibility(View.VISIBLE);
        location.setVisibility(View.VISIBLE);
        sendButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.playButton)
    public void play(ImageButton button){
        playing = !playing;
        if (playing) {
             button.setImageResource(R.drawable.stop_music);
            startPlay();
        } else {
            button.setImageResource(R.drawable.play);
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
//                    playButton.setText(PLAY_LABEL);
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
            playButton.setImageResource(R.drawable.play);
            player.release();
            player = null;
        }
    }

    private void initRetrofit() {
        Retrofit retrofitDoor = new Retrofit.Builder()
//                .baseUrl("http://192.168.101.155:3000/")
                .baseUrl("http://192.168.101.160:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        messagingService = retrofitDoor.create(MessagingService.class);
    }

    @OnClick(R.id.yes_record_sendButton)
    public void send(){
//        int senderId = Integer.parseInt(receiverId.getText().toString());
        int senderId = 1;
        String senderName = "testSenderName";
        String data = encodeMessage();

        String spinnerLocation = location.getSelectedItem().toString();
        String destinationId = "c15e581c4f861078b8b2b42c32a4b905";
        if(spinnerLocation == "hall"){
            destinationId = "d4ed9e691e0382103ed6852d03a4a215";
        }
        Call<MessagePushedDTO> call = messagingService.push(new MessageDTO(senderId, senderName, data,destinationId));
        call.enqueue(new Callback<MessagePushedDTO>(){

            @Override
            public void onResponse(Call<MessagePushedDTO> call, Response<MessagePushedDTO> response) {
                MessagePushedDTO messagePushedDTO = response.body();
                if(response.code() == 200) {
                    //reset view
                    Toast.makeText(getApplicationContext(), response.code()+ "Message pushed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), response.code() + "Message push failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessagePushedDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to call server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String encodeMessage(){
        File file = new File(fileName);
        byte[] bytes = new byte[(int) file.length()];
        try {

            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();

        } catch (Exception e) {
            Log.e(TAG,"Exception while reading message: " + e);
        }
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
