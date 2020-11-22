package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFS;

public class ConcreteCommandRunner implements CommandRunner{
    final CommandType command;
    ConcreteCommandRunner(CommandType c){
        this.command = c;
    }

    int getNumOfParameters(){
        return command.getNumOfParameters();
    }

    void echo(String[] parameters){
        System.out.println("");
    }

    @Override
    public boolean execute(CVFS cvfs) {
        System.out.println("Command "+command+"needs parameter(s)!");
        return false;
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (parameters.length == 0){
            return execute(cvfs);
        }
        if (parameters == null || parameters.length != getNumOfParameters()) {
            System.out.println("command failed because: Illegal set of parameters");
            return false;
        }
        return true;

    }

}
