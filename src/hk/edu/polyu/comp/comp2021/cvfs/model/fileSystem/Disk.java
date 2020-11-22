
package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;

import java.util.ArrayList;

/**
 * Interface for Disk, as higher level of encapsulation of File
 */
public interface Disk {
    /**
     * @param name name of file
     * @return the found File
     */
    File findFile(String name);

    /**
     * @param docName name of Document
     * @param typeStr name of type
     * @param content content of doc
     */
    void makeDocument(String docName, String typeStr, String content);
    /**
     * @return a ArrayList of all File object contained in this Directory, the order is same as the creation order.
     */
    ArrayList<File> list();
    /**
     * @return A ArrayList of all File object contained in this Directory and its subDir.
     */
    ArrayList<File> rList();

    /**
     * @param newDirName the name of new directory
     */
    void changeDir(String newDirName);

    /**
     * @return working dir of the disk
     */
    Directory getWorkingDir();

    /**
     * @return name of working Dir
     */
    String getWorkingDirName();

    /**
     * @return capacity of this disk
     */
    int getCapacity();

    /**
     * @param dirName name of new dir
     */
    void makeDir(String dirName);

    /**
     * @param fileName name of dir to be deleted
     */
    void deleteFile(String fileName);

    /**
     * @param oldName the name of file to be renamed
     * @param newName new name of file
     */
    void rename(String oldName, String newName);
}
