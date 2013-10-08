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
     * Mode: incremental
     * Topologi: 1 perceptron
     * @param Data
     * @param weight
     * @param learningrate
     * @param t
     * @param target
     * @param actMode: 0 = linear, 1 = sign, 2 = step, 3 = sigmoid
     * @param maxIteration 
     */
    public void NeuralNetwork(ArrayList<ArrayList<Integer>> Data, double[] weight, double learningrate, double t, int[] target, int actMode, int maxIteration){
        int bias = 1;
        int epoch = 1;
        double out = 0;
        double output = 0;
        double error = 0;
        double tempdelta = 0;
        ArrayList<Double> deltaW = new ArrayList<Double>();
        
        int iter = 0;
        System.out.println("Epoch "+epoch);
        while (iter<maxIteration){
            for(int j=0; j<Data.get(0).size(); j++){
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
                        output = 0;
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
                iter++;
                System.out.println();
            }
            epoch++;
            System.out.println("Epoch "+epoch);
        }
        
    }
}
