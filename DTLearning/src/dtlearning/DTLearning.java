/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtlearning;
import dtlearning.controller.*;
import dtlearning.model.*;
import java.lang.*;
import java.util.*;

/**
 *
 * @author Anasthasia
 */
public class DTLearning {

   
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
    

    public double countRemainder(int sumEx, ArrayList<AttributeCalculation> attr){
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
    
    public double countInfGain(ArrayList<Double> entropy, double remainder){
        double gain = 0;
        gain = countEntropy(entropy) - remainder;
        return gain;
    }
    
    public ArrayList<AttributeCalculation> createTableAttr(Examples ex, int attNumber){
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
    
    public String BestInfGain(Examples ex){
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //ArffParser AP = new ArffParser("F:\\Amelia\\Documents\\Teknik Informatika 2010\\Semester 7\\IF4071 Machine Learning\\Eksperimen DTL\\playtennis.arff");
    
        System.out.println("isPos" + AP.Ex.isExamplesPositive());
        System.out.println(AP.Ex.isExampleNegative());
        
        // ngetest best IG
        DTLearning dtl = new DTLearning();
        String b = "";
        b = dtl.BestInfGain(AP.Ex);
        System.out.println("best: " + b);
        
        ArrayList<Attribute> AR = AP.listOfAttribute;
//        @attribute outlook { sunny, overcast, rain }
//        @attribute temperature { hot, mild, cool }
//        @attribute humidity { high, normal }
//        @attribute wind { weak, strong }
//        @attribute playTennis { no, yes}
        Node o = new Node(AR.get(0));
        Node h = new Node(AR.get(2));
        Node w = new Node(AR.get(3));
        HashMap<String, Object> anak2 = new HashMap<String, Object>();
        anak2.put(o.getAttribute().getAttributeValue().get(0), h);
        anak2.put(o.getAttribute().getAttributeValue().get(1), "yes");
        anak2.put(o.getAttribute().getAttributeValue().get(2), w);
        HashMap<String, Object> anakH = new HashMap<String, Object>();
        anakH.put(h.getAttribute().getAttributeValue().get(0), "yes");
        anakH.put(h.getAttribute().getAttributeValue().get(1), "no");
        HashMap<String, Object> anakW = new HashMap<String, Object>();
        anakW.put(w.getAttribute().getAttributeValue().get(0), "yes");
        anakW.put(w.getAttribute().getAttributeValue().get(1), "no");
        o.setChildren(anak2);
        h.setChildren(anakH);
        w.setChildren(anakW);
        System.out.println(o.toString());
    }
}
