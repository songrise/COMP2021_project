package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;



public class Disk implements Serializable {
    private static final long serialVersionUID = 2021L;
    private final int size;
    private final Directory root;
    private Directory workingDir;

    Disk(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Disk size cannot < 0");
        } else {
            this.size = size;
        }
        this.root = Directory.createRoot();
        workingDir = root;
    }

    // -----------------Private methods----------------//


    // -----------------Public methods----------------//
    public void makeDir(String dirName) {
        workingDir.createDirectory(dirName);
    }

    public void deleteFile(String fileName) {
        workingDir.deleteFile(fileName);
    }

    public void makeDocument(String docName, String typeStr, String content) {
        workingDir.createDocument(docName, typeStr, content);
    }

    public File findFile(String fileName) throws NoSuchElementException {
        for (File f : workingDir.files) {
            if (f.name.equals(fileName)) {
                return f;
            }
        }
        throw new NoSuchElementException("No file named " + fileName + "in working directory!");
    }

    public Directory getWorkingDir() {
        return this.workingDir;
    }

    public ArrayList<File> list() {
        return workingDir.list();
    }

    public ArrayList<File> rList() {
        return workingDir.rList();
    }

    public void changeDir(String newDirName) {
        this.workingDir = (Directory) findFile(newDirName);
    }
}
