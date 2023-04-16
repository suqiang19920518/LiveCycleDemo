package com.example.lifecycledemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private LocationObserver mLocationObserver;
    private LocationComponent mLocationComponent;
    private MyService myService;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                //ON_CREATE --> ON_START --> ON_RESUME --> ON_PAUSE --> ON_STOP --> ON_DESTROY
                Log.e(TAG, "onStateChanged: event =" + event);
            }
        });

        initObserver();
        initComponent();
    }

    private void initComponent() {
        mLocationComponent = new LocationComponent(this, new LocationComponent.Callback() {
            @Override
            public void updateUI(LocationComponent.State state) {
                Log.e(TAG, "LocationComponent: state =" + state);
            }
        });
    }

    private void initObserver() {
        mLocationObserver = new LocationObserver(getLifecycle(), new LocationObserver.Callback() {
            @Override
            public void updateUI(LocationObserver.State state) {
                Log.e(TAG, "LocationObserver: state =" + state);
            }
        });
        getLifecycle().addObserver(mLocationObserver);
    }

    private void initView() {
        findViewById(R.id.btn_self_define_lifecycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SimpleLifecycleActivity.class));
            }
        });
        findViewById(R.id.btn_life_cycle_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myService == null) {
                    myService = new MyService();
                }
                serviceIntent = new Intent(MainActivity.this, MyService.class);
                startService(serviceIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationObserver.enable();
        mLocationComponent.enable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceIntent != null) {
            stopService(serviceIntent);
        }
    }
}