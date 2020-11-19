package hk.edu.polyu.comp.comp2021.cvfs.controller.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

public class ConcreteCriterion implements Criterion {
    private final String criName;

    ConcreteCriterion() {
        this.criName = null;
    }

    ConcreteCriterion(String name) {
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
    // -----------------Public methods----------------//
    @Override
    public String getName() {
        return this.criName;
    }

    /**
     * 
     * @return the evaluated result of this criterion.
     */
    @Override
    public boolean eval(File f) {
        return false;
    }

    @Override
    public String toString() {
        return ""+criName;
    }
}
