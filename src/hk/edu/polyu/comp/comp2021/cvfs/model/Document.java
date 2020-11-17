package hk.edu.polyu.comp.comp2021.cvfs.model;


import java.util.ArrayDeque;

import java.util.NoSuchElementException;


public class Document extends File {

    private static final long serialVersionUID = 2021L;
    private final Directory directory;
    private FileType type;
    private String name;
    private String content;

    // -----------------Constructor----------------//
    private Document(Directory dir) {
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

    public void setContent(String content) {
        this.content = content;
    }

    // -----------------Public methods----------------//

    public int getSize() {
        return 40 + content.length() * 2;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

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
            sb.append(stack.pop()).append("/");
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

}