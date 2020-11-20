/**
* -*- coding : utf-8 -*-
* @FileName  : LogicOpsFactory.java
* @Author    : Ruixiang JIANG (Songrise)
* @Time      : 2020-11-19
* @Github    ：https://github.com/songrise
* @Descriptions: Logic operations and their simple factory. https://medium.com/nestedif/java-simple-factory-pattern-9c2538dd0265
**/

package hk.edu.polyu.comp.comp2021.cvfs.model.operation;

class LogicAndOperation extends ConcreteOperation {
    LogicAndOperation() {
        super("&&");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        Boolean criA = (Boolean) operand1;
        Boolean criB = (Boolean) operand2;
        return criA && criB;
    }
}

class LogicOrOperation extends ConcreteOperation {
    LogicOrOperation() {
        super("||");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        Boolean criA = (Boolean) operand1;
        Boolean criB = (Boolean) operand2;
        return criA || criB;
    }
}

class LogicNotOperation extends ConcreteOperation {
    LogicNotOperation() {
        super("!");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        // in this case, operand2 are ommited since not is unary.
        Boolean criA = (Boolean) operand1;
        return !criA;
    }
}

public class LogicOpsFactory {
    public static Operation createOperation(String opName) {
        switch (opName) {
            case "&&":
                return new LogicAndOperation();
            case "||":
                return new LogicOrOperation();
            case "!":
                return new LogicNotOperation();
        }
        throw new UnsupportedOperationException("Invalid Operation name: " + opName);
    }
}
