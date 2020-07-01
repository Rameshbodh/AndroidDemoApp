package com.example.schedulerexample.application;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

public class SchedulerExampleApplication extends Application implements LifecycleObserver {

    private static final String TAG = SchedulerExampleApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        Log.w(TAG, "onCreated");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {
        Log.w(TAG, "onStarted");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop() {
        Log.w(TAG, "onStoped");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy() {
        Log.w(TAG, "onDestroyed");
    }
}
