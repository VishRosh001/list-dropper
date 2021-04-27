package com.list.dropper.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.list.dropper.R;

import java.io.InputStream;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsActivity extends AppCompatActivity {

    Button btnProjectPicker, btnBackgroundPicker, btnTaskPicker;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnBackgroundPicker = findViewById(R.id.btn_background_picker);
        btnProjectPicker = findViewById(R.id.btn_title_picker);
        btnTaskPicker = findViewById(R.id.btn_task_picker);

        btnProjectPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openColourPickerForTitle(0xffff0000);}
        });
        btnBackgroundPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openColourPickerForBackground(0xffffffff);}
        });
        btnTaskPicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){openColourPickerForTask(0xff000000);}
        });
    }

    public void openColourPickerForTitle(int defaultColour) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}
            @Override
            public void onOk(AmbilWarnaDialog dialog, int colour) {
                String hexColour = "#"+Integer.toHexString(colour).substring(2);
                CssUtils.updateColours(getApplicationContext(), "PROJECT_TITLE", hexColour);
            }
        });
        colorPicker.show();
     }

    public void openColourPickerForBackground(int defaultColour) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}
            @Override
            public void onOk(AmbilWarnaDialog dialog, int colour) {
                String hexColour = "#"+Integer.toHexString(colour).substring(2);
                CssUtils.updateColours(getApplicationContext(), "BACKGROUND", hexColour);
            }
        });
        colorPicker.show();
    }

    public void openColourPickerForTask(int defaultColour) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, defaultColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}
            @Override
            public void onOk(AmbilWarnaDialog dialog, int colour) { ;
                String hexColour = "#"+Integer.toHexString(colour).substring(2);
                CssUtils.updateColours(getApplicationContext(), "TASK", hexColour);
            }
        });
        colorPicker.show();
    }
}

