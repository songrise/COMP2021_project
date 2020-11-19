package hk.edu.polyu.comp.comp2021.cvfs.controller;

public class Operation {
    private final String name;

    Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean eval(Object operand1, Object operand2) {
        return false;
    }

}
