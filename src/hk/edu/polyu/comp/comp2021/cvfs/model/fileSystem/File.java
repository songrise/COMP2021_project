package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;


/**
 * Interface for file
 */
public interface File{
    /**
     * @return name of this File
     */
    String getName();
    /**
     * @return type name of this File
     */
    String getType();
    /**
     * @return size of this File
     */
    int getSize();
    /**
     * @return full name of this File, format: name.extension name, e.g. test.txt
     */
    String getFullName();

    /**
     * @param nameStr new name of the file
     * @throws IllegalArgumentException if the name is not valid
     */
    void setName(String nameStr) throws IllegalArgumentException;

    /**
     * @return true if this File is a directory
     */
    boolean isDirectory();

    @Override
    String toString();

    @Override
    boolean equals(Object obj);
}
