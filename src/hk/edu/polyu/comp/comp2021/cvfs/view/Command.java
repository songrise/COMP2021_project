package hk.edu.polyu.comp.comp2021.cvfs.view;

class Command {
    final CommandType type;
    final String[] parameters;

    Command(CommandType type, String[] parameters) {
        this.type = type;
        this.parameters = parameters;
    }
}
