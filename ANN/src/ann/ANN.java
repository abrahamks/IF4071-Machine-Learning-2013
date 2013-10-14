/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;
import ann.ANNOperation;
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
        ArffParserANN parser = new ArffParserANN("playtennis.arff", "biner");
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
                
        an.NeuralNetwork(data, w, 1, 1, target, 2, 8);
    }
}
