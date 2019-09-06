package com.mantis.ipc;

import android.app.Application;

import xiaofei.library.hermeseventbus.HermesEventBus;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HermesEventBus.getDefault().init(MyApp.this);
        HermesEventBus.getDefault().connectApp(this, "com.mantis.icpMain");
    }
}
