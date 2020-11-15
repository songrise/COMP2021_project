package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;
import java.util.HashSet;

public class Document extends File {

    private static final long serialVersionUID = 2021L;
    private FileType type;
    private String name;
    private String content;
    private Directory directory;

    // -----------------Constructor----------------//
    Document(Directory dir) {
        this.directory = dir;
    }

    Document(String fileName, String typeStr, String fileContent, Directory dir) {
        this.setName(fileName);
        this.setType(typeStr);
        this.setContent(fileContent);
        this.directory = dir;
    }

    // -----------------Private methods----------------//

    private String getContent() {
        return this.content;
    }

    public int getSize() {
        return 40 + content.length() * 2;
    }

    private void setContent(String content) {
        this.content = content;
    }

    // -----------------Public methods----------------//

    @Override
    public boolean isDirectory() {
        return false;
    }

    public static void main(String[] args) {
         //latter put it to test folder as Unit test;
    }

}