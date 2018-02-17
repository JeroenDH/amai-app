package com.capgemini.hackaton.hackaton2018;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class BeaconActivity extends AppCompatActivity {
    private static final String TAG = BeaconActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
