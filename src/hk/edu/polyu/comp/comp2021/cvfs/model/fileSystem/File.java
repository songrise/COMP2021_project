package hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem;



public interface File{
    String getName();

    String getType();

    int getSize();

    String getFullName();

    void setName(String nameStr) throws IllegalArgumentException;

    boolean isDirectory();

    @Override
    String toString();

    @Override
    boolean equals(Object obj);
}
