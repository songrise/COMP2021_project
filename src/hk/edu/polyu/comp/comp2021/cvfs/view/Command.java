package hk.edu.polyu.comp.comp2021.cvfs.view;

/**
 * Interface for command
 */
public interface Command {
    /**
     * @return get type of this
     */
    CommandType getType();

    /**
     * @return get parameters of this
     */
    String[] getParameters();
}
