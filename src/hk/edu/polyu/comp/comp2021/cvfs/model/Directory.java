/**
* -*- coding : utf-8 -*-
* @FileName  : Directory.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-15
* @Github    ：https://github.com/songrise
* @Descriptions: Directory class
**/

package hk.edu.polyu.comp.comp2021.cvfs.model;


import java.util.*;

public class Directory extends File {

    // -----------------field ----------------//

    private static final long serialVersionUID = 2021L;
    private final FileType type; // type of directory cannot be modified.
    private String name;
    private Directory parentDir;
    private final ArrayList<File> files;

    // -----------------Constructor----------------//
    private Directory() {// when this constructor is called, this dir is the root dir
        this.setName("unnamed");
        this.type = FileType.initType("DIR");
        parentDir = null;
        files = new ArrayList<>();
    }

    private Directory(String name) {
        this();
        this.setName(name);
    }

    private Directory(String name, Directory parent) {
        this(name);
        parentDir = parent;
    }

    // -----------------Private methods----------------//
    private boolean duplicateName(String fileName) {
        for (File f : files) {
            if (f.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * delete this file. Deletion of a directory will recursively delete all files
     * inside this dir. Avoid using this method
     */
    private void deleteFile() {
        Directory parent = parentDir;
        if (parent != null)// root dir cannot be deleted
            parent.files.remove(this);
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
        if (duplicateName(fileName)) {
            throw new IllegalArgumentException("File: " + fileName + " already exist!");
        }
        Document newDoc = new Document(fileName, typeStr, content, this);
        this.files.add(newDoc);
        return newDoc;
    }

    protected Directory createDirectory(String dirName) throws IllegalArgumentException {
        if (duplicateName(dirName)) {
            throw new IllegalArgumentException("File: " + dirName + " already exist!");
        }
        Directory newDir = new Directory(dirName, this);
        this.files.add(newDir);
        return newDir;
    }

    protected static Directory createRoot() {
        Directory newDir = new Directory();
        newDir.setName("");
        return newDir;
    }

    protected void deleteFile(String toDelName) throws NoSuchElementException {
        for (File d : files) {
            if (d.name.equals(toDelName)) {
                this.deleteFile(d);
                return;
            }
        }
        throw new NoSuchElementException("The file " + toDelName + "doesn't exist!");
    }

    protected File getParentDirectory() {

        if (this.parentDir == null) {
            throw new NoSuchElementException("Now is at root directory!");
        }
        return this.parentDir;
    }

    protected ArrayList<File> list() {
        ArrayList<File> result = new ArrayList<>(files);
        return result;
    }

    protected ArrayList<File> rList() {
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

    // -----------------Public methods----------------//

    @Override
    public String getFullPath() {
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
    public String toString() {
        return "Directory{" + "type=" + type + ", name='" + name + '\'' + ", parentDir=" + parentDir + ", files="
                + files + '}';
    }

    @Override
    public boolean isDirectory() {
        return true;
    }



    public static void main(String[] args) {
        // latter put it to test folder as Unit test;
    }

}
