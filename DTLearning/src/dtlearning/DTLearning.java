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
    
    public double countInfGain(ArrayList<Double> entropy, double remainder){
        double gain = 0;
        gain = countEntropy(entropy) - remainder;
        return gain;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //ArffParser AP = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\playtennis.arff");
        
        /* tabel outlook */
        ArrayList<ArrayList<Double>> outlook = new ArrayList<ArrayList<Double>>();
        
        ArrayList<Double> outlook1 = new ArrayList<Double>();
        ArrayList<Double> outlook2 = new ArrayList<Double>();
        ArrayList<Double> outlook3 = new ArrayList<Double>();
        
        outlook1.add((double)2);
        outlook1.add((double)3);
        
        outlook2.add((double)4);
        outlook2.add((double)0);
        
        outlook3.add((double)3);
        outlook3.add((double)2);
        
        outlook.add(0, outlook1);
        outlook.add(1, outlook2);
        outlook.add(2, outlook3);
        /* end of tabel outlook */
        
        
        /* tabel temperature */
        ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
        
        ArrayList<Double> temp1 = new ArrayList<Double>();
        ArrayList<Double> temp2 = new ArrayList<Double>();
        ArrayList<Double> temp3 = new ArrayList<Double>();
        
        temp1.add((double)2);
        temp1.add((double)4);
        
        temp2.add((double)4);
        temp2.add((double)2);
        
        temp3.add((double)3);
        temp3.add((double)1);
        
        temp.add(0, temp1);
        temp.add(1, temp2);
        temp.add(2, temp3);
        /* end of tabel temperature */
        
        System.out.println("berikut adalah tabel outlook: ");
        for(int i=0; i< outlook.size(); i++){
            System.out.println(outlook.get(i));
        }
        System.out.println("menghitung remainder outlook:");
        DTLearning dtl = new DTLearning();
        double c = dtl.countRemainder(12, outlook);
        
        System.out.println("berikut adalah tabel temperature: ");
        for(int i=0; i< temp.size(); i++){
            System.out.println(temp.get(i));
        }
        System.out.println("menghitung remainder temperature:");
        dtl.countRemainder(12, temp);
        
        ArrayList<Double> enGlobal = new ArrayList<Double>();
        enGlobal.add((double)0.5);
        enGlobal.add((double)0.5);
        
        System.out.println("inf gain outlook: "+dtl.countInfGain(enGlobal, c));
    }
}
