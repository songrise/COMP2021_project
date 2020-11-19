package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;

public class File implements Serializable {
    private static final long serialVersionUID = 2021L;
    private String name;
    private FileType type;

    // -----------------Constructor----------------//
    File() {
    }

    File(String name, String type) {
        this.setName(name);
        this.setType(type);
    }

    // -----------------Private methods----------------//
    private boolean isAllowedInName(char ch) {
        return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || ('1' <= ch && ch <= '9');
    }

    private boolean isValidName(String nameStr) {
        final int MAX_LEN = 10;
        if (nameStr == null) {
            throw new IllegalArgumentException("Name of file cannot be empty");
        } else if (nameStr.length() > MAX_LEN) {
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

    // -----------------Public methods----------------//
    /**
     * @return name of this file
     */
    public String getName() {
        return this.name;
    }

    public FileType getType() {
        return this.type;
    }

    public int getSize() {
        return 0;
    }

    /**
     * @return full name of this file e.g. test.txt
     */
    public String getFullName() {
        if (this.isDirectory())
            return this.name + "/";
        else
            return this.name + "." + this.type.toString();
    }

    /**
     * @param nameStr
     * @throws IllegalArgumentException
     */
    public void setName(String nameStr) throws IllegalArgumentException {
        if (isValidName(nameStr))
            this.name = nameStr;
    }

    public void setType(String typeStr) {
        this.type = FileType.initType(typeStr);
    }

    /**
     * @return true if this file is a directory.
     */
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
