package com.project.OOP.utils;
import org.json.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CSVDownloader {

    public static JSONObject getJSONFromURL(String url) throws IOException {
        StringBuilder sb = new StringBuilder();
        int currentChar;
        try(InputStream is = new URL(url).openStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((currentChar = br.read()) != -1){
                sb.append((char) currentChar);
            }
            JSONObject json = new JSONObject(sb.toString());
            return json;
        }
    }

    public static void downloadCSVfromJSON(JSONObject json){
        JSONObject result = json.getJSONObject("result");
        JSONArray data = result.getJSONArray("resources");
        for(Object resource : data){
            if(resource instanceof JSONObject){
                JSONObject jsonResource = (JSONObject) resource;
                if(jsonResource.getString("format").contains("CSV")){
                    try {
                        downloadCSVFromURL(jsonResource.getString("url"));
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    }
                }
            }
        }

    }

    private static void downloadCSVFromURL(String url) throws IOException{
        if(!Files.exists(Paths.get("data.csv"))){
            InputStream is = new URL(url).openStream();
            Files.copy(is, Paths.get("data.csv"), StandardCopyOption.REPLACE_EXISTING);
        } else {
            System.out.println("Il file CSV è già stato scaricato!");
        }
    }

}
