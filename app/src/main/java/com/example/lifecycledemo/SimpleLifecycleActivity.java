package com.example.lifecycledemo;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 自定义类使其成为 LifecycleOwner
 * 可以使用 LifecycleRegistry 类，但需要将事件转发到该类
 *【LifecycleOwner 是单一方法接口，表示类具有 Lifecycle。它具有一种方法（即 getLifecycle()），该方法必须由类实现。】
 */
public class SimpleLifecycleActivity extends Activity implements LifecycleOwner {
    private static final String TAG = "SimpleLifecycleActivity";

    private LifecycleRegistry mLifecycleRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_lifecycle);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                //ON_CREATE --> ON_START --> ON_RESUME --> ON_PAUSE --> ON_STOP --> ON_DESTROY
                Log.e(TAG, "onStateChanged: event =" + event);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}