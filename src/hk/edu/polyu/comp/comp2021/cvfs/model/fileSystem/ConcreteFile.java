package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;

import java.io.Serializable;

/**
 * Implemetens File interface
 */
public class ConcreteFile implements File, Serializable {
    private static final long serialVersionUID = 2021L;
    private String name;
    private FileType type;

    /**
     * This is used to instantiate a root dir
     * @param type name of type
     */
    // -----------------Constructor----------------//
    ConcreteFile(String type){
        this.name = "";
        this.setType(type);
    }


    /**
     * @param name name of the file
     * @param type name of type
     */
    ConcreteFile(String name, String type) {
        this.setName(name);
        this.setType(type);
    }

    // -----------------Private methods----------------//
    private boolean isAllowedInName(char ch) {
        return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || ('1' <= ch && ch <= '9');
    }

    @SuppressWarnings("SameReturnValue")
    private boolean isValidName(String nameStr) {
        final int MAXLEN = 10;
        if (nameStr == null||nameStr.length() == 0) {
            throw new IllegalArgumentException("Name of file cannot be empty");
        } else if (nameStr.length() > MAXLEN) {
            throw new IllegalArgumentException("Name of file cannot longer than 10");
        } else {
            for (char ch : nameStr.toCharArray()) {
                if (!isAllowedInName(ch)) {
                    throw new IllegalArgumentException("Illegal character found in file name: " + ch);
                }
            }
        }
        return true;
    }

    private void setType(String typeStr) {
        this.type = FileType.initType(typeStr);
    }

    // -----------------Public methods----------------//
    /**
     * @return name of this file
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getType() {
        return this.type.toString();
    }

    @Override
    public int getSize() {
        return 0;
    }

    /**
     * @return full name of this file e.g. test.txt
     */
    @Override
    public String getFullName() {
        return this.getName()+"."+this.getType();
    }

    /**
     * @param nameStr name Of file, excluding extension name
     * @throws IllegalArgumentException if name is invalid
     */
    @Override
    public void setName(String nameStr) throws IllegalArgumentException {
        if (isValidName(nameStr))
            this.name = nameStr;
    }


    /**
     * @return true if this file is a directory.
     */
    @Override
    public boolean isDirectory() {
        return this.type.isDirectory();
    }

    @Override
    public String toString() {
        return "File{" + "name='" + name + '\'' + ", type=" + type + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        File otherFile = (File) obj;
        return this.getName().equals(otherFile.getName());
    }
}
