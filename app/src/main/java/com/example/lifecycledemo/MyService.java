package com.example.lifecycledemo;

import androidx.lifecycle.LifecycleService;

/**
 * LifecycleService是Service的直接子类，使用起来与普通Service没有差别。
 */
public class MyService extends LifecycleService {

    private final MyLifecycleObserver myLifecycleObserver;

    public MyService() {
        myLifecycleObserver = new MyLifecycleObserver();
        getLifecycle().addObserver(myLifecycleObserver);
    }

}