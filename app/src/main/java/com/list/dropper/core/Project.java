package com.list.dropper.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project implements Serializable {

    private String title;
    private List<Task> tasks;
    private int projectId;

    public Project(int projectId, String title){
        this.title = title;
        this.tasks = new ArrayList<>();
        this.projectId = projectId;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public String getTitle(){
        return this.title;
    }

    public List<Task> getTasks(){
        return this.tasks;
    }

    public int getProjectId(){return this.projectId;}

    @Override
    public String toString(){
        return this.title;
    }
}
