package com.list.dropper.savedfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.list.dropper.MainActivity;
import com.list.dropper.R;
import com.list.dropper.settings.SettingsActivity;
import com.list.dropper.fileslist.FilesActivity;
import com.list.dropper.fileslist.file.File;
import com.list.dropper.viewer.HtmlViewerActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class SavedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<SavedFile> savedFiles;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        this.savedFiles = new ArrayList<>();

        this.recyclerView = findViewById(R.id.recycler_saved);
        this.progressBar = findViewById(R.id.progressBar);
        this.progressBar.setVisibility(View.INVISIBLE);
        this.setAdapter();

        java.io.File file = new java.io.File(this.getExternalFilesDir(null).getAbsolutePath()+"/", "styles.css");
        if(!file.exists() || file.isDirectory()) loadStylesCss();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(SavedActivity.this, MainActivity.class);
                intent.putExtra("EXIT_APP", true);
                startActivity(intent);
            }
        };

        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public void onResume() {
        super.onResume();

        String serialised = LocalUtils.ReadLocal(this.getApplicationContext());
        if(!serialised.isEmpty()) SavedFileRegistry.getRegistry().deserialiseRegistry(serialised);

        this.savedFiles.clear();
        this.savedFiles.addAll(SavedFileRegistry.getRegistry().getRegistryQueue());
        Objects.requireNonNull(this.recyclerView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_saved_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_file_explorer:
                Intent intent = new Intent(SavedActivity.this, FilesActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                Intent settings_intent = new Intent(SavedActivity.this, SettingsActivity.class);
                startActivity(settings_intent);
            default:
                break;
        }
        return true;
    }

    public void setAdapter(){
        final SavedListAdapter listAdapter = new SavedListAdapter(this.savedFiles);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(listAdapter);

        listAdapter.setItemClickListener(new SavedListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                File selectedFile = savedFiles.get(position).getFile();
                String filePath = savedFiles.get(position).getFilePath();

                Intent intent = new Intent(SavedActivity.this, HtmlViewerActivity.class);
                intent.putExtra("filename", selectedFile.getDisplayName());
                intent.putExtra("file_path", filePath);
                startActivity(intent);
            }
        });
    }

    private void loadStylesCss(){
        String baseDir = getExternalFilesDir(null).getAbsolutePath();

        try {
            FileOutputStream outputStream = new FileOutputStream(new java.io.File(baseDir +"/", "styles.css")) ;
            InputStream inputStream = this.getAssets().open("styles.css");

            int n;

            this.progressBar.setVisibility(View.VISIBLE);
            while((n = inputStream.read()) != -1){
                outputStream.write(n);
            }
            this.progressBar.setVisibility(View.INVISIBLE);
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
