/**
* -*- coding : utf-8 -*-
* @FileName  : Directory.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-15
* @Github    ：https://github.com/songrise
* @Descriptions: Directory class
**/

package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;

import java.util.*;

public class Directory extends ConcreteFile {

    // -----------------field ----------------//

    private static final long serialVersionUID = 2021L;
    private Directory parentDir;
    private final ArrayList<File> files;

    // -----------------Constructor----------------//
    private Directory() {// when this constructor is called, this dir is the root dir
        super("", "DIR");
        parentDir = null;
        files = new ArrayList<>();
    }

    private Directory(String name, Directory parent) {
        this();
        this.setName(name);
        parentDir = parent;
    }

    // -----------------Private methods----------------//
    /**
     * Check if the name is already exists in this directory.
     * 
     * @param fileName
     * @return
     */
    private boolean nameAlreadyExists(String fileName) {
        for (File f : files) {
            if (f.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * delete the specified File Object in current directory. Deletion of a
     * directory will recursively delete all files inside this dir.
     */
    private void deleteFile(File toDel) {
        this.files.remove(toDel);
    }

    // -----------------Protected methods----------------//
    protected File createDocument(String fileName, String typeStr, String content) throws IllegalArgumentException {
        if (nameAlreadyExists(fileName)) {
            throw new IllegalArgumentException("File: " + fileName + " already exist!");
        }
        Document newDoc = new Document(fileName, typeStr, content, this);
        this.files.add(newDoc);
        return newDoc;
    }

    protected void createDirectory(String dirName) throws IllegalArgumentException {
        if (nameAlreadyExists(dirName)) {
            throw new IllegalArgumentException("File: " + dirName + " already exist!");
        }
        Directory newDir = new Directory(dirName, this);
        this.files.add(newDir);
    }

    /**
     * Factory method for creating a root dir.
     * 
     * @return
     */
    protected static Directory createRoot() {
        return new Directory();
    }

    void deleteFile(String toDelName) throws NoSuchElementException {
        File fileToDel = this.findFile(toDelName);
        this.deleteFile(fileToDel);
    }

    File getParentDirectory() {
        if (this.parentDir == null) {
            throw new NoSuchElementException("Now is at root directory!");
        }
        return this.parentDir;
    }

    ArrayList<File> list() {
        return new ArrayList<>(files);
    }

    ArrayList<File> rList() {
        ArrayList<File> result = new ArrayList<>();
        if (files.isEmpty()) {
            return result;
        }
        for (File f : files) {
            result.add(f);
            if (f.isDirectory()) {
                Directory fd = (Directory) f;
                result.addAll(fd.rList());
            }
        }
        return result;
    }

    File findFile(String fileName) throws NoSuchElementException {
        if (fileName == null) {
            throw new IllegalArgumentException("Null file name");
        }
        if (fileName.equals("..")) {// suppose ".." always means parent dir
            return this.getParentDirectory();
        }
        for (File f : files) {
            if (f.getName().equals(fileName)) {// try name match
                return f;
            }
        }
        throw new NoSuchElementException("No file named " + fileName + " in working directory!");
    }

    void renameFile(String oldName, String newName) {
        File fileToRename = findFile(oldName);
        fileToRename.setName(newName);
    }

    // -----------------Public methods----------------//
    public String getFullPath() {// TODO refactor to base class
        ArrayDeque<String> stack = new ArrayDeque<>();
        Directory crtDir = this;
        while (crtDir != null) {
            stack.push(crtDir.getName());
            try {
                crtDir = (Directory) crtDir.getParentDirectory();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        StringBuilder sb = new StringBuilder(".");
        while (!stack.isEmpty()) {
            sb.append(stack.pop()).append("/");
        }
        return sb.toString();
    }

    @Override
    public String getFullName() {
        return this.getName()+"/";
    }

    @Override
    public String toString() {
        return "Directory{" + "type=" + this.getType() + ", name='" + this.getName() + '\'' + ", parentDir=" + parentDir
                + ", files=" + files + '}';
    }

    public static void main(String[] args) {
        // latter put it to test folder as Unit test;
    }

}
