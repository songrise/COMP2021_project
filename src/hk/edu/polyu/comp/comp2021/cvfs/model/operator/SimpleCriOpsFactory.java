/**
* -*- coding : utf-8 -*-
* @FileName  : SimpleCriOpsFactory.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-19
* @Github    ：https://github.com/songrise
* @Descriptions: Strategy pattern for operations
**/

package hk.edu.polyu.comp.comp2021.cvfs.model.operator;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

/**
 *
 */
class NameContainsOperator extends ConcreteOperator {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    NameContainsOperator() {
        super("contains");
    }

    /**
     * 
     * @return true if first operand contains second operand
     */
    @Override
    public boolean eval(Object operand1, Object operand2) {
        File fileA = (File) operand1;
        String strB = (String) operand2;
        strB =strB.substring(1, strB.length() - 1);
        return fileA.getName().contains(strB);
    }
}

/**
 *
 */
class TypeEqualsOperator extends ConcreteOperator {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    TypeEqualsOperator() {
        super("equals");
    }

    /**
     * 
     * @return true if first operand equals second operand in term of File type
     */
    @Override
    public boolean eval(Object operand1, Object operand2) {

        File fileA = (File) operand1;
        if (fileA.isDirectory())
            return false;

        String strB = (String) operand2;
        strB =strB.substring(1, strB.length() - 1);
        return fileA.getType().equals(strB.toLowerCase());
    }
}

/**
 *
 */
class SizeLargerOperator extends ConcreteOperator {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeLargerOperator() {
        super(">");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        File fileA = (File) operand1;
        String strB = (String) operand2;
        return fileA.getSize() > Integer.parseInt(strB);
    }
}

/**
 *
 *
 */
class SizeLessOperator extends ConcreteOperator {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeLessOperator() {
        super("<");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        File fileA = (File) operand1;
        String strB = (String) operand2;
        return fileA.getSize() < Integer.parseInt(strB);
    }
}

/**
 *
 */
class SizeLargerEqualOperator extends ConcreteOperator {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeLargerEqualOperator() {
        super(">=");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        File fileA = (File) operand1;
        String strB = (String) operand2;
        return fileA.getSize() >= Integer.parseInt(strB);
    }
}

/**
 *
 */
class SizeLessEqualOperator extends ConcreteOperator {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeLessEqualOperator() {
        super("<=");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        File fileA = (File) operand1;
        String strB = (String) operand2;
        return fileA.getSize() <= Integer.parseInt(strB);
    }
}

/**
 *
 */
class SizeNotEqualOperator extends ConcreteOperator {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeNotEqualOperator() {
        super("!=");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        File fileA = (File) operand1;
        String strB = (String) operand2;
        return fileA.getSize() != Integer.parseInt(strB);
    }
}

/**
 *
 */
class SizeEqualOperator extends ConcreteOperator {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeEqualOperator() {
        super("!=");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        File fileA = (File) operand1;
        String strB = (String) operand2;
        return fileA.getSize() == Integer.parseInt(strB);
    }
}

/**
 * A simple factory class for operations of simple criteria.
 */
public class SimpleCriOpsFactory {


    /**
     * @param opName name of the criterion
     * @return dispathed Operation object.
     */
    public static Operator createOperation(String opName) {
        switch (opName) {
            case "contains":
                return new NameContainsOperator();
            case "equals":
                return new TypeEqualsOperator();
            case ">":
                return new SizeLargerOperator();
            case "<":
                return new SizeLessOperator();
            case ">=":
                return new SizeLargerEqualOperator();
            case "<=":
                return new SizeLessEqualOperator();
            case "==":
                return new SizeEqualOperator();
            case "!=":
                return new SizeNotEqualOperator();
        }

        throw new UnsupportedOperationException("Invalid Operation name: " + opName);
    }
}