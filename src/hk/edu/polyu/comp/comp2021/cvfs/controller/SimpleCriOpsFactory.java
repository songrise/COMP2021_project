/**
* -*- coding : utf-8 -*-
* @FileName  : SimpleCriOpsFactory.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-19
* @Github    ：https://github.com/songrise
* @Descriptions: Strategy pattern for operations
**/

package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.model.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.FileType;

import java.lang.reflect.Field;

class NameContainsOperation extends Operation {
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
        String strB = (String) operand1;
        return fileA.getName().contains(strB);
    }
}

class TypeEqualsOperation extends Operation {
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
        String strB = (String) operand2;
        return fileA.getType().equals(FileType.initType(strB));
    }
}

class SizeLargerOperation extends Operation {
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

class SizeLessOperation extends Operation {
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

class SizeLargerEqualOperation extends Operation {
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

class SizeLessEqualOperation extends Operation {
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

class SizeNotEqualOperation extends Operation {
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

class SizeEqualOperation extends Operation {
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

public class SimpleCriOpsFactory {

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

        throw new UnsupportedOperationException("Invalid Operation name: "+opName);
    }
}