package com.project.OOP.utils;

import com.project.OOP.AgricultureAid;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVParser {

    public static ArrayList<AgricultureAid> getDataFromCSV() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("data.csv"));
        String line;
        ArrayList<Float> invs = new ArrayList<>();
        ArrayList<AgricultureAid> aids = new ArrayList<>();

        line = fileReader.readLine();
        while((line = fileReader.readLine()) != null){
            invs.clear();
            String[] values = line.split(";");
            for(String value : values[3].split("(TOTAL)?\\s?,\\s?")){
                try {
                    invs.add(Float.parseFloat(value));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            aids.add(new AgricultureAid(values[0], values[1], values[2], invs));
        }
        return aids;
    }

}
