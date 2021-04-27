package com.list.dropper.viewer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.list.dropper.R;
import com.list.dropper.core.Project;
import com.list.dropper.editor.ReadFileRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class HtmlViewerActivity extends AppCompatActivity {

    private String filename, filePath;
    private WebView htmlView;

    @SuppressLint("HandlerLeak")
    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
            Bundle bundle = message.getData();
            String fileData = bundle.getString("file_content");
            Project[] projects = FileDeserialiser.deserialiseString(fileData);
            String htmlDoc = HtmlUtils.createHtmlDoc(projects);
            String _filename = bundle.getString("filename");
            String _filePath = bundle.getString("file_path");
            String baseDir = getExternalFilesDir(null).getAbsolutePath();

            htmlView.clearCache(true);

            htmlView.addJavascriptInterface(new JavascriptInterface(getApplicationContext(), projects, _filePath, _filename), "and");
            File f = new File(baseDir +"/", "styles.css");

            if(f.exists() && !f.isDirectory()) {
                htmlView.loadDataWithBaseURL("file://" + baseDir +"/", htmlDoc, "text/html", "utf-8", null);
            }else{
                htmlView.loadDataWithBaseURL("file:///android_asset/", htmlDoc, "text/html", "utf-8", null);
            }
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        this.htmlView = findViewById(R.id.html_view);
        WebSettings webSettings = htmlView.getSettings();
        webSettings.setJavaScriptEnabled(true);
      //  htmlView.setWebChromeClient(new HtmlViewClient());
        htmlView.setWebViewClient(new HtmlViewClient());
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        Bundle bundle = this.getIntent().getExtras();
        this.filename = bundle.getString("filename") == null ? "untitled" : bundle.getString("filename");
        this.filePath = bundle.getString("file_path");
    }

    @Override
    protected void onResume() {
        super.onResume();

        ReadFileRunnable readFileRunnable = new ReadFileRunnable(this.mainHandler, this.filePath, this.filename);
        new Thread(readFileRunnable).start();
    }
}
