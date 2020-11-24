package hk.edu.polyu.comp.comp2021.cvfs.model.operator;

/**
 * Interface for the operation, both unary and binary.
 */
public interface Operator {
    /**
     * @return name of this.
     */
    String getName();

    /**
     * @param operand1 operand1
     * @param operand2 operand2
     * @return the result of evaluation.
     */
    boolean eval(Object operand1, Object operand2);
}
