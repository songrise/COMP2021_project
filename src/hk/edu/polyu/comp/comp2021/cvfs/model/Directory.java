/**
* -*- coding : utf-8 -*-
* @FileName  : Directory.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-15
* @Github    ：https://github.com/songrise
* @Descriptions: Directory class
**/

package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;
import java.util.*;

import hk.edu.polyu.comp.comp2021.cvfs.model.AbstractFile;

public class Directory implements AbstractFile, Serializable {

    // -----------------field ----------------//

    private static final long serialVersionUID = 2021L;
    private final FileType type; // type of directory cannot be modified.
    private String name;
    private Directory parentDir;
    ArrayList<AbstractFile> files;

    // -----------------Constructor----------------//
    private Directory() {// when this constructor is called, this dir is the root dir
        this.setName("unnamed");
        this.type = FileType.initType("DIR");
        parentDir = null;
        files = new ArrayList<AbstractFile>();
    }

    private Directory(String name) {
        this();
        this.setName(name);
    }

    private Directory(String name, Directory parent) {
        this(name);
        parentDir = parent;
    }

    @Override
    public AbstractFile createFile(String fileName) {
        // TODO Auto-generated method stub
        Document newDoc = new Document();
        this.files.add(newDoc);
        return newDoc;
    }

    // -----------------Private methods----------------//

    // -----------------Protected methods----------------//
    protected Directory createDirectory(String dirName) {
        // TODO Auto-generated method stub
        Directory newDir = new Directory(dirName, this);
        this.files.add(newDir);
        return newDir;
    }

    protected static Directory createRoot() {
        Directory newDir = new Directory();
        newDir.setName("");
        return newDir;
    }

    /**
     *
     * delete this file. Deletion of a directory will recursively delete all files
     * inside this dir.
     */
    public void deleteFile() {
        Directory parent = parentDir;
        if (parent.files.contains(this))
            parent.files.remove(this);
    }

    /**
     *
     * delete the specified AbstractFile Object in current directory. Deletion of a
     * directory will recursively delete all files inside this dir.
     */
    protected void deleteFile(AbstractFile toDel) {
        toDel.deleteFile();
    }
    // -----------------Public methods----------------//

    /**
     * 
     * @param nameStr
     * @throws IllegalArgumentException
     */
    public void setName(String nameStr) throws IllegalArgumentException {
        if (nameStr == null) {
            throw new IllegalArgumentException();
        } else if (nameStr.length() > 10) {
            throw new IllegalArgumentException("Name of file longer than 10");
        } else {
            HashSet<Character> allowedCH = new HashSet<Character>() {// initialize a valid character set.
                private static final long serialVersionUID = 2021L;
                {
                    for (char c = 'a', C = 'A'; c <= 'z'; c++, C++) {
                        add(c);
                        add(C);
                    }
                    for (int i = 0; i < 10; i++) {
                        add(Integer.toString(i).charAt(0));
                    }
                }
            };
            for (char ch : nameStr.toCharArray()) {
                if (!allowedCH.contains(ch)) {
                    throw new IllegalArgumentException("Illegal character in file name: " + ch);
                }
            }
        }

        // when this line is reached, the file name is valid.
        this.name = nameStr;
    }

    /**
     * 
     * @return name of this file
     */
    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.name + "/";
    }

    public void deleteFile(String toDelName) throws NoSuchElementException {
        for (Directory d : files) {
            if (d.name == toDelName) {
                this.deleteFile(d);
                return;
            }
        }
        throw new NoSuchElementException("The file " + toDelName + "doesn't exist!");
    }

    public ArrayList<AbstractFile> list() {
        ArrayList<AbstractFile> result = new ArrayList<>();
        for (AbstractFile document : files) {
            result.add(document);
        }
        return result;
    }

    public ArrayList<String> rList() {
        ArrayList<String> result = new ArrayList<>();
        ArrayDeque<AbstractFile> stack = new ArrayDeque<>();
        for (AbstractFile document : files) {
            result.add(document.getName());
        }
        return result;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public AbstractFile getParentDirectory() {
        return parentDir;
    }

    @Override
    public String getFullPath() {
        ArrayDeque<String> stack = new ArrayDeque<>();
        Directory crtDir = this;
        while (crtDir != null) {
            stack.push(crtDir.getName());
            crtDir = crtDir.parentDir;
        }
        StringBuilder sb = new StringBuilder("./");
        while (!stack.isEmpty()) {
            sb.append(stack.pop() + "/");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Directory otherDir = (Directory) o;
        return this.getFullName().equals(otherDir.getFullName()) && this.getFullPath().equals(otherDir.getFullPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, parentDir);
    }
}
