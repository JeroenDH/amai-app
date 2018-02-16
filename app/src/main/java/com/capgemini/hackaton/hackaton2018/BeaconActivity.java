package com.capgemini.hackaton.hackaton2018;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.estimote.cloud_plugin.common.EstimoteCloudCredentials;
import com.estimote.internal_plugins_api.cloud.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class BeaconActivity extends AppCompatActivity {
    private static final String TAG = BeaconActivity.class.getSimpleName();
    private ProximityZone roomZone;
    private ProximityObserver proximityObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        initiateProximitySDK();
        ButterKnife.bind(this);
    }


    @OnClick(R.id.detectButton)
    public void detectStuff(){
        Log.i(TAG,"Detecting stuff and stuff..");
        Toast.makeText(getApplicationContext(), "Detecting stuff and stuff..", Toast.LENGTH_SHORT).show();
    }

    public void initiateProximitySDK(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                999);

        EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("hackaton-amai-app-j1t","632dcf2e96dc83510aad823e32b8a34e");
        this.proximityObserver = new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
                .withBalancedPowerMode()
                .withOnErrorAction(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e(TAG,"proximityObserver error");
                        return null;
                    }
                })
                .build();

        this.roomZone = this.proximityObserver.zoneBuilder()
                .forAttachmentKeyAndValue("room", "hall")
                .inNearRange()
                .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                    @Override public Unit invoke(ProximityAttachment proximityAttachment) {
                        Log.d(TAG,"withOnEnterAction");
                        Toast.makeText(getApplicationContext(), "withOnEnterAction", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                })
                .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment proximityAttachment) {
                        Log.d(TAG,"withOnExitAction");
                        Toast.makeText(getApplicationContext(), "withOnExitAction", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                })
                .withOnChangeAction(new Function1<List<? extends ProximityAttachment>, Unit>() {
                    @Override
                    public Unit invoke(List<? extends ProximityAttachment> proximityAttachments) {
                        Log.d(TAG, "withOnChangeAction");
                        Toast.makeText(getApplicationContext(), "withOnChangeAction", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                })
                .create();

        ProximityObserver.Handler observationHandler =
                proximityObserver
                        .addProximityZone(roomZone)
                        .start();

        Log.d(TAG,"Initialization ProximitySDK finished");
        Toast.makeText(getApplicationContext(), "Initialization ProximitySDK finished", Toast.LENGTH_SHORT).show();

    }

}
