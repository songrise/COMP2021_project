package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class File implements Serializable {
    private static final long serialVersionUID = 2021L;
    protected String name;
    private File parentDir;
    private FileType type;

    File() {

    }

    File(String name, String type, File parentDir) {
        this.setName(name);
        this.setType(type);
        this.parentDir = parentDir;
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
     * @return full name of this file e.g. test.txt
     */
    public String getFullName() {
        if (this.isDirectory())
            return this.name + "/";
        else
            return this.name + "." + this.type.toString();
    }

    /**
     * 
     * @return true if this file is a directory.
     */
    public boolean isDirectory() {
        return this.type.getTypeID() == 5;
    }

    public File getParentDirectory() {
        if (this.parentDir == null) {
            throw new NoSuchElementException("Now is at root directory!");
        }
        return this.parentDir;
    }

    public String getFullPath() {
        ArrayDeque<String> stack = new ArrayDeque<>();
        File crtDir = this;
        while (crtDir != null) {
            stack.push(crtDir.getName());
            crtDir = crtDir.parentDir;
        }
        StringBuilder sb = new StringBuilder("./");
        while (!stack.isEmpty()) {
            sb.append(stack.pop() + "/");
        }
        return sb.toString();
    }

    /**
     * 
     * @param nameStr
     * @throws IllegalArgumentException
     */
    public void setName(String nameStr) throws IllegalArgumentException {
        if (nameStr == null) {
            throw new IllegalArgumentException();
        } else if (nameStr.length() > 10) {
            throw new IllegalArgumentException("Name of file longer than 10");
        } else {
            HashSet<Character> allowedCH = new HashSet<Character>() {// initialize a valid character set.
                private static final long serialVersionUID = 2021L;
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

    public void setType(String typeStr) {
        this.type = FileType.initType(typeStr);
    }

}
