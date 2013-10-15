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
	ArffParserANN parser = new ArffParserANN("playtennis.arff", "index");
        System.out.println("\n==Hasil :");
        System.out.println(parser.data);
        System.out.println("\n==Target :");
        System.out.println(parser.target);        
        ANNOperation an = new ANNOperation();
        ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> x1 = new ArrayList<Integer>();
        ArrayList<Integer> x2 = new ArrayList<Integer>();
        ArrayList<Integer> t = new ArrayList<Integer>();
        x1.add(1);
        x1.add(1);
        x1.add(0);
        x1.add(0);
        x2.add(1);
        x2.add(0);
        x2.add(1);
        x2.add(0);
        data.add(x1);
        data.add(x2);
        t.add(1);
        t.add(0);
        t.add(0);
        t.add(1);
               
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
        double[] w = new double[] {0.05, 0.05};
	// menambahkan bias
        ArrayList<Integer> bias = new ArrayList<Integer>();
        for(int i=0; i<parser.data.get(0).size(); i++){
            bias.add(1);
        }
        parser.data.add(bias);
        
        // menambahkan weight
        ArrayList<Double> weight = new ArrayList<Double>();
        for(int i=0; i<(parser.data.size()+1); i++){
            weight.add(0.5);
        }
        
        GeneralParser gp = new GeneralParser("AND1.txt");
        an.NeuralNetwork(gp.getData(), gp.getWeight(), 0.1, 1, gp.getTarget(), 1, 0, 10, 0);
        //GeneralParser gp = new GeneralParser("NAND.txt");
        //an.BackPropagation(data, w, 0.1, 1, t, 3, 0, 2, 3, 0.001);    }
}
