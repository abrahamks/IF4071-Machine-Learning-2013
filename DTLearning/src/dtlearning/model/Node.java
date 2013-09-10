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
    
    public void setAllChildrenPos() {
        // Return the single-node tree Root, label +
        HashMap<String,Object> allPositive = new HashMap<String,Object>();
        for (int i=0; i < this.getAttribute().getAttributeValue().size(); i++) {
            String k = this.getAttribute().getAttributeValue().get(i); // key
            allPositive.put(k,"yes");
        }
        this.children = allPositive;
    }
    
    public void setAllChildrenNeg() {
        // Return the single-node tree Root, label +
        HashMap<String,Object> allNegative = new HashMap<String,Object>();
        for (int i=0; i < this.getAttribute().getAttributeValue().size(); i++) {
            String k = this.getAttribute().getAttributeValue().get(i); // key
            allNegative.put(k,"no");
        }
        this.children = allNegative;
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
    
    public String toString(){
        String result = "Model Pohon ID3";
        result += toString(0);
        return result;
    }
    
    public String toString(int level) {
        String result = "";

        if (this.attribute != null){
            for (int i=0; i < this.children.size(); i++) {
                result += "\n";
                for (int j = 0;j < level; j++){
                    if (j == level - 1){
                        result += " |-> ";
                    }
                    else{
                        result += " | ";
                    }
                }

                String keyIndex = this.attribute.getAttributeValue().get(i);
                result +=  this.attribute.getAttributeName() + " : " + keyIndex;
                result += ((Node)this.children.get(keyIndex)).toString(level + 1);
            }
        }
        else{
            result += " : " + ((this.children != null) ? "yes" : "no");
        }
        return result;
    }
}
