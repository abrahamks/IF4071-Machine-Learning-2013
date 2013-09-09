/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtlearning.model;
import java.util.ArrayList;
/**
 *
 * @author Anasthasia
 */
public class AttributeCalculation {
    // training data example:
    // attribute name: outlook
    //      sunny   |   overcast    |   rainy
    // yes  2       |   4           |   3
    // no   3       |   0           |   2
    
    private String attName;
    private ArrayList<Double> valCount;

    public AttributeCalculation(){
        valCount = new ArrayList<>();
        valCount.add(0,0.0);
        valCount.add(1,0.0);
    }
    
    /**
     * @return the valCount
     */
    public ArrayList<Double> getValCount() {
        return valCount;
    }

    /**
     * @param valCount the valCount to set
     */
    public void setValCount(ArrayList<Double> valCount) {
        this.valCount = valCount;
    }
    public ArrayList<Double> setIDx0(ArrayList<Double> target, double d){
        // manipulasi index-0
        ArrayList<Double> a = target;
        a.set(0, a.get(0) + d);
        return a;
    }
    public ArrayList<Double> setIDx1(ArrayList<Double> target, double d){
        // manipulasi index-1
        ArrayList<Double> a = target;
        a.set(1, a.get(1) + d);
        return a;
    }

    /**
     * @return the attName
     */
    public String getAttName() {
        return attName;
    }

    /**
     * @param attName the attName to set
     */
    public void setAttName(String attName) {
        this.attName = attName;
    }
    
}
