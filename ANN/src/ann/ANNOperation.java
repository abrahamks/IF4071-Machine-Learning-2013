/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;
import java.text.DecimalFormat;
import java.util.*;

/**
 *
 * @author Anasthasia
 */
public class ANNOperation {
    
    /**
     * 1, if x >= threshold
     * 0, if x < threshold
     * @param x = value
     * @param t = threshold
     * @return int
     */
    public static int StepFunction(double x, double t){        
        int step = 0;
        if(x >= t){
            step = 1;
        }else if(x < t){
            step = 0;
        }
        return step;
    }
    
    /**
     * +1, if x >= 0
     * -1, if x < 0
     * @param x = value
     * @return int
     */
    public static int SignFunction(double x){
        int sign = 0;
        if(x >= 0){
            sign = 1;
        }else if(x < 0){
            sign = -1;
        }
        return sign;
    }
    
    /**
     * 1/(1+e^(-x))
     * @param x
     * @return 
     */
    public static double SigmoidFunction(double x){
        return 1 / (1 + Math.exp(-x));
    }
    
    /**    
     * Topologi: 1 perceptron
     * @param Data
     * @param weight
     * @param learningrate
     * @param t = threshold
     * @param target
     * @param actMode: 0 = linear, 1 = sign, 2 = step, 3 = sigmoid
     * @param countMode: 0 = incremental, 1 = batch
     * @param maxIteration 
     * @param epsilon
     */
    public void NeuralNetwork(ArrayList<ArrayList<Integer>> Data, ArrayList<Double> weight, double learningrate, double t,  ArrayList<Integer> target, int actMode, int countMode, int maxIteration, double epsilon){
        int bias = 1;
        int epoch = 1;
        double out = 0;
        double output = 0;
        double error = 0;
        double tempdelta = 0;
        double MSE = 999;
        double tempError;
        
        double e = 0;
        double temp_output = 0;
        double o = 0;
        DecimalFormat df = new DecimalFormat("##.##");
        ArrayList<Double> deltaW = new ArrayList<Double>();
        ArrayList<ArrayList<Double>> deltaW_batch = new ArrayList<ArrayList<Double>>();
        
        for(int i=0; i<Data.get(0).size(); i++){
            ArrayList<Double> temp = new ArrayList<Double>();
            deltaW_batch.add(temp);
        }
        
        boolean flag = true;
        int line = 0;
        int n = Data.get(0).size();
        System.out.println("n = "+n);
        while ((flag == true) && (epoch <= maxIteration)){
            if(MSE == epsilon){
                System.out.println("udah nol woy");
            }
            System.out.println("\nEpoch / iterasi ke- "+epoch);
            MSE = 0;
            tempError = 0;
            for(int j=0; j<Data.get(0).size(); j++){ // melakukan perhitungan pada iterasi ke-epoch
                for(int i=0; i<Data.size(); i++){
                    System.out.print(Data.get(i).get(j)+" ");
                }
                System.out.print(" | ");
                for(int i=0; i<Data.size(); i++){
                    System.out.print(df.format(weight.get(i))+"\t");
                }
                System.out.print(" | ");
                for(int i=0; i<Data.size(); i++){
                    out += Data.get(i).get(j) * weight.get(i);
                }                

                switch(actMode){
                    case 0: // linear
                        output = out;
                        break;
                    case 1: // sign
                        output = SignFunction(out);
                        break;
                    case 2: // step
                        output = StepFunction(out, t);
                        break;
                    case 3: // sigmoid
                        output = SigmoidFunction(out);
                        break;
                }                

                error = target.get(j) - output;
                out = 0;
                System.out.print(output+"\t| ");
                System.out.print(target.get(j)+" | ");
                System.out.print(error+"\t| ");
                
                // menghitung delta W:
                if(countMode == 0){ // incremental
                    for(int i=0; i<Data.size(); i++){
                        tempdelta = learningrate*error*Data.get(i).get(j);
                        deltaW.add(tempdelta);
                        System.out.print(df.format(deltaW.get(i))+"\t");
                    }

                    System.out.print(" | ");
                    for(int i=0; i<Data.size(); i++){
                        weight.set(i,weight.get(i) + deltaW.get(i));
                        System.out.print(df.format(weight.get(i))+"\t");
                    }
                    deltaW.clear();
                }else if(countMode == 1){ // batch
                    for(int i=0; i<Data.size(); i++){
                        tempdelta = learningrate*error*Data.get(i).get(j);
                        deltaW_batch.get(i).add(tempdelta);
                        System.out.print(deltaW_batch.get(i).get(j)+"\t");
                    }
                }
                
                line++;
                System.out.println();
            }
            
            /*** menghitung MSE ***/
            
            for(int i=0; i<n; i++){
                temp_output = 0;
                for(int k=0; k<Data.size();k++){
                    temp_output += weight.get(k) * Data.get(k).get(i);
                    //System.out.println("temp_output: "+df.format(weight.get(k))+" x "+Data.get(k).get(i)+" = "+df.format(temp_output));
                }
                switch(actMode){
                    case 0: // linear
                        o = temp_output;
                        break;
                    case 1: // sign
                        o = SignFunction(temp_output);
                        break;
                    case 2: // step
                        o = StepFunction(temp_output, t);
                        break;
                    case 3: // sigmoid
                        o = SigmoidFunction(temp_output);
                        break;
                }
                System.out.println("o:"+o);
                e = target.get(i) - o;
                tempError += e * e; // (t-o)^2
            }
            System.out.println("tempError = "+tempError);
            MSE = tempError/n;
            System.out.println("MSE: "+MSE+" , Epsilon: "+epsilon);
            if(MSE <= epsilon){
                flag = false;
            }
            /*** selesai menghitung MSE ***/
            
            if(countMode == 1){ // batch, add new weight
                System.out.print("new delta: ");
                for(int i=0; i<Data.size(); i++){
                    double deltabatch = 0;
                    for(int k=0; k<deltaW_batch.get(i).size(); k++){
                        deltabatch += deltaW_batch.get(i).get(k);
                    }                    
                    weight.set(i,weight.get(i) + deltabatch);
                    System.out.print(weight.get(i)+"    ");
                    deltaW_batch.get(i).clear();
                }  
                System.out.println();
            }            
            epoch++;   
        }
        
    }
    
    /**
     * topologi: 1 hidden layer, s cell
     * @param Data
     * @param weight
     * @param learningrate
     * @param t
     * @param target
     * @param actMode: 0 = linear, 1 = sign, 2 = step, 3 = sigmoid
     * @param countMode: 0 = incremental, 1 = batch
     * @param s: jumlah sel
     * @param maxIteration
     * @param epsilon 
     */
    public void BackPropagation(ArrayList<ArrayList<Integer>> Data, double[] weight, double learningrate, double t, int[] target, int actMode, int countMode, int s, int maxIteration, double epsilon){
        
    }
}
