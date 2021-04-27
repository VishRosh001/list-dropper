package com.list.dropper.savedfile;

import android.content.Context;

import com.google.gson.internal.$Gson$Preconditions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LocalUtils {

    public static void WriteLocal(String data, Context context){
        try{
            OutputStreamWriter writer = new OutputStreamWriter(context.openFileOutput("saves.json", Context.MODE_PRIVATE));
            writer.write(data);
            writer.close();
        }catch(IOException e){
            System.err.println(e);
        }
    }

    public static String ReadLocal(Context context){
        String text = "";

        try {
            InputStream inStream = context.openFileInput("saves.json");

            if(inStream != null){
                InputStreamReader reader = new InputStreamReader(inStream);
                BufferedReader bReader = new BufferedReader(reader);
                String read = "";
                StringBuilder stringBuilder = new StringBuilder();

                while((read = bReader.readLine()) != null){
                    stringBuilder.append("\n").append(read);
                }
                inStream.close();
                text = stringBuilder.toString();
            }

        } catch(IOException e){
            System.err.println(e);
        }
        return text;
    }

}
