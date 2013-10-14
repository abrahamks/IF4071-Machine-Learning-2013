/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;
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
    public void NeuralNetwork(ArrayList<ArrayList<Integer>> Data, double[] weight, double learningrate, double t, int[] target, int actMode, int countMode, int maxIteration, double epsilon){
        int bias = 1;
        int epoch = 1;
        double out = 0;
        double output = 0;
        double error = 0;
        double tempdelta = 0;
        double MSE = 0;
        double tempError;
        ArrayList<Double> deltaW = new ArrayList<Double>();
        ArrayList<ArrayList<Double>> deltaW_batch = new ArrayList<ArrayList<Double>>();
        
        for(int i=0; i<Data.get(0).size(); i++){
            ArrayList<Double> temp = new ArrayList<Double>();
            deltaW_batch.add(temp);
        }
        
        int line = 0;
        int n = Data.get(0).size();
        System.out.println("n = "+n);
        while (epoch<=maxIteration || MSE <= epsilon){
            System.out.println("\nEpoch / iterasi ke- "+epoch);
            tempError = 0;
            for(int j=0; j<Data.get(0).size(); j++){ // melakukan perhitungan pada iterasi ke-epoch
                for(int i=0; i<Data.size(); i++){
                    System.out.print(Data.get(i).get(j)+"   ");
                }
                System.out.print(" | ");
                for(int i=0; i<Data.size(); i++){
                    System.out.print(weight[i]+"    ");
                }
                System.out.print(" | ");
                for(int i=0; i<Data.size(); i++){
                    out += Data.get(i).get(j) * weight[i];
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

                error = target[j] - output;
                out = 0;
                System.out.print(output+" | ");
                System.out.print(target[j]+" | ");
                System.out.print(error+"    | ");
                
                if(countMode == 0){ // incremental
                    for(int i=0; i<Data.size(); i++){
                        tempdelta = learningrate*error*Data.get(i).get(j);
                        deltaW.add(tempdelta);
                        System.out.print(deltaW.get(i)+"    ");
                    }

                    System.out.print(" | ");
                    for(int i=0; i<Data.size(); i++){
                        weight[i] = weight[i] + deltaW.get(i);
                        System.out.print(weight[i]+"    ");
                    }
                    deltaW.clear();
                }else if(countMode == 1){ // batch
                    for(int i=0; i<Data.size(); i++){
                        tempdelta = learningrate*error*Data.get(i).get(j);
                        deltaW_batch.get(i).add(tempdelta);
                        System.out.print(deltaW_batch.get(i).get(j)+"    ");
                    }
                }
                
                tempError += error * error; // (t-o)^2
                line++;
                System.out.println();
            }
            
            if(countMode == 1){ // batch, add new weight
                System.out.print("new delta: ");
                for(int i=0; i<Data.size(); i++){
                    double deltabatch = 0;
                    for(int k=0; k<deltaW_batch.get(i).size(); k++){
                        deltabatch += deltaW_batch.get(i).get(k);
                    }                    
                    weight[i] = weight[i] + deltabatch;
                    System.out.print(weight[i]+"    ");
                    deltaW_batch.get(i).clear();
                }  
                System.out.println();
            }
            System.out.println("tempError = "+tempError);
            MSE = tempError/n;
            System.out.println("MSE: "+MSE+" , Epsilon: "+epsilon);
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
