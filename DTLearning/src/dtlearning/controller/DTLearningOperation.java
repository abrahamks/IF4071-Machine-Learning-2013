/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtlearning.controller;

import dtlearning.model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


/**
 *
 * @author Abraham Krisnanda
 */
public class DTLearningOperation {
     public static double countEntropy(ArrayList<Double> en){
        double etr = 0;
        for(int i=0; i<en.size(); i++){
            if(en.get(i) == 0){
                etr = 0;
            }else{
                etr += -(en.get(i)*(Math.log(2)/Math.log(en.get(i))));
            }            
        }
        return etr;
    }
    

    public static double countRemainder(int sumEx, ArrayList<AttributeCalculation> attr){
        // menghitung remainder untuk sebuah listOfAttribute
        // ArrayList level 1 --> Attribute values
        // ArrayList level 2 --> Classification values
        // sumEx --> total Examples
        double rem = 0;
        ArrayList<Double> sumVal = new ArrayList<Double>();
        ArrayList<ArrayList<Double>> sumEntr = new ArrayList<ArrayList<Double>>();

        // menghitung total setiap listOfAttribute value
        for(int i=0; i<attr.size(); i++){
            double sum = 0;
            for(int j=0; j<attr.get(i).getValCount().size(); j++){
                sum += attr.get(i).getValCount().get(j);
            }
            sumVal.add(sum);
        }        
        
        // menghitung parameter untuk entropi
        for(int i=0; i<attr.size(); i++){
            ArrayList<Double> n = new ArrayList<Double>();

            for(int j=0; j<attr.get(i).getValCount().size(); j++){
                double prob = attr.get(i).getValCount().get(j)/sumVal.get(i);                
                n.add(prob);
            }
            sumEntr.add(n);
        }
        
        // menghitung remainder listOfAttribute
        for(int i=0; i<attr.size(); i++){
            double x = countEntropy(sumEntr.get(i));
            rem += (sumVal.get(i)/sumEx)*countEntropy(sumEntr.get(i));
        }        
        return rem;
    }
    
    public static double countInfGain(ArrayList<Double> entropy, double remainder){
        double gain = 0;
        gain = countEntropy(entropy) - remainder;
        return gain;
    }
    
    public static ArrayList<AttributeCalculation> createTableAttr(Examples ex, int attNumber){
        ArrayList<AttributeCalculation> tab = new ArrayList<AttributeCalculation>();
        int numOfAttr = ex.getAttributes().size();
                
        for(int i=0; i< ex.getAttributes().get(attNumber).getAttributeValue().size(); i++){
            // iterasi setiap attribute value: sunny, outcast, rain
            String name = ex.getAttributes().get(attNumber).getAttributeValue().get(i);
            AttributeCalculation ac = new AttributeCalculation();
            for(int j=0; j<ex.getData().size(); j++){
                // iterasi setiap data set
                // cek jumlah "yes" dan "no" untuk setiap attibute value (sunny, outcast, rain)
                ac.setAttName(name);
                if (ex.getData().get(j).get(attNumber).equals(name) && ex.getData().get(j).get(numOfAttr-1).equals("yes")){
                    ac.setValCount(ac.setIDx0(ac.getValCount(), 1.0));
                }
                else if(ex.getData().get(j).get(attNumber).equals(name) && ex.getData().get(j).get(numOfAttr-1).equals("no")){
                    ac.setValCount(ac.setIDx1(ac.getValCount(), 1.0));
                }
            }
            tab.add(ac);
        }
        return tab;
    }
    
    public static String BestInfGain(Examples ex){
        // mencari attribute dengan IG terbesar (outlook/temperature/humidity/wind)
        String bestAtt = "";
        ArrayList<AttributeIG> best = new ArrayList<AttributeIG>();
        int numOfAttr = ex.getAttributes().size();
        int numOfData = ex.getData().size();
        int yes = 0;
        int no = 0;
        
        for(int j=0; j<numOfData; j++){
            if(ex.getData().get(j).get(numOfAttr-1).equals("yes")){
                yes++;
            }
            else if(ex.getData().get(j).get(numOfAttr-1).equals("no")){
                no++;
            }
        }
        
        ArrayList<Double> ent = new ArrayList<Double>();
        ent.add((double)yes/numOfData);
        ent.add((double)no/numOfData);
        
        for(int i=0; i<(numOfAttr-1); i++){
            // iterasi setiap attribute pada examples (outlook/temperature/humidity/wind)
            ArrayList<AttributeCalculation> tab = new ArrayList<AttributeCalculation>();
            tab = createTableAttr(ex, i);
            double remainder = countRemainder(numOfData, tab);
            double IG = countInfGain(ent, remainder);
            AttributeIG ig = new AttributeIG();
            ig.setAtrName(ex.getAttributes().get(i).getAttributeName());
            ig.setGain(IG);
            best.add(ig);
        }
        
        Collections.sort(best, new CustomComparator());
        bestAtt = best.get(0).getAtrName();
        
        return bestAtt;
    }
    
    public static class CustomComparator implements Comparator<AttributeIG> {
              @Override
              public int compare(AttributeIG t1, AttributeIG t2) {
                  int comp = 0;
                 if(t1.getGain()< t2.getGain()){
                         comp = 1;
                 }else if(t1.getGain() == t2.getGain()){
                         comp = 0;
                 }else if(t1.getGain() > t2.getGain()){
                         comp = -1;
                 }
                 return comp;
              }
        }
    
    public static Examples FilterExamples (Examples oldEx, Attribute parent, String attrValue ) {
        String lineRemoved = "";
        int indexParent = oldEx.getAttributes().indexOf(parent);
        // iterate data in index indexParent for value=attrValue
        for (int i=0; i< oldEx.getData().size(); i++) {
            if (!(oldEx.getData().get(i).get(indexParent).equals(attrValue))) {
                lineRemoved = oldEx.getData().remove(i).toString(); // remove dari newEx karena ga penting
                i--;
            }
        }
        return oldEx;
    }
    
    public static Node ID3(Examples Ex, Attribute Target_attr, ArrayList<Attribute> Attributes) {
        // create a Root node for the tree
        Node root = new Node();
        if (Ex.isExamplesPositive()) {
            // if all Examples are positive, Return the single-node tree Root, label +
            root.setAttribute(Target_attr);
            root.setAllChildrenPos();
        }
        else if (Ex.isExampleNegative()) {
             // if all Examples are negative, Return the single-node tree Root, label -
            root.setAttribute(Target_attr);
            root.setAllChildrenNeg();
        }
        else if (Attributes.size()==2) {
            // 2 --> targetAttribute & classification
            root.setAttribute(Target_attr);
            // define index Target_attr dari example
            int index = Ex.getAttributes().indexOf(Target_attr);
            int numOfValue = Ex.getAttributes().get(index).getAttributeValue().size();
            int numOfAttr = Ex.getAttributes().size();
            HashMap<String, Object> mostCommonValue = new HashMap<String,Object>();
            
            for(int i=0; i<numOfValue; i++){
                int nYes=0; // jumlah value Yes
                int nNo=0; // jumlah value No
                for (int j=0; j< Ex.getData().size(); j++) {
                    String value = Ex.getAttributes().get(index).getAttributeValue().get(i); // attribute value (wind/strong)
                    if (Ex.getData().get(j).get(index).equals(value) && Ex.getData().get(j).get(numOfAttr-1).equals("yes")){
                        nYes++;
                    }
                    else if(Ex.getData().get(j).get(index).equals(value) && Ex.getData().get(j).get(numOfAttr-1).equals("no")){
                        nNo++;
                    }
                }
                                
                if(nYes > nNo){
                    // set attribute value dengan "yes"
                    mostCommonValue.put(Target_attr.getAttributeValue().get(i), "yes");
                }else{
                    // set attribute value dengan "no"
                    mostCommonValue.put(Target_attr.getAttributeValue().get(i), "no");
                }
            }
            root.setChildren(mostCommonValue);
        }
        else {
            String tempAttr = BestInfGain(Ex); // A <-- attribute name best classifies Example
            int flag=-1; // index attribute
            for (int i=0; i < Attributes.size(); i++) {
                if (Attributes.get(i).getAttributeName().equals(tempAttr)) {
                    flag=i;
                    break;
                }
            }
            System.out.println("a");
            Attribute A = Attributes.get(flag);
            root.setAttribute(A);
            // The decision attribute for Root <- A
            HashMap <String, Object> branch = new HashMap <String, Object> ();
            Examples exampleVi = new Examples();
            for (int i=0; i < A.getAttributeValue().size() ; i++) {
                // for each possible value i of A
                // add a new tree branch below root
                exampleVi = FilterExamples(Ex, A, A.getAttributeValue().get(i));
                // filter examples Ex by value tempAttr from Attribute A(Parent)
                if (exampleVi.getData().isEmpty()) {
                    // dari sini
                    int index = Ex.getAttributes().indexOf(Target_attr);
                    int numOfValue = Ex.getAttributes().get(index).getAttributeValue().size();
                    int numOfAttr = Ex.getAttributes().size();

                    for(int k=0; k<numOfValue; k++){
                        int nYes=0; // jumlah value Yes
                        int nNo=0; // jumlah value No
                        for (int j=0; j< Ex.getData().size(); j++) {
                            String value = Ex.getAttributes().get(index).getAttributeValue().get(k); // attribute value (wind/strong)
                            if (Ex.getData().get(j).get(index).equals(value) && Ex.getData().get(j).get(numOfAttr-1).equals("yes")){
                                nYes++;
                            }
                            else if(Ex.getData().get(j).get(index).equals(value) && Ex.getData().get(j).get(numOfAttr-1).equals("no")){
                                nNo++;
                            }
                        }

                        if(nYes > nNo){
                            // set attribute value dengan "yes"
                            branch.put(Target_attr.getAttributeValue().get(k), "yes");
                        }else{
                            // set attribute value dengan "no"
                            branch.put(Target_attr.getAttributeValue().get(k), "no");
                        }
                    }
                }
                else{
                    Node NodeID3 = new Node();
                    ArrayList<Attribute> Attributes_A = Attributes;
                    Attributes_A.remove(A);
                    Attribute predictiveTarget_attr;
                    
                    String tempAttr2 = BestInfGain(Ex); // A <-- attribute name best classifies Example
                    int flag2=-1; // index attribute
                    for (int ii=0; ii < Attributes_A.size(); ii++) {
                        if (Attributes_A.get(ii).getAttributeName().equals(tempAttr2)) {
                            flag2=ii;
                            break;
                        }
                    }
                    System.out.println("a");
                    predictiveTarget_attr = Attributes_A.get(flag2);
                    
                    
                    
                    NodeID3 = ID3(exampleVi, predictiveTarget_attr, Attributes_A);
                    branch.put(Target_attr.getAttributeValue().get(i), NodeID3);
                }
            }
        }
        return root;
    }
}
