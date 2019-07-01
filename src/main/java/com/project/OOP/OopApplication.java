package com.project.OOP;

import com.project.OOP.utils.CSVDownloader;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class OopApplication {

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
