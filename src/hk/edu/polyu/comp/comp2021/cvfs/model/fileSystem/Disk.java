/**
 * -*- coding : utf-8 -*-
 *
 * @FileName : Disk.java
 * @Author : Ruixiang JIANG (Songrise)
 * @Time : 2020-11-17
 * @Github ：https://github.com/songrise
 * @Descriptions: An encapsulation of root dir, working dir, and capacity.
 * Generally, it is just a delegate class. But we still believe this encapsulation is necessary.
 **/

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

    private boolean canStore(String contentToStore) {
        int size = this.calStorage();
        final int newFileSize = contentToStore.length() * 2 + 40;
        if (newFileSize + size > this.capacity) {
            throw new OutOfMemoryError(
                    "Insufficient storage!" + (newFileSize) + " required, " + (this.capacity - size) + " left.");
        }
        return true;
    }



    // -----------------Public methods----------------//
    public File findFile(String name) {
        return this.workingDir.findFile(name);
    }
    
    public void makeDocument(String docName, String typeStr, String content) {
        if (this.canStore(content))
            workingDir.createDocument(docName, typeStr, content);
    }

    public ArrayList<File> list() {
        return workingDir.list();
    }

    public ArrayList<File> rList() {
        return workingDir.rList();
    }

    public void changeDir(String newDirName) {
        try {
            File f = this.workingDir.findFile(newDirName);
            if (f.isDirectory()) {
                workingDir = (Directory) f;
            } else {
                throw new IllegalArgumentException(newDirName + "is not name of a directory!");
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(newDirName + "not found in current directory!");
        }
    }

    public Directory getWorkingDir() {
        return this.workingDir;
    }

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

    public void rename(String oldName, String newName) {
        workingDir.renameFile(oldName, newName);
    }

    @Override
    public String toString() {
        return "Disk{" + "capacity=" + capacity + ", root=" + root + ", workingDir=" + workingDir + '}';
    }
}
