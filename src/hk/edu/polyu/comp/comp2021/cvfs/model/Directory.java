/**
* -*- coding : utf-8 -*-
* @FileName  : Directory.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-15
* @Github    ：https://github.com/songrise
* @Descriptions: Directory class
**/

package src.hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.ArrayList;
import java.util.HashSet;

import hk.edu.polyu.comp.comp2021.cvfs.model.AbstractFile;

public class Directory implements AbstractFile {

    // -----------------Filed----------------//

    private final FileType type; // type of directory cannot be modified.
    private String name;
    private Directory parentDir;
    ArrayList<Directory> subDir;
    ArrayList<Document> docs;

    // -----------------Constructor----------------//
    Directory() {// when this constructor is called, this dir is the root dir
        this.name = "unnamed";
        this.type = FileType.initType("DIR");
        parentDir = null;
        subDir = new ArrayList<Directory>();
        docs = new ArrayList<Document>();
    }

    Directory(Directory parent) {
        this();
        parentDir = parent;
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

    /**
     * 
     * @return A AbstractFile reference, which is the newly created file.
     */
    public AbstractFile createFile() {

    }

    /**
     * 
     * delete this file. Deletion of a directory will recursively delete all files
     * inside this dir.
     */
    public void deleteFile() {

    }

    @Override
    public boolean isDirectory() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public src.hk.edu.polyu.comp.comp2021.cvfs.model.AbstractFile getParentDirectory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFullPath() {
        // TODO Auto-generated method stub
        return null;
    }

}
