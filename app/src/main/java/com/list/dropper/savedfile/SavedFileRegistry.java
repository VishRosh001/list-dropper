package com.list.dropper.savedfile;

import com.google.gson.Gson;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class SavedFileRegistry {

    private final Queue<SavedFile> savedFileRegistry;
    private final int capacity;

    private static SavedFileRegistry fileRegistry;

    private SavedFileRegistry(){
        this.capacity = 10;
        this.savedFileRegistry = new ArrayBlockingQueue<SavedFile>(capacity);
    }

    public static SavedFileRegistry getRegistry(){
        if(fileRegistry == null)fileRegistry = new SavedFileRegistry();
        return fileRegistry;
    }

    public Queue<SavedFile> getRegistryQueue(){ return this.savedFileRegistry;}

    public void addToRegistry(SavedFile savedFile){
        if(!this.savedFileRegistry.contains(savedFile)){
            if(this.savedFileRegistry.size() == this.capacity){
                this.savedFileRegistry.remove();
            }
            this.savedFileRegistry.add(savedFile);
        }
    }


    public String getSerialised(){
        Gson gson = new Gson();
        return gson.toJson(this.savedFileRegistry.toArray());
    }

    public void deserialiseRegistry(String serialised){
        Gson gson = new Gson();
        SavedFile[] savedFiles = gson.fromJson(serialised, SavedFile[].class);
        for(SavedFile sf : savedFiles){this.addToRegistry(sf);}
    }

    public void printRegistry(){
        System.out.println("-----------------------------------------------------------");
        for(SavedFile s : this.savedFileRegistry){
            System.out.println(s.getFile().getDisplayName());
        }
        System.out.println("------------------------------------------------------------");
    }

}
