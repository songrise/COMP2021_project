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
    /**
     * create a new disk of specified size, and change current disk to it
     * 
     * @param size
     */
    public void newDisk(int size) {
        this.crtDisk = new Disk(size);
    }

    /**
     * create a new document in working directory, the name must be distinct
     * 
     * @param docName
     * @param typeStr
     * @param content
     */
    public void newDoc(String docName, String typeStr, String content) {
        this.crtDisk.makeDocument(docName, typeStr, content);
    }

    /**
     * create a new dir in working directory, the name must be distinct
     * 
     * @param docName
     */
    public void newDir(String dirName) {
        crtDisk.makeDir(dirName);
    }

    /**
     * delete the specified file in working directory.Deletion of directory is
     * recursive.
     * 
     * @param docName
     * @param typeStr
     * @param content
     */
    public void delFile(String fileName) {
        crtDisk.deleteFile(fileName);
    }

    /**
     * rename the specified file in working directory.
     *
     */
    public void rename(String oldName, String newName) {
        File f = crtDisk.findFile(oldName);
        f.setName(newName);
    }

    // TODO incomplete method
    public ArrayList<File> list() {
        ArrayList<File> fileList = crtDisk.list();
        return fileList;
    }

    // TODO incomplete method
    public ArrayList<File> rlist() {
        ArrayList<File> fileList = crtDisk.rList();
        return fileList;
    }

    public boolean isDocument(String fileName) {
        return !this.crtDisk.findFile(fileName).isDirectory();
    }

}
