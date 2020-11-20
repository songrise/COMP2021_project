/**
 * -*- coding : utf-8 -*-
 *
 * @FileName : FileManager.java
 * @Author : Ruixiang JIANG (Songrise)
 * @Time : 2020-11-17
 * @Github ：https://github.com/songrise
 * @Descriptions: Encapsulation of criteria and disk.
 **/

package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.*;
import java.util.ArrayList;

import hk.edu.polyu.comp.comp2021.cvfs.controller.criterion.*;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.ConcreteDisk;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.Disk;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

public class FileManager implements Serializable {
    private static final long serialVersionUID = 2021L;
    private final CriterionList criteria;
    private Disk crtDisk;

    public FileManager() {
        this.newDisk(255);
        this.criteria = new CriterionList();

    }

    public FileManager(int capacity) {// initialize with specified storage.
        this();
        this.newDisk(capacity);
    }

    // -----------------Private methods----------------//
    private void writeSerializable(String fileName) {// for store propose
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(crtDisk);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSerializable(String fileName) {// for load propose
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            crtDisk = (Disk) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return a deep copy of the current disk object
     */
    private Object deepCopy() {
        // Note: this method reference
        // https://www.cnblogs.com/mengdd/archive/2013/02/20/2917971.html

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(crtDisk);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // -----------------Public methods----------------//

    /**
     * create a new disk of specified size, and change current disk to it
     *
     * @param size
     */
    public void newDisk(int size) {
        this.crtDisk = new ConcreteDisk(size);
    }

    /**
     * create a new document in working directory, the name must be distinct
     *
     * @param docName
     * @param typeStr
     * @param content
     */
    public void newDoc(String docName, String typeStr, String content) {
        this.crtDisk.makeDocument(docName, typeStr, content);
    }

    /**
     * create a new dir in working directory, the name must be distinct
     *
     */
    public void newDir(String dirName) {

        crtDisk.makeDir(dirName);
    }

    /**
     * delete the specified file in working directory.Deletion of directory is
     * recursive.
     *
     */
    public void delFile(String fileName) {

        crtDisk.deleteFile(fileName);
    }

    /**
     * rename the specified file in working directory.
     *
     */
    public void rename(String oldName, String newName) {

        crtDisk.rename(oldName, newName);
    }

    /**
     * change current directory to specified dir ".." is the parent dir throw
     * exception when null dirname, cd .. at root, dirName is not name of a
     * directory.
     */
    public void changeDir(String dirName) {

        crtDisk.changeDir(dirName);
    }

    // TODO incomplete method
    public ArrayList<File> list() {
        return crtDisk.list();
    }

    // TODO incomplete method, a wrapper is needed for indentation
    public ArrayList<File> rlist() {

        return crtDisk.rList();
    }

    /**
     * Store the disk to local file system, the output is binary encoded.
     */
    public void store() {
        writeSerializable("storedCVFS.CVFS");
    }

    /**
     * Load the disk from local file system, the output is binary encoded.
     */

    public void load() {

        readSerializable("storedCVFS.CVFS");
    }



    public void newSimpleCri(String criName, String attrName, String opName, String val) {
        Criterion cri = new SimpleCriterion(criName, attrName, opName, val);
        criteria.addCriterion(cri);
    }

    public void newBinaryCri(String thisCriName, String criNameA, String criNameB, String logicOp) {
        Criterion criA = criteria.findCriterion(criNameA);
        Criterion criB = criteria.findCriterion(criNameB);
        Criterion newCri = new CompositeCriterion(thisCriName, logicOp, criA, criB);
        this.criteria.addCriterion(newCri);
    }

    public void newNegation(String thisCriName, String otherCriName) {
        Criterion cri = criteria.findCriterion(otherCriName);
        Criterion negate = new NegationCriterion(thisCriName, cri);
        criteria.addCriterion(negate);
    }

    public String getAllCriteria() {
        return criteria.getAllCriteria();
    }

    public boolean meetCriterion(String criName, String fileName) {
        Criterion cri = criteria.findCriterion(criName);
        hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File f = this.crtDisk.findFile(fileName);
        return cri.eval(f);
    }

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

}
