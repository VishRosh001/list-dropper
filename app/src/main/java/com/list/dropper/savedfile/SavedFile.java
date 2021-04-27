package com.list.dropper.savedfile;

import com.list.dropper.fileslist.file.File;

//TODO: Create API for storing file paths
public class SavedFile{
    private final String filePath;
    private final File file;

    public SavedFile(String filePath, File file) {
        this.filePath = filePath;
        this.file = file;
    }

    @Override
    public boolean equals(Object object){
        boolean areEqual = false;

        if(object instanceof SavedFile){
            areEqual = (this.filePath.equals(((SavedFile) object).filePath) && (this.file.getDisplayName().equals(((SavedFile)object).file.getDisplayName())));
        }
        return areEqual;
    }

    @Override
    public String toString(){
        return this.file.getDisplayName();
    }

    public String getFilePath(){return this.filePath;}
    public File getFile(){return this.file;}
}
