package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFS;

/**
 * Interface for command runner
 */
public interface CommandRunner {
    /**
     * @param cvfs CVFS object
     * @return true if successfully executed
     */
    boolean execute(CVFS cvfs);

    /**
     * @param cvfs CVFS object
     * @param parameters list of parameters
     * @return true if successfully executed
     */
    boolean execute(CVFS cvfs, String[] parameters);
}
