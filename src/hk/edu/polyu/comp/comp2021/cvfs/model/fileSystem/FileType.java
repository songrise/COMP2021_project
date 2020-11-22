/**
* -*- coding : utf-8 -*-
* @FileName  : FileType.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-14
* @Github    ：https://github.com/songrise
* @Descriptions: type of file, may use a enum class to replace.
**/

package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;

import java.io.Serializable;

/**
 * Type of file. It may be wiser to implement us enum.
 */
public final class FileType implements Serializable {

    private static final long serialVersionUID = 2021L;
    private final int typeID;

    private FileType(String typeStr) throws IllegalArgumentException{
        if (typeStr == null || typeStr.length()==0) {
            throw new IllegalArgumentException("type name cannot be empty!");
        }
        String t = typeStr.toUpperCase();
        switch (t) {
            case "TXT":
                this.typeID = 1;
                break;
            case "JAVA":
                this.typeID = 2;
                break;
            case "HTML":
                this.typeID = 3;
                break;
            case "CSS":
                this.typeID = 4;
                break;
            case "DIR":
                this.typeID = 5;
                break;
            default:
                throw new IllegalArgumentException("Invalid type name!");
        }
    }

    /**
     * A factory method
     *
     * @param typeStr type name, String. valid
     *                argument:"txt","java","html","css","dir". case-insensitive
     * @return reference to newly created FileType object.
     * @throws IllegalArgumentException if the typeStr is invalid
     */
    public static FileType initType(String typeStr) throws IllegalArgumentException {
        return new FileType(typeStr);
    }

    /**
     *
     * @return type id, 1~4 for documents, 5 is directory.
     */
    int getTypeID() {
        return this.typeID;
    }

    /**
     * @return True if this FileType object is type directory
     */
    public boolean isDirectory() {
        return this.getTypeID() == 5;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        FileType otherType = (FileType) obj;

        return this.getTypeID() == otherType.getTypeID();
    }

    @Override
    public String toString() {
        switch (this.getTypeID()) {
            case 1:
                return "txt";
            case 2:
                return "java";
            case 3:
                return "html";
            case 4:
                return "css";
            case 5:
                return "directory";
            default:
                return "errortype";
        }
    }
}
