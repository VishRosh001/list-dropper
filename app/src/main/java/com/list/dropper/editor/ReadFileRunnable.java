package com.list.dropper.editor;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dropbox.core.DbxException;
import com.list.dropper.dropbox.DropboxClientFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ReadFileRunnable implements Runnable {

    private String _filePath;
    private String filePath;
    private String _filename;
    Handler handler;

    public ReadFileRunnable(Handler handler, String filePath, String filename){
        this.filePath = filePath + "/" + filename + ".txt";
        this.handler = handler;
        this._filePath = filePath;
        this._filename = filename;
    }

    @Override
    public void run() {
        BufferedInputStream inStream = null;
        BufferedReader reader = null;

        StringBuilder content = new StringBuilder();

        try {
            inStream = new BufferedInputStream(DropboxClientFactory.getClient().files().download(this.filePath).getInputStream());
            reader = new BufferedReader(new InputStreamReader(inStream));

            String line;
            while((line = reader.readLine()) != null){
                content.append(line);
                content.append("\n");
            }
        } catch (DbxException | IOException e) {
            e.printStackTrace();
        }
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("file_content", content.toString());
        bundle.putString("file_path", filePath);
        bundle.putString("filename", _filename);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
