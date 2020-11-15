package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayDeque;

public class CVFS {
    private ArrayDeque<Disk> sysStack;
    private Disk crtDisk;

    CVFS() {
        sysStack = new ArrayDeque<>(128);
        this.newDisk(255);
    }

    // -----------------Public methods----------------//
    public void newDisk(int size) {
        this.crtDisk = new Disk(size);
    }

    public void newDoc() {

    }

    public void newDir(String dirName) {
        crtDisk.makeDir(dirName);
    }

    public void delFile(String fileName) {
        crtDisk.deleteFile(fileName);
    }

    public void rename(String oldName, String newName) {
        
    }

    public void list() {

    }

}
