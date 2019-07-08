package com.project.OOP.utils;
import org.json.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Classe che mette a disposizione i metodi per recuperare le infomazioni da un CSV rintracciato parsando un JSON
 */
public class CSVDownloader {

    /**
     * Metodo che riceve un URL che si riferisce ad un JSON che viene scaricato ed inserito all'interno di un JSON object
     * @param url URL che identifica il JSON
     * @return Restituisce il JSON object
     * @throws IOException Errore che viene lanciato nel caso in cui ci siano problemi nell'Input/Output
     */
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

    /**
     * Metodo che parsa il JSON che gli viene dato come argomento cerca di scaricare tutti i link affiancati dalla chiave format che contengono CSV
     * @param json Oggetto JSON che verrà parsato al fine di ottenere il link per il CSV e scaricarlo
     */
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

    /**
     * Metodo che scarica il file di formato CSV all'interno di un file chiamato data tramite un URL che gli viene dato in ingresso
     * @param url URL che si riferisce al file CSV
     * @throws IOException Errore che viene lanciato nel caso in cui ci siano problemi nell'Input/Output
     */
    private static void downloadCSVFromURL(String url) throws IOException{
        if(!Files.exists(Paths.get("data.csv"))){
            InputStream is = new URL(url).openStream();
            Files.copy(is, Paths.get("data.csv"), StandardCopyOption.REPLACE_EXISTING);
        } else {
            System.out.println("Il file CSV è già stato scaricato!");
        }
    }

}
