/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtlearning.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
/**
 *
 * @author Abraham Krisnanda
 */
public class ArffParser {
    private BufferedReader br = null;
    public ArrayList<ArrayList<String>> attribute;
    public ArrayList<ArrayList<String>> data;
    int i=0;
    public ArffParser(String fileLocation ) {
        try {
            String currentLine;
            String identifier;
            br = new BufferedReader(new FileReader(fileLocation));
            attribute = new ArrayList<ArrayList<String>>();
            data = new ArrayList<ArrayList<String>>();
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
                                String cl = currentLine.replaceAll("\\s",""); // remove whitespace
                                String[] attrValue = cl.split("\\{")[1].split(",");
                                ArrayList<String> tempValue = new ArrayList<>();
                                for (int i=0; i<attrValue.length;i++) {
                                    if (i==(attrValue.length-1)) {
                                        // last element, remove the "}"
                                        tempValue.add(attrValue[i].split("}")[0]);
                                    }
                                    else {
                                        tempValue.add(attrValue[i]);
                                    }
                                }
                                attribute.add(tempValue);
                            }
                                break;
                            case "@data":
                                break;
                        }
                    }
                    else {
                        // data
                        String cl = currentLine.replaceAll("\\s", ""); // remove whitespaces
                        String[] dataValue = cl.split(",");
                        ArrayList<String> tempData = new ArrayList<>();
                        for (int i=0; i < dataValue.length; i++) {
                            tempData.add(dataValue[i]);
                        }
                        data.add(tempData);
                    }
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
                try {
                        if (br != null)br.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
        }

    }
}
