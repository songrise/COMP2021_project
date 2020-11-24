package hk.edu.polyu.comp.comp2021.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.operator.LogicOpsFactory;
import hk.edu.polyu.comp.comp2021.cvfs.model.operator.Operator;

/**
 * Class for the binaryCriterion
 */
public class BinaryCriterion extends ConcreteCriterion {
    

    /**
     *
     */
    private static final long serialVersionUID = 2021L;
    private final Criterion criterionA;
    private final Criterion criterionB;
    private final String logicOpName;

    /**
     * @param criName name of criterion, should be two english letters
     * @param criterionA first Criterion object
     * @param logicOp name of logic operation, including &&, ||, !
     * @param criterionB second Criterion object
     */
    public BinaryCriterion(String criName, Criterion criterionA, String logicOp, Criterion criterionB) {
        super(criName);
        this.criterionA = criterionA;
        this.criterionB = criterionB;
        this.logicOpName = logicOp;
    }



    @Override
    public boolean eval(File f) {
        Operator logicOp = LogicOpsFactory.createOperation(logicOpName);
        if (criterionB != null) {
            return logicOp.eval(this.criterionA.eval(f), this.criterionB.eval(f));
        }
        return logicOp.eval(this.criterionA.eval(f), null);
    }

    @Override
    public String toString() {
        // implicit recursion here, base case is when dynamic type of criterion is
        // simple criterion
        return "(" + this.criterionA.toString() + ")" + " " + logicOpName + " " + "(" + this.criterionB.toString()
                + ")";
    }
}
