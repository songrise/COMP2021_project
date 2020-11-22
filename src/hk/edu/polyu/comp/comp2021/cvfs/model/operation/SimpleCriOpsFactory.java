/**
* -*- coding : utf-8 -*-
* @FileName  : SimpleCriOpsFactory.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-19
* @Github    ：https://github.com/songrise
* @Descriptions: Strategy pattern for operations
**/

package hk.edu.polyu.comp.comp2021.cvfs.model.operation;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

/**
 *
 */
class NameContainsOperation extends ConcreteOperation {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    NameContainsOperation() {
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
        return fileA.getName().contains(strB);
    }
}

/**
 *
 */
class TypeEqualsOperation extends ConcreteOperation {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    TypeEqualsOperation() {
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
        return fileA.getType().equals(strB.toLowerCase());
    }
}

/**
 *
 */
class SizeLargerOperation extends ConcreteOperation {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeLargerOperation() {
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
class SizeLessOperation extends ConcreteOperation {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeLessOperation() {
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
class SizeLargerEqualOperation extends ConcreteOperation {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeLargerEqualOperation() {
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
class SizeLessEqualOperation extends ConcreteOperation {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeLessEqualOperation() {
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
class SizeNotEqualOperation extends ConcreteOperation {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeNotEqualOperation() {
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
class SizeEqualOperation extends ConcreteOperation {
    private static final long serialVersionUID = 2021L;
    /**
     *
     */
    SizeEqualOperation() {
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
    public static Operation createOperation(String opName) {
        switch (opName) {
            case "contains":
                return new NameContainsOperation();
            case "equals":
                return new TypeEqualsOperation();
            case ">":
                return new SizeLargerOperation();
            case "<":
                return new SizeLessOperation();
            case ">=":
                return new SizeLargerEqualOperation();
            case "<=":
                return new SizeLessEqualOperation();
            case "==":
                return new SizeEqualOperation();
            case "!=":
                return new SizeNotEqualOperation();
        }

        throw new UnsupportedOperationException("Invalid Operation name: " + opName);
    }
}