package com.example.lifecycledemo;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLifecycleObserver implements LifecycleObserver {
    private static final String TAG = "MyLifecycleObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void startGetLocation() {
        Log.e(TAG, "connecting...");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void stopGetLocation() {
        Log.e(TAG, "disConnecting...");
    }

}
