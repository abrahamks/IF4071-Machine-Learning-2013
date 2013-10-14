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
 * @author Anasthasia
 */
public class GeneralParser {
    
    private BufferedReader br = null;
    
    public GeneralParser(String filelocation){
        ArrayList<ArrayList<Integer>> Data = new ArrayList<ArrayList<Integer>>();
        
        try{
            int i=0;
            String currentLine;
            String identifier;
            br = new BufferedReader(new FileReader(filelocation));
            while((currentLine = br.readLine()) != null){         
                if(!currentLine.equals("")){
                    identifier = currentLine.split(" ")[0];
                    if(identifier.startsWith("@")){
                        switch(identifier){
                            case "@data":                                
                                break;
                            case "@target":
                                ArrayList<Integer> target = new ArrayList<Integer>();
                                String[] sTarget = currentLine.split(" ")[1].split(",");
                                for(int j=0; j<sTarget.length; j++){
                                    target.add(Integer.parseInt(sTarget[j]));
                                }
                                System.out.println("target:");
                                for(int k=0; k< target.size();k++){
                                    System.out.print(target.get(k) + " ");
                                }
                                System.out.println("\n");
                                break;
                            case "@weight":
                                ArrayList<Double> weight = new ArrayList<Double>();
                                String[] sWeight = currentLine.split(" ")[1].split(",");
                                for(int j=0; j<sWeight.length; j++){
                                    double w = Double.parseDouble(sWeight[j]);
                                    weight.add(w);
                                }
                                System.out.println("weight:");
                                for(int k=0; k< weight.size();k++){
                                    System.out.print(weight.get(k) + " ");
                                }
                                break;
                        }
                    }
                    else{
                        // @data
                        ArrayList<Integer> d = new ArrayList<Integer>();
                        String[] _data = currentLine.split(",");
                        for(int j=0; j<_data.length; j++){
                            d.add(Integer.parseInt(_data[j]));
                        }
                        Data.add(d);
                        for(int k=0; k< Data.get(i).size();k++){
                            System.out.print(Data.get(i).get(k) + " ");
                        }
                        System.out.println();
                        i++;
                    }   
                }
            }
            Data = ArffParserANN.convertData(Data);
            System.out.println(Data.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
