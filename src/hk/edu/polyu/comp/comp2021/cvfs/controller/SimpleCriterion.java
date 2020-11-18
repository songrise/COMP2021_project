package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.model.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.FileType;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleCriterion extends Criterion {

    // separation of data and logic, good design for reusability and maintenance
    private HashMap<String, ArrayList<String>> attrOp;// attribute and their corresponding operations

    // ? This may use a wrapper class?
    // ? currently no idea on how to handle 3 possible forms gracefully
    private String attrName;
    private String opName;
    private String val;

    SimpleCriterion() {

    }

    SimpleCriterion(String criName, String attrName, String op, String val) {
        super(criName);
        initLookUp(); // may also use a initializer block.
        this.setAttrName(attrName);
        this.setOpName(op);
        this.setVal(val);
    }

    // -----------------Private methods----------------//
    private void initLookUp() {
        attrOp = new HashMap<String, ArrayList<String>>() {
            {
                put("name", new ArrayList<String>() {
                    {
                        add("contains");
                    }
                });
                put("type", new ArrayList<String>() {
                    {
                        add("equals");
                    }
                });
                put("size", new ArrayList<String>() {
                    {
                        add(">");
                        add("<");
                        add(">=");
                        add("<=");
                        add("==");
                        add("!=");
                    }
                });
            }
        };
    }

    private boolean isValidAttr(String attrName) {
        if (attrName == null) {
            return false;
        }
        return attrOp.containsKey(attrName);
    }

    private boolean isValidOp(String opStr) {
        if (opStr == null) {
            return false;
        }
        return attrOp.get(this.attrName).contains(opStr);
    }

    // assume view has trimmed the double quote.
    private boolean isValidVal(String val) {
        if (val == null) {
            return false;
        }

        if (this.attrName.equals("size")) {
            try {
                Integer.parseInt(val);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private void setAttrName(String attrName) {
        if (isValidAttr(attrName)) {
            this.attrName = attrName;
        } else {
            throw new IllegalArgumentException("Invalid Attribute name " + attrName + "!");
        }
    }

    private void setOpName(String opStr) {
        if (isValidOp(opStr)) {
            this.opName = opStr;
        } else {
            throw new IllegalArgumentException("Invalid operation " + opStr + " for " + attrName + "!");
        }
    }

    private void setVal(String val) {
        if (isValidVal(val)) {
            this.val = val;
        } else {
            throw new IllegalArgumentException("Invalid value " + val + " for " + attrName + "!");
        }
    }

    // -----------------Public methods----------------//

    @Override
    public boolean eval(File f) {
        // This code is very bad and I will refactor later
        // ? I am still thinking how this could be done by polymorphism
        if (this.attrName.equals("name")) {
            return f.getName().contains(val);
        } else if (this.attrName.equals("type")) {
            return f.getType().equals(FileType.initType(val));
        } else {
            switch (opName) {
                case ">":
                    return f.getSize() > Integer.parseInt(val);
                case "<":
                    return f.getSize() < Integer.parseInt(val);
                case ">=":
                    return f.getSize() >= Integer.parseInt(val);
                case "<=":
                    return f.getSize() <= Integer.parseInt(val);
                case "==":
                    return f.getSize() == Integer.parseInt(val);
                case "!=":
                    return f.getSize() != Integer.parseInt(val);
            }
        }
        return false;
    }
}
