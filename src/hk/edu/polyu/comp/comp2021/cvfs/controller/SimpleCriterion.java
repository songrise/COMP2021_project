package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.controller.Operation;
import hk.edu.polyu.comp.comp2021.cvfs.controller.SimpleCriOpsFactory;
import hk.edu.polyu.comp.comp2021.cvfs.model.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.FileType;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleCriterion extends Criterion {

    // separation of data and logic, good design for reusability and maintenance
    private static final HashMap<String, ArrayList<String>> attrOp;// attribute and their corresponding operations
    // ? This may use a wrapper class?
    private String attrName;
    private String opName;
    private String val;
    static {
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

    SimpleCriterion() {

    }

    public SimpleCriterion(String criName, String attrName, String op, String val) {
        super(criName);
        this.setAttrName(attrName);
        this.setOpName(op);
        this.setVal(val);
    }

    // -----------------Private methods----------------//

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
        // Strategy design pattern
        Operation op = SimpleCriOpsFactory.createOperation(opName);
        return op.eval(f, val);
    }

    @Override
    public String toString() {
        return super.toString()+" "+attrName+" "+opName+" "+val;
    }
}