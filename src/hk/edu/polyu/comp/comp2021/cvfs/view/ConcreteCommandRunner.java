package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFS;

/**
 * implements CommandRunner interface
 */
public class ConcreteCommandRunner implements CommandRunner{
    private final CommandType command;

    /**
     * @param c type of the command
     */
    ConcreteCommandRunner(CommandType c){
        this.command = c;
    }

    /**
     * @return number of parameters needed for this command
     */
    int getNumOfParameters(){
        return getCommand().getNumOfParameters();
    }

    /**
     * echo the command related info to screen
     * @param parameters parameters
     */
    void echo(String[] parameters){
        System.out.println("");
    }

    @Override
    public boolean execute(CVFS cvfs) {
        System.out.println("Command "+ getCommand() +"needs parameter(s)!");
        return false;
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (parameters.length == 0){
            return execute(cvfs);
        }
        if (parameters.length != getNumOfParameters()) {
            System.out.println("command failed because: Illegal set of parameters");
            return false;
        }
        return true;

    }

    /**
     * @return CommandType of this
     */
    public CommandType getCommand() {
        return command;
    }
}
