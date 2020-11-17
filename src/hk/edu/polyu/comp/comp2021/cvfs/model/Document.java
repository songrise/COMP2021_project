package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.NoSuchElementException;

import hk.edu.polyu.comp.comp2021.cvfs.model.Directory;

public class Document extends File {

    private static final long serialVersionUID = 2021L;
    private FileType type;
    private String name;
    private String content;
    private Directory directory;

    // -----------------Constructor----------------//
    protected Document(Directory dir) {
        this.directory = dir;
    }

    protected Document(String fileName, String typeStr, String fileContent, Directory dir) {
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

    // -----------------Public methods----------------//

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public String getFullPath() {
        ArrayDeque<String> stack = new ArrayDeque<>();
        Directory crtDir = (Directory) this.getParentDirectory();
        while (crtDir != null) {
            stack.push(crtDir.getName());
            try {
                crtDir = (Directory) crtDir.getParentDirectory();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        StringBuilder sb = new StringBuilder(".");
        while (!stack.isEmpty()) {
            sb.append(stack.pop() + "/");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Document{" + "type=" + type + ", name='" + name + '\'' + ", content='" + content + '\'' + ", directory="
                + directory + '}';
    }

    protected File getParentDirectory() {

        if (this.directory == null) {
            throw new NoSuchElementException("Now is at root directory!");
        }
        return this.directory;
    }

    public static void main(String[] args) {
        // latter put it to test folder as Unit test;
    }

}