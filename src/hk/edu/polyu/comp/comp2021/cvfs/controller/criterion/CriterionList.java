package hk.edu.polyu.comp.comp2021.cvfs.controller.criterion;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CriterionList {
    private final ArrayList<Criterion> criteria;

    public CriterionList(){
        this.criteria = new ArrayList<>(16);
        criteria.add(IsDocumentCriterion.getCriterion());
    }
    // -----------------Private methods----------------//
    private boolean nameAlreadyUsed(String criName){
        try{
            findCriterion(criName);
            return false;
        }catch (NoSuchElementException e){
            return true;
        }

    }
    // -----------------Public methods----------------//
    public void addCriterion(Criterion cri){
        //TODO bug
//        if(!nameAlreadyUsed(cri.getName())){
            criteria.add(cri);
//        }
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
