package com.mantis.ipc.utils;

import android.text.TextUtils;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {
    private static final String TAG = "StreamGobbler";
    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public void run() {
        try {
            BufferedReader stderr = new BufferedReader(new InputStreamReader(is));

            String temp;
            String res = "";
            while ((temp = stderr.readLine()) != null) {
                res += temp;
            }
            if(!TextUtils.isEmpty(res)) {
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}