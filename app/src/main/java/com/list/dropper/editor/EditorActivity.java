package com.list.dropper.editor;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.list.dropper.R;
import com.list.dropper.editor.syntax.Highlighter;

public class EditorActivity extends AppCompatActivity {

    private String filename, filePath;
    private EditText editText;
    private Highlighter highlighter;

    @SuppressLint("HandlerLeak")
    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
            Bundle bundle = message.getData();
            editText.setText(bundle.getString("file_content"));
        }};

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Bundle bundle = this.getIntent().getExtras();
        this.editText = findViewById(R.id.edit_file);
        this.highlighter = new Highlighter();
        this.filename = bundle.getString("filename") == null ? "untitled" : bundle.getString("filename");
        this.filePath = bundle.getString("file_path");

        setTitle(this.filename);
    }

    @Override
    protected void onResume(){
        super.onResume();

        ReadFileRunnable readFileRunnable = new ReadFileRunnable(this.mainHandler, this.filePath, this.filename);
        new Thread(readFileRunnable).start();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable != null) highlighter.highlight(editable);
            }
        });
    }
}
