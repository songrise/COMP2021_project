package hk.edu.polyu.comp.comp2021.cvfs.model.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.operation.LogicOpsFactory;
import hk.edu.polyu.comp.comp2021.cvfs.model.operation.Operation;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

/**
 * Class for newNegation of a existing Criterion
 */
public class NegationCriterion extends ConcreteCriterion{
    private final Criterion originalCri;


    /**
     * @param criName name of the criterion
     * @param cri The Criterion object to negate.
     */
    public NegationCriterion(String criName, Criterion cri){
        super(criName);
        assert cri != null;
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
