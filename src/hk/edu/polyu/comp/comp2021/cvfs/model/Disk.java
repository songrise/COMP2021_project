package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;

import hk.edu.polyu.comp.comp2021.cvfs.model.AbstractFile;
import hk.edu.polyu.comp.comp2021.cvfs.model.Directory;

public class Disk implements Serializable {
    private static final long serialVersionUID = 2021L;
    private final int size;
    private Directory root;
    private Directory crtDir;

    Disk(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Disk size cannot < 0");
        } else {
            this.size = size;
        }
        this.root = Directory.createRoot();
        crtDir = root;
    }

    // -----------------Private methods----------------//
    // -----------------Public methods----------------//
    public void makeDir(String dirName) {
        crtDir.createDirectory(dirName);
    }

    public void deleteFile(String fileName) {
        crtDir.deleteFile(fileName);
    }

    public void makeDocument(String docName) {

    }

    public Directory getCurrentDir() {
        return this.crtDir;
    }

    public AbstractFile findFile(String fileName) {
        this.crtDir.docs
    }
}
