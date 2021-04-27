package com.list.dropper.core;

import java.io.Serializable;

public class Task implements Serializable {

    private String name;
    private boolean isDone;
    private String taskId;

    public Task(String taskId, String name){
        this.name = name;
        this.isDone = false;
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getDone(){
        return this.isDone;
    }

    public String getTaskId(){return this.taskId;}

    @Override
    public String toString(){
        return this.name;
    }
}
