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
        ArffParser AP = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\coba.arff");
        ArffParser model1 = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\playtennis.arff");
        ArffParser model2 = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\playtennis2.arff");
        ArffParser dataset3 = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\dataset3.arff");
        ArffParser monk = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\train_monk1.arff");
        ArffParser datasetmonk = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\testdatamonk1.arff");
        DTLearningOperation D = new DTLearningOperation();
        Node i3 = new Node();
        i3 = D.ID3(monk.Ex, monk.listOfAttribute.get(0), monk.listOfAttribute);
        
        
        
        System.out.println("-----------------------------");
        System.out.println(i3.toString());
        System.out.println("______________________________");
        Examples classificationResult = new Examples();
        classificationResult = D.CobaClassify(datasetmonk.Ex, i3);
        System.out.println(classificationResult.toString());
//        Examples aa = new Examples();
//        aa = D.BruteForceDataSetGenerator(PT.Ex);
        //aa = D.CobaClassify(AP.Ex,i3);
        //System.out.println(aa.toString());
        
    }
}
