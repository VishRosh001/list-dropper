package com.list.dropper.fileslist.file;

import java.io.Serializable;

public class File{

    private String filename;
    private String fileDate;
    private FileType fileType;

    public File(String filename, String fileDate, FileType fileType) {
        this.filename = filename;
        this.fileDate = fileDate;
        this.fileType = fileType;
    }

    public File(String filename, String fileDate) {
       this(filename, fileDate, FileType.TxtFile);
    }

    public File(FileType fileType) {
        this.fileType = fileType;
    }

    public File() {}

    public String getFilename() {
        return filename;
    }

    public String getFileDate() {
        return fileDate;
    }

    public FileType getFileType() {
        return fileType;
    }

    public String getDisplayName(){
        String[] fileParts = this.filename.split("\\.");
        StringBuilder displayName = new StringBuilder();
        displayName.append(fileParts[0]);
        for(int i = 1; i < fileParts.length-1; ++i){
            displayName.append(".").append(fileParts[i]);
        }
        return displayName.toString();
    }

    public void setFileName(String fileName) {
        this.filename = fileName;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString(){
        return this.filename;
    }
}
