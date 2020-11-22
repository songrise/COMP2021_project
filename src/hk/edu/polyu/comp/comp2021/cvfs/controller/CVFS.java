/**
* -*- coding : utf-8 -*-
* @FileName  : CVFS.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-20
* @Github    ：https://github.com/songrise
* @Descriptions: Delegate class, encapsulation of model.
**/

package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Objects;

/**
 * Class for CVFS, it is basically encapsulation of Disk and Criterion, it also provide utility to support undo and redo.
 */
public class CVFS {
    private final ArrayDeque<FileManager> sysUndoStack;
    private final ArrayDeque<FileManager> sysRedoStack;
    private FileManager fm;

    /**
     *
     */
    public CVFS() {
        this.sysUndoStack = new ArrayDeque<>(64);
        this.sysRedoStack = new ArrayDeque<>(64);
        this.fm = new FileManager();
    }

    //
    private Object deepCopy() {
        // Note: this method reference
        // https://www.cnblogs.com/mengdd/archive/2013/02/20/2917971.html

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(fm);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void pushUndoStack() {
        FileManager fmCopy = (FileManager) deepCopy();
        sysUndoStack.push(Objects.requireNonNull(fmCopy));
    }

    private FileManager popUndoStack() {
        if (sysUndoStack.isEmpty()) {
            throw new EmptyStackException();
        } else {
            pushRedoStack();
            return sysUndoStack.pop();
        }
    }

    private void pushRedoStack() {
        FileManager fmCopy = (FileManager) deepCopy();
        sysRedoStack.push(Objects.requireNonNull(fmCopy));
    }

    private FileManager popRedoStack() {
        if (sysRedoStack.isEmpty()) {
            throw new EmptyStackException(); // nothing to redo
        } else {
            sysUndoStack.push((FileManager) Objects.requireNonNull(deepCopy()));
            return sysRedoStack.pop();
        }
    }

    // public
    /**
     * create a new disk of specified size, and change current disk to it
     *
     * @param size: size of disk
     */
    public void newDisk(int size) {
        this.fm.newDisk(size);
    }

    /**
     * create a new document in working directory, the name must be distinct
     *
     * @param docName name of Document
     * @param typeStr name of type
     * @param content content of doc
     */
    public void newDoc(String docName, String typeStr, String content) {
        pushUndoStack();
        this.fm.newDoc(docName, typeStr, content);
    }

    /**
     * create a new dir in working directory, the name must be distinct
     *
     * @param dirName name of new dir
     */
    public void newDir(String dirName) {
        pushUndoStack();
        this.fm.newDir(dirName);
    }

    /**
     * delete the specified file in working directory.Deletion of directory is
     * recursive.
     *
     * @param fileName name of file to delete
     */
    public void delFile(String fileName) {
        pushUndoStack();
        fm.delFile(fileName);
    }

    /**
     * rename the specified file in working directory.
     *
     * @param oldName name of file to rename
     * @param newName new name of file
     */
    public void rename(String oldName, String newName) {
        pushUndoStack();
        fm.rename(oldName, newName);
    }

    /**
     * change current directory to specified dir .
     * @param dirName name of dir, specifically, ".." is the parent dir
     */
    public void changeDir(String dirName) {
        pushUndoStack();
        fm.changeDir(dirName);
    }

    /**
     * @return a ArrayList of all File object contained in this Directory, the order is same as the creation order.
     */
    // TODO incomplete method
    public ArrayList<File> list() {
        return fm.list();
    }

    /**
     * @return A ArrayList of all File object contained in this Directory and its subDir.
     */
    public ArrayList<File> rlist() {

        return fm.rlist();
    }
    /**
     * @param criName  name of criterion, should be two english letters
     * @param attrName name of attribute
     * @param opName   name of operator
     * @param val      name of value
     */
    public void newSimpleCri(String criName, String attrName, String opName, String val) {
        fm.newSimpleCri(criName, attrName, opName, val);
    }
    /**
     * @param thisCriName name of new criterion, should be two english letters
     * @param criNameA name of first cri
     * @param logicOp name of logic operation, including &&, ||, !
     * @param criNameB ame of second cri
     */
    public void newBinaryCri(String thisCriName, String criNameA, String logicOp, String criNameB) {
        fm.newBinaryCri(thisCriName, criNameA, logicOp, criNameB);
    }
    /**
     * @param thisCriName name of the new criterion
     * @param otherCriName The name of Criterion to negate.
     */
    public void newNegation(String thisCriName, String otherCriName) {
        fm.newNegation(thisCriName, otherCriName);
    }


    /**
     * @return A String of all the criteria name, one each line.
     */
    public String printAllCriteria() {
        return fm.getAllCriteria();
    }


    /**
     * @param criName name of criterion
     * @return An ArrayList of all file that satisfy the specified criterion in working dir
     */
    public ArrayList<File> searchByCriterion(String criName) {
        return fm.searchByCriterion(criName);
    }
    /**
     * @param criName name of criterion
     * @return An ArrayList of all file that satisfy the specified criterion in working dir and its sub dir
     */
    public ArrayList<File> rSearchByCriterion(String criName) {
        return fm.rSearchByCriterion(criName);
    }

    /**
     * Store the disk to local file system, the output is binary encoded.
     */
    public void store() {
        this.fm.store();
    }

    /**
     * Load the disk from local file system, the output is binary encoded.
     */

    public void load() {
        pushUndoStack();
        this.fm.load();
    }

    /**
     * undo previous operation
     */
    public void undo() {
        this.fm = popUndoStack();
    }

    /**
     * redo previous operation
     */
    public void redo() {
        this.fm = popRedoStack();
    }

    /**
     * @return name of working dir.
     */
    public String getPath(){
        return fm.getPath();
    }
}
