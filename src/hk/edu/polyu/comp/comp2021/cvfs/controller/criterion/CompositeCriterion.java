package hk.edu.polyu.comp.comp2021.cvfs.controller.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.controller.operation.LogicOpsFactory;
import hk.edu.polyu.comp.comp2021.cvfs.controller.operation.Operation;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

public class CompositeCriterion extends ConcreteCriterion {

    private final Criterion criterionA;
    private final Criterion criterionB;
    private final String logicOpName;

    public CompositeCriterion(String criName, String logicOp, Criterion criterionA, Criterion criterionB) {
        super(criName);
        this.criterionA = criterionA;
        this.criterionB = criterionB;
        this.logicOpName = logicOp;
    }



    @Override
    public boolean eval(File f) {
        Operation logicOp = LogicOpsFactory.createOperation(logicOpName);
        return logicOp.eval(this.criterionA.eval(f), this.criterionB.eval(f));
    }

    @Override
    public String toString() {
        // implicit recursion here, base case is when dynamic type of criterion is
        // simple criterion
        return "(" + this.criterionA.toString() + ")" + " " + logicOpName + " " + "(" + this.criterionB.toString()
                + ")";
    }
}
