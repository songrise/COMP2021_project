package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Disk implements Serializable {
    private static final long serialVersionUID = 2021L;
    private final int capacity;
    private final Directory root;
    private Directory workingDir;

    Disk(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Disk size cannot < 0");
        } else {
            this.capacity = capacity;
        }
        this.root = Directory.createRoot();
        workingDir = root;
    }

    // -----------------Private methods----------------//

    private int calStorage() {
        int size = 0;
        for (File f : root.rList()) {
            size += f.getSize();
        }
        return size;
    }

    // -----------------Protected methods----------------//

    protected void makeDocument(String docName, String typeStr, String content) {
        // storage will only full when creating new file, so lazy evaluation here.
        int size = this.calStorage();
        if (content.length() * 2 + 40 + size > this.capacity) {
            throw new OutOfMemoryError("Insufficient storage!" + (content.length() * 2 + 40) + " required, "
                    + (this.capacity - size) + " left.");
        }
        workingDir.createDocument(docName, typeStr, content);
    }

    protected File findFile(String fileName) throws NoSuchElementException {
        if (fileName == null) {
            throw new IllegalArgumentException("Null file name");
        }
        for (File f : workingDir.files) {
            if (f.name.equals(fileName)) {
                return f;
            }
        }
        throw new NoSuchElementException("No file named " + fileName + " in working directory!");
    }

    protected ArrayList<File> list() {
        return workingDir.list();
    }

    protected ArrayList<File> rList() {
        return workingDir.rList();
    }

    protected void changeDir(String newDirName) {
        if (newDirName == null) {
            throw new IllegalArgumentException("Null file name");
        } else if (newDirName.equals("..")) {
            try {
                this.workingDir = (Directory) workingDir.getParentDirectory();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        } else {
            try {
                File f = findFile(newDirName);
                if (f.getClass() != workingDir.getClass()) {
                    throw new IllegalArgumentException(newDirName + "is not a directory!");
                }
                workingDir = (Directory) f;
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(newDirName + "not found");
            }

        }
    }

    protected Directory getWorkingDir() {
        return (Directory) this.workingDir;
    }

    // -----------------Public methods----------------//

    public String getWorkingDirName() {
        return this.workingDir.getName();
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void makeDir(String dirName) {
        workingDir.createDirectory(dirName);
    }

    public void deleteFile(String fileName) {
        workingDir.deleteFile(fileName);
    }

    @Override
    public String toString() {
        return "Disk{" +
                "capacity=" + capacity +
                ", root=" + root +
                ", workingDir=" + workingDir +
                '}';
    }
}
