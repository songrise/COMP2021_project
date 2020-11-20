package hk.edu.polyu.comp.comp2021.cvfs.controller.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

public interface Criterion {
    String getName();

    boolean eval(File f);

    String toString();
}
