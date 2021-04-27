package com.list.dropper.viewer;

import android.content.Context;
import android.widget.Toast;

import com.list.dropper.core.Project;
import com.list.dropper.core.Task;
import com.list.dropper.dropbox.WriteFileRunnable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavascriptInterface {

    Context context;
    Project[] projects;
    String filePath;
    String filename;

    JavascriptInterface(Context context, Project[] projects, String filePath, String filename){
        this.context = context;
        this.projects = projects;
        this.filePath = filePath;
        this.filename = filename;
    }

    @android.webkit.JavascriptInterface
    public void showToast(String toast){
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    @android.webkit.JavascriptInterface
    public void toggleState(String id){
        if(projects == null || projects.length <= 0) return;
        if(filename == null || filePath == null) return;
        String[] ids = id.split("T");
        int projectIndex = Integer.parseInt(ids[0]);
        int taskIndex = Integer.parseInt(ids[1]);
        Task task = projects[projectIndex].getTasks().get(taskIndex);
        task.setDone(!task.getDone());

        String fileOutput = ViewerSerialiser.serialiseProjects(projects);

        File outDir = context.getCacheDir();
        File outputFile = null;
        try {
            outputFile = File.createTempFile(filename, "txt", outDir);
            FileWriter writer = new FileWriter(outputFile, false);
            writer.write(fileOutput);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(outputFile == null) return;

        WriteFileRunnable writeFileRunnable = new WriteFileRunnable(filePath, outputFile, filename);
        new Thread(writeFileRunnable).start();
    }

}
