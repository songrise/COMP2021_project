package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;

import hk.edu.polyu.comp.comp2021.cvfs.model.Directory;

public class Disk implements Serializable {
    private static final long serialVersionUID = 2021L;
    private final int size;
    Directory root;
    Directory currentDir;

    Disk(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Disk size cannot < 0");
        } else {
            this.size = size;
        }
        this.root = Directory.createRoot();
        currentDir = root;
    }

    // -----------------Private methods----------------//
    // -----------------Public methods----------------//
    public void makeDir(String dirName) {
        root.createDirectory(dirName);
    }
    public void deleteDir(String dirName){
        currentDir.deleteFile(dirName);
    }
    public void makeDocument(String docName){

    }
    public void deleteDocument(String docName){

    }

}
