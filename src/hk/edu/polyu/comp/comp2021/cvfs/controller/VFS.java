package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

import java.util.ArrayList;

/**
 * Interface for a VFS (Virtual File System)
 */
public interface VFS {
    /**
     * create a new disk of specified size, and change current disk to it
     *
     * @param size: size of disk
     */
    void newDisk(int size);

    /**
     * create a new document in working directory, the name must be distinct
     *
     * @param docName name of Document
     * @param typeStr name of type
     * @param content content of doc
     */
    void newDoc(String docName, String typeStr, String content);
    /**
     * create a new dir in working directory, the name must be distinct
     *
     * @param dirName name of new dir
     */
    void newDir(String dirName);

    /**
     * delete the specified file in working directory.Deletion of directory is
     * recursive.
     *
     * @param fileName name of file to delete
     */
    void delFile(String fileName);
    /**
     * rename the specified file in working directory.
     *
     * @param oldName name of file to rename
     * @param newName new name of file
     */
    void rename(String oldName, String newName);
    /**
     * change current directory to specified dir .
     * @param dirName name of dir, specifically, ".." is the parent dir
     */
    void changeDir(String dirName);


    /**
     * @return a ArrayList of all File object contained in this Directory, the order is same as the creation order.
     */
    ArrayList<File> list();
    /**
     * @return A ArrayList of all File object contained in this Directory and its subDir.
     */
    ArrayList<File> rlist();
    /**
     * @param criName  name of criterion, should be two english letters
     * @param attrName name of attribute
     * @param opName   name of operator
     * @param val      name of value
     */
    void newSimpleCri(String criName, String attrName, String opName, String val);
    /**
     * @param thisCriName name of new criterion, should be two english letters
     * @param criNameA    name of first cri
     * @param logicOp     name of logic operation, including &&, ||, !
     * @param criNameB    ame of second cri
     */
    void newBinaryCri(String thisCriName, String criNameA, String logicOp, String criNameB);
    /**
     * @param thisCriName  name of the new criterion
     * @param otherCriName The name of Criterion to negate.
     */
    void newNegation(String thisCriName, String otherCriName);
    /**
     * @return A String of all the criteria name, one each line.
     */
    String printAllCriteria();
    /**
     * @param criName name of criterion
     * @return An ArrayList of all file that satisfy the specified criterion in
     *         working dir
     */
    ArrayList<File> searchByCriterion(String criName);
    /**
     * @param criName name of criterion
     * @return An ArrayList of all file that satisfy the specified criterion in
     *         working dir and its sub dir
     */
    ArrayList<File> rSearchByCriterion(String criName);

    /**
     * Store the disk to local file system, the output is binary encoded.
     */
    void store();
    /**
     * Load the disk from local file system, the output is binary encoded.
     */
    void load();
    /**
     * undo previous operation
     */
    void undo();
    /**
     * redo previous operation
     */
    void redo();
    /**
     * @return name of working dir.
     */
    String getPath();
}
