package hk.edu.polyu.comp.comp2021.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.operator.Operator;
import hk.edu.polyu.comp.comp2021.cvfs.model.operator.SimpleCriOpsFactory;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for simple criterion
 */
public class SimpleCriterion extends ConcreteCriterion {
    /**
     *
     */
    private static final long serialVersionUID = 2021L;
    // separation of data and logic, good design for reusability and maintenance
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final HashMap<String, ArrayList<String>> attrOp;// attribute and their corresponding operations
    // ? This may use a wrapper class?
    private String attrName;
    private String opName;
    private String val;

    static {// initialize a look-up
        attrOp = new HashMap<String, ArrayList<String>>() {
            /**
            *
            */
            private static final long serialVersionUID = 2021L;
            {
                put("name", new ArrayList<String>() {
                    /**
                     *
                     */
                    private static final long serialVersionUID = 2021L;

                    {
                        add("contains");
                    }
                });
                put("type", new ArrayList<String>() {
                    /**
                    *
                    */
                    private static final long serialVersionUID = 2021L;
                    {
                        add("equals");
                    }
                });
                put("size", new ArrayList<String>() {
                    /**
                    *
                    */
                    private static final long serialVersionUID = 2021L;
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

    /**
     * @param criName  name of criterion, should be two english letters
     * @param attrName name of attribute
     * @param op       name of operator
     * @param val      name of value
     */
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
        } else if (this.attrName.equals("type") || this.attrName.equals(("name"))) {
            if (val.length() <= 1 || val.charAt(0) != '"' || val.charAt((val.length() - 1)) != '"') {
                throw new IllegalArgumentException("Illegal val operand, must be embraced in double quote!");
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
        } else if (this.attrName.equals("type") || this.attrName.equals(("name"))) {
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
        Operator op = SimpleCriOpsFactory.createOperation(opName);
        return op.eval(f, val);
    }

    @Override
    public String toString() {
        return attrName + " " + opName + " " + val;
    }
}