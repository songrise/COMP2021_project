package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;

import java.util.ArrayList;

public interface Disk {
    // -----------------Public methods----------------//
    File findFile(String name);

    void makeDocument(String docName, String typeStr, String content);

    ArrayList<File> list();

    ArrayList<File> rList();

    void changeDir(String newDirName);

    Directory getWorkingDir();

    String getWorkingDirName();

    int getCapacity();

    void makeDir(String dirName);

    void deleteFile(String fileName);

    void rename(String oldName, String newName);
}
