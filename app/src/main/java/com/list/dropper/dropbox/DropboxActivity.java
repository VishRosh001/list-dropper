package com.list.dropper.dropbox;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import com.dropbox.core.android.Auth;

/**
 * Base class for Activities that require auth tokens
 * Will redirect to auth flow if needed
 */

public abstract class DropboxActivity extends AppCompatActivity {

    private boolean isAuth = false;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);
        if (accessToken == null) {
            accessToken = Auth.getOAuth2Token();
            if (accessToken != null) {
                prefs.edit().putString("access-token", accessToken).apply();
                isAuth = true;
            }
        } else {
           isAuth = true;
        }
    }

    protected boolean hasToken() {
        return DropboxUtils.getAccesToken(this) != null;
    }

    public boolean isAuth() {
        return isAuth;
    }
}
