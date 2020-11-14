package hk.edu.polyu.comp.comp2021.cvfs.model;

public interface AbstractFile {

    /**
     * 
     * @return name of this file
     */
    public String getName();

    /**
     * 
     * @return the type of this file
     */
    public FileType getType();

    /**
     * 
     * @return A AbstractFile reference, which is the newly created file.
     */
    public AbstractFile createFile();

    /**
     * 
     * delete this file.
     */
    public void deleteFile();
}
