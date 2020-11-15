package hk.edu.polyu.comp.comp2021.cvfs.model;

public class File {
    protected String name;

    /**
     * 
     * @return name of this file
     */
    public String getName();

    /**
     * 
     * @return true if this file is a directory.
     */
    public boolean isDirectory();

    public AbstractFile getParentDirectory();

    public String getFullPath();

    public void setName(String fileName);

    /**
     * 
     * @return A AbstractFile reference, which is the newly created file.
     */
    public AbstractFile createFile(String fileName);

    /**
     * 
     * delete this file.
     */
    public void deleteFile();
}
