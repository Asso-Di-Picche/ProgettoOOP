package com.project.OOP.utils;

import com.project.OOP.model.AgricultureAid;
import com.project.OOP.model.AgricultureAidCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe che fornisce il metodo per parsare il file CSV già scaricato
 */
public class CSVParser {

    /**
     * Metodo che parsa il file CSV già scaricato e salvato con nome data inserendo tutte le infomazioni recuperate
     * sotto forma di oggetti all'interno di {@link AgricultureAidCollection}
     * @return restituisce la collezione di oggetti
     * @throws IOException XXXXXXXXXXXXX
     */
    public static AgricultureAidCollection getDataFromCSV() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("data.csv"));
        String line;
        ArrayList<Float> invs = new ArrayList<>();
        ArrayList<AgricultureAid> aids = new ArrayList<>();

        line = fileReader.readLine();
        while((line = fileReader.readLine()) != null){
            invs.clear();
            String[] values = line.split(";");
            String[] innerValues = values[3].split("\\s?,\\s?");
            String objective = innerValues[0];
            for(String value : Arrays.copyOfRange(innerValues, 1, innerValues.length)){
                try {
                    if(!value.isEmpty())
                        invs.add(Float.parseFloat(value));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            aids.add(new AgricultureAid(values[0], values[1], values[2], objective, invs));
        }
        return new AgricultureAidCollection(aids);
    }

}
