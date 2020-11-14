package src.hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.model.AbstractFile;

public class Document implements AbstractFile {

    private FileType type;
    private String name;

    // -----------------Public methods----------------//

    public void setName(String nameStr) {

    }

    /**
     * 
     * @return name of this file
     */
    public String getName() {
        return name;
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
        return null;
    }

    @Override
    public src.hk.edu.polyu.comp.comp2021.cvfs.model.AbstractFile createFile(String fileName) {
        // TODO Auto-generated method stub
        return null;
    }
}
