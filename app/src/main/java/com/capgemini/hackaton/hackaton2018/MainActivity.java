package com.capgemini.hackaton.hackaton2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.capgemini.hackaton.hackaton2018.retrofit.DoorDTO;
import com.capgemini.hackaton.hackaton2018.retrofit.DoorProfileDTO;
import com.capgemini.hackaton.hackaton2018.retrofit.OpenDoorService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.openDoorButton) Button openDoorButton;

    private OpenDoorService openDoorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRetrofit();
        ButterKnife.bind(this);
    }

    private void initRetrofit() {
        Retrofit retrofitDoor = new Retrofit.Builder()
                .baseUrl("http://192.168.101.109:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openDoorService = retrofitDoor.create(OpenDoorService.class);
    }

    @OnClick(R.id.openDoorButton)
    public void openDoor(){
        String username = "test";
        String password = "test";
        Call<DoorProfileDTO> call = openDoorService.open(new DoorDTO(username, password));
        call.enqueue(new Callback<DoorProfileDTO>(){

            @Override
            public void onResponse(Call<DoorProfileDTO> call, Response<DoorProfileDTO> response) {
                DoorProfileDTO doorProfileDTO = response.body();
                if(response.code() == 200) {
                    Toast.makeText(getApplicationContext(), response.code()+ " Open Door! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), response.code() + " Open Door failed! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DoorProfileDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Open Door failure!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
