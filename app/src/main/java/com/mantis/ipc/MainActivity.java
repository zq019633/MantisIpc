package com.mantis.ipc;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ipc.MessageEvent;
import com.mantis.ipc.utils.ShellUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xiaofei.library.hermeseventbus.HermesEventBus;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        HermesEventBus.getDefault().register(this);
        Intent intent = new Intent(MainActivity.this, ClickServer.class);
        startService(intent);

//        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ClickServer.class);
//                intent.putExtra("click","input tap 35 276");
//                startService(intent);
//            }
//        });
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Toast.makeText(MainActivity.this,"我被点击了",Toast.LENGTH_SHORT).show();
//            }
//        });
    }


//    @Override
//    public void onDestroy() {
//        HermesEventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void  getMainAppEvent(MessageEvent event){
//        Toast.makeText( MainActivity.this, "Plugin APP 方法被调用", Toast.LENGTH_SHORT ).show();
//        Log.i("接收主app","="+event.getMessage());
//        ShellUtils.execCommand(event.getMessage(), true);
//    }

}
