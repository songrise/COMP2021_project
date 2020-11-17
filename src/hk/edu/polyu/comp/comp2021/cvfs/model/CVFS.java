package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CVFS {
    private ArrayDeque<Disk> sysUndoStack;
    private ArrayDeque<Disk> sysRedoStack;

    private Disk crtDisk;

    CVFS() {
        sysUndoStack = new ArrayDeque<>(128);
        sysRedoStack = new ArrayDeque<>(128);
        this.newDisk(255);
    }

    // -----------------Private methods----------------//
    private void writeSerializable() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("disk.model"));
            oos.writeObject(crtDisk);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSerializable() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("disk.model"));
            crtDisk = (Disk) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return a deep copy of the current disk object
     */
    private Object deepCopy() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(crtDisk);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void pushUndoStack() {
        Disk crtDiskCopy = (Disk) deepCopy();
        sysUndoStack.push(crtDiskCopy);
    }

    private Disk popUndoStack() {
        if (sysUndoStack.isEmpty()) {
            throw new EmptyStackException();
        } else {
            sysRedoStack.push((Disk) deepCopy());
            Disk d = sysUndoStack.pop();
            return d;
        }
    }

    private void pushRedoStack() {
        Disk crtDiskCopy = (Disk) deepCopy();
        sysRedoStack.push(crtDiskCopy);
    }

    private Disk popRedoStack() {
        if (sysRedoStack.isEmpty()) {
            throw new EmptyStackException();
        } else {
            sysUndoStack.push((Disk) deepCopy());
            Disk d = sysRedoStack.pop();
            return d;
        }
    }

    // -----------------Public methods----------------//
    /**
     * create a new disk of specified size, and change current disk to it
     *
     * @param size
     */
    public void newDisk(int size) {
        // pushUndoStack();
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
        // pushUndoStack();
        this.crtDisk.makeDocument(docName, typeStr, content);
    }

    /**
     * create a new dir in working directory, the name must be distinct
     *
     * @param docName
     */
    public void newDir(String dirName) {
        // pushUndoStack();
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
        // pushUndoStack();
        crtDisk.deleteFile(fileName);
    }

    /**
     * rename the specified file in working directory.
     *
     */
    public void rename(String oldName, String newName) {
        // pushUndoStack();

        File f = crtDisk.findFile(oldName);
        f.setName(newName);
    }

    /**
     * change current directory to specified dir ".." is the parent dir throw
     * exception when null dirname, cd .. at root, dirName is not name of a
     * directory.
     */
    public void changeDir(String dirName) {
        crtDisk.changeDir(dirName);
    }

    // TODO incomplete method
    public ArrayList<File> list() {
        // pushUndoStack();

        ArrayList<File> fileList = crtDisk.list();
        return fileList;
    }

    // TODO incomplete method
    public ArrayList<File> rlist() {
        // pushUndoStack();

        ArrayList<File> fileList = crtDisk.rList();
        return fileList;
    }

    public boolean isDocument(String fileName) {
        return !this.crtDisk.findFile(fileName).isDirectory();
    }

    public void store() {
        writeSerializable();
    }

    public void read() {
        // pushUndoStack();
        readSerializable();
    }

    public void undo() {
        this.crtDisk = popUndoStack();
    }

    public void redo() {
        this.crtDisk = popRedoStack();
    }

    public static void main(String[] args) {
        //latter put this to unit test.
        CVFS t = new CVFS();
        t.newDisk(127);
        t.newDoc("TestTxt","TXT","TESTING");
        t.newDoc("TestHtml","htMl","TESTING,Html");
        System.out.println(t.isDocument("TestTxt"));
        t.newDir("TFolder1");
        if(t.isDocument("TFolder1")){
            System.out.println("ERROR");
        }
        for (File f :t.list()){
            System.out.println(f.getFullName());
        }
        t.changeDir("TFolder1");
        t.newDoc("TestTxt2","TXT","TESTING");
        for (File f :t.list()){
            System.out.println(f.getFullName());
        }
//        t.changeDir(".."); buggy
//        for (File f :t.list()){
//            System.out.println(f.getFullName());
//        }
    }

}
