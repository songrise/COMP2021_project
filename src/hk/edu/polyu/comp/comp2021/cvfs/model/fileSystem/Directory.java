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

/**
 * Class for directory
 */
public final class Directory extends ConcreteFile {

    // -----------------field ----------------//

    private static final long serialVersionUID = 2021L;
    private Directory parentDir;
    private final ArrayList<File> files;

    // -----------------Constructor----------------//
    private Directory() {// when this constructor is called, this dir is the root dir
        super("DIR");
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
     * @param fileName name to check
     * @return true if name already used
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

    /**
     * create a new document with specified name,type,content in this directory
     * @param docName name of Document
     * @param typeStr name of type
     * @param content content of doc
     * @throws IllegalArgumentException if the name is already used.
     */
    // -----------------Package-private methods----------------//
    void createDocument(String docName, String typeStr, String content) throws IllegalArgumentException {
        if (nameAlreadyExists(docName)) {
            throw new IllegalArgumentException("File: " + docName + " already exist!");
        }
        Document newDoc = new Document(docName, typeStr, content, this);
        this.files.add(0,newDoc);

    }

    /**
     * create a new directory with specified name in this directory
     * @param dirName name of Dir
     * @throws IllegalArgumentException if the name is already used.
     */
    void createDirectory(String dirName) throws IllegalArgumentException {
        if (nameAlreadyExists(dirName)) {
            throw new IllegalArgumentException("File: " + dirName + " already exist!");
        }
        Directory newDir = new Directory(dirName, this);
        this.files.add(files.size(),newDir);
    }

    /**
     * Factory method for creating a root dir.
     * 
     * @param toDelName name of file to delete
     * @throws NoSuchElementException if the file not found
     */


    void deleteFile(String toDelName) throws NoSuchElementException {
        File fileToDel = this.findFile(toDelName);
        this.deleteFile(fileToDel);
    }

    /**
     * @return the parent dir of this Directory object.
     * @throws NoSuchElementException if no parent directory (ususally at root)
     */
    Directory getParentDirectory() throws NoSuchElementException{
        if (this.parentDir == null) {
            throw new NoSuchElementException("Now is at root directory!");
        }
        return this.parentDir;
    }

    /**
     * @return a ArrayList of all File object contained in this Directory, the order is same as the creation order.
     */
    ArrayList<File> list() {
        return new ArrayList<>(files);
    }

    /**
     * @return A ArrayList of all File object contained in this Directory and its subDir.
     */
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

    /**
     * @param fileName name of the File to be found
     * @return the File object
     * @throws NoSuchElementException if no File named fileName.
     */
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

    /**
     * @param oldName the name of file to be renamed
     * @param newName new name of file
     */
    void renameFile(String oldName, String newName) {
        File fileToRename = findFile(oldName);
        fileToRename.setName(newName);
    }

    /**
     * @return a Directory object whose parent Dir is null;
     */
    // -----------------Public methods----------------//
    public static Directory createRoot() {
        return new Directory();
    }

    /**
     * @return full path of this Dir.
     */
    public String getFullPath() {
        ArrayDeque<String> stack = new ArrayDeque<>();
        Directory crtDir = this;
        while (crtDir != null) {
            stack.push(crtDir.getName());
            try {
                crtDir = crtDir.getParentDirectory();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
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
        return "Directory{"  + "name=" + this.getFullName()+"}";
    }


}
