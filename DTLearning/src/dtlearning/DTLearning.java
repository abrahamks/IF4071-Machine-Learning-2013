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
        ArffParser PT = new ArffParser("F:\\4th Grade\\1st Semester\\IF4071 - Machine Learning\\Eksperimen DTL\\playtennis.arff");
        DTLearningOperation D = new DTLearningOperation();
        Node i3 = new Node();
        i3 = D.ID3(PT.Ex, PT.listOfAttribute.get(0), PT.listOfAttribute);
        
        
        
        System.out.println("-----------------------------");
        System.out.println(i3.toString());
        System.out.println("______________________________");
        Examples aa = new Examples();
        aa = D.dataSetGenerator(PT.Ex);
        //aa = D.CobaClassify(AP.Ex,i3);
        //System.out.println(aa.toString());
        
    }
}
