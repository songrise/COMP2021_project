package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.model.File;

public class Criterion {
    private final String criName;

    Criterion() {
        this.criName = null;
    }

    Criterion(String name) {
        if (isValidName(name))
            this.criName = name;
        else {
            throw new IllegalArgumentException();
        }
    }

    // -----------------Private methods----------------//
    private boolean isAlphabetical(String name) {
        for (char ch : name.toCharArray()) {
            if (!Character.isLetter(ch))
                return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        if (name.length() != 2) {
            return false;
        }
        return isAlphabetical(name);
    }

    public String getName() {
        return this.criName;
    }

    /**
     * 
     * @return the evaluated result of this criterion.
     */
    public boolean eval(File f) {
        return false;
    }

    @Override
    public String toString() {
        return ""+criName;
    }
}
