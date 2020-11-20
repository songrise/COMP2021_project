package hk.edu.polyu.comp.comp2021.cvfs.controller.criterion;
//!Bugs
import java.io.Serializable;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class CriterionList implements Serializable {
    private static final long serialVersionUID = 2021L;
    private final HashSet<Criterion> criteria;

    public CriterionList(){
        this.criteria = new HashSet<>(16);
        criteria.add(IsDocumentCriterion.getCriterion());
    }
    // -----------------Private methods----------------//
    private boolean nameAlreadyUsed(String criName){
        for (Criterion cri:criteria){
            if (cri.getName().equals(criName))
                return true;
        }
        return false;
    }
    // -----------------Public methods----------------//
    public void addCriterion(Criterion cri){
        if(!nameAlreadyUsed(cri.getName())){
                criteria.add(cri);
        }
        else throw new IllegalArgumentException("Criterion named "+cri.getName()+ " already exists!");
    }

    public Criterion findCriterion(String name){
        for (Criterion cri :criteria){
            if(cri.getName().equals(name)){
                return cri;
            }
        }
        throw new NoSuchElementException("No criterion named "+name);
    }
    public String getAllCriteria(){
        StringBuilder sb = new StringBuilder();
        for(Criterion c : criteria){
            sb.append(c.toString());
            sb.append("\n");
        }
        return sb.toString();
    }



}
