package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.controller.operation.LogicOpsFactory;
import hk.edu.polyu.comp.comp2021.cvfs.controller.operation.Operation;
import hk.edu.polyu.comp.comp2021.cvfs.model.File;

public class CompositeCriterion extends Criterion {

    final Criterion criterionA;
    final Criterion criterionB;
    final String logicOpName;

    public CompositeCriterion(String criName, String logicOp, Criterion criterionA, Criterion criterionB) {
        super(criName);
        this.criterionA = criterionA;
        this.criterionB = criterionB;
        this.logicOpName = logicOp;
    }

    // -----------------Private methods----------------//
    private boolean isCompositeCriterion(Criterion cri) {
        return cri.getClass() == this.getClass();
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
