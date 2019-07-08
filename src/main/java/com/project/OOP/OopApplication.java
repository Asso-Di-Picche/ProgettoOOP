package com.project.OOP;

import com.project.OOP.utils.CSVDownloader;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * Classe da cui parte l'esecuzione del programma, avviando il servizio
 */
@SpringBootApplication
public class OopApplication {

	/**
	 * Funzione che inizializza l'applicazione e la fa partire. Prima di avviare l'applicazione Spring Boot verrà scaricato
	 * il file CSV dal JSON fornito tramite le specifiche del progetto, nel caso in cui questo non sia presente
	 * @param args Eventuali argomenti passati tramite cli
	 */
	public static void main(String[] args) {
		try {
			JSONObject obj = CSVDownloader.getJSONFromURL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=nywhJT0h8NIfj3nWwXgjPw");
			CSVDownloader.downloadCSVfromJSON(obj);
		} catch (IOException e){
			System.out.println(e);
		}
		SpringApplication.run(OopApplication.class, args);
	}

}
