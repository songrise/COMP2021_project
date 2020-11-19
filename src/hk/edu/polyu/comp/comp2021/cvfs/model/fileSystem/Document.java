package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;

import java.util.ArrayDeque;

import java.util.NoSuchElementException;

public class Document extends File {

    private static final long serialVersionUID = 2021L;
    private final Directory parentDir;
    private String content;

    // -----------------Constructor----------------//

    Document(String fileName, String typeStr, String fileContent, Directory dir) {
        super(fileName, typeStr);
        this.setContent(fileContent);
        this.parentDir = dir;
    }

    // -----------------Private methods----------------//

    // -----------------Public methods----------------//
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        // content cannot be null, but it can be empty.
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null!");
        }
        this.content = content;
    }

    @Override
    public int getSize() {
        return 40 + content.length() * 2;
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

    protected File getParentDirectory() {

        if (this.parentDir == null) {
            throw new NoSuchElementException("Now is at root directory!");
        }
        return this.parentDir;
    }

    @Override
    public String toString() {
        return "Document{" + "type=" + this.getType() + ", name='" + this.getName() + '\'' + ", content='" + content
                + '\'' + ", directory=" + parentDir + '}';
    }
}