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

public class CVFS {
    private final ArrayDeque<FileManager> sysUndoStack;
    private final ArrayDeque<FileManager> sysRedoStack;
    private FileManager fm;

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
     * @param docName
     * @param typeStr
     * @param content
     */
    public void newDoc(String docName, String typeStr, String content) {
        pushUndoStack();
        this.fm.newDoc(docName, typeStr, content);
    }

    /**
     * create a new dir in working directory, the name must be distinct
     *
     */
    public void newDir(String dirName) {
        pushUndoStack();
        this.fm.newDir(dirName);
    }

    /**
     * delete the specified file in working directory.Deletion of directory is
     * recursive.
     *
     */
    public void delFile(String fileName) {
        pushUndoStack();
        fm.delFile(fileName);
    }

    /**
     * rename the specified file in working directory.
     *
     */
    public void rename(String oldName, String newName) {
        pushUndoStack();
        fm.rename(oldName, newName);
    }

    /**
     * change current directory to specified dir ".." is the parent dir throw
     * exception when null dirname, cd .. at root, dirName is not name of a
     * directory.
     */
    public void changeDir(String dirName) {
        pushUndoStack();
        fm.changeDir(dirName);
    }

    // TODO incomplete method
    public ArrayList<File> list() {
        return fm.list();
    }

    // TODO incomplete method, a wrapper is needed for indentation
    public ArrayList<File> rlist() {

        return fm.rlist();
    }

    public void newSimpleCri(String criName, String attrName, String opName, String val) {
        fm.newSimpleCri(criName, attrName, opName, val);
    }

    public void newBinaryCri(String thisCriName, String criNameA, String logicOp, String criNameB) {
        fm.newBinaryCri(thisCriName, criNameA, logicOp, criNameB);
    }

    public void newNegation(String thisCriName, String otherCriName) {
        fm.newNegation(thisCriName, otherCriName);
    }

    public String printAllCriteria() {
        return fm.getAllCriteria();
    }

    public ArrayList<File> searchByCriterion(String criName) {
        return fm.searchByCriterion(criName);
    }

    public ArrayList<File> rSearchByCriterion(String criName) {
        return fm.searchByCriterion(criName);
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

    public String getPath(){
        return fm.getPath();
    }
}
