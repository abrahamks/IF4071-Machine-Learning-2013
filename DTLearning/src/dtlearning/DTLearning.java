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
        ArffParser AP = new ArffParser("F:\\Amelia\\Documents\\Teknik Informatika 2010\\Semester 7\\IF4071 Machine Learning\\Eksperimen DTL\\playtennis.arff");        System.out.println("isPos" + AP.Ex.isExamplesPositive());
        System.out.println(AP.Ex.isExampleNegative());
        
        System.out.println("check ID 3");
        Node i3 = new Node();
        DTLearningOperation D = new DTLearningOperation();
        i3 = D.ID3(AP.Ex, AP.listOfAttribute.get(0), AP.listOfAttribute);
        System.out.println(i3.toString());
        System.out.println("end checking ID3");
//        // ngetest best IG
                
//        String b = "";
//        b = DTLearningOperation.BestInfGain(AP.Ex);
//        System.out.println("best: " + b);
        
        
//        ArrayList<Attribute> AR = AP.listOfAttribute;
//        @attribute outlook { sunny, overcast, rain }
//        @attribute temperature { hot, mild, cool }
//        @attribute humidity { high, normal }
//        @attribute wind { weak, strong }
//        @attribute playTennis { no, yes}
//        Node o = new Node(AR.get(0));
//        Node h = new Node(AR.get(2));
//        Node w = new Node(AR.get(3));
//        HashMap<String, Object> anak2 = new HashMap<String, Object>();
//        anak2.put(o.getAttribute().getAttributeValue().get(0), h);
//        anak2.put(o.getAttribute().getAttributeValue().get(1), "yes");
//        anak2.put(o.getAttribute().getAttributeValue().get(2), w);
//        HashMap<String, Object> anakH = new HashMap<String, Object>();
//        anakH.put(h.getAttribute().getAttributeValue().get(0), "yes");
//        anakH.put(h.getAttribute().getAttributeValue().get(1), "no");
//        HashMap<String, Object> anakW = new HashMap<String, Object>();
//        anakW.put(w.getAttribute().getAttributeValue().get(0), "yes");
//        anakW.put(w.getAttribute().getAttributeValue().get(1), "no");
//        o.setChildren(anak2);
//        h.setChildren(anakH);
//        w.setChildren(anakW);
//        o.setAllChildrenPos();
//        System.out.println(o.toString());
//        o.setAllChildrenNeg();
//        System.out.println(o.toString());
    }
}
