/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;
import ann.ANNOperation;
import ann.ArffParserANN;
import java.util.ArrayList;

/**
 *
 * @author Anasthasia
 */
public class ANN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //ArffParserANN parser = new ArffParserANN("playtennis.arff", "biner");
        ANNOperation an = new ANNOperation();
        ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> x1 = new ArrayList<Integer>();
        ArrayList<Integer> x2 = new ArrayList<Integer>();
        ArrayList<Integer> x3 = new ArrayList<Integer>();
        x1.add(0);
        x1.add(0);
        x1.add(1);
        x1.add(1);
        x3.add(1);
        x3.add(1);
        x3.add(1);
        x3.add(1);
        x2.add(0);
        x2.add(1);
        x2.add(0);
        x2.add(1);
        data.add(x1);
        data.add(x2);
        data.add(x3);
        
        double[] w = {2,3,0};
        int[] target = {1,1,1,0};
               
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
        //an.NeuralNetwork(data, w, 1, 1, target, 2, 0, 2, 0);
        
        GeneralParser gp = new GeneralParser("AND1.txt");
        an.NeuralNetwork(gp.getData(), gp.getWeight(), 0.1, 1, gp.getTarget(), 0, 0, 10, 0);
    }
}
