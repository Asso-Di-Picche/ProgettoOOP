package com.project.OOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.OOP.utils.CSVDownloader;
import com.project.OOP.utils.CSVParser;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class BaseController {
    @RequestMapping(value = "/obj", method = RequestMethod.GET, produces="application/json")
    String test(){
        try {
            JSONObject obj = CSVDownloader.getJSONFromURL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=nywhJT0h8NIfj3nWwXgjPw");
            CSVDownloader.downloadCSVfromJSON(obj);
            ArrayList<AgricultureAid> objects = CSVParser.getDataFromCSV();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(objects);
        } catch (IOException e){
            return e.toString();
        }
    }
}
