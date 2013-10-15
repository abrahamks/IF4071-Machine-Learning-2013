/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Abraham Krisnanda
 */
public class ArffParserANN {
    private BufferedReader br = null;
    public ArrayList<ArrayList<Integer>> data;
    public ArrayList<ArrayList<String>> attributes;
    public ArrayList<Integer> target;
    public ArffParserANN (String fileLocation, String mode) {
        int i = 0;
        data = new ArrayList<>();
        attributes = new ArrayList<>();
        target = new ArrayList<>();
        // insialisasi data dan attributes
        ArrayList<ArrayList<Integer>> tempData = new ArrayList<>(); 
        // temporary Data untuk melakukan konversi ke Data
        try {
            String currentLine;
            String identifier;
            br = new BufferedReader(new FileReader(fileLocation));
            while ((currentLine = br.readLine()) != null) {
                i++;
                if (!currentLine.equals("")) {
                    identifier = currentLine.split(" ")[0];
                    if (identifier.startsWith("@")) {
                        switch (identifier) {
                            case "@relation":
                                break;
                            case "@attribute":
                            {
                                // @attribute outlook { sunny, overcast, rain }
                                // outlook --> ga dipake
                                // attrValue = sunny;overcast;rain
                                // tempAttribute.setAttributeName(currentLine.split(" ")[1]);
                                String cl = currentLine.replaceAll("\\s",""); // remove whitespace
                                String[] attrValue = cl.split("\\{")[1].split(",");
                                ArrayList<String> tempListOfAttributeValues = new ArrayList<>();
                                for (int j=0; j<attrValue.length; j++) {
                                    // convert array attrValue to ArrayList<Integer> tempListOfAttributeValues
                                    if (j==(attrValue.length-1)) {
                                        // last element, remove the "}"
                                        tempListOfAttributeValues.add(attrValue[j].split("}")[0]);
                                    }
                                    else {
                                        tempListOfAttributeValues.add(attrValue[j]);
                                    }
                                }
                                attributes.add(tempListOfAttributeValues);
                            }
                                break;
                            case "@data":
                                break;
                        }
                    }
                    else {
                        // @data
                        String cl = currentLine.replaceAll("\\s", ""); // remove whitespaces
                        String[] dataValue = cl.split(",");
                        switch(mode) {
                            case "index":
                            {
                                // satu baris temporary data
                                ArrayList<Integer> tempDataRow = new ArrayList<>();
                                for (int j=0; j < dataValue.length; j++) {
                                    // iterasi setiap data values
                                    tempDataRow.add(getValueFromIndex(dataValue[j], attributes.get(j)));
                                    if (j == (dataValue.length -1)) {
                                        // ketika index terakhir tambahkan tempDataRow ke tempData
                                        tempData.add(tempDataRow);
                                    }
                                }
                                break;
                            }
                            case "biner":
                            {
                                // satu baris temporary data
                                ArrayList<Integer> tempDataRow = new ArrayList<>();
                                for (int j=0; j < dataValue.length; j++) {
                                    // iterasi setiap data values
                                    if (isOneColumn(attributes.get(j))) {
                                        // dataValue[j] bernilai 0 || 1
                                        tempDataRow.add(getValueFromIndex(dataValue[j], attributes.get(j)));
                                        if (j == (dataValue.length -1)) {
                                            // ketika index terakhir tambahkan tempDataRow ke tempData
                                            tempData.add(tempDataRow);
                                        }
                                    }
                                    else {
                                        // dataValue [j] bernilai 001 || 010 || 100
                                        switch (getValueFromIndex(dataValue[j], attributes.get(j))){
                                            case 0:
                                            // index 0 --> 100
                                            {
                                                tempDataRow.add(1);
                                                tempDataRow.add(0);
                                                tempDataRow.add(0);
                                                break;
                                            }
                                            case 1:
                                            // index 1 --> 010
                                            {
                                                tempDataRow.add(0);
                                                tempDataRow.add(1);
                                                tempDataRow.add(0);
                                                break;
                                            }
                                            case 2:
                                            // index 3 --> 001
                                            {
                                                tempDataRow.add(0);
                                                tempDataRow.add(0);
                                                tempDataRow.add(1);
                                                break;
                                            }    
                                        }
                                        if (j == (dataValue.length -1)) {
                                            // ketika index terakhir tambahkan tempDataRow ke tempData
                                            tempData.add(tempDataRow);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
            data = convertData(tempData);
            target.addAll(data.get(data.size()-1)); // index terakhir merupakan target
            data.remove(data.size()-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    public int getValueFromIndex(String dataValue, ArrayList<String> attributeValues) {
        int index = -1;
        for (int i=0; i < attributeValues.size(); i++) {
            if (dataValue.equals(attributeValues.get(i))){
                index = i;
                break;
            }
        }
        return index;
    }

    public boolean isOneColumn(ArrayList<String> attributeValues) {
        //Return true apabila hanya memerlukan satu kolom untuk merepresentasikan data (bukan 3 kolom)
        if (attributeValues.size() == 2) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public static ArrayList<ArrayList<Integer>> convertData(ArrayList<ArrayList<Integer>> tempData) {
        // convert data into 
        ArrayList<ArrayList<Integer>> _data = new ArrayList<>();
        for (int j=0; j < tempData.get(0).size(); j++) {
            // j --> iterator for _data
            ArrayList<Integer> row2Column = new ArrayList<>();
            // temporary ArrayList for convert
            // 0 , 0 , 0 , 1
            // 0 , 1 , 1 , 1
            // to
            // x1 = 0 , 0
            // x2 = 0 , 1
            // x3 = 0 , 1
            // x4 = 1 , 1
            
            for (int i=0; i< tempData.size(); i++) {
                // i --> iterator for tempData
                row2Column.add(tempData.get(i).get(j));
            }
            _data.add(row2Column);
        }
        return _data;
    }
    
    
    
    
}


