package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFS;

public interface CommandRunner {
    boolean execute(CVFS cvfs);
    boolean execute(CVFS cvfs, String[] parameters);
}
