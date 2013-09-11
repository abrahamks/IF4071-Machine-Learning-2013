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
            if(en.get(i) == 0.0){
                etr = 0.0;
                break;
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
        System.out.println("IG = "+gain);
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
            System.out.print("("+ex.getAttributes().get(i).getAttributeName() + ")");
            double IG = countInfGain(ent, remainder);
            AttributeIG ig = new AttributeIG();
            ig.setAtrName(ex.getAttributes().get(i).getAttributeName());
            ig.setGain(IG);
            best.add(ig);
        }
        
        // Collections.sort(best, new CustomComparator());
        double maxVal = Double.MIN_VALUE;
        for (int i = 0; i < best.size(); i++){
            if (!Double.isNaN(best.get(i).getGain())){
                if (best.get(i).getGain() > maxVal){
                    maxVal = best.get(i).getGain();
                    bestAtt = best.get(i).getAtrName();
                }
            }
        }

        System.out.println("maxVal : " + maxVal);
        
        System.out.println("Best IG = "+bestAtt+"\n");     
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
        int indexParent = -1;
        for (int i = 0; i < oldEx.getAttributes().size(); i++){
            if (oldEx.getAttributes().get(i).getAttributeName().equals(parent.getAttributeName())){
                indexParent = i;
                break;
            }
        }
        
        // iterate data in index indexParent for value=attrValue
        for (int i=0; i< oldEx.getData().size(); i++) {
            if (!(oldEx.getData().get(i).get(indexParent).equals(attrValue))) {
                lineRemoved = oldEx.getData().remove(i).toString(); // remove dari newEx karena ga penting
                i--;
            }
        }
        for(int j=0; j<oldEx.getData().size(); j++){
            oldEx.getData().get(j).remove(oldEx.getData().get(j).get(indexParent));
        }
        oldEx.getAttributes().remove(oldEx.getAttributes().get(indexParent));
        return oldEx;
    }
    
    public Node ID3(Examples Ex, Attribute Target_attr, ArrayList<Attribute> Attributes) {
        // create a Root node for the tree
        Node root = new Node();
        if (Ex.isExampleEmpty()){
            root.setAttribute(null);
            root.setChildren(new HashMap<String,Object>());
        }
        else if (Ex.isExamplesPositive()) {
            // if all Examples are positive, Return the single-node tree Root, label +
//            root.setAttribute(Target_attr);
//            root.setAllChildrenPos();
            root.setAttribute(null);
            root.setChildren(new HashMap<String,Object>());
        }
        else if (Ex.isExampleNegative()) {
             // if all Examples are negative, Return the single-node tree Root, label -
            //root.setAttribute(Target_attr);
            //root.setAllChildrenNeg();
            root.setAttribute(null);
            root.setChildren(null);
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
            Attribute A = Attributes.get(flag);
            root.setAttribute(A);
            // The decision attribute for Root <- A
            HashMap <String, Object> branch = new HashMap <String, Object> ();
            
            for (int i=0; i < A.getAttributeValue().size() ; i++) {
                // for each possible value i of A
                // add a new tree branch below root
                Examples Excpy = new Examples(Ex);
                Examples exampleVi = FilterExamples(Excpy, A, A.getAttributeValue().get(i));
                // filter examples Ex by value tempAttr from Attribute A(Parent)
                if (exampleVi.getData().size()==1) {
                    // dari sini
                    int index = -1;
                    for (int j = 0; j < Excpy.getAttributes().size(); j++){
                        if (Excpy.getAttributes().get(j).getAttributeName().equals(Target_attr.getAttributeName())){
                            index = j;
                            break;
                        }
                    }
                    
                    int numOfValue = Excpy.getAttributes().get(index).getAttributeValue().size();
                    int numOfAttr = Excpy.getAttributes().size();
                    
                    HashMap<String, Object> mostCommonValue = new HashMap<String,Object>();

                    for(int k=0; k<numOfValue; k++){
                        int nYes=0; // jumlah value Yes
                        int nNo=0; // jumlah value No
                        for (int j=0; j< Excpy.getData().size(); j++) {
                            String value = Excpy.getAttributes().get(index).getAttributeValue().get(k); // attribute value (wind/strong)
                            if (Excpy.getData().get(j).get(index).equals(value) && Excpy.getData().get(j).get(numOfAttr-1).equals("yes")){
                                nYes++;
                            }
                            else if(Excpy.getData().get(j).get(index).equals(value) && Excpy.getData().get(j).get(numOfAttr-1).equals("no")){
                                nNo++;
                            }
                        }

                        if(nYes > nNo){
                            // set attribute value dengan "yes"
                            mostCommonValue.put(Target_attr.getAttributeValue().get(k), "yes");
                        }else{
                            // set attribute value dengan "no"
                            mostCommonValue.put(Target_attr.getAttributeValue().get(k), "no");
                        }
                    }
                    root.setChildren(mostCommonValue);
                }
                else{
                    Node NodeID3 = new Node();
                    ArrayList<Attribute> Attributes_A = exampleVi.getAttributes();
                    //Attributes_A.remove(A);
                    
//                    Attribute predictiveTarget_attr;
//                    
//                    String tempAttr2 = BestInfGain(exampleVi); // A <-- attribute name best classifies Example
//                    int flag2=-1; // index attribute
//                    for (int ii=0; ii < Attributes_A.size(); ii++) {
//                        if (Attributes_A.get(ii).getAttributeName().equals(tempAttr2)) {
//                            flag2=ii;
//                            break;
//                        }
//                    }
//                    System.out.println("a");
//                    predictiveTarget_attr = Attributes_A.get(flag2);
                    
                    NodeID3 = new Node();
                    NodeID3 = ID3(exampleVi, exampleVi.getAttributes().get(0), Attributes_A);
                    branch.put(A.getAttributeValue().get(i), NodeID3);                    
                }
            }
            root.setChildren(branch);
        }
        return root;
    }
    
    public Examples ClassifyExamples (Examples dataSet, Node N) {
        Examples ds = new Examples(dataSet);
        
        int rootIndex0 = ds.indexOfAttribute(N.getAttribute().getAttributeName());
        int resultIndex = ds.getAttributes().size()-1;
        String tempKey="";
        for (int i=0; i < dataSet.getData().size(); i++) {
            tempKey = ds.getData().get(i).get(rootIndex0);
            Node branchI = new Node();
            branchI = (Node) N.getChildren().get(tempKey);
            if (branchI.getAttribute() == null) {
                // yes / no
                if (branchI.getChildren() != null) {
                    // yes
                    ArrayList<String> data_ds   = new ArrayList<>();
                    data_ds = ds.getData().get(i);
                    data_ds.add("yes");
                }
                else {
                    // no
                    ArrayList<String> data_ds = new ArrayList<>();
                    data_ds = ds.getData().get(i);
                    data_ds.add("yes");
                }
            }
            else {
                for (int j=0; j < branchI.getChildren().size(); j++) {
                    Node nextNode = new Node();
                    String tempK = branchI.getAttribute().getAttributeValue().get(j);
                    nextNode = (Node) branchI.getChildren().get(tempK);
                    ClassifyExamples(dataSet, nextNode);
                }
            }
        }
        
        return ds;
    }
    
    public Examples Classify(Node id3, Examples ex){
        Examples classify = new Examples(ex);
        for (int i=0; i < ex.getData().size(); i++) {
            Node currentNode = new Node();
            currentNode = id3;
            String rootAttr = id3.getAttribute().getAttributeName();
            int indexParent = ex.indexOfAttribute(rootAttr);
            while (currentNode.getAttribute() != null) {
                // is not leaf
                for(int k=0; k< ex.getData().get(i).size(); k++){
                    for (int j=0; j< id3.getAttribute().getAttributeValue().size();j++) {
                    // iterasi key
                        if (ex.getData().get(i).get(k).equals(id3.getAttribute().getAttributeValue().get(j))){
                            currentNode = (Node) id3.getChildren().get(id3.getAttribute().getAttributeValue().get(j));                            
                        }
                    }
                }
            }
            if(currentNode.getAttribute()==null) {
                if (currentNode.getChildren() != null) {
                    ArrayList<String> data_ds   = new ArrayList<>();
                    data_ds = classify.getData().get(i);
                    data_ds.add("yes");
                }
                else {
                     ArrayList<String> data_ds   = new ArrayList<>();
                    data_ds = classify.getData().get(i);
                    data_ds.add("no");
                }
            }
        }
        return classify;
    }
    
    public Examples CobaClassify (Examples testSet, Node N) {
        // iterasi sebanyak jmlah data
        int lastIndex = testSet.getData().get(0).size();
        for (int nData=0; nData < testSet.getData().size(); nData++) {
            Node root = N;
            // belum ketemu yes / no
            while(!root.isLeaf()) {
                
                String SrootAttr = root.getAttribute().getAttributeName();
                int indexRoot = testSet.indexOfAttribute(SrootAttr); // cari indexRoot
                String ex_iR = testSet.getData().get(nData).get(indexRoot);// cek example ke-nData pada indexRoot
                // cari terusan kaki nya
                Node kaki = ((Node) root.getChildren().get(ex_iR));
                if (kaki.isLeaf()) {
                    if (kaki.getChildren()==null) {
                        // negative
                        testSet.getData().get(nData).add(lastIndex, "no");
                        break;
                    }
                    else {
                        // positive
                        testSet.getData().get(nData).add(lastIndex, "yes");
                    }
                }
                root = new Node(kaki);
            }
            System.out.println("halo");
        }
        return testSet;
    }
    
    public static ArrayList<Attribute> cloneAttribute(ArrayList<Attribute> ListOfA) {
        ArrayList<Attribute> clone = new ArrayList<>();
        for (Attribute item : ListOfA) {
            Attribute A = new Attribute(item);
            clone.add(A);
        }
        return clone;
    }
    
    public Examples dataSetGenerator(Examples ex) {
        Examples newDataSet = new Examples(ex);
        ArrayList<ArrayList<String>> newData= new ArrayList<ArrayList<String>>();
        ArrayList<Attribute> newAttributes = cloneAttribute(ex.getAttributes());
        return ex;
    }
}
