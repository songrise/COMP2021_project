package hk.edu.polyu.comp.comp2021.cvfs.controller;

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



    @Override
    public boolean eval(File f) {
        Operation logicOp = LogicOpsFactory.createOperation(logicOpName);
        return logicOp.eval(this.criterionA.eval(f),this.criterionB.eval(f));
    }
}
