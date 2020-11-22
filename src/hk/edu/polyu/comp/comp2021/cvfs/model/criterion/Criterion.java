package hk.edu.polyu.comp.comp2021.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

/**
 * Interface for criteria
 */
public interface Criterion {
    /**
     * @return name of this criterion
     */
    String getName();

    /**
     * @param f the file object to evaluate on
     * @return the evaluated result, true if f satisfy this criterion.
     */
    boolean eval(File f);

    String toString();
}
