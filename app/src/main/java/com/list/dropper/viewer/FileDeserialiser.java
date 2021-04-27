package com.list.dropper.viewer;

import com.list.dropper.core.Project;
import com.list.dropper.core.Task;

import java.util.ArrayList;

public class FileDeserialiser {

    public static Project[] deserialiseString(String data){
        ArrayList<Project> projects = new ArrayList<>();

        String[] dataLines = data.split("\n");

        Project currentProject = new Project(0, "_global_");
        for(int i = 0; i < dataLines.length; ++i){
            String line = dataLines[i].trim();
            if(line.isEmpty())continue;
            if(isLineTask(line)){
                currentProject.addTask(createTask(line, currentProject.getTasks().size(), currentProject.getProjectId()));
            }else if(isLineProject(line)){
                projects.add(currentProject);
                currentProject = new Project(projects.size(), line.substring(0, line.length()-1));
            }
        }
        projects.add(currentProject);
        return projects.toArray(new Project[0]);
    }

    public static boolean isLineTask(String line){
        if(line.startsWith("-"))return true;
        return false;
    }

    public static boolean isLineProject(String line){
        if(line.endsWith(":"))return true;
        return false;
    }

    public static Task createTask(String line, int uId, int pId){
        Task task = new Task(""+pId+"T"+uId,  "place");

        String taskName = line;
        int endIndex = taskName.indexOf('@');
        if(endIndex < 0) endIndex = taskName.length();

        taskName = taskName.substring(1, endIndex);
        task.setName(taskName);
        if(line.contains(" @done"))task.setDone(true);

        return task;
    }

}
