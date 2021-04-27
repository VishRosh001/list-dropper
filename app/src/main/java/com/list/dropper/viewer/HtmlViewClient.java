package com.list.dropper.viewer;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HtmlViewClient extends WebViewClient {

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if(request.getUrl().toString().endsWith("styles.css")){
            return getCssWebResourceResponseFromStorage(view);
        }
        return super.shouldInterceptRequest(view, request);
    }

    private WebResourceResponse getCssWebResourceResponseFromStorage(WebView view) {
        try {
            String baseDir = view.getContext().getExternalFilesDir(null).getAbsolutePath();
            File file = new File(baseDir +"/", "styles.css");
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            return new WebResourceResponse("text/css", "UTF-8", inputStream);
        } catch (IOException e) {
            return null;
        }
    }
}
