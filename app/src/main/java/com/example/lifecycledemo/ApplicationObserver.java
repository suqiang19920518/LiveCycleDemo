package com.example.lifecycledemo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public class ApplicationObserver implements LifecycleEventObserver {
    private static final String TAG = "ApplicationObserver";

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            onCreate();
        } else if (event == Lifecycle.Event.ON_START) {
            onStart();
        } else if (event == Lifecycle.Event.ON_RESUME) {
            onResume();
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            onPause();
        } else if (event == Lifecycle.Event.ON_STOP) {
            onStop();
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroy();
        }
    }

    public void onCreate() {
        Log.e(TAG, "onCreate:");
    }

    public void onStart() {
        Log.e(TAG, "onStart:");
    }

    public void onResume() {
        Log.e(TAG, "onResume:");
    }

    public void onPause() {
        Log.e(TAG, "onPause:");
    }

    public void onStop() {
        Log.e(TAG, "onStop:");
    }

    public void onDestroy() {
        Log.e(TAG, "onDestroy:");
    }
}
