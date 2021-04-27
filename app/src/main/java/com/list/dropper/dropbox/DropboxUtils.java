package com.list.dropper.dropbox;

import android.app.Activity;
import android.content.SharedPreferences;

public class DropboxUtils {

    public static String getAccesToken(Activity activity){
        SharedPreferences prefs = activity.getSharedPreferences("dropbox-sample", 0);
        return prefs.getString("access-token", null);
    }

}
