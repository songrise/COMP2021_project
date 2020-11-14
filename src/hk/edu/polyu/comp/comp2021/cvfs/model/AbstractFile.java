/**
* -*- coding : utf-8 -*-
* @FileName  : AbstractFile.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-15
* @Github    ：https://github.com/songrise
* @Descriptions: Interface for the a general file
**/

package hk.edu.polyu.comp.comp2021.cvfs.model;

public interface AbstractFile {

    /**
     * 
     * @return name of this file
     */
    public String getName();

    /**
     * 
     * @return true if this file is a directory.
     */
    public boolean isDirectory();

    public AbstractFile getParentDirectory();

    public String getFullPath();

    public void setName(String fileName);

    /**
     * 
     * @return A AbstractFile reference, which is the newly created file.
     */
    public AbstractFile createFile(String fileName);

    /**
     * 
     * delete this file.
     */
    public void deleteFile();
}
