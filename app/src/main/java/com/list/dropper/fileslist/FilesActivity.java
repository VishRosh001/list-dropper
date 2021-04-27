package com.list.dropper.fileslist;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.list.dropper.R;
import com.list.dropper.editor.EditorActivity;
import com.list.dropper.fileslist.file.File;
import com.list.dropper.fileslist.file.FileType;
import com.list.dropper.savedfile.LocalUtils;
import com.list.dropper.savedfile.SavedFile;
import com.list.dropper.savedfile.SavedFileRegistry;
import com.list.dropper.viewer.HtmlViewerActivity;

import java.util.ArrayList;

public class FilesActivity extends AppCompatActivity {

    private ArrayList<File> fileList;
    private RecyclerView recyclerView;
    private ArrayList<String> currentPath;

    private Handler mainHandler;

    private File selectedFile = null;
    private int selectedIndex = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainHandler = new Handler();

        setContentView(R.layout.activity_file);
        this.recyclerView = findViewById(R.id.recycler_files);

        this.fileList = new ArrayList<>();
        this.currentPath = new ArrayList<>();
        this.currentPath.add("");

        loadDirectory();

        this.setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearSelection();
    }

    @Override
    protected void onPause() {
        super.onPause();

        String serialised = SavedFileRegistry.getRegistry().getSerialised();
        LocalUtils.WriteLocal(serialised, this.getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_file, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item = menu.findItem(R.id.action_open);
        MenuItem item1 = menu.findItem(R.id.action_save);

        if(selectedFile == null){
            item.setEnabled(false);
            item1.setEnabled(false);
        }else{
            item.setEnabled(true);
            item1.setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_open:
                Intent intent = new Intent(FilesActivity.this, HtmlViewerActivity.class);

                intent.putExtra("filename", selectedFile.getDisplayName());
                intent.putExtra("file_path", getPathString());
                startActivity(intent);
                break;
            case R.id.action_save:
                SavedFileRegistry.getRegistry().addToRegistry(new SavedFile(getPathString(), selectedFile));
              //  SavedFileRegistry.getRegistry().printRegistry();
                clearSelection();
                break;
            default:
                break;
        }
        return true;
    }

    public void clearSelection(){
        if(selectedIndex != -1) {
            recyclerView.getChildAt(selectedIndex).setBackgroundColor(getResources().getColor(R.color.white, getTheme()));
            selectedIndex = -1;
        }
        selectedFile = null;
        invalidateOptionsMenu();
    }

    private void addDirectoryToPath(String directory){
        this.currentPath.add(directory);
        loadDirectory();
    }

    private void removeDirectory(){
        if(this.currentPath.size() == 1)return;
        this.currentPath.remove(this.currentPath.size()-1);
        loadDirectory();
    }

    public void loadDirectory(){
        this.fileList.clear();
        this.fileList.add(new File("..", "", FileType.Dir));
        startFetchFilesThread();
    }

    private void setAdapter(){
        FileListAdapter listAdapter = new FileListAdapter(this.fileList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(listAdapter);

        listAdapter.setItemClickListener(new FileListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position == 0){
                    removeDirectory();
                    return;
                }
                File file = fileList.get(position);
                if(file.getFileType() == FileType.Dir){
                    addDirectoryToPath(file.getFilename());
                }else{
                    if(selectedIndex != -1) {
                        recyclerView.getChildAt(selectedIndex).setBackgroundColor(getResources().getColor(R.color.white, getTheme()));
                        selectedFile = null;
                    }

                    if (position != selectedIndex) {
                        selectedIndex = position;
                        recyclerView.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.darkWhite, getTheme()));
                        selectedFile = file;
                    } else {
                        selectedIndex = -1;
                        selectedFile = null;
                    }
                    invalidateOptionsMenu();
                }
            }
        });
    }

    public void startFetchFilesThread(){
        FetchFilesRunnable fetchFilesThread = new FetchFilesRunnable(this.fileList, this.mainHandler, this.recyclerView, getPathString());
        new Thread(fetchFilesThread).start();
    }

    private String getPathString(){
        StringBuilder path = new StringBuilder();
        for(String s : this.currentPath){
            path.append(s);
        }
        return path.toString();
    }
}
