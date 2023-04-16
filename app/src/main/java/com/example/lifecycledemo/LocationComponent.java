package com.example.lifecycledemo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * 当Activity生命周期变化的时候，LocationComponent自身能够检测到Activity的生命周期变化，从而做相应的处理
 */
public class LocationComponent {
    private static final String TAG = "LocationComponent";

    private boolean enabled;
    private LifecycleOwner mLifecycleOwner;
    private Callback mCallback;

    interface Callback {
        void updateUI(State state);
    }

    public enum State {
        CONNECTING,
        DISCONNECTING;
    }

    /**
     * lifecycle其实是用观察者模式实现的，当Activity生命周期变化的时候，通知相应的Observers即观察者。
     * 使用lifecycle，我们可以将释放资源的动作内聚在自身，减少与调用者之间的耦合。
     * @param lifecycleOwner
     * @param callback
     */
    public LocationComponent(LifecycleOwner lifecycleOwner, Callback callback) {
        mLifecycleOwner = lifecycleOwner;
        mCallback = callback;
        mLifecycleOwner.getLifecycle().addObserver(new LifecycleEventObserver() {
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
        });
    }

    public void onCreate() {
        Log.e(TAG, "onCreate:");
    }

    public void onStart() {
        Log.e(TAG, "onStart:");
        if (enabled) {
            if (mCallback != null) {
                mCallback.updateUI(State.CONNECTING);
            }
            // connect
            Log.e(TAG, "connecting...");
        }
    }

    public void onResume() {
        Log.e(TAG, "onResume:");
    }

    public void enable() {
        enabled = true;
        if (mLifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
            Log.e(TAG, "connect if not connected");
        }
    }

    public void onPause() {
        Log.e(TAG, "onPause:");
    }

    public void onStop() {
        Log.e(TAG, "onStop:");
        if (mCallback != null) {
            mCallback.updateUI(State.DISCONNECTING);
        }
        // disconnect if connected
        Log.e(TAG, "disConnecting...");
    }

    public void onDestroy() {
        Log.e(TAG, "onDestroy:");

    }
}
