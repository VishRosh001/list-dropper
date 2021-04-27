package com.list.dropper;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.users.FullAccount;
import com.list.dropper.dropbox.DropboxAuthActivity;
import com.list.dropper.dropbox.DropboxClientFactory;
import com.list.dropper.savedfile.SavedActivity;

public class MainActivity extends DropboxAuthActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.startOAuth2Authentication(MainActivity.this, getString(R.string.dropbox_key));
                loadData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasToken()) {
            Intent openWithIntent = new Intent(MainActivity.this, SavedActivity.class);
            startActivity(openWithIntent);
        }else{
            Auth.startOAuth2Authentication(MainActivity.this, getString(R.string.dropbox_key));
        }
    }

    //TODO: Clean the debug code!
    protected void loadData() {
        new GetCurrentAccountTask(DropboxClientFactory.getClient(), new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {
                System.out.println(result.getEmail());
                System.out.println(result.getName());
                System.out.println(result.getAccountType().name());
            }

            @Override
            public void onError(Exception e) {
                Log.e(getClass().getName(), "Failed to get account details.", e);
            }
        }).execute();
    }
}