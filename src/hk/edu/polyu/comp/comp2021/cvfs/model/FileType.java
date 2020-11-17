/**
* -*- coding : utf-8 -*-
* @FileName  : FileType.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-14
* @Github    ：https://github.com/songrise
* @Descriptions: type of file, may use a enum class to replace.
**/

package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;

public class FileType implements Serializable {

    private static final long serialVersionUID = 2021L;
    private int typeID; // this is not final, since file type could be modified

    private FileType(String typeStr) {
        if (typeStr != null) {
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
    }

    /**
     * 
     * @return type id, 1~4 for documents, 5 is directory.
     */
    protected int getTypeID() {
        return this.typeID;
    }

    /**
     * 
     * @param typeStr type name, String. valid
     *                argument:"txt","java","html","css","dir". case-insensitive
     * @return reference to newly created FileType object.
     * @throws IllegalArgumentException
     */
    public static FileType initType(String typeStr) throws IllegalArgumentException {
        if (typeStr != null) {
            return new FileType(typeStr);
        } else {
            throw new IllegalArgumentException("Null type name!");
        }
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

        return this.typeID == otherType.typeID;
    }

    @Override
    public String toString() {
        switch (this.typeID) {
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

    public static void main(String[] args) {

    }
}
