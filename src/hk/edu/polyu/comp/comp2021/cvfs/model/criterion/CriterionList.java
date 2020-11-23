package hk.edu.polyu.comp.comp2021.cvfs.model.criterion;
//!Bugs
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A container for criterion
 */
public class CriterionList implements Serializable {
    private static final long serialVersionUID = 2021L;
    private final ArrayList<Criterion> criteria;

    /**
     *
     */
    public CriterionList(){
        this.criteria = new ArrayList<>(16);
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

    /**
     * @param cri the Criterion object to be added in to the Crilist
     */
    // -----------------Public methods----------------//
    public void addCriterion(Criterion cri){
        if(!nameAlreadyUsed(cri.getName())){
                criteria.add(cri);
        }
        else throw new IllegalArgumentException("Criterion named "+cri.getName()+ " already exists!");
    }

    /**
     * @param name the name of Criterion to be found.
     * @return  Critetion object with the specified name.
     * @throws NoSuchElementException if not found specified criterion
     */
    public Criterion findCriterion(String name) throws NoSuchElementException{
        for (Criterion cri :criteria){
            if(cri.getName().equals(name)){
                return cri;
            }
        }
        throw new NoSuchElementException("No criterion named ["+name+"]");
    }

    /**
     * @return A String of all the criteria name, one each line.
     */
    public String getAllCriteria(){
        StringBuilder sb = new StringBuilder();
        for(Criterion c : criteria){
            sb.append(c.toString());
            sb.append("\n");
        }
        return sb.toString();
    }



}
