/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtlearning.model;

import java.util.ArrayList;
/**
 *
 * @author Abraham Krisnanda
 */
public class Examples {
//    training examples
//    contoh :
//    sunny, hot, high, weak, no
//    sunny, hot, high, strong, no
    private ArrayList<Attribute> attributes;
    private ArrayList<ArrayList<String>> data;
    public Examples() {
        data = new ArrayList<ArrayList<String>>();
        // banyak data --> data.size()
    }
    
    public Examples (Examples ex) {
        attributes = new ArrayList<Attribute>(ex.attributes);
        data = new ArrayList<ArrayList<String>>(ex.data);
    }
    /**
     * @return the data
     */
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(ArrayList<ArrayList<String>> data) {
        this.data = data;
    }
    
    /**
     * @return the attributes
     */
    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }
    
    public boolean isExamplesPositive() {
        boolean classificationValue = true;
        int cvIndex = getData().get(0).size()-1;
        // index of classificationValue
        for (int i=0; i< getData().size(); i++) {
            if (getData().get(i).get(cvIndex).equals("no")) {
                classificationValue = false;
            }
        }
        return classificationValue;
    }
    
    public boolean isExampleNegative() {
        boolean classificationValue = true;
        int cvIndex = getData().get(0).size()-1;
        // index of classificationValue
        for (int i=0; i< getData().size(); i++) {
            if (getData().get(i).get(cvIndex).equals("yes")) {
                classificationValue = false;
            }
        }
        return classificationValue;
    }
    
    public boolean isExampleEmpty() {
        return (getData().isEmpty());
    }

}
