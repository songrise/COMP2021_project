/* Encapsulation of criteria and disk, support for undo and redo
 * -*- coding : utf-8 -*-
 *
 * @FileName : FileManager.java
 * @Author : Ruixiang JIANG (Songrise)
 * @Time : 2020-11-17
 * @Github ：https://github.com/songrise
 * 
 **/

package hk.edu.polyu.comp.comp2021.cvfs.controller;

import java.io.*;
import java.util.ArrayList;

import hk.edu.polyu.comp.comp2021.cvfs.model.criterion.*;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.ConcreteDisk;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.Disk;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

/**
 * encapsulation of Disk, also provided utility to implement store() and load().
 */
public class FileManager implements Serializable {
    private static final long serialVersionUID = 2021L;
    private final CriterionList criteria;
    private Disk crtDisk;

    /**
     *
     */
    public FileManager() {
        final int DEFAULT_SIZE = 255;
        this.newDisk(DEFAULT_SIZE);
        this.criteria = new CriterionList();

    }


    // -----------------Private methods----------------//
    private void writeSerializable() {// for store propose
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("storedCVFS.CVFS"));
            oos.writeObject(crtDisk);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSerializable() {// for load propose
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("storedCVFS.CVFS"));
            crtDisk = (Disk) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


    // -----------------Public methods----------------//

    /**
     * create a new disk of specified size, and change current disk to it
     *
     * @param size: size of disk
     */
    public void newDisk(int size) {
        this.crtDisk = new ConcreteDisk(size);
    }

    /**
     * create a new document in working directory, the name must be distinct
     *
     * @param docName name of Document
     * @param typeStr name of type
     * @param content content of doc
     */
    public void newDoc(String docName, String typeStr, String content) {
        this.crtDisk.makeDocument(docName, typeStr, content);
    }

    /**
     * create a new dir in working directory, the name must be distinct
     *
     * @param dirName name of new dir
     */
    public void newDir(String dirName) {

        crtDisk.makeDir(dirName);
    }

    /**
     * delete the specified file in working directory.Deletion of directory is
     * recursive.
     *
     * @param fileName name of file to delete
     */
    public void delFile(String fileName) {

        crtDisk.deleteFile(fileName);
    }

    /**
     * rename the specified file in working directory.
     *
     * @param oldName name of file to rename
     * @param newName new name of file
     */
    public void rename(String oldName, String newName) {

        crtDisk.rename(oldName, newName);
    }

    /**
     * change current directory to specified dir .
     * @param dirName name of dir, specifically, ".." is the parent dir
     */
    public void changeDir(String dirName) {

        crtDisk.changeDir(dirName);
    }

    /**
     * @return a ArrayList of all File object contained in this Directory, the order is same as the creation order.
     */
    public ArrayList<File> list() {
        return crtDisk.list();
    }

    /**
     * @return A ArrayList of all File object contained in this Directory and its subDir.
     */
    public ArrayList<File> rlist() {

        return crtDisk.rList();
    }

    /**
     * Store the disk to local file system, the output is binary encoded.
     */
    public void store() {
        writeSerializable();
    }

    /**
     * Load the disk from local file system, the output is binary encoded.
     */

    public void load() {

        readSerializable();
    }
    /**
     * @param criName  name of criterion, should be two english letters
     * @param attrName name of attribute
     * @param opName   name of operator
     * @param val      name of value
     */
    public void newSimpleCri(String criName, String attrName, String opName, String val) {
        Criterion cri = new SimpleCriterion(criName, attrName, opName, val);
        criteria.addCriterion(cri);
    }
    /**
     * @param thisCriName name of new criterion, should be two english letters
     * @param criNameA name of first cri
     * @param logicOp name of logic operation, including &&, ||, !
     * @param criNameB ame of second cri
     */
    public void newBinaryCri(String thisCriName, String criNameA, String logicOp, String criNameB) {
        Criterion criA = criteria.findCriterion(criNameA);
        Criterion criB = criteria.findCriterion(criNameB);
        Criterion newCri = new BinaryCriterion(thisCriName, criA, logicOp, criB);
        this.criteria.addCriterion(newCri);
    }
    /**
     * @param thisCriName name of the new criterion
     * @param otherCriName The name of Criterion to negate.
     */
    public void newNegation(String thisCriName, String otherCriName) {
        Criterion cri = criteria.findCriterion(otherCriName);
        Criterion negate = new NegationCriterion(thisCriName, cri);
        criteria.addCriterion(negate);
    }
    /**
     * @return A String of all the criteria name, one each line.
     */
    public String getAllCriteria() {
        return criteria.getAllCriteria();
    }

    /**
     * @param criName name of criterion
     * @return An ArrayList of all file that satisfy the specified criterion in working dir
     */
    public ArrayList<File> searchByCriterion(String criName) {
        Criterion cri = criteria.findCriterion(criName);
        ArrayList<File> matched = new ArrayList<>();
        for (File f : this.list()) {
            if (cri.eval(f)) {
                matched.add(f);
            }
        }
        return matched;
    }
    /**
     * @param criName name of criterion
     * @return An ArrayList of all file that satisfy the specified criterion in working dir and its sub dir
     */
    public ArrayList<File> rSearchByCriterion(String criName) {
        Criterion cri = criteria.findCriterion(criName);
        ArrayList<File> matched = new ArrayList<>();
        for (File f : this.rlist()) {
            if (cri.eval(f)) {
                matched.add(f);
            }
        }
        return matched;
    }

    /**
     * @return full path of working dir
     */
    // -----------------Auxiliary Public methods----------------//
    public String getPath(){
        return crtDisk.getWorkingDir().getFullPath();
    }

}
