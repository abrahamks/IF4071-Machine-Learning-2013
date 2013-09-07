/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtlearning;
import dtlearning.controller.*;
import java.lang.*;
import java.util.*;

/**
 *
 * @author Anasthasia
 */
public class DTLearning {

    public double countEntropy(ArrayList<Double> en){
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
    
    public double countRemainder(int sumEx, ArrayList<ArrayList<Double>> attr){
        // menghitung remainder untuk sebuah attribute
        double rem = 0;
        ArrayList<Double> sumVal = new ArrayList<Double>();
        ArrayList<ArrayList<Double>> sumEntr = new ArrayList<ArrayList<Double>>();
        System.out.println("attr size: "+attr.size());
        // menghitung total setiap attribute value
        for(int i=0; i<attr.size(); i++){
            double sum = 0;
            for(int j=0; j<attr.get(i).size(); j++){
                sum += attr.get(i).get(j);
            }
            sumVal.add(sum);
        }        
        
        // menghitung parameter untuk entropi
        for(int i=0; i<attr.size(); i++){
            ArrayList<Double> n = new ArrayList<Double>();
            for(int j=0; j<attr.get(i).size(); j++){
                double prob = attr.get(i).get(j)/sumVal.get(i);                
                n.add(prob);
            }
            sumEntr.add(n);
        }
        
        // menghitung remainder attribute
        for(int i=0; i<attr.size(); i++){
            double x = countEntropy(sumEntr.get(i));
            rem += (sumVal.get(i)/sumEx)*countEntropy(sumEntr.get(i));
            System.out.println("rem: "+rem);
        }
        
        System.out.println("remainder atribute outlook: " + rem);
        return rem;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
ArffParser AP = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\playtennis.arff");
        ArrayList<ArrayList<Double>> test = new ArrayList<ArrayList<Double>>();
        
        
        ArrayList<Double> i1 = new ArrayList<Double>();
        ArrayList<Double> i2 = new ArrayList<Double>();
        ArrayList<Double> i3 = new ArrayList<Double>();
        
        i1.add((double)2);
        i1.add((double)3);
        
        i2.add((double)4);
        i2.add((double)0);
        
        i3.add((double)3);
        i3.add((double)2);
        
        test.add(0, i1);
        test.add(1, i2);
        test.add(2, i3);
        
        System.out.println("berikut adalah tabel outlook: ");
        for(int i=0; i< test.size(); i++){
            System.out.println(test.get(i));
        }
        System.out.println("test.size: " + test.size());
        System.out.println("test1.size: " + test.get(0).size());
        System.out.println("menghitung remainder outlook:");
        DTLearning dtl = new DTLearning();
        dtl.countRemainder(12, test);
    }
}
