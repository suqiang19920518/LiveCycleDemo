package com.example.lifecycledemo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * lifecycle其实是用观察者模式实现的，当Activity生命周期变化的时候，通知相应的Observers即观察者。
 * 使用lifecycle，我们可以将释放资源的动作内聚在自身，减少与调用者之间的耦合。
 */
public class LocationObserver implements DefaultLifecycleObserver {
    private static final String TAG = "LocationObserver";

    private boolean enabled;
    private Lifecycle mLifeCycle;
    private Callback mCallback;

    interface Callback {
        void updateUI(State state);
    }

    public enum State {
        CONNECTING,
        DISCONNECTING;
    }

    public LocationObserver(Lifecycle lifecycle, Callback callback) {
        mLifeCycle = lifecycle;
        mCallback = callback;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "onCreate...");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "onStart...");
        if (enabled) {
            if (mCallback != null) {
                mCallback.updateUI(State.CONNECTING);
            }
            // connect
            Log.e(TAG, "connecting...");
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "onResume...");
    }

    public void enable() {
        enabled = true;
        if (mLifeCycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
            Log.e(TAG, "connect if not connected");
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "onPause...");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "onStop...");
        if (mCallback != null) {
            mCallback.updateUI(State.DISCONNECTING);
        }
        // disconnect if connected
        Log.e(TAG, "disConnecting...");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.e(TAG, "onDestroy...");
    }
}
