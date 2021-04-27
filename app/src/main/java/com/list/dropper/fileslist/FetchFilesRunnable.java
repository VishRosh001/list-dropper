package com.list.dropper.fileslist;

import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.list.dropper.dropbox.DropboxClientFactory;
import com.list.dropper.fileslist.file.File;
import com.list.dropper.fileslist.file.FileType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class FetchFilesRunnable implements Runnable {
    private ArrayList<File> fileList;
    private Handler handler;
    private RecyclerView recycleView;
    private String path;

    public FetchFilesRunnable(ArrayList<File> fileList, Handler handler, RecyclerView view, String currentPath) {
        this.fileList = fileList;
        this.handler = handler;
        this.recycleView = view;
        this.path = currentPath;
    }

    @Override
    public void run() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

        try {
            ListFolderResult result = DropboxClientFactory.getClient().files().listFolder(path);

            while(true){
                for (Metadata metadata : result.getEntries()) {
                    if(metadata instanceof FolderMetadata){
                        fileList.add(new File("/"+metadata.getName(), "31/01/2021", FileType.Dir));
                    }else{
                        FileMetadata fileMetadata = (FileMetadata)metadata;
                        String[] fileSplit = fileMetadata.getName().split("\\.");

                        if(fileSplit.length > 0 && fileSplit[fileSplit.length-1].equals("txt")){
                            fileList.add(new File(fileMetadata.getName(), dateFormatter.format(fileMetadata.getServerModified()), FileType.TxtFile));
                        }
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Objects.requireNonNull(recycleView.getAdapter()).notifyDataSetChanged();
                    }
                });

                if (!result.getHasMore()) {
                    break;
                }

                result = DropboxClientFactory.getClient().files().listFolderContinue(result.getCursor());
            }
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }
}
