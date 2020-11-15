package src.hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.HashSet;

public class Document implements AbstractFile, Serializable {

    private static final long serialVersionUID = 2021L;
    private FileType type;
    private String name;
    private String content;
    private Directory directory;

    // -----------------Constructor----------------//
    Document(Directory dir) {
        this();
        this.directory = dir;
    }

    Document(String fileName, FileType type, String fileContent) {
        setName(fileName);
        setType(type.toString());
        setContent(fileContent);
        directory = null;

    }

    // -----------------Private methods----------------//

    private String getContent() {
        return this.content;
    }

    private int getSize() {
        return content.length();
    }

    private Directory getDirectory() {
        return this.directory;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void renameFile(String oldName, String newName) {

    }

    // -----------------Public methods----------------//

    public void setName(String nameStr) throws IllegalArgumentException {
        if (nameStr == null) {
            throw new IllegalArgumentException();
        } else if (nameStr.length() > 10) {
            throw new IllegalArgumentException("Name of file longer than 10");
        } else {
            HashSet<Character> allowedCH = new HashSet<Character>() {// initialize a valid character set.
                {
                    for (char c = 'a', C = 'A'; c <= 'z'; c++, C++) {
                        add(c);
                        add(C);
                    }
                    for (int i = 0; i < 10; i++) {
                        add(Integer.toString(i).charAt(0));
                    }
                }
            };

            for (char ch : nameStr.toCharArray()) {
                if (!allowedCH.contains(ch)) {
                    throw new IllegalArgumentException("Illegal character in file name: " + ch);
                }
            }
        }
        // when this line is reached, the file name is valid.
        this.name = nameStr;
    }

    public void setType(String typeStr) throws IllegalArgumentException {
        if (typeStr == null) {
            throw new IllegalArgumentException();
        } else {
            String t = typeStr.toUpperCase();
            if (!(t.equals("TXT") && t.equals("JAVA") && t.equals("HTML") && t.equals("CSS")))
                throw new IllegalArgumentException("Invalid type name!");
            else {
                this.type = FileType.initType(typeStr);
            }
        }
    }

    /**
     * 
     * @return name of this file
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return the type of this file
     */
    public FileType getType() {
        return this.type;
    }

    /**
     * 
     * delete this file.
     */
    public void deleteFile() {

    }

    @Override
    public boolean isDirectory() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public src.hk.edu.polyu.comp.comp2021.cvfs.model.AbstractFile getParentDirectory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFullPath() {
        // TODO Auto-generated method stub
        return getDirectory().getFullName() + getName();
    }

    @Override
    public src.hk.edu.polyu.comp.comp2021.cvfs.model.AbstractFile createFile(String fileName) {
        // TODO Auto-generated method stub
        return null;
    }
}