package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayDeque;
import java.util.ArrayList;

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

    public void newDoc(String docName, String typeStr, String content) {
        this.crtDisk.makeDocument(docName, typeStr, content);
    }

    public void newDir(String dirName) {
        crtDisk.makeDir(dirName);
    }

    public void delFile(String fileName) {
        crtDisk.deleteFile(fileName);
    }

    public void rename(String oldName, String newName) {
        File f = crtDisk.findFile(oldName);
        f.setName(newName);
    }

    public void list() {
        ArrayList<File> fileList = crtDisk.list();
        // TODO
    }

    public void rlist() {
        ArrayList<File> fileList = crtDisk.rList();
        // TODO
    }

    public boolean isDocument(String fileName) {
        return !this.crtDisk.findFile(fileName).isDirectory();
    }

}
