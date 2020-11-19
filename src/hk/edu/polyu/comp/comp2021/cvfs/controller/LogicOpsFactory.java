package hk.edu.polyu.comp.comp2021.cvfs.controller;

class LogicAndOperation extends Operation{
    LogicAndOperation(){
        super("&&");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        Boolean criA = (Boolean) operand1;
        Boolean criB = (Boolean) operand2;
        return criA && criB;
    }
}

class LogicOrOperation extends Operation{
    LogicOrOperation(){
        super("||");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        Boolean criA = (Boolean) operand1;
        Boolean criB = (Boolean) operand2;
        return criA || criB;
    }
}
class LogicNotOperation extends Operation{
    LogicNotOperation(){
        super("!");
    }

    @Override
    public boolean eval(Object operand1, Object operand2) {
        //in this case, operand2 are ommited since not is unary.
        Boolean criA = (Boolean) operand1;
        return !criA;
    }
}



public class LogicOpsFactory {
    public static Operation createOperation(String opName){
        switch (opName){
            case "&&":
                return new LogicAndOperation();
            case"||":
                return new LogicOrOperation();
            case "!":
                return new LogicNotOperation();
        }
        throw new UnsupportedOperationException("Invalid Operation name: "+opName);
    }
}
