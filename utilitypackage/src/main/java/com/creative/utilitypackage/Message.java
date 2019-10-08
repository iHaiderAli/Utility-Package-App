package com.creative.utilitypackage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kashif Ahmed on 21/6/2019.
 */

public class Message {

    public static void showMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLogs(String key, String msg) {
        Log.d(key, msg);
    }

}
