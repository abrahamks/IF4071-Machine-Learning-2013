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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArffParser AP = new ArffParser("F:\\Amelia\\Documents\\Teknik Informatika 2010\\Semester 7\\IF4071 Machine Learning\\Eksperimen DTL\\playtennis.arff");
//        ArffParser model1 = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\playtennis.arff");
//        ArffParser model2 = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\playtennis2.arff");
//        ArffParser dataset3 = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\dataset3.arff");
        DTLearningOperation D = new DTLearningOperation();
//        Node i3 = new Node();
//        i3 = D.ID3(AP.Ex, AP.listOfAttribute.get(0), AP.listOfAttribute);
        
        Examples train = new Examples();
        train = D.SplitforDataTrain(AP.Ex, 0.6);
        System.out.println("TRAINING SET:");
        System.out.println(train.toString());
        
        Examples test = new Examples();
        test = D.SplitforDataTest(AP.Ex, 0.6);
        System.out.println("\nTEST SET:");
        System.out.println(test.toString());
        
        
        System.out.println("-----------------------------");
//        System.out.println(i3.toString());
        System.out.println("______________________________");
        Examples classificationResult = new Examples();
//        classificationResult = D.CobaClassify(dataset3.Ex, i3);
//        System.out.println(classificationResult.toString());
//        Examples aa = new Examples();
//        aa = D.BruteForceDataSetGenerator(PT.Ex);
        //aa = D.CobaClassify(AP.Ex,i3);
        //System.out.println(aa.toString());
        
    }
}
