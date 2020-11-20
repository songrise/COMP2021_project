package hk.edu.polyu.comp.comp2021.cvfs.controller.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.controller.operation.LogicOpsFactory;
import hk.edu.polyu.comp.comp2021.cvfs.controller.operation.Operation;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

public class NegationCriterion extends ConcreteCriterion{
    private final Criterion originalCri;


    public NegationCriterion(String criName, Criterion cri){
        super(criName);
        this.originalCri = cri;
    }

    @Override
    public boolean eval(File f) {
        Operation op = LogicOpsFactory.createOperation("!");
        return op.eval(originalCri.eval(f),null);
    }

    @Override
    public String toString() {
        return "!("+originalCri.toString()+")";
    }
}
