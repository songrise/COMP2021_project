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
import java.util.Objects;

/**
 * Class for CVFS,namly (Polyu) COMP VFS, it implementes VFS interface.
 * it is basically an encapsulation of Disk and Criterion.
 */
public class CVFS implements VFS {
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
            throw new IllegalStateException("No operation to undo!");
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
            throw new IllegalStateException("No operation to redo!"); // nothing to redo
        } else {
            sysUndoStack.push((FileManager) Objects.requireNonNull(deepCopy()));
            return sysRedoStack.pop();
        }
    }

    // public

    @Override
    public void newDisk(int size) {
        this.fm.newDisk(size);
    }

    @Override
    public void newDoc(String docName, String typeStr, String content) {
        pushUndoStack();
        this.fm.newDoc(docName, typeStr, content);
    }

    @Override
    public void newDir(String dirName) {
        pushUndoStack();
        this.fm.newDir(dirName);
    }

    @Override
    public void delFile(String fileName) {
        pushUndoStack();
        fm.delFile(fileName);
    }

    @Override
    public void rename(String oldName, String newName) {
        pushUndoStack();
        fm.rename(oldName, newName);
    }

    @Override
    public void changeDir(String dirName) {
        pushUndoStack();
        fm.changeDir(dirName);
    }

    @Override
    public ArrayList<File> list() {
        return fm.list();
    }

    @Override
    public ArrayList<File> rlist() {

        return fm.rlist();
    }

    @Override
    public void newSimpleCri(String criName, String attrName, String opName, String val) {
        pushUndoStack();
        fm.newSimpleCri(criName, attrName, opName, val);
    }

    @Override
    public void newBinaryCri(String thisCriName, String criNameA, String logicOp, String criNameB) {
        pushUndoStack();
        fm.newBinaryCri(thisCriName, criNameA, logicOp, criNameB);
    }

    @Override
    public void newNegation(String thisCriName, String otherCriName) {
        pushUndoStack();
        fm.newNegation(thisCriName, otherCriName);
    }

    @Override
    public String printAllCriteria() {
        return fm.getAllCriteria();
    }

    @Override
    public ArrayList<File> searchByCriterion(String criName) {
        return fm.searchByCriterion(criName);
    }

    @Override
    public ArrayList<File> rSearchByCriterion(String criName) {
        return fm.rSearchByCriterion(criName);
    }

    @Override
    public void store() {
        this.fm.store();
    }

    @Override
    public void load() {
        pushUndoStack();
        this.fm.load();
    }

    @Override
    public void undo() {
        this.fm = popUndoStack();
    }

    @Override
    public void redo() {
        this.fm = popRedoStack();
    }

    @Override
    public String getPath() {
        return fm.getPath();
    }
}
