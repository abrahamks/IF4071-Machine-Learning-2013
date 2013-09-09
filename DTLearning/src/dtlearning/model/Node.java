/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtlearning.model;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author Abraham Krisnanda
 */
public class Node{
    private Attribute attribute;
    private HashMap<String, Object> children;
    
    public Node() {
        
    }
    
    public Node(Attribute attr) {
        this.attribute = attr;
        this.children = new HashMap<>();
        for (int i = 0; i < this.attribute.getAttributeValue().size(); i++) {
            this.children.put(this.attribute.getAttributeValue().get(i), null);
        }
    }

    /**
     * @return the attribute
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
   
    /**
     * @return the children
     */
    public HashMap<String, Object> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(HashMap<String, Object> children) {
        this.children = children;
    }
    
    public boolean isLeaf() {
        boolean flag = true;
        for(int i=0; i < this.getChildren().size(); i++) {
            String k = this.getAttribute().getAttributeValue().get(i); // key
            if (this.getChildren().get(k) instanceof dtlearning.model.Node) {
                // menuju kepada yes / no
                flag = false;
            }
        }   
        return flag;
    }
    
    public String toString() {
    String result="";
    // basis
    for (int i=0; i < this.children.size(); i++) {
        result += "\n" + this.attribute.getAttributeName();
        String keyIndex = this.attribute.getAttributeValue().get(i);
        result += "\n |" + keyIndex; // root
        result += "\n ||_>" + this.children.get(keyIndex).toString();
    }

//        int lv=0;
//        String tree="undefined tree";
//        if (this.label == 1 ) {
//            tree=this.attribute.getAttributeName(); // root
//        }
//        else {
//            tree=this.attribute.getAttributeName();
//            for(int i=0; i < this.getChildren().size(); i++) {
//                tree +="\n" +  this.getChildren().get(this.attribute.getAttributeValue().get(i)).toString();
//            }
    return result;
    }
}
