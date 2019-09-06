package com.mantis.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.ipc.MessageEvent;
import com.mantis.ipc.utils.ShellUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xiaofei.library.hermeseventbus.HermesEventBus;

public class ClickServer extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onStart(final Intent intent, final int startId) {
        super.onStart(intent, startId);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  getMainAppEvent(MessageEvent event){
        Toast.makeText( this, "Plugin APP 方法被调用", Toast.LENGTH_SHORT ).show();
        Log.i("接收主app","="+event.getMessage());
        ShellUtils.execCommand(event.getMessage(), true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String click = intent.getStringExtra("click");
        HermesEventBus.getDefault().register(this);
        Log.i("点击", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        HermesEventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
