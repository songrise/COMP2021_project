package hk.edu.polyu.comp.comp2021.cvfs.view;


/**
 * implements Command interface
 */
class ConcreteCommand implements Command {
    private final CommandType type;
    private final String[] parameters;

    /**
     * @param type type of command
     * @param parameters list of parameters
     */
    ConcreteCommand(CommandType type, String[] parameters) {
        this.type = type;
        this.parameters = parameters;
    }

    /**
     * @return type of this
     */
    @Override
    public CommandType getType() {
        return type;
    }

    /**
     * @return parameters of this
     */
    @Override
    public String[] getParameters() {
        return parameters;
    }
}
