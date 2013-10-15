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
        System.out.println("\t x \t | \t weight \t | output | target | error | \t delta w \t | \t new w");
        
        while ((flag == true) && (epoch <= maxIteration)){
            System.out.println("\nEpoch / iterasi ke- "+epoch);
            MSE = 0;
            tempError = 0;
            for(int j=0; j<Data.get(0).size(); j++){ // melakukan perhitungan pada iterasi ke-epoch
                for(int i=0; i<Data.size(); i++){
                    System.out.print(Data.get(i).get(j)+"\t");
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
                System.out.print(df.format(output)+" | ");
                System.out.print(target.get(j)+"\t | ");
                System.out.print(df.format(error)+" | ");
                
                // menghitung delta W:
                if(countMode == 0){ // incremental
                    for(int i=0; i<Data.size(); i++){
                        tempdelta = learningrate*error*Data.get(i).get(j);
                        deltaW.add(tempdelta);
                        System.out.print(df.format(deltaW.get(i))+"  ");
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
                        System.out.print(df.format(deltaW_batch.get(i).get(j))+" ");
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
                //System.out.println("o:"+o);
                e = target.get(i) - o;
                tempError += e * e; // (t-o)^2
            }
            System.out.println("tempError = "+df.format(tempError));
            MSE = tempError/n;
            System.out.println("MSE: "+df.format(MSE)+" , Epsilon: "+epsilon);
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
                    System.out.print(df.format(weight.get(i))+" ");
                    deltaW_batch.get(i).clear();
                }  
                System.out.println();
            }            
            epoch++;   
        }
        
    }
    
    /**
     * topologi: 1 hidden layer, sel cell
     * @param Data
     * @param w
     * @param learningrate
     * @param t
     * @param target
     * @param actMode: 0 = linear, 1 = sign, 2 = step, 3 = sigmoid
     * @param countMode: 0 = incremental, 1 = batch
     * @param sel: jumlah sel
     * @param maxIteration
     * @param epsilon 
     */
    public void BackPropagation(ArrayList<ArrayList<Integer>> Data, double[] w, double learningrate, double t, ArrayList<Integer> target, int actMode, int countMode, int sel, int maxIteration, double epsilon){
        int bias = 1;
        // initializing x bias
        ArrayList<Integer> xBias = new ArrayList<>();
        for (int i=0; i<Data.get(0).size();i++) {
            // membuat xBias dengan jumlah elemen sama dengan x0
            xBias.add(bias);
        }
        Data.add(0, xBias);
        // append bias pada index 0
        // insialisasi jumlah x (x0..xN)
        // proses penghitungan setiap hidden cell
        ArrayList<Double> outputCellWeight = new ArrayList<>();
        // inisialisasi weight untuk ouput cell
        ArrayList<ArrayList<Double>> hiddenCellWeight = new ArrayList<>();
        // arrayList level 1 --> weight keseluruhan yang berhubungan dengan hidden perceptron
        ArrayList<Double> hiddenCellSubWeight = new ArrayList<>();
        // temporary ArrayList
        // w0..wN dari hiddenCellWeight
        // pastikan w.length dan hidenCellSubWeight.size() sama
        double hiddenCellOutput = 0.0;
        // output dari hiddenCell
        double output = 0.0;
        // output dari outputCell
        ArrayList<Double> o = new ArrayList<>(); 
        // menampung hasil output hidden cell
        double deltaOutputCell = 0.0;
        // delta Output
        ArrayList<Double> deltaHiddenCell = new ArrayList<>();
        // menampung hasil delta hidden cell
        for (int j = 0; j < w.length; j++) {
            // translasi array w ke ArrayList hiddenCellSubWeight
            hiddenCellSubWeight.add(w[j]);
            // translasi array w ke ArrayList outputCellWeight
            outputCellWeight.add(w[j]);
            if (j == w.length-1) {
                // set wBias, index0, pada outputCellWeight
                outputCellWeight.add(0, w[0]);
            }
        }
        for (int s = 0; s < sel; s++) {
            // inisialisasi weight untuk setiap hidden cell
            hiddenCellWeight.add(hiddenCellSubWeight);
        }
        
        for (int j = 0; j < Data.get(0).size(); j++) {
            // mulai proses penghitungan untuk setiap sel
            PrintHiddenCellWeightTable(hiddenCellWeight);
            for (int s = 0; s < sel; s++) {
                double sigma = 0.0;
                // pemrosesan seluruh data
                // penghitungan sigma
                for (int n = 0; n < Data.size(); n++) {
                    // x0..xN
                    sigma += Data.get(n).get(j) * hiddenCellWeight.get(s).get(n);
                    // sigma += xn * wn
                }
                // hitung output (o<1,1>) berdasarkan fungsi
                DecimalFormat df = new DecimalFormat("##.##");
                sigma = Double.parseDouble(df.format(sigma));
                System.out.println("sigma " + s + ":" +sigma);
                switch(actMode){
                    case 0: // linear
                        hiddenCellOutput = sigma;
                        break;
                    case 1: // sign
                        hiddenCellOutput = SignFunction(sigma);
                        break;
                    case 2: // step
                        hiddenCellOutput = StepFunction(sigma, t);
                        break;
                    case 3: // sigmoid
                        hiddenCellOutput = SigmoidFunction(sigma);
                        break;
                }
                hiddenCellOutput = Double.parseDouble(df.format(hiddenCellOutput));
                o.add(hiddenCellOutput);
                System.out.println("out<1," + (s+1) +"> : " + hiddenCellOutput);
                if (s == sel-1) {
                    // hitung output sel
                    sigma = 0;
                    for (int i=0; i<o.size(); i++) {
                        // +1 karena ada w0(bias)
                        if (i==0) {
                            // bias
                            sigma += 0.05 + o.get(i) * outputCellWeight.get(i+1);
                        }
                        else {
                            // o<1,1> * w1
                            // +1 karena index 0 pada outputCellWeight digunakan utk bias
                            System.out.println("i" + i +":" + o.get(i) + "*" + outputCellWeight.get(i+1));
                            sigma += o.get(i) * outputCellWeight.get(i+1);
                        }
                    }
                    sigma = Double.parseDouble(df.format(sigma));
                    System.out.println("sigma_output:" +sigma);
                    // assign output
                    switch(actMode){
                    case 0: // linear
                        output = sigma;
                        break;
                    case 1: // sign
                        output = SignFunction(sigma);
                        break;
                    case 2: // step
                        output = StepFunction(sigma, t);
                        break;
                    case 3: // sigmoid
                        output = SigmoidFunction(sigma);
                        break;
                    }
                    deltaOutputCell = output * (1-output)*(target.get(j)-output);
                    // deltaOutput = output * (1-output)(error) // target-output    
                    for (int x=0; x<sel; x++) {
                        // hitung delta sebanyak hidden cell
                        // delta 1,1 = o1,1(1-o1,1)(w1*delta2,1)
                        double tempDelta = o.get(x)*(1-o.get(x))*(outputCellWeight.get(x))*deltaOutputCell;
//                        System.out.println("tempDelta =" + o.get(x) + "*" + (1-o.get(x)) + "*" + (outputCellWeight.get(x)) + "*" + deltaOutputCell);
                        deltaHiddenCell.add(tempDelta);
                    }
                    System.out.println("delta 2:" +deltaOutputCell);
                    PrintDeltaHiddenCell(deltaHiddenCell);
                    System.out.println("===End of Data-" + j + "===\n");
                    o.clear();
                    // kosongkan output sel
                    
                    for (int i=0; i<hiddenCellWeight.size(); i++) {
                        // i --> jumlah hidden cell
                        ArrayList<Double> tempALfor1cell = new ArrayList<>();
                        // temporary array list to append all new weight
                        for (int k=0; k<hiddenCellWeight.get(0).size(); k++) {
                            // update hidden cell weight untuk data berikutnya
//                            System.out.println("hhhh_" +"i_" + i  + "k_" + k + hiddenCellWeight.get(i).get(k));
//                            System.out.println("+" + learningrate
//                                    + "*" + deltaHiddenCell.get(i) + "*" + Data.get(j).get(i));
                            double newW = hiddenCellWeight.get(i).get(k) + learningrate*deltaHiddenCell.get(i)*Data.get(j).get(i);
                            newW = Double.parseDouble(df.format(newW));
                            tempALfor1cell.add(newW);
                            // wn' = wn + learningRate * delta i *  x
                        }
                        hiddenCellWeight.set(i, tempALfor1cell);
                    }
                    
                    // update outputCellWeight
                    // TODO
                }
            }
        }
    }
    
    public void PrintHiddenCellWeightTable(ArrayList<ArrayList<Double>> hiddenCellWeight) {
        for (int i=0; i<hiddenCellWeight.size();  i++) {
            // hiddenCellWeight.size() --> jumlah hidden sel
            for (int j=0; j<hiddenCellWeight.get(0).size(); j++) {
                System.out.println("w" + i + "" + j + ":" + hiddenCellWeight.get(i).get(j));
            }
        }
    }
    
    public void PrintDeltaHiddenCell(ArrayList<Double> deltaHidden) {
        for (int i=0; i<deltaHidden.size(); i++) {
            System.out.println("delta<1," + i +"> :" + deltaHidden.get(i));
        }
    }
}
