package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;

import java.util.ArrayDeque;

import java.util.NoSuchElementException;

/**
 * class for documents
 */
public class Document extends ConcreteFile {

    private static final long serialVersionUID = 2021L;
    private final Directory parentDir;
    private String content;

    // -----------------Constructor----------------//

    /**
     * This constructer should only be used by a Directory object.
     * @param docName name of Document
     * @param typeStr name of type
     * @param fileContent content of doc
     * @param dir Parent directory of this file
     */
    Document(String docName, String typeStr, String fileContent, Directory dir) {
        super(docName, typeStr);
        this.setContent(fileContent);
        this.parentDir = dir;
    }



    /**
     * @return parentDirectory of this Document. A document must have its parent dir.
     */
    Directory getParentDirectory() {
        return this.parentDir;
    }


    /**
     * @return content in this file
     */
    // -----------------Public methods----------------//
    public String getContent() {
        return this.content;
    }

    /**
     * @param content content
     * @throws IllegalArgumentException if content is null
     */
    public void setContent(String content) throws IllegalArgumentException{
        // content cannot be null, but it can be empty.
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null!");
        }
        this.content = content;
    }

    @Override
    public int getSize() {
        final int fileHeaderSize = 40;
        return fileHeaderSize + content.length() * 2;
    }


    @Override
    public String toString() {
        return "Document{" + "name=" + this.getFullName()+"}";
    }
}