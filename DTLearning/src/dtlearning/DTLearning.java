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
        
        Node i3 = new Node();
        DTLearningOperation D = new DTLearningOperation();
        i3 = D.ID3(AP.Ex, AP.listOfAttribute.get(0), AP.listOfAttribute);
        System.out.println("-----------------------------");
        System.out.println(i3.toString());
    }
}
