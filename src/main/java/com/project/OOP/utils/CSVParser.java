package com.project.OOP.utils;

import com.project.OOP.AgricultureAid;
import com.project.OOP.AgricultureAidCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVParser {

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
