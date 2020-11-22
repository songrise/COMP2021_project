package hk.edu.polyu.comp.comp2021.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.operation.LogicOpsFactory;
import hk.edu.polyu.comp.comp2021.cvfs.model.operation.Operation;

public class CompositeCriterion extends ConcreteCriterion {

    private final Criterion criterionA;
    private final Criterion criterionB;
    private final String logicOpName;

    public CompositeCriterion(String criName, Criterion criterionA, String logicOp, Criterion criterionB) {
        super(criName);
        this.criterionA = criterionA;
        this.criterionB = criterionB;
        this.logicOpName = logicOp;
    }



    @Override
    public boolean eval(File f) {
        Operation logicOp = LogicOpsFactory.createOperation(logicOpName);
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
