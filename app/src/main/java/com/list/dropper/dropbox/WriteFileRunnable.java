package com.list.dropper.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class WriteFileRunnable implements Runnable{
    private String filePath;
    private File file;
    private String filename;

    public WriteFileRunnable(String filePath, File file, String filename){
        this.filePath = filePath;
        this.file = file;
        this.filename = filename;
    }

    @Override
    public void run() {
        try(InputStream in = new FileInputStream(file)){
            FileMetadata metadata = DropboxClientFactory.getClient().files()
                    .uploadBuilder(filePath)
                    .withClientModified(new Date(file.lastModified()))
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
        } catch (DbxException | IOException e) {
            e.printStackTrace();
        }
    }
}
